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

import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import java.io.IOException;

import java.nio.charset.Charset;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(factory = "JSONWebServiceClient")
public class JSONWebServiceClientImpl implements JSONWebServiceClient {

	@Activate
	public void activate(Map<String, Object> properties)
		throws IOReactorException {

		_setHeaders(String.valueOf(properties.get("headers")));

		setHostName(String.valueOf(properties.get("hostName")));
		setHostPort(
			Integer.parseInt(String.valueOf(properties.get("hostPort"))));
		setKeyStore((KeyStore)properties.get("keyStore"));
		setLogin(String.valueOf(properties.get("login")));
		setPassword(String.valueOf(properties.get("password")));
		setProtocol(String.valueOf(properties.get("protocol")));

		if (properties.containsKey("proxyAuthType")) {
			setProxyAuthType(String.valueOf(properties.get("proxyAuthType")));
			setProxyDomain(String.valueOf(properties.get("proxyDomain")));
			setProxyWorkstation(
				String.valueOf(properties.get("proxyWorkstation")));
		}

		if (properties.containsKey("proxyHostName")) {
			setProxyHostName(String.valueOf(properties.get("proxyHostName")));
			setProxyHostPort(
				Integer.parseInt(
					String.valueOf(properties.get("proxyHostPort"))));
			setProxyLogin(String.valueOf(properties.get("proxyLogin")));
			setProxyPassword(String.valueOf(properties.get("proxyPassword")));
		}

		afterPropertiesSet();
	}

	public void afterPropertiesSet() throws IOReactorException {
		HttpAsyncClientBuilder httpAsyncClientBuilder =
			HttpAsyncClients.custom();

		httpAsyncClientBuilder = httpAsyncClientBuilder.useSystemProperties();

		NHttpClientConnectionManager nHttpClientConnectionManager =
			getPoolingNHttpClientConnectionManager();

		httpAsyncClientBuilder.setConnectionManager(
			nHttpClientConnectionManager);

		httpAsyncClientBuilder.setDefaultCredentialsProvider(
			_getCredentialsProvider());
		httpAsyncClientBuilder.setDefaultRequestConfig(
			_getProxyRequestConfig());

		try {
			_closeableHttpAsyncClient = httpAsyncClientBuilder.build();

			_closeableHttpAsyncClient.start();

			_idleConnectionMonitorThread = new IdleConnectionMonitorThread(
				nHttpClientConnectionManager);

			_idleConnectionMonitorThread.start();

			if (_logger.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder();

				sb.append("Configured client for ");
				sb.append(_protocol);
				sb.append("://");
				sb.append(_hostName);
				sb.append(":");
				sb.append(_hostPort);

				_logger.debug(sb.toString());
			}
		}
		catch (Exception e) {
			_logger.error("Unable to configure client", e);
		}
	}

	@Override
	public void destroy() {
		try {
			_closeableHttpAsyncClient.close();
		}
		catch (IOException ioe) {
			_logger.error("Unable to close client", ioe);
		}

		_closeableHttpAsyncClient = null;

		_idleConnectionMonitorThread.shutdown();
	}

