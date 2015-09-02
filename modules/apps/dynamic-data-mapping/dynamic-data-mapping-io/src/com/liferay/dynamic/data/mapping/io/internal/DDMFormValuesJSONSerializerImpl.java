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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesJSONSerializerImpl
	implements DDMFormValuesJSONSerializer {

	@Override
	public String serialize(DDMFormValues ddmFormValues) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addAvailableLanguageIds(
			jsonObject, ddmFormValues.getAvailableLocales());
		addDefaultLanguageId(jsonObject, ddmFormValues.getDefaultLocale());
		addFieldValues(jsonObject, ddmFormValues.getDDMFormFieldValues());

		return jsonObject.toString();
	}

	protected void addAvailableLanguageIds(
		JSONObject jsonObject, Set<Locale> availableLocales) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : availableLocales) {
			jsonArray.put(LocaleUtil.toLanguageId(availableLocale));
		}

		jsonObject.put("availableLanguageIds", jsonArray);
	}

	protected void addDefaultLanguageId(
		JSONObject jsonObject, Locale defaultLocale) {

		jsonObject.put(
			"defaultLanguageId", LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void addFieldValues(
		JSONObject jsonObject, List<DDMFormFieldValue> ddmFormFieldValues) {

		jsonObject.put("fieldValues", toJSONArray(ddmFormFieldValues));
	}

	protected void addNestedFieldValues(
		JSONObject jsonObject,
		List<DDMFormFieldValue> nestedDDMFormFieldValues) {

		if (nestedDDMFormFieldValues.isEmpty()) {
			return;
		}

		jsonObject.put(
			"nestedFieldValues", toJSONArray(nestedDDMFormFieldValues));
	}

	protected void addValue(JSONObject jsonObject, Value value) {
		if (value == null) {
			return;
		}

		if (value.isLocalized()) {
			jsonObject.put("value", toJSONObject(value));
		}
		else {
			jsonObject.put("value", value.getString(LocaleUtil.ROOT));
		}
	}

	protected JSONArray toJSONArray(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			jsonArray.put(toJSONObject(ddmFormFieldValue));
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(DDMFormFieldValue ddmFormFieldValue) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("instanceId", ddmFormFieldValue.getInstanceId());
		jsonObject.put("name", ddmFormFieldValue.getName());

		addNestedFieldValues(
			jsonObject, ddmFormFieldValue.getNestedDDMFormFieldValues());
		addValue(jsonObject, ddmFormFieldValue.getValue());

		return jsonObject;
	}

	protected JSONObject toJSONObject(Value value) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale : value.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				value.getString(availableLocale));
		}

		return jsonObject;
	}

}