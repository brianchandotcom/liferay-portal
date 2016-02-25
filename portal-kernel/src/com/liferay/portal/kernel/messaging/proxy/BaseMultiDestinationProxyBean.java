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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseMultiDestinationProxyBean {

	public void afterPropertiesSet() {
		_synchronousMessageSender =
			SingleDestinationMessageSenderFactoryUtil.
				getSynchronousMessageSender(_mode);
	}

	public abstract String getDestinationName(ProxyRequest proxyRequest);

	public void send(ProxyRequest proxyRequest) {
		MessageBusUtil.sendMessage(
			getDestinationName(proxyRequest), buildMessage(proxyRequest));
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link MessageBusUtil#getMessageBus)
	 */
	@Deprecated
	public void setMessageBus(MessageBus messageBus) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #setSynchronousMessageSenderMode(
	 *             SynchronousMessageSender.Mode)}
	 */
	@Deprecated
	public void setSynchronousMessageSender(
		SynchronousMessageSender synchronousMessageSender) {
	}

	public void setSynchronousMessageSenderMode(
		SynchronousMessageSender.Mode mode) {

		_mode = mode;
	}

	public Object synchronousSend(ProxyRequest proxyRequest) throws Exception {
		ProxyResponse proxyResponse =
			(ProxyResponse)_synchronousMessageSender.send(
				getDestinationName(proxyRequest), buildMessage(proxyRequest));

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

	protected Message buildMessage(ProxyRequest proxyRequest) {
		Message message = new Message();

		message.setPayload(proxyRequest);

		MessageValuesThreadLocal.populateMessageFromThreadLocals(message);

		return message;
	}

	private SynchronousMessageSender.Mode _mode;
	private SynchronousMessageSender _synchronousMessageSender;

}