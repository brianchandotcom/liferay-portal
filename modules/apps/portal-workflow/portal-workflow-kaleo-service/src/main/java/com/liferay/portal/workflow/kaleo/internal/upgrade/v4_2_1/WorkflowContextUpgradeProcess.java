/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v4_2_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jhosseph Gonzalez
 */
public class WorkflowContextUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeWorkflowContext("kaleoInstanceId", "KaleoInstance");
		_upgradeWorkflowContext("kaleoLogId", "KaleoLog");
		_upgradeWorkflowContext(
			"kaleoTaskInstanceTokenId", "KaleoTaskInstanceToken");
	}

	private void _upgradeWorkflowContext(String columnName, String tableName)
		throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ctCollectionId, ", columnName,
					", workflowContext from ", tableName,
					" where workflowContext is not null and workflowContext ",
					"like '%com.liferay.headless.common.spi.service.context.",
					"ServiceContextUtil%'"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update ", tableName,
						" set workflowContext = ? where ctCollectionId = ? ",
						"and ", columnName, " = ?"))) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				JSONObject workflowContextJSONObject =
					JSONFactoryUtil.createJSONObject(
						resultSet.getString("workflowContext"));

				JSONObject mapJSONObject =
					workflowContextJSONObject.getJSONObject("map");

				mapJSONObject.put(
					"serviceContext",
					JSONUtil.put(
						"javaClass", ServiceContext.class.getName()
					).put(
						"serializable",
						() -> {
							JSONObject serviceContextJSONObject =
								mapJSONObject.getJSONObject("serviceContext");

							serviceContextJSONObject.remove("javaClass");

							return serviceContextJSONObject;
						}
					));

				preparedStatement2.setString(
					1, workflowContextJSONObject.toString());

				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(3, resultSet.getLong(columnName));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}