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

package com.liferay.arquillian.extension.junit.bridge.container.remote;

import org.jboss.arquillian.container.spi.client.container.ContainerConfiguration;

/**
 * @author Preston Crary
 */
public class LiferayRemoteContainerConfiguration
	implements ContainerConfiguration {

	public static final String LIFERAY_DEFAULT_HTTP_HOST = "localhost";

	public static final int LIFERAY_DEFAULT_HTTP_PORT = 8080;

	public static final String LIFERAY_DEFAULT_JMX_PASSWORD = "";

	public static final String LIFERAY_DEFAULT_JMX_SERVICE_URL =
		"service:jmx:rmi:///jndi/rmi://localhost:8099/jmxrmi";

	public static final String LIFERAY_DEFAULT_JMX_USERNAME = "";

	public String getHttpHost() {
		return _httpHost;
	}

	public int getHttpPort() {
		return _httpPort;
	}

	public String getJmxPassword() {
		return _jmxPassword;
	}

	public String getJmxServiceURL() {
		return _jmxServiceURL;
	}

	public String getJmxUsername() {
		return _jmxUsername;
	}

	public boolean isAutostartBundle() {
		return true;
	}

	public void setHttpHost(String httpHost) {
		_httpHost = httpHost;
	}

	public void setHttpPort(int httpPort) {
		_httpPort = httpPort;
	}

	public void setJmxPassword(String jmxPassword) {
		_jmxPassword = jmxPassword;
	}

	public void setJmxServiceURL(String jmxServiceURL) {
		_jmxServiceURL = jmxServiceURL;
	}

	public void setJmxUsername(String jmxUsername) {
		_jmxUsername = jmxUsername;
	}

	@Override
	public void validate() {
		if (_httpHost == null) {
			_httpHost = LIFERAY_DEFAULT_HTTP_HOST;
		}

		if (_httpPort == null) {
			_httpPort = LIFERAY_DEFAULT_HTTP_PORT;
		}

		if (_jmxServiceURL == null) {
			_jmxServiceURL = LIFERAY_DEFAULT_JMX_SERVICE_URL;
		}

		if (_jmxPassword == null) {
			_jmxPassword = LIFERAY_DEFAULT_JMX_PASSWORD;
		}

		if (_jmxUsername == null) {
			_jmxUsername = LIFERAY_DEFAULT_JMX_USERNAME;
		}
	}

	private String _httpHost;
	private Integer _httpPort;
	private String _jmxPassword;
	private String _jmxServiceURL;
	private String _jmxUsername;

}