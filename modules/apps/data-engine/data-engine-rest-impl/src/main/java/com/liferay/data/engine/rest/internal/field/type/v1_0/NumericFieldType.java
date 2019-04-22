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
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.NumericFieldUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gabriel Albuquerque
 */
public class NumericFieldType extends BaseFieldType {

	public NumericFieldType(
		DataDefinitionField dataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SoyDataFactory soyDataFactory) {

		super(
			dataDefinitionField, httpServletRequest, httpServletResponse,
			soyDataFactory);
	}

	@Override
	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		DataDefinitionField dataDefinitionField = super.deserialize(jsonObject);

		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "dataType",
				jsonObject.getString("dataType")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "placeholder",
				LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("placeholder"))));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "predefinedValue",
				LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("predefinedValue"))));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "tooltip",
				LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("tooltip"))));

		return dataDefinitionField;
	}

	@Override
	public JSONObject toJSONObject() throws Exception {
		JSONObject jsonObject = super.toJSONObject();

		jsonObject.put(
			"dataType",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(), "dataType")
		).put(
			"placeholder",
			CustomPropertyUtil.getLocalizedValue(
				dataDefinitionField.getCustomProperties(), "placeholder")
		).put(
			"predefinedValue",
			CustomPropertyUtil.getLocalizedValue(
				dataDefinitionField.getCustomProperties(), "predefinedValue")
		).put(
			"tooltip",
			CustomPropertyUtil.getLocalizedValue(
				dataDefinitionField.getCustomProperties(), "tooltip")
		);

		return jsonObject;
	}

	@Override
	protected void addContext(Map<String, Object> context) {
		context.put(
			"dataType",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(), "dataType",
				"decimal"));
		context.put(
			"placeholder",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertyUtil.getLocalizedValue(
					dataDefinitionField.getCustomProperties(), "placeholder")));
		context.put(
			"predefinedValue",
			getFormattedValue(
				LocalizedValueUtil.getLocalizedValue(
					httpServletRequest.getLocale(),
					CustomPropertyUtil.getLocalizedValue(
						dataDefinitionField.getCustomProperties(),
						"predefinedValue")),
				httpServletRequest.getLocale()));
		context.put("symbols", getSymbolsMap(httpServletRequest.getLocale()));
		context.put(
			"tooltip",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertyUtil.getLocalizedValue(
					dataDefinitionField.getCustomProperties(), "tooltip")));
		context.put(
			"value",
			getFormattedValue(
				CustomPropertyUtil.getString(
					dataDefinitionField.getCustomProperties(), "value"),
				httpServletRequest.getLocale()));
	}

	protected String getFormattedValue(Object value, Locale locale) {

		if (Validator.isNull(value)||StringUtil.equals((String)value, "NaN")) {
			return StringPool.BLANK;
		}

		DecimalFormat numberFormat = NumericFieldUtil.getNumberFormat(locale);

		return numberFormat.format(GetterUtil.getNumber(value));
	}

	protected Map<String, String> getSymbolsMap(Locale locale) {
		DecimalFormat formatter = NumericFieldUtil.getNumberFormat(locale);

		DecimalFormatSymbols decimalFormatSymbols =
			formatter.getDecimalFormatSymbols();

		Map<String, String> symbolsMap = new HashMap<>();

		symbolsMap.put(
			"decimalSymbol",
			String.valueOf(decimalFormatSymbols.getDecimalSeparator()));
		symbolsMap.put(
			"thousandsSeparator",
			String.valueOf(decimalFormatSymbols.getGroupingSeparator()));

		return symbolsMap;
	}

}