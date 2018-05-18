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

import com.liferay.commerce.cloud.server.model.ForecastOrder;
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
public interface ForecastOrderService {

	public static final String ADDRESS = CommerceCloudUtil.getServiceAddress(
		ForecastOrderService.class);

	public static ForecastOrderService createProxy(Vertx vertx) {
		return new ForecastOrderServiceVertxEBProxy(vertx, ADDRESS);
	}

	public void addForecastOrders(
		String projectId, List<ForecastOrder> forecastOrders,
		Handler<AsyncResult<Void>> handler);

	public void getForecastOrders(
		String projectId, boolean processed,
		Handler<AsyncResult<List<ForecastOrder>>> handler);

	public void updateProcessed(
		String projectId, long orderId, boolean processed,
		Handler<AsyncResult<Void>> handler);

}