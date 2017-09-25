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

package com.liferay.petra.json.web.service.client;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class JSONWebServiceInvocationException extends Exception {

	public JSONWebServiceInvocationException(String message) {
		super(message);
	}

	public JSONWebServiceInvocationException(String message, int status) {
		super(message);

		_status = status;
	}

	public JSONWebServiceInvocationException(
		String message, int status, Throwable cause) {

		super(message, cause);

		_status = status;
	}

	public JSONWebServiceInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSONWebServiceInvocationException(Throwable cause) {
		super(cause);
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	@Override
	public String toString() {
		String message = super.getMessage();

		if ((message != null) && (message.length() > 0)) {
			return message;
		}

		return "Server returned status " + _status;
	}

	private int _status;

}