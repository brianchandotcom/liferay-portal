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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutTransformer {

	public DDMFormLayoutTransformer(
		DDMForm ddmForm, DDMFormLayout ddmFormLayout,
		Map<String, List<Object>> renderedDDMFormFieldsMap,
		boolean showRequiredFieldsWarning, Locale locale) {

		_ddmFormLayout = ddmFormLayout;
		_renderedDDMFormFieldsMap = renderedDDMFormFieldsMap;
		_showRequiredFieldsWarning = showRequiredFieldsWarning;
		_locale = locale;

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);
	}

	public List<Object> getPages() {
		return getPages(_ddmFormLayout.getDDMFormLayoutPages());
	}

	protected boolean containsRequiredField(List<String> ddmFormFieldNames) {
		for (String ddmFormFieldName : ddmFormFieldNames) {
			DDMFormField ddmFormField = _ddmFormFieldsMap.get(ddmFormFieldName);

			if (ddmFormField.isRequired()) {
				return true;
			}
		}

		return false;
	}

	protected Map<String, Object> getColumn(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		Map<String, Object> column = new HashMap<>();

		column.put(
			"fields", getFields(ddmFormLayoutColumn.getDDMFormFieldNames()));
		column.put("size", ddmFormLayoutColumn.getSize());

		return column;
	}

	protected List<Object> getColumns(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		List<Object> columns = new ArrayList<>();

		for (DDMFormLayoutColumn ddmFormLayoutColumn : ddmFormLayoutColumns) {
			columns.add(getColumn(ddmFormLayoutColumn));
		}

		return columns;
	}

	protected List<Object> getFields(List<String> ddmFormFieldNames) {
		List<Object> renderedDDMFormFields = new ArrayList<>();

		for (String ddmFormFieldName : ddmFormFieldNames) {
			renderedDDMFormFields.addAll(
				_renderedDDMFormFieldsMap.get(ddmFormFieldName));
		}

		return renderedDDMFormFields;
	}

	protected Map<String, Object> getPage(DDMFormLayoutPage ddmFormLayoutPage) {
		Map<String, Object> page = new HashMap<>();

		LocalizedValue description = ddmFormLayoutPage.getDescription();

		page.put("description", description.getString(_locale));

		page.put("rows", getRows(ddmFormLayoutPage.getDDMFormLayoutRows()));

		boolean showRequiredFieldsWarning = isShowRequiredFieldsWarning(
			ddmFormLayoutPage.getDDMFormLayoutRows());

		page.put("showRequiredFieldsWarning", showRequiredFieldsWarning);

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		page.put("title", title.getString(_locale));

		return page;
	}

	protected List<Object> getPages(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		List<Object> pages = new ArrayList<>();

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			pages.add(getPage(ddmFormLayoutPage));
		}

		return pages;
	}

	protected Map<String, Object> getRow(DDMFormLayoutRow ddFormLayoutRow) {
		Map<String, Object> row = new HashMap<>();

		row.put(
			"columns", getColumns(ddFormLayoutRow.getDDMFormLayoutColumns()));

		return row;
	}

	protected List<Object> getRows(List<DDMFormLayoutRow> ddmFormLayoutRows) {
		List<Object> rows = new ArrayList<>();

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			rows.add(getRow(ddmFormLayoutRow));
		}

		return rows;
	}

	protected boolean isShowRequiredFieldsWarning(
		List<DDMFormLayoutRow> ddmFormLayoutRows) {

		if (!_showRequiredFieldsWarning) {
			return false;
		}

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			for (DDMFormLayoutColumn ddmFormLayoutColumn :
					ddmFormLayoutRow.getDDMFormLayoutColumns()) {

				if (containsRequiredField(
						ddmFormLayoutColumn.getDDMFormFieldNames())) {

					return true;
				}
			}
		}

		return false;
	}

	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final DDMFormLayout _ddmFormLayout;
	private final Locale _locale;
	private final Map<String, List<Object>> _renderedDDMFormFieldsMap;
	private final boolean _showRequiredFieldsWarning;

}