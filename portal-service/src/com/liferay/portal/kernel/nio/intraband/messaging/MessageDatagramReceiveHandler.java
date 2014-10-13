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

package com.liferay.portal.kernel.nio.intraband.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.nio.intraband.BaseAsyncDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.nio.ByteBuffer;

import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class MessageDatagramReceiveHandler
	extends BaseAsyncDatagramReceiveHandler {

	public MessageDatagramReceiveHandler(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Override
	protected void doReceive(
			RegistrationReference registrationReference, Datagram datagram)
		throws Exception {

		ByteBuffer byteBuffer = datagram.getDataByteBuffer();

		MessageRoutingBag messageRoutingBag = MessageRoutingBag.fromByteArray(
			byteBuffer.array());

		Destination destination = _messageBus.getDestination(
			messageRoutingBag.getDestinationName());

		if (destination != null) {
			Set<MessageListener> messageListeners =
				destination.getMessageListeners();

			if (destination instanceof IntrabandBridgeDestination) {
				if (messageListeners.isEmpty()) {
					IntrabandBridgeDestination intrabandBridgeDestination =
						(IntrabandBridgeDestination)destination;

					intrabandBridgeDestination.sendMessageRoutingBag(
						messageRoutingBag);
				}
				else {
					destination.send(messageRoutingBag.getMessage());
				}
			}
			else {
				if (!messageListeners.isEmpty()) {
					for (MessageListener messageListener : messageListeners) {
						try {
							messageListener.receive(
								messageRoutingBag.getMessage());
						}
						catch (MessageListenerException mle) {
							throw new MessageBusException(mle);
						}
					}
				}
			}
		}

		if (messageRoutingBag.isSynchronizedBridge()) {
			Intraband intraband = registrationReference.getIntraband();

			intraband.sendDatagram(
				registrationReference,
				Datagram.createResponseDatagram(
					datagram, messageRoutingBag.toByteArray()));
		}
	}

	private final MessageBus _messageBus;

}