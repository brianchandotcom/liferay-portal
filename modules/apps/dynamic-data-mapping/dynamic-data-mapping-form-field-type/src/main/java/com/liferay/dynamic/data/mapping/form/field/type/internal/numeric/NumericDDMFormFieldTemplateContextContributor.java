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
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=numeric",
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

		LocalizedValue placeholder = (LocalizedValue)ddmFormField.getProperty(
			"placeholder");

		Locale locale = ddmFormFieldRenderingContext.getLocale();

		String predefinedValue = getValueString(
			ddmFormField.getPredefinedValue(), locale,
			ddmFormFieldRenderingContext);

		LocalizedValue tooltip = (LocalizedValue)ddmFormField.getProperty(
			"tooltip");

		Map<String, Object> parameters = HashMapBuilder.<String, Object>put(
			"dataType", getDataType(ddmFormField, ddmFormFieldRenderingContext)
		).put(
			"placeholder",
			getValueString(placeholder, locale, ddmFormFieldRenderingContext)
		).put(
			"predefinedValue",
			getFormattedValue(
				ddmFormFieldRenderingContext, locale, predefinedValue)
		).put(
			"symbols", getSymbolsMap(locale)
		).put(
			"tooltip",
			getValueString(tooltip, locale, ddmFormFieldRenderingContext)
		).build();

		String value = HtmlUtil.extractText(
			ddmFormFieldRenderingContext.getValue());

		if (Objects.equals(value, "NaN")) {
			parameters.put("value", "");
		}
		else {
			parameters.put(
				"value",
				getFormattedValue(ddmFormFieldRenderingContext, locale, value));
		}

		return parameters;
	}

	protected String getDataType(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> changedProperties =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (MapUtil.isNotEmpty(changedProperties)) {
			String dataType = (String)changedProperties.get("dataType");

			if (dataType != null) {
				return dataType;
			}
		}

		return ddmFormField.getDataType();
	}

	protected String getFormattedValue(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		Locale locale, String value) {

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		if (GetterUtil.getBoolean(
				ddmFormFieldRenderingContext.getProperty("valueChanged"))) {

			DecimalFormat numberFormat =
				NumericDDMFormFieldUtil.getNumberFormat(locale);

			return numberFormat.format(GetterUtil.getNumber(value));
		}

		return value;
	}

	protected Map<String, String> getSymbolsMap(Locale locale) {
		DecimalFormat formatter = NumericDDMFormFieldUtil.getNumberFormat(
			locale);

		DecimalFormatSymbols decimalFormatSymbols =
			formatter.getDecimalFormatSymbols();

		return HashMapBuilder.put(
			"decimalSymbol",
			String.valueOf(decimalFormatSymbols.getDecimalSeparator())
		).put(
			"thousandsSeparator",
			String.valueOf(decimalFormatSymbols.getGroupingSeparator())
		).build();
	}

	protected String getValueString(
		Value value, Locale locale,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (value == null) {
			return StringPool.BLANK;
		}

		String valueString = value.getString(locale);

		if (ddmFormFieldRenderingContext.isViewMode()) {
			valueString = HtmlUtil.extractText(value.getString(locale));
		}

		return valueString;
	}

}