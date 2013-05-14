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

import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Michael C. Han
 */
public class DefaultSingleDestinationMessageSender
	implements SingleDestinationMessageSender {

	public DefaultSingleDestinationMessageSender() {
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public DefaultSingleDestinationMessageSender(
		String destinationName, MessageSender messageSender) {

		_destinationName = destinationName;
		_messageSender = messageSender;
	}

	@Override
	public void send(Message message) {
		_messageSender.send(_destinationName, message);
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

	public void setMessageSender(MessageSender messageSender) {
		_messageSender = messageSender;
	}

	private String _destinationName;
	private MessageSender _messageSender;

}