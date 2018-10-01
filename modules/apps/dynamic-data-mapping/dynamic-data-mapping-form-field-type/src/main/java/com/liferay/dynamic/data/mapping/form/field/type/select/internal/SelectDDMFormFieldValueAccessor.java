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

package com.liferay.dynamic.data.mapping.form.field.type.select.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.function.IntFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renato Rego
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=select",
	service =
		{DDMFormFieldValueAccessor.class, SelectDDMFormFieldValueAccessor.class}
)
public class SelectDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONArray> {

	@Override
	public IntFunction<JSONArray[]> getArrayGeneratorFunction() {
		return JSONArray[]::new;
	}

	@Override
	public JSONArray getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		return createJSONArray(value.getString(locale));
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONArray jsonArray = getValue(ddmFormFieldValue, locale);

		if (jsonArray.length() > 0) {
			return false;
		}

		return true;
	}

	@Override
	public Object map(Object value) {
		if (Validator.isNull(value)) {
			return value;
		}

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(value.toString());

			StringBundler sb = new StringBundler(jsonArray.length() * 2 - 1);

			for (int i = 0; i < jsonArray.length(); i++) {
				sb.append(jsonArray.get(i));

				if (i < (jsonArray.length() - 1)) {
					sb.append(CharPool.COMMA);
				}
			}

			return sb.toString();
		}
		catch (JSONException jsone) {
			_log.error("Unable to parse JSON array", jsone);

			return StringPool.BLANK;
		}
	}

	protected JSONArray createJSONArray(String json) {
		try {
			return jsonFactory.createJSONArray(json);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON array", jsone);
			}

			return jsonFactory.createJSONArray();
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldValueAccessor.class);

}