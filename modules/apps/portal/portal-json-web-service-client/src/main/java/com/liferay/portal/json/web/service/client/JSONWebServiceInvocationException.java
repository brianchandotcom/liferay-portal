/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class JSONWebServiceInvocationException extends JSONWebServiceException {

	public JSONWebServiceInvocationException(String message) {
		super(message);
	}

	public JSONWebServiceInvocationException(String message, int status) {
		super(message, status);
	}

	public JSONWebServiceInvocationException(
		String message, int status, Throwable throwable) {

		super(message, status, throwable);
	}

	public JSONWebServiceInvocationException(
		String message, Throwable throwable) {

		super(message, throwable);
	}

	public JSONWebServiceInvocationException(Throwable throwable) {
		super(throwable);
	}

}