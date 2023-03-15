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

package com.liferay.osb.faro.functional.test.util;

/**
 * @author Cheryl Tang
 */
public class AppResponse {

	public AppResponse(
		int httpStatusCode, String reasonPhrase, String responseBody) {

		_httpStatusCode = httpStatusCode;
		_reasonPhrase = reasonPhrase;
		_responseBody = responseBody;
	}

	public int getHttpStatusCode() {
		return _httpStatusCode;
	}

	public String getReasonPhrase() {
		return _reasonPhrase;
	}

	public String getResponseBody() {
		return _responseBody;
	}

	private final int _httpStatusCode;
	private final String _reasonPhrase;
	private final String _responseBody;

}