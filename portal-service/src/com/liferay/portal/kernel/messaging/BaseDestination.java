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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Set;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseDestination implements Destination {

	@Override
	public void addDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		_destinationEventListeners.add(destinationEventListener);
	}

	public void afterPropertiesSet() {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is null");
		}

		open();
	}

	@Override
	public void close() {
		close(false);
	}

	@Override
	public void close(boolean force) {
	}

	@Override
	public void copyDestinationEventListeners(Destination destination) {
		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destination.addDestinationEventListener(destinationEventListener);
		}
	}

	@Override
	public void copyMessageListeners(Destination destination) {
		for (MessageListener messageListener : messageListeners) {
			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)messageListener;

			destination.register(
				invokerMessageListener.getMessageListener(),
				invokerMessageListener.getClassLoader());
		}
	}

	@Override
	public int getMessageListenerCount() {
		return messageListeners.size();
	}

	@Override
	public Set<MessageListener> getMessageListeners() {
		return Collections.unmodifiableSet(messageListeners);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isRegistered() {
		if (getMessageListenerCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void open() {
	}

	@Override
	public boolean register(MessageListener messageListener) {
		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener);

		return registerMessageListener(invokerMessageListener);
	}

	@Override
	public boolean register(
		MessageListener messageListener, ClassLoader classloader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classloader);

		return registerMessageListener(invokerMessageListener);
	}

	@Override
	public void removeDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		_destinationEventListeners.remove(destinationEventListener);
	}

	@Override
	public void removeDestinationEventListeners() {
		_destinationEventListeners.clear();
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean unregister(MessageListener messageListener) {
		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener);

		return unregisterMessageListener(invokerMessageListener);
	}

	public boolean unregister(
		MessageListener messageListener, ClassLoader classloader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classloader);

		return unregisterMessageListener(invokerMessageListener);
	}

	@Override
	public void unregisterMessageListeners() {
		for (MessageListener messageListener : messageListeners) {
			unregisterMessageListener((InvokerMessageListener)messageListener);
		}
	}

	protected void fireMessageListenerRegisteredEvent(
		MessageListener messageListener) {

		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destinationEventListener.messageListenerRegistered(
				getName(), messageListener);
		}
	}

	protected void fireMessageListenerUnregisteredEvent(
		MessageListener messageListener) {

		for (DestinationEventListener listener : _destinationEventListeners) {
			listener.messageListenerUnregistered(getName(), messageListener);
		}
	}

	protected boolean registerMessageListener(
		InvokerMessageListener invokerMessageListener) {

		boolean registered = messageListeners.add(invokerMessageListener);

		if (registered) {
			fireMessageListenerRegisteredEvent(
				invokerMessageListener.getMessageListener());
		}

		return registered;
	}

	protected boolean unregisterMessageListener(
		InvokerMessageListener invokerMessageListener) {

		boolean unregistered = messageListeners.remove(invokerMessageListener);

		if (unregistered) {
			fireMessageListenerUnregisteredEvent(
				invokerMessageListener.getMessageListener());
		}

		return unregistered;
	}

	protected Set<MessageListener> messageListeners =
		new ConcurrentHashSet<MessageListener>();
	protected String name = StringPool.BLANK;

	private Set<DestinationEventListener> _destinationEventListeners =
		new ConcurrentHashSet<DestinationEventListener>();

}