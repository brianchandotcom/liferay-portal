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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormInstanceUpgradeProcess extends UpgradeProcess {

	public DDMFormInstanceUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("where settings_ not like '%workflowDefinition\\\",\\\"");
		sb.append("value\\\":\\\"[\\\\\\\\\"no-workflow\\\\\\\\\"]\\\"%' and ");
		sb.append("settings_ like '%workflowDefinition\\\",\\\"value\\\":\\\"");
		sb.append("[\\\\\\\\\"%@%\\\\\\\\\"]\\\"%'");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select formInstanceId, settings_ from DDMFormInstance " +
					sb.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMFormInstance set settings_ = ? where " +
						"formInstanceId = ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"select formInstanceVersionId, settings_ from " +
					"DDMFormInstanceVersion " + sb.toString());
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMFormInstanceVersion set settings_ = ? where " +
						"formInstanceVersionId = ?")) {

			_executePreparedStatements(
				"formInstanceId", preparedStatement1, preparedStatement2);

			_executePreparedStatements(
				"formInstanceVersionId", preparedStatement3,
				preparedStatement4);
		}
	}

	private void _executePreparedStatements(
			String idColumnName, PreparedStatement selectPreparedStatement,
			PreparedStatement updatePreparedStatement)
		throws JSONException, SQLException {

		try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
			while (resultSet.next()) {
				JSONObject settingsJSONObject = _jsonFactory.createJSONObject(
					resultSet.getString("settings_"));

				if (_upgradeSettings(settingsJSONObject)) {
					updatePreparedStatement.setString(
						1, settingsJSONObject.toString());
					updatePreparedStatement.setLong(
						2, resultSet.getLong(idColumnName));

					updatePreparedStatement.addBatch();
				}
			}
		}

		updatePreparedStatement.executeBatch();
	}

	private boolean _upgradeSettings(JSONObject settingsJSONObject) {
		boolean upgraded = false;

		JSONArray fieldValuesJSONArray = settingsJSONObject.getJSONArray(
			"fieldValues");

		for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
			JSONObject fieldValueJSONObject =
				fieldValuesJSONArray.getJSONObject(i);

			if (StringUtil.equals(
					fieldValueJSONObject.getString("name"),
					"workflowDefinition")) {

				String workflowDefinition = fieldValueJSONObject.getString(
					"value");

				if (workflowDefinition.matches("\\[\".+@\\d+\"\\]")) {
					fieldValueJSONObject.put(
						"value",
						workflowDefinition.replaceAll(
							"@\\d+\"\\]",
							StringPool.QUOTE + StringPool.CLOSE_BRACKET));

					upgraded = true;
				}

				break;
			}
		}

		return upgraded;
	}

	private final JSONFactory _jsonFactory;

}