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
				alterTableAddColumn("KaleoAction", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoCondition", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoCondition", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoDefinitionVersion", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoDefinitionVersion", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoInstance", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoInstance", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoInstanceToken", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoInstanceToken", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoLog", "kaleoDefinitionId")) {
				alterTableAddColumn("KaleoLog", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoNode", "kaleoDefinitionId")) {
				alterTableAddColumn("KaleoNode", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoNotification", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoNotification", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoNotificationRecipient", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoNotificationRecipient", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTask", "kaleoDefinitionId")) {
				alterTableAddColumn("KaleoTask", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTaskAssignment", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoTaskAssignment", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn(
					"KaleoTaskAssignmentInstance", "kaleoDefinitionId")) {

				alterTableAddColumn(
					"KaleoTaskAssignmentInstance", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTaskForm", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoTaskForm", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTaskFormInstance", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoTaskFormInstance", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTaskInstanceToken", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoTaskInstanceToken", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTimer", "kaleoDefinitionId")) {
				alterTableAddColumn("KaleoTimer", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTimerInstanceToken", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoTimerInstanceToken", "kaleoDefinitionId", "LONG");
			}

			if (!hasColumn("KaleoTransition", "kaleoDefinitionId")) {
				alterTableAddColumn(
					"KaleoTransition", "kaleoDefinitionId", "LONG");
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