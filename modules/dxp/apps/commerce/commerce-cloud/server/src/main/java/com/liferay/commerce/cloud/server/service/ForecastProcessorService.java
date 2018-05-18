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

import com.liferay.commerce.cloud.server.model.Forecast;
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
public interface ForecastProcessorService {

	public static final String ADDRESS = CommerceCloudUtil.getServiceAddress(
		ForecastProcessorService.class);

	public static ForecastProcessorService createProxy(Vertx vertx) {
		return new ForecastProcessorServiceVertxEBProxy(vertx, ADDRESS);
	}

	public void getForecasts(
		Project project, long time,
		Handler<AsyncResult<List<Forecast>>> handler);

	public void processForecastOrders(Handler<AsyncResult<Integer>> handler);

}