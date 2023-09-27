/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch.monitoring.web.internal.servlet.display.context;

/**
 * @author André de Oliveira
 */
public class ErrorDisplayContext {

	public String getConnectExceptionAddress() {
		return _connectExceptionAddress;
	}

	public Exception getException() {
		return _exception;
	}

	public void setConnectExceptionAddress(String connectExceptionAddress) {
		_connectExceptionAddress = connectExceptionAddress;
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	private String _connectExceptionAddress;
	private Exception _exception;

}