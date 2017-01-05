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

package com.liferay.dynamic.data.mapping.type.select.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueSerializer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, property = "ddm.form.field.type.name=select")
public class SelectDDMFormFieldValueSerializer
	implements DDMFormFieldValueSerializer {

	@Override
	public Object serialize(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		String dataSourceType = GetterUtil.getString(
			ddmFormField.getProperty("dataSourceType"), "manual");

		boolean isManualDataSourceType = Objects.equals(
			dataSourceType, "manual");

		if (value.isLocalized()) {
			try {
				return toJSONObject(
					ddmFormField, value, isManualDataSourceType);
			}
			catch (Exception e) {
				_log.error(e);

				return null;
			}
		}
		else {
			String valueStr = value.getString(LocaleUtil.ROOT);

			if (isManualDataSourceType || Validator.isNull(valueStr)) {
				return valueStr;
			}

			try {
				JSONArray jsonArray = extractValuesJSONArray(valueStr);

				return jsonArray.toJSONString();
			}
			catch (Exception e) {
				_log.error(e);

				return null;
			}
		}
	}

	protected JSONArray extractValuesJSONArray(String valueStr)
		throws Exception {

		JSONArray valuesJsonArray = _jsonFactory.createJSONArray(valueStr);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (int i = 0; i < valuesJsonArray.length(); i++) {
			String[] values = StringUtil.split(
				valuesJsonArray.getString(i), CharPool.POUND);

			jsonArray.put(values[0]);
		}

		return jsonArray;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected JSONObject toJSONObject(
			DDMFormField ddmFormField, Value value,
			boolean isManualDataSourceType)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Locale availableLocale : value.getAvailableLocales()) {
			String valueStr = value.getString(availableLocale);

			if (!isManualDataSourceType) {
				jsonObject.put(
					LocaleUtil.toLanguageId(availableLocale), valueStr);
			}
			else {
				JSONArray jsonArray = extractValuesJSONArray(valueStr);

				jsonObject.put(
					LocaleUtil.toLanguageId(availableLocale),
					jsonArray.toJSONString());
			}
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldValueSerializer.class);

	private JSONFactory _jsonFactory;

}