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
		elasticsearchConnection.setNetworkHostAddresses(_networkHostAddresses);
		elasticsearchConnection.setPassword(_password);
		elasticsearchConnection.setPostCloseRunnable(_postCloseRunnable);
		elasticsearchConnection.setPreConnectElasticsearchConnectionConsumer(
			_preConnectElasticsearchConnectionConsumer);
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
	private String[] _networkHostAddresses;
	private String _password;
	private Runnable _postCloseRunnable;
	private Consumer<ElasticsearchConnection>
		_preConnectElasticsearchConnectionConsumer;
	private String _truststorePassword;
	private String _truststorePath;
	private String _truststoreType;
	private String _userName;

}