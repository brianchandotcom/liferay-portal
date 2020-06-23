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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.util.ClassLoaderUtil;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.KeyStore;

import java.util.function.Consumer;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Michael C. Han
 */
public class ElasticsearchConnection {

	public void close() {
		try {
			if (_restHighLevelClient == null) {
				return;
			}

			try {
				_restHighLevelClient.close();
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			_restHighLevelClient = null;
		}
		finally {
			if (_postCloseRunnable != null) {
				_postCloseRunnable.run();
			}
		}
	}

	public void connect() {
		if (!_active) {
			if (_log.isWarnEnabled()) {
				_log.warn("Connecting inactive connection");
			}
		}

		if (_preConnectElasticsearchConnectionConsumer != null) {
			_preConnectElasticsearchConnectionConsumer.accept(this);
		}

		_restHighLevelClient = createRestHighLevelClient();
	}

	public String getConnectionId() {
		return _connectionId;
	}

	public RestHighLevelClient getRestHighLevelClient() {
		return _restHighLevelClient;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isConnected() {
		if (_restHighLevelClient != null) {
			return true;
		}

		return false;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setAuthenticationEnabled(boolean authenticationEnabled) {
		_authenticationEnabled = authenticationEnabled;
	}

	public void setConnectionId(String connectionId) {
		_connectionId = connectionId;
	}

	public void setHttpSSLEnabled(boolean httpSSLEnabled) {
		_httpSSLEnabled = httpSSLEnabled;
	}

	public void setNetworkHostAddresses(String[] networkHostAddresses) {
		_networkHostAddresses = networkHostAddresses;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setPostCloseRunnable(Runnable postCloseRunnable) {
		_postCloseRunnable = postCloseRunnable;
	}

	public void setPreConnectElasticsearchConnectionConsumer(
		Consumer<ElasticsearchConnection>
			preConnectElasticsearchConnectionConsumer) {

		_preConnectElasticsearchConnectionConsumer =
			preConnectElasticsearchConnectionConsumer;
	}

	public void setTruststorePassword(String truststorePassword) {
		_truststorePassword = truststorePassword;
	}

	public void setTruststorePath(String truststorePath) {
		_truststorePath = truststorePath;
	}

	public void setTruststoreType(String truststoreType) {
		_truststoreType = truststoreType;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	protected void configureSecurity(RestClientBuilder restClientBuilder) {
		restClientBuilder.setHttpClientConfigCallback(
			httpClientBuilder -> {
				if (_authenticationEnabled) {
					httpClientBuilder.setDefaultCredentialsProvider(
						createCredentialsProvider());
				}

				if (_httpSSLEnabled) {
					httpClientBuilder.setSSLContext(createSSLContext());
				}

				return httpClientBuilder;
			});
	}

	protected CredentialsProvider createCredentialsProvider() {
		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		credentialsProvider.setCredentials(
			AuthScope.ANY,
			new UsernamePasswordCredentials(_userName, _password));

		return credentialsProvider;
	}

	protected RestHighLevelClient createRestHighLevelClient() {
		HttpHost[] httpHosts = new HttpHost[_networkHostAddresses.length];

		for (int i = 0; i < _networkHostAddresses.length; i++) {
			httpHosts[i] = HttpHost.create(_networkHostAddresses[i]);
		}

		RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);

		configureSecurity(restClientBuilder);

		Class<? extends ElasticsearchConnection> clazz = getClass();

		return ClassLoaderUtil.getWithContextClassLoader(
			() -> new RestHighLevelClient(restClientBuilder), clazz);
	}

	protected SSLContext createSSLContext() {
		try {
			Path path = Paths.get(_truststorePath);

			InputStream is = Files.newInputStream(path);

			KeyStore keyStore = KeyStore.getInstance(_truststoreType);

			keyStore.load(is, _truststorePassword.toCharArray());

			SSLContextBuilder sslContextBuilder = SSLContexts.custom();

			sslContextBuilder.loadKeyMaterial(
				keyStore, _truststorePassword.toCharArray());
			sslContextBuilder.loadTrustMaterial(keyStore, null);

			return sslContextBuilder.build();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnection.class);

	private boolean _active;
	private boolean _authenticationEnabled;
	private String _connectionId;
	private boolean _httpSSLEnabled;
	private String[] _networkHostAddresses;
	private String _password;
	private Runnable _postCloseRunnable;
	private Consumer<ElasticsearchConnection>
		_preConnectElasticsearchConnectionConsumer;
	private RestHighLevelClient _restHighLevelClient;
	private String _truststorePassword;
	private String _truststorePath;
	private String _truststoreType;
	private String _userName;

}