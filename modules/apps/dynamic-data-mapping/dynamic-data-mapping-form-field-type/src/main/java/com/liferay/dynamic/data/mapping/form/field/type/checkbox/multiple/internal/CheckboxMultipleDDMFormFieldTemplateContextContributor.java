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

package com.liferay.dynamic.data.mapping.form.field.type.checkbox.multiple.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=checkbox_multiple",
	service = {
		CheckboxMultipleDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class CheckboxMultipleDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"inline",
			GetterUtil.getBoolean(ddmFormField.getProperty("inline")));
		parameters.put(
			"options", getOptions(ddmFormField, ddmFormFieldRenderingContext));

		List<String> predefinedValue = getValue(
			getPredefinedValue(ddmFormField, ddmFormFieldRenderingContext));

		if (predefinedValue != null) {
			parameters.put("predefinedValue", predefinedValue);
		}

		parameters.put(
			"showAsSwitcher",
			GetterUtil.getBoolean(ddmFormField.getProperty("showAsSwitcher")));
		parameters.put(
			"value",
			getValue(
				GetterUtil.getString(
					ddmFormFieldRenderingContext.getValue(), "[]")));

		return parameters;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		List<Map<String, String>> keyValuePairs =
			(List<Map<String, String>>)
				ddmFormFieldRenderingContext.getProperty("options");

		if (ListUtil.isEmpty(keyValuePairs)) {
			return ddmFormField.getDDMFormFieldOptions();
		}

		for (Map<String, String> keyValuePair : keyValuePairs) {
			ddmFormFieldOptions.addOptionLabel(
				keyValuePair.get("value"),
				ddmFormFieldRenderingContext.getLocale(),
				keyValuePair.get("label"));
		}

		return ddmFormFieldOptions;
	}

	protected List<Object> getOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		CheckboxMultipleDDMFormFieldContextHelper
			checkboxMultipleDDMFormFieldContextHelper =
				new CheckboxMultipleDDMFormFieldContextHelper(
					jsonFactory,
					getDDMFormFieldOptions(
						ddmFormField, ddmFormFieldRenderingContext),
					ddmFormFieldRenderingContext.getLocale());

		return checkboxMultipleDDMFormFieldContextHelper.getOptions(
			ddmFormFieldRenderingContext);
	}

	protected String getPredefinedValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		if (predefinedValue == null) {
			return null;
		}

		String predefinedValueString = predefinedValue.getString(
			ddmFormFieldRenderingContext.getLocale());

		return predefinedValueString;
	}

	protected List<String> getValue(String valueString) {
		JSONArray jsonArray = null;

		try {
			jsonArray = jsonFactory.createJSONArray(valueString);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			jsonArray = jsonFactory.createJSONArray();
		}

		List<String> values = new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			values.add(String.valueOf(jsonArray.get(i)));
		}

		return values;
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		CheckboxMultipleDDMFormFieldTemplateContextContributor.class);

}