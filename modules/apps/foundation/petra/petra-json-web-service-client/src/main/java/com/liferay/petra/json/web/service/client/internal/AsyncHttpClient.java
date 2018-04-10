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

import com.google.common.util.concurrent.SettableFuture;

import java.io.Closeable;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;

/**
 * @author Ivica Cardic
 */
public class AsyncHttpClient implements Closeable {

	public AsyncHttpClient(
		int maxRetryCount, CloseableHttpAsyncClient closeableHttpAsyncClient) {

		_maxRetryCount = maxRetryCount;
		_closeableHttpAsyncClient = closeableHttpAsyncClient;

		if (_maxRetryCount > 0) {
			executorService = Executors.newCachedThreadPool();
		}
	}

	@Override
	public void close() throws IOException {
		_closeableHttpAsyncClient.close();

		if (_maxRetryCount > 0) {
			executorService.shutdown();

			try {
				if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
					executorService.shutdownNow();
				}
			}
			catch (InterruptedException ie) {
				executorService.shutdownNow();
			}
		}
	}

	public Future<HttpResponse> execute(
		HttpHost httpHost, HttpRequest httpRequest) {

		return execute(httpHost, httpRequest, HttpClientContext.create());
	}

	public Future<HttpResponse> execute(
		HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {

		if (_maxRetryCount > 0) {
			SettableFuture<HttpResponse> responseFuture =
				SettableFuture.create();

			executorService.submit(
				new ExecuteTask(
					httpHost, httpRequest, httpContext, responseFuture,
					_closeableHttpAsyncClient, executorService,
					_maxRetryCount));

			return responseFuture;
		}
		else {
			return _closeableHttpAsyncClient.execute(
				httpHost, httpRequest, httpContext, null);
		}
	}

	protected ExecutorService executorService;

	private final CloseableHttpAsyncClient _closeableHttpAsyncClient;
	private final int _maxRetryCount;

}