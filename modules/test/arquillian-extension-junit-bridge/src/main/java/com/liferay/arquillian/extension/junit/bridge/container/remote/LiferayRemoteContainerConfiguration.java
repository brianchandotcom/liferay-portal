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

import org.jboss.arquillian.container.osgi.jmx.JMXContainerConfiguration;

/**
 * @author Preston Crary
 */
public class LiferayRemoteContainerConfiguration
	extends JMXContainerConfiguration {

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

	@Override
	public boolean isAutostartBundle() {
		return true;
	}

	public void setHttpHost(String httpHost) {
		_httpHost = httpHost;
	}

	public void setHttpPort(int httpPort) {
		_httpPort = httpPort;
	}

	@Override
	public void validate() {
		if (_httpHost == null) {
			setHttpHost(LIFERAY_DEFAULT_HTTP_HOST);
		}

		if (_httpPort == null) {
			setHttpPort(LIFERAY_DEFAULT_HTTP_PORT);
		}

		if (jmxServiceURL == null) {
			setJmxServiceURL(LIFERAY_DEFAULT_JMX_SERVICE_URL);
		}

		if (jmxPassword == null) {
			setJmxPassword(LIFERAY_DEFAULT_JMX_PASSWORD);
		}

		if (jmxUsername == null) {
			setJmxUsername(LIFERAY_DEFAULT_JMX_USERNAME);
		}
	}

	private String _httpHost;
	private Integer _httpPort;

}