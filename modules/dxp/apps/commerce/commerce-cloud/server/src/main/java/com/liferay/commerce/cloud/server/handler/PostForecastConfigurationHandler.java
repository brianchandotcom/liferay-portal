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
import com.liferay.commerce.cloud.server.model.ForecastConfiguration;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Andrea Di Giorgi
 */
public class PostForecastConfigurationHandler
	implements Handler<RoutingContext> {

	public static final String PATH = GetForecastConfigurationHandler.PATH;

	public PostForecastConfigurationHandler(
		ForecastConfigurationService forecastSchedulerService) {

		_forecastConfigurationService = forecastSchedulerService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = routingContext.get(WebKeys.PROJECT);

		JsonObject jsonObject = routingContext.getBodyAsJson();

		ForecastConfiguration forecastConfiguration = new ForecastConfiguration(
			jsonObject);

		_forecastConfigurationService.updateForecastConfiguration(
			project, forecastConfiguration,
			asyncResult -> VertxUtil.handleHttpResponse(
				asyncResult, routingContext));
	}

	private final ForecastConfigurationService _forecastConfigurationService;

}