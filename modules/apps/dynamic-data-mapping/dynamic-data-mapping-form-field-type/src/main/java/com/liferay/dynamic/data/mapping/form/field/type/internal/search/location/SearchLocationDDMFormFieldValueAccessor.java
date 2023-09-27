/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.search.location;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SEARCH_LOCATION,
	service = DDMFormFieldValueAccessor.class
)
public class SearchLocationDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<String> {

	@Override
	public String getValue(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return StringPool.BLANK;
		}

		return value.getString(locale);
	}

	@Override
	public String getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return getValue(ddmFormFieldValue, locale);
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		JSONObject jsonObject = _getJSONObject(value.getString(locale));

		String placeValue = jsonObject.getString("place");

		if (Validator.isNull(placeValue.trim())) {
			return true;
		}

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		LocalizedValue visibleFields = (LocalizedValue)ddmFormField.getProperty(
			"visibleFields");

		for (String visibleField :
				StringUtil.split(
					StringUtil.removeChars(
						GetterUtil.getString(visibleFields.getString(locale)),
						CharPool.CLOSE_BRACKET, CharPool.OPEN_BRACKET,
						CharPool.QUOTE))) {

			String visibleFieldValue = jsonObject.getString(
				visibleField.trim());

			if (Validator.isNull(visibleFieldValue.trim())) {
				return true;
			}
		}

		return false;
	}

	private JSONObject _getJSONObject(String value) {
		try {
			return _jsonFactory.createJSONObject(value);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return _jsonFactory.createJSONObject();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchLocationDDMFormFieldValueAccessor.class);

	@Reference
	private JSONFactory _jsonFactory;

}