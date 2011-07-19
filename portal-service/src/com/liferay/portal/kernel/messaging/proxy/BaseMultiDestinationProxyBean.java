/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;

/**
 * @author Michael C. Han
 */
public abstract class BaseMultiDestinationProxyBean {

	public abstract String getDestinationName(ProxyRequest proxyRequest);

	public MessageSender getMessageSender() {
		return _messageSender;
	}

	public SynchronousMessageSender getSynchronousMessageSender() {
		return _synchronousMessageSender;
	}

	public void setMessageSender(MessageSender messageSender) {
		_messageSender = messageSender;
	}

	public void setSynchronousMessageSender(
		SynchronousMessageSender synchronousMessageSender) {

		_synchronousMessageSender = synchronousMessageSender;
	}

	private MessageSender _messageSender;
	private SynchronousMessageSender _synchronousMessageSender;

}