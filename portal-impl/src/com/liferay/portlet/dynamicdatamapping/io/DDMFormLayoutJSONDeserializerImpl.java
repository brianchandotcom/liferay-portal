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

package com.liferay.portlet.dynamicdatamapping.io;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONDeserializerImpl
	implements DDMFormLayoutJSONDeserializer {

	@Override
	public DDMFormLayout deserialize(String serializedDDMFormLayout)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			serializedDDMFormLayout);

		DDMFormLayout ddmFormLayout = getDDMFormLayout(jsonObject);

		return ddmFormLayout;
	}

	protected DDMFormLayout getDDMFormLayout(JSONObject jsonObject) {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		List<DDMFormLayoutRow> ddmFormLayoutRows = getDDMFormLayoutRows(
			jsonObject.getJSONArray("rows"));

		ddmFormLayout.setDDMFormLayoutRows(ddmFormLayoutRows);

		return ddmFormLayout;
	}

	protected DDMFormLayoutColumn getDDMFormLayoutColumn(
		JSONObject jsonObject) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			jsonObject.getString("fieldName"), jsonObject.getInt("size"));

		return ddmFormLayoutColumn;
	}

	protected List<DDMFormLayoutColumn> getDDMFormLayoutColumns(
		JSONArray jsonArray) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			new ArrayList<DDMFormLayoutColumn>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutColumn ddmFormLayoutColumn = getDDMFormLayoutColumn(
				jsonArray.getJSONObject(i));

			ddmFormLayoutColumns.add(ddmFormLayoutColumn);
		}

		return ddmFormLayoutColumns;
	}

	protected DDMFormLayoutRow getDDMFormLayoutRow(JSONObject jsonObject) {
		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			getDDMFormLayoutColumns(jsonObject.getJSONArray("columns"));

		ddmFormLayoutRow.setDDMFormLayoutColumns(ddmFormLayoutColumns);

		return ddmFormLayoutRow;
	}

	protected List<DDMFormLayoutRow> getDDMFormLayoutRows(JSONArray jsonArray) {
		List<DDMFormLayoutRow> ddmFormLayoutRows =
			new ArrayList<DDMFormLayoutRow>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutRow ddmFormLayoutRow = getDDMFormLayoutRow(
				jsonArray.getJSONObject(i));

			ddmFormLayoutRows.add(ddmFormLayoutRow);
		}

		return ddmFormLayoutRows;
	}

}