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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2;

import com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2.util.DDMFormFieldUpgradeProcessUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Carolina Barbosa
 */
public class DDMFormInstanceReportUpgradeProcess extends UpgradeProcess {

	public DDMFormInstanceReportUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMFormInstanceReport.data_, ",
						"DDMFormInstanceReport.formInstanceReportId from ",
						"DDMFormInstanceReport"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMFormInstanceReport set data_ = ? where " +
						"formInstanceReportId = ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String data = resultSet.getString(1);

					String newData = upgradeDDMFormInstanceReportData(data);

					if (StringUtil.equals(data, newData)) {
						continue;
					}

					updatePreparedStatement.setString(1, newData);
					updatePreparedStatement.setLong(2, resultSet.getLong(2));

					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

	protected String upgradeDDMFormInstanceReportData(String data)
		throws JSONException {

		JSONObject normalizedDataJSONObject = _jsonFactory.createJSONObject();

		JSONObject dataJSONObject = _jsonFactory.createJSONObject(data);

		for (String key : dataJSONObject.keySet()) {
			if (StringUtil.equals(key, "totalItems")) {
				normalizedDataJSONObject.put(key, dataJSONObject.getInt(key));

				continue;
			}

			normalizedDataJSONObject.put(
				DDMFormFieldUpgradeProcessUtil.getNormalizedName(key),
				_getNormalizedFieldJSONObject(
					dataJSONObject.getJSONObject(key)));
		}

		return normalizedDataJSONObject.toString();
	}

	private JSONObject _getNormalizedFieldJSONObject(
		JSONObject fieldJSONObject) {

		String type = fieldJSONObject.getString("type");

		if (!DDMFormFieldUpgradeProcessUtil.isDDMFormFieldWithOptions(type)) {
			return fieldJSONObject;
		}

		JSONObject normalizedFieldJSONObject = _jsonFactory.createJSONObject();

		if (fieldJSONObject.has("totalEntries")) {
			normalizedFieldJSONObject.put(
				"totalEntries", fieldJSONObject.getInt("totalEntries"));
		}

		return normalizedFieldJSONObject.put(
			"type", type
		).put(
			"values",
			_getNormalizedValuesJSONObject(
				fieldJSONObject.getJSONObject("values"))
		);
	}

	private JSONObject _getNormalizedValuesJSONObject(
		JSONObject valuesJSONObject) {

		JSONObject normalizedValuesJSONObject = _jsonFactory.createJSONObject();

		for (String key : valuesJSONObject.keySet()) {
			normalizedValuesJSONObject.put(
				DDMFormFieldUpgradeProcessUtil.getNormalizedName(key),
				valuesJSONObject.getInt(key));
		}

		return normalizedValuesJSONObject;
	}

	private final JSONFactory _jsonFactory;

}