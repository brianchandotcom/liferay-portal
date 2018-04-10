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

import java.util.concurrent.ExecutorService;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.protocol.HttpContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ivica Cardic
 */
public class ExecuteTask implements Runnable {

	public ExecuteTask(
		HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext,
		SettableFuture<HttpResponse> responseFuture,
		HttpAsyncClient httpAsyncClient, ExecutorService executorService,
		int maxRetryCount) {

		_httpHost = httpHost;
		_httpRequest = httpRequest;
		_httpContext = httpContext;
		_responseFuture = responseFuture;
		_httpAsyncClient = httpAsyncClient;
		_executorService = executorService;
		_maxRetryCount = maxRetryCount;
	}

	public void run() {
		try {
			_httpAsyncClient.execute(
				_httpHost, _httpRequest, _httpContext,
				new ExecuteFutureCallback());
		}
		catch (Exception e) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Passing exception to response", e);
			}

			_responseFuture.setException(e);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		AsyncHttpClient.class);

	private final ExecutorService _executorService;
	private final HttpAsyncClient _httpAsyncClient;
	private final HttpContext _httpContext;
	private final HttpHost _httpHost;
	private final HttpRequest _httpRequest;
	private final int _maxRetryCount;
	private final SettableFuture<HttpResponse> _responseFuture;
	private int _retryCount;

	private class ExecuteFutureCallback
		implements FutureCallback<HttpResponse> {

		@Override
		public void cancelled() {
			_responseFuture.set(null);
		}

		@Override
		public void completed(HttpResponse httpResponse) {
			try {
				_responseFuture.set(httpResponse);
			}
			catch (Exception e) {
				if (_logger.isDebugEnabled()) {
					_logger.debug("Passing exception to response", e);
				}

				_responseFuture.setException(e);
			}
		}

		@Override
		public void failed(Exception e) {
			if (_retryCount >= _maxRetryCount) {
				_responseFuture.setException(e);
			}
			else {
				if (_logger.isWarnEnabled()) {
					_logger.warn("Unable to process request, will retry again");
				}

				if (_logger.isDebugEnabled()) {
					_logger.debug(e.getMessage(), e);
				}

				_retryCount++;

				_executorService.submit(ExecuteTask.this);
			}
		}

	}

}