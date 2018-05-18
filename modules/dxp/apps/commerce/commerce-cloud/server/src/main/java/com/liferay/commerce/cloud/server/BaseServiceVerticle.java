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

		ConfigRetriever.getConfigAsFuture(
			configRetriever
		).compose(
			configJsonObject -> {
				_registerService(configJsonObject);

				startFuture.complete();
			},
			startFuture
		);
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
				"Registered service {0} with {1}",
				serviceInterface.getSimpleName(), clazz.getSimpleName());
		}
	}

	private MessageConsumer<JsonObject> _messageConsumer;
	private ServiceBinder _serviceBinder;

}