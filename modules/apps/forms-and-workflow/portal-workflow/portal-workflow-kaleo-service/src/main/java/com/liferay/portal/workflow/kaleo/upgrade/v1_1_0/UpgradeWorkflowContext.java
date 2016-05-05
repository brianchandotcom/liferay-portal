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

package com.liferay.portal.workflow.kaleo.upgrade.v1_1_0;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.upgrade.BaseWorkflowContextUpgradeProcess;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

import org.jabsorb.JSONSerializer;

/**
 * @author Jang Kim
 */
public class UpgradeWorkflowContext extends BaseWorkflowContextUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateTable("KaleoInstance", "kaleoInstanceId");
		updateTable("KaleoLog", "kaleoLogId");
		updateTable("KaleoTaskInstanceToken", "kaleoTaskInstanceTokenId");
	}

	protected void updateTable(String tableName, String fieldName)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName);
			PreparedStatement ps = connection.prepareStatement(
				"select " + fieldName + ", workflowContext from " + tableName +
					" where workflowContext is not null and workflowContext " +
						"not like '%serializable%'");
			ResultSet rs = ps.executeQuery()) {

			JSONSerializer jsonSerializer = new JSONSerializer();

			jsonSerializer.registerDefaultSerializers();

			while (rs.next()) {
				long fieldValue = rs.getLong(fieldName);
				String workflowContextJSON = rs.getString("workflowContext");

				if (Validator.isNull(workflowContextJSON)) {
					continue;
				}

				workflowContextJSON = renamePortalJavaClassNames(
					workflowContextJSON);

				Map<String, Serializable> workflowContext =
					(Map<String, Serializable>)jsonSerializer.fromJSON(
						workflowContextJSON);

				replaceEntryClassName(workflowContext);

				updateWorkflowContext(
					tableName, fieldName, fieldValue,
					WorkflowContextUtil.convert(workflowContext));
			}
		}
	}

}