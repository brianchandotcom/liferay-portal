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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.model.CompanyConstants;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public abstract class BaseMessageListener implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		long companyId = message.getLong("companyId");

		try {
			if (companyId == CompanyConstants.SYSTEM) {
				doReceive(message);
			}
			else {
				doReceive(message, companyId);
			}
		}
		catch (MessageListenerException messageListenerException) {
			throw messageListenerException;
		}
		catch (Exception exception) {
			throw new MessageListenerException(exception);
		}
	}

	protected abstract void doReceive(Message message) throws Exception;

	protected void doReceive(Message message, long companyId) throws Exception {
		doReceive(message);
	}

}