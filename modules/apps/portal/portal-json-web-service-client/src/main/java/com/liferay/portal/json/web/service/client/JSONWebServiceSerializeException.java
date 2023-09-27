/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceSerializeException extends JSONWebServiceException {

	public JSONWebServiceSerializeException(String message) {
		super(message);
	}

	public JSONWebServiceSerializeException(
		String message, Throwable throwable) {

		super(message, throwable);
	}

	public JSONWebServiceSerializeException(Throwable throwable) {
		super(throwable);
	}

}