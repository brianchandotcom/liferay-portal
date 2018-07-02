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

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class DefaultSingleDestinationMessageSender
	implements SingleDestinationMessageSender {

	@Override
	public void send(Message message) {
		_messageBus.sendMessage(_destinationName, message);
	}

	@Override
	public void send(Object payload) {
		Message message = new Message();

		message.setPayload(payload);

		send(message);
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	private String _destinationName;
	private MessageBus _messageBus;

}