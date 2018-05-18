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

package com.liferay.commerce.cloud.server.eleflow.handler;

import com.liferay.commerce.cloud.server.constants.WebKeys;
import com.liferay.commerce.cloud.server.eleflow.util.EleflowUtil;
import com.liferay.commerce.cloud.server.model.Forecast;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ForecastProcessorService;
import com.liferay.commerce.cloud.server.service.ForecastService;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.netty.handler.codec.http.HttpResponseStatus;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastCallbackHandler implements Handler<RoutingContext> {

	public static final String PATH = "/projects/:projectId/forecast/callback";

	public EleflowForecastCallbackHandler(
		ForecastProcessorService forecastProcessorService,
		ForecastService forecastService) {

		_forecastProcessorService = forecastProcessorService;
		_forecastService = forecastService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = routingContext.get(WebKeys.PROJECT);

		HttpServerRequest httpServerRequest = routingContext.request();

		String dateString = httpServerRequest.getParam("date");

		if (dateString == null) {
			routingContext.fail(HttpResponseStatus.BAD_REQUEST.code());

			return;
		}

		long time = EleflowUtil.getTime(dateString);

		Future<List<Forecast>> forecastsFuture = Future.future();

		_forecastProcessorService.getForecasts(project, time, forecastsFuture);

		forecastsFuture.compose(
			forecasts -> {
				Future<Void> future = Future.future();

				_forecastService.addForecasts(project, forecasts, future);

				return future;
			}
		).setHandler(
			asyncResult -> VertxUtil.handleHttpResponse(
				asyncResult, routingContext)
		);
	}

	private final ForecastProcessorService _forecastProcessorService;
	private final ForecastService _forecastService;

}