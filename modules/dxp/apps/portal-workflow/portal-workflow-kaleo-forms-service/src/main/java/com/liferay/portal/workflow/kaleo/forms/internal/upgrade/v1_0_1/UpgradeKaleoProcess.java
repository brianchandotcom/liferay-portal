/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcellus Tavares
 */
public class UpgradeKaleoProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateWorkflowDefinition();
	}

	protected void updateKaleoProcess(
			long kaleoProcessId, String workflowDefinitioName,
			int workflowDefinitionVersion)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update KaleoProcess set workflowDefinitionName = ?, " +
					"workflowDefinitionVersion = ? where kaleoProcessId = ?")) {

			ps.setString(1, workflowDefinitioName);
			ps.setInt(2, workflowDefinitionVersion);
			ps.setLong(3, kaleoProcessId);

			ps.executeUpdate();
		}
	}

	protected void updateWorkflowDefinition() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select classPK, workflowDefinitionName, " +
					"workflowDefinitionVersion from WorkflowDefinitionLink " +
						"where classNameId = ?")) {

			long kaleoProcessClassNameId = PortalUtil.getClassNameId(
				KaleoProcess.class);

			ps.setLong(1, kaleoProcessClassNameId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long kaleoProcessId = rs.getLong("classPK");
					String workflowDefinitioName = rs.getString(
						"workflowDefinitionName");
					int workflowDefinitionVersion = rs.getInt(
						"workflowDefinitionVersion");

					updateKaleoProcess(
						kaleoProcessId, workflowDefinitioName,
						workflowDefinitionVersion);
				}
			}
		}
	}

}