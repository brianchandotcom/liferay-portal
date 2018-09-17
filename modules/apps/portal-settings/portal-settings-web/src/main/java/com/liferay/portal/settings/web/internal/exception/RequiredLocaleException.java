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

package com.liferay.portal.settings.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Samuel Trong Tran
 */
public class RequiredLocaleException extends PortalException {

	public RequiredLocaleException() {
	}

	public RequiredLocaleException(String msg) {
		super(msg);
	}

	public RequiredLocaleException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RequiredLocaleException(
		String[] messageArguments, String messageKey) {

		_messageArguments = messageArguments;
		_messageKey = messageKey;
	}

	public RequiredLocaleException(Throwable cause) {
		super(cause);
	}

	public String[] getMessageArguments() {
		return _messageArguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public void setMessageArguments(String[] messageArguments) {
		_messageArguments = messageArguments;
	}

	public void setMessageKey(String messageKey) {
		_messageKey = messageKey;
	}

	private String[] _messageArguments;
	private String _messageKey;

}