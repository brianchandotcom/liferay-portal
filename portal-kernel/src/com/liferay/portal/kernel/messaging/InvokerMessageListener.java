/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InvokerMessageListener)) {
			return false;
		}

		InvokerMessageListener messageListenerInvoker =
			(InvokerMessageListener)object;

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

		if (_classLoader != null) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			_messageListener.receive(message);
		}
		finally {
			if (_classLoader != null) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@Override
	public String toString() {
		return _messageListener.toString();
	}

	private final ClassLoader _classLoader;
	private final MessageListener _messageListener;

}