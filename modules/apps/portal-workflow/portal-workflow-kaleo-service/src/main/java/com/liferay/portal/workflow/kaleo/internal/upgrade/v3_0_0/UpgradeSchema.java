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
public class UpgradeSchema extends UpgradeProcess {

	protected void addBatch(
			PreparedStatement ps, long kaleoDefinitionId,
			long kaleoDefinitionVersionId)
		throws SQLException {

		ps.setLong(1, kaleoDefinitionId);
		ps.setLong(2, kaleoDefinitionVersionId);

		ps.addBatch();
	}

	protected void addKaleoDefinitionId() throws Exception {
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

	@Override
	protected void doUpgrade() throws Exception {
		addKaleoDefinitionId();

		upgradeKaleoDefinitionId();
	}

	protected void upgradeKaleoDefinitionId() throws Exception {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("select KaleoDefinition.kaleoDefinitionId, ");
		sb1.append("KaleoDefinitionVersion.kaleoDefinitionVersionId from ");
		sb1.append("KaleoDefinitionVersion inner join KaleoDefinition on ");
		sb1.append("KaleoDefinition.companyId = ");
		sb1.append("KaleoDefinitionVersion.companyId and ");
		sb1.append("KaleoDefinition.name = KaleoDefinitionVersion.name");

		List<PreparedStatement> preparedStatements = new ArrayList<>(18);

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(sb1.toString());
			ResultSet rs = ps.executeQuery()) {

			for (String tableName : _TABLE_NAMES) {
				StringBundler sb2 = new StringBundler(4);

				sb2.append("update ");
				sb2.append(tableName);
				sb2.append(" set kaleoDefinitionId = ? where ");
				sb2.append("kaleoDefinitionVersionId = ? ");

				preparedStatements.add(
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb2.toString()));
			}

			while (rs.next()) {
				long kaleoDefinitionId = rs.getLong("kaleoDefinitionId");
				long kaleoDefinitionVersionId = rs.getLong(
					"kaleoDefinitionVersionId");

				for (PreparedStatement preparedStatement : preparedStatements) {
					addBatch(
						preparedStatement, kaleoDefinitionId,
						kaleoDefinitionVersionId);
				}
			}

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
		"KaleoAction", "KaleoCondition", "KaleoDefinitionVersion",
		"KaleoInstance", "KaleoInstanceToken", "KaleoLog", "KaleoNode",
		"KaleoNotification", "KaleoNotificationRecipient", "KaleoTask",
		"KaleoTaskAssignment", "KaleoTaskAssignmentInstance", "KaleoTaskForm",
		"KaleoTaskFormInstance", "KaleoTaskInstanceToken", "KaleoTimer",
		"KaleoTimerInstanceToken", "KaleoTransition"
	};

}