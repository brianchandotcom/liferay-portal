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

package com.liferay.talend.runtime.apio;

/**
 * @author Zoltán Takács  Apio Architect response. It stores HTTP status code
 *         and body string
 */
public final class ApioResult {

	/**
	 * Constructor sets status code and response body
	 *
	 * @param statusCode status code
	 * @param body response body; in case of <code>null</code> "" will be set
	 */
	public ApioResult(int statusCode, String body) {
		_statusCode = statusCode;

		if (body == null) {
			body = "";
		}

		_body = body;
	}

	/**
	 * Returns response body
	 *
	 * @return response body
	 */
	public String getBody() {
		return _body;
	}

	/**
	 * Returns response status code
	 *
	 * @return response status code
	 */
	public int getStatusCode() {
		return _statusCode;
	}

	private final String _body;
	private final int _statusCode;

}