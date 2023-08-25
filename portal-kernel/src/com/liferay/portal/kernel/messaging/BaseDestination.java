/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class BaseDestination implements Destination {

	@Override
	public boolean addDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		return _destinationEventListeners.add(destinationEventListener);
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
		BaseDestination baseDestination = (BaseDestination)destination;

		Set<MessageListener> targetMessageListeners =
			baseDestination.messageListeners;

		targetMessageListeners.addAll(messageListeners);
	}

	@Override
	public void destroy() {
		close(true);

		removeDestinationEventListeners();

		unregisterMessageListeners();
	}

	@Override
	public DestinationStatistics getDestinationStatistics() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDestinationType() {
		return _destinationType;
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

		return false;
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
		MessageListener messageListener, ClassLoader classLoader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classLoader);

		return registerMessageListener(invokerMessageListener);
	}

	@Override
	public boolean removeDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		return _destinationEventListeners.remove(destinationEventListener);
	}

	@Override
	public void removeDestinationEventListeners() {
		_destinationEventListeners.clear();
	}

	@Override
	public void send(Message message) {
		throw new UnsupportedOperationException();
	}

	public void setDestinationType(String destinationType) {
		_destinationType = destinationType;
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
		MessageListener messageListener, ClassLoader classLoader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classLoader);

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

	protected Set<MessageListener> messageListeners = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	protected String name = StringPool.BLANK;

	private final Set<DestinationEventListener> _destinationEventListeners =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private String _destinationType;

}