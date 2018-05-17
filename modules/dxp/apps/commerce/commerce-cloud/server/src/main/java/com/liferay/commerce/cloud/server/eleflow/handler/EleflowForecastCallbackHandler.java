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