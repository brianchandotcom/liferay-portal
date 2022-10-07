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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.internal.io.util.DDMFormFieldSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "ddm.form.layout.serializer.type=json",
	service = DDMFormLayoutSerializer.class
)
public class DDMFormLayoutJSONSerializer implements DDMFormLayoutSerializer {

	@Override
	public DDMFormLayoutSerializerSerializeResponse serialize(
		DDMFormLayoutSerializerSerializeRequest
			ddmFormLayoutSerializerSerializeRequest) {

		DDMFormLayout ddmFormLayout =
			ddmFormLayoutSerializerSerializeRequest.getDDMFormLayout();

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		addDefaultLanguageId(jsonObject, ddmFormLayout.getDefaultLocale());
		_addPages(jsonObject, ddmFormLayout.getDDMFormLayoutPages());
		_addPaginationMode(jsonObject, ddmFormLayout.getPaginationMode());
		addRules(jsonObject, ddmFormLayout.getDDMFormRules());

		if (Validator.isNotNull(ddmFormLayout.getDefinitionSchemaVersion())) {
			jsonObject.put(
				"definitionSchemaVersion",
				ddmFormLayout.getDefinitionSchemaVersion());
		}

		DDMFormFieldSerializerUtil.serialize(
			ddmFormLayout.getDDMFormFields(), _ddmFormFieldTypeServicesTracker,
			_jsonFactory, jsonObject);

		DDMFormLayoutSerializerSerializeResponse.Builder builder =
			DDMFormLayoutSerializerSerializeResponse.Builder.newBuilder(
				jsonObject.toString());

		return builder.build();
	}

	protected void addDefaultLanguageId(
		JSONObject jsonObject, Locale defaultLocale) {

		jsonObject.put(
			"defaultLanguageId", LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void addRules(
		JSONObject jsonObject, List<DDMFormRule> ddmFormRules) {

		if (ListUtil.isEmpty(ddmFormRules)) {
			return;
		}

		jsonObject.put(
			"rules", DDMFormRuleJSONSerializer.serialize(ddmFormRules));
	}

	protected JSONObject toJSONObject(DDMFormLayoutColumn ddmFormLayoutColumn) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("size", ddmFormLayoutColumn.getSize());

		_addFieldNames(jsonObject, ddmFormLayoutColumn.getDDMFormFieldNames());

		return jsonObject;
	}

	protected JSONObject toJSONObject(DDMFormLayoutPage ddmFormLayoutPage) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		_addDescription(jsonObject, ddmFormLayoutPage.getDescription());
		_addRows(jsonObject, ddmFormLayoutPage.getDDMFormLayoutRows());
		_addTitle(jsonObject, ddmFormLayoutPage.getTitle());

		return jsonObject;
	}

	protected JSONObject toJSONObject(DDMFormLayoutRow ddmFormLayoutRow) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		_addColumns(jsonObject, ddmFormLayoutRow.getDDMFormLayoutColumns());

		return jsonObject;
	}

	private void _addColumns(
		JSONObject jsonObject, List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DDMFormLayoutColumn ddmFormLayoutColumn : ddmFormLayoutColumns) {
			jsonArray.put(toJSONObject(ddmFormLayoutColumn));
		}

		jsonObject.put("columns", jsonArray);
	}

	private void _addDescription(
		JSONObject pageJSONObject, LocalizedValue description) {

		Map<Locale, String> values = description.getValues();

		if (values.isEmpty()) {
			return;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Locale availableLocale : description.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				description.getString(availableLocale));
		}

		pageJSONObject.put("description", jsonObject);
	}

	private void _addFieldNames(
		JSONObject jsonObject, List<String> ddmFormFieldNames) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String ddmFormFieldName : ddmFormFieldNames) {
			jsonArray.put(ddmFormFieldName);
		}

		jsonObject.put("fieldNames", jsonArray);
	}

	private void _addPages(
		JSONObject jsonObject, List<DDMFormLayoutPage> ddmFormLayoutPages) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			jsonArray.put(toJSONObject(ddmFormLayoutPage));
		}

		jsonObject.put("pages", jsonArray);
	}

	private void _addPaginationMode(
		JSONObject jsonObject, String paginationMode) {

		jsonObject.put("paginationMode", paginationMode);
	}

	private void _addRows(
		JSONObject jsonObject, List<DDMFormLayoutRow> ddmFormLayoutRows) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			jsonArray.put(toJSONObject(ddmFormLayoutRow));
		}

		jsonObject.put("rows", jsonArray);
	}

	private void _addTitle(JSONObject pageJSONObject, LocalizedValue title) {
		if (MapUtil.isEmpty(title.getValues())) {
			return;
		}

		pageJSONObject.put("title", LocalizedValueUtil.toJSONObject(title));
	}

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

}