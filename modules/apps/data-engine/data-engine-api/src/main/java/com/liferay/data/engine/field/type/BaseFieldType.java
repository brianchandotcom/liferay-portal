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

package com.liferay.data.engine.field.type;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author     Marcela Cunha
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public abstract class BaseFieldType implements FieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		if (!jsonObject.has("name")) {
			throw new Exception("Name is required");
		}

		if (!jsonObject.has("type")) {
			throw new Exception("Type is required");
		}

		SPIDataDefinitionField spiDataDefinitionField =
			new SPIDataDefinitionField();

		spiDataDefinitionField.setDefaultValue(
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("defaultValue")));
		spiDataDefinitionField.setFieldType(jsonObject.getString("type"));
		spiDataDefinitionField.setIndexable(
			jsonObject.getBoolean("indexable", true));
		spiDataDefinitionField.setIndexType(jsonObject.getString("indexType"));
		spiDataDefinitionField.setLabel(
			LocalizedValueUtil.toLocalizedValues(
				Optional.ofNullable(
					jsonObject.getJSONObject("label")
				).orElse(
					JSONFactoryUtil.createJSONObject()
				)));
		spiDataDefinitionField.setLocalizable(
			jsonObject.getBoolean("localizable", false));
		spiDataDefinitionField.setName(jsonObject.getString("name"));

		if (jsonObject.has("nestedDataDefinitionFields")) {
			spiDataDefinitionField.setNestedSPIDataDefinitionFields(
				JSONUtil.toArray(
					(JSONArray)GetterUtil.getObject(
						jsonObject.getJSONArray("nestedDataDefinitionFields"),
						JSONFactoryUtil.createJSONArray()),
					nestedDataDefinitionFieldJSONObject -> {
						if (jsonObject.has("type")) {
							FieldType fieldType = fieldTypeTracker.getFieldType(
								nestedDataDefinitionFieldJSONObject.getString(
									"type"));

							return fieldType.deserialize(
								fieldTypeTracker,
								nestedDataDefinitionFieldJSONObject);
						}

						return null;
					},
					SPIDataDefinitionField.class));
		}

		spiDataDefinitionField.setReadOnly(jsonObject.getBoolean("readOnly"));
		spiDataDefinitionField.setRepeatable(
			jsonObject.getBoolean("repeatable"));
		spiDataDefinitionField.setRequired(jsonObject.getBoolean("required"));
		spiDataDefinitionField.setShowlabel(
			jsonObject.getBoolean("showLabel", true));
		spiDataDefinitionField.setTip(
			LocalizedValueUtil.toLocalizedValues(
				Optional.ofNullable(
					jsonObject.getJSONObject("tip")
				).orElse(
					JSONFactoryUtil.createJSONObject()
				)));
		spiDataDefinitionField.setVisible(
			jsonObject.getBoolean("visible", true));

		return spiDataDefinitionField;
	}

	@Override
	public Map<String, Object> includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		Map<String, Object> context = HashMapBuilder.<String, Object>put(
			"dir",
			LanguageUtil.get(httpServletRequest, LanguageConstants.KEY_DIR)
		).put(
			"fieldName", spiDataDefinitionField.getName()
		).put(
			"indexable", spiDataDefinitionField.getIndexable()
		).put(
			"indexType", spiDataDefinitionField.getIndexType()
		).put(
			"label",
			MapUtil.getString(
				spiDataDefinitionField.getLabel(),
				LocaleUtil.toLanguageId(httpServletRequest.getLocale()))
		).put(
			"localizable", spiDataDefinitionField.getLocalizable()
		).put(
			"name", spiDataDefinitionField.getName()
		).put(
			"nestedDataDefinitionFields",
			spiDataDefinitionField.getNestedSPIDataDefinitionFields()
		).put(
			"predefinedValue",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				spiDataDefinitionField.getDefaultValue())
		).put(
			"readOnly", spiDataDefinitionField.getReadOnly()
		).put(
			"repeatable", spiDataDefinitionField.getRepeatable()
		).put(
			"required", spiDataDefinitionField.getRequired()
		).put(
			"showLabel", spiDataDefinitionField.getShowLabel()
		).put(
			"tip",
			MapUtil.getString(
				spiDataDefinitionField.getTip(),
				LocaleUtil.toLanguageId(httpServletRequest.getLocale()))
		).put(
			"type", spiDataDefinitionField.getFieldType()
		).put(
			"visible", spiDataDefinitionField.getVisible()
		).build();

		includeContext(
			context, httpServletRequest, httpServletResponse,
			spiDataDefinitionField);

		return context;
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		String name = spiDataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new Exception("Name is required");
		}

		String type = spiDataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new Exception("Type is required");
		}

		return JSONUtil.put(
			"defaultValue",
			LocalizedValueUtil.toJSONObject(
				spiDataDefinitionField.getDefaultValue())
		).put(
			"indexable", spiDataDefinitionField.getIndexable()
		).put(
			"indexType", spiDataDefinitionField.getIndexType()
		).put(
			"label",
			LocalizedValueUtil.toJSONObject(spiDataDefinitionField.getLabel())
		).put(
			"localizable", spiDataDefinitionField.getLocalizable()
		).put(
			"name", name
		).put(
			"nestedDataDefinitionFields",
			JSONUtil.toJSONArray(
				spiDataDefinitionField.getNestedSPIDataDefinitionFields(),
				nestedSPIDataDefinitionField -> {
					FieldType fieldType = fieldTypeTracker.getFieldType(
						nestedSPIDataDefinitionField.getFieldType());

					return fieldType.toJSONObject(
						fieldTypeTracker, nestedSPIDataDefinitionField);
				})
		).put(
			"readOnly", spiDataDefinitionField.getReadOnly()
		).put(
			"repeatable", spiDataDefinitionField.getRepeatable()
		).put(
			"required", spiDataDefinitionField.getRequired()
		).put(
			"showLabel", spiDataDefinitionField.getShowLabel()
		).put(
			"tip",
			LocalizedValueUtil.toJSONObject(spiDataDefinitionField.getTip())
		).put(
			"type", type
		).put(
			"visible", spiDataDefinitionField.getVisible()
		);
	}

	protected abstract void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField);

}