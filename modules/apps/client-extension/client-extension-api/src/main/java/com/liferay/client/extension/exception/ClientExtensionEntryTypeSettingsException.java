/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class ClientExtensionEntryTypeSettingsException extends PortalException {

	public ClientExtensionEntryTypeSettingsException(
		String messageKey, Object... messageArguments) {

		_messageKey = messageKey;
		_messageArguments = messageArguments;
	}

	public Object[] getMessageArguments() {
		return _messageArguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	private final Object[] _messageArguments;
	private final String _messageKey;

}