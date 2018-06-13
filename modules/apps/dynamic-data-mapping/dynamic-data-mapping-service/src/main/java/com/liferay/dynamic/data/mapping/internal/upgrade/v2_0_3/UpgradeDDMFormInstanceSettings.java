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

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDMFormInstanceSettings extends UpgradeProcess {

	public UpgradeDDMFormInstanceSettings(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected String addAutosaveEnabledSetting(JSONObject settingsJSONObject) {
		JSONArray fieldValues = settingsJSONObject.getJSONArray("fieldValues");

		JSONObject autosaveEnabledSetting = createAutosaveEnabledSetting();

		fieldValues.put(autosaveEnabledSetting);

		settingsJSONObject.put("fieldValues", fieldValues);

		return settingsJSONObject.toJSONString();
	}

	protected JSONObject createAutosaveEnabledSetting() {
		JSONObject autosaveEnabledSetting = _jsonFactory.createJSONObject();

		autosaveEnabledSetting.put("instanceId", StringUtil.randomString());
		autosaveEnabledSetting.put("name", "autosaveEnabled");
		autosaveEnabledSetting.put("value", "true");

		return autosaveEnabledSetting;
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
				long formInstanceId = rs.getLong("formInstanceId");

				String settings = rs.getString("settings_");

				if (Validator.isNotNull(settings)) {
					JSONObject settingsJSONObject =
						_jsonFactory.createJSONObject(settings);

					settings = addAutosaveEnabledSetting(settingsJSONObject);

					ps2.setString(1, settings);

					ps2.setLong(2, formInstanceId);

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	private final JSONFactory _jsonFactory;

}