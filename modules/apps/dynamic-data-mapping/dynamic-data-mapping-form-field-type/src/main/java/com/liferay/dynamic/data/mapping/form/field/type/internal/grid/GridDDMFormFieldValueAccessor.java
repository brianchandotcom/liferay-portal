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

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.GRID,
	service = {
		DDMFormFieldValueAccessor.class, GridDDMFormFieldValueAccessor.class
	}
)
public class GridDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONObject> {

	@Override
	public IntFunction<JSONObject[]> getArrayGeneratorIntFunction() {
		return JSONObject[]::new;
	}

	@Override
	public JSONObject getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		return createJSONObject(value.getString(locale));
	}

	@Override
	public JSONObject getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return getValue(ddmFormFieldValue, locale);
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONObject jsonObject = getValue(ddmFormFieldValue, locale);

		Set<String> keys = _getUniqueKeys(jsonObject);

		Set<String> rowValues = _getDDMFormFieldRowValues(
			ddmFormFieldValue.getDDMFormField());

		Stream<String> stream = rowValues.stream();

		return stream.anyMatch(rowValue -> !keys.contains(rowValue));
	}

	protected JSONObject createJSONObject(String json) {
		try {
			return jsonFactory.createJSONObject(json);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON object", jsonException);
			}

			return jsonFactory.createJSONObject();
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private Set<String> _getDDMFormFieldRowValues(DDMFormField ddmFormField) {
		DDMFormFieldOptions ddmFormFieldOptions =
			(DDMFormFieldOptions)ddmFormField.getProperty("rows");

		return ddmFormFieldOptions.getOptionsValues();
	}

	private Set<String> _getUniqueKeys(JSONObject jsonObject) {
		Set<String> uniqueKeys = new HashSet<>();

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			uniqueKeys.add(iterator.next());
		}

		return uniqueKeys;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GridDDMFormFieldValueAccessor.class);

}