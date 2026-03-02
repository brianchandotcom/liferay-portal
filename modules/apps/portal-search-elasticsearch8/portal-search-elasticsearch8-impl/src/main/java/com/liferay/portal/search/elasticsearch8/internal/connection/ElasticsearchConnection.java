/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.connection;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.function.Supplier;

/**
 * @author Michael C. Han
 */
public class ElasticsearchConnection {

	public void close() {
		try {
			if (_elasticsearchClient == null) {
				return;
			}

			try {
				_elasticsearchClient.close();
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			_elasticsearchClient = null;
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

		if (_preConnectRunnable != null) {
			_preConnectRunnable.run();
		}

		_elasticsearchClient = _createElasticsearchClient();
	}

	public String getConnectionId() {
		return _connectionId;
	}

	public ElasticsearchClient getElasticsearchClient() {
		return _elasticsearchClient;
	}

	public JsonpMapper getJsonpMapper() {
		return _restClientTransport.jsonpMapper();
	}

	public RestClientTransport getRestClientTransport() {
		return _restClientTransport;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isConnected() {
		if (_elasticsearchClient != null) {
			return true;
		}

		return false;
	}

	public static class Builder {

		public Builder(Supplier<String[]> networkHostAddressesSupplier) {
			_networkHostAddressesSupplier = networkHostAddressesSupplier;
		}

		public Builder active(boolean active) {
			_active = active;

			return this;
		}

		public Builder authenticationEnabled(boolean authenticationEnabled) {
			_authenticationEnabled = authenticationEnabled;

			return this;
		}

		public ElasticsearchConnection build() {
			return new ElasticsearchConnection(this);
		}

		public Builder compressionEnabled(boolean compressionEnabled) {
			_compressionEnabled = compressionEnabled;

			return this;
		}

		public Builder connectionId(String connectionId) {
			_connectionId = connectionId;

			return this;
		}

		public Builder httpSSLEnabled(boolean httpSSLEnabled) {
			_httpSSLEnabled = httpSSLEnabled;

			return this;
		}

		public Builder maxConnections(int maxConnections) {
			_maxConnections = maxConnections;

			return this;
		}

		public Builder maxConnectionsPerRoute(int maxConnectionsPerRoute) {
			_maxConnectionsPerRoute = maxConnectionsPerRoute;

			return this;
		}

		public Builder password(String password) {
			_password = password;

			return this;
		}

		public Builder postCloseRunnable(Runnable postCloseRunnable) {
			_postCloseRunnable = postCloseRunnable;

			return this;
		}

		public Builder preConnectRunnable(Runnable preConnectRunnable) {
			_preConnectRunnable = preConnectRunnable;

			return this;
		}

		public Builder proxyConfig(ProxyConfig proxyConfig) {
			_proxyConfig = proxyConfig;

			return this;
		}

		public Builder truststorePassword(String truststorePassword) {
			_truststorePassword = truststorePassword;

			return this;
		}

		public Builder truststorePath(String truststorePath) {
			_truststorePath = truststorePath;

			return this;
		}

		public Builder truststoreType(String truststoreType) {
			_truststoreType = truststoreType;

			return this;
		}

		public Builder userName(String userName) {
			_userName = userName;

			return this;
		}

		private boolean _active;
		private boolean _authenticationEnabled;
		private boolean _compressionEnabled;
		private String _connectionId;
		private boolean _httpSSLEnabled;
		private int _maxConnections;
		private int _maxConnectionsPerRoute;
		private final Supplier<String[]> _networkHostAddressesSupplier;
		private String _password;
		private Runnable _postCloseRunnable;
		private Runnable _preConnectRunnable;
		private ProxyConfig _proxyConfig;
		private String _truststorePassword;
		private String _truststorePath;
		private String _truststoreType;
		private String _userName;

	}

	private ElasticsearchConnection(Builder builder) {
		_active = builder._active;
		_authenticationEnabled = builder._authenticationEnabled;
		_compressionEnabled = builder._compressionEnabled;
		_connectionId = builder._connectionId;
		_httpSSLEnabled = builder._httpSSLEnabled;
		_maxConnections = builder._maxConnections;
		_maxConnectionsPerRoute = builder._maxConnectionsPerRoute;
		_networkHostAddressesSupplier = builder._networkHostAddressesSupplier;
		_password = builder._password;
		_postCloseRunnable = builder._postCloseRunnable;
		_preConnectRunnable = builder._preConnectRunnable;
		_proxyConfig = builder._proxyConfig;
		_truststorePassword = builder._truststorePassword;
		_truststorePath = builder._truststorePath;
		_truststoreType = builder._truststoreType;
		_userName = builder._userName;
	}

	private ElasticsearchClient _createElasticsearchClient() {
		RestClientTransport restClientTransport =
			new RestClientTransportFactory.Builder(
				_networkHostAddressesSupplier.get()
			).authenticationEnabled(
				_authenticationEnabled
			).compressionEnabled(
				_compressionEnabled
			).httpSSLEnabled(
				_httpSSLEnabled
			).maxConnections(
				_maxConnections
			).maxConnectionsPerRoute(
				_maxConnectionsPerRoute
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
			).newRestClientTransport();

		_restClientTransport = restClientTransport;

		return new ElasticsearchClient(restClientTransport);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnection.class);

	private final boolean _active;
	private final boolean _authenticationEnabled;
	private final boolean _compressionEnabled;
	private final String _connectionId;
	private ElasticsearchClient _elasticsearchClient;
	private final boolean _httpSSLEnabled;
	private final int _maxConnections;
	private final int _maxConnectionsPerRoute;
	private final Supplier<String[]> _networkHostAddressesSupplier;
	private final String _password;
	private final Runnable _postCloseRunnable;
	private final Runnable _preConnectRunnable;
	private final ProxyConfig _proxyConfig;
	private RestClientTransport _restClientTransport;
	private final String _truststorePassword;
	private final String _truststorePath;
	private final String _truststoreType;
	private final String _userName;

}