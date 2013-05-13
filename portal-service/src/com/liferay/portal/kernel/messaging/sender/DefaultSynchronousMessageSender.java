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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

/**
 * @author Michael C. Han
 */
public class DefaultSynchronousMessageSender
	implements SynchronousMessageSender {

	public DefaultSynchronousMessageSender() {
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public DefaultSynchronousMessageSender(
		MessageBus messageBus, PortalUUID portalUUID, long timeout) {

		_messageBus = messageBus;
		_portalUUID = portalUUID;
		_timeout = timeout;
	}

	@Override
	public Object send(String destinationName, Message message)
		throws MessageBusException {

		return send(destinationName, message, _timeout);
	}

	@Override
	public Object send(String destinationName, Message message, long timeout)
		throws MessageBusException {

		Destination destination = _messageBus.getDestination(destinationName);

		if (destination == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Destination " + destinationName + " is not configured");
			}

			return null;
		}

		if (destination.getMessageListenerCount() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Destination " + destinationName +
						" does not have any message listeners");
			}

			return null;
		}

		message.setDestinationName(destinationName);

		String responseDestinationName = message.getResponseDestinationName();

		// Create a temporary destination if no response destination is
		// configured

		if (Validator.isNull(responseDestinationName) ||
			!_messageBus.hasDestination(responseDestinationName)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Response destination " + responseDestinationName +
						" is not configured");
			}

			message.setResponseDestinationName(
				DestinationNames.MESSAGE_BUS_DEFAULT_RESPONSE);
		}

		String responseId = _portalUUID.generate();

		message.setResponseId(responseId);

		SynchronousMessageListener synchronousMessageListener =
			new SynchronousMessageListener(_messageBus, message, timeout);

		return synchronousMessageListener.send();
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public void setPortalUUID(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	public void setTimeout(long timeout) {
		_timeout = timeout;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultSynchronousMessageSender.class);

	private MessageBus _messageBus;
	private PortalUUID _portalUUID;
	private long _timeout;

}