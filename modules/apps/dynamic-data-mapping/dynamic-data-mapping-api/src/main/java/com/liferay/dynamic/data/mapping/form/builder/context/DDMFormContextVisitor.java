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

package com.liferay.dynamic.data.mapping.form.builder.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.function.Consumer;

/**
 * @author Marcellus Tavares
 */
public class DDMFormContextVisitor {

	public DDMFormContextVisitor(JSONArray pagesJSONArray) {
		_pagesJSONArray = pagesJSONArray;
	}

	public void onVisitColumn(Consumer<JSONObject> columnConsumer) {
		_columnConsumer = columnConsumer;
	}

	public void onVisitField(Consumer<JSONObject> fieldConsumer) {
		_fieldConsumer = fieldConsumer;
	}

	public void onVisitPage(Consumer<JSONObject> pageConsumer) {
		_pageConsumer = pageConsumer;
	}

	public void onVisitRow(Consumer<JSONObject> rowConsumer) {
		_rowConsumer = rowConsumer;
	}

	public void visit() {
		traversePages(_pagesJSONArray);
	}

	protected void traverseColumns(JSONArray columnnsJSONArray) {
		for (int i = 0; i < columnnsJSONArray.length(); i++) {
			JSONObject columnJSONObject = columnnsJSONArray.getJSONObject(i);

			traverseFields(columnJSONObject.getJSONArray("fields"));

			_columnConsumer.accept(columnJSONObject);
		}
	}

	protected void traverseFields(JSONArray fieldsJSONArray) {
		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			_fieldConsumer.accept(fieldJSONObject);
		}
	}

	protected void traversePages(JSONArray pagesJSONArray) {
		for (int i = 0; i < pagesJSONArray.length(); i++) {
			JSONObject pageJSONObject = pagesJSONArray.getJSONObject(i);

			traverseRows(pageJSONObject.getJSONArray("rows"));

			_pageConsumer.accept(pageJSONObject);
		}
	}

	protected void traverseRows(JSONArray rowsJSONArray) {
		for (int i = 0; i < rowsJSONArray.length(); i++) {
			JSONObject rowJSONObject = rowsJSONArray.getJSONObject(i);

			traverseColumns(rowJSONObject.getJSONArray("columns"));

			_rowConsumer.accept(rowJSONObject);
		}
	}

	private Consumer<JSONObject> _columnConsumer = new NOPJSONObjectConsumer();
	private Consumer<JSONObject> _fieldConsumer = new NOPJSONObjectConsumer();
	private Consumer<JSONObject> _pageConsumer = new NOPJSONObjectConsumer();
	private final JSONArray _pagesJSONArray;
	private Consumer<JSONObject> _rowConsumer = new NOPJSONObjectConsumer();

	private static class NOPJSONObjectConsumer implements Consumer<JSONObject> {

		@Override
		public void accept(JSONObject jsonObject) {
		}

	}

}