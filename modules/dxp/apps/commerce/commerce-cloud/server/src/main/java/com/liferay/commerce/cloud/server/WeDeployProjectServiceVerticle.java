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

import com.liferay.commerce.cloud.server.service.ProjectService;
import com.liferay.commerce.cloud.server.service.impl.WeDeployProjectServiceImpl;
import com.liferay.commerce.cloud.server.util.CommerceCloudUtil;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Andrea Di Giorgi
 */
public class WeDeployProjectServiceVerticle
	extends BaseServiceVerticle<ProjectService> {

	@Override
	protected Logger getLogger() {
		return _logger;
	}

	@Override
	protected ProjectService getServiceImpl(JsonObject configJsonObject) {
		String host = CommerceCloudUtil.getWeDeployDataServiceHost(
			configJsonObject);
		String token = CommerceCloudUtil.getWeDeployToken(configJsonObject);

		if (_logger.isInfoEnabled()) {
			_logger.info(
				"Using WeDeploy project service implementation pointing to {0}",
				host);
		}

		return new WeDeployProjectServiceImpl(vertx, host, token);
	}

	@Override
	protected Class<ProjectService> getServiceInterface() {
		return ProjectService.class;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		WeDeployProjectServiceVerticle.class);

}