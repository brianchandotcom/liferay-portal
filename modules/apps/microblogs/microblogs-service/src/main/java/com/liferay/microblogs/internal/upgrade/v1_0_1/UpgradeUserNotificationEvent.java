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

package com.liferay.microblogs.internal.upgrade.v1_0_1;

import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Evan Thibodeau
 */
public class UpgradeUserNotificationEvent extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeNotifications();
	}

	protected void updateNotification(
			long userNotificationEventId, JSONObject jsonObject)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update UserNotificationEvent set payload = ? where " +
					"userNotificationEventId = ?")) {

			ps.setString(1, jsonObject.toString());
			ps.setLong(2, userNotificationEventId);

			ps.executeUpdate();
		}
	}

	protected void upgradeNotifications() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select userNotificationEventId, payload from " +
					"UserNotificationEvent where type_ = ?")) {

			ps.setString(1, MicroblogsPortletKeys.MICROBLOGS);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String payload = rs.getString("payload");

					JSONObject payloadJSONObject =
						JSONFactoryUtil.createJSONObject(payload);

					int notificationType = payloadJSONObject.getInt(
						"notificationType");

					if (notificationType != 0) {
						return;
					}

					long userNotificationEventId = rs.getLong(
						"userNotificationEventId");

					payloadJSONObject.put(
						"notificationType",
						MicroblogsEntryConstants.NOTIFICATION_TYPE_REPLY);

					updateNotification(
						userNotificationEventId, payloadJSONObject);
				}
			}
		}
	}

}