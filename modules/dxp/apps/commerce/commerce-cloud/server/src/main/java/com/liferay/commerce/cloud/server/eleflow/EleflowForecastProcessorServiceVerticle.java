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

package com.liferay.commerce.cloud.server.eleflow;

import com.liferay.commerce.cloud.server.BaseServiceVerticle;
import com.liferay.commerce.cloud.server.eleflow.service.impl.EleflowForecastProcessorServiceImpl;
import com.liferay.commerce.cloud.server.eleflow.util.EleflowUtil;
import com.liferay.commerce.cloud.server.service.ForecastProcessorService;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastProcessorServiceVerticle
	extends BaseServiceVerticle<ForecastProcessorService> {

	@Override
	protected Logger getLogger() {
		return _logger;
	}

	@Override
	protected ForecastProcessorService getServiceImpl(
		JsonObject configJsonObject) {

		String host = EleflowUtil.getHost(configJsonObject);
		String path = EleflowUtil.getPath(configJsonObject);

		if (_logger.isInfoEnabled()) {
			_logger.info(
				"Using Eleflow forecast processor implementation pointing to " +
					"{0}{1}",
				host, path);
		}

		return new EleflowForecastProcessorServiceImpl(vertx, host, path);
	}

	@Override
	protected Class<ForecastProcessorService> getServiceInterface() {
		return ForecastProcessorService.class;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		EleflowForecastProcessorServiceVerticle.class);

}