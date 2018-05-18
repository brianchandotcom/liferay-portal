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
			_forecastOrdersBodyCodec
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

		addAuthorization(httpRequest);

		JsonObject jsonObject = JsonUtil.getJsonObject("processed", processed);

		httpRequest.sendJsonObject(
			jsonObject,
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	private static String _getId(String projectId, long orderId) {
		return projectId + "-" + orderId;
	}

	private static final BodyCodec<List<ForecastOrder>>
		_forecastOrdersBodyCodec;

	static {
		_forecastOrdersBodyCodec = BodyCodec.create(
			BodyCodecImpl.JSON_ARRAY_DECODER.andThen(
				jsonArray -> JsonUtil.fromJsonArray(
					jsonArray, ForecastOrder::new)));
	}

}