/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.function.Consumer;

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

		_restHighLevelClient = _createRestHighLevelClient();
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

	public void setMaxConnections(int maxConnections) {
		_maxConnections = maxConnections;
	}

	public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
		_maxConnectionsPerRoute = maxConnectionsPerRoute;
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

	public void setProxyConfig(ProxyConfig proxyConfig) {
		_proxyConfig = proxyConfig;
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

	private RestHighLevelClient _createRestHighLevelClient() {
		return RestHighLevelClientFactory.builder(
		).authenticationEnabled(
			_authenticationEnabled
		).httpSSLEnabled(
			_httpSSLEnabled
		).maxConnections(
			_maxConnections
		).maxConnectionsPerRoute(
			_maxConnectionsPerRoute
		).networkHostAddresses(
			_networkHostAddresses
		).password(
			_password
		).truststorePassword(
			_truststorePassword
		).proxyConfig(
			_proxyConfig
		).truststorePath(
			_truststorePath
		).truststoreType(
			_truststoreType
		).userName(
			_userName
		).build(
		).newRestHighLevelClient();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnection.class);

	private boolean _active;
	private boolean _authenticationEnabled;
	private String _connectionId;
	private boolean _httpSSLEnabled;
	private int _maxConnections;
	private int _maxConnectionsPerRoute;
	private String[] _networkHostAddresses;
	private String _password;
	private Runnable _postCloseRunnable;
	private Consumer<ElasticsearchConnection>
		_preConnectElasticsearchConnectionConsumer;
	private ProxyConfig _proxyConfig;
	private RestHighLevelClient _restHighLevelClient;
	private String _truststorePassword;
	private String _truststorePath;
	private String _truststoreType;
	private String _userName;

}