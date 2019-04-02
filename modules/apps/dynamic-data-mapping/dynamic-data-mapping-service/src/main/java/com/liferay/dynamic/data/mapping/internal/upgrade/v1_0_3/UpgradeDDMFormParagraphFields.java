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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Laszlo Hudak
 */
public class UpgradeDDMFormParagraphFields extends UpgradeProcess {

	public UpgradeDDMFormParagraphFields(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select structureId, definition from DDMStructure where " +
					"classNameId = ? ");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 = connection.prepareStatement(
				"select structureVersionId, definition from " +
					"DDMStructureVersion where structureId = ?");
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			ps1.setLong(1, getClassNameId());

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString("definition");

					ps2.setString(1, makeFieldsLocalizable(definition));

					long structureId = rs.getLong("structureId");

					ps2.setLong(2, structureId);

					ps2.addBatch();

					ps3.setLong(1, structureId);

					try (ResultSet rs2 = ps3.executeQuery()) {
						while (rs2.next()) {
							definition = rs2.getString("definition");

							ps4.setString(1, makeFieldsLocalizable(definition));

							long structureVersionId = rs2.getLong(
								"structureVersionId");

							ps4.setLong(2, structureVersionId);

							ps4.addBatch();
						}
					}
				}
			}

			ps2.executeBatch();

			ps4.executeBatch();
		}
	}

	protected long getClassNameId() {
		return PortalUtil.getClassNameId(
			"com.liferay.dynamic.data.lists.model.DDLRecordSet");
	}

	protected void makeFieldsLocalizable(
		JSONArray fieldsJSONArray, JSONArray availableLanguageIds) {

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);

			String type = jsonObject.getString("type");

			if (type.equals("paragraph") &&
				!_isValueLocalizable(jsonObject, availableLanguageIds)) {

				String originalValue = jsonObject.getString("text");

				Map<String, String> localizedValue = new HashMap<>();

				for (int j = 0; j < availableLanguageIds.length(); j++) {
					localizedValue.put(
						availableLanguageIds.getString(j), originalValue);
				}

				jsonObject.put("text", localizedValue);

				JSONArray nestedFieldsJSONArray = jsonObject.getJSONArray(
					"nestedFields");

				if (nestedFieldsJSONArray != null) {
					makeFieldsLocalizable(
						nestedFieldsJSONArray, availableLanguageIds);
				}
			}
		}
	}

	protected String makeFieldsLocalizable(String definition)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(definition);

		JSONArray availableLanguageIdsJSONArray = jsonObject.getJSONArray(
			"availableLanguageIds");

		JSONArray fieldsJSONArray = jsonObject.getJSONArray("fields");

		makeFieldsLocalizable(fieldsJSONArray, availableLanguageIdsJSONArray);

		return jsonObject.toJSONString();
	}

	private boolean _isValueLocalizable(
		JSONObject fieldJSONObject, JSONArray availableLanguageIdsJSONArray) {

		JSONObject jsonObject = fieldJSONObject.getJSONObject("text");

		if (jsonObject == null) {
			return false;
		}

		for (int i = 0; i < availableLanguageIdsJSONArray.length(); i++) {
			if (!jsonObject.has(availableLanguageIdsJSONArray.getString(i))) {
				return false;
			}
		}

		return true;
	}

	private final JSONFactory _jsonFactory;

}