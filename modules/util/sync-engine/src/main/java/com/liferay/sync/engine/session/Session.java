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
import com.liferay.sync.engine.util.OSDetector;
import com.liferay.sync.engine.util.PropsValues;
import com.liferay.sync.engine.util.ReleaseInfo;

import java.io.IOException;
import java.io.OutputStream;

import java.net.ProxySelector;
import java.net.URL;

import java.nio.charset.Charset;
import java.nio.file.Path;

import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.commons.io.output.CountingOutputStream;
import org.apache.http.Header;
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
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicHeader;
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

	public static HttpClientBuilder createHttpClientBuilder(
		boolean trustSelfSigned, int maxConnections, int connectionTimeout,
		int socketTimeout) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.disableAutomaticRetries();

		RequestConfig.Builder builder = RequestConfig.custom();

		builder.setConnectTimeout(connectionTimeout);
		builder.setSocketTimeout(socketTimeout);

		List<Header> headers = new ArrayList<>(2);

		Header syncBuildHeader = new BasicHeader(
			"Sync-Build", String.valueOf(ReleaseInfo.getBuildNumber()));

		headers.add(syncBuildHeader);

		String syncDevice = null;

		if (OSDetector.isApple()) {
			syncDevice = "desktop-mac";
		}
		else if (OSDetector.isLinux()) {
			syncDevice = "desktop-linux";
		}
		else if (OSDetector.isWindows()) {
			syncDevice = "desktop-windows";
		}

		Header syncDeviceHeader = new BasicHeader("Sync-Device", syncDevice);

		headers.add(syncDeviceHeader);

		httpClientBuilder.setDefaultHeaders(headers);

		httpClientBuilder.setDefaultRequestConfig(builder.build());
		httpClientBuilder.setMaxConnPerRoute(maxConnections);
		httpClientBuilder.setMaxConnTotal(maxConnections);
		httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
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

		return httpClientBuilder;
	}

	public static HttpClient getAnonymousHttpClient() {
		if (_anonymousHttpClient != null) {
			return _anonymousHttpClient;
		}

		HttpClientBuilder httpClientBuilder = createHttpClientBuilder(
			true, 1000, PropsValues.SYNC_HTTP_CONNECTION_TIMEOUT,
			PropsValues.SYNC_HTTP_CONNECTION_TIMEOUT * 2);

		_anonymousHttpClient = httpClientBuilder.build();

		return _anonymousHttpClient;
	}

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

		HttpClientBuilder httpClientBuilder = createHttpClientBuilder(
			trustSelfSigned, maxConnections,
			PropsValues.SYNC_HTTP_CONNECTION_TIMEOUT,
			PropsValues.SYNC_HTTP_SOCKET_TIMEOUT);

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		_httpHost = new HttpHost(
			url.getHost(), url.getPort(), url.getProtocol());

		credentialsProvider.setCredentials(
			new AuthScope(_httpHost),
			new UsernamePasswordCredentials(login, password));

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

		_httpClient = httpClientBuilder.build();

		_oAuthEnabled = false;
	}

	public Session(
		URL url, String oAuthConsumerKey, String oAuthConsumerSecret,
		String oAuthToken, String oAuthTokenSecret, boolean trustSelfSigned,
		int maxConnections) {

		_executorService = Executors.newFixedThreadPool(maxConnections);

		HttpClientBuilder httpClientBuilder = createHttpClientBuilder(
			trustSelfSigned, maxConnections,
			PropsValues.SYNC_HTTP_CONNECTION_TIMEOUT,
			PropsValues.SYNC_HTTP_SOCKET_TIMEOUT);

		_httpClient = httpClientBuilder.build();

		_httpHost = new HttpHost(
			url.getHost(), url.getPort(), url.getProtocol());

		_oAuthConsumer = new CommonsHttpOAuthConsumer(
			oAuthConsumerKey, oAuthConsumerSecret);

		_oAuthConsumer.setTokenWithSecret(oAuthToken, oAuthTokenSecret);

		_oAuthEnabled = true;
	}

	public void asynchronousExecute(
			final HttpGet httpGet, final Handler<Void> handler)
		throws Exception {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					execute(httpGet, handler);
				}
				catch (Exception e) {
					handler.handleException(e);
				}
			}

		};

		_executorService.execute(runnable);
	}

	public void asynchronousExecute(
			final HttpPost httpPost, final Map<String, Object> parameters,
			final Handler<Void> handler)
		throws Exception {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					execute(httpPost, parameters, handler);
				}
				catch (Exception e) {
					handler.handleException(e);
				}
			}

		};

		_executorService.execute(runnable);
	}

	public HttpResponse execute(
			HttpPost httpPost, Map<String, Object> parameters)
		throws Exception {

		_buildHttpPostBody(httpPost, parameters);

		_prepareHttpRequest(httpPost);

		return _httpClient.execute(_httpHost, httpPost, _getBasicHttpContext());
	}

	public <T> T execute(
			HttpPost httpPost, Map<String, Object> parameters,
			Handler<? extends T> handler)
		throws Exception {

		_buildHttpPostBody(httpPost, parameters);

		_prepareHttpRequest(httpPost);

		return _httpClient.execute(
			_httpHost, httpPost, handler, _getBasicHttpContext());
	}

	public HttpResponse execute(HttpRequest httpRequest) throws Exception {
		return execute(httpRequest, _getBasicHttpContext());
	}

	public <T> T execute(HttpRequest httpRequest, Handler<? extends T> handler)
		throws Exception {

		return execute(httpRequest, handler, _getBasicHttpContext());
	}

	public <T> T execute(
			HttpRequest httpRequest, Handler<? extends T> handler,
			HttpContext httpContext)
		throws Exception {

		_prepareHttpRequest(httpRequest);

		return _httpClient.execute(
			_httpHost, httpRequest, handler, httpContext);
	}

	public HttpResponse execute(
			HttpRequest httpRequest, HttpContext httpContext)
		throws Exception {

		_prepareHttpRequest(httpRequest);

		return _httpClient.execute(_httpHost, httpRequest, httpContext);
	}

	public int getDownloadRate() {
		return _downloadRate;
	}

	public ExecutorService getExecutorService() {
		return _executorService;
	}

	public HttpClient getHttpClient() {
		return _httpClient;
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

	public void startTrackTransferRate() {
		if ((_trackTransferRateScheduledFuture != null) &&
			!_trackTransferRateScheduledFuture.isDone()) {

			return;
		}

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				_downloadRate = _downloadedBytes.get();

				_downloadedBytes.set(0);

				_uploadRate = _uploadedBytes.get();

				_uploadedBytes.set(0);
			}

		};

		_trackTransferRateScheduledFuture =
			_scheduledExecutorService.scheduleWithFixedDelay(
				runnable, 0, 1, TimeUnit.SECONDS);
	}

	public void stopTrackTransferRate() {
		if (_trackTransferRateScheduledFuture == null) {
			return;
		}

		_trackTransferRateScheduledFuture.cancel(false);
	}

	private static SSLConnectionSocketFactory _getTrustingSSLSocketFactory()
		throws Exception {

		SSLContextBuilder sslContextBuilder = SSLContexts.custom();

		sslContextBuilder.loadTrustMaterial(
			new TrustStrategy() {

				@Override
				public boolean isTrusted(
					X509Certificate[] x509Certificates, String authType) {

					return true;
				}

			});

		return new SSLConnectionSocketFactory(
			sslContextBuilder.build(), new NoopHostnameVerifier());
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

	private BasicHttpContext _getBasicHttpContext() {
		if (_basicHttpContext != null) {
			return _basicHttpContext;
		}

		_basicHttpContext = new BasicHttpContext();

		_basicHttpContext.setAttribute(
			HttpClientContext.AUTH_CACHE, _getBasicAuthCache());

		return _basicHttpContext;
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

	private void _prepareHttpRequest(HttpRequest httpRequest) throws Exception {
		if (_oAuthEnabled) {
			_oAuthConsumer.sign(httpRequest);
		}
		else {
			httpRequest.setHeader("Sync-JWT", _token);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		Session.class);

	private static HttpClient _anonymousHttpClient;
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
	private OAuthConsumer _oAuthConsumer;
	private final boolean _oAuthEnabled;
	private String _token;
	private ScheduledFuture<?> _trackTransferRateScheduledFuture;
	private final AtomicInteger _uploadedBytes = new AtomicInteger(0);
	private volatile int _uploadRate;

}