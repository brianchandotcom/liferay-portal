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
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ForecastService;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Andrea Di Giorgi
 */
public class GetForecastsHandler implements Handler<RoutingContext> {

	public static final String PATH = "/projects/:projectId/forecasts/:time";

	public GetForecastsHandler(ForecastService forecastService) {
		_forecastService = forecastService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = (Project)routingContext.get(WebKeys.PROJECT);

		long time = Long.valueOf(routingContext.pathParam("time"));

		_forecastService.getForecasts(
			project.getId(), time,
			asyncResult -> VertxUtil.handleHttpResponse(
				asyncResult, routingContext));
	}

	private final ForecastService _forecastService;

}