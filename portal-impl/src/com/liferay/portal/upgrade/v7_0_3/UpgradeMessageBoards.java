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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
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

			StringBundler sb = new StringBundler(7);

			sb.append("insert into ");
			sb.append(tempTableName);
			sb.append(" select MBMessage.threadId from MBMessage inner join ");
			sb.append("MBThread on MBMessage.threadId = MBThread.threadId ");
			sb.append("where MBThread.categoryId = -1 group by ");
			sb.append("MBMessage.threadId having count(MBMessage.messageId) ");
			sb.append("= 1");

			runSQL(sb.toString());

			_deleteAssetEntry(tempTableName);
			_deleteTable("MBDiscussion", tempTableName);
			_deleteTable("MBMessage", tempTableName);
			_deleteTable("MBThread", tempTableName);
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
		finally {
			runSQL("drop table " + tempTableName);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteEmptyMBDiscussion();
		populateMBDiscussionGroupId();
	}

	protected void populateMBDiscussionGroupId() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select MBThread.groupId, MBDiscussion.discussionId from ");
		sb.append("MBDiscussion inner join MBThread on MBDiscussion.threadId ");
		sb.append("= MBThread.threadId where MBDiscussion.groupId = 0");

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MBDiscussion set groupId = ? where discussionId " +
						"= ?");
			PreparedStatement ps2 = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps2.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong(1);
					long discussionId = rs.getLong(2);

					ps1.setLong(1, groupId);
					ps1.setLong(2, discussionId);

					ps1.addBatch();
				}

				ps1.executeBatch();
			}
		}
	}

	private void _deleteAssetEntry(String tempTableName) throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.message.boards.kernel.model.MBDiscussion");

		StringBundler sb = new StringBundler(7);

		sb.append("delete from AssetEntry where classPK in (");
		sb.append("select MBMessage.messageId from MBMessage inner join ");
		sb.append(tempTableName);
		sb.append(" on MBMessage.threadId = ");
		sb.append(tempTableName);
		sb.append(".threadId) and classNameId = ");
		sb.append(classNameId);

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sb.toString());

		sb = new StringBundler(9);

		sb.append("delete AssetEntry from AssetEntry inner join MBMessage on ");
		sb.append("AssetEntry.classPK = MBMessage.messageId and ");
		sb.append("AssetEntry.classNameId = ");
		sb.append(classNameId);
		sb.append(" inner join ");
		sb.append(tempTableName);
		sb.append(" on MBMessage.threadId = ");
		sb.append(tempTableName);
		sb.append(".threadId");

		String sql = sb.toString();

		dbTypeToSQLMap.add(DBType.MARIADB, sql);
		dbTypeToSQLMap.add(DBType.MYSQL, sql);

		runSQL(dbTypeToSQLMap);
	}

	private void _deleteTable(String tableName, String tempTableName)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("delete from ");
		sb.append(tableName);
		sb.append(" where threadId in (select threadId from ");
		sb.append(tempTableName);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sb.toString());

		sb = new StringBundler(11);

		sb.append("delete ");
		sb.append(tableName);
		sb.append(" from ");
		sb.append(tableName);
		sb.append(" inner join ");
		sb.append(tempTableName);
		sb.append(" on ");
		sb.append(tableName);
		sb.append(".threadId = ");
		sb.append(tempTableName);
		sb.append(".threadId");

		String sql = sb.toString();

		dbTypeToSQLMap.add(DBType.MARIADB, sql);
		dbTypeToSQLMap.add(DBType.MYSQL, sql);

		runSQL(dbTypeToSQLMap);
	}

}