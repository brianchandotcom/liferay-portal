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

package com.liferay.data.engine.rest.internal.field;

import com.liferay.data.engine.rest.dto.v1_0.CustomProperty;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.CustomPropertyUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcela Cunha
 */
public abstract class FieldType {

	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		if (!jsonObject.has("name")) {
			throw new Exception("Name is required");
		}

		if (!jsonObject.has("type")) {
			throw new Exception("Type is required");
		}

		return new DataDefinitionField() {
			{
				fieldType = jsonObject.getString("type");
				indexable = jsonObject.getBoolean("indexable", true);

				label = (LocalizedValue[])GetterUtil.getObject(
					LocalizedValueUtil.getLocalizedProperty(
						"label", jsonObject),
					new LocalizedValue[0]);

				localizable = jsonObject.getBoolean("localizable", false);
				name = jsonObject.getString("name");
				repeatable = jsonObject.getBoolean("repeatable", false);

				customProperties = CustomPropertyUtil.addCustomProperty(
					customProperties, "showLabel",
					jsonObject.getBoolean("showLabel"));

				tip = (LocalizedValue[])GetterUtil.getObject(
					LocalizedValueUtil.getLocalizedProperty("tip", jsonObject),
					new LocalizedValue[0]);
			}
		};
	}

	public void includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> context,
		DataDefinitionField dataDefinitionField, boolean readOnly) {

		String languageId = LanguageUtil.getLanguageId(httpServletRequest);

		CustomProperty[] customProperties =
			dataDefinitionField.getCustomProperties();

		context.put(
			"dir",
			LanguageUtil.get(httpServletRequest, LanguageConstants.KEY_DIR));
		context.put("indexable", dataDefinitionField.getIndexable());
		context.put(
			"label",
			LocalizedValueUtil.getLocalizedValue(
				dataDefinitionField.getLabel(), languageId));
		context.put("localizable", dataDefinitionField.getLocalizable());
		context.put("name", dataDefinitionField.getName());
		context.put(
			"readOnly",
			CustomPropertyUtil.getBooleanCustomProperty(
				customProperties, "readOnly", false));
		context.put("repeatable", dataDefinitionField.getRepeatable());
		context.put(
			"required",
			CustomPropertyUtil.getBooleanCustomProperty(
				customProperties, "required", false));
		context.put(
			"showLabel",
			CustomPropertyUtil.getBooleanCustomProperty(
				customProperties, "showLabel", true));
		context.put(
			"tip",
			LocalizedValueUtil.getLocalizedValue(
				dataDefinitionField.getTip(), languageId));
		context.put("type", dataDefinitionField.getFieldType());
		context.put(
			"visible",
			CustomPropertyUtil.getBooleanCustomProperty(
				customProperties, "visible", true));
	}

	public JSONObject toJSONObject(
			DataDefinitionField dataDefinitionField, JSONFactory jsonFactory)
		throws Exception {

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new Exception("Name is required");
		}

		String type = dataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new Exception("Type is required");
		}

		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put("indexable", dataDefinitionField.getIndexable());

		LocalizedValueUtil.setLocalizedProperty(
			"label", jsonFactory, jsonObject,
			LocalizedValueUtil.toLocalizationMap(
				dataDefinitionField.getLabel()));

		jsonObject.put("localizable", dataDefinitionField.getLocalizable());

		jsonObject.put("name", name);

		jsonObject.put("repeatable", dataDefinitionField.getRepeatable());

		jsonObject.put(
			"showLabel",
			CustomPropertyUtil.getBooleanCustomProperty(
				dataDefinitionField.getCustomProperties(), "showLabel", true));

		LocalizedValueUtil.setLocalizedProperty(
			"tip", jsonFactory, jsonObject,
			LocalizedValueUtil.toLocalizationMap(dataDefinitionField.getTip()));

		jsonObject.put("type", type);

		return jsonObject;
	}

}