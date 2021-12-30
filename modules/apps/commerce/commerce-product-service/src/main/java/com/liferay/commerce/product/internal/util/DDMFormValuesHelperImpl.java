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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true, service = DDMFormValuesHelper.class
)
public class DDMFormValuesHelperImpl implements DDMFormValuesHelper {

	/**
	 * @deprecated As of Mueller (7.2.x), without direct replacement, as this
	 *             processing is no longer necessary
	 */
	@Deprecated
	@Override
	public String cleanDDMFormValuesJSON(String json) throws PortalException {
		return json;
	}

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
			DDMFormFieldValue ddmFormFieldValue = _getDDMFormFieldValue(
				jsonArray.getJSONObject(i));

			if ((ddmFormFieldValue != null) &&
				(ddmFormFieldValue.getValue() != null)) {

				ddmFormFieldValues.add(ddmFormFieldValue);
			}
		}

		if (ddmFormFieldValues.isEmpty()) {
			return null;
		}

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		return ddmFormValues;
	}

	/**
	 * @param      json1
	 * @param      json2
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link
	 *             JsonHelper#equals(String, String)}
	 */
	@Deprecated
	@Override
	public boolean equals(String json1, String json2) throws PortalException {
		throw new UnsupportedOperationException();
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

	private DDMFormFieldValue _getDDMFormFieldValue(JSONObject jsonObject) {
		String key = jsonObject.getString("key");

		if (Validator.isNull(key)) {
			return null;
		}

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(key);

		JSONArray valueJSONArray = jsonObject.getJSONArray("value");

		if (valueJSONArray != null) {
			ddmFormFieldValue.setValue(
				new UnlocalizedValue(valueJSONArray.toString()));
		}
		else {
			ddmFormFieldValue.setValue(new UnlocalizedValue(StringPool.BLANK));
		}

		return ddmFormFieldValue;
	}

	private JSONObject _toJSONObject(DDMFormFieldValue ddmFormFieldValue) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("key", ddmFormFieldValue.getName());

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(LocaleUtil.ROOT);

		jsonObject.put("value", valueString);

		return jsonObject;
	}

	@Reference
	private JSONFactory _jsonFactory;

}