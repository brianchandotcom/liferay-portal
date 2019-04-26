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
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseMultiDestinationProxyBean {

	public void afterPropertiesSet() {
	}

	public abstract String getDestinationName(ProxyRequest proxyRequest);

	public void send(ProxyRequest proxyRequest) {
		MessageBusUtil.sendMessage(
			getDestinationName(proxyRequest), buildMessage(proxyRequest));
	}

	public void setSynchronousMessageSenderMode(
		SynchronousMessageSender.Mode mode) {

		_mode = mode;
	}

	public Object synchronousSend(ProxyRequest proxyRequest) throws Exception {
		Message message = new Message();

		message.setPayload(proxyRequest);

		SynchronousMessageSender synchronousMessageSender =
			_getSynchronousMessageSender();

		ProxyResponse proxyResponse =
			(ProxyResponse)synchronousMessageSender.send(
				getDestinationName(proxyRequest), message);

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

	private SynchronousMessageSender _getSynchronousMessageSender() {
		if (_mode == SynchronousMessageSender.Mode.DEFAULT) {
			return _defaultSynchronousMessageSender;
		}

		return _directSynchronousMessageSender;
	}

	private static volatile SynchronousMessageSender
		_defaultSynchronousMessageSender =
			ServiceProxyFactory.newServiceTrackedInstance(
				SynchronousMessageSender.class,
				BaseMultiDestinationProxyBean.class,
				"_defaultSynchronousMessageSender", "(mode=DEFAULT)", true);
	private static volatile SynchronousMessageSender
		_directSynchronousMessageSender =
			ServiceProxyFactory.newServiceTrackedInstance(
				SynchronousMessageSender.class,
				BaseMultiDestinationProxyBean.class,
				"_directSynchronousMessageSender", "(mode=DIRECT)", true);

	private SynchronousMessageSender.Mode _mode;

}