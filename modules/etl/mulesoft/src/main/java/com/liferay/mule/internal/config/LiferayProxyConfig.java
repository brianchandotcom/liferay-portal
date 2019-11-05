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

package com.liferay.mule.internal.config;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.client.proxy.ProxyConfig;
import org.mule.runtime.http.api.client.proxy.ProxyConfigBuilder;

/**
 * @author Matija Petanjek
 */
public class LiferayProxyConfig {

	public ProxyConfig getProxyConfig() {
		ProxyConfigBuilder proxyConfigBuilder = ProxyConfig.builder();

		if ((_proxyHost != null) && (_proxyPort != null)) {
			proxyConfigBuilder.host(_proxyHost);
			proxyConfigBuilder.port(_proxyPort);
			proxyConfigBuilder.username(_proxyUsername);
			proxyConfigBuilder.password(_proxyPassword);

			return proxyConfigBuilder.build();
		}

		return null;
	}

	private static final String _PROXY_CONFIG = "Proxy Config";

	@DisplayName("Host")
	@Optional
	@Parameter
	@Placement(order = 1, tab = _PROXY_CONFIG)
	private String _proxyHost;

	@DisplayName("Password")
	@Optional
	@Parameter
	@Password
	@Placement(order = 4, tab = _PROXY_CONFIG)
	private String _proxyPassword;

	@DisplayName("Port")
	@Optional
	@Parameter
	@Placement(order = 2, tab = _PROXY_CONFIG)
	private Integer _proxyPort;

	@DisplayName("Username")
	@Optional
	@Parameter
	@Placement(order = 3, tab = _PROXY_CONFIG)
	private String _proxyUsername;

}