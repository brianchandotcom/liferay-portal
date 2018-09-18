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

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.UUID;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = {"mode=DEFAULT", "timeout=10000"},
	service = SynchronousMessageSender.class
)
public class DefaultSynchronousMessageSender
	implements SynchronousMessageSender {

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

		String responseId = generateUUID();

		message.setResponseId(responseId);

		SynchronousMessageListener synchronousMessageListener =
			new SynchronousMessageListener(
				_messageBus, message, timeout, _entityCache, _finderCache);

		return synchronousMessageListener.send();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_timeout = GetterUtil.getLong(properties.get("timeout"), 10000);
	}

	protected String generateUUID() {
		UUID uuid = new UUID(
			SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

		return uuid.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultSynchronousMessageSender.class);

	@Reference
	private EntityCache _entityCache;

	@Reference
	private FinderCache _finderCache;

	@Reference
	private MessageBus _messageBus;

	private long _timeout;

}