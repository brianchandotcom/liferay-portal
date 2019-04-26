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
public class UpgradeDDMFormInstanceSettings extends UpgradeProcess {

	public UpgradeDDMFormInstanceSettings(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected String addNewSetting(
		JSONObject settingsJSONObject, String propertyName, String value) {

		JSONArray fieldValuesJSONArray = settingsJSONObject.getJSONArray(
			"fieldValues");

		JSONObject settingJSONObject = createSettingJSONObject(
			propertyName, value);

		fieldValuesJSONArray.put(settingJSONObject);

		settingsJSONObject.put("fieldValues", fieldValuesJSONArray);

		return settingsJSONObject.toJSONString();
	}

	protected void convertToJSONArrayValue(
		JSONObject fieldJSONObject, String defaultValue) {

		JSONArray jsonArrayValue = _jsonFactory.createJSONArray();

		jsonArrayValue.put(fieldJSONObject.getString("value", defaultValue));

		fieldJSONObject.put("value", jsonArrayValue);
	}

	protected JSONObject createSettingJSONObject(
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

	@Override
	protected void doUpgrade() throws Exception {
		String sql = "select formInstanceId, settings_ from DDMFormInstance";

		try (PreparedStatement ps1 = connection.prepareStatement(sql);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMFormInstance set settings_ = ? where " +
						"formInstanceId = ?")) {

			while (rs.next()) {
				String settings = rs.getString("settings_");

				if (Validator.isNotNull(settings)) {
					JSONObject settingsJSONObject =
						_jsonFactory.createJSONObject(settings);

					addNewSetting(
						settingsJSONObject, "autosaveEnabled", "true");
					addNewSetting(
						settingsJSONObject, "requireAuthentication", "false");

					updateSettings(settingsJSONObject);

					ps2.setString(1, settingsJSONObject.toJSONString());

					ps2.setLong(2, rs.getLong("formInstanceId"));

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	protected JSONObject getFieldValueJSONObject(
		String fieldName, JSONArray fieldValues) {

		for (int i = 0; i < fieldValues.length(); i++) {
			JSONObject jsonObject = fieldValues.getJSONObject(i);

			if (Objects.equals(jsonObject.getString("name"), fieldName)) {
				return jsonObject;
			}
		}

		return null;
	}

	protected void updateSettings(JSONObject settingsJSONObject) {
		JSONArray fieldValues = settingsJSONObject.getJSONArray("fieldValues");

		convertToJSONArrayValue(
			getFieldValueJSONObject("storageType", fieldValues), "json");
		convertToJSONArrayValue(
			getFieldValueJSONObject("workflowDefinition", fieldValues),
			"no-workflow");
	}

	private final JSONFactory _jsonFactory;

}