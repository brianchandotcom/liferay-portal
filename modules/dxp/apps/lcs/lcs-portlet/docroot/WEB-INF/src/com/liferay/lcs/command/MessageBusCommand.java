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

package com.liferay.lcs.command;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.MessageBusCommandMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

/**
 * @author Riccardo Ferrari
 */
public class MessageBusCommand implements Command {

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		MessageBusCommandMessage messageBusCommandMessage =
			(MessageBusCommandMessage)commandMessage;

		String destinationName = messageBusCommandMessage.getDestinationName();

		Message message = new Message();

		message.setDestinationName(destinationName);
		message.setPayload(messageBusCommandMessage.getPayload());
		message.setResponse(messageBusCommandMessage.getResponse());
		message.setResponseDestinationName(
			messageBusCommandMessage.getResponseDestinationName());
		message.setResponseId(messageBusCommandMessage.getResponseId());
		message.setValues(messageBusCommandMessage.getValues());

		MessageBusUtil.sendMessage(destinationName, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageBusCommand.class);

}