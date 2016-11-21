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
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;

/**
 * @author Micha Kiener
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public abstract class BaseProxyBean {

	/**
	 * @deprecated As of 7.0.0, with no direct link
	 */
	@Deprecated
	public void afterPropertiesSet() {
	}

	public void send(ProxyRequest proxyRequest) {
		SingleDestinationMessageSender singleDestinationMessageSender =
			SingleDestinationMessageSenderFactoryUtil.
				createSingleDestinationMessageSender(_destinationName);

		singleDestinationMessageSender.send(buildMessage(proxyRequest));
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setDestinationName)
	 */
	@Deprecated
	public void setSingleDestinationMessageSender(
		SingleDestinationMessageSender singleDestinationMessageSender) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #setSynchronousMessageSenderMode} and {@link
	 *             #setSynchronousDestinationName}
	 */
	@Deprecated
	public void setSingleDestinationSynchronousMessageSender(
		SingleDestinationSynchronousMessageSender
			singleDestinationSynchronousMessageSender) {
	}

	public void setSynchronousDestinationName(
		String synchronousDestinationName) {

		_synchronousDestinationName = synchronousDestinationName;
	}

	public void setSynchronousMessageSenderMode(
		SynchronousMessageSender.Mode synchronousMessageSenderMode) {

		_synchronousMessageSenderMode = synchronousMessageSenderMode;
	}

	public Object synchronousSend(ProxyRequest proxyRequest) throws Exception {
		SingleDestinationSynchronousMessageSender
			singleDestinationSynchronousMessageSender =
				SingleDestinationMessageSenderFactoryUtil.
					createSingleDestinationSynchronousMessageSender(
						_synchronousDestinationName,
						_synchronousMessageSenderMode);

		ProxyResponse proxyResponse =
			(ProxyResponse)singleDestinationSynchronousMessageSender.send(
				buildMessage(proxyRequest));

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

		if (proxyRequest.isLocal()) {
			message.put(MessagingProxy.LOCAL_MESSAGE, Boolean.TRUE);
		}

		return message;
	}

	private String _destinationName;
	private String _synchronousDestinationName;
	private SynchronousMessageSender.Mode _synchronousMessageSenderMode;

}