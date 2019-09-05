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

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.NestableRuntimeException;

/**
 * @author Gergely Mathe
 */
public class ExportImportRuntimeException extends NestableRuntimeException {

	public ExportImportRuntimeException() {
	}

	public ExportImportRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ExportImportRuntimeException(Throwable cause) {
		super(cause);
	}

	public String getClassName() {
		return _className;
	}

	public String[] getData() {
		return _data;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setData(String data) {
		_data = new String[] {data};
	}

	public void setData(String[] data) {
		_data = data;
	}

	public void setMessageKey(String messageKey) {
		_messageKey = messageKey;
	}

	private String _className;
	private String[] _data;
	private String _messageKey;

}