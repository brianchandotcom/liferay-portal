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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;

import java.util.Map;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseMultiDestinationProxyBean {

	public abstract String getDestinationName(ProxyRequest proxyRequest);

	public void send(ProxyRequest proxyRequest) {
		_messageSender.send(
			getDestinationName(proxyRequest), createMessage(proxyRequest));
	}

	public void setMessageSender(MessageSender messageSender) {
		_messageSender = messageSender;
	}

	public void setSynchronousMessageSender(
		SynchronousMessageSender synchronousMessageSender) {

		_synchronousMessageSender = synchronousMessageSender;
	}

	public Object synchronousSend(ProxyRequest proxyRequest) throws Exception {
		ProxyResponse proxyResponse =
			(ProxyResponse)_synchronousMessageSender.send(
				getDestinationName(proxyRequest), createMessage(proxyRequest));

		if (proxyResponse == null) {
			return proxyRequest.execute(this);
		}
		else if (proxyResponse.hasError()) {
			throw proxyResponse.getException();
		}
		else {
			return proxyResponse.getResult();
		}
	}

	protected Message createMessage(ProxyRequest proxyRequest) {
		Message message = new Message();

		message.setPayload(proxyRequest);

		Map<String, Object> messageValues =
			MessageValuesThreadLocal.getValues();

		if (!messageValues.isEmpty()) {
			for (String key : messageValues.keySet()) {
				message.put(key, messageValues.get(key));
			}
		}

		return message;
	}

	private MessageSender _messageSender;
	private SynchronousMessageSender _synchronousMessageSender;

}