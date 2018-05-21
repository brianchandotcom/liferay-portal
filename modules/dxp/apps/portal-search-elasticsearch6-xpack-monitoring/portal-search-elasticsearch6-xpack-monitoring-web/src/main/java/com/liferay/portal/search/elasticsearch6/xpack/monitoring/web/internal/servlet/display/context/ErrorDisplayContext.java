/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.servlet.display.context;

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