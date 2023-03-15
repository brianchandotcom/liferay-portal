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

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;

/**
 * @author Cheryl Tang
 */
public class FaroResponseHandler implements ResponseHandler<AppResponse> {

	/**
	 * Handles the response from a request.
	 *
	 * @param  httpResponse the response to handle
	 * @return the AppResponse containing the status code and JSON object as a
	 *         String
	 * @throws IOException if an exception occurred
	 */
	@Override
	public AppResponse handleResponse(HttpResponse httpResponse)
		throws IOException {

		StatusLine statusLine = httpResponse.getStatusLine();

		int statusCode = statusLine.getStatusCode();

		String reasonPhrase = statusLine.getReasonPhrase();

		if (statusCode == 204) {
			return new AppResponse(statusCode, reasonPhrase, null);
		}

		HttpEntity httpEntity = httpResponse.getEntity();

		if (httpEntity == null) {
			throw new ClientProtocolException("Response contains no content");
		}

		ContentResponseHandler contentResponseHandler =
			new ContentResponseHandler();

		Content content = contentResponseHandler.handleEntity(httpEntity);

		return new AppResponse(statusCode, reasonPhrase, content.asString());
	}

}