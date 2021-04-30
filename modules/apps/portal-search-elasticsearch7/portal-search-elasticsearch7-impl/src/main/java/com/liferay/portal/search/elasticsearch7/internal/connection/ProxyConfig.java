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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Stream;

/**
 * @author Adam Brandizzi
 */
public class ProxyConfig {

	public static Builder builder(Http http) {
		return new Builder(http);
	}

	public String getHost() {
		return _host;
	}

	public String getPassword() {
		return _password;
	}

	public int getPort() {
		return _port;
	}

	public String getUserName() {
		return _userName;
	}

	public boolean shouldApplyConfig() {
		return _shouldApplyConfig;
	}

	public boolean shouldApplyCredentials() {
		return _shouldApplyCredentials;
	}

	public static class Builder {

		public Builder(Http http) {
			_http = http;
		}

		public ProxyConfig build() {
			ProxyConfig proxyConfig = new ProxyConfig();

			proxyConfig._host = getHost();
			proxyConfig._port = getPort();
			proxyConfig._shouldApplyConfig = shouldApplyConfig();
			proxyConfig._shouldApplyCredentials = shouldApplyCredentials();

			return proxyConfig;
		}

		public Builder host(String host) {
			if (!Validator.isBlank(host)) {
				_host = host;
			}

			return this;
		}

		public Builder networkAddresses(String[] networkHostAddresses) {
			_networkHostAddresses = networkHostAddresses;

			return this;
		}

		public Builder password(String password) {
			_password = password;

			return this;
		}

		public Builder port(int port) {
			if (port > 0) {
				_port = port;
			}

			return this;
		}

		public Builder userName(String userName) {
			_userName = userName;

			return this;
		}

		protected String getHost() {
			return _host;
		}

		protected int getPort() {
			return _port;
		}

		protected boolean hasHostAndPort() {
			if (Validator.isBlank(_host)) {
				return false;
			}

			if (_port <= 0) {
				return false;
			}

			return true;
		}

		protected boolean shouldApplyConfig() {
			if (hasHostAndPort()) {
				return true;
			}

			if (!_http.hasProxyConfig()) {
				return false;
			}

			return Stream.of(
				_networkHostAddresses
			).allMatch(
				host -> !_http.isNonProxyHost(host)
			);
		}

		protected boolean shouldApplyCredentials() {
			if (!shouldApplyConfig()) {
				return false;
			}

			if (Validator.isBlank(_password)) {
				return false;
			}

			if (Validator.isBlank(_userName)) {
				return false;
			}

			return true;
		}

		private String _host = SystemProperties.get("http.proxyHost");
		private final Http _http;
		private String[] _networkHostAddresses = {};
		private String _password;
		private int _port = GetterUtil.getInteger(
			SystemProperties.get("http.proxyPort"));
		private String _userName;

	}

	private ProxyConfig() {
	}

	private ProxyConfig(ProxyConfig proxyConfig) {
		_shouldApplyConfig = proxyConfig._shouldApplyConfig;
		_shouldApplyCredentials = proxyConfig._shouldApplyCredentials;
		_host = proxyConfig._host;
		_password = proxyConfig._password;
		_port = proxyConfig._port;
		_userName = proxyConfig._userName;
	}

	private String _host;
	private String _password;
	private int _port;
	private boolean _shouldApplyConfig;
	private boolean _shouldApplyCredentials;
	private String _userName;

}