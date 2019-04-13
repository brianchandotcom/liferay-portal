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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertyUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcela Cunha
 */
public abstract class FieldType {

	public FieldType(
		DataDefinitionField dataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SoyDataFactory soyDataFactory) {

		this.dataDefinitionField = dataDefinitionField;
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		this.soyDataFactory = soyDataFactory;
	}

	public Map<String, Object> createContext() {
		Map<String, Object> context = new HashMap<>();

		context.put(
			"dir",
			LanguageUtil.get(httpServletRequest, LanguageConstants.KEY_DIR));
		context.put("indexable", dataDefinitionField.getIndexable());
		context.put(
			"label",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				dataDefinitionField.getLabel()));
		context.put("localizable", dataDefinitionField.getLocalizable());
		context.put("name", dataDefinitionField.getName());
		context.put(
			"readOnly",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "readOnly", false));
		context.put("repeatable", dataDefinitionField.getRepeatable());
		context.put(
			"required",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "required", false));
		context.put(
			"showLabel",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "showLabel", true));
		context.put(
			"tip",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(), dataDefinitionField.getTip()));
		context.put("type", dataDefinitionField.getFieldType());
		context.put(
			"visible",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "visible", true));

		addContext(context);

		return context;
	}

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
				customProperties = CustomPropertyUtil.add(
					customProperties, "showLabel",
					jsonObject.getBoolean("showLabel"));
				fieldType = jsonObject.getString("type");
				indexable = jsonObject.getBoolean("indexable", true);
				label = LocalizedValueUtil.toLocalizedValues(
					Optional.ofNullable(
						jsonObject.getJSONObject("label")
					).orElse(
						JSONFactoryUtil.createJSONObject()
					));
				localizable = jsonObject.getBoolean("localizable", false);
				name = jsonObject.getString("name");
				repeatable = jsonObject.getBoolean("repeatable", false);
				tip = LocalizedValueUtil.toLocalizedValues(
					Optional.ofNullable(
						jsonObject.getJSONObject("tip")
					).orElse(
						JSONFactoryUtil.createJSONObject()
					));
			}
		};
	}

	public JSONObject toJSONObject(DataDefinitionField dataDefinitionField)
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
			"showLabel",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "showLabel", true)
		).put(
			"tip", LocalizedValueUtil.toJSONObject(dataDefinitionField.getTip())
		).put(
			"type", type
		);
	}

	protected abstract void addContext(Map<String, Object> context);

	protected DataDefinitionField dataDefinitionField;
	protected HttpServletRequest httpServletRequest;
	protected HttpServletResponse httpServletResponse;
	protected SoyDataFactory soyDataFactory;

}