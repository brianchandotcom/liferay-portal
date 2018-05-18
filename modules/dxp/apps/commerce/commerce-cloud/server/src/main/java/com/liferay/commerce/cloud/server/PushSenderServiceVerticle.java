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