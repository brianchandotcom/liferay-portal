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

package com.liferay.commerce.cloud.server.service;

import com.liferay.commerce.cloud.server.model.ForecastConfiguration;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.util.CommerceCloudUtil;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author Andrea Di Giorgi
 */
@ProxyGen
public interface ForecastConfigurationService {

	public static final String ADDRESS = CommerceCloudUtil.getServiceAddress(
		ForecastConfigurationService.class);

	public static ForecastConfigurationService createProxy(Vertx vertx) {
		return new ForecastConfigurationServiceVertxEBProxy(vertx, ADDRESS);
	}

	public void getForecastConfiguration(
		Project project, Handler<AsyncResult<ForecastConfiguration>> handler);

	public void updateForecastConfiguration(
		Project project, ForecastConfiguration forecastConfiguration,
		Handler<AsyncResult<Void>> handler);

}