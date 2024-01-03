/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectEntryCountException extends PortalException {

	public ObjectEntryCountException() {
	}

	public ObjectEntryCountException(
		List<Object> arguments, String message, String messageKey,
		String objectDefinitionLabel) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
		_objectDefinitionLabel = objectDefinitionLabel;
	}

	public ObjectEntryCountException(String message) {
		super(message);
	}

	public ObjectEntryCountException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectEntryCountException(Throwable throwable) {
		super(throwable);
	}

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public String getObjectDefinitionLabel() {
		return _objectDefinitionLabel;
	}

	private List<Object> _arguments;
	private String _messageKey;
	private String _objectDefinitionLabel;

}