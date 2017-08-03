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

package com.liferay.lcs.messaging.osgi.internal;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = MessageRouterMessageListener.class)
public class LCSMessageRouterMessageListener
	extends BaseMessageListener implements MessageRouterMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		message.put("LCS_DESTINATION", message.getDestinationName());

		message.setDestinationName(_LCS_MESSAGE_BUS_DESTINATION_NAME);

		_messageBus.sendMessage(_LCS_MESSAGE_BUS_DESTINATION_NAME, message);
	}

	private static final String _LCS_MESSAGE_BUS_DESTINATION_NAME =
		"liferay/lcs_message_bus";

	@Reference
	private MessageBus _messageBus;

}