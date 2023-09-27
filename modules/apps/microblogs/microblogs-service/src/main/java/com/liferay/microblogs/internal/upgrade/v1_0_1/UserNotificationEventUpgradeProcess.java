/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.internal.upgrade.v1_0_1;

import com.liferay.microblogs.constants.MicroblogsEntryConstants;
import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Evan Thibodeau
 */
public class UserNotificationEventUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeNotifications();
	}

	private void _updateNotification(
			long userNotificationEventId, JSONObject jsonObject)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update UserNotificationEvent set payload = ? where " +
					"userNotificationEventId = ?")) {

			preparedStatement.setString(1, jsonObject.toString());
			preparedStatement.setLong(2, userNotificationEventId);

			preparedStatement.executeUpdate();
		}
	}

	private void _upgradeNotifications() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select userNotificationEventId, payload from " +
					"UserNotificationEvent where type_ = ?")) {

			preparedStatement.setString(1, MicroblogsPortletKeys.MICROBLOGS);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String payload = resultSet.getString("payload");

					JSONObject payloadJSONObject =
						JSONFactoryUtil.createJSONObject(payload);

					int notificationType = payloadJSONObject.getInt(
						"notificationType");

					if (notificationType != 0) {
						return;
					}

					long userNotificationEventId = resultSet.getLong(
						"userNotificationEventId");

					payloadJSONObject.put(
						"notificationType",
						MicroblogsEntryConstants.NOTIFICATION_TYPE_REPLY);

					_updateNotification(
						userNotificationEventId, payloadJSONObject);
				}
			}
		}
	}

}