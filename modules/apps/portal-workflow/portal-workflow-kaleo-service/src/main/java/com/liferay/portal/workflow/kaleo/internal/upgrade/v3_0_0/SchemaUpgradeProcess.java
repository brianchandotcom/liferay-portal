/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			alterTableAddColumn("KaleoAction", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoCondition", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoDefinitionVersion", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoInstance", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoInstanceToken", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoLog", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoNode", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoNotification", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoNotificationRecipient", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoTask", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoTaskAssignment", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoTaskAssignmentInstance", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoTaskForm", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoTaskFormInstance", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoTaskInstanceToken", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoTimer", "kaleoDefinitionId", "LONG");

			alterTableAddColumn(
				"KaleoTimerInstanceToken", "kaleoDefinitionId", "LONG");

			alterTableAddColumn("KaleoTransition", "kaleoDefinitionId", "LONG");
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