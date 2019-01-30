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

import com.liferay.data.engine.exception.DEDataLayoutSerializerException;
import com.liferay.data.engine.io.DEDataLayoutSerializer;
import com.liferay.data.engine.io.DEDataLayoutSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataLayoutSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "data.layout.serializer.type=json",
	service = DEDataLayoutSerializer.class
)
public class DEDataLayoutJSONSerializer implements DEDataLayoutSerializer {

	@Override
	public DEDataLayoutSerializerApplyResponse apply(
			DEDataLayoutSerializerApplyRequest
				deDataLayoutSerializerApplyRequest)
		throws DEDataLayoutSerializerException {

		return DEDataLayoutSerializerApplyResponse.Builder.of(
			processDEDataLayoutPages(
				deDataLayoutSerializerApplyRequest.getDEDataLayout()));
	}

	protected JSONObject processDEDataLayoutColumns(
		Queue<DEDataLayoutColumn> deDataLayoutColumns) {

		JSONArray columnsArray = jsonFactory.createJSONArray();

		for (DEDataLayoutColumn deDataLayoutColumn : deDataLayoutColumns) {
			JSONObject column = jsonFactory.createJSONObject();

			column.put("size", deDataLayoutColumn.getColumnSize());

			JSONArray fieldNames = jsonFactory.createJSONArray();

			for (String fieldName : deDataLayoutColumn.getFieldsName()) {
				fieldNames.put(fieldName);
			}

			column.put("fieldNames", fieldNames);

			columnsArray.put(column);
		}

		JSONObject columns = jsonFactory.createJSONObject();

		columns.put("columns", columnsArray);

		return columns;
	}

	protected String processDEDataLayoutPages(DEDataLayout deDataLayout)
		throws DEDataLayoutSerializerException {

		String defaultLanguageId = deDataLayout.getDefaultLanguageId();

		if (Validator.isNull(defaultLanguageId)) {
			throw new DEDataLayoutSerializerException.InvalidDefaultLanguageId(
				"Default language id cannot be null or empty");
		}

		String paginationMode = deDataLayout.getPaginationMode();

		if (Validator.isNull(paginationMode) ||
			!ArrayUtil.contains(_PAGINATION_MODES, paginationMode)) {

			throw new DEDataLayoutSerializerException.InvalidPaginationMode(
				"Pagination mode must be 'wizard' or 'pagination'");
		}

		Queue<DEDataLayoutPage> deDataLayoutPages =
			deDataLayout.getDEDataLayoutPages();

		JSONArray pages = jsonFactory.createJSONArray();

		for (DEDataLayoutPage deDataLayoutPage : deDataLayoutPages) {
			Map<String, String> title = deDataLayoutPage.getTitle();

			if (title.isEmpty()) {
				throw new DEDataLayoutSerializerException.InvalidPageTitle(
					"Page title cannot be empty ");
			}

			JSONObject page = jsonFactory.createJSONObject();

			page.put("description", deDataLayoutPage.getDescription());

			page.put(
				"rows",
				processDEDataLayoutRows(
					deDataLayoutPage.getDEDataLayoutRows()));

			page.put("title", title);

			pages.put(page);
		}

		JSONObject layout = jsonFactory.createJSONObject();

		layout.put("defaultLanguageId", defaultLanguageId);
		layout.put("pages", pages);
		layout.put("paginationMode", paginationMode);

		return layout.toString();
	}

	protected JSONArray processDEDataLayoutRows(
		Queue<DEDataLayoutRow> deDataLayoutRows) {

		JSONArray rowsArray = jsonFactory.createJSONArray();

		for (DEDataLayoutRow deDataLayoutRow : deDataLayoutRows) {
			rowsArray.put(
				processDEDataLayoutColumns(
					deDataLayoutRow.getDEDataLayoutColumns()));
		}

		return rowsArray;
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final String[] _PAGINATION_MODES = {"wizard", "pagination"};

}