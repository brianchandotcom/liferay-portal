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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.internal.util.DDMImpl;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.BaseFieldRenderer;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class StringFieldRenderer extends BaseFieldRenderer {

	protected StringFieldRenderer(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		String fieldType = getFieldType(field);

		List<String> values = new ArrayList<>();

		for (Serializable value : field.getValues(locale)) {
			String valueString = String.valueOf(value);

			if (Validator.isNull(valueString)) {
				continue;
			}

			if (fieldType.equals(DDMImpl.TYPE_SELECT)) {
				valueString = handleSelectFieldValue(
					field, valueString, locale);
			}
			else if (fieldType.equals(DDMImpl.TYPE_RADIO)) {
				return handleRadioFieldValue(
					field, String.valueOf(value), locale);
			}

			values.add(valueString);
		}

		return StringUtil.merge(values, StringPool.COMMA_AND_SPACE);
	}

	@Override
	protected String doRender(Field field, Locale locale, int valueIndex)
		throws Exception {

		Serializable value = field.getValue(locale, valueIndex);

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		String fieldType = getFieldType(field);

		String valueString = String.valueOf(value);

		if (fieldType.equals(DDMImpl.TYPE_SELECT)) {
			return handleSelectFieldValue(field, valueString, locale);
		}
		else if (fieldType.equals(DDMImpl.TYPE_RADIO)) {
			return handleRadioFieldValue(field, valueString, locale);
		}

		return valueString;
	}

	protected LocalizedValue getFieldOptionLabel(
			Field field, String optionValue)
		throws Exception {

		DDMStructure ddmStructure = field.getDDMStructure();

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			field.getName());

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		return ddmFormFieldOptions.getOptionLabels(optionValue);
	}

	protected String getFieldType(Field field) throws Exception {
		DDMStructure ddmStructure = field.getDDMStructure();

		return ddmStructure.getFieldType(field.getName());
	}

	protected String handleRadioFieldValue(
			Field field, String value, Locale locale)
		throws Exception {

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		LocalizedValue label = getFieldOptionLabel(field, value);

		if (label == null) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(label.getString(locale));
	}

	protected String handleSelectFieldValue(
			Field field, String json, Locale locale)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		if (jsonArray.length() == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(jsonArray.length() * 2);

		for (int i = 0; i < jsonArray.length(); i++) {
			LocalizedValue label = getFieldOptionLabel(
				field, jsonArray.getString(i));

			if (label == null) {
				continue;
			}

			sb.append(label.getString(locale));
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private final JSONFactory _jsonFactory;

}