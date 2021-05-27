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

package com.liferay.push.notifications.sender.apple.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.Response;
import com.liferay.push.notifications.sender.apple.internal.AppleResponse;

/**
 * @author Bruno Farache
 */
public class ApplePushNotificationsResponseMessageListener
	extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		Response response = (Response)message.getPayload();

		if (response.isSucceeded() || !(response instanceof AppleResponse)) {
			return;
		}

		throw new PushNotificationsException(response.getStatus());
	}

}