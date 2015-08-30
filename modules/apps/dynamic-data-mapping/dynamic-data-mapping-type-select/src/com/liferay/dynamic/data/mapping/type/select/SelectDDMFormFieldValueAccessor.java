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

package com.liferay.dynamic.data.mapping.type.select;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Renato Rego
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=select",
	service = {
		DDMFormFieldValueAccessor.class, SelectDDMFormFieldValueAccessor.class
	}
)
public class SelectDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONArray> {

	@Override
	public JSONArray getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		try {
			Value value = ddmFormFieldValue.getValue();

			return JSONFactoryUtil.createJSONArray(value.getString(locale));
		}
		catch (JSONException jsone) {
			_log.error("Unable to parse JSON array", jsone);

			return _EMPTY_JSON_ARRAY;
		}
	}

	private static final JSONArray _EMPTY_JSON_ARRAY =
		JSONFactoryUtil.createJSONArray();

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldValueAccessor.class);

}