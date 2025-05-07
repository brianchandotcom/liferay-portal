/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class RequiredObjectEntryVersionException extends PortalException {

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustNotDeleteLatestVersion
		extends RequiredObjectEntryVersionException {

		public MustNotDeleteLatestVersion(String message, String messageKey) {
			super(message, messageKey);
		}

	}

	public static class MustNotDeleteUniqueVersion
		extends RequiredObjectEntryVersionException {

		public MustNotDeleteUniqueVersion(String message, String messageKey) {
			super(message, messageKey);
		}

	}

	private RequiredObjectEntryVersionException(String msg, String messageKey) {
		super(msg);

		_messageKey = messageKey;
	}

	private final String _messageKey;

}