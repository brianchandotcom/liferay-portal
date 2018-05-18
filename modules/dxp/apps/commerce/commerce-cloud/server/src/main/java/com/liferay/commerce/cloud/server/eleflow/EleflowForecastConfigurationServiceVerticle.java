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

package com.liferay.commerce.cloud.server.eleflow;

import com.liferay.commerce.cloud.server.BaseServiceVerticle;
import com.liferay.commerce.cloud.server.eleflow.handler.EleflowForecastCallbackHandler;
import com.liferay.commerce.cloud.server.eleflow.service.impl.EleflowForecastConfigurationServiceImpl;
import com.liferay.commerce.cloud.server.eleflow.util.EleflowUtil;
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

		String host = EleflowUtil.getHost(configJsonObject);
		String path = EleflowUtil.getPath(configJsonObject);

		if (_logger.isInfoEnabled()) {
			_logger.info(
				"Using Eleflow forecast configuration implementation " +
					"pointing to {0}{1}",
				host, path);
		}

		return new EleflowForecastConfigurationServiceImpl(
			vertx, callbackURL, host, path);
	}

	@Override
	protected Class<ForecastConfigurationService> getServiceInterface() {
		return ForecastConfigurationService.class;
	}

	private String _getCallbackURL(JsonObject configJsonObject) {
		StringBuilder sb = new StringBuilder();

		sb.append("https://");
		sb.append(CommerceCloudUtil.getHost(configJsonObject));
		sb.append(EleflowForecastCallbackHandler.PATH);

		return sb.toString();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		EleflowForecastConfigurationServiceVerticle.class);

}