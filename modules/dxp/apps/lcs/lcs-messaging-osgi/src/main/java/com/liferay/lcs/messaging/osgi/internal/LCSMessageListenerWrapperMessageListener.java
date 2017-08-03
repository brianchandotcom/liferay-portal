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

import com.liferay.lcs.messaging.LCSMessageListener;
import com.liferay.lcs.messaging.LCSMessageListenerException;
import com.liferay.lcs.messaging.MessageBusMessage;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

/**
 * @author Riccardo Ferrari
 */
public class LCSMessageListenerWrapperMessageListener
	implements MessageListener {

	public LCSMessageListenerWrapperMessageListener(
		LCSMessageListener lcsMessageListener) {

		_lcsMessageListener = lcsMessageListener;
	}

	public LCSMessageListener getLCSMessageListener() {
		return _lcsMessageListener;
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		try {
			MessageBusMessage messageBusMessage = new MessageBusMessage();

			messageBusMessage.setCreateTime(System.currentTimeMillis());
			messageBusMessage.setDestinationName(message.getDestinationName());
			messageBusMessage.setPayload(message.getPayload());
			messageBusMessage.setResponse((String)message.getResponse());
			messageBusMessage.setResponseDestinationName(
				message.getResponseDestinationName());
			messageBusMessage.setResponseId(message.getResponseId());
			messageBusMessage.setValues(message.getValues());

			_lcsMessageListener.receive(messageBusMessage);
		}
		catch (LCSMessageListenerException lcsmle) {
			throw new MessageListenerException(lcsmle);
		}
	}

	private final LCSMessageListener _lcsMessageListener;

}