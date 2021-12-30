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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class DataProviderInstanceUpgradeProcess extends UpgradeProcess {

	public DataProviderInstanceUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMDataProviderInstance.definition, ",
					"DDMDataProviderInstance.dataProviderInstanceId from ",
					"DDMDataProviderInstance"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMDataProviderInstance set definition = ? where " +
						"dataProviderInstanceId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String definition = resultSet.getString(1);
					long dataProviderInstanceId = resultSet.getLong(2);

					String newDefinition =
						_upgradeDataProviderInstanceDefinition(definition);

					preparedStatement2.setString(1, newDefinition);

					preparedStatement2.setLong(2, dataProviderInstanceId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private String _upgradeDataProviderInstanceDefinition(String definition)
		throws JSONException {

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray fieldValuesJSONArray = definitionJSONObject.getJSONArray(
			"fieldValues");

		_upgradeDataProviderInstanceFieldValues(fieldValuesJSONArray);

		return definitionJSONObject.toString();
	}

	private void _upgradeDataProviderInstanceFieldValues(
		JSONArray fieldValuesJSONArray) {

		JSONObject fieldValueJSONObject = _jsonFactory.createJSONObject();

		fieldValueJSONObject.put(
			"instanceId", StringUtil.randomString(8)
		).put(
			"name", "timeout"
		).put(
			"value", "1000"
		);

		fieldValuesJSONArray.put(fieldValueJSONObject);
	}

	private final JSONFactory _jsonFactory;

}