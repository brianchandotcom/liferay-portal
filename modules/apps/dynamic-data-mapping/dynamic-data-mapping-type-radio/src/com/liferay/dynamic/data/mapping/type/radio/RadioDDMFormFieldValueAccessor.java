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

package com.liferay.dynamic.data.mapping.type.radio;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldValueAccessor;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;

import java.util.Locale;

/**
 * @author Renato Rego
 */
public class RadioDDMFormFieldValueAccessor
	extends DDMFormFieldValueAccessor<String> {

	public RadioDDMFormFieldValueAccessor(Locale locale) {
		super(locale);
	}

	@Override
	public String get(DDMFormFieldValue ddmFormFieldValue) {
		try {
			Value value = ddmFormFieldValue.getValue();

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
				value.getString(locale));

			return jsonArray.getString(0);
		}
		catch (JSONException jsone) {
			_log.error("Unable to parse JSON array", jsone);

			return StringPool.BLANK;
		}
	}

	@Override
	public Class<String> getAttributeClass() {
		return String.class;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RadioDDMFormFieldValueAccessor.class);

}