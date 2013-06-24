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

package com.liferay.portal.messaging.async;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class AsyncMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String responseDestinationName = message.getResponseDestinationName();

		Runnable runnable = (Runnable)message.getPayload();

		try {
			runnable.run();
		}
		catch (RuntimeException re) {
			if (Validator.isNotNull(responseDestinationName)) {
				if (_log.isWarnEnabled()) {
					_log.warn(re, re);
				}
			}
		}

		if (Validator.isNotNull(responseDestinationName)) {
			Message responseMessage = MessageBusUtil.createResponseMessage(
				message);

			MessageBusUtil.sendMessage(
				responseDestinationName, responseMessage);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AsyncMessageListener.class);

}