/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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