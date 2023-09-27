/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.internal.messaging;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.service.PushNotificationsDeviceLocalService;

/**
 * @author Silvio Santos
 * @author Bruno Farache
 */
public class PushNotificationsMessageListener extends BaseMessageListener {

	public PushNotificationsMessageListener(
		PushNotificationsDeviceLocalService
			pushNotificationsDeviceLocalService) {

		_pushNotificationsDeviceLocalService =
			pushNotificationsDeviceLocalService;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject payloadJSONObject = (JSONObject)message.getPayload();

		JSONArray toUserIdsJSONArray = payloadJSONObject.getJSONArray(
			PushNotificationsConstants.KEY_TO_USER_IDS);

		long[] toUserIds = null;

		if (toUserIdsJSONArray != null) {
			toUserIds = new long[toUserIdsJSONArray.length()];

			for (int i = 0; i < toUserIdsJSONArray.length(); i++) {
				toUserIds[i] = toUserIdsJSONArray.getLong(i);
			}

			payloadJSONObject.remove(
				PushNotificationsConstants.KEY_TO_USER_IDS);
		}
		else {
			long userId = payloadJSONObject.getLong(
				PushNotificationsConstants.KEY_USER_ID);

			toUserIds = new long[] {userId};
		}

		try {
			_pushNotificationsDeviceLocalService.sendPushNotification(
				toUserIds, payloadJSONObject);
		}
		catch (Exception exception) {
			_log.error("Unable to send notification", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PushNotificationsMessageListener.class);

	private final PushNotificationsDeviceLocalService
		_pushNotificationsDeviceLocalService;

}