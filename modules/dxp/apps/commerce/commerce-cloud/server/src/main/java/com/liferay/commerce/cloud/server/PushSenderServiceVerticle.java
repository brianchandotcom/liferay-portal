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

import com.liferay.commerce.cloud.server.service.PushSenderService;
import com.liferay.commerce.cloud.server.service.impl.PushSenderServiceImpl;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Andrea Di Giorgi
 */
public class PushSenderServiceVerticle
	extends BaseServiceVerticle<PushSenderService> {

	@Override
	protected Logger getLogger() {
		return _logger;
	}

	@Override
	protected PushSenderService getServiceImpl(JsonObject configJsonObject) {
		return new PushSenderServiceImpl(vertx);
	}

	@Override
	protected Class<PushSenderService> getServiceInterface() {
		return PushSenderService.class;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		PushSenderServiceVerticle.class);

}