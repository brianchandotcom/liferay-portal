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

package com.liferay.commerce.cloud.server;

import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.service.ForecastOrderService;
import com.liferay.commerce.cloud.server.service.ForecastProcessorService;
import com.liferay.commerce.cloud.server.service.ForecastService;
import com.liferay.commerce.cloud.server.service.ProjectService;
import com.liferay.commerce.cloud.server.service.PushSenderService;
import com.liferay.commerce.cloud.server.util.JsonUtil;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCloudServerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		JsonUtil.init();

		CompositeFuture.all(
			_deployVerticle(ForecastConfigurationService.ADDRESS),
			_deployVerticle(ForecastOrderService.ADDRESS),
			_deployVerticle(ForecastProcessorService.ADDRESS),
			_deployVerticle(ForecastService.ADDRESS),
			_deployVerticle(ProjectService.ADDRESS),
			_deployVerticle(PushSenderService.ADDRESS)
		).compose(
			__ -> _deployVerticle(
				"com.liferay.commerce.cloud.server.forecast.processor")
		).compose(
			__ -> _deployVerticle("com.liferay.commerce.cloud.server.http")
		).compose(
			__ -> {
				if (_logger.isInfoEnabled()) {
					_logger.info("Commerce Cloud Server started succesfully");
				}

				startFuture.complete();
			},
			startFuture
		);
	}

	private Future<String> _deployVerticle(String serviceName) {
		Future<String> future = Future.future();

		vertx.deployVerticle("service:" + serviceName, future);

		return future;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		CommerceCloudServerVerticle.class);

}