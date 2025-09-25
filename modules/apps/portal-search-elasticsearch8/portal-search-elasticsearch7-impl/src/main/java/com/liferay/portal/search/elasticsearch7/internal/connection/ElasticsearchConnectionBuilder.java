/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import java.util.function.Consumer;

/**
 * @author Bryan Engler
 */
public class ElasticsearchConnectionBuilder {

	public ElasticsearchConnectionBuilder active(boolean active) {
		_active = active;

		return this;
	}

	public ElasticsearchConnectionBuilder authenticationEnabled(
		boolean authenticationEnabled) {

		_authenticationEnabled = authenticationEnabled;

		return this;
	}

	public ElasticsearchConnection build() {
		ElasticsearchConnection elasticsearchConnection =
			new ElasticsearchConnection();

		elasticsearchConnection.setActive(_active);
		elasticsearchConnection.setAuthenticationEnabled(
			_authenticationEnabled);
		elasticsearchConnection.setConnectionId(_connectionId);
		elasticsearchConnection.setHttpSSLEnabled(_httpSSLEnabled);
		elasticsearchConnection.setMaxConnections(_maxConnections);
		elasticsearchConnection.setMaxConnectionsPerRoute(
			_maxConnectionsPerRoute);
		elasticsearchConnection.setNetworkHostAddresses(_networkHostAddresses);
		elasticsearchConnection.setPassword(_password);
		elasticsearchConnection.setPostCloseRunnable(_postCloseRunnable);
		elasticsearchConnection.setPreConnectElasticsearchConnectionConsumer(
			_preConnectElasticsearchConnectionConsumer);
		elasticsearchConnection.setProxyConfig(_proxyConfig);
		elasticsearchConnection.setTruststorePassword(_truststorePassword);
		elasticsearchConnection.setTruststorePath(_truststorePath);
		elasticsearchConnection.setTruststoreType(_truststoreType);
		elasticsearchConnection.setUserName(_userName);

		return elasticsearchConnection;
	}

	public ElasticsearchConnectionBuilder connectionId(String connectionId) {
		_connectionId = connectionId;

		return this;
	}

	public ElasticsearchConnectionBuilder httpSSLEnabled(
		boolean httpSSLEnabled) {

		_httpSSLEnabled = httpSSLEnabled;

		return this;
	}

	public ElasticsearchConnectionBuilder maxConnections(int maxConnections) {
		_maxConnections = maxConnections;

		return this;
	}

	public ElasticsearchConnectionBuilder maxConnectionsPerRoute(
		int maxConnectionsPerRoute) {

		_maxConnectionsPerRoute = maxConnectionsPerRoute;

		return this;
	}

	public ElasticsearchConnectionBuilder networkHostAddresses(
		String[] networkHostAddresses) {

		_networkHostAddresses = networkHostAddresses;

		return this;
	}

	public ElasticsearchConnectionBuilder password(String password) {
		_password = password;

		return this;
	}

	public ElasticsearchConnectionBuilder postCloseRunnable(
		Runnable postCloseRunnable) {

		_postCloseRunnable = postCloseRunnable;

		return this;
	}

	public ElasticsearchConnectionBuilder
		preConnectElasticsearchConnectionConsumer(
			Consumer<ElasticsearchConnection>
				preConnectElasticsearchConnectionConsumer) {

		_preConnectElasticsearchConnectionConsumer =
			preConnectElasticsearchConnectionConsumer;

		return this;
	}

	public ElasticsearchConnectionBuilder proxyConfig(ProxyConfig proxyConfig) {
		_proxyConfig = proxyConfig;

		return this;
	}

	public ElasticsearchConnectionBuilder truststorePassword(
		String truststorePassword) {

		_truststorePassword = truststorePassword;

		return this;
	}

	public ElasticsearchConnectionBuilder truststorePath(
		String truststorePath) {

		_truststorePath = truststorePath;

		return this;
	}

	public ElasticsearchConnectionBuilder truststoreType(
		String truststoreType) {

		_truststoreType = truststoreType;

		return this;
	}

	public ElasticsearchConnectionBuilder userName(String userName) {
		_userName = userName;

		return this;
	}

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
	private String _truststorePassword;
	private String _truststorePath;
	private String _truststoreType;
	private String _userName;

}