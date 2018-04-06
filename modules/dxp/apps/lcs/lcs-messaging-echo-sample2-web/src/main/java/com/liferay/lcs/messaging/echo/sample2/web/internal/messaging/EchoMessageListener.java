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

package com.liferay.lcs.messaging.echo.sample2.web.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true, property = "destination.name=lcs_echo",
	service = MessageListener.class
)
public class EchoMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Received message:" + message);
		}

		String payload = (String)message.getPayload();

		if (_log.isInfoEnabled()) {
			_log.info("Message payload: " + payload);
		}

		String responseDestinationName = message.getResponseDestinationName();

		if ((responseDestinationName != null) &&
			(responseDestinationName.length() > 0)) {

			Message responseMessage = MessageBusUtil.createResponseMessage(
				message);

			StringBuilder sb = new StringBuilder(payload);

			sb.reverse();

			responseMessage.setPayload(sb.toString());

			MessageBusUtil.sendMessage(
				responseDestinationName, responseMessage);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EchoMessageListener.class);

}