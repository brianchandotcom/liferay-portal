/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
public class PutForecastOrdersHandler implements Handler<RoutingContext> {

	public static final String PATH = "/projects/:projectId/forecast/orders";

	public PutForecastOrdersHandler(ForecastOrderService forecastOrderService) {
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