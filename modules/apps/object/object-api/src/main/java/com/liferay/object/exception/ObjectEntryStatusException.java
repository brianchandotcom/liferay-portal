/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pedro Tavares
 */
public class ObjectEntryStatusException extends PortalException {

	public ObjectEntryStatusException(String message) {
		super(message);
	}

	public ObjectEntryStatusException(String message, String messageKey) {
		super(message);

		_messageKey = messageKey;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	private String _messageKey;

}