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

import com.liferay.mule.internal.connection.BasicCachedConnectionProvider;
import com.liferay.mule.internal.connection.OAuth2CachedConnectionProvider;
import com.liferay.mule.internal.operation.LiferayOperations;

import java.util.concurrent.TimeUnit;

import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

/**
 * @author Matija Petanjek
 */
@Configuration
@ConnectionProviders(
	{BasicCachedConnectionProvider.class, OAuth2CachedConnectionProvider.class}
)
@Operations(LiferayOperations.class)
public class LiferayConfig {

	@DisplayName("Connection Timeout")
	@Optional(defaultValue = "5")
	@Parameter
	@Placement(order = 1, tab = Placement.ADVANCED_TAB)
	@Summary("Socket connection timeout value")
	private int _connectionTimeout;

	@DisplayName("Connection Timeout Unit")
	@Optional(defaultValue = "SECONDS")
	@Parameter
	@Placement(order = 2, tab = Placement.ADVANCED_TAB)
	@Summary("Time unit to be used in the Timeout configurations")
	private TimeUnit _connectionTimeoutTimeUnit;

}