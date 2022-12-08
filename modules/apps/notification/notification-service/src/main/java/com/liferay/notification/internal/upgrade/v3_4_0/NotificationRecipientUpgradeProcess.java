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

package com.liferay.notification.internal.upgrade.v3_4_0;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Feliphe Marinho
 */
public class NotificationRecipientUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select count(*) from NotificationRecipient");
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select count(*) from NotificationRecipientSetting");
			ResultSet resultSet2 = preparedStatement2.executeQuery()) {

			if (resultSet1.next() && (resultSet1.getInt(1) > 0)) {
				return;
			}
			else if (resultSet2.next() && (resultSet2.getInt(1) == 0)) {
				return;
			}
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select notificationQueueEntryId, companyId, userId, ",
					"userName, createDate, modifiedDate from ",
					"NotificationQueueEntry"));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select notificationTemplateId, companyId, userId, ",
					"userName, createDate, modifiedDate from ",
					"NotificationTemplate"));
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into NotificationRecipient (uuid_, ",
						"notificationRecipientId, companyId, userId, ",
						"userName, createDate, modifiedDate, classNameId, ",
						"classPK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"));
			PreparedStatement preparedStatement4 = connection.prepareStatement(
				"select distinct notificationRecipientId from " +
					"NotificationRecipientSetting where createDate = ?")) {

			while (resultSet1.next()) {
				_insertNotificationRecipient(
					resultSet1.getLong("notificationQueueEntryId"),
					NotificationQueueEntry.class, preparedStatement3,
					preparedStatement4, resultSet1);
			}

			while (resultSet2.next()) {
				_insertNotificationRecipient(
					resultSet2.getLong("notificationTemplateId"),
					NotificationTemplate.class, preparedStatement3,
					preparedStatement4, resultSet2);
			}

			preparedStatement3.executeBatch();
		}
	}

	private Long _getNotificationRecipientId(
			PreparedStatement preparedStatement, ResultSet resultSet)
		throws Exception {

		preparedStatement.setTimestamp(1, resultSet.getTimestamp("createDate"));

		try (ResultSet resultSet3 = preparedStatement.executeQuery()) {
			while (resultSet3.next()) {
				return resultSet3.getLong("notificationRecipientId");
			}
		}

		throw new UpgradeException(
			StringBundler.concat(
				"Please manually re-create recipient data in your ",
				"Notifications portlet, as this data was erroneously deleted ",
				"during a previous upgrade process"));
	}

	private void _insertNotificationRecipient(
			long classPK, Class<?> clazz, PreparedStatement preparedStatement3,
			PreparedStatement preparedStatement4, ResultSet resultSet)
		throws Exception {

		preparedStatement3.setString(1, PortalUUIDUtil.generate());
		preparedStatement3.setLong(
			2, _getNotificationRecipientId(preparedStatement4, resultSet));
		preparedStatement3.setLong(3, resultSet.getLong("companyId"));
		preparedStatement3.setLong(4, resultSet.getLong("userId"));
		preparedStatement3.setString(5, resultSet.getString("userName"));
		preparedStatement3.setTimestamp(
			6, resultSet.getTimestamp("createDate"));
		preparedStatement3.setTimestamp(
			7, resultSet.getTimestamp("modifiedDate"));
		preparedStatement3.setLong(8, PortalUtil.getClassNameId(clazz));
		preparedStatement3.setLong(9, classPK);

		preparedStatement3.addBatch();
	}

}