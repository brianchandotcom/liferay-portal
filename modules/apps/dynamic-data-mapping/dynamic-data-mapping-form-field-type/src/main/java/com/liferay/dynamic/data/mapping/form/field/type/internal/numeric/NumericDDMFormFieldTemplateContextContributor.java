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

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.util.NumericDDMFormFieldUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.NUMERIC,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		NumericDDMFormFieldTemplateContextContributor.class
	}
)
public class NumericDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		String dataType = GetterUtil.getString(
			getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext, "dataType"));
		boolean inputMask = GetterUtil.getBoolean(
			getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext, "inputMask"));
		Locale locale = ddmFormFieldRenderingContext.getLocale();

		if (inputMask && StringUtil.equals(dataType, "double")) {
			parameters.putAll(
				_getNumericInputMaskParameters(
					_getPropertyValue(
						ddmFormField, ddmFormFieldRenderingContext, locale,
						"numericInputMask")));
		}
		else {
			parameters.put("symbols", getSymbolsMap(locale));
		}

		return HashMapBuilder.<String, Object>put(
			"confirmationErrorMessage",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, locale, "confirmationErrorMessage")
		).put(
			"confirmationLabel",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, locale, "confirmationLabel")
		).put(
			"dataType", dataType
		).put(
			"direction", ddmFormField.getProperty("direction")
		).put(
			"hideField",
			GetterUtil.getBoolean(ddmFormField.getProperty("hideField"))
		).put(
			"inputMask", inputMask
		).put(
			"inputMaskFormat",
			_getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext, locale,
				"inputMaskFormat")
		).put(
			"numericInputMask",
			_getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext, locale,
				"numericInputMask")
		).put(
			"placeholder",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, locale, "placeholder")
		).put(
			"predefinedValue",
			getFormattedValue(
				ddmFormFieldRenderingContext, locale,
				DDMFormFieldTypeUtil.getPropertyValue(
					ddmFormField, ddmFormFieldRenderingContext.getLocale(),
					"predefinedValue"))
		).put(
			"requireConfirmation",
			GetterUtil.getBoolean(
				ddmFormField.getProperty("requireConfirmation"))
		).put(
			"tooltip",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, locale, "tooltip")
		).put(
			"value",
			() -> {
				String value = HtmlUtil.extractText(
					ddmFormFieldRenderingContext.getValue());

				if (Objects.equals(value, "NaN")) {
					return StringPool.BLANK;
				}

				return getFormattedValue(
					ddmFormFieldRenderingContext, locale, value);
			}
		).putAll(
			parameters
		).build();
	}

	protected String getFormattedValue(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		Locale locale, String value) {

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		if (GetterUtil.getBoolean(
				ddmFormFieldRenderingContext.getProperty("valueChanged"))) {

			DecimalFormat decimalFormat =
				NumericDDMFormFieldUtil.getDecimalFormat(locale);

			return decimalFormat.format(GetterUtil.getNumber(value));
		}

		return value;
	}

	protected Object getPropertyValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		String propertyName) {

		Map<String, Object> changedProperties =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (MapUtil.isNotEmpty(changedProperties)) {
			Object propertyValue = changedProperties.get(propertyName);

			if (propertyValue != null) {
				return propertyValue;
			}
		}

		return ddmFormField.getProperty(propertyName);
	}

	protected Map<String, String> getSymbolsMap(Locale locale) {
		DecimalFormat decimalFormat = NumericDDMFormFieldUtil.getDecimalFormat(
			locale);

		DecimalFormatSymbols decimalFormatSymbols =
			decimalFormat.getDecimalFormatSymbols();

		return HashMapBuilder.put(
			"decimalSymbol",
			String.valueOf(decimalFormatSymbols.getDecimalSeparator())
		).put(
			"thousandsSeparator",
			String.valueOf(decimalFormatSymbols.getGroupingSeparator())
		).build();
	}

	private Map<String, Object> _getNumericInputMaskParameters(
		String numericInputMask) {

		try {
			JSONObject numericInputMaskJSONObject =
				_jsonFactory.createJSONObject(numericInputMask);

			return HashMapBuilder.<String, Object>put(
				"append", numericInputMaskJSONObject.getString("append")
			).put(
				"appendType", numericInputMaskJSONObject.getString("appendType")
			).put(
				"decimalPlaces",
				numericInputMaskJSONObject.getInt("decimalPlaces")
			).put(
				"symbols", numericInputMaskJSONObject.getJSONObject("symbols")
			).build();
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}

			return new HashMap<>();
		}
	}

	private String _getPropertyValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		Locale locale, String propertyName) {

		Map<String, Object> changedProperties =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (MapUtil.isNotEmpty(changedProperties)) {
			Object changedPropertyValue = changedProperties.get(propertyName);

			if (changedPropertyValue instanceof LocalizedValue) {
				LocalizedValue localizedValue =
					(LocalizedValue)changedPropertyValue;

				String propertyValue = localizedValue.getString(locale);

				if (propertyValue != null) {
					return propertyValue;
				}
			}
		}

		return DDMFormFieldTypeUtil.getPropertyValue(
			ddmFormField, locale, propertyName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NumericDDMFormFieldTemplateContextContributor.class);

	@Reference
	private JSONFactory _jsonFactory;

}