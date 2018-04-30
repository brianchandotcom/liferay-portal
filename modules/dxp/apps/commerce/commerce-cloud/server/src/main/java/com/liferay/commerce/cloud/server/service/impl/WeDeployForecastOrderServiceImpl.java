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
import com.liferay.commerce.cloud.server.model.ForecastOrder;
import com.liferay.commerce.cloud.server.service.ForecastOrderService;
import com.liferay.commerce.cloud.server.util.JsonUtil;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.codec.impl.BodyCodecImpl;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class WeDeployForecastOrderServiceImpl
	extends BaseWeDeployServiceImpl implements ForecastOrderService {

	public WeDeployForecastOrderServiceImpl(
		Vertx vertx, String host, String token) {

		super(vertx, host, token);
	}

	@Override
	public void addForecastOrders(
		String projectId, List<ForecastOrder> forecastOrders,
		Handler<AsyncResult<Void>> handler) {

		HttpRequest<Void> httpRequest = webClient.post(
			"/forecast_orders"
		).as(
			BodyCodec.none()
		);

		addAuthorization(httpRequest);

		JsonArray jsonArray = new JsonArray();

		for (ForecastOrder forecastOrder : forecastOrders) {
			JsonObject jsonObject = forecastOrder.toJson();

			jsonObject.put("id", _getId(projectId, forecastOrder.getOrderId()));
			jsonObject.put("processed", false);
			jsonObject.put("projectId", projectId);

			jsonArray.add(jsonObject);
		}

		httpRequest.putHeader(
			HttpHeaders.CONTENT_TYPE.toString(), ContentTypes.APPLICATION_JSON);

		httpRequest.sendBuffer(
			Buffer.buffer(jsonArray.encode()),
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	@Override
	public void getForecastOrders(
		String projectId, boolean processed,
		Handler<AsyncResult<List<ForecastOrder>>> handler) {

		HttpRequest<List<ForecastOrder>> httpRequest = webClient.get(
			"/forecast_orders"
		).as(
			_bodyCodec
		);

		addAuthorization(httpRequest);

		JsonArray filterJsonArray = new JsonArray();

		JsonArray andFilterJsonArray = new JsonArray();

		andFilterJsonArray.add(
			JsonUtil.getFilterJsonObject("projectId", projectId));
		andFilterJsonArray.add(
			JsonUtil.getFilterJsonObject("processed", processed));

		filterJsonArray.add(JsonUtil.getJsonObject("and", andFilterJsonArray));

		httpRequest.setQueryParam("filter", filterJsonArray.encode());

		JsonArray sortJsonArray = new JsonArray();

		sortJsonArray.add(JsonUtil.getJsonObject("createTime", "asc"));

		httpRequest.setQueryParam("sort", sortJsonArray.encode());

		httpRequest.send(
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	@Override
	public void updateProcessed(
		String projectId, long orderId, boolean processed,
		Handler<AsyncResult<Void>> handler) {

		HttpRequest<Void> httpRequest = webClient.patch(
			"/forecast_orders/" + _getId(projectId, orderId)
		).as(
			BodyCodec.none()
		);

		JsonObject jsonObject = JsonUtil.getJsonObject("processed", processed);

		httpRequest.sendJsonObject(
			jsonObject,
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	private static String _getId(String projectId, long orderId) {
		return projectId + "-" + orderId;
	}

	private static final BodyCodec<List<ForecastOrder>> _bodyCodec;

	static {
		_bodyCodec = BodyCodec.create(
			BodyCodecImpl.JSON_ARRAY_DECODER.andThen(
				jsonArray -> JsonUtil.fromJsonArray(
					jsonArray, ForecastOrder::new)));
	}

}