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