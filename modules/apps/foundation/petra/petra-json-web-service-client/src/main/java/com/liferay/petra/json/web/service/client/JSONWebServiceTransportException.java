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
 */
public class JSONWebServiceTransportException extends JSONWebServiceException {

	public JSONWebServiceTransportException(String message) {
		super(message);
	}

	public JSONWebServiceTransportException(String message, int status) {
		super(message, status);
	}

	public JSONWebServiceTransportException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSONWebServiceTransportException(Throwable cause) {
		super(cause);
	}

	public static class AuthenticationFailure
		extends JSONWebServiceTransportException {

		public AuthenticationFailure(String message) {
			super(message);
		}

		public AuthenticationFailure(String message, Throwable cause) {
			super(message, cause);
		}

		public AuthenticationFailure(Throwable cause) {
			super(cause);
		}

	}

	public static class CommunicationFailure
		extends JSONWebServiceTransportException {

		public CommunicationFailure(String message, int status) {
			super(message, status);
		}

		public CommunicationFailure(String message, Throwable cause) {
			super(message, cause);
		}

		public CommunicationFailure(Throwable cause) {
			super(cause);
		}

	}

	public static class SigningFailure
		extends JSONWebServiceTransportException {

		public SigningFailure(String message, int status) {
			super(message, status);
		}

		public SigningFailure(String message, Throwable cause) {
			super(message, cause);
		}

		public SigningFailure(Throwable cause) {
			super(cause);
		}

	}

}