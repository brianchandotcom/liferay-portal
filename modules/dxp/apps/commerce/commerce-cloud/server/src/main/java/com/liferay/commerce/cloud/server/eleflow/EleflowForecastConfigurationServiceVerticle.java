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
import com.liferay.commerce.cloud.server.eleflow.service.impl.EleflowForecastConfigurationServiceImpl;
import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.util.CommerceCloudUtil;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastConfigurationServiceVerticle
	extends BaseServiceVerticle<ForecastConfigurationService> {

	@Override
	protected Logger getLogger() {
		return _logger;
	}

	@Override
	protected ForecastConfigurationService getServiceImpl(
		JsonObject configJsonObject) {

		String callbackURL = _getCallbackURL(configJsonObject);

		String eleflowHost = configJsonObject.getString(
			"ELEFLOW_HOST", _DEFAULT_ELEFLOW_HOST);
		String eleflowPath = configJsonObject.getString(
			"ELEFLOW_PATH", _DEFAULT_ELEFLOW_PATH);

		if (_logger.isInfoEnabled()) {
			_logger.info(
				"Using Eleflow forecast configuration implementation " +
					"pointing to {0}{1}",
				eleflowHost, eleflowPath);
		}

		return new EleflowForecastConfigurationServiceImpl(
			vertx, callbackURL, eleflowHost, eleflowPath);
	}

	@Override
	protected Class<ForecastConfigurationService> getServiceInterface() {
		return ForecastConfigurationService.class;
	}

	private String _getCallbackURL(JsonObject configJsonObject) {
		StringBuilder sb = new StringBuilder();

		sb.append("https://");
		sb.append(CommerceCloudUtil.getHost(configJsonObject));
		sb.append("/projects/:projectId/callback");

		return sb.toString();
	}

	private static final String _DEFAULT_ELEFLOW_HOST =
		"b2l9j0pz3i.execute-api.us-west-2.amazonaws.com";

	private static final String _DEFAULT_ELEFLOW_PATH = "/Prod";

	private static final Logger _logger = LoggerFactory.getLogger(
		EleflowForecastConfigurationServiceVerticle.class);

}