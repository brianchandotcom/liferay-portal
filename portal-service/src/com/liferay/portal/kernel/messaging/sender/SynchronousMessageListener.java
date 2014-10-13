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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * @author Michael C. Han
 */
public class SynchronousMessageListener implements MessageListener {

	public SynchronousMessageListener(
		MessageBus messageBus, Message message, long timeout) {

		_messageBus = messageBus;
		_message = message;
		_timeout = timeout;
		_responseId = _message.getResponseId();
	}

	public Object getResults() {
		return _results;
	}

	@Override
	public void receive(Message message) {
		if (!message.getResponseId().equals(_responseId)) {
			return;
		}

		synchronized (this) {
			_results = message.getPayload();

			notify();
		}
	}

	public Object send() throws MessageBusException {
		String destinationName = _message.getDestinationName();
		String responseDestinationName = _message.getResponseDestinationName();

		_messageBus.registerMessageListener(responseDestinationName, this);

		try {
			synchronized (this) {
				_messageBus.sendMessage(destinationName, _message);

				wait(_timeout);

				if (_results == null) {
					throw new MessageBusException(
						"No reply received for message: " + _message);
				}
			}

			return _results;
		}
		catch (InterruptedException ie) {
			throw new MessageBusException(
				"Message sending interrupted for: " + _message, ie);
		}
		finally {
			_messageBus.unregisterMessageListener(
				responseDestinationName, this);

			EntityCacheUtil.clearLocalCache();
			FinderCacheUtil.clearLocalCache();
			ThreadLocalCacheManager.destroy();
		}
	}

	private final Message _message;
	private final MessageBus _messageBus;
	private final String _responseId;
	private Object _results;
	private final long _timeout;

}