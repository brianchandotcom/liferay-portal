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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoActionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoConditionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoDefinitionVersionTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoLogTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoNotificationRecipientTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoNotificationTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTaskAssignmentInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTaskAssignmentTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTaskFormInstanceTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTaskFormTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTaskInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTaskTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTimerInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTimerTable;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_0_0.util.KaleoTransitionTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class SchemaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_addKaleoDefinitionId();

		_upgradeKaleoDefinitionId();
	}

	private void _addBatch(
			PreparedStatement preparedStatement, long kaleoDefinitionId,
			long kaleoDefinitionVersionId)
		throws SQLException {

		preparedStatement.setLong(1, kaleoDefinitionId);
		preparedStatement.setLong(2, kaleoDefinitionVersionId);

		preparedStatement.addBatch();
	}

	private void _addKaleoDefinitionId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("KaleoAction", "kaleoDefinitionId")) {
				alter(
					KaleoActionTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoCondition", "kaleoDefinitionId")) {
				alter(
					KaleoConditionTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoDefinitionVersion", "kaleoDefinitionId")) {
				alter(
					KaleoDefinitionVersionTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoInstance", "kaleoDefinitionId")) {
				alter(
					KaleoInstanceTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoInstanceToken", "kaleoDefinitionId")) {
				alter(
					KaleoInstanceTokenTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoLog", "kaleoDefinitionId")) {
				alter(
					KaleoLogTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoNode", "kaleoDefinitionId")) {
				alter(
					KaleoNodeTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoNotification", "kaleoDefinitionId")) {
				alter(
					KaleoNotificationTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoNotificationRecipient", "kaleoDefinitionId")) {
				alter(
					KaleoNotificationRecipientTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTask", "kaleoDefinitionId")) {
				alter(
					KaleoTaskTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTaskAssignment", "kaleoDefinitionId")) {
				alter(
					KaleoTaskAssignmentTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn(
					"KaleoTaskAssignmentInstance", "kaleoDefinitionId")) {

				alter(
					KaleoTaskAssignmentInstanceTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTaskForm", "kaleoDefinitionId")) {
				alter(
					KaleoTaskFormTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTaskFormInstance", "kaleoDefinitionId")) {
				alter(
					KaleoTaskFormInstanceTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTaskInstanceToken", "kaleoDefinitionId")) {
				alter(
					KaleoTaskInstanceTokenTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTimer", "kaleoDefinitionId")) {
				alter(
					KaleoTimerTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTimerInstanceToken", "kaleoDefinitionId")) {
				alter(
					KaleoTimerInstanceTokenTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}

			if (!hasColumn("KaleoTransition", "kaleoDefinitionId")) {
				alter(
					KaleoTransitionTable.class,
					new AlterTableAddColumn("kaleoDefinitionId", "LONG"));
			}
		}
	}

	private void _upgradeKaleoDefinitionId() throws Exception {
		List<PreparedStatement> preparedStatements = new ArrayList<>(18);

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select KaleoDefinition.kaleoDefinitionId, ",
					"KaleoDefinitionVersion.kaleoDefinitionVersionId from ",
					"KaleoDefinitionVersion inner join KaleoDefinition on ",
					"KaleoDefinition.companyId = ",
					"KaleoDefinitionVersion.companyId and ",
					"KaleoDefinition.name = KaleoDefinitionVersion.name"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			for (String tableName : _TABLE_NAMES) {
				preparedStatements.add(
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						StringBundler.concat(
							"update ", tableName,
							" set kaleoDefinitionId = ? where ",
							"kaleoDefinitionVersionId = ? ")));
			}

			while (resultSet.next()) {
				long kaleoDefinitionId = resultSet.getLong("kaleoDefinitionId");
				long kaleoDefinitionVersionId = resultSet.getLong(
					"kaleoDefinitionVersionId");

				for (PreparedStatement curPreparedStatement :
						preparedStatements) {

					_addBatch(
						curPreparedStatement, kaleoDefinitionId,
						kaleoDefinitionVersionId);
				}
			}

			for (PreparedStatement curPreparedStatement : preparedStatements) {
				curPreparedStatement.executeBatch();
			}
		}
		finally {
			for (PreparedStatement curPreparedStatement : preparedStatements) {
				DataAccess.cleanUp(curPreparedStatement);
			}
		}
	}

	private static final String[] _TABLE_NAMES = {
		"KaleoAction", "KaleoCondition", "KaleoDefinitionVersion",
		"KaleoInstance", "KaleoInstanceToken", "KaleoLog", "KaleoNode",
		"KaleoNotification", "KaleoNotificationRecipient", "KaleoTask",
		"KaleoTaskAssignment", "KaleoTaskAssignmentInstance", "KaleoTaskForm",
		"KaleoTaskFormInstance", "KaleoTaskInstanceToken", "KaleoTimer",
		"KaleoTimerInstanceToken", "KaleoTransition"
	};

}