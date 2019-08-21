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

package com.liferay.dynamic.data.mapping.form.field.type.text.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=text",
	service = DDMFormFieldValueJSONSerializer.class
)
public class TextDDMFormFieldValueJSONDeserializer
	implements DDMFormFieldValueJSONSerializer {

	public Object serialize(DDMFormField ddmFormField, Value value) {
		if (value.isLocalized()) {
			return _toJSONObject(value);
		}

		String valueString = value.getString(LocaleUtil.ROOT);

		if (Validator.isNull(valueString) &&
			MapUtil.getBoolean(ddmFormField.getProperties(), "random")) {

			valueString = StringUtil.randomString();
		}

		return valueString;
	}

	private JSONObject _toJSONObject(Value value) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Locale availableLocale : value.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				value.getString(availableLocale));
		}

		return jsonObject;
	}

	@Reference
	private JSONFactory _jsonFactory;

}