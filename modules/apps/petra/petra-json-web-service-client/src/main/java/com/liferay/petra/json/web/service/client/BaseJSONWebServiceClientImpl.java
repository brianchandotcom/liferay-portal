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

package com.liferay.petra.json.web.service.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.liferay.petra.json.web.service.client.internal.AsyncHttpClient;
import com.liferay.petra.json.web.service.client.internal.IdleConnectionMonitorThread;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import com.liferay.petra.json.web.service.client.internal.X509TrustManagerImpl;

import java.io.IOException;

import java.nio.charset.Charset;

import java.security.KeyStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
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
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
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
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Igor Beslic
 */
public abstract class BaseJSONWebServiceClientImpl
	implements JSONWebServiceClient {

	public void afterPropertiesSet() throws IOReactorException {
		if (_classLoader != null) {
			TypeFactory typeFactory = TypeFactory.defaultInstance();

			_objectMapper.setTypeFactory(
				typeFactory.withClassLoader(_classLoader));
		}

		HttpAsyncClientBuilder httpAsyncClientBuilder =
			HttpAsyncClients.custom();

		httpAsyncClientBuilder = httpAsyncClientBuilder.useSystemProperties();

		NHttpClientConnectionManager nHttpClientConnectionManager =
			getPoolingNHttpClientConnectionManager();

		httpAsyncClientBuilder.setConnectionManager(
			nHttpClientConnectionManager);

		httpAsyncClientBuilder.setDefaultCredentialsProvider(
			_getCredentialsProvider());

		setProxyHost(httpAsyncClientBuilder);

		try {
			CloseableHttpAsyncClient closeableHttpAsyncClient =
				httpAsyncClientBuilder.build();

			closeableHttpAsyncClient.start();

			_asyncHttpClient = new AsyncHttpClient(
				closeableHttpAsyncClient, _maxAttempts);

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
		if (_asyncHttpClient != null) {
			try {
				_asyncHttpClient.close();
			}
			catch (IOException ioe) {
				_logger.error("Unable to close client", ioe);
			}

			_asyncHttpClient = null;
		}

		if (_idleConnectionMonitorThread != null) {
			_idleConnectionMonitorThread.shutdown();
		}
	}

	@Override
	public String doDelete(String url, List<NameValuePair> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doDelete(
			url, parameters, Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doDelete(
			String url, List<NameValuePair> parameters,
			List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		if (!parameters.isEmpty()) {
			String queryString = URLEncodedUtils.format(parameters, _CHARSET);

			url += "?" + queryString;
		}

		log("DELETE", url, parameters, headers);

		HttpDelete httpDelete = new HttpDelete(url);

		addHeaders(httpDelete, headers);

		return execute(httpDelete);
	}

	@Override
	public String doDelete(String url, Map<String, String> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doDelete(
			url, toNameValuePairs(parameters),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doDelete(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doDelete(
			url, toNameValuePairs(parameters), toNameValuePairs(headers));
	}

	@Override
	public String doDelete(String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		Map<String, String> parameters = new HashMap<String, String>();

		for (int i = 0; i < parametersArray.length; i += 2) {
			parameters.put(parametersArray[i], parametersArray[i + 1]);
		}

		return doDelete(
			url, _toNameValuePairs(parametersArray),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doGet(String url, List<NameValuePair> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doGet(url, parameters, Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doGet(
			String url, List<NameValuePair> parameters,
			List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		if (!parameters.isEmpty()) {
			String queryString = URLEncodedUtils.format(parameters, _CHARSET);

			url += "?" + queryString;
		}

		log("GET", url, parameters, headers);

		HttpGet httpGet = new HttpGet(url);

		addHeaders(httpGet, headers);

		return execute(httpGet);
	}

	@Override
	public String doGet(String url, Map<String, String> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doGet(url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doGet(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doGet(
			url, toNameValuePairs(parameters), toNameValuePairs(headers));
	}

	@Override
	public String doGet(String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doGet(
			url, _toNameValuePairs(parametersArray),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public <V, T> List<V> doGetToList(
			Class<T> clazz, String url, List<NameValuePair> parameters,
			List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		String json = doGet(url, parameters, headers);

		if (json == null) {
			return Collections.emptyList();
		}

		try {
			TypeFactory typeFactory = _objectMapper.getTypeFactory();

			List<V> list = new ArrayList<V>();

			JavaType javaType = typeFactory.constructCollectionType(
				list.getClass(), clazz);

			return _objectMapper.readValue(json, javaType);
		}
		catch (IOException ioe) {
			throw _getJSONWebServiceSerializeException(json, clazz);
		}
	}

	@Override
	public <V, T> List<V> doGetToList(
			Class<T> clazz, String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return doGetToList(
			clazz, url, toNameValuePairs(parameters),
			toNameValuePairs(headers));
	}

	@Override
	public <V, T> List<V> doGetToList(
			Class<T> clazz, String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return doGetToList(
			clazz, url, _toNameValuePairs(parametersArray),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public <T> T doGetToObject(
			Class<T> clazz, String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		String json = doGet(url, _toNameValuePairs(parametersArray));

		if (json == null) {
			return null;
		}

		try {
			return _objectMapper.readValue(json, clazz);
		}
		catch (IOException ioe) {
			throw _getJSONWebServiceSerializeException(json, clazz);
		}
	}

	@Override
	public String doPost(String url, List<NameValuePair> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPost(url, parameters, Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doPost(
			String url, List<NameValuePair> parameters,
			List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		log("POST", url, parameters, headers);

		HttpPost httpPost = new HttpPost(url);

		HttpEntity httpEntity = new UrlEncodedFormEntity(parameters, _CHARSET);

		addHeaders(httpPost, headers);

		httpPost.setEntity(httpEntity);

		return execute(httpPost);
	}

	@Override
	public String doPost(String url, Map<String, String> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPost(
			url, toNameValuePairs(parameters),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doPost(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPost(
			url, toNameValuePairs(parameters), toNameValuePairs(headers));
	}

	@Override
	public String doPost(String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPost(
			url, _toNameValuePairs(parametersArray),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doPostAsJSON(String url, Object object)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		try {
			String json = _objectMapper.writeValueAsString(object);

			return doPostAsJSON(url, json);
		}
		catch (IOException ioe) {
			throw _getJSONWebServiceSerializeException(object);
		}
	}

	@Override
	public String doPostAsJSON(String url, String json)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPostAsJSON(url, json, Collections.<String, String>emptyMap());
	}

	@Override
	public String doPostAsJSON(
			String url, String json, List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		HttpPost httpPost = new HttpPost(url);

		addHeaders(httpPost, headers);

		StringEntity stringEntity = new StringEntity(json, _CHARSET);

		stringEntity.setContentType("application/json");

		httpPost.setEntity(stringEntity);

		return execute(httpPost);
	}

	@Override
	public String doPostAsJSON(
			String url, String json, Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPostAsJSON(url, json, toNameValuePairs(headers));
	}

	@Override
	public <T> T doPostToObject(
			Class<T> clazz, String url, List<NameValuePair> parameters,
			List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		String json = doPost(url, parameters, headers);

		if (json == null) {
			return null;
		}

		try {
			return _objectMapper.readValue(json, clazz);
		}
		catch (IOException ioe) {
			throw _getJSONWebServiceSerializeException(json, clazz);
		}
	}

	@Override
	public <T> T doPostToObject(
			Class<T> clazz, String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return doPostToObject(
			clazz, url, toNameValuePairs(parameters),
			toNameValuePairs(headers));
	}

	@Override
	public <T> T doPostToObject(
			Class<T> clazz, String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		String json = doPost(url, parametersArray);

		if (json == null) {
			return null;
		}

		try {
			return _objectMapper.readValue(json, clazz);
		}
		catch (IOException ioe) {
			throw _getJSONWebServiceSerializeException(json, clazz);
		}
	}

	@Override
	public String doPut(String url, List<NameValuePair> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPut(url, parameters, Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doPut(
			String url, List<NameValuePair> parameters,
			List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		log("PUT", url, parameters, headers);

		HttpPut httpPut = new HttpPut(url);

		HttpEntity httpEntity = new UrlEncodedFormEntity(parameters, _CHARSET);

		addHeaders(httpPut, headers);

		httpPut.setEntity(httpEntity);

		return execute(httpPut);
	}

	@Override
	public String doPut(String url, Map<String, String> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPut(
			url, toNameValuePairs(parameters),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public String doPut(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPut(
			url, toNameValuePairs(parameters), toNameValuePairs(headers));
	}

	@Override
	public String doPut(String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return doPut(
			url, _toNameValuePairs(parametersArray),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public <T> T doPutToObject(
			Class<T> clazz, String url, List<NameValuePair> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return doPutToObject(
			clazz, url, parameters, Collections.<NameValuePair>emptyList());
	}

	@Override
	public <T> T doPutToObject(
			Class<T> clazz, String url, List<NameValuePair> parameters,
			List<NameValuePair> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		String json = doPut(url, parameters, headers);

		if (json == null) {
			return null;
		}

		try {
			return _objectMapper.readValue(json, clazz);
		}
		catch (IOException ioe) {
			throw _getJSONWebServiceSerializeException(json, clazz);
		}
	}

	@Override
	public <T> T doPutToObject(
			Class<T> clazz, String url, Map<String, String> parameters)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return doPutToObject(
			clazz, url, toNameValuePairs(parameters),
			Collections.<NameValuePair>emptyList());
	}

	@Override
	public <T> T doPutToObject(
			Class<T> clazz, String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		String json = doPut(url, parameters, headers);

		if (json == null) {
			return null;
		}

		try {
			return _objectMapper.readValue(json, clazz);
		}
		catch (IOException ioe) {
			throw _getJSONWebServiceSerializeException(json, clazz);
		}
	}

	@Override
	public <T> T doPutToObject(
			Class<T> clazz, String url, String... parametersArray)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return doPutToObject(
			clazz, url, _toNameValuePairs(parametersArray),
			Collections.<NameValuePair>emptyList());
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
	public void registerModule(Module module) {
		_objectMapper.registerModule(module);
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

	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
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
	public void setMaxAttempts(int maxAttempts) {
		_maxAttempts = maxAttempts;
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

	protected BaseJSONWebServiceClientImpl() {
		_objectMapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		_objectMapper.enableDefaultTypingAsProperty(
			ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, "class");
	}

	protected void addHeaders(
		HttpMessage httpMessage, List<NameValuePair> headers) {

		for (NameValuePair nameValuePair : headers) {
			httpMessage.addHeader(
				nameValuePair.getName(), nameValuePair.getValue());
		}

		for (Map.Entry<String, String> entry : _headers.entrySet()) {
			httpMessage.addHeader(entry.getKey(), entry.getValue());
		}
	}

	protected String execute(HttpRequestBase httpRequestBase)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		signRequest(httpRequestBase);

		HttpHost httpHost = new HttpHost(_hostName, _hostPort, _protocol);

		try {
			if (_asyncHttpClient == null) {
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

				future = _asyncHttpClient.execute(
					httpHost, httpRequestBase, httpClientContext);
			}
			else {
				future = _asyncHttpClient.execute(httpHost, httpRequestBase);
			}

			HttpResponse httpResponse = future.get();

			StatusLine statusLine = httpResponse.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if (_logger.isTraceEnabled()) {
				_logger.trace("Server returned status " + statusCode);
			}

			HttpEntity httpEntity = httpResponse.getEntity();

			if ((statusCode == HttpServletResponse.SC_NO_CONTENT) ||
				(((httpEntity == null) ||
				  (httpEntity.getContentLength() == 0)) &&
				 _isStatus2XX(statusCode))) {

				return null;
			}

			String content = EntityUtils.toString(httpEntity, _CHARSET);

			if ((httpEntity.getContentType() != null) &&
				_isApplicationJSONContentType(httpEntity)) {

				content = updateJSON(content);
			}

			if (_isStatus2XX(statusCode)) {
				return content;
			}
			else if ((statusCode == HttpServletResponse.SC_BAD_REQUEST) ||
					 (statusCode == HttpServletResponse.SC_FORBIDDEN) ||
					 (statusCode ==
						 HttpServletResponse.SC_METHOD_NOT_ALLOWED) ||
					 (statusCode == HttpServletResponse.SC_NOT_ACCEPTABLE) ||
					 (statusCode == HttpServletResponse.SC_NOT_FOUND)) {

				throw new JSONWebServiceInvocationException(
					content, statusCode);
			}
			else if (statusCode == HttpServletResponse.SC_UNAUTHORIZED) {
				throw new JSONWebServiceTransportException.
					AuthenticationFailure(
						"Not authorized to access JSON web service " + content);
			}

			StringBuilder sb = new StringBuilder();

			sb.append("Server returned status ");
			sb.append(statusCode);
			sb.append(" and content ");
			sb.append(content);

			throw new JSONWebServiceTransportException.CommunicationFailure(
				sb.toString(), statusCode);
		}
		catch (ExecutionException ee) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to transmit request to " + _hostName, ee);
		}
		catch (InterruptedException ie) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to transmit request to " + _hostName, ie);
		}
		catch (IOException ioe) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to transmit request to " + _hostName, ioe);
		}
		finally {
			httpRequestBase.releaseConnection();
		}
	}

	protected String getExceptionMessage(String json) {
		int exceptionMessageStart = json.indexOf("exception\":\"") + 12;

		int exceptionMessageEnd = json.indexOf("\"", exceptionMessageStart);

		return json.substring(exceptionMessageStart, exceptionMessageEnd);
	}

	protected PoolingNHttpClientConnectionManager
			getPoolingNHttpClientConnectionManager()
		throws IOReactorException {

		PoolingNHttpClientConnectionManager
			poolingNHttpClientConnectionManager =
				new PoolingNHttpClientConnectionManager(
					new DefaultConnectingIOReactor(), null,
					getSchemeIOSessionStrategyRegistry(), null, null, 60000,
					TimeUnit.MILLISECONDS);

		poolingNHttpClientConnectionManager.setMaxTotal(20);

		return poolingNHttpClientConnectionManager;
	}

	protected Registry<SchemeIOSessionStrategy>
		getSchemeIOSessionStrategyRegistry() {

		RegistryBuilder<SchemeIOSessionStrategy> registryBuilder =
			RegistryBuilder.<SchemeIOSessionStrategy>create();

		registryBuilder.register("http", NoopIOSessionStrategy.INSTANCE);

		if (_keyStore == null) {
			registryBuilder.register(
				"https", SSLIOSessionStrategy.getSystemDefaultStrategy());
		}
		else {
			registryBuilder.register("https", getSSLIOSessionStrategy());
		}

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

		String[] httpProtocols = _split(System.getProperty("https.protocols"));

		String[] cipherSuites = _split(
			System.getProperty("https.cipherSuites"));

		return new SSLIOSessionStrategy(
			sslContext, httpProtocols, cipherSuites,
			SSLIOSessionStrategy.getDefaultHostnameVerifier());
	}

	protected int getStatus(String json) {
		Matcher statusMatcher = _statusPattern.matcher(json);

		if (!statusMatcher.find()) {
			return 0;
		}

		return Integer.parseInt(statusMatcher.group(1));
	}

	protected boolean isNull(String s) {
		if ((s == null) || s.equals("")) {
			return true;
		}

		return false;
	}

	protected void log(
		String httpCommand, String url, List<NameValuePair> parameters,
		List<NameValuePair> headers) {

		if (!_logger.isTraceEnabled()) {
			return;
		}

		StringBuilder sb = new StringBuilder(
			12 + (headers.size() * 4) + (parameters.size() * 4) + 2);

		sb.append("Sending ");
		sb.append(httpCommand);
		sb.append(" request to ");
		sb.append(_login);
		sb.append("@");
		sb.append(_hostName);
		sb.append(url);
		sb.append("\n");
		sb.append("HTTP Headers: ");

		_update(sb, headers);

		sb.append("\n");
		sb.append("HTTP parameters: ");

		_update(sb, parameters);

		sb.append("\n");

		_logger.trace(sb.toString());
	}

	protected void setProxyHost(HttpAsyncClientBuilder httpClientBuilder) {
		if ((_proxyHostName == null) || _proxyHostName.equals("")) {
			return;
		}

		httpClientBuilder.setProxy(
			new HttpHost(_proxyHostName, _proxyHostPort));
		httpClientBuilder.setProxyAuthenticationStrategy(
			new ProxyAuthenticationStrategy());
	}

	protected abstract void signRequest(HttpRequestBase httpRequestBase)
		throws JSONWebServiceTransportException.SigningFailure;

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

	protected String updateJSON(String json)
		throws JSONWebServiceInvocationException {

		if ((json == null) || json.equals("") || json.equals("{}") ||
			json.equals("[]")) {

			return null;
		}

		Matcher matcher = _errorMessagePattern.matcher(json);

		if (matcher.find()) {
			throw new JSONWebServiceInvocationException(
				json, Integer.parseInt(matcher.group(2)));
		}
		else if (json.contains("exception\":\"")) {
			throw new JSONWebServiceInvocationException(
				getExceptionMessage(json), getStatus(json));
		}

		return json;
	}

	private static boolean _isBlank(String s) {
		if (s == null) {
			return true;
		}

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	private static String[] _split(final String s) {
		if (_isBlank(s)) {
			return null;
		}

		return s.split(" *, *");
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

	private JSONWebServiceSerializeException
		_getJSONWebServiceSerializeException(Object object) {

		StringBuffer sb = new StringBuffer();

		sb.append("Unable to serialize object with type ");
		sb.append(object.getClass());

		return new JSONWebServiceSerializeException(sb.toString());
	}

	private <T> JSONWebServiceSerializeException
		_getJSONWebServiceSerializeException(String json, Class<T> clazz) {

		StringBuffer sb = new StringBuffer();

		sb.append("Unable to deserialize ");
		sb.append(json);
		sb.append(" into object with type ");
		sb.append(clazz.getName());

		return new JSONWebServiceSerializeException(sb.toString());
	}

	private Credentials _getProxyCredentials() {
		if ("ntlm".equalsIgnoreCase(_proxyAuthType)) {
			return new NTCredentials(
				_proxyLogin, _proxyPassword, _proxyWorkstation, _proxyDomain);
		}

		return new UsernamePasswordCredentials(_proxyLogin, _proxyPassword);
	}

	private boolean _isApplicationJSONContentType(HttpEntity httpEntity) {
		Header contentTypeHeader = httpEntity.getContentType();

		String contentTypeHeaderValue = contentTypeHeader.getValue();

		if (contentTypeHeaderValue.contains("application/json")) {
			return true;
		}

		return false;
	}

	private boolean _isStatus2XX(int statusCode) {
		if ((statusCode == 200) || (statusCode == 201) || (statusCode == 202) ||
			(statusCode == 203) || (statusCode == 204)) {

			return true;
		}

		return false;
	}

	private List<NameValuePair> _toNameValuePairs(String... keyValuesArray) {
		if ((keyValuesArray == null) || (keyValuesArray.length == 0)) {
			return Collections.emptyList();
		}

		if ((keyValuesArray.length % 2) == 1) {
			throw new IllegalArgumentException(
				"Expected even number of variable arguments");
		}

		List<NameValuePair> nameValuePairs = new LinkedList<NameValuePair>();

		for (int i = 0; i < keyValuesArray.length; i += 2) {
			nameValuePairs.add(
				new BasicNameValuePair(
					keyValuesArray[i], keyValuesArray[i + 1]));
		}

		return nameValuePairs;
	}

	private void _update(
		StringBuilder stringBuilder, List<NameValuePair> nameValuePairs) {

		Iterator<NameValuePair> iterator = nameValuePairs.iterator();

		stringBuilder.append("{");

		while (iterator.hasNext()) {
			NameValuePair nameValuePair = iterator.next();

			stringBuilder.append(nameValuePair.getName());

			stringBuilder.append("=");

			stringBuilder.append(nameValuePair.getValue());

			if (iterator.hasNext()) {
				stringBuilder.append(",");
			}
		}

		stringBuilder.append("}");
	}

	private static final Charset _CHARSET = Charset.forName("UTF-8");

	private static final Logger _logger = LoggerFactory.getLogger(
		JSONWebServiceClientImpl.class);

	private static final Pattern _errorMessagePattern = Pattern.compile(
		"errorCode\":\\s*(\\d+).+message\":.+status\":\\s*(\\d+)");
	private static final Pattern _statusPattern = Pattern.compile(
		"status\":(\\d+)");

	private AsyncHttpClient _asyncHttpClient;
	private ClassLoader _classLoader;
	private String _contextPath;
	private Map<String, String> _headers = Collections.emptyMap();
	private String _hostName;
	private int _hostPort = 80;
	private IdleConnectionMonitorThread _idleConnectionMonitorThread;
	private KeyStore _keyStore;
	private String _login;
	private int _maxAttempts;
	private String _oAuthAccessSecret;
	private String _oAuthAccessToken;
	private String _oAuthConsumerKey;
	private String _oAuthConsumerSecret;
	private ObjectMapper _objectMapper = new ObjectMapper();
	private String _password;
	private String _protocol = "http";
	private String _proxyAuthType;
	private String _proxyDomain;
	private String _proxyHostName;
	private int _proxyHostPort;
	private String _proxyLogin;
	private String _proxyPassword;
	private String _proxyWorkstation;

}