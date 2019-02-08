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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.exception.DEDataLayoutDeserializerException;
import com.liferay.data.engine.io.DEDataLayoutDeserializer;
import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "data.layout.deserializer.type=json",
	service = DEDataLayoutDeserializer.class
)
public class DEDataLayoutJSONDeserializer implements DEDataLayoutDeserializer {

	@Override
	public DEDataLayoutDeserializerApplyResponse apply(
			DEDataLayoutDeserializerApplyRequest
				deDataLayoutDeserializerApplyRequest)
		throws DEDataLayoutDeserializerException {

		DEDataLayout deDataLayout = new DEDataLayout();

		JSONObject jsonObject = null;

		try {
			String content = deDataLayoutDeserializerApplyRequest.getContent();

			jsonObject = jsonFactory.createJSONObject(content);

			deDataLayout.setPaginationMode(
				jsonObject.getString("paginationMode"));

			deDataLayout.setDefaultLanguageId(
				jsonObject.getString("defaultLanguageId"));

			_createDEDataLayoutPages(deDataLayout, jsonObject);
		}
		catch (JSONException jsone) {
			throw new DEDataLayoutDeserializerException(jsone);
		}

		return DEDataLayoutDeserializerApplyResponse.Builder.of(deDataLayout);
	}

	@Reference
	protected JSONFactory jsonFactory;

	private Queue<DEDataLayoutColumn> _createDEDataLayoutColumns(
		JSONArray columns) {

		Queue<DEDataLayoutColumn> deDataLayoutColumns = new ArrayDeque<>();

		for (Object columnObject : columns) {
			JSONObject column = (JSONObject)columnObject;

			DEDataLayoutColumn deDataLayoutColumn = new DEDataLayoutColumn();

			deDataLayoutColumn.setColumnSize(column.getInt("columnSize"));

			deDataLayoutColumn.setFieldsName(
				JSONUtil.toStringList(column.getJSONArray("fieldNames")));

			deDataLayoutColumns.add(deDataLayoutColumn);
		}

		return deDataLayoutColumns;
	}

	private void _createDEDataLayoutPages(
		DEDataLayout deDataLayout, JSONObject jsonObject) {

		if (jsonObject.has("pages")) {
			Queue<DEDataLayoutPage> deDataLayoutPages = new ArrayDeque<>();

			JSONArray jsonArray = jsonObject.getJSONArray("pages");

			for (Object pageObject : jsonArray) {
				JSONObject page = (JSONObject)pageObject;

				DEDataLayoutPage deDataLayoutPage = new DEDataLayoutPage();

				deDataLayoutPage.setDescription(
					_getLocalizedValues(page.getJSONObject("description")));

				deDataLayoutPage.setDEDataLayoutRows(
					_createDEDataLayoutRows(page.getJSONArray("rows")));

				deDataLayoutPage.setTitle(
					_getLocalizedValues(page.getJSONObject("title")));

				deDataLayoutPages.add(deDataLayoutPage);
			}

			deDataLayout.setDEDataLayoutPages(deDataLayoutPages);
		}
	}

	private Queue<DEDataLayoutRow> _createDEDataLayoutRows(JSONArray rows) {
		Queue<DEDataLayoutRow> deDataLayoutRows = new ArrayDeque<>();

		for (Object rowObject : rows) {
			JSONObject row = (JSONObject)rowObject;

			DEDataLayoutRow deDataLayoutRow = new DEDataLayoutRow();

			deDataLayoutRow.setDEDataLayoutColumns(
				_createDEDataLayoutColumns(row.getJSONArray("columns")));

			deDataLayoutRows.add(deDataLayoutRow);
		}

		return deDataLayoutRows;
	}

	private Map<String, String> _getLocalizedValues(JSONObject jsonObject) {
		Iterator<String> keys = jsonObject.keys();

		Map<String, String> localizedValues = new HashMap<>();

		while (keys.hasNext()) {
			String key = keys.next();

			localizedValues.put(key, jsonObject.getString(key));
		}

		return localizedValues;
	}

}