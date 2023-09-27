/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class ObjectActionParametersException extends PortalException {

	public ObjectActionParametersException(Map<String, Object> messageKeys) {
		_messageKeys = messageKeys;
	}

	public Map<String, Object> getMessageKeys() {
		return _messageKeys;
	}

	private final Map<String, Object> _messageKeys;

}