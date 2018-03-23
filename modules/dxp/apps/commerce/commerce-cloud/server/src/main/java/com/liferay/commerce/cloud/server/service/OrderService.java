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

package com.liferay.commerce.cloud.server.service;

import com.liferay.commerce.cloud.server.model.Order;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.util.CommerceCloudUtil;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@ProxyGen
public interface OrderService {

	public static final String ADDRESS = CommerceCloudUtil.getServiceAddress(
		OrderService.class);

	public static OrderService createProxy(Vertx vertx) {
		return new OrderServiceVertxEBProxy(vertx, ADDRESS);
	}

	public void addOrders(
		Project project, List<Order> orders,
		Handler<AsyncResult<Void>> handler);

}