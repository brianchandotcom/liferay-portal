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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_1;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;

/**
 * @author Rodrigo Paulino
 */
public class UpgradeDDMFormField extends UpgradeProcess {

	public UpgradeDDMFormField(JSONFactory jsonFactory) {
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

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.dynamic.data.mapping.model.DDMFormInstance"));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString("definition");

					ps2.setString(1, _upgradeDefinition(definition));

					long structureId = rs.getLong("structureId");

					ps2.setLong(2, structureId);

					ps2.addBatch();

					ps3.setLong(1, structureId);

					try (ResultSet rs2 = ps3.executeQuery()) {
						while (rs2.next()) {
							definition = rs2.getString("definition");

							ps4.setString(1, _upgradeDefinition(definition));

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

	private void _convertStringToJSONArray(JSONObject jsonObject, String key) {
		Object value = jsonObject.get(key);

		if (value instanceof String) {
			String valueString = (String)value;

			if (!valueString.startsWith(StringPool.OPEN_BRACKET) ||
				!valueString.endsWith(StringPool.CLOSE_BRACKET)) {

				jsonObject.put(
					key,
					JSONUtil.putAll(
						StringUtil.split(valueString)
					).toString());
			}
		}
	}

	private String _upgradeDefinition(String definition)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(definition);

		_upgradeFields(jsonObject.getJSONArray("fields"));

		return jsonObject.toJSONString();
	}

	private void _upgradeFields(JSONArray fieldsJSONArray) {
		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);

			String type = jsonObject.getString("type");

			if (type.equals("checkbox_multiple") || type.equals("radio") ||
				type.equals("select")) {

				JSONObject predefinedValueJSONObject = jsonObject.getJSONObject(
					"predefinedValue");

				Iterator<String> iterator = predefinedValueJSONObject.keys();

				while (iterator.hasNext()) {
					String languageKey = iterator.next();

					_convertStringToJSONArray(
						predefinedValueJSONObject, languageKey);
				}
			}

			if (type.equals("select")) {
				_convertStringToJSONArray(jsonObject, "dataSourceType");
			}

			JSONArray nestedFieldsJSONArray = jsonObject.getJSONArray(
				"nestedFields");

			if (nestedFieldsJSONArray != null) {
				_upgradeFields(nestedFieldsJSONArray);
			}
		}
	}

	private final JSONFactory _jsonFactory;

}