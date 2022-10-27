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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
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
		try (PreparedStatement selectPreparedStatement1 =
				connection.prepareStatement(
					StringBundler.concat(
						"SELECT notificationQueueEntryId, companyId, userId, ",
						"userName, createDate, modifiedDate, bcc, cc, from_, ",
						"fromName, to_, toName FROM NotificationQueueEntry"));
			ResultSet resultSet1 = selectPreparedStatement1.executeQuery();
			PreparedStatement selectPreparedStatement2 =
				connection.prepareStatement(
					StringBundler.concat(
						"SELECT notificationTemplateId, companyId, userId, ",
						"userName, createDate, modifiedDate, bcc, cc, from_, ",
						"fromName, to_ FROM NotificationTemplate"));
			ResultSet resultSet2 = selectPreparedStatement2.executeQuery();
			PreparedStatement insertIntoPreparedStatement1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"INSERT INTO NotificationRecipient (uuid_, ",
						"notificationRecipientId, companyId, userId, ",
						"userName, createDate, modifiedDate, className, ",
						"classPK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"));
			PreparedStatement insertIntoPreparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"INSERT INTO NotificationRecipientSetting (uuid_, ",
						"notificationRecipientSettingId, companyId, userId, ",
						"userName, createDate, modifiedDate, ",
						"notificationRecipientId, name, value) VALUES (?, ?, ",
						"?, ?, ?, ?, ?, ?, ?, ?)"))) {

			while (resultSet1.next()) {
				_prepareInsertIntoStatements(
					increment(), resultSet1.getLong("companyId"),
					resultSet1.getLong("userId"),
					resultSet1.getString("userName"),
					resultSet1.getTimestamp("createDate"),
					resultSet1.getTimestamp("modifiedDate"),
					NotificationQueueEntry.class.getName(),
					resultSet1.getLong("notificationQueueEntryId"),
					Arrays.asList(
						"bcc", "cc", "from_", "fromName", "to_", "toName"),
					insertIntoPreparedStatement1, insertIntoPreparedStatement2,
					resultSet1);
			}

			while (resultSet2.next()) {
				_prepareInsertIntoStatements(
					increment(), resultSet2.getLong("companyId"),
					resultSet2.getLong("userId"),
					resultSet2.getString("userName"),
					resultSet2.getTimestamp("createDate"),
					resultSet2.getTimestamp("modifiedDate"),
					NotificationTemplate.class.getName(),
					resultSet2.getLong("notificationTemplateId"),
					Arrays.asList("bcc", "cc", "from_", "fromName", "to_"),
					insertIntoPreparedStatement1, insertIntoPreparedStatement2,
					resultSet2);
			}

			insertIntoPreparedStatement1.executeBatch();
			insertIntoPreparedStatement2.executeBatch();
		}
		catch (Exception exception) {
			_log.error(exception);
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

	private void _prepareInsertIntoStatements(
			long notificationRecipientId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			String recipientClassName, long recipientClassPK,
			List<String> notificationRecipientSettingsName,
			PreparedStatement insertIntoPreparedStatement1,
			PreparedStatement insertIntoPreparedStatement2, ResultSet resultSet)
		throws SQLException {

		insertIntoPreparedStatement1.setString(1, PortalUUIDUtil.generate());
		insertIntoPreparedStatement1.setLong(2, notificationRecipientId);
		insertIntoPreparedStatement1.setLong(3, companyId);
		insertIntoPreparedStatement1.setLong(4, userId);
		insertIntoPreparedStatement1.setString(5, userName);
		insertIntoPreparedStatement1.setTimestamp(6, createDate);
		insertIntoPreparedStatement1.setTimestamp(7, modifiedDate);
		insertIntoPreparedStatement1.setString(8, recipientClassName);
		insertIntoPreparedStatement1.setLong(9, recipientClassPK);

		insertIntoPreparedStatement1.addBatch();

		for (String notificationRecipientSettingName :
				notificationRecipientSettingsName) {

			insertIntoPreparedStatement2.setString(
				1, PortalUUIDUtil.generate());
			insertIntoPreparedStatement2.setLong(2, increment());
			insertIntoPreparedStatement2.setLong(3, companyId);
			insertIntoPreparedStatement2.setLong(4, userId);
			insertIntoPreparedStatement2.setString(5, userName);
			insertIntoPreparedStatement2.setTimestamp(6, createDate);
			insertIntoPreparedStatement2.setTimestamp(7, modifiedDate);
			insertIntoPreparedStatement2.setLong(8, notificationRecipientId);
			insertIntoPreparedStatement2.setString(
				9,
				StringUtil.removeSubstring(
					notificationRecipientSettingName, "_"));
			insertIntoPreparedStatement2.setString(
				10, resultSet.getString(notificationRecipientSettingName));

			insertIntoPreparedStatement2.addBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationRecipientUpgradeProcess.class);

}