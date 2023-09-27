/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client;

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

	public JSONWebServiceTransportException(
		String message, Throwable throwable) {

		super(message, throwable);
	}

	public JSONWebServiceTransportException(Throwable throwable) {
		super(throwable);
	}

	public static class AuthenticationFailure
		extends JSONWebServiceTransportException {

		public AuthenticationFailure(String message) {
			super(message);
		}

		public AuthenticationFailure(String message, Throwable throwable) {
			super(message, throwable);
		}

		public AuthenticationFailure(Throwable throwable) {
			super(throwable);
		}

	}

	public static class CommunicationFailure
		extends JSONWebServiceTransportException {

		public CommunicationFailure(String message, int status) {
			super(message, status);
		}

		public CommunicationFailure(String message, Throwable throwable) {
			super(message, throwable);
		}

		public CommunicationFailure(Throwable throwable) {
			super(throwable);
		}

	}

	public static class SigningFailure
		extends JSONWebServiceTransportException {

		public SigningFailure(String message, int status) {
			super(message, status);
		}

		public SigningFailure(String message, Throwable throwable) {
			super(message, throwable);
		}

		public SigningFailure(Throwable throwable) {
			super(throwable);
		}

	}

}