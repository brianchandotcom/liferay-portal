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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class DefaultMessageBus implements MessageBus {

	@Override
	public synchronized void addDestination(Destination destination) {
		_destinations.put(destination.getName(), destination);

		for (MessageBusEventListener messageBusEventListener :
				_messageBusEventListeners) {

			messageBusEventListener.destinationAdded(destination);
		}
	}

	@Override
	public boolean addMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		return _messageBusEventListeners.add(messageBusEventListener);
	}

	@Override
	public Destination getDestination(String destinationName) {
		return _destinations.get(destinationName);
	}

	@Override
	public int getDestinationCount() {
		return _destinations.size();
	}

	@Override
	public Collection<String> getDestinationNames() {
		return _destinations.keySet();
	}

	@Override
	public Collection<Destination> getDestinations() {
		return _destinations.values();
	}

	@Override
	public boolean hasDestination(String destinationName) {
		return _destinations.containsKey(destinationName);
	}

	@Override
	public boolean hasMessageListener(String destinationName) {
		Destination destination = _destinations.get(destinationName);

		if ((destination != null) && destination.isRegistered()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public synchronized boolean registerMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			throw new IllegalStateException(
				"Destination " + destinationName + " is not configured");
		}

		return destination.register(messageListener);
	}

	@Override
	public synchronized Destination removeDestination(String destinationName) {
		Destination destination = _destinations.remove(destinationName);

		if (destination != null) {
			destination.removeDestinationEventListeners();
			destination.unregisterMessageListeners();

			for (MessageBusEventListener messageBusEventListener :
					_messageBusEventListeners) {

				messageBusEventListener.destinationRemoved(destination);
			}
		}

		return destination;
	}

	@Override
	public boolean removeMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		return _messageBusEventListeners.remove(messageBusEventListener);
	}

	@Override
	public void replace(Destination destination) {
		Destination oldDestination = _destinations.get(destination.getName());

		oldDestination.copyDestinationEventListeners(destination);
		oldDestination.copyMessageListeners(destination);

		removeDestination(oldDestination.getName());

		addDestination(destination);
	}

	@Override
	public void sendMessage(String destinationName, Message message) {
		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Destination " + destinationName + " is not configured");
			}

			return;
		}

		message.setDestinationName(destinationName);

		destination.send(message);
	}

	@Override
	public void shutdown() {
		shutdown(false);
	}

	@Override
	public synchronized void shutdown(boolean force) {
		for (Destination destination : _destinations.values()) {
			destination.close(force);
		}
	}

	@Override
	public synchronized boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			return false;
		}

		return destination.unregister(messageListener);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultMessageBus.class);

	private final Map<String, Destination> _destinations = new HashMap<>();
	private final Set<MessageBusEventListener> _messageBusEventListeners =
		new ConcurrentHashSet<>();

}