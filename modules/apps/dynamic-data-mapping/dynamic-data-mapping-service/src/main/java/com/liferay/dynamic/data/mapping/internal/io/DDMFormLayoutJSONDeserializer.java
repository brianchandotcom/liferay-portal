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

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.layout.deserializer.type=json",
	service = DDMFormLayoutDeserializer.class
)
public class DDMFormLayoutJSONDeserializer
	implements DDMFormLayoutDeserializer {

	@Override
	public DDMFormLayoutDeserializerDeserializeResponse deserialize(
		DDMFormLayoutDeserializerDeserializeRequest
			ddmFormLayoutDeserializerDeserializeRequest) {

		String content =
			ddmFormLayoutDeserializerDeserializeRequest.getContent();

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(content);

			setDDMFormLayoutDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmFormLayout);
			setDDMFormLayoutPages(
				jsonObject.getJSONArray("pages"), ddmFormLayout);

			setDDMFormLayoutPageTitlesDefaultLocale(ddmFormLayout);
			setDDMFormLayoutPaginationMode(
				jsonObject.getString("paginationMode"), ddmFormLayout);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}

		DDMFormLayoutDeserializerDeserializeResponse.Builder builder =
			DDMFormLayoutDeserializerDeserializeResponse.Builder.newBuilder(
				ddmFormLayout);

		return builder.build();
	}

	protected DDMFormLayoutColumn getDDMFormLayoutColumn(
		JSONObject jsonObject) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			jsonObject.getInt("size"));

		setDDMFormLayouColumnFieldNames(
			jsonObject.getJSONArray("fieldNames"), ddmFormLayoutColumn);

		return ddmFormLayoutColumn;
	}

	protected List<String> getDDMFormLayoutColumnFieldNames(
		JSONArray jsonArray) {

		List<String> ddmFormFieldNames = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmFormFieldNames.add(jsonArray.getString(i));
		}

		return ddmFormFieldNames;
	}

	protected List<DDMFormLayoutColumn> getDDMFormLayoutColumns(
		JSONArray jsonArray) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutColumn ddmFormLayoutColumn = getDDMFormLayoutColumn(
				jsonArray.getJSONObject(i));

			ddmFormLayoutColumns.add(ddmFormLayoutColumn);
		}

		return ddmFormLayoutColumns;
	}

	protected DDMFormLayoutPage getDDMFormLayoutPage(JSONObject jsonObject) {
		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		setDDMFormLayoutPageDescription(
			jsonObject.getJSONObject("description"), ddmFormLayoutPage);
		setDDMFormLayoutPageRows(
			jsonObject.getJSONArray("rows"), ddmFormLayoutPage);
		setDDMFormLayoutPageTitle(
			jsonObject.getJSONObject("title"), ddmFormLayoutPage);

		return ddmFormLayoutPage;
	}

	protected List<DDMFormLayoutPage> getDDMFormLayoutPages(
		JSONArray jsonArray) {

		List<DDMFormLayoutPage> ddmFormLayoutPages = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutPage ddmFormLayoutPage = getDDMFormLayoutPage(
				jsonArray.getJSONObject(i));

			ddmFormLayoutPages.add(ddmFormLayoutPage);
		}

		return ddmFormLayoutPages;
	}

	protected DDMFormLayoutRow getDDMFormLayoutRow(JSONObject jsonObject) {
		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		setDDMFormLayoutRowColumns(
			jsonObject.getJSONArray("columns"), ddmFormLayoutRow);

		return ddmFormLayoutRow;
	}

	protected List<DDMFormLayoutRow> getDDMFormLayoutRows(JSONArray jsonArray) {
		List<DDMFormLayoutRow> ddmFormLayoutRows = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutRow ddmFormLayoutRow = getDDMFormLayoutRow(
				jsonArray.getJSONObject(i));

			ddmFormLayoutRows.add(ddmFormLayoutRow);
		}

		return ddmFormLayoutRows;
	}

	protected LocalizedValue getDescription(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		LocalizedValue description = new LocalizedValue();

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			description.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return description;
	}

	protected LocalizedValue getTitle(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		LocalizedValue title = new LocalizedValue();

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			title.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return title;
	}

	protected void setDDMFormLayouColumnFieldNames(
		JSONArray jsonArray, DDMFormLayoutColumn ddmFormLayoutColumn) {

		List<String> ddmFormLayoutColumnNames =
			getDDMFormLayoutColumnFieldNames(jsonArray);

		ddmFormLayoutColumn.setDDMFormFieldNames(ddmFormLayoutColumnNames);
	}

	protected void setDDMFormLayoutDefaultLocale(
		String defaultLanguageId, DDMFormLayout ddmFormLayout) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmFormLayout.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormLayoutPageDescription(
		JSONObject jsonObject, DDMFormLayoutPage ddmFormLayoutPage) {

		LocalizedValue description = getDescription(jsonObject);

		if (description == null) {
			return;
		}

		ddmFormLayoutPage.setDescription(description);
	}

	protected void setDDMFormLayoutPageRows(
		JSONArray jsonArray, DDMFormLayoutPage ddmFormLayoutPage) {

		ddmFormLayoutPage.setDDMFormLayoutRows(getDDMFormLayoutRows(jsonArray));
	}

	protected void setDDMFormLayoutPages(
		JSONArray jsonArray, DDMFormLayout ddmFormLayout) {

		ddmFormLayout.setDDMFormLayoutPages(getDDMFormLayoutPages(jsonArray));
	}

	protected void setDDMFormLayoutPageTitle(
		JSONObject jsonObject, DDMFormLayoutPage ddmFormLayoutPage) {

		LocalizedValue title = getTitle(jsonObject);

		if (title == null) {
			return;
		}

		ddmFormLayoutPage.setTitle(title);
	}

	protected void setDDMFormLayoutPageTitlesDefaultLocale(
		DDMFormLayout ddmFormLayout) {

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue title = ddmFormLayoutPage.getTitle();

			title.setDefaultLocale(ddmFormLayout.getDefaultLocale());
		}
	}

	protected void setDDMFormLayoutPaginationMode(
		String paginationMode, DDMFormLayout ddmFormLayout) {

		ddmFormLayout.setPaginationMode(paginationMode);
	}

	protected void setDDMFormLayoutRowColumns(
		JSONArray jsonArray, DDMFormLayoutRow ddmFormLayoutRow) {

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			getDDMFormLayoutColumns(jsonArray));
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormLayoutJSONDeserializer.class);

	private JSONFactory _jsonFactory;

}