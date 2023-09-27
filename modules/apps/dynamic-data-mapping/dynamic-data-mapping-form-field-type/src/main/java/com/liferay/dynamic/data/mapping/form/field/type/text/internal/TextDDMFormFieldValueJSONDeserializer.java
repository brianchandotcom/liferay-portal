/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = "ddm.form.field.type.name=text",
	service = DDMFormFieldValueJSONSerializer.class
)
public class TextDDMFormFieldValueJSONDeserializer
	implements DDMFormFieldValueJSONSerializer {

	@Override
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