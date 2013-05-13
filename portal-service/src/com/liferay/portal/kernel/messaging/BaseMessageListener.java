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

package com.liferay.portal.kernel.messaging;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public abstract class BaseMessageListener implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		try {
			doReceive(message);
		}
		catch (MessageListenerException mle) {
			throw mle;
		}
		catch (Exception e) {
			throw new MessageListenerException(e);
		}
	}

	protected abstract void doReceive(Message message) throws Exception;

}