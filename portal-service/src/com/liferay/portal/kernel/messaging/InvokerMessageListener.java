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

/**
 * @author Michael C. Han
 */
public class InvokerMessageListener implements MessageListener {

	public InvokerMessageListener(MessageListener messageListener) {
		this(messageListener, Thread.currentThread().getContextClassLoader());
	}

	public InvokerMessageListener(
		MessageListener messageListener, ClassLoader classLoader) {

		_messageListener = messageListener;
		_classLoader = classLoader;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof InvokerMessageListener)) {
			return false;
		}

		InvokerMessageListener messageListenerInvoker =
			(InvokerMessageListener)obj;

		return _messageListener.equals(
			messageListenerInvoker.getMessageListener());
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public MessageListener getMessageListener() {
		return _messageListener;
	}

	@Override
	public int hashCode() {
		return _messageListener.hashCode();
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			_messageListener.receive(message);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final ClassLoader _classLoader;
	private final MessageListener _messageListener;

}