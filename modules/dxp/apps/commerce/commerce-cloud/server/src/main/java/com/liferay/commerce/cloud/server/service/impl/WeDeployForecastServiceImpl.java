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

package com.liferay.commerce.cloud.server.service.impl;

import com.liferay.commerce.cloud.server.constants.ContentTypes;
import com.liferay.commerce.cloud.server.model.Forecast;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ForecastService;
import com.liferay.commerce.cloud.server.service.PushSenderService;
import com.liferay.commerce.cloud.server.util.JsonUtil;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.codec.impl.BodyCodecImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class WeDeployForecastServiceImpl
	extends BaseWeDeployServiceImpl implements ForecastService {

	public WeDeployForecastServiceImpl(Vertx vertx, String host, String token) {
		super(vertx, host, token);

		_pushSenderService = PushSenderService.createProxy(vertx);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void addForecasts(
		Project project, List<Forecast> forecasts,
		Handler<AsyncResult<Void>> handler) {

		HttpRequest<Void> httpRequest = webClient.post(
			"/forecasts"
		).as(
			BodyCodec.none()
		);

		addAuthorization(httpRequest);

		List<Future> futures = new ArrayList<>(
			(int)Math.ceil((double)forecasts.size() / _FORECASTS_SIZE) + 1);

		futures.add(_pushForecasts(project, forecasts));

		JsonArray jsonArray = new JsonArray();

		for (Forecast forecast : forecasts) {
			JsonObject jsonObject = forecast.toJson();

			jsonObject.put("id", _getId(project, forecast));
			jsonObject.put("projectId", project.getId());

			jsonArray.add(jsonObject);

			if (jsonArray.size() == _FORECASTS_SIZE) {
				Future<Void> future = _addForecasts(jsonArray);

				futures.add(future);

				jsonArray = new JsonArray();
			}
		}

		if (!jsonArray.isEmpty()) {
			Future<Void> future = _addForecasts(jsonArray);

			futures.add(future);
		}

		CompositeFuture.all(
			futures
		).<Void>mapEmpty(
		).setHandler(
			handler
		);
	}

	@Override
	public void getForecasts(
		String projectId, long time,
		Handler<AsyncResult<List<Forecast>>> handler) {

		HttpRequest<List<Forecast>> httpRequest = webClient.get(
			"/forecasts"
		).as(
			_forecastsBodyCodec
		);

		addAuthorization(httpRequest);

		JsonArray filterJsonArray = new JsonArray();

		filterJsonArray.add(
			JsonUtil.getFilterJsonObject("projectId", projectId));
		filterJsonArray.add(JsonUtil.getFilterJsonObject("time", ">", time));

		httpRequest.setQueryParam("filter", filterJsonArray.encode());

		httpRequest.send(
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	private Future<Void> _addForecasts(JsonArray jsonArray) {
		Future<Void> future = Future.future();

		HttpRequest<Void> httpRequest = webClient.post(
			"/forecasts"
		).as(
			BodyCodec.none()
		);

		addAuthorization(httpRequest);

		httpRequest.putHeader(
			HttpHeaders.CONTENT_TYPE.toString(), ContentTypes.APPLICATION_JSON);

		httpRequest.sendBuffer(
			Buffer.buffer(jsonArray.encode()),
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, future));

		return future;
	}

	private String _getId(Project project, Forecast forecast) {
		StringBuilder sb = new StringBuilder();

		sb.append(project.getId());
		sb.append('-');
		sb.append(forecast.getCompanyId());
		sb.append('-');
		sb.append(forecast.getCustomerId());
		sb.append('-');
		sb.append(forecast.getSKU());
		sb.append('-');
		sb.append(forecast.getTarget());
		sb.append('-');
		sb.append(forecast.getPeriod());

		return sb.toString();
	}

	private Future<Void> _pushForecasts(
		Project project, List<Forecast> forecasts) {

		Future<Void> future = Future.future();

		_pushSenderService.pushForecasts(project, forecasts, future);

		return future;
	}

	private static final int _FORECASTS_SIZE = 200;

	private static final BodyCodec<List<Forecast>> _forecastsBodyCodec;

	static {
		_forecastsBodyCodec = BodyCodec.create(
			BodyCodecImpl.JSON_ARRAY_DECODER.andThen(
				jsonArray -> JsonUtil.fromJsonArray(jsonArray, Forecast::new)));
	}

	private final PushSenderService _pushSenderService;

}