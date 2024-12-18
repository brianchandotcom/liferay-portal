/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectRelationshipEdgeException extends PortalException {

	public ObjectRelationshipEdgeException(
		List<Object> arguments, String message, String messageKey) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	public ObjectRelationshipEdgeException(String message, String messageKey) {
		super(message);

		_messageKey = messageKey;
	}

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	private List<Object> _arguments;
	private final String _messageKey;

}