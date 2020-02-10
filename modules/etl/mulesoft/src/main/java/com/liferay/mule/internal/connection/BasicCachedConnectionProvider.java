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

package com.liferay.mule.internal.connection;

import com.liferay.mule.internal.connection.config.BasicAuthenticationConfig;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Matija Petanjek
 */
@Alias("basic-connection")
@DisplayName("1 - Basic")
public class BasicCachedConnectionProvider
	extends BaseCachedConnectionProvider {

	@Override
	public LiferayConnection connect() throws ConnectionException {
		return LiferayConnection.withBasicAuthentication(
			httpService, openApiSpecPath,
			_basicAuthenticationConfig.getUsername(),
			_basicAuthenticationConfig.getPassword(),
			liferayProxyConfig.getProxyConfig());
	}

	@ParameterGroup(name = "Connection config")
	@Placement(order = 1)
	private BasicAuthenticationConfig _basicAuthenticationConfig;

}