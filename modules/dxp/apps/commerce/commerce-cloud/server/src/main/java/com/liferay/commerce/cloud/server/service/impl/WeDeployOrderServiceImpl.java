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
import com.liferay.commerce.cloud.server.model.Order;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.OrderService;
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

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class WeDeployOrderServiceImpl
	extends BaseWeDeployServiceImpl implements OrderService {

	public WeDeployOrderServiceImpl(Vertx vertx, String host, String token) {
		super(vertx, host, token);
	}

	@Override
	public void addOrders(
		Project project, List<Order> orders,
		Handler<AsyncResult<Void>> handler) {

		HttpRequest<Void> httpRequest = webClient.post(
			"/orders"
		).as(
			BodyCodec.none()
		);

		addAuthorization(httpRequest);

		JsonArray jsonArray = new JsonArray();

		for (Order order : orders) {
			JsonObject jsonObject = order.toJson();

			jsonObject.put("id", project.getId() + "-" + order.getOrderId());
			jsonObject.put("processed", false);
			jsonObject.put("projectId", project.getId());

			jsonArray.add(jsonObject);
		}

		httpRequest.putHeader(
			HttpHeaders.CONTENT_TYPE.toString(), ContentTypes.APPLICATION_JSON);

		httpRequest.sendBuffer(
			Buffer.buffer(jsonArray.encode()),
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

}