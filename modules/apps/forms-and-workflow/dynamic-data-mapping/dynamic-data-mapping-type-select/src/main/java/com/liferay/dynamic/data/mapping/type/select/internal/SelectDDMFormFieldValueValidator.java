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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Encryptor;

import java.security.Key;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=select",
	service = DDMFormFieldValueValidator.class
)
public class SelectDDMFormFieldValueValidator
	implements DDMFormFieldValueValidator {

	@Override
	public void validate(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormFieldValueValidationException {

		String dataSourceType = GetterUtil.getString(
			ddmFormField.getProperty("dataSourceType"), "manual");

		if (Objects.equals(dataSourceType, "manual")) {
			validateDDMFormFieldOptions(
				ddmFormField, ddmFormFieldValue.getValue());
		}
		else {
			validateDataProvider(ddmFormField, ddmFormFieldValue.getValue());
		}
	}

	protected JSONArray createJSONArray(String fieldName, String json)
		throws DDMFormFieldValueValidationException {

		try {
			return jsonFactory.createJSONArray(json);
		}
		catch (JSONException jsone) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			throw new DDMFormFieldValueValidationException(
				String.format(
					"Invalid data stored for select field \"%s\"", fieldName));
		}
	}

	protected String decryptSelectedValue(Key key, String selectedValue) {
		try {
			return Encryptor.decrypt(key, selectedValue);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return selectedValue;
		}
	}

	protected Key getKey(HttpSession session) {
		Key key = null;

		try {
			String serializedKey = (String)session.getAttribute("key");

			if (serializedKey != null) {
				key = Encryptor.deserializeKey(serializedKey);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return key;
	}

	protected void validateDataProvider(DDMFormField ddmFormField, Value value)
		throws DDMFormFieldValueValidationException {

		String ddmDataProviderInstanceId = GetterUtil.getString(
			ddmFormField.getProperty("ddmDataProviderInstanceId"));

		HttpSession session = PortalSessionThreadLocal.getHttpSession();

		Key key = getKey(session);

		if (key == null) {
			return;
		}

		Map<Locale, String> selectedValues = value.getValues();

		for (String selectedValue : selectedValues.values()) {
			JSONArray jsonArray = createJSONArray(
				ddmFormField.getName(), selectedValue);

			for (int i = 0; i < jsonArray.length(); i++) {
				Matcher matcher = _VALUE_PATTERN.matcher(
					jsonArray.getString(i));

				if (!matcher.matches()) {
					throw new DDMFormFieldValueValidationException(
						String.format(
							"Invalid value found for select field \"%s\"",
							ddmFormField.getName()));
				}

				String[] values = StringUtil.split(
					jsonArray.getString(i), CharPool.POUND);

				String decrytedSelectedValue = decryptSelectedValue(
					key, values[1]);

				String selectedValueExpr = String.format(
					"%s#%s", values[0], ddmDataProviderInstanceId);

				if (!selectedValueExpr.equals(decrytedSelectedValue)) {
					throw new DDMFormFieldValueValidationException(
						String.format(
							"Invalid value found for select field \"%s\"",
							ddmFormField.getName()));
				}
			}
		}
	}

	protected void validateDDMFormFieldOptions(
			DDMFormField ddmFormField, Value value)
		throws DDMFormFieldValueValidationException {

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions == null) {
			throw new DDMFormFieldValueValidationException(
				String.format(
					"Options must be set for select field \"%s\"",
					ddmFormField.getName()));
		}

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		if (optionValues.isEmpty()) {
			throw new DDMFormFieldValueValidationException(
				"Options must contain at least one alternative");
		}

		Map<Locale, String> selectedValues = value.getValues();

		for (String selectedValue : selectedValues.values()) {
			validateSelectedValue(ddmFormField, optionValues, selectedValue);
		}
	}

	protected void validateSelectedValue(
			DDMFormField ddmFormField, Set<String> optionValues,
			String selectedValue)
		throws DDMFormFieldValueValidationException {

		JSONArray jsonArray = createJSONArray(
			ddmFormField.getName(), selectedValue);

		for (int i = 0; i < jsonArray.length(); i++) {
			if (Validator.isNull(jsonArray.getString(i)) &&
				!ddmFormField.isRequired()) {

				continue;
			}

			if (!optionValues.contains(jsonArray.getString(i))) {
				throw new DDMFormFieldValueValidationException(
					String.format(
						"The selected option \"%s\" is not a valid alternative",
						jsonArray.getString(i)));
			}
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Pattern _VALUE_PATTERN = Pattern.compile(".+#.+");

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldValueValidator.class);

}