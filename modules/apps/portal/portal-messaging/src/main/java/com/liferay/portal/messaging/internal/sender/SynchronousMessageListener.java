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

package com.liferay.portal.messaging.internal.sender;

import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael C. Han
 */
public class SynchronousMessageListener implements MessageListener {

	public SynchronousMessageListener(
		MessageBus messageBus, Message message, long timeout,
		EntityCache entityCache, FinderCache finderCache) {

		_messageBus = messageBus;
		_message = message;
		_timeout = timeout;
		_entityCache = entityCache;
		_finderCache = finderCache;

		_responseId = _message.getResponseId();
	}

	public Object getResults() {
		return _results;
	}

	@Override
	public void receive(Message message) {
		String responseId = message.getResponseId();

		if (!responseId.equals(_responseId)) {
			return;
		}

		_results = message.getPayload();

		_countDownLatch.countDown();
	}

	public Object send() throws MessageBusException {
		String destinationName = _message.getDestinationName();

		String responseDestinationName = _message.getResponseDestinationName();

		_messageBus.registerMessageListener(responseDestinationName, this);

		try {
			_messageBus.sendMessage(destinationName, _message);

			_countDownLatch.await(_timeout, TimeUnit.MILLISECONDS);

			if (_results == null) {
				throw new MessageBusException(
					"No reply received for message: " + _message);
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

			_entityCache.clearLocalCache();
			_finderCache.clearLocalCache();
			ThreadLocalCacheManager.destroy();
		}
	}

	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private final EntityCache _entityCache;
	private final FinderCache _finderCache;
	private final Message _message;
	private final MessageBus _messageBus;
	private final String _responseId;
	private Object _results;
	private final long _timeout;

}