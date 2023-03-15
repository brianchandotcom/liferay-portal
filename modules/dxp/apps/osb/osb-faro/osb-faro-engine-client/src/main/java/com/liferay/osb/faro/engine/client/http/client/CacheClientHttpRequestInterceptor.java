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

import com.liferay.osb.faro.engine.client.FaroClientHttpResponse;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.IOException;

import java.util.List;

import org.apache.http.HttpStatus;

import org.springframework.cache.Cache;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Shinn Lok
 */
public class CacheClientHttpRequestInterceptor
	implements ClientHttpRequestInterceptor {

	public CacheClientHttpRequestInterceptor(Cache cache) {
		_cache = cache;
	}

	@Override
	public ClientHttpResponse intercept(
			HttpRequest httpRequest, byte[] bytes,
			ClientHttpRequestExecution clientHttpRequestExecution)
		throws IOException {

		HttpMethod httpMethod = httpRequest.getMethod();

		if ((httpMethod != null) &&
			(httpMethod.equals(HttpMethod.PATCH) ||
			 httpMethod.equals(HttpMethod.POST) ||
			 httpMethod.equals(HttpMethod.PUT) ||
			 httpMethod.equals(HttpMethod.DELETE))) {

			_cache.clear();
		}

		if (bytes.length > 0) {
			return clientHttpRequestExecution.execute(httpRequest, bytes);
		}

		String key = getKey(httpRequest);

		FaroClientHttpResponse faroClientHttpResponse =
			getFaroClientHttpResponse(key);

		if (faroClientHttpResponse != null) {
			return faroClientHttpResponse;
		}

		ClientHttpResponse clientHttpResponse =
			clientHttpRequestExecution.execute(httpRequest, bytes);

		if (clientHttpResponse.getRawStatusCode() != HttpStatus.SC_OK) {
			return clientHttpResponse;
		}

		try {
			faroClientHttpResponse = new FaroClientHttpResponse(
				clientHttpResponse);

			_cache.put(key, faroClientHttpResponse);

			return faroClientHttpResponse;
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	protected FaroClientHttpResponse getFaroClientHttpResponse(String key) {
		Cache.ValueWrapper valueWrapper = _cache.get(key);

		if (valueWrapper == null) {
			return null;
		}

		return (FaroClientHttpResponse)valueWrapper.get();
	}

	protected String getKey(HttpRequest httpRequest) {
		StringBundler sb = new StringBundler(5);

		HttpMethod httpMethod = httpRequest.getMethod();

		if (httpMethod != null) {
			sb.append(httpMethod.name());
		}

		sb.append(StringPool.COLON);
		sb.append(httpRequest.getURI());
		sb.append(StringPool.COLON);
		sb.append(getProjectId(httpRequest));

		return sb.toString();
	}

	protected String getProjectId(HttpRequest httpRequest) {
		HttpHeaders httpHeaders = httpRequest.getHeaders();

		List<String> headers = httpHeaders.getOrEmpty(_ASAH_PROJECT_ID_HEADER);

		if (headers.isEmpty()) {
			return StringPool.BLANK;
		}

		return headers.get(0);
	}

	private static final String _ASAH_PROJECT_ID_HEADER = "OSB-Asah-Project-ID";

	private final Cache _cache;

}