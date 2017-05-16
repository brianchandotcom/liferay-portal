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

package com.liferay.lcs.messaging.echo.sample1.messaging;

import com.liferay.lcs.messaging.LCSMessageBusService;
import com.liferay.lcs.messaging.LCSMessageListener;
import com.liferay.lcs.messaging.LCSMessageListenerException;
import com.liferay.lcs.messaging.MessageBusMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Ferrari
 */
public class EchoLCSMessageListener implements LCSMessageListener {

	public EchoLCSMessageListener(LCSMessageBusService lcsMessageBusService) {
		_lcsMessageBusService = lcsMessageBusService;
	}

	@Override
	public void receive(MessageBusMessage messageBusMessage)
		throws LCSMessageListenerException {

		if (_log.isInfoEnabled()) {
			_log.info("Received message:" + messageBusMessage);
		}

		String payload = (String)messageBusMessage.getPayload();

		if (_log.isInfoEnabled()) {
			_log.info("Message payload: " + payload);
		}

		String responseDestinationName =
			messageBusMessage.getResponseDestinationName();

		if (Validator.isNotNull(responseDestinationName)) {
			MessageBusMessage responseMessageBusMessage =
				new MessageBusMessage();

			responseMessageBusMessage.setDestinationName(
				responseDestinationName);
			responseMessageBusMessage.setResponseId(
				messageBusMessage.getResponseId());

			StringBuilder sb = new StringBuilder(payload);

			sb.reverse();

			responseMessageBusMessage.setPayload(sb.toString());

			_lcsMessageBusService.sendMessage(
				responseDestinationName, responseMessageBusMessage);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EchoLCSMessageListener.class);

	private final LCSMessageBusService _lcsMessageBusService;

}