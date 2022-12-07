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

package com.liferay.notification.internal.upgrade.v3_0_0;

import com.liferay.notification.internal.upgrade.v3_0_0.util.NotificationRecipientSettingTable;
import com.liferay.notification.internal.upgrade.v3_0_0.util.NotificationRecipientTable;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Arrays;
import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class NotificationRecipientUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select notificationQueueEntryId, companyId, userId, ",
					"userName, createDate, modifiedDate, bcc, cc, from_, ",
					"fromName, to_, toName FROM NotificationQueueEntry"));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select notificationTemplateId, companyId, userId, ",
					"userName, createDate, modifiedDate, bcc, cc, from_, ",
					"fromName, to_ FROM NotificationTemplate"));
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into NotificationRecipient (uuid_, ",
						"notificationRecipientId, companyId, userId, ",
						"userName, createDate, modifiedDate, classNameId, ",
						"classPK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"));
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into NotificationRecipientSetting (uuid_, ",
						"notificationRecipientSettingId, companyId, userId, ",
						"userName, createDate, modifiedDate, ",
						"notificationRecipientId, name, value) VALUES (?, ?, ",
						"?, ?, ?, ?, ?, ?, ?, ?)"))) {

			while (resultSet1.next()) {
				_insert(
					increment(), resultSet1.getLong("companyId"),
					resultSet1.getLong("userId"),
					resultSet1.getString("userName"),
					resultSet1.getTimestamp("createDate"),
					resultSet1.getTimestamp("modifiedDate"),
					PortalUtil.getClassNameId(NotificationQueueEntry.class),
					resultSet1.getLong("notificationQueueEntryId"),
					Arrays.asList(
						"bcc", "cc", "from_", "fromName", "to_", "toName"),
					preparedStatement3, preparedStatement4, resultSet1);
			}

			while (resultSet2.next()) {
				_insert(
					increment(), resultSet2.getLong("companyId"),
					resultSet2.getLong("userId"),
					resultSet2.getString("userName"),
					resultSet2.getTimestamp("createDate"),
					resultSet2.getTimestamp("modifiedDate"),
					PortalUtil.getClassNameId(NotificationTemplate.class),
					resultSet2.getLong("notificationTemplateId"),
					Arrays.asList("bcc", "cc", "from_", "fromName", "to_"),
					preparedStatement3, preparedStatement4, resultSet2);
			}

			preparedStatement3.executeBatch();

			preparedStatement4.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				"NotificationQueueEntry", "bcc", "cc", "from_", "fromName",
				"to_", "toName"),
			UpgradeProcessFactory.dropColumns(
				"NotificationTemplate", "bcc", "cc", "from_", "fromName", "to_")
		};
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			NotificationRecipientTable.create(),
			NotificationRecipientSettingTable.create()
		};
	}

	private void _insert(
			long notificationRecipientId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			long recipientClassNameId, long recipientClassPK,
			List<String> notificationRecipientSettingsName,
			PreparedStatement preparedStatement3,
			PreparedStatement preparedStatement4, ResultSet resultSet)
		throws SQLException {

		preparedStatement3.setString(1, PortalUUIDUtil.generate());
		preparedStatement3.setLong(2, notificationRecipientId);
		preparedStatement3.setLong(3, companyId);
		preparedStatement3.setLong(4, userId);
		preparedStatement3.setString(5, userName);
		preparedStatement3.setTimestamp(6, createDate);
		preparedStatement3.setTimestamp(7, modifiedDate);
		preparedStatement3.setLong(8, recipientClassNameId);
		preparedStatement3.setLong(9, recipientClassPK);

		preparedStatement3.addBatch();

		for (String notificationRecipientSettingName :
				notificationRecipientSettingsName) {

			preparedStatement4.setString(1, PortalUUIDUtil.generate());
			preparedStatement4.setLong(2, increment());
			preparedStatement4.setLong(3, companyId);
			preparedStatement4.setLong(4, userId);
			preparedStatement4.setString(5, userName);
			preparedStatement4.setTimestamp(6, createDate);
			preparedStatement4.setTimestamp(7, modifiedDate);
			preparedStatement4.setLong(8, notificationRecipientId);
			preparedStatement4.setString(
				9,
				StringUtil.removeSubstring(
					notificationRecipientSettingName, "_"));
			preparedStatement4.setString(
				10, resultSet.getString(notificationRecipientSettingName));

			preparedStatement4.addBatch();
		}
	}

}