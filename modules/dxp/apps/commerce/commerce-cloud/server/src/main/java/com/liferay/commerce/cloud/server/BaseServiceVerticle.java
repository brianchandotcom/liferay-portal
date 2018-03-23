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

import com.liferay.commerce.cloud.server.util.CommerceCloudUtil;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseServiceVerticle<T> extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		ConfigRetriever configRetriever = ConfigRetriever.create(vertx);

		configRetriever.getConfig(
			asyncResult -> {
				if (asyncResult.failed()) {
					startFuture.fail(asyncResult.cause());

					return;
				}

				_registerService(asyncResult.result());

				startFuture.complete();
			});
	}

	@Override
	public void stop() throws Exception {
		_serviceBinder.unregister(_messageConsumer);
	}

	protected abstract Logger getLogger();

	protected abstract T getServiceImpl(JsonObject configJsonObject);

	protected abstract Class<T> getServiceInterface();

	private void _registerService(JsonObject configJsonObject) {
		_serviceBinder = new ServiceBinder(vertx);

		Class<T> serviceInterface = getServiceInterface();
		T serviceImpl = getServiceImpl(configJsonObject);

		_serviceBinder.setAddress(
			CommerceCloudUtil.getServiceAddress(serviceInterface));

		_serviceBinder.register(serviceInterface, serviceImpl);

		Logger logger = getLogger();

		if (logger.isInfoEnabled()) {
			Class<?> clazz = serviceImpl.getClass();

			logger.info(
				"Registered service {0} with {1}", serviceInterface.getName(),
				clazz.getName());
		}
	}

	private MessageConsumer<JsonObject> _messageConsumer;
	private ServiceBinder _serviceBinder;

}