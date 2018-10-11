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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseUpgradeClassNames extends UpgradeProcess {

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

	protected abstract void updateClassName(
		String tableName, String columnName);

	protected abstract Map<String, Serializable> updateWorkflowContext(
		String workflowContextJSON);

	protected void updateWorkflowContext(
			String tableName, String primaryKeyName, long primaryKeyValue,
			String workflowContext)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"update ", tableName, " set workflowContext = ? where ",
					primaryKeyName, " = ?"))) {

			ps.setString(1, workflowContext);
			ps.setLong(2, primaryKeyValue);

			ps.executeUpdate();
		}
	}

	protected void updateWorkflowContextEntryClassName(
			String tableName, String primaryKeyName)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName);
			PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"select ", primaryKeyName, ", workflowContext from ",
					tableName, " where workflowContext is not null"));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String workflowContextJSON = rs.getString("workflowContext");

				if (Validator.isNull(workflowContextJSON)) {
					continue;
				}

				Map<String, Serializable> workflowContext =
					updateWorkflowContext(workflowContextJSON);

				if (workflowContext != null) {
					long primaryKeyValue = rs.getLong(primaryKeyName);

					updateWorkflowContext(
						tableName, primaryKeyName, primaryKeyValue,
						WorkflowContextUtil.convert(workflowContext));
				}
			}
		}
	}

}