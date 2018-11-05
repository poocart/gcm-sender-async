/**
 * Copyright (C) 2015 Open Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.whispersystems.gcm.server;

/**
 * The result of a GCM send operation.
 */
public class Result {

  private final Object context;
  private final String canonicalRegistrationId;
  private final String messageId;
  private final String error;

  Result(Object context, String canonicalRegistrationId, String messageId, String error) {
    this.context                 = context;
    this.canonicalRegistrationId = canonicalRegistrationId;
    this.messageId               = messageId;
    this.error                   = error;
  }

  Result(Object context, String messageId, String error) {
    this.context                 = context;
    this.messageId               = messageId;
    this.error                   = error;
    this.canonicalRegistrationId = null;
  }

  /**
   * Returns the "canonical" GCM registration ID for this destination.
   * See GCM documentation for details.
   * @return The canonical GCM registration ID.
   */
  public String getCanonicalRegistrationId() {
    return canonicalRegistrationId;
  }

  /**
   * @return If a "canonical" GCM registration ID is present in the response.
   */
  public boolean hasCanonicalRegistrationId() {
    return canonicalRegistrationId != null && !canonicalRegistrationId.isEmpty();
  }

  /**
   * @return The assigned GCM message ID, if successful.
   */
  public String getMessageId() {
    return messageId;
  }

  /**
   * @return The raw error string, if present.
   */
  public String getError() {
    return error;
  }

  /**
   * @return If the send was a success.
   */
  public boolean isSuccess() {
    return messageId != null && !messageId.isEmpty() && (error == null || error.isEmpty());
  }

  /**
   * @return If the destination GCM registration ID is no longer registered.
   */
  public boolean isUnregistered() {
    return "UNREGISTERED".equals(error);
  }

  /**
   * @return If messages to this device are being throttled.
   */
  public boolean isThrottled() {
    return "QUOTA_EXCEEDED".equals(error);
  }

  /**
   * @return If the destination GCM registration ID is invalid.
   */
  public boolean isInvalidRegistrationId() {
    return "SENDER_ID_MISMATCH".equals(error);
  }

  /**
   * @return The context passed into Sender.send(), if any.
   */
  public Object getContext() {
    return context;
  }
}
