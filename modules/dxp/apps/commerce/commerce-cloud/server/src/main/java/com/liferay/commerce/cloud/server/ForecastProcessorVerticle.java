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

import com.liferay.commerce.cloud.server.service.ForecastProcessorService;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Andrea Di Giorgi
 */
public class ForecastProcessorVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		ConfigRetriever configRetriever = ConfigRetriever.create(vertx);

		ConfigRetriever.getConfigAsFuture(
			configRetriever
		).compose(
			configJsonObject -> {
				long interval = configJsonObject.getLong(
					"FORECAST_PROCESSOR_INTERVAL",
					_DEFAULT_FORECAST_PROCESSOR_INTERVAL);

				ForecastProcessorService forecastProcessorService =
					ForecastProcessorService.createProxy(vertx);

				_setTimer(forecastProcessorService, interval);

				startFuture.complete();
			},
			startFuture
		);
	}

	private void _setTimer(
		ForecastProcessorService forecastProcessorService, long interval) {

		vertx.setTimer(
			interval,
			timerId -> {
				Future<Integer> future = Future.future();

				forecastProcessorService.processForecastOrders(future);

				future.setHandler(
					asyncResult -> {
						if (asyncResult.failed()) {
							_logger.error(
								"Unable to process forecasts",
								asyncResult.cause());
						}
						else {
							int forecastOrdersCount = asyncResult.result();

							if ((forecastOrdersCount > 0) &&
								_logger.isDebugEnabled()) {

								_logger.debug(
									"Succesfully processed {0} forecast orders",
									forecastOrdersCount);
							}
						}

						_setTimer(forecastProcessorService, interval);
					});
			});
	}

	private static final long _DEFAULT_FORECAST_PROCESSOR_INTERVAL = 1000;

	private static final Logger _logger = LoggerFactory.getLogger(
		ForecastProcessorVerticle.class);

}