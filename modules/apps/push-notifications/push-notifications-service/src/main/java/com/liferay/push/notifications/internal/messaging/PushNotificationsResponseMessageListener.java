/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.internal.messaging;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.push.notifications.sender.Response;

/**
 * @author Bruno Farache
 */
public class PushNotificationsResponseMessageListener
	extends BaseMessageListener {

	public PushNotificationsResponseMessageListener(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Response response = (Response)message.getPayload();

		String json = _jsonFactory.serialize(response);

		if (!response.isSucceeded()) {
			_log.error(json);
		}
		else if (_log.isDebugEnabled()) {
			_log.debug(json);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PushNotificationsResponseMessageListener.class);

	private final JSONFactory _jsonFactory;

}