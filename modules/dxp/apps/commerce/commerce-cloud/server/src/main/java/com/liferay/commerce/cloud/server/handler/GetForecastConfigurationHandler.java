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

package com.liferay.commerce.cloud.server.handler;

import com.liferay.commerce.cloud.server.constants.WebKeys;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Andrea Di Giorgi
 */
public class GetForecastConfigurationHandler
	implements Handler<RoutingContext> {

	public static final String PATH =
		"/projects/:projectId/forecast/configuration/";

	public GetForecastConfigurationHandler(
		ForecastConfigurationService forecastSchedulerService) {

		_forecastConfigurationService = forecastSchedulerService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = routingContext.get(WebKeys.PROJECT);

		_forecastConfigurationService.getForecastConfiguration(
			project,
			asyncResult -> VertxUtil.handleHttpResponse(
				asyncResult, routingContext));
	}

	private final ForecastConfigurationService _forecastConfigurationService;

}