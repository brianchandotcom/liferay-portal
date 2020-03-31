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

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Lancha
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=fieldset",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		FieldSetDDMFormFieldTemplateContextContributor.class
	}
)
public class FieldSetDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, List<Object>> nestedFieldsMap =
			(Map<String, List<Object>>)ddmFormFieldRenderingContext.getProperty(
				"nestedFields");

		if (nestedFieldsMap == null) {
			nestedFieldsMap = new HashMap<>();
		}

		List<Object> nestedFields = getNestedFields(
			nestedFieldsMap,
			getNestedFieldNames(
				GetterUtil.getString(
					ddmFormField.getProperty("nestedFieldNames")),
				nestedFieldsMap.keySet()));

		return HashMapBuilder.<String, Object>put(
			"dataDefinitionId",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormFieldRenderingContext, "dataDefinitionId")
		).put(
			"nestedFields", nestedFields
		).put(
			"predefinedValue",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"predefinedValue")
		).put(
			"rows", getRowsJSONArray(ddmFormField, nestedFields)
		).put(
			"value",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormFieldRenderingContext, "value")
		).build();
	}

	protected int countVisibleNestedFields(List<Object> nestedFields) {
		Stream<Object> stream = nestedFields.stream();

		return GetterUtil.getInteger(
			stream.filter(
				this::_isNestedFieldVisible
			).count());
	}

	protected JSONObject createRowJSONObject(List<Object> nestedFields) {
		int columnSize = 12 / nestedFields.size();

		JSONArray columnsJSONArray = jsonFactory.createJSONArray();

		for (Object nestedFieldContext : nestedFields) {
			JSONArray fieldsJSONArray = jsonFactory.createJSONArray();

			fieldsJSONArray.put(
				MapUtil.getString(
					(Map<String, ?>)nestedFieldContext, "fieldName"));

			JSONObject columnJSONObject = jsonFactory.createJSONObject();

			columnJSONObject.put(
				"fields", fieldsJSONArray
			).put(
				"size", columnSize
			);

			columnsJSONArray.put(columnJSONObject);
		}

		JSONObject rowJSONObject = jsonFactory.createJSONObject();

		rowJSONObject.put("columns", columnsJSONArray);

		return rowJSONObject;
	}

	protected JSONArray getJSONArray(String rows) {
		try {
			return jsonFactory.createJSONArray(rows);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}
		}

		return jsonFactory.createJSONArray();
	}

	protected String[] getNestedFieldNames(
		String nestedFieldNames, Set<String> defaultNestedFieldNames) {

		if (Validator.isNotNull(nestedFieldNames)) {
			return StringUtil.split(nestedFieldNames);
		}

		return defaultNestedFieldNames.toArray(new String[0]);
	}

	protected List<Object> getNestedFields(
		Map<String, List<Object>> nestedFieldsMap, String[] nestedFieldNames) {

		List<Object> nestedFields = new ArrayList<>();

		for (String nestedFieldName : nestedFieldNames) {
			nestedFields.addAll(nestedFieldsMap.get(nestedFieldName));
		}

		return nestedFields;
	}

	protected JSONArray getRowsJSONArray(
		DDMFormField ddmFormField, List<Object> nestedFields) {

		String rows = GetterUtil.getString(ddmFormField.getProperty("rows"));

		if (Validator.isNotNull(rows) || nestedFields.isEmpty()) {
			return getJSONArray(rows);
		}

		JSONArray rowsJSONArray = jsonFactory.createJSONArray();

		Stream<Object> visibleFieldsStream = nestedFields.stream();

		List<Object> visibleNestedFields = visibleFieldsStream.filter(
			this::_isNestedFieldVisible
		).collect(
			Collectors.toList()
		);

		if (!visibleNestedFields.isEmpty()) {
			rowsJSONArray.put(createRowJSONObject(visibleNestedFields));
		}

		Stream<Object> invisibleFieldsStream = nestedFields.stream();

		List<Object> invisibleNestedFields = invisibleFieldsStream.filter(
			nestedFieldContext -> !_isNestedFieldVisible(nestedFieldContext)
		).collect(
			Collectors.toList()
		);

		if (!invisibleNestedFields.isEmpty()) {
			rowsJSONArray.put(createRowJSONObject(invisibleNestedFields));
		}

		return rowsJSONArray;
	}

	@Reference
	protected JSONFactory jsonFactory;

	private boolean _isNestedFieldVisible(Object nestedFieldContext) {
		return MapUtil.getBoolean(
			(Map<String, ?>)nestedFieldContext, "visible", true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FieldSetDDMFormFieldTemplateContextContributor.class);

}