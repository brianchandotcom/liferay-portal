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

import com.liferay.petra.string.StringPool;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

/**
 * @author Cheryl Tang
 */
public class FaroRestUtil {

	public static void clearCache() {
		try {
			delete(FaroPagePool.getEndpoint("/main", "/cache"), false);
		}
		catch (Exception exception) {
		}
	}

	public static AppResponse delete(String url, boolean allowUnsuccessful)
		throws Exception {

		return _execute(Request.Delete(url), allowUnsuccessful);
	}

	public static AppResponse get(String url, boolean allowUnsuccessful)
		throws Exception {

		return _execute(Request.Get(url), allowUnsuccessful);
	}

	public static AppResponse post(String url, boolean allowUnsuccessful)
		throws Exception {

		return _execute(Request.Post(url), allowUnsuccessful);
	}

	public static AppResponse post(
			String url, String json, boolean allowUnsuccessful)
		throws Exception {

		Request request = Request.Post(url);

		return _execute(
			request.bodyString(json, ContentType.APPLICATION_JSON),
			allowUnsuccessful);
	}

	private static String _encodeAuthorizationFields(
		String userName, String password) {

		String authorizationString = userName.concat(
			StringPool.COLON
		).concat(
			password
		);

		return new String(
			Base64.encodeBase64(
				authorizationString.getBytes(StandardCharsets.UTF_8)),
			StandardCharsets.UTF_8);
	}

	/**
	 * Sends an API request and returns an AppResponse containing the status
	 * code and a JSON object representation of the response body.
	 *
	 * @param  request the API request to send
	 * @param  allowUnsuccessful flag of whether to allow responses with
	 *         unsuccessful status codes
	 * @return the AppResponse containing the status code and JSON object
	 *         representation of the response
	 * @throws Exception if an exception occurred
	 */
	private static AppResponse _execute(
			Request request, boolean allowUnsuccessful)
		throws Exception {

		Executor executor = Executor.newInstance();

		request.addHeader(
			"Authorization",
			"Basic " + _encodeAuthorizationFields("test@liferay.com", "test"));

		Response response = executor.execute(request);

		AppResponse appResponse = response.handleResponse(_faroResponseHandler);

		int statusCode = appResponse.getHttpStatusCode();

		if ((statusCode >= 300) && !allowUnsuccessful) {
			throw new HttpResponseException(
				statusCode, appResponse.getReasonPhrase());
		}

		return appResponse;
	}

	private static final FaroResponseHandler _faroResponseHandler =
		new FaroResponseHandler();

}