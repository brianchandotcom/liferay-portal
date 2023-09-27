/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Adam Brandizzi
 * @author Pedro Queiroz
 */
public class DDMFormInstanceSettingsUpgradeProcess extends UpgradeProcess {

	public DDMFormInstanceSettingsUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String sql = "select formInstanceId, settings_ from DDMFormInstance";

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMFormInstance set settings_ = ? where " +
						"formInstanceId = ?")) {

			while (resultSet.next()) {
				String settings = resultSet.getString("settings_");

				if (Validator.isNotNull(settings)) {
					JSONObject settingsJSONObject =
						_jsonFactory.createJSONObject(settings);

					_addNewSetting(
						settingsJSONObject, "autosaveEnabled", "true");
					_addNewSetting(
						settingsJSONObject, "requireAuthentication", "false");

					_updateSettings(settingsJSONObject);

					preparedStatement2.setString(
						1, settingsJSONObject.toString());

					preparedStatement2.setLong(
						2, resultSet.getLong("formInstanceId"));

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private String _addNewSetting(
		JSONObject settingsJSONObject, String propertyName, String value) {

		JSONArray fieldValuesJSONArray = settingsJSONObject.getJSONArray(
			"fieldValues");

		JSONObject settingJSONObject = _createSettingJSONObject(
			propertyName, value);

		fieldValuesJSONArray.put(settingJSONObject);

		settingsJSONObject.put("fieldValues", fieldValuesJSONArray);

		return settingsJSONObject.toString();
	}

	private void _convertToJSONArrayValue(
		JSONObject fieldJSONObject, String defaultValue) {

		JSONArray valueJSONArray = _jsonFactory.createJSONArray();

		valueJSONArray.put(fieldJSONObject.getString("value", defaultValue));

		fieldJSONObject.put("value", valueJSONArray);
	}

	private JSONObject _createSettingJSONObject(
		String propertyName, String value) {

		JSONObject settingJSONObject = _jsonFactory.createJSONObject();

		settingJSONObject.put(
			"instanceId", StringUtil.randomString()
		).put(
			"name", propertyName
		).put(
			"value", value
		);

		return settingJSONObject;
	}

	private JSONObject _getFieldValueJSONObject(
		String fieldName, JSONArray fieldValuesJSONArray) {

		for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
			JSONObject jsonObject = fieldValuesJSONArray.getJSONObject(i);

			if (Objects.equals(jsonObject.getString("name"), fieldName)) {
				return jsonObject;
			}
		}

		return _jsonFactory.createJSONObject();
	}

	private void _updateSettings(JSONObject settingsJSONObject) {
		JSONArray fieldValuesJSONArray = settingsJSONObject.getJSONArray(
			"fieldValues");

		_convertToJSONArrayValue(
			_getFieldValueJSONObject("storageType", fieldValuesJSONArray),
			"json");
		_convertToJSONArrayValue(
			_getFieldValueJSONObject(
				"workflowDefinition", fieldValuesJSONArray),
			"no-workflow");
	}

	private final JSONFactory _jsonFactory;

}