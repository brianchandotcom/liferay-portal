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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Lino Alves
 */
public class UpgradeDDMFormFieldValidation extends UpgradeProcess {

	public UpgradeDDMFormFieldValidation(JSONFactory jsonFactory) {
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

					ps2.setString(1, updateValidation(definition));

					long structureId = rs.getLong("structureId");

					ps2.setLong(2, structureId);

					ps2.addBatch();

					ps3.setLong(1, structureId);

					try (ResultSet rs2 = ps3.executeQuery()) {
						while (rs2.next()) {
							definition = rs2.getString("definition");

							ps4.setString(1, updateValidation(definition));

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

	protected String updateValidation(String definition)
		throws PortalException {

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray fieldsJSONArray = definitionJSONObject.getJSONArray("fields");

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			JSONObject validationJSONObject = fieldJSONObject.getJSONObject(
				"validation");

			JSONObject expressionJSONObject =
				validationJSONObject.getJSONObject("expression");

			String expressionValue = expressionJSONObject.getString("value");

			if (Validator.isNull(expressionValue)) {
				fieldJSONObject.remove("validation");

				continue;
			}

			if (Validator.isNotNull(expressionJSONObject.getString("name"))) {
				continue;
			}

			expressionJSONObject.put(
				"name", _getExpressionName(expressionValue));

			String parameterValue = _getParameterValueFromExpression(
				expressionValue);

			_addParameterValue(
				parameterValue, validationJSONObject,
				definitionJSONObject.getString("defaultLanguageId"));

			if (Validator.isNotNull(parameterValue)) {
				expressionJSONObject.put(
					"value",
					StringUtil.replace(
						expressionValue, parameterValue, "{parameter}"));
			}
		}

		return definitionJSONObject.toJSONString();
	}

	private void _addParameterValue(
		String value, JSONObject validation, String defaultLanguageId) {

		JSONObject parameter = validation.getJSONObject("parameter");

		if (!parameter.has(defaultLanguageId)) {
			parameter.put(defaultLanguageId, value);
		}
	}

	private String _getExpressionName(String expressionValue) {
		String name = "";

		if (expressionValue.startsWith("contains")) {
			name = "contains";
		}
		else if (expressionValue.startsWith("NOT(contains")) {
			name = "notContains";
		}
		else if (expressionValue.startsWith("isEmailAddress")) {
			name = "email";
		}
		else if (expressionValue.startsWith("match")) {
			name = "regularExpression";
		}
		else if (expressionValue.startsWith("isURL")) {
			name = "url";
		}

		return name;
	}

	private String _getParameterValueFromExpression(String expressionValue) {
		String[] parts = expressionValue.split("\"");

		if (parts.length > 1) {
			return parts[1];
		}

		return "";
	}

	private final JSONFactory _jsonFactory;

}