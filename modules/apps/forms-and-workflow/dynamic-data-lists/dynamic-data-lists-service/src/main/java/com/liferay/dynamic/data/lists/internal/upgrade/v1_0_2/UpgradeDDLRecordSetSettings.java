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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_0_2;

import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adam Brandizzi
 */
public class UpgradeDDLRecordSetSettings extends UpgradeProcess {

	public UpgradeDDLRecordSetSettings(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected String addRequireAuthenticationSetting(
		JSONObject settingsJSONObject) {

		JSONArray fieldValues = settingsJSONObject.getJSONArray("fieldValues");

		JSONObject requireAuthenticationSetting =
			createRequireAuthenticationSetting();

		fieldValues.put(requireAuthenticationSetting);

		settingsJSONObject.put("fieldValues", fieldValues);

		return settingsJSONObject.toJSONString();
	}

	protected JSONObject createRequireAuthenticationSetting() {
		JSONObject requireAuthenticationSetting =
			_jsonFactory.createJSONObject();

		requireAuthenticationSetting.put(
			"instanceId", StringUtil.randomString());
		requireAuthenticationSetting.put("name", "requireAuthentication");
		requireAuthenticationSetting.put("value", "false");

		return requireAuthenticationSetting;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String sql = "select recordSetId, scope, settings_ from DDLRecordSet";

		try (PreparedStatement ps1 = connection.prepareStatement(sql);
			ResultSet rs = ps1.executeQuery();

			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDLRecordSet set settings_ = ? where recordSetId " +
						"= ?")) {

			while (rs.next()) {
				long recordSetId = rs.getLong("recordSetId");

				int scope = rs.getInt("scope");

				String settings = rs.getString("settings_");

				if (Validator.isNotNull(settings)) {
					JSONObject settingsJSONObject =
						_jsonFactory.createJSONObject(settings);

					if (scope == DDLRecordSetConstants.SCOPE_FORMS) {
						settings = addRequireAuthenticationSetting(
							settingsJSONObject);

						ps2.setString(1, settings);

						ps2.setLong(2, recordSetId);

						ps2.addBatch();
					}
				}
			}

			ps2.executeBatch();
		}
	}

	private final JSONFactory _jsonFactory;

}