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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionRule;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static DataDefinition toDataDefinition(DDMStructure ddmStructure)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			ddmStructure.getDefinition());

		return new DataDefinition() {
			{
				dataDefinitionFields = JSONUtil.toArray(
					jsonObject.getJSONArray("fields"),
					fieldJSONObject -> _toDataDefinitionField(fieldJSONObject),
					DataDefinitionField.class);
				dataDefinitionKey = ddmStructure.getStructureKey();
				dataDefinitionRules = JSONUtil.toArray(
					jsonObject.getJSONArray("rules"),
					ruleJSONObject -> _toDataDefinitionRule(ruleJSONObject),
					DataDefinitionRule.class);
				dateCreated = ddmStructure.getCreateDate();
				dateModified = ddmStructure.getModifiedDate();
				description = LocalizedValueUtil.toStringObjectMap(
					ddmStructure.getDescriptionMap());
				id = ddmStructure.getStructureId();
				name = LocalizedValueUtil.toStringObjectMap(
					ddmStructure.getNameMap());
				siteId = ddmStructure.getGroupId();
				storageType = ddmStructure.getStorageType();
				userId = ddmStructure.getUserId();
			}
		};
	}

	public static String toJSON(DataDefinition dataDefinition)
		throws Exception {

		return JSONUtil.put(
			"fields",
			JSONUtil.toJSONArray(
				dataDefinition.getDataDefinitionFields(),
				dataDefinitionField -> _toJSONObject(dataDefinitionField))
		).put(
			"rules",
			JSONUtil.toJSONArray(
				dataDefinition.getDataDefinitionRules(),
				dataDefinitionRule -> _toJSONObject(dataDefinitionRule))
		).toString();
	}

	private static DataDefinitionField _toDataDefinitionField(
			JSONObject jsonObject)
		throws Exception {

		return new DataDefinitionField() {
			{
				if (jsonObject.has("predefinedValue")) {
					defaultValue = LocalizedValueUtil.toLocalizedValues(
						jsonObject.getJSONObject("predefinedValue"));
				}

				if (!jsonObject.has("type")) {
					throw new Exception("Type is required");
				}

				fieldType = jsonObject.getString("type");

				indexable = jsonObject.getBoolean("indexable", true);

				if (!jsonObject.has("label")) {
					throw new Exception("Label is required");
				}

				label = LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("label"));

				localizable = jsonObject.getBoolean("localizable", false);

				if (!jsonObject.has("name")) {
					throw new Exception("Name is required");
				}

				name = jsonObject.getString("name");

				repeatable = jsonObject.getBoolean("repeatable", false);

				if (!jsonObject.has("tip")) {
					throw new Exception("Tip is required");
				}

				tip = LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("tip"));
			}
		};
	}

	private static DataDefinitionRule _toDataDefinitionRule(
		JSONObject jsonObject) {

		return new DataDefinitionRule() {
			{
				dataDefinitionFieldNames = JSONUtil.toStringArray(
					jsonObject.getJSONArray("fields"));
				dataDefinitionRuleParameters =
					DataDefinitionRuleParameterUtil.
						toDataDefinitionRuleParameters(
							jsonObject.getJSONObject("parameters"));
				name = jsonObject.getString("name");
				ruleType = jsonObject.getString("ruleType");
			}
		};
	}

	private static JSONObject _toJSONObject(
			DataDefinitionField dataDefinitionField)
		throws Exception {

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new Exception("Name is required");
		}

		String type = dataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new Exception("Type is required");
		}

		return JSONUtil.put(
			"defaultValue", dataDefinitionField.getDefaultValue()
		).put(
			"indexable", dataDefinitionField.getIndexable()
		).put(
			"label",
			LocalizedValueUtil.toJSONObject(dataDefinitionField.getLabel())
		).put(
			"localizable", dataDefinitionField.getLocalizable()
		).put(
			"name", name
		).put(
			"repeatable", dataDefinitionField.getRepeatable()
		).put(
			"tip", LocalizedValueUtil.toJSONObject(dataDefinitionField.getTip())
		).put(
			"type", type
		);
	}

	private static JSONObject _toJSONObject(
			DataDefinitionRule dataDefinitionRule)
		throws Exception {

		return JSONUtil.put(
			"fields",
			JSONFactoryUtil.createJSONArray(
				dataDefinitionRule.getDataDefinitionFieldNames())
		).put(
			"name", dataDefinitionRule.getName()
		).put(
			"parameters",
			DataDefinitionRuleParameterUtil.toJSONObject(
				dataDefinitionRule.getDataDefinitionRuleParameters())
		).put(
			"ruleType", dataDefinitionRule.getRuleType()
		);
	}

}