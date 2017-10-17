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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_2_0;

import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class UpgradeDDLRecordSetSettingsFieldValues extends UpgradeProcess {

	public UpgradeDDLRecordSetSettingsFieldValues(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected void convertToJSONArrayValue(
		JSONObject fieldJSONObject, String defaultValue) {

		JSONArray jsonArrayValue = _jsonFactory.createJSONArray();

		jsonArrayValue.put(fieldJSONObject.getString("value", defaultValue));

		fieldJSONObject.put("value", jsonArrayValue);
	}

	@Override
	protected void doUpgrade() throws Exception {
		String sql =
			"select recordSetId, scope, settings_ from DDLRecordSet where " +
				"DDLRecordSet.scope = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(sql);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDLRecordSet set settings_ = ? where recordSetId " +
						"= ?")) {

			ps1.setInt(1, DDLRecordSetConstants.SCOPE_FORMS);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long recordSetId = rs.getLong("recordSetId");

					String settings = rs.getString("settings_");

					if (Validator.isNotNull(settings)) {
						JSONObject settingsJSONObject =
							_jsonFactory.createJSONObject(settings);

						updateSettings(settingsJSONObject);

						ps2.setString(1, settingsJSONObject.toJSONString());

						ps2.setLong(2, recordSetId);

						ps2.addBatch();
					}
				}

				ps2.executeBatch();
			}
		}
	}

	protected JSONObject getFieldValue(
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
			getFieldValue("storageType", fieldValues), "json");
		convertToJSONArrayValue(
			getFieldValue("workflowDefinition", fieldValues), "no-workflow");
	}

	private final JSONFactory _jsonFactory;

}