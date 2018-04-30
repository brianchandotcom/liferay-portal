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

package com.liferay.commerce.cloud.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.service.ForecastOrderService;
import com.liferay.commerce.cloud.server.service.ForecastProcessorService;
import com.liferay.commerce.cloud.server.service.ProjectService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCloudServerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		CompositeFuture.all(
			_deployVerticle(ForecastConfigurationService.ADDRESS),
			_deployVerticle(ForecastOrderService.ADDRESS),
			_deployVerticle(ForecastProcessorService.ADDRESS),
			_deployVerticle(ProjectService.ADDRESS)
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

	static {
		Json.mapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Json.mapper.registerModule(new JavaTimeModule());
	}

}