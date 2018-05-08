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

package com.liferay.petra.json.web.service.client.internal;

import java.io.Closeable;
import java.io.IOException;

import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class AsyncHttpClient implements Closeable {

	public AsyncHttpClient(
		CloseableHttpAsyncClient closeableHttpAsyncClient, int maxAttempts) {

		_closeableHttpAsyncClient = closeableHttpAsyncClient;

		if (maxAttempts < 1) {
			_maxAttempts = 1;
		}
		else {
			_maxAttempts = maxAttempts;
		}
	}

	@Override
	public void close() throws IOException {
		_closeableHttpAsyncClient.close();
	}

	public Future<HttpResponse> execute(
		HttpHost httpHost, HttpRequest httpRequest) {

		return execute(httpHost, httpRequest, HttpClientContext.create());
	}

	public Future<HttpResponse> execute(
		final HttpHost httpHost, HttpRequest httpRequest,
		HttpContext httpContext) {

		for (int i = 1; i <= _maxAttempts; i++) {
			if ((_maxAttempts == 1) || (_maxAttempts == i)) {
				return _closeableHttpAsyncClient.execute(
					httpHost, httpRequest, httpContext, null);
			}

			try {
				Future<HttpResponse> httpResponseFuture =
					_closeableHttpAsyncClient.execute(
						httpHost, httpRequest, httpContext, null);

				httpResponseFuture.get();

				return httpResponseFuture;
			}
			catch (Exception e) {
				if (_logger.isTraceEnabled()) {
					_logger.trace(
						"Unable to execute HTTP request in attempt " + i, e);
				}

				try {
					Thread.sleep(100L);
				}
				catch (InterruptedException ie) {
					_logger.error("Interrupted", ie);

					if (_logger.isInfoEnabled()) {
						_logger.info(
							"Aborting executing after " + i + " attempts");
					}
				}
			}
		}

		throw new RuntimeException(
			"Unable to execute HTTP request after " + _maxAttempts +
				" attempts");
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		AsyncHttpClient.class);

	private final CloseableHttpAsyncClient _closeableHttpAsyncClient;
	private final int _maxAttempts;

}