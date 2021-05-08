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

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Marcellus Tavares
 */
public class OptionsDDMFormFieldContextHelper {

	public OptionsDDMFormFieldContextHelper(
		JSONFactory jsonFactory, DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		_jsonFactory = jsonFactory;

		_ddmForm = ddmFormField.getDDMForm();

		_ddmFormFieldRenderingContext = ddmFormFieldRenderingContext;

		_value = ddmFormFieldRenderingContext.getValue();
	}

	public Map<String, Object> getValue() {
		Map<String, Object> localizedValue = new HashMap<>();

		if (Validator.isNull(_value)) {
			Locale locale = _ddmFormFieldRenderingContext.getLocale();

			if (locale == null) {
				locale = LocaleUtil.getSiteDefault();
			}

			localizedValue.put(
				LocaleUtil.toLanguageId(locale), createDefaultOptions());

			return localizedValue;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(_value);

			Iterator<String> iterator = jsonObject.keys();

			while (iterator.hasNext()) {
				String languageId = iterator.next();

				List<Object> options = createOptions(
					jsonObject.getJSONArray(languageId));

				localizedValue.put(languageId, options);
			}

			return localizedValue;
		}
		catch (JSONException jsonException) {
			_log.error("Unable to parse JSON array", jsonException);

			return localizedValue;
		}
	}

	protected List<Object> createDefaultOptions() {
		String defaultOptionLabel = getDefaultOptionLabel();

		String defaultOptionValue = getDefaultOptionValue(defaultOptionLabel);

		return ListUtil.fromArray(
			createOption(
				defaultOptionLabel, defaultOptionValue, defaultOptionValue));
	}

	protected Map<String, String> createOption(
		String label, String reference, String value) {

		return HashMapBuilder.put(
			"label", label
		).put(
			"reference", reference
		).put(
			"value", value
		).build();
	}

	protected List<Object> createOptions(JSONArray jsonArray) {
		List<Object> options = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Map<String, String> option = createOption(
				jsonObject.getString("label"),
				jsonObject.getString("reference"),
				jsonObject.getString("value"));

			options.add(option);
		}

		return options;
	}

	protected String getDefaultOptionLabel() {
		ResourceBundle resourceBundle = getResourceBundle(
			_ddmForm.getDefaultLocale());

		return LanguageUtil.get(resourceBundle, "option");
	}

	protected String getDefaultOptionValue(String defaultOptionValue) {
		for (int i = 0; i < _DEFAULT_OPTION_VALUE_RANDOM_NUMBERS_LENGTH; i++) {
			defaultOptionValue = defaultOptionValue.concat(
				String.valueOf(RandomUtil.nextInt(10)));
		}

		return defaultOptionValue;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());
	}

	private static final int _DEFAULT_OPTION_VALUE_RANDOM_NUMBERS_LENGTH = 8;

	private static final Log _log = LogFactoryUtil.getLog(
		OptionsDDMFormFieldContextHelper.class);

	private final DDMForm _ddmForm;
	private final DDMFormFieldRenderingContext _ddmFormFieldRenderingContext;
	private final JSONFactory _jsonFactory;
	private final String _value;

}