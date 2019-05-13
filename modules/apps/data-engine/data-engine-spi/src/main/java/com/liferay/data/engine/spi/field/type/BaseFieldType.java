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

package com.liferay.data.engine.spi.field.type;

import com.liferay.data.engine.spi.definition.SPIDataDefinitionField;
import com.liferay.data.engine.spi.field.type.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcela Cunha
 */
public abstract class BaseFieldType implements FieldType {

	@Override
	public SPIDataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		if (!jsonObject.has("name")) {
			throw new Exception("Name is required");
		}

		if (!jsonObject.has("type")) {
			throw new Exception("Type is required");
		}

		SPIDataDefinitionField spiDataDefinitionField =
			new SPIDataDefinitionField();

		spiDataDefinitionField.setCustomProperties(
			new HashMap<String, Object>() {
				{
					put("showLabel", jsonObject.getBoolean("showLabel"));
				}
			});
		spiDataDefinitionField.setFieldType(jsonObject.getString("type"));
		spiDataDefinitionField.setIndexable(
			jsonObject.getBoolean("indexable", true));
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
		spiDataDefinitionField.setRepeatable(
			jsonObject.getBoolean("repeatable", false));
		spiDataDefinitionField.setTip(
			LocalizedValueUtil.toLocalizedValues(
				Optional.ofNullable(
					jsonObject.getJSONObject("tip")
				).orElse(
					JSONFactoryUtil.createJSONObject()
				)));

		return spiDataDefinitionField;
	}

	@Override
	public Map<String, Object> includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		Map<String, Object> context = new HashMap<>();

		context.put(
			"dir",
			LanguageUtil.get(httpServletRequest, LanguageConstants.KEY_DIR));
		context.put("indexable", spiDataDefinitionField.getIndexable());
		context.put(
			"label",
			MapUtil.getString(
				spiDataDefinitionField.getLabel(),
				LocaleUtil.toLanguageId(httpServletRequest.getLocale())));
		context.put("localizable", spiDataDefinitionField.getLocalizable());
		context.put("name", spiDataDefinitionField.getName());
		context.put(
			"readOnly",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "readOnly",
				false));
		context.put("repeatable", spiDataDefinitionField.getRepeatable());
		context.put(
			"required",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "required",
				false));
		context.put(
			"showLabel",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "showLabel",
				true));
		context.put(
			"tip",
			MapUtil.getString(
				spiDataDefinitionField.getTip(),
				LocaleUtil.toLanguageId(httpServletRequest.getLocale())));
		context.put("type", spiDataDefinitionField.getFieldType());
		context.put(
			"visible",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "visible", true));

		includeContext(
			context, httpServletRequest, httpServletResponse,
			spiDataDefinitionField);

		return context;
	}

	@Override
	public JSONObject toJSONObject(
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
			"indexable", spiDataDefinitionField.getIndexable()
		).put(
			"label",
			LocalizedValueUtil.toJSONObject(spiDataDefinitionField.getLabel())
		).put(
			"localizable", spiDataDefinitionField.getLocalizable()
		).put(
			"name", name
		).put(
			"repeatable", spiDataDefinitionField.getRepeatable()
		).put(
			"showLabel",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "showLabel", true)
		).put(
			"tip",
			LocalizedValueUtil.toJSONObject(spiDataDefinitionField.getTip())
		).put(
			"type", type
		);
	}

	protected abstract void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField);

}