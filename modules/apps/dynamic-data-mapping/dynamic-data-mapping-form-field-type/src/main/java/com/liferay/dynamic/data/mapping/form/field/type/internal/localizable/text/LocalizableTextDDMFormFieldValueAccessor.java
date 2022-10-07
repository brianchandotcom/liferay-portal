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

package com.liferay.dynamic.data.mapping.form.field.type.internal.localizable.text;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.function.IntFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.LOCALIZABLE_TEXT,
	service = {
		DDMFormFieldValueAccessor.class,
		LocalizableTextDDMFormFieldValueAccessor.class
	}
)
public class LocalizableTextDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONObject> {

	@Override
	public IntFunction<JSONObject[]> getArrayGeneratorIntFunction() {
		return JSONObject[]::new;
	}

	@Override
	public JSONObject getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return jsonFactory.createJSONObject();
		}

		try {
			return jsonFactory.createJSONObject(value.getString(locale));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON object", jsonException);
			}

			return jsonFactory.createJSONObject();
		}
	}

	@Override
	public JSONObject getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return getValue(ddmFormFieldValue, locale);
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONObject jsonObject = getValue(ddmFormFieldValue, locale);

		if (jsonObject.length() == 0) {
			return true;
		}

		for (String key : jsonObject.keySet()) {
			if (Validator.isNotNull(jsonObject.getString(key))) {
				return false;
			}
		}

		return true;
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		LocalizableTextDDMFormFieldValueAccessor.class);

}