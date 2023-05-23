/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.notification.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Murilo Stodolni
 */
public class NotificationRecipientSettingValueException
	extends PortalException {

	public String getMessageKey() {
		return _messageKey;
	}

	public static class FromMustNotBeNull
		extends NotificationRecipientSettingValueException {

		public FromMustNotBeNull() {
			super("From is null", "from-is-required");
		}

	}

	public static class FromNameMustNotBeNull
		extends NotificationRecipientSettingValueException {

		public FromNameMustNotBeNull() {
			super("From name is null", "from-name-is-required");
		}

	}

	public static class ToMustNotBeNull
		extends NotificationRecipientSettingValueException {

		public ToMustNotBeNull() {
			super("To is null", "to-is-required");
		}

	}

	private NotificationRecipientSettingValueException(
		String message, String messageKey) {

		super(message);

		_messageKey = messageKey;
	}

	private final String _messageKey;

}