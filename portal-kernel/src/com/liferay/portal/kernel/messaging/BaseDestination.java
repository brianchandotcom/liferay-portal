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
	public void close() {
		close(false);
	}

	@Override
	public void close(boolean force) {
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

		messageListeners.clear();
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

	protected boolean registerMessageListener(
		InvokerMessageListener invokerMessageListener) {

		return messageListeners.add(invokerMessageListener);
	}

	protected boolean unregisterMessageListener(
		InvokerMessageListener invokerMessageListener) {

		return messageListeners.remove(invokerMessageListener);
	}

	protected Set<MessageListener> messageListeners = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	protected String name = StringPool.BLANK;

	private String _destinationType;

}