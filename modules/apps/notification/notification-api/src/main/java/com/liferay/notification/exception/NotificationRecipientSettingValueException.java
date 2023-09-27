/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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