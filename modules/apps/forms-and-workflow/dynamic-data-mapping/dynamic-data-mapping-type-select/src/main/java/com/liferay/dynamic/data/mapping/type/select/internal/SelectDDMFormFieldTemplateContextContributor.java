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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldOptionsFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=select",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		SelectDDMFormFieldTemplateContextContributor.class
	}
)
public class SelectDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"dataSourceType",
			GetterUtil.getString(
				ddmFormField.getProperty("dataSourceType"), "manual"));
		parameters.put(
			"multiple",
			ddmFormField.isMultiple() ? "multiple" : StringPool.BLANK);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormFieldOptionsFactory.create(
				ddmFormField, ddmFormFieldRenderingContext);

		parameters.put(
			"options",
			getOptions(
				ddmFormFieldOptions, ddmFormFieldRenderingContext.getLocale()));

		Map<String, String> stringsMap = new HashMap<>();

		ResourceBundle resourceBundle = getResourceBundle(
			ddmFormFieldRenderingContext.getLocale());

		stringsMap.put(
			"chooseAnOption",
			LanguageUtil.get(resourceBundle, "choose-an-option"));
		stringsMap.put(
			"dynamicallyLoadedData",
			LanguageUtil.get(resourceBundle, "dynamically-loaded-data"));
		stringsMap.put(
			"emptyList", LanguageUtil.get(resourceBundle, "empty-list"));
		stringsMap.put("search", LanguageUtil.get(resourceBundle, "search"));

		parameters.put("strings", stringsMap);

		parameters.put(
			"value", getValue(ddmFormFieldRenderingContext.getValue()));

		return parameters;
	}

	protected List<Object> getOptions(
		DDMFormFieldOptions ddmFormFieldOptions, Locale locale) {

		List<Object> options = new ArrayList<>();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			Map<String, String> optionMap = new HashMap<>();

			LocalizedValue optionLabel = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			optionMap.put("label", optionLabel.getString(locale));

			optionMap.put("value", optionValue);

			options.add(optionMap);
		}

		return options;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		ResourceBundle portalResourceBundle =
			portalResourceBundleLoader.loadResourceBundle(locale);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		return new AggregateResourceBundle(
			resourceBundle, portalResourceBundle);
	}

	protected List<String> getValue(String valueString) {
		String[] valuesStringArray = toStringArray(valueString);

		return ListUtil.toList(valuesStringArray);
	}

	protected String[] toStringArray(String value) {
		if (Validator.isNull(value)) {
			return GetterUtil.DEFAULT_STRING_VALUES;
		}

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(value);

			return ArrayUtil.toStringArray(jsonArray);
		}
		catch (JSONException jsone) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return StringUtil.split(value);
		}
	}

	@Reference
	protected DDMFormFieldOptionsFactory ddmFormFieldOptionsFactory;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldTemplateContextContributor.class);

}