	@Override
	public String doDelete(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doDelete(
			url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doDelete(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		if (!nameValuePairs.isEmpty()) {
			String queryString = URLEncodedUtils.format(
				nameValuePairs, _CHARSET);

			url += "?" + queryString;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending DELETE request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpDelete httpDelete = new HttpDelete(url);

		addHeaders(httpDelete, headers);

		return execute(httpDelete);
	}

	@Override
	public String doGet(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doGet(url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doGet(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		if (!nameValuePairs.isEmpty()) {
			String queryString = URLEncodedUtils.format(
				nameValuePairs, _CHARSET);

			url += "?" + queryString;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending GET request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpGet httpGet = new HttpGet(url);

		addHeaders(httpGet, headers);

		return execute(httpGet);
	}

	@Override
	public String doPost(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doPost(url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doPost(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending POST request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		HttpEntity httpEntity = new UrlEncodedFormEntity(
			nameValuePairs, _CHARSET);

		addHeaders(httpPost, headers);

		httpPost.setEntity(httpEntity);

		return execute(httpPost);
	}

	@Override
	public String doPostAsJSON(String url, String json)
		throws JSONWebServiceTransportException {

		return doPostAsJSON(url, json, Collections.<String, String>emptyMap());
	}

	@Override
	public String doPostAsJSON(
			String url, String json, Map<String, String> headers)
		throws JSONWebServiceTransportException {

		HttpPost httpPost = new HttpPost(url);

		addHeaders(httpPost, headers);

		StringEntity stringEntity = new StringEntity(json.toString(), _CHARSET);

		stringEntity.setContentType("application/json");

		httpPost.setEntity(stringEntity);

		return execute(httpPost);
	}

	@Override
	public String doPut(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doPut(url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doPut(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending PUT request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpPut httpPut = new HttpPut(url);

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		HttpEntity httpEntity = new UrlEncodedFormEntity(
			nameValuePairs, _CHARSET);

		addHeaders(httpPut, headers);

		httpPut.setEntity(httpEntity);

		return execute(httpPut);
	}

	public Map<String, String> getHeaders() {
		return _headers;
	}

	@Override
	public String getHostName() {
		return _hostName;
	}

	@Override
	public int getHostPort() {
		return _hostPort;
	}

	@Override
	public String getProtocol() {
		return _protocol;
	}

	public String getProxyAuthType() {
		return _proxyAuthType;
	}

	public String getProxyDomain() {
		return _proxyDomain;
	}

	public String getProxyHostName() {
		return _proxyHostName;
	}

	public int getProxyHostPort() {
		return _proxyHostPort;
	}

	public String getProxyLogin() {
		return _proxyLogin;
	}

	public String getProxyPassword() {
		return _proxyPassword;
	}

	public String getProxyWorkstation() {
		return _proxyWorkstation;
	}

	@Override
	public void resetHttpClient() {
		destroy();

		try {
			afterPropertiesSet();
		}
		catch (IOReactorException iore) {
			_logger.error(iore.getMessage());
		}
	}

	public void setContextPath(String contextPath) {
		_contextPath = contextPath;
	}

	public void setHeaders(Map<String, String> headers) {
		_headers = headers;
	}

	@Override
	public void setHostName(String hostName) {
		_hostName = hostName;
	}

	@Override
	public void setHostPort(int hostPort) {
		_hostPort = hostPort;
	}

	@Override
	public void setKeyStore(KeyStore keyStore) {
		_keyStore = keyStore;
	}

	@Override
	public void setLogin(String login) {
		_login = login;
	}

	@Override
	public void setOAuthAccessSecret(String oAuthAccessSecret) {
		_oAuthAccessSecret = oAuthAccessSecret;
	}

	@Override
	public void setOAuthAccessToken(String oAuthAccessToken) {
		_oAuthAccessToken = oAuthAccessToken;
	}

	@Override
	public void setOAuthConsumerKey(String oAuthConsumerKey) {
		_oAuthConsumerKey = oAuthConsumerKey;
	}

	@Override
	public void setOAuthConsumerSecret(String oAuthConsumerSecret) {
		_oAuthConsumerSecret = oAuthConsumerSecret;
	}

	@Override
	public void setPassword(String password) {
		_password = password;
	}

	@Override
	public void setProtocol(String protocol) {
		_protocol = protocol;
	}

	public void setProxyAuthType(String proxyAuthType) {
		_proxyAuthType = proxyAuthType;
	}

	public void setProxyDomain(String proxyDomain) {
		_proxyDomain = proxyDomain;
	}

	public void setProxyHostName(String proxyHostName) {
		_proxyHostName = proxyHostName;
	}

	public void setProxyHostPort(int proxyHostPort) {
		_proxyHostPort = proxyHostPort;
	}

	public void setProxyLogin(String proxyLogin) {
		_proxyLogin = proxyLogin;
	}

	public void setProxyPassword(String proxyPassword) {
		_proxyPassword = proxyPassword;
	}

	public void setProxyWorkstation(String proxyWorkstation) {
		_proxyWorkstation = proxyWorkstation;
	}

	protected void addHeaders(
		HttpMessage httpMessage, Map<String, String> headers) {

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpMessage.addHeader(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, String> entry : _headers.entrySet()) {
			httpMessage.addHeader(entry.getKey(), entry.getValue());
		}
	}

	protected String execute(HttpRequestBase httpRequestBase)
		throws JSONWebServiceTransportException {

		HttpHost httpHost = new HttpHost(_hostName, _hostPort, _protocol);

		try {
			if (_closeableHttpAsyncClient == null) {
				afterPropertiesSet();
			}

			Future<HttpResponse> future = null;

			if (!isNull(_login) && !isNull(_password)) {
				HttpClientContext httpClientContext =
					HttpClientContext.create();

				AuthCache authCache = new BasicAuthCache();

				AuthScheme authScheme = null;

				if (!isNull(_proxyHostName)) {
					authScheme = new BasicScheme(ChallengeState.PROXY);
				}
				else {
					authScheme = new BasicScheme(ChallengeState.TARGET);
				}

				authCache.put(httpHost, authScheme);

				httpClientContext.setAttribute(
					ClientContext.AUTH_CACHE, authCache);

				future = _closeableHttpAsyncClient.execute(
					httpHost, httpRequestBase, httpClientContext, null);
			}
			else {
				future = _closeableHttpAsyncClient.execute(
					httpHost, httpRequestBase, null);
			}

			HttpResponse httpResponse = future.get();

			StatusLine statusLine = httpResponse.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if ((statusCode == HttpServletResponse.SC_BAD_REQUEST) ||
				(statusCode == HttpServletResponse.SC_FORBIDDEN) ||
				(statusCode == HttpServletResponse.SC_NOT_ACCEPTABLE) ||
				(statusCode == HttpServletResponse.SC_NOT_FOUND)) {

				if (httpResponse.getEntity() != null) {
					if (_logger.isDebugEnabled()) {
						_logger.debug("Server returned status " + statusCode);
					}

					return EntityUtils.toString(
						httpResponse.getEntity(), _CHARSET);
				}
			}
			else if ((statusCode == HttpServletResponse.SC_ACCEPTED) ||
					 (statusCode == HttpServletResponse.SC_OK)) {

				return EntityUtils.toString(httpResponse.getEntity(), _CHARSET);
			}
			else if (statusCode == HttpServletResponse.SC_NO_CONTENT) {
				return null;
			}
			else if (statusCode == HttpServletResponse.SC_UNAUTHORIZED) {
				throw new JSONWebServiceTransportException.
					AuthenticationFailure(
						"Not authorized to access JSON web service");
			}

			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Server returned status " + statusCode, statusCode);
		}
		catch (IOException ioe) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to transmit request", ioe);
		}
		catch (InterruptedException ie) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to transmit request", ie);
		}
		catch (ExecutionException ee) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to transmit request", ee);
		}
		finally {
			httpRequestBase.releaseConnection();
		}
	}

	protected PoolingNHttpClientConnectionManager
		getPoolingNHttpClientConnectionManager() throws IOReactorException {

		PoolingNHttpClientConnectionManager
			poolingNHttpClientConnectionManager = null;

		ConnectingIOReactor connectingIOReactor =
			new DefaultConnectingIOReactor();

		if (_keyStore != null) {
			poolingNHttpClientConnectionManager =
				new PoolingNHttpClientConnectionManager(
					connectingIOReactor, null,
					getSchemeIOSessionStrategyRegistry(), null, null, 60000,
					TimeUnit.MILLISECONDS);
		}
		else {
			poolingNHttpClientConnectionManager =
				new PoolingNHttpClientConnectionManager(connectingIOReactor);
		}

		poolingNHttpClientConnectionManager.setMaxTotal(20);

		return poolingNHttpClientConnectionManager;
	}

	protected Registry<SchemeIOSessionStrategy>
		getSchemeIOSessionStrategyRegistry() {

		RegistryBuilder<SchemeIOSessionStrategy> registryBuilder =
			RegistryBuilder.<SchemeIOSessionStrategy>create();

		registryBuilder.register("http", NoopIOSessionStrategy.INSTANCE);
		registryBuilder.register("https", getSSLIOSessionStrategy());

		return registryBuilder.build();
	}

	protected SSLIOSessionStrategy getSSLIOSessionStrategy() {
		SSLContextBuilder sslContextBuilder = SSLContexts.custom();

		SSLContext sslContext = null;

		try {
			sslContextBuilder.loadTrustMaterial(
				_keyStore, new TrustSelfSignedStrategy());

			sslContext = sslContextBuilder.build();

			sslContext.init(
				null, new TrustManager[] {new X509TrustManagerImpl()}, null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new SSLIOSessionStrategy(
			sslContext, new String[] {"TLSv1"}, null,
			SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	}

	protected boolean isNull(String s) {
		if ((s == null) || s.equals("")) {
			return true;
		}

		return false;
	}

	protected void log(String message, Map<String, String> map) {
		if (!_logger.isDebugEnabled() || map.isEmpty()) {
			return;
		}

		StringBuilder sb = new StringBuilder((map.size() * 4) + 2);

		sb.append(message);
		sb.append(":");

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value == null) {
				key = "-" + key;
				value = "";
			}

			sb.append("\n");
			sb.append(key);
			sb.append("=");
			sb.append(value);
		}

		_logger.debug(sb.toString());
	}

	protected void setProxyHost(HttpClientBuilder httpClientBuilder) {
		if ((_proxyHostName == null) || _proxyHostName.equals("")) {
			return;
		}

		httpClientBuilder.setProxy(
			new HttpHost(_proxyHostName, _proxyHostPort));
		httpClientBuilder.setProxyAuthenticationStrategy(
			new ProxyAuthenticationStrategy());
	}

	protected List<NameValuePair> toNameValuePairs(
		Map<String, String> parameters) {

		List<NameValuePair> nameValuePairs = new LinkedList<NameValuePair>();

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			String key = entry.getKey();

			String value = entry.getValue();

			if (value == null) {
				key = "-" + key;

				value = "";
			}

			NameValuePair nameValuePair = new BasicNameValuePair(key, value);

			nameValuePairs.add(nameValuePair);
		}

		return nameValuePairs;
	}

	private CredentialsProvider _getCredentialsProvider() {
		if ((isNull(_login) || isNull(_password)) &&
			(isNull(_proxyLogin) || isNull(_proxyPassword))) {

			return null;
		}

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		if (!isNull(_login)) {
			credentialsProvider.setCredentials(
				new AuthScope(_hostName, _hostPort),
				new UsernamePasswordCredentials(_login, _password));

			if (_logger.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder();

				sb.append("Basic credentials are used for ");
				sb.append(_hostName);
				sb.append(":");
				sb.append(_hostPort);

				_logger.debug(sb.toString());
			}
		}

		if (isNull(_proxyLogin)) {
			return credentialsProvider;
		}

		credentialsProvider.setCredentials(
			new AuthScope(_proxyHostName, _proxyHostPort),
			_getProxyCredentials());

		if (_logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();

			sb.append("Proxy credentials are used for ");
			sb.append(_hostName);
			sb.append(":");
			sb.append(_hostPort);

			_logger.debug(sb.toString());
		}

		return credentialsProvider;
	}

	private Credentials _getProxyCredentials() {
		if ("ntlm".equalsIgnoreCase(_proxyAuthType)) {
			return new NTCredentials(
				_proxyLogin, _proxyPassword, _proxyWorkstation, _proxyDomain);
		}

		return new UsernamePasswordCredentials(_proxyLogin, _proxyPassword);
	}

	private RequestConfig _getProxyRequestConfig() {
		if (isNull(_proxyLogin) || isNull(_proxyPassword)) {
			return null;
		}

		RequestConfig.Builder builder = RequestConfig.custom();

		builder.setProxy(
			new HttpHost(_proxyHostName, _proxyHostPort, _protocol));

		return builder.build();
	}

	private void _setHeaders(String headersString) {
		if (headersString == null) {
			return;
		}

		headersString = headersString.trim();

		if (headersString.length() < 3) {
			return;
		}

		Map<String, String> headers = new HashMap<String, String>();

		for (String header : headersString.split(";")) {
			String[] headerParts = header.split("=");

			if (headerParts.length != 2) {
				if (_logger.isDebugEnabled()) {
					_logger.debug("Ignoring invalid header " + header);
				}

				continue;
			}

			headers.put(headerParts[0], headerParts[1]);
		}

		setHeaders(headers);
	}

	private static final Charset _CHARSET = Charset.forName("UTF-8");

	private static final Logger _logger = LoggerFactory.getLogger(
		JSONWebServiceClientImpl.class);

	private CloseableHttpAsyncClient _closeableHttpAsyncClient;
	private String _contextPath;
	private Map<String, String> _headers = Collections.emptyMap();
	private String _hostName;
	private int _hostPort = 80;
	private IdleConnectionMonitorThread _idleConnectionMonitorThread;
	private KeyStore _keyStore;
	private String _login;
	private String _oAuthAccessSecret;
	private String _oAuthAccessToken;
	private String _oAuthConsumerKey;
	private String _oAuthConsumerSecret;
	private String _password;
	private String _protocol = "http";
	private String _proxyAuthType;
	private String _proxyDomain;
	private String _proxyHostName;
	private int _proxyHostPort;
	private String _proxyLogin;
	private String _proxyPassword;
	private String _proxyWorkstation;

	private class IdleConnectionMonitorThread extends Thread {

		public IdleConnectionMonitorThread(
			NHttpClientConnectionManager nHttpClientConnectionManager) {

			_nHttpClientConnectionManager = nHttpClientConnectionManager;
		}

		@Override
		public void run() {
			try {
				while (!_shutdown) {
					synchronized (this) {
						wait(5000);

						_nHttpClientConnectionManager.closeExpiredConnections();

						_nHttpClientConnectionManager.closeIdleConnections(
							30, TimeUnit.SECONDS);
					}
				}
			}
			catch (InterruptedException ie) {
			}
		}

		public void shutdown() {
			_shutdown = true;

			synchronized (this) {
				notifyAll();
			}
		}

		private final NHttpClientConnectionManager
			_nHttpClientConnectionManager;
		private volatile boolean _shutdown;

	}

	private class X509TrustManagerImpl implements X509TrustManager {

		public X509TrustManagerImpl() {
			try {
				X509TrustManager x509TrustManager = null;

				TrustManagerFactory trustManagerFactory =
					TrustManagerFactory.getInstance(
						TrustManagerFactory.getDefaultAlgorithm());

				trustManagerFactory.init((KeyStore)null);

				for (TrustManager trustManager :
						trustManagerFactory.getTrustManagers()) {

					if (trustManager instanceof X509TrustManager) {
						x509TrustManager = (X509TrustManager)trustManager;

						break;
					}
				}

				_x509TrustManager = x509TrustManager;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void checkClientTrusted(
				X509Certificate[] x509Certificates, String authType)
			throws CertificateException {

			if (x509Certificates.length != 1) {
				_x509TrustManager.checkClientTrusted(
					x509Certificates, authType);
			}
		}

		@Override
		public void checkServerTrusted(
				X509Certificate[] x509Certificates, String authType)
			throws CertificateException {

			if (x509Certificates.length != 1) {
				_x509TrustManager.checkServerTrusted(
					x509Certificates, authType);
			}
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return _x509TrustManager.getAcceptedIssuers();
		}

		private final X509TrustManager _x509TrustManager;

	}

}