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

package com.liferay.portal.workflow.kaleo.upgrade.v1_3_0;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Lino Alves
 */
public class UpgradeClassNames extends BaseWorkflowContextUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateClassName("KaleoInstance", "className");
		updateClassName("KaleoInstanceToken", "className");
		updateClassName("KaleoLog", "currentAssigneeClassName");
		updateClassName("KaleoLog", "previousAssigneeClassName");
		updateClassName("KaleoNotificationRecipient", "recipientClassName");
		updateClassName("KaleoTaskAssignment", "assigneeClassName");
		updateClassName("KaleoTaskAssignmentInstance", "assigneeClassName");
		updateClassName("KaleoTaskInstanceToken", "className");

		updateWorkflowContextEntryClassName("KaleoInstance", "kaleoInstanceId");
		updateWorkflowContextEntryClassName("KaleoLog", "kaleoLogId");
		updateWorkflowContextEntryClassName(
			"KaleoTaskInstanceToken", "kaleoTaskInstanceTokenId");
		updateWorkflowContextEntryClassName(
			"KaleoTimerInstanceToken", "kaleoTimerInstanceTokenId");
	}

	protected void updateClassName(String tableName, String columnName) {
		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			Table table = new Table(tableName);

			for (Map.Entry<String, String> entry : classNamesMap.entrySet()) {
				table.updateColumnValue(
					columnName, entry.getKey(), entry.getValue());
			}
		}
	}

	protected void updateWorkflowContextEntryClassName(
			String tableName, String primaryKeyName)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName);
			PreparedStatement ps = connection.prepareStatement(
				"select " + primaryKeyName + ", workflowContext from " +
					tableName + " where workflowContext is not null");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long primaryKeyValue = rs.getLong(primaryKeyName);
				String workflowContextJSON = rs.getString("workflowContext");

				if (Validator.isNull(workflowContextJSON)) {
					continue;
				}

				String updatedWorkflowContextJSON = renamePortalJavaClassNames(
					workflowContextJSON);

				Map<String, Serializable> workflowContext =
					WorkflowContextUtil.convert(updatedWorkflowContextJSON);

				if (!isEntryClassNameRenamed(workflowContext) &&
					workflowContextJSON.equals(updatedWorkflowContextJSON)) {

					continue;
				}

				replaceEntryClassName(workflowContext);

				updateWorkflowContext(
					tableName, primaryKeyName, primaryKeyValue,
					WorkflowContextUtil.convert(workflowContext));
			}
		}
	}

}