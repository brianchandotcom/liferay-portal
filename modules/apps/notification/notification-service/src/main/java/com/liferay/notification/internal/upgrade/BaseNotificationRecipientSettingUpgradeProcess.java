/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.upgrade;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Pedro Leite
 */
public abstract class BaseNotificationRecipientSettingUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select NotificationRecipient.notificationRecipientId, ",
					"NotificationRecipient.companyId, ",
					"NotificationRecipient.userId, ",
					"NotificationRecipient.userName from ",
					"NotificationRecipient inner join NotificationQueueEntry ",
					"on NotificationRecipient.classPK = ",
					"NotificationQueueEntry.notificationQueueEntryId where ",
					"NotificationQueueEntry.type_ = '",
					NotificationConstants.TYPE_EMAIL, "'"));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select NotificationRecipient.notificationRecipientId, ",
					"NotificationRecipient.companyId, ",
					"NotificationRecipient.userId, ",
					"NotificationRecipient.userName from ",
					"NotificationRecipient inner join NotificationTemplate on ",
					"NotificationRecipient.classPK = ",
					"NotificationTemplate.notificationTemplateId where ",
					"NotificationTemplate.type_ = '",
					NotificationConstants.TYPE_EMAIL, "'"));
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into NotificationRecipientSetting (uuid_, ",
						"notificationRecipientSettingId, companyId, userId, ",
						"userName, createDate, modifiedDate, ",
						"notificationRecipientId, name, value) values (?, ?, ",
						"?, ?, ?, ?, ?, ?, ?, ?)"))) {

			while (resultSet1.next()) {
				_insertNotificationRecipientSetting(
					preparedStatement3, resultSet1);
			}

			while (resultSet2.next()) {
				_insertNotificationRecipientSetting(
					preparedStatement3, resultSet2);
			}

			preparedStatement3.executeBatch();
		}
	}

	protected abstract String getNotificationRecipientSettingName();

	protected abstract String getNotificationRecipientSettingValue();

	private void _insertNotificationRecipientSetting(
			PreparedStatement preparedStatement, ResultSet resultSet)
		throws Exception {

		preparedStatement.setString(1, PortalUUIDUtil.generate());
		preparedStatement.setLong(2, increment());
		preparedStatement.setLong(3, resultSet.getLong("companyId"));
		preparedStatement.setLong(4, resultSet.getLong("userId"));
		preparedStatement.setString(5, resultSet.getString("userName"));

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		preparedStatement.setTimestamp(6, timestamp);
		preparedStatement.setTimestamp(7, timestamp);

		preparedStatement.setLong(
			8, resultSet.getLong("notificationRecipientId"));
		preparedStatement.setString(9, getNotificationRecipientSettingName());
		preparedStatement.setString(10, getNotificationRecipientSettingValue());

		preparedStatement.addBatch();
	}

}