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
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.nio.ByteBuffer;

import java.rmi.RemoteException;

import java.util.List;
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
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}

		Set<MessageListener> messageListeners =
			destination.getMessageListeners();

		for (MessageListener messageListener : messageListeners) {
			try {
				messageListener.receive(message);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void sendMessageRoutingBag(MessageRoutingBag messageRoutingBag) {
		if (SPIUtil.isSPI()) {
			SPI spi = SPIUtil.getSPI();

			try {
				String routingId = toRoutingId(spi);

				messageRoutingBag.appendRoutingId(routingId);

				if (!messageRoutingBag.isRoutingDowncast()) {
					RegistrationReference registrationReference =
						spi.getRegistrationReference();

					sendMessageRoutingBag(
						registrationReference, messageRoutingBag);
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		List<SPI> spis = MPIHelperUtil.getSPIs();

		if (spis.isEmpty() && !SPIUtil.isSPI()) {
			MessageBusUtil.addDestination(destination);
		}
		else {
			messageRoutingBag.setRoutingDowncast(true);

			try {
				for (SPI spi : spis) {
					String routingId = toRoutingId(spi);

					if (!messageRoutingBag.isVisited(routingId)) {
						RegistrationReference registrationReference =
							spi.getRegistrationReference();

						sendMessageRoutingBag(
							registrationReference, messageRoutingBag);
					}
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected void sendMessageRoutingBag(
		RegistrationReference registrationReference,
		MessageRoutingBag messageRoutingBag) {

		try {
			Intraband intraband = registrationReference.getIntraband();

			Datagram datagram = intraband.sendSyncDatagram(
				registrationReference,
				Datagram.createRequestDatagram(
					SystemDataType.MESSAGE.getValue(),
					messageRoutingBag.toByteArray()));

			ByteBuffer byteBuffer = datagram.getDataByteBuffer();

			MessageRoutingBag receivedMessageRoutingBag =
				MessageRoutingBag.fromByteArray(byteBuffer.array());

			Message receivedMessage = receivedMessageRoutingBag.getMessage();

			Message message = messageRoutingBag.getMessage();

			receivedMessage.copyTo(message);

			message.put(
				MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String toRoutingId(SPI spi) throws RemoteException {
		String spiProviderName = spi.getSPIProviderName();

		SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

		String spiId = spiConfiguration.getSPIId();

		return spiProviderName.concat(StringPool.POUND).concat(spiId);
	}

}