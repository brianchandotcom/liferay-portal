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
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class DefaultSingleDestinationSynchronousMessageSender
	implements SingleDestinationSynchronousMessageSender {

	@Override
	public Object send(Message message) throws MessageBusException {
		return _synchronousMessageSender.send(_destinationName, message);
	}

	@Override
	public Object send(Message message, long timeout)
		throws MessageBusException {

		return _synchronousMessageSender.send(
			_destinationName, message, timeout);
	}

	@Override
	public Object send(Object payload) throws MessageBusException {
		Message message = new Message();

		message.setPayload(payload);

		return send(message);
	}

	@Override
	public Object send(Object payload, long timeout)
		throws MessageBusException {

		Message message = new Message();

		message.setPayload(payload);

		return send(message, timeout);
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setSynchronousMessageSender(
		SynchronousMessageSender synchronousMessageSender) {

		_synchronousMessageSender = synchronousMessageSender;
	}

	private String _destinationName;
	private SynchronousMessageSender _synchronousMessageSender;

}