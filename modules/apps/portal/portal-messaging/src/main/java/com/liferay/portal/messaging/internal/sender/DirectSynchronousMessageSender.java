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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.nio.intraband.messaging.IntrabandBridgeDestination;
import com.liferay.portal.messaging.internal.SynchronousDestination;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(property = "mode=DIRECT", service = SynchronousMessageSender.class)
public class DirectSynchronousMessageSender
	implements SynchronousMessageSender {

	@Override
	public Object send(String destinationName, Message message)
		throws MessageBusException {

		Destination destination = _messageBus.getDestination(destinationName);

		if (destination == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Destination " + destinationName + " is not configured");
			}

			return null;
		}

		if (destination instanceof IntrabandBridgeDestination ||
			destination instanceof SynchronousDestination) {

			destination.send(message);
		}
		else {
			Set<MessageListener> messageListeners =
				destination.getMessageListeners();

			for (MessageListener messageListener : messageListeners) {
				try {
					messageListener.receive(message);
				}
				catch (MessageListenerException mle) {
					throw new MessageBusException(mle);
				}
			}
		}

		return message.getResponse();
	}

	@Override
	public Object send(String destinationName, Message message, long timeout)
		throws MessageBusException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				DirectSynchronousMessageSender.class.getName() +
					" does not support timeout");
		}

		return send(destinationName, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DirectSynchronousMessageSender.class);

	@Reference
	private MessageBus _messageBus;

}