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

package com.liferay.dynamic.data.mapping.form.field.type.internal.options.helper;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
		Map<String, Object> changedProperties =
			(Map<String, Object>)_ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (MapUtil.isNotEmpty(changedProperties)) {
			Map<String, Object> changedLocalizedValues =
				(Map<String, Object>)changedProperties.get("value");

			if (MapUtil.isNotEmpty(changedLocalizedValues)) {
				return changedLocalizedValues;
			}
		}

		Map<String, Object> localizedValues = new HashMap<>();

		if (Validator.isNull(_value)) {
			localizedValues.put(_getLanguageId(), createDefaultOptions());

			return localizedValues;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(_value);

			Iterator<String> iterator = jsonObject.keys();

			while (iterator.hasNext()) {
				String languageId = iterator.next();

				List<Object> options = _createOptions(
					jsonObject.getJSONArray(languageId));

				localizedValues.put(languageId, options);
			}

			return localizedValues;
		}
		catch (JSONException jsonException) {
			_log.error("Unable to parse JSON array", jsonException);

			return localizedValues;
		}
	}

	protected List<Object> createDefaultOptions() {
		String defaultOptionLabel = _getDefaultOptionLabel();

		String defaultOptionValue = DDMFormFieldUtil.getDDMFormFieldName(
			defaultOptionLabel);

		return ListUtil.fromArray(
			_createOption(
				defaultOptionLabel, defaultOptionValue, defaultOptionValue));
	}

	private Map<String, String> _createOption(
		String label, String reference, String value) {

		return HashMapBuilder.put(
			"label", label
		).put(
			"reference", reference
		).put(
			"value", value
		).build();
	}

	private List<Object> _createOptions(JSONArray jsonArray) {
		List<Object> options = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Map<String, String> option = _createOption(
				jsonObject.getString("label"),
				jsonObject.getString("reference"),
				jsonObject.getString("value"));

			options.add(option);
		}

		return options;
	}

	private String _getDefaultOptionLabel() {
		ResourceBundle resourceBundle = _getResourceBundle(
			_ddmForm.getDefaultLocale());

		return LanguageUtil.get(resourceBundle, "option");
	}

	private String _getLanguageId() {
		Locale locale = _ddmFormFieldRenderingContext.getLocale();

		if (locale == null) {
			locale = LocaleUtil.getSiteDefault();
		}

		return LocaleUtil.toLanguageId(locale);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OptionsDDMFormFieldContextHelper.class);

	private final DDMForm _ddmForm;
	private final DDMFormFieldRenderingContext _ddmFormFieldRenderingContext;
	private final JSONFactory _jsonFactory;
	private final String _value;

}