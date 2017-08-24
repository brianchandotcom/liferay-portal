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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class DDMFormValuesHelperImpl implements DDMFormValuesHelper {

	public static final String FIELD_CP_DEFINITION_OPTION_REL_ID =
		"cpDefinitionOptionRelId";

	public static final String FIELD_CP_DEFINITION_OPTION_VALUE_REL_ID =
		"cpDefinitionOptionValueRelId";

	public static final String FIELD_VALUE_PREFIX =
		"_cpDefinitionOptionValueRelId_";

	@Override
	public DDMFormValues deserialize(
			DDMForm ddmForm, String json, Locale locale)
		throws PortalException {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(locale);
		ddmFormValues.setDefaultLocale(locale);

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(
				jsonArray.getJSONObject(i));

			ddmFormFieldValues.add(ddmFormFieldValue);
		}

		if (ddmFormFieldValues.isEmpty()) {
			return null;
		}

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		return ddmFormValues;
	}

	public boolean equals(String json1, String json2) throws PortalException {
		JSONArray jsonArray1 = _jsonFactory.createJSONArray(json1);
		JSONArray jsonArray2 = _jsonFactory.createJSONArray(json2);

		if (jsonArray1.length() != jsonArray2.length()) {
			return false;
		}

		Set<KeyValuePair> set1 = _toSet(jsonArray1);
		Set<KeyValuePair> set2 = _toSet(jsonArray2);

		return set1.equals(set2);
	}

	@Override
	public String serialize(DDMFormValues ddmFormValues) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		if (ddmFormValues == null) {
			return jsonArray.toString();
		}

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			JSONObject jsonObject = _toJSONObject(ddmFormFieldValue);

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	protected DDMFormFieldValue getDDMFormFieldValue(JSONObject jsonObject) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(
			jsonObject.getString(FIELD_CP_DEFINITION_OPTION_REL_ID));

		String cpDefinitionOptionValueRelId = jsonObject.getString(
			FIELD_CP_DEFINITION_OPTION_VALUE_REL_ID);

		if (Validator.isNotNull(cpDefinitionOptionValueRelId)) {
			String ddmFormFieldValueValue =
				FIELD_VALUE_PREFIX + cpDefinitionOptionValueRelId;

			ddmFormFieldValue.setValue(
				new UnlocalizedValue(ddmFormFieldValueValue));
		}

		return ddmFormFieldValue;
	}

	private JSONObject _toJSONObject(DDMFormFieldValue ddmFormFieldValue) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			FIELD_CP_DEFINITION_OPTION_REL_ID, ddmFormFieldValue.getName());

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(LocaleUtil.ROOT);

		valueString = StringUtil.replace(
			valueString, FIELD_VALUE_PREFIX, StringPool.BLANK);

		jsonObject.put(FIELD_CP_DEFINITION_OPTION_VALUE_REL_ID, valueString);

		return jsonObject;
	}

	private Set<KeyValuePair> _toSet(JSONArray jsonArray) {
		Set<KeyValuePair> set = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject1 = jsonArray.getJSONObject(i);

			String key = jsonObject1.getString(
				FIELD_CP_DEFINITION_OPTION_REL_ID);
			String value = jsonObject1.getString(
				FIELD_CP_DEFINITION_OPTION_VALUE_REL_ID);

			set.add(new KeyValuePair(key, value));
		}

		return set;
	}

	@Reference
	private JSONFactory _jsonFactory;

}