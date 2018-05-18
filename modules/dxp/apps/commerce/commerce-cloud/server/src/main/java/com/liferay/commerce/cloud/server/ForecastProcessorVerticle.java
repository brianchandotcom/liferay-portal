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

import com.liferay.commerce.cloud.server.service.ForecastProcessorService;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.concurrent.TimeUnit;

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

	private static final long _DEFAULT_FORECAST_PROCESSOR_INTERVAL =
		TimeUnit.MINUTES.toMillis(5);

	private static final Logger _logger = LoggerFactory.getLogger(
		ForecastProcessorVerticle.class);

}