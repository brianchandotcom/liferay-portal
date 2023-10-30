/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v4_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.workflow.kaleo.definition.util.WorkflowDefinitionContentUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Rafael Praxedes
 */
public class KaleoDefinitionContentUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeContent("kaleoDefinitionId", "KaleoDefinition");
		_upgradeContent("kaleoDefinitionVersionId", "KaleoDefinitionVersion");
	}

	private void _upgradeContent(String columnName, String tableName)
		throws Exception {

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				StringBundler.concat(
					"select content, ", columnName, " from ", tableName));
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					StringBundler.concat(
						"update ", tableName, " set content = ? where ",
						columnName, " = ?"))) {

			while (resultSet.next()) {
				preparedStatement.setString(
					1,
					WorkflowDefinitionContentUtil.toJSON(
						resultSet.getString("content")));
				preparedStatement.setLong(2, resultSet.getLong(columnName));

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

}