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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoActionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoConditionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoDefinitionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoLogTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoNotificationRecipientTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoNotificationTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTaskAssignmentInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTaskAssignmentTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTaskFormInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTaskFormTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTaskInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTaskTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTimerInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTimerTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util.KaleoTransitionTable;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class UpgradeKaleoDefinitionVersion extends UpgradeProcess {

	protected void addBatch(
			PreparedStatement ps, long kaleoDefinitionId,
			long kaleoDefinitionVersionId)
		throws SQLException {

		ps.setLong(1, kaleoDefinitionVersionId);
		ps.setLong(2, kaleoDefinitionId);

		ps.addBatch();
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeKaleoDefinitionVersion();

		removeDuplicateKaleoDefinitions();
		removeKaleoDefinitionId();
		removeStartKaleoNodeId();
	}

	protected String getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

	protected void removeDuplicateKaleoDefinitions()
		throws IOException, SQLException {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select companyId, name, MAX(version) as version from " +
					"KaleoDefinition group by companyId, name");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from KaleoDefinition where companyId = ? and " +
						"name = ? and version < ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String name = rs.getString("name");
				int version = rs.getInt("version");

				ps2.setLong(1, companyId);
				ps2.setString(2, name);
				ps2.setInt(3, version);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void removeKaleoDefinitionId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (hasColumn("KaleoAction", "kaleoDefinitionId")) {
				alter(
					KaleoActionTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoCondition", "kaleoDefinitionId")) {
				alter(
					KaleoConditionTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoInstance", "kaleoDefinitionId")) {
				alter(
					KaleoInstanceTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoInstanceToken", "kaleoDefinitionId")) {
				alter(
					KaleoInstanceTokenTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoLog", "kaleoDefinitionId")) {
				alter(
					KaleoLogTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoNode", "kaleoDefinitionId")) {
				alter(
					KaleoNodeTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoNotification", "kaleoDefinitionId")) {
				alter(
					KaleoNotificationTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoNotificationRecipient", "kaleoDefinitionId")) {
				alter(
					KaleoNotificationRecipientTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTask", "kaleoDefinitionId")) {
				alter(
					KaleoTaskTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTaskAssignment", "kaleoDefinitionId")) {
				alter(
					KaleoTaskAssignmentTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTaskAssignmentInstance", "kaleoDefinitionId")) {
				alter(
					KaleoTaskAssignmentInstanceTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTaskForm", "kaleoDefinitionId")) {
				alter(
					KaleoTaskFormTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTaskFormInstance", "kaleoDefinitionId")) {
				alter(
					KaleoTaskFormInstanceTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTaskInstanceToken", "kaleoDefinitionId")) {
				alter(
					KaleoTaskInstanceTokenTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTimer", "kaleoDefinitionId")) {
				alter(
					KaleoTimerTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTimerInstanceToken", "kaleoDefinitionId")) {
				alter(
					KaleoTimerInstanceTokenTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}

			if (hasColumn("KaleoTransition", "kaleoDefinitionId")) {
				alter(
					KaleoTransitionTable.class,
					new AlterTableDropColumn("kaleoDefinitionId"));
			}
		}
	}

	protected void removeStartKaleoNodeId() throws Exception {
		if (hasColumn("KaleoDefinition", "startKaleoNodeId")) {
			alter(
				KaleoDefinitionTable.class,
				new AlterTableDropColumn("startKaleoNodeId"));
		}
	}

	protected void upgradeKaleoDefinitionVersion() throws Exception {
		StringBundler sb1 = new StringBundler(3);

		sb1.append("select * from KaleoDefinition kd where not exists ");
		sb1.append("(select 1 from KaleoDefinitionVersion kdv where kdv.name ");
		sb1.append("= kd.name and kdv.companyId = kd.companyId)");

		StringBundler sb2 = new StringBundler(6);

		sb2.append("insert into KaleoDefinitionVersion ");
		sb2.append("(kaleoDefinitionVersionId, groupId, companyId, userId, ");
		sb2.append("userName, statusByUserId, statusByUserName, statusDate, ");
		sb2.append("createDate, modifiedDate, name, title, description, ");
		sb2.append("content, version, startKaleoNodeId, status) values (?, ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

		List<PreparedStatement> preparedStatements = new ArrayList<>(17);

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString());
			ResultSet rs = ps1.executeQuery()) {

			for (String tableName : _TABLE_NAMES) {
				if (hasColumn(tableName, "kaleoDefinitionId")) {
					StringBundler sb3 = new StringBundler(4);

					sb3.append("update ");
					sb3.append(tableName);
					sb3.append(" set kaleoDefinitionVersionId = ? where ");
					sb3.append("kaleoDefinitionId = ? ");

					preparedStatements.add(
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection, sb3.toString()));
				}
			}

			while (rs.next()) {
				long kaleoDefinitionId = rs.getLong("kaleoDefinitionId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String content = rs.getString("content");
				int version = rs.getInt("version");
				long startKaleoNodeId = rs.getLong("startKaleoNodeId");

				long kaleoDefinitionVersionId = increment();

				ps2.setLong(1, kaleoDefinitionVersionId);

				ps2.setLong(2, groupId);
				ps2.setLong(3, companyId);
				ps2.setLong(4, userId);
				ps2.setString(5, userName);
				ps2.setLong(6, userId);
				ps2.setString(7, userName);
				ps2.setTimestamp(8, modifiedDate);
				ps2.setTimestamp(9, createDate);
				ps2.setTimestamp(10, modifiedDate);
				ps2.setString(11, name);
				ps2.setString(12, title);
				ps2.setString(13, description);
				ps2.setString(14, content);
				ps2.setString(15, getVersion(version));
				ps2.setLong(16, startKaleoNodeId);
				ps2.setInt(17, WorkflowConstants.STATUS_APPROVED);

				ps2.addBatch();

				for (PreparedStatement preparedStatement : preparedStatements) {
					addBatch(
						preparedStatement, kaleoDefinitionId,
						kaleoDefinitionVersionId);
				}
			}

			ps2.executeBatch();

			for (PreparedStatement preparedStatement : preparedStatements) {
				preparedStatement.executeBatch();
			}
		}
		finally {
			for (PreparedStatement preparedStatement : preparedStatements) {
				DataAccess.cleanUp(preparedStatement);
			}
		}
	}

	private static final String[] _TABLE_NAMES = {
		"KaleoAction", "KaleoCondition", "KaleoInstance", "KaleoInstanceToken",
		"KaleoLog", "KaleoNode", "KaleoNotification",
		"KaleoNotificationRecipient", "KaleoTask", "KaleoTaskAssignment",
		"KaleoTaskAssignmentInstance", "KaleoTaskForm", "KaleoTaskFormInstance",
		"KaleoTaskInstanceToken", "KaleoTimer", "KaleoTimerInstanceToken",
		"KaleoTransition"
	};

}