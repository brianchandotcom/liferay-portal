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
import com.liferay.commerce.cloud.server.model.Order;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.OrderService;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class PostOrdersHandler implements Handler<RoutingContext> {

	public PostOrdersHandler(OrderService orderService) {
		_orderService = orderService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = routingContext.get(WebKeys.PROJECT);

		List<Order> orders = _getOrders(routingContext);

		_orderService.addOrders(
			project, orders,
			asyncResult -> VertxUtil.handleHttpResponse(
				asyncResult, routingContext));
	}

	private List<Order> _getOrders(RoutingContext routingContext) {
		JsonArray jsonArray = routingContext.getBodyAsJsonArray();

		List<Order> orders = new ArrayList<>(jsonArray.size());

		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = jsonArray.getJsonObject(i);

			orders.add(new Order(jsonObject));
		}

		return orders;
	}

	private final OrderService _orderService;

}