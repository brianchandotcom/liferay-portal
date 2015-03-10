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

package com.liferay.sync.engine.session;

import com.btr.proxy.search.ProxySearch;

import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.util.PropsValues;

import java.io.IOException;
import java.io.OutputStream;

import java.net.ProxySelector;
import java.net.URL;

import java.nio.charset.Charset;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.output.CountingOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 * @author Dennis Ju
 */
public class Session {

	public static HttpRoutePlanner getHttpRoutePlanner() {
		if (_httpRoutePlanner != null) {
			return _httpRoutePlanner;
		}

		ProxySearch proxySearch = ProxySearch.getDefaultProxySearch();

		ProxySelector proxySelector = proxySearch.getProxySelector();

		if (proxySelector == null) {
			proxySelector = ProxySelector.getDefault();
		}

		_httpRoutePlanner = new SystemDefaultRoutePlanner(proxySelector);

		return _httpRoutePlanner;
	}

	public Session(
		URL url, String login, String password, boolean trustSelfSigned,
		int maxConnections) {

		_executorService = Executors.newFixedThreadPool(maxConnections);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.disableAutomaticRetries();

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		_httpHost = new HttpHost(
			url.getHost(), url.getPort(), url.getProtocol());

		credentialsProvider.setCredentials(
			new AuthScope(_httpHost),
			new UsernamePasswordCredentials(login, password));

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

		RequestConfig.Builder builder = RequestConfig.custom();

		builder.setConnectTimeout(PropsValues.SYNC_HTTP_CONNECTION_TIMEOUT);
		builder.setSocketTimeout(PropsValues.SYNC_HTTP_SOCKET_TIMEOUT);

		httpClientBuilder.setDefaultRequestConfig(builder.build());

		httpClientBuilder.setMaxConnPerRoute(maxConnections);
		httpClientBuilder.setMaxConnTotal(maxConnections);
		httpClientBuilder.setRoutePlanner(getHttpRoutePlanner());

		if (trustSelfSigned) {
			try {
				httpClientBuilder.setSSLSocketFactory(
					_getTrustingSSLSocketFactory());
			}
			catch (Exception e) {
				_logger.error(e.getMessage(), e);
			}
		}

		_httpClient = httpClientBuilder.build();

		_token = null;

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				_downloadRate = _downloadedBytes.get();

				_downloadedBytes.set(0);

				_uploadRate = _uploadedBytes.get();

				_uploadedBytes.set(0);
			}

		};

		_scheduledExecutorService.scheduleAtFixedRate(
			runnable, 0, 1000, TimeUnit.MILLISECONDS);
	}

	public HttpResponse execute(HttpRequest httpRequest) throws Exception {
		return execute(httpRequest, getBasicHttpContext());
	}

	public <T> T execute(HttpRequest httpRequest, Handler<? extends T> handler)
		throws Exception {

		return execute(httpRequest, handler, getBasicHttpContext());
	}

	public <T> T execute(
			HttpRequest httpRequest, Handler<? extends T> handler,
			HttpContext httpContext)
		throws Exception {

		httpRequest.setHeader("Sync-JWT", _token);

		return _httpClient.execute(
			_httpHost, httpRequest, handler, httpContext);
	}

	public HttpResponse execute(
			HttpRequest httpRequest, HttpContext httpContext)
		throws Exception {

		httpRequest.setHeader("Sync-JWT", _token);

		return _httpClient.execute(_httpHost, httpRequest, httpContext);
	}

	public void executeAsynchronousGet(
			final String urlPath, final Handler<Void> handler)
		throws Exception {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					executeGet(urlPath, handler);
				}
				catch (Exception e) {
					handler.handleException(e);
				}
			}

		};

		_executorService.execute(runnable);
	}

	public void executeAsynchronousPost(
			final String urlPath, final Map<String, Object> parameters,
			final Handler<Void> handler)
		throws Exception {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					executePost(urlPath, parameters, handler);
				}
				catch (Exception e) {
					handler.handleException(e);
				}
			}

		};

		_executorService.execute(runnable);
	}

	public HttpResponse executeGet(String urlPath) throws Exception {
		HttpGet httpGet = new HttpGet(urlPath);

		httpGet.setHeader("Sync-JWT", _token);

		return _httpClient.execute(_httpHost, httpGet, getBasicHttpContext());
	}

	public <T> T executeGet(String urlPath, Handler<? extends T> handler)
		throws Exception {

		HttpGet httpGet = new HttpGet(urlPath);

		httpGet.setHeader("Sync-JWT", _token);

		return _httpClient.execute(
			_httpHost, httpGet, handler, getBasicHttpContext());
	}

	public HttpResponse executePost(
			String urlPath, Map<String, Object> parameters)
		throws Exception {

		HttpPost httpPost = new HttpPost(urlPath);

		httpPost.setHeader("Sync-JWT", _token);

		_buildHttpPostBody(httpPost, parameters);

		return _httpClient.execute(_httpHost, httpPost, getBasicHttpContext());
	}

	public <T> T executePost(
			String urlPath, Map<String, Object> parameters,
			Handler<? extends T> handler)
		throws Exception {

		HttpPost httpPost = new HttpPost(urlPath);

		httpPost.setHeader("Sync-JWT", _token);

		_buildHttpPostBody(httpPost, parameters);

		return _httpClient.execute(
			_httpHost, httpPost, handler, getBasicHttpContext());
	}

	public BasicHttpContext getBasicHttpContext() {
		if (_basicHttpContext != null) {
			return _basicHttpContext;
		}

		_basicHttpContext = new BasicHttpContext();

		_basicHttpContext.setAttribute(
			HttpClientContext.AUTH_CACHE, _getBasicAuthCache());

		return _basicHttpContext;
	}

	public int getDownloadRate() {
		return _downloadRate;
	}

	public ExecutorService getExecutorService() {
		return _executorService;
	}

	public int getUploadRate() {
		return _uploadRate;
	}

	public void incrementDownloadedBytes(int bytes) {
		_downloadedBytes.getAndAdd(bytes);
	}

	public void incrementUploadedBytes(int bytes) {
		_uploadedBytes.getAndAdd(bytes);
	}

	public void setToken(String token) {
		_token = token;
	}

	private void _buildHttpPostBody(
			HttpPost httpPost, Map<String, Object> parameters)
		throws Exception {

		HttpEntity httpEntity = _getEntity(parameters);

		httpPost.setEntity(httpEntity);
	}

	private BasicAuthCache _getBasicAuthCache() {
		BasicAuthCache basicAuthCache = new BasicAuthCache();

		BasicScheme basicScheme = new BasicScheme();

		basicAuthCache.put(_httpHost, basicScheme);

		return basicAuthCache;
	}

	private HttpEntity _getEntity(Map<String, Object> parameters)
		throws Exception {

		Path deltaFilePath = (Path)parameters.get("deltaFilePath");
		Path filePath = (Path)parameters.get("filePath");
		String zipFileIds = (String)parameters.get("zipFileIds");
		Path zipFilePath = (Path)parameters.get("zipFilePath");

		MultipartEntityBuilder multipartEntityBuilder =
			_getMultipartEntityBuilder(parameters);

		if (deltaFilePath != null) {
			multipartEntityBuilder.addPart(
				"deltaFile",
				_getFileBody(
					deltaFilePath, (String)parameters.get("mimeType"),
					(String)parameters.get("title")));
		}
		else if (filePath != null) {
			multipartEntityBuilder.addPart(
				"file",
				_getFileBody(
					filePath, (String)parameters.get("mimeType"),
					(String)parameters.get("title")));
		}
		else if (zipFileIds != null) {
			return _getURLEncodedFormEntity(parameters);
		}
		else if (zipFilePath != null) {
			multipartEntityBuilder.addPart(
				"zipFile",
				_getFileBody(
					zipFilePath, "application/zip",
					String.valueOf(zipFilePath.getFileName())));
		}

		return multipartEntityBuilder.build();
	}

	private ContentBody _getFileBody(
			Path filePath, String mimeType, String fileName)
		throws Exception {

		return new FileBody(
			filePath.toFile(), ContentType.create(mimeType), fileName) {

			@Override
			public void writeTo(OutputStream out) throws IOException {
				CountingOutputStream output = new CountingOutputStream(out) {

					@Override
					protected void beforeWrite(int n) {
						incrementUploadedBytes(n);

						super.beforeWrite(n);
					}

				};

				super.writeTo(output);
			}

		};
	}

	private MultipartEntityBuilder _getMultipartEntityBuilder(
		Map<String, Object> parameters) {

		MultipartEntityBuilder multipartEntityBuilder =
			MultipartEntityBuilder.create();

		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			if (_ignoredParameterKeys.contains(entry.getKey())) {
				continue;
			}

			multipartEntityBuilder.addPart(
				entry.getKey(), _getStringBody(entry.getValue()));
		}

		return multipartEntityBuilder;
	}

	private StringBody _getStringBody(Object value) {
		return new StringBody(
			String.valueOf(value),
			ContentType.create(
				ContentType.TEXT_PLAIN.getMimeType(),
				Charset.forName("UTF-8")));
	}

	private SSLConnectionSocketFactory _getTrustingSSLSocketFactory()
		throws Exception {

		SSLContextBuilder sslContextBuilder = SSLContexts.custom();

		sslContextBuilder.loadTrustMaterial(
			null, new TrustSelfSignedStrategy());

		return new SSLConnectionSocketFactory(
			sslContextBuilder.build(), new NoopHostnameVerifier());
	}

	private HttpEntity _getURLEncodedFormEntity(Map<String, Object> parameters)
		throws Exception {

		List<NameValuePair> nameValuePairs = new ArrayList<>();

		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			NameValuePair nameValuePair = new BasicNameValuePair(
				entry.getKey(), String.valueOf(entry.getValue()));

			nameValuePairs.add(nameValuePair);
		}

		return new UrlEncodedFormEntity(nameValuePairs);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		Session.class);

	private static HttpRoutePlanner _httpRoutePlanner;
	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newSingleThreadScheduledExecutor();

	private BasicHttpContext _basicHttpContext;
	private final AtomicInteger _downloadedBytes = new AtomicInteger(0);
	private volatile int _downloadRate;
	private final ExecutorService _executorService;
	private final HttpClient _httpClient;
	private final HttpHost _httpHost;
	private final Set<String> _ignoredParameterKeys = new HashSet<>(
		Arrays.asList("filePath", "syncFile", "syncSite", "uiEvent"));
	private String _token;
	private final AtomicInteger _uploadedBytes = new AtomicInteger(0);
	private volatile int _uploadRate;

}