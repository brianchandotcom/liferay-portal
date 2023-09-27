/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.upgrade.v2_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Roberto Díaz
 */
public class UserNotificationEventUpgradeProcess extends UpgradeProcess {

	public UserNotificationEventUpgradeProcess(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("Notifications_UserNotificationEvent")) {
			_updateUserNotificationEventActionRequired();
		}

		_updateUserNotificationEvents();
	}

	private void _updateUserNotificationEventActionRequired() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				StringBundler.concat(
					"update UserNotificationEvent set actionRequired = ",
					"[$TRUE$] where userNotificationEventId in (select ",
					"userNotificationEventId from ",
					"Notifications_UserNotificationEvent where actionRequired ",
					"= [$TRUE$])"));

			runSQL(
				"update UserNotificationEvent set actionRequired = [$FALSE$] " +
					"where actionRequired IS NULL");
		}
	}

	private void _updateUserNotificationEvents() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select userNotificationEventId, payload, actionRequired " +
					"from UserNotificationEvent where payload like " +
						"'%actionRequired%'");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update UserNotificationEvent set payload = ?, " +
						"actionRequired = ? where userNotificationEventId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			runSQL("update UserNotificationEvent set delivered = [$TRUE$]");

			runSQL(
				StringBundler.concat(
					"update UserNotificationEvent set deliveryType = ",
					UserNotificationDeliveryConstants.TYPE_WEBSITE,
					" where deliveryType = 0 or deliveryType is null"));

			while (resultSet.next()) {
				long userNotificationEventId = resultSet.getLong(
					"userNotificationEventId");
				String payload = resultSet.getString("payload");
				boolean actionRequired = resultSet.getBoolean("actionRequired");

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					payload);

				actionRequired |= jsonObject.getBoolean("actionRequired");

				jsonObject.remove("actionRequired");

				preparedStatement2.setString(1, jsonObject.toString());

				preparedStatement2.setBoolean(2, actionRequired);
				preparedStatement2.setLong(3, userNotificationEventId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private final UserNotificationEventLocalService
		_userNotificationEventLocalService;

}