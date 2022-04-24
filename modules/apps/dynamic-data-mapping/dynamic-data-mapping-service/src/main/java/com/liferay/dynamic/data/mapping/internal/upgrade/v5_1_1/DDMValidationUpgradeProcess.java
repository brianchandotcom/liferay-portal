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

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_1;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Rodrigo Paulino
 */
public class DDMValidationUpgradeProcess extends UpgradeProcess {

	public DDMValidationUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement1 =
				connection.prepareStatement(
					"select structureId, definition from DDMStructure where " +
						"classNameId = ? ");
			PreparedStatement selectPreparedStatement2 =
				connection.prepareStatement(
					"select structureVersionId, definition from " +
						"DDMStructureVersion where structureId = ?");
			PreparedStatement updatePreparedStatement1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement updatePreparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			_upgradeDDMStructure(
				selectPreparedStatement1, selectPreparedStatement2,
				updatePreparedStatement1, updatePreparedStatement2);

			updatePreparedStatement1.executeBatch();

			updatePreparedStatement2.executeBatch();
		}
	}

	private boolean _upgradeDateValidation(
		JSONObject fieldJSONObject, boolean upgraded) {

		if (Objects.equals(
				DDMFormFieldTypeConstants.DATE,
				fieldJSONObject.getString("type"))) {

			JSONObject validationJSONObject = fieldJSONObject.getJSONObject(
				"validation");

			if (validationJSONObject == null) {
				return upgraded;
			}

			JSONObject expressionJSONObject =
				validationJSONObject.getJSONObject("expression");

			if (Objects.equals(
					expressionJSONObject.getString("name"), "dateRange") &&
				StringUtil.startsWith(
					expressionJSONObject.getString("value"), "dateRange")) {

				String fieldName = fieldJSONObject.getString("name");

				expressionJSONObject.put(
					"value",
					StringBundler.concat(
						"futureDates(", fieldName,
						", \"{parameter}\") AND pastDates(", fieldName,
						", \"{parameter}\")"));

				return true;
			}
		}

		return upgraded;
	}

	private void _upgradeDDMStructure(
			PreparedStatement selectPreparedStatement1,
			PreparedStatement selectPreparedStatement2,
			PreparedStatement updatePreparedStatement1,
			PreparedStatement updatePreparedStatement2)
		throws Exception {

		selectPreparedStatement1.setLong(
			1,
			PortalUtil.getClassNameId(
				"com.liferay.dynamic.data.mapping.model.DDMFormInstance"));

		try (ResultSet resultSet = selectPreparedStatement1.executeQuery()) {
			while (resultSet.next()) {
				JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
					resultSet.getString("definition"));

				if (_upgradeDefinition(definitionJSONObject)) {
					updatePreparedStatement1.setString(
						1, definitionJSONObject.toString());

					long structureId = resultSet.getLong("structureId");

					updatePreparedStatement1.setLong(2, structureId);

					updatePreparedStatement1.addBatch();

					_upgradeDDMStructureVersion(
						selectPreparedStatement2, structureId,
						updatePreparedStatement2);
				}
			}
		}
	}

	private void _upgradeDDMStructureVersion(
			PreparedStatement selectPreparedStatement, long structureId,
			PreparedStatement updatePreparedStatement)
		throws Exception {

		selectPreparedStatement.setLong(1, structureId);

		try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
			while (resultSet.next()) {
				JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
					resultSet.getString("definition"));

				if (_upgradeDefinition(definitionJSONObject)) {
					updatePreparedStatement.setString(
						1, definitionJSONObject.toString());
					updatePreparedStatement.setLong(
						2, resultSet.getLong("structureVersionId"));

					updatePreparedStatement.addBatch();
				}
			}
		}
	}

	private boolean _upgradeDefinition(JSONObject definitionJSONObject) {
		boolean upgraded = false;

		JSONArray fieldsJSONArray = definitionJSONObject.getJSONArray("fields");

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			upgraded = _upgradeDateValidation(
				fieldsJSONArray.getJSONObject(i), upgraded);
		}

		return upgraded;
	}

	private final JSONFactory _jsonFactory;

}