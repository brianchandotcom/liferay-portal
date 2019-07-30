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

import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionRule;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static DataDefinition toDataDefinition(
			DDMStructure ddmStructure, FieldTypeTracker fieldTypeTracker)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			ddmStructure.getDefinition());

		return new DataDefinition() {
			{
				dataDefinitionFields = JSONUtil.toArray(
					jsonObject.getJSONArray("fields"),
					fieldJSONObject -> _toDataDefinitionField(
						fieldTypeTracker, fieldJSONObject),
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

	public static String toJSON(
			DataDefinition dataDefinition, FieldTypeTracker fieldTypeTracker)
		throws Exception {

		return JSONUtil.put(
			"fields",
			JSONUtil.toJSONArray(
				dataDefinition.getDataDefinitionFields(),
				dataDefinitionField -> _toJSONObject(
					dataDefinitionField, fieldTypeTracker))
		).put(
			"rules",
			JSONUtil.toJSONArray(
				dataDefinition.getDataDefinitionRules(),
				dataDefinitionRule -> _toJSONObject(dataDefinitionRule))
		).toString();
	}

	private static DataDefinitionField _toDataDefinitionField(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		if (jsonObject.has("type")) {
			FieldType fieldType = fieldTypeTracker.getFieldType(
				jsonObject.getString("type"));

			return DataDefinitionFieldUtil.toDataDefinitionField(
				fieldType.deserialize(fieldTypeTracker, jsonObject));
		}

		return new DataDefinitionField();
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
			DataDefinitionField dataDefinitionField,
			FieldTypeTracker fieldTypeTracker)
		throws Exception {

		FieldType fieldType = fieldTypeTracker.getFieldType(
			dataDefinitionField.getFieldType());

		return fieldType.toJSONObject(
			fieldTypeTracker,
			DataDefinitionFieldUtil.toSPIDataDefinitionField(
				dataDefinitionField));
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