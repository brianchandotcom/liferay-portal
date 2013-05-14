/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging.config;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.security.pacl.permission.PortalMessageBusPermission;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class AbstractMessagingConfigurator
	implements MessagingConfigurator {

	public void afterPropertiesSet() {
		MessageBus messageBus = getMessageBus();

		for (DestinationEventListener destinationEventListener :
				_globalDestinationEventListeners) {

			messageBus.addDestinationEventListener(destinationEventListener);
		}

		for (Destination destination : _destinations) {
			messageBus.addDestination(destination);
		}

		for (Map.Entry<String, List<DestinationEventListener>>
				destinationEventListeners :
					_specificDestinationEventListeners.entrySet()) {

			String destinationName = destinationEventListeners.getKey();

			for (DestinationEventListener destinationEventListener :
					destinationEventListeners.getValue()) {

				messageBus.addDestinationEventListener(
					destinationName, destinationEventListener);
			}
		}

		for (Destination destination : _replacementDestinations) {
			messageBus.replace(destination);
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			ClassLoader operatingClassLoader = getOperatingClassloader();

			currentThread.setContextClassLoader(operatingClassLoader);

			for (Map.Entry<String, List<MessageListener>> messageListeners :
					_messageListeners.entrySet()) {

				String destinationName = messageListeners.getKey();

				for (MessageListener messageListener :
						messageListeners.getValue()) {

					messageBus.registerMessageListener(
						destinationName, messageListener);
				}
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void destroy() {
		MessageBus messageBus = getMessageBus();

		for (Map.Entry<String, List<MessageListener>> messageListeners :
				_messageListeners.entrySet()) {

			String destinationName = messageListeners.getKey();

			for (MessageListener messageListener :
					messageListeners.getValue()) {

				messageBus.unregisterMessageListener(
					destinationName, messageListener);
			}
		}

		for (Destination destination : _destinations) {
			messageBus.removeDestination(destination.getName());

			destination.close();
		}

		for (Map.Entry<String, List<DestinationEventListener>>
				destinationEventListeners :
					_specificDestinationEventListeners.entrySet()) {

			String destinationName = destinationEventListeners.getKey();

			for (DestinationEventListener destinationEventListener :
					destinationEventListeners.getValue()) {

				messageBus.removeDestinationEventListener(
					destinationName, destinationEventListener);
			}
		}

		for (DestinationEventListener destinationEventListener :
				_globalDestinationEventListeners) {

			messageBus.removeDestinationEventListener(destinationEventListener);
		}
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #afterPropertiesSet}
	 */
	@Override
	public void init() {
		afterPropertiesSet();
	}

	@Override
	public void setDestinations(List<Destination> destinations) {
		for (Destination destination : destinations) {
			try {
				PortalMessageBusPermission.checkListen(destination.getName());
			}
			catch (SecurityException se) {
				if (_log.isInfoEnabled()) {
					_log.info("Rejecting destination " + destination.getName());
				}

				continue;
			}

			_destinations.add(destination);
		}
	}

	@Override
	public void setGlobalDestinationEventListeners(
		List<DestinationEventListener> globalDestinationEventListeners) {

		_globalDestinationEventListeners = globalDestinationEventListeners;
	}

	@Override
	public void setMessageListeners(
		Map<String, List<MessageListener>> messageListeners) {

		_messageListeners = messageListeners;

		for (List<MessageListener> messageListenersList :
				_messageListeners.values()) {

			for (MessageListener messageListener : messageListenersList) {
				Class<?> messageListenerClass = messageListener.getClass();

				try {
					Method setMessageBusMethod = messageListenerClass.getMethod(
						"setMessageBus", MessageBus.class);

					setMessageBusMethod.setAccessible(true);

					setMessageBusMethod.invoke(
						messageListener, getMessageBus());

					continue;
				}
				catch (Exception e) {
				}

				try {
					Method setMessageBusMethod =
						messageListenerClass.getDeclaredMethod(
							"setMessageBus", MessageBus.class);

					setMessageBusMethod.setAccessible(true);

					setMessageBusMethod.invoke(
						messageListener, getMessageBus());
				}
				catch (Exception e) {
				}
			}
		}
	}

	@Override
	public void setReplacementDestinations(
		List<Destination> replacementDestinations) {

		_replacementDestinations = replacementDestinations;
	}

	@Override
	public void setSpecificDestinationEventListener(
		Map<String, List<DestinationEventListener>>
			specificDestinationEventListeners) {

		_specificDestinationEventListeners = specificDestinationEventListeners;
	}

	protected abstract MessageBus getMessageBus();

	protected abstract ClassLoader getOperatingClassloader();

	private static Log _log = LogFactoryUtil.getLog(
		AbstractMessagingConfigurator.class);

	private List<Destination> _destinations = new ArrayList<Destination>();
	private List<DestinationEventListener> _globalDestinationEventListeners =
		new ArrayList<DestinationEventListener>();
	private Map<String, List<MessageListener>> _messageListeners =
		new HashMap<String, List<MessageListener>>();
	private List<Destination> _replacementDestinations =
		new ArrayList<Destination>();
	private Map<String, List<DestinationEventListener>>
		_specificDestinationEventListeners =
			new HashMap<String, List<DestinationEventListener>>();

}