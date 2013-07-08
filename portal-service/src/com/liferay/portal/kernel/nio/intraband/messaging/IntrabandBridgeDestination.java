/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
		message.setDestinationName(getName());

		MessageRoutingBag messageRoutingBag =
			(MessageRoutingBag)message.get(
				MessageRoutingBag.MESSAGE_ROUTING_BAG);

		if (messageRoutingBag == null) {
			messageRoutingBag = new MessageRoutingBag(message, true);

			message.put(
				MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);
		}

		sendMessageBag(messageRoutingBag);

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

	public void sendMessageBag(MessageRoutingBag messageRoutingBag) {
		if (SPIUtil.isSPI()) {
			SPI spi = SPIUtil.getSPI();

			try {

				// Record current visiting

				String routingId = _toRoutingId(spi);

				messageRoutingBag.appendRoutingId(routingId);

				// Push to MPI

				if (!messageRoutingBag.isRoutingDowncast()) {
					RegistrationReference registrationReference =
						spi.getRegistrationReference();

					_doSendMessageBag(registrationReference, messageRoutingBag);
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// Push to SPIs

		List<SPI> spis = MPIHelperUtil.getSPIs();

		if (spis.isEmpty() && !SPIUtil.isSPI()) {

			// All spis dead and current process is the root MPI, recover back
			// to original destination

			MessageBusUtil.addDestination(destination);
		}
		else {
			messageRoutingBag.setRoutingDowncast(true);

			try {
				for (SPI spi : spis) {
					String routingId = _toRoutingId(spi);

					if (!messageRoutingBag.isVisited(routingId)) {
						RegistrationReference registrationReference =
							spi.getRegistrationReference();

						_doSendMessageBag(
							registrationReference, messageRoutingBag);
					}
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void _doSendMessageBag(
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

			MessageRoutingBag receiveMessageRoutingBag =
				MessageRoutingBag.fromByteArray(byteBuffer.array());

			Message receivedMessage = receiveMessageRoutingBag.getMessage();

			Message message = messageRoutingBag.getMessage();

			receivedMessage.copyTo(message);

			message.put(
				MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String _toRoutingId(SPI spi) throws RemoteException {
		String spiProviderName = spi.getSPIProviderName();

		SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

		String spiId = spiConfiguration.getSPIId();

		return spiProviderName.concat(StringPool.POUND).concat(spiId);
	}

}