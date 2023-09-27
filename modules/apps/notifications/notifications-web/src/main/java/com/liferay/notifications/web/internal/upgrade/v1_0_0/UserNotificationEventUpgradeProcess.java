/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Sergio González
 */
public class UserNotificationEventUpgradeProcess extends UpgradeProcess {

	public UserNotificationEventUpgradeProcess(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateUserNotificationEvents();
	}

	private void _updateUserNotificationEvents() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<UserNotificationEvent> userNotificationEvents =
				_userNotificationEventLocalService.getTypeNotificationEvents(
					"6_WAR_soportlet");

			for (UserNotificationEvent userNotificationEvent :
					userNotificationEvents) {

				JSONObject payloadJSONObject = JSONFactoryUtil.createJSONObject(
					userNotificationEvent.getPayload());

				String type = payloadJSONObject.getString("portletId");

				if (Validator.isNull(type)) {
					return;
				}

				payloadJSONObject.remove("portletId");

				long entryId = payloadJSONObject.getLong("entryId");

				if (entryId > 0) {
					payloadJSONObject.put("classPK", entryId);

					payloadJSONObject.remove("entryId");
				}
				else if (type.equals("1_WAR_contactsportlet")) {
					long socialRequestId = payloadJSONObject.getLong(
						"requestId");

					if (socialRequestId > 0) {
						payloadJSONObject.put("classPK", socialRequestId);

						payloadJSONObject.remove("socialRequestId");
					}
				}
				else if (type.equals("2_WAR_soportlet")) {
					long memberRequestId = payloadJSONObject.getLong(
						"memberRequestId");

					if (memberRequestId > 0) {
						payloadJSONObject.put("classPK", memberRequestId);

						payloadJSONObject.remove("memberRequestId");
					}
				}

				userNotificationEvent.setType(type);
				userNotificationEvent.setPayload(payloadJSONObject.toString());

				_userNotificationEventLocalService.updateUserNotificationEvent(
					userNotificationEvent);
			}
		}
	}

	private final UserNotificationEventLocalService
		_userNotificationEventLocalService;

}