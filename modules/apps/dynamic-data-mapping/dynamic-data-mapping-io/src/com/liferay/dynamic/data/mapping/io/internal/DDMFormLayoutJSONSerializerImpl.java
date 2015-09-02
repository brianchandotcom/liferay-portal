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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONSerializerImpl
	implements DDMFormLayoutJSONSerializer {

	@Override
	public String serialize(DDMFormLayout ddmFormLayout) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addDefaultLanguageId(jsonObject, ddmFormLayout.getDefaultLocale());
		addPages(jsonObject, ddmFormLayout.getDDMFormLayoutPages());
		addPaginationMode(jsonObject, ddmFormLayout.getPaginationMode());

		return jsonObject.toString();
	}

	protected void addColumns(
		JSONObject jsonObject, List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormLayoutColumn ddmFormLayoutColumn : ddmFormLayoutColumns) {
			jsonArray.put(toJSONObject(ddmFormLayoutColumn));
		}

		jsonObject.put("columns", jsonArray);
	}

	protected void addDefaultLanguageId(
		JSONObject jsonObject, Locale defaultLocale) {

		jsonObject.put(
			"defaultLanguageId", LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void addDescription(
		JSONObject pageJSONObject, LocalizedValue description) {

		Map<Locale, String> values = description.getValues();

		if (values.isEmpty()) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale : description.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				description.getString(availableLocale));
		}

		pageJSONObject.put("description", jsonObject);
	}

	protected void addFieldNames(
		JSONObject jsonObject, List<String> ddmFormFieldNames) {

		if (ddmFormFieldNames.isEmpty()) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String ddmFormFieldName : ddmFormFieldNames) {
			jsonArray.put(ddmFormFieldName);
		}

		jsonObject.put("fieldNames", jsonArray);
	}

	protected void addPages(
		JSONObject jsonObject, List<DDMFormLayoutPage> ddmFormLayoutPages) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			jsonArray.put(toJSONObject(ddmFormLayoutPage));
		}

		jsonObject.put("pages", jsonArray);
	}

	protected void addRows(
		JSONObject jsonObject, List<DDMFormLayoutRow> ddmFormLayoutRows) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			jsonArray.put(toJSONObject(ddmFormLayoutRow));
		}

		jsonObject.put("rows", jsonArray);
	}

	protected void addTitle(JSONObject pageJSONObject, LocalizedValue title) {
		Map<Locale, String> values = title.getValues();

		if (values.isEmpty()) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale : title.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				title.getString(availableLocale));
		}

		pageJSONObject.put("title", jsonObject);
	}

	protected JSONObject toJSONObject(DDMFormLayoutColumn ddmFormLayoutColumn) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("size", ddmFormLayoutColumn.getSize());

		addFieldNames(jsonObject, ddmFormLayoutColumn.getDDMFormFieldNames());

		return jsonObject;
	}

	protected JSONObject toJSONObject(DDMFormLayoutPage ddmFormLayoutPage) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addDescription(jsonObject, ddmFormLayoutPage.getDescription());
		addRows(jsonObject, ddmFormLayoutPage.getDDMFormLayoutRows());
		addTitle(jsonObject, ddmFormLayoutPage.getTitle());

		return jsonObject;
	}

	protected JSONObject toJSONObject(DDMFormLayoutRow ddmFormLayoutRow) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addColumns(jsonObject, ddmFormLayoutRow.getDDMFormLayoutColumns());

		return jsonObject;
	}

	private void addPaginationMode(
		JSONObject jsonObject, String paginationMode) {

		jsonObject.put("paginationMode", paginationMode);
	}

}