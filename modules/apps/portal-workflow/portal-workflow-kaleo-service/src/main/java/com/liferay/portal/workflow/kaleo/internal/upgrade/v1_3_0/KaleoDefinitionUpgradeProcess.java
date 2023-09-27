/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Leonardo Barros
 */
public class KaleoDefinitionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select kaleoDefinitionId, content from KaleoDefinition " +
					"where content like '%WorkflowConstants.toStatus(%'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long kaleoDefinitionId = resultSet.getLong(1);

				String content = resultSet.getString(2);

				content = StringUtil.replace(
					content, "WorkflowConstants.toStatus(",
					"WorkflowConstants.getLabelStatus(");

				_updateContent(kaleoDefinitionId, content);
			}
		}
	}

	private void _updateContent(long kaleoDefinitionId, String content)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update KaleoDefinition set content = ? where " +
					"kaleoDefinitionId = ?")) {

			preparedStatement.setString(1, content);
			preparedStatement.setLong(2, kaleoDefinitionId);

			preparedStatement.executeUpdate();
		}
	}

}