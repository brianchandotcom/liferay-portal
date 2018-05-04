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
import com.liferay.commerce.cloud.server.model.ForecastOrder;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ForecastOrderService;
import com.liferay.commerce.cloud.server.util.JsonUtil;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class PostForecastOrdersHandler implements Handler<RoutingContext> {

	public static final String PATH = "/projects/:projectId/forecast/orders/";

	public PostForecastOrdersHandler(
		ForecastOrderService forecastOrderService) {

		_forecastOrderService = forecastOrderService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = routingContext.get(WebKeys.PROJECT);

		List<ForecastOrder> forecastOrders = JsonUtil.fromJsonArray(
			routingContext.getBodyAsJsonArray(), ForecastOrder::new);

		_forecastOrderService.addForecastOrders(
			project.getId(), forecastOrders,
			asyncResult -> VertxUtil.handleHttpResponse(
				asyncResult, routingContext));
	}

	private final ForecastOrderService _forecastOrderService;

}