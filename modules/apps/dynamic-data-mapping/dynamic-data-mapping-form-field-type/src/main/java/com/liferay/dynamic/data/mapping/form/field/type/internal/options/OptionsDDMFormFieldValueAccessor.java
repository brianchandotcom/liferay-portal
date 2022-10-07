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

package com.liferay.dynamic.data.mapping.form.field.type.internal.options;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.OPTIONS,
	service = DDMFormFieldValueAccessor.class
)
public class OptionsDDMFormFieldValueAccessor
	extends DefaultDDMFormFieldValueAccessor {

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		String valueString = StringUtil.trim(
			getValue(ddmFormFieldValue, locale));

		if (Validator.isNull(valueString)) {
			return true;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(valueString);

			JSONArray jsonArray = jsonObject.getJSONArray(
				_language.getLanguageId(locale));

			if ((jsonArray == null) || (jsonArray.length() == 0)) {
				return true;
			}

			return false;
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON object", jsonException);
			}

			return true;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OptionsDDMFormFieldValueAccessor.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}