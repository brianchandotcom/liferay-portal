/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectEntryVersionLatestException extends PortalException {

	public ObjectEntryVersionLatestException(String msg) {
		super(msg);
	}

	public ObjectEntryVersionLatestException(
		String message, String messageKey) {

		super(message);

		_messageKey = messageKey;
	}

	public ObjectEntryVersionLatestException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectEntryVersionLatestException(Throwable throwable) {
		super(throwable);
	}

	public String getMessageKey() {
		return _messageKey;
	}

	private String _messageKey;

}