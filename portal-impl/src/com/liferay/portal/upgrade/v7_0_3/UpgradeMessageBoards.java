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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	protected void deleteEmptyMBDiscussion() throws Exception {
		String tempTableName = "TEMP_TABLE_" + StringUtil.randomString(4);

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				StringBundler.concat(
					"create table ", tempTableName, " (threadId LONG NOT NULL ",
					"PRIMARY KEY)"));

			runSQL(
				StringBundler.concat(
					"insert into ", tempTableName,
					" select MBMessage.threadId from MBMessage inner join ",
					"MBThread on MBMessage.threadId = MBThread.threadId where ",
					"MBThread.categoryId = -1 group by MBMessage.threadId ",
					"having count(MBMessage.messageId) = 1"));

			_deleteAssetEntry(tempTableName);
			_deleteTable("MBDiscussion", tempTableName);
			_deleteTable("MBMessage", tempTableName);
			_deleteTable("MBThread", tempTableName);
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
		finally {
			dropTable(tempTableName);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteEmptyMBDiscussion();
		populateMBDiscussionGroupId();
	}

	protected void populateMBDiscussionGroupId() throws Exception {
		try (PreparedStatement preparedStatement1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MBDiscussion set groupId = ? where discussionId " +
						"= ?");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select MBThread.groupId, MBDiscussion.discussionId from ",
					"MBDiscussion inner join MBThread on ",
					"MBDiscussion.threadId = MBThread.threadId where ",
					"MBDiscussion.groupId = 0"))) {

			try (ResultSet resultSet = preparedStatement2.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong(1);
					long discussionId = resultSet.getLong(2);

					preparedStatement1.setLong(1, groupId);
					preparedStatement1.setLong(2, discussionId);

					preparedStatement1.addBatch();
				}

				preparedStatement1.executeBatch();
			}
		}
	}

	private void _deleteAssetEntry(String tempTableName) throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.message.boards.kernel.model.MBDiscussion");

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
			StringBundler.concat(
				"delete from AssetEntry where classPK in (",
				"select MBMessage.messageId from MBMessage inner join ",
				tempTableName, " on MBMessage.threadId = ", tempTableName,
				".threadId) and classNameId = ", classNameId));

		String sql = StringBundler.concat(
			"delete AssetEntry from AssetEntry inner join MBMessage on ",
			"AssetEntry.classPK = MBMessage.messageId and ",
			"AssetEntry.classNameId = ", classNameId, " inner join ",
			tempTableName, " on MBMessage.threadId = ", tempTableName,
			".threadId");

		dbTypeToSQLMap.add(DBType.MARIADB, sql);
		dbTypeToSQLMap.add(DBType.MYSQL, sql);

		runSQL(dbTypeToSQLMap);
	}

	private void _deleteTable(String tableName, String tempTableName)
		throws Exception {

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
			StringBundler.concat(
				"delete from ", tableName,
				" where threadId in (select threadId from ", tempTableName,
				StringPool.CLOSE_PARENTHESIS));

		String sql = StringBundler.concat(
			"delete ", tableName, " from ", tableName, " inner join ",
			tempTableName, " on ", tableName, ".threadId = ", tempTableName,
			".threadId");

		dbTypeToSQLMap.add(DBType.MARIADB, sql);
		dbTypeToSQLMap.add(DBType.MYSQL, sql);

		runSQL(dbTypeToSQLMap);
	}

}