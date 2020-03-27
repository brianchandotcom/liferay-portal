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
import com.liferay.portal.kernel.messaging.DestinationWrapper;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.resiliency.spi.SPI;

import java.rmi.RemoteException;

import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class IntrabandBridgeDestination extends DestinationWrapper {

	public IntrabandBridgeDestination(Destination destination) {
		super(destination);
	}

	@Override
	public void send(Message message) {
		if (message.getBoolean(MessagingProxy.LOCAL_MESSAGE)) {
			destination.send(message);

			return;
		}

		message.setDestinationName(getName());

		MessageRoutingBag messageRoutingBag = (MessageRoutingBag)message.get(
			MessageRoutingBag.MESSAGE_ROUTING_BAG);

		if (messageRoutingBag == null) {
			messageRoutingBag = new MessageRoutingBag(message, true);

			message.put(
				MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);
		}

		sendMessageRoutingBag(messageRoutingBag);

		try {
			Message responseMessage = messageRoutingBag.getMessage();

			responseMessage.copyTo(message);

			messageRoutingBag.setMessage(message);
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}

		Set<MessageListener> messageListeners =
			destination.getMessageListeners();

		for (MessageListener messageListener : messageListeners) {
			try {
				messageListener.receive(message);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	public void sendMessageRoutingBag(MessageRoutingBag messageRoutingBag) {
		MessageBusUtil.addDestination(destination);
	}

	protected void sendMessageRoutingBag(
		RegistrationReference registrationReference,
		MessageRoutingBag messageRoutingBag) {
	}

	protected String toRoutingId(SPI spi) throws RemoteException {
		return null;
	}

}