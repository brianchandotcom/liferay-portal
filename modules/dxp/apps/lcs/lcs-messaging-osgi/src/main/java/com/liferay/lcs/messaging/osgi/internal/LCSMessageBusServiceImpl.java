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

package com.liferay.lcs.messaging.osgi.internal;

import com.liferay.lcs.messaging.bus.LCSMessageBusService;
import com.liferay.lcs.messaging.bus.LCSMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = LCSMessageBusService.class)
public class LCSMessageBusServiceImpl implements LCSMessageBusService {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(destination.name=*)", unbind = "unregisterLCSMessageListener"
	)
	public synchronized void registerLCSMessageListener(
		LCSMessageListener lcsMessageListener, Map<String, Object> properties) {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			ClassLoader operatingClassLoader = (ClassLoader)properties.get(
				"message.listener.operating.class.loader");

			if (operatingClassLoader != null) {
				currentThread.setContextClassLoader(operatingClassLoader);
			}

			String destinationName = MapUtil.getString(
				properties, "destination.name");

			registerLCSMessageListener(destinationName, lcsMessageListener);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public boolean registerLCSMessageListener(
		String destinationName, LCSMessageListener lcsMessageListener) {

		_destinationResolver.resolveLocalDestination(destinationName);

		LCSMessageListenerWrapperMessageListener
			lcsMessageListenerWrapperMessageListener =
				new LCSMessageListenerWrapperMessageListener(
					lcsMessageListener);

		return _messageBus.registerMessageListener(
			destinationName, lcsMessageListenerWrapperMessageListener);
	}

	@Override
	public void sendMessage(
		String destinationName, Map<String, String> metadata, String payload,
		String responseDestinationName) {

		_destinationResolver.resolveRemoteDestination(destinationName);

		Message message = new Message();

		message.setDestinationName(destinationName);
		message.setPayload(payload);
		message.setResponseDestinationName(responseDestinationName);

		if (metadata != null) {
			Map<String, Object> values = new HashMap<>();

			values.putAll(metadata);

			message.setValues(values);
		}

		_messageBus.sendMessage(message.getDestinationName(), message);
	}

	@Override
	public void sendMessage(String destinationName, String payload) {
		sendMessage(destinationName, null, payload, null);
	}

	public synchronized void unregisterLCSMessageListener(
		LCSMessageListener lcsMessageListener, Map<String, Object> properties) {

		String destinationName = MapUtil.getString(
			properties, "destination.name");

		unregisterLCSMessageListener(destinationName, lcsMessageListener);
	}

	@Override
	public boolean unregisterLCSMessageListener(
		String destinationName, LCSMessageListener lcsMessageListener) {

		Destination destination = _messageBus.getDestination(destinationName);

		if (destination == null) {
			return false;
		}

		for (MessageListener messageListener :
				destination.getMessageListeners()) {

			if (messageListener instanceof
					LCSMessageListenerWrapperMessageListener) {

				LCSMessageListenerWrapperMessageListener
					lcsMessageListenerWrapperMessageListener =
						(LCSMessageListenerWrapperMessageListener)
							messageListener;

				if (lcsMessageListener.equals(
						lcsMessageListenerWrapperMessageListener.
							getLCSMessageListener())) {

					return _messageBus.unregisterMessageListener(
						destinationName,
						lcsMessageListenerWrapperMessageListener);
				}
			}
		}

		return false;
	}

	@Reference
	private DestinationResolver _destinationResolver;

	@Reference
	private MessageBus _messageBus;

}