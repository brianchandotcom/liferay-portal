/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.NestableRuntimeException;

/**
 * @author Gergely Mathe
 */
public class ExportImportRuntimeException extends NestableRuntimeException {

	public ExportImportRuntimeException() {
	}

	public ExportImportRuntimeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ExportImportRuntimeException(Throwable throwable) {
		super(throwable);
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