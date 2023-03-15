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

package com.liferay.osb.faro.engine.client.http.client;

import com.liferay.osb.faro.util.FaroRequestAudit;
import com.liferay.osb.faro.util.FaroThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.IOException;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Shinn Lok
 */
public class AuditClientHttpRequestInterceptor
	implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(
			HttpRequest httpRequest, byte[] bytes,
			ClientHttpRequestExecution clientHttpRequestExecution)
		throws IOException {

		FaroRequestAudit faroRequestAudit =
			FaroThreadLocal.getFaroRequestAudit();

		if ((faroRequestAudit == null) || !faroRequestAudit.isEnabled()) {
			return clientHttpRequestExecution.execute(httpRequest, bytes);
		}

		FaroRequestAudit childFaroRequestAudit = new FaroRequestAudit();

		HttpMethod httpMethod = httpRequest.getMethod();

		if (httpMethod != null) {
			childFaroRequestAudit.setMethod(httpMethod.toString());
		}

		URI uri = httpRequest.getURI();

		String urlPath = uri.getPath();

		String query = uri.getQuery();

		if (query != null) {
			urlPath = StringBundler.concat(urlPath, StringPool.QUESTION, query);
		}

		childFaroRequestAudit.setURLPath(urlPath);

		childFaroRequestAudit.setStartTime(System.currentTimeMillis());

		ClientHttpResponse clientHttpResponse = null;

		try {
			clientHttpResponse = clientHttpRequestExecution.execute(
				httpRequest, bytes);

			return clientHttpResponse;
		}
		finally {
			childFaroRequestAudit.setEndTime(System.currentTimeMillis());

			if (clientHttpResponse != null) {
				childFaroRequestAudit.setStatusCode(
					clientHttpResponse.getRawStatusCode());
			}

			faroRequestAudit.addChildFaroRequestAudit(childFaroRequestAudit);
		}
	}

}