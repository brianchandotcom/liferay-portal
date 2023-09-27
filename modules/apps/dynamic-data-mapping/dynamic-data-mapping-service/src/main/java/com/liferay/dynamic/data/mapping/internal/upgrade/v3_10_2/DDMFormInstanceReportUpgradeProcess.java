/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return normalizedFieldJSONObject.put(
			"totalEntries",
			() -> {
				if (fieldJSONObject.has("totalEntries")) {
					return fieldJSONObject.getInt("totalEntries");
				}

				return null;
			}
		).put(
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

		if ((valuesJSONObject != null) && (valuesJSONObject.length() > 0)) {
			for (String key : valuesJSONObject.keySet()) {
				normalizedValuesJSONObject.put(
					DDMFormFieldUpgradeProcessUtil.getNormalizedName(key),
					valuesJSONObject.getInt(key));
			}
		}

		return normalizedValuesJSONObject;
	}

	private final JSONFactory _jsonFactory;

}