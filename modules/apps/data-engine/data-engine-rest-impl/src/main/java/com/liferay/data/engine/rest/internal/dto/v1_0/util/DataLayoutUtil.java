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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutUtil {

	public static DataLayout toDataLayout(String content) throws Exception {
		DataLayout dataLayout = new DataLayout();

		JSONObject jsonObject = null;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(content);

			dataLayout.setPaginationMode(
				jsonObject.getString("paginationMode"));

			dataLayout.setDefaultLanguageId(
				jsonObject.getString("defaultLanguageId"));

			_createDEDataLayoutPages(dataLayout, jsonObject);
		}
		catch (JSONException jsone) {
			throw new Exception(jsone);
		}

		return dataLayout;
	}

	public static String toJSONString(DataLayout dataLayout) throws Exception {
		String defaultLanguageId = dataLayout.getDefaultLanguageId();

		if (Validator.isNull(defaultLanguageId)) {
			throw new Exception("Default language id cannot be null or empty");
		}

		String paginationMode = dataLayout.getPaginationMode();

		if (Validator.isNull(paginationMode) ||
			!ArrayUtil.contains(_PAGINATION_MODES, paginationMode)) {

			throw new Exception(
				"Pagination mode must be 'wizard' or 'pagination'");
		}

		DataLayoutPage[] dataLayoutPages = dataLayout.getDataLayoutPages();

		JSONArray pages = JSONFactoryUtil.createJSONArray();

		for (DataLayoutPage dataLayoutPage : dataLayoutPages) {
			LocalizedValue[] title = dataLayoutPage.getTitle();

			if (title.length < 1) {
				throw new Exception("Page title cannot be empty ");
			}

			JSONObject page = JSONFactoryUtil.createJSONObject();

			page.put("description", dataLayoutPage.getDescription());

			page.put(
				"rows",
				_processDEDataLayoutRows(dataLayoutPage.getDataLayoutRows()));

			page.put("title", title);

			pages.put(page);
		}

		JSONObject layout = JSONFactoryUtil.createJSONObject();

		layout.put("defaultLanguageId", defaultLanguageId);
		layout.put("pages", pages);
		layout.put("paginationMode", paginationMode);

		return layout.toJSONString();
	}

	private static DataLayoutColumn[] _createDEDataLayoutColumns(
		JSONArray columns) {

		List<DataLayoutColumn> dataLayoutColumns = new ArrayList<>();

		for (Object columnObject : columns) {
			JSONObject column = (JSONObject)columnObject;

			DataLayoutColumn dataLayoutColumn = new DataLayoutColumn();

			dataLayoutColumn.setColumnSize(column.getInt("columnSize"));

			List<String> fieldsNameList = JSONUtil.toStringList(
				column.getJSONArray("fieldNames"));

			dataLayoutColumn.setFieldsName(
				fieldsNameList.toArray(new String[fieldsNameList.size()]));

			dataLayoutColumns.add(dataLayoutColumn);
		}

		return dataLayoutColumns.toArray(
			new DataLayoutColumn[dataLayoutColumns.size()]);
	}

	private static void _createDEDataLayoutPages(
		DataLayout dataLayout, JSONObject jsonObject) {

		if (jsonObject.has("pages")) {
			List<DataLayoutPage> dataLayoutPages = new ArrayList<>();

			JSONArray jsonArray = jsonObject.getJSONArray("pages");

			for (Object pageObject : jsonArray) {
				JSONObject page = (JSONObject)pageObject;

				DataLayoutPage dataLayoutPage = new DataLayoutPage();

				dataLayoutPage.setDescription(
					_getLocalizedValues(page.getJSONObject("description")));

				dataLayoutPage.setDataLayoutRows(
					_createDEDataLayoutRows(page.getJSONArray("rows")));

				dataLayoutPage.setTitle(
					_getLocalizedValues(page.getJSONObject("title")));

				dataLayoutPages.add(dataLayoutPage);
			}

			dataLayout.setDataLayoutPages(
				dataLayoutPages.toArray(
					new DataLayoutPage[dataLayoutPages.size()]));
		}
	}

	private static DataLayoutRow[] _createDEDataLayoutRows(JSONArray rows) {
		List<DataLayoutRow> dataLayoutRows = new ArrayList<>();

		for (Object rowObject : rows) {
			JSONObject row = (JSONObject)rowObject;

			DataLayoutRow dataLayoutRow = new DataLayoutRow();

			dataLayoutRow.setDataLayoutColums(
				_createDEDataLayoutColumns(row.getJSONArray("columns")));

			dataLayoutRows.add(dataLayoutRow);
		}

		return dataLayoutRows.toArray(new DataLayoutRow[dataLayoutRows.size()]);
	}

	private static LocalizedValue[] _getLocalizedValues(JSONObject jsonObject) {
		Iterator<String> keys = jsonObject.keys();

		List<LocalizedValue> localizedValues = new ArrayList<>();

		while (keys.hasNext()) {
			String key = keys.next();

			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.setKey(key);
			localizedValue.setValue(jsonObject.getString(key));

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(
			new LocalizedValue[localizedValues.size()]);
	}

	private static JSONObject _processDEDataLayoutColumns(
		DataLayoutColumn[] dataLayoutColumns) {

		JSONArray columnsArray = JSONFactoryUtil.createJSONArray();

		for (DataLayoutColumn dataLayoutColumn : dataLayoutColumns) {
			JSONObject column = JSONFactoryUtil.createJSONObject();

			column.put("size", dataLayoutColumn.getColumnSize());

			JSONArray fieldNames = JSONFactoryUtil.createJSONArray();

			for (String fieldName : dataLayoutColumn.getFieldsName()) {
				fieldNames.put(fieldName);
			}

			column.put("fieldNames", fieldNames);

			columnsArray.put(column);
		}

		JSONObject columns = JSONFactoryUtil.createJSONObject();

		columns.put("columns", columnsArray);

		return columns;
	}

	private static JSONArray _processDEDataLayoutRows(
		DataLayoutRow[] dataLayoutRows) {

		JSONArray rowsArray = JSONFactoryUtil.createJSONArray();

		for (DataLayoutRow dataLayoutRow : dataLayoutRows) {
			rowsArray.put(
				_processDEDataLayoutColumns(
					dataLayoutRow.getDataLayoutColums()));
		}

		return rowsArray;
	}

	private static final String[] _PAGINATION_MODES = {"wizard", "pagination"};

}