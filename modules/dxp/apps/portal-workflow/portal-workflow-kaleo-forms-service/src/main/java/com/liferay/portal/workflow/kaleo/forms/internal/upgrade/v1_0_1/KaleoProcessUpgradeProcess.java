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
public class KaleoProcessUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateWorkflowDefinition();
	}

	private void _updateKaleoProcess(
			long kaleoProcessId, String workflowDefinitioName,
			int workflowDefinitionVersion)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update KaleoProcess set workflowDefinitionName = ?, " +
					"workflowDefinitionVersion = ? where kaleoProcessId = ?")) {

			preparedStatement.setString(1, workflowDefinitioName);
			preparedStatement.setInt(2, workflowDefinitionVersion);
			preparedStatement.setLong(3, kaleoProcessId);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateWorkflowDefinition() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classPK, workflowDefinitionName, " +
					"workflowDefinitionVersion from WorkflowDefinitionLink " +
						"where classNameId = ?")) {

			long kaleoProcessClassNameId = PortalUtil.getClassNameId(
				KaleoProcess.class);

			preparedStatement.setLong(1, kaleoProcessClassNameId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long kaleoProcessId = resultSet.getLong("classPK");
					String workflowDefinitionName = resultSet.getString(
						"workflowDefinitionName");
					int workflowDefinitionVersion = resultSet.getInt(
						"workflowDefinitionVersion");

					_updateKaleoProcess(
						kaleoProcessId, workflowDefinitionName,
						workflowDefinitionVersion);
				}
			}
		}
	}

}