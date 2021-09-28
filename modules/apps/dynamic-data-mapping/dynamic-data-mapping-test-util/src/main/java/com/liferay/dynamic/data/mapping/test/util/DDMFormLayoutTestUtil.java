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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.stream.Stream;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormLayoutTestUtil {

	public static DDMFormLayout createDDMFormLayout(
		DDMFormLayoutPage... ddmFormLayoutPages) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		Stream.of(
			ddmFormLayoutPages
		).forEach(
			ddmFormLayout::addDDMFormLayoutPage
		);

		return ddmFormLayout;
	}

	public static DDMFormLayout createDDMFormLayout(
		String paginationMode, DDMFormLayoutPage... ddmFormLayoutPages) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setPaginationMode(paginationMode);

		Stream.of(
			ddmFormLayoutPages
		).forEach(
			ddmFormLayout::addDDMFormLayoutPage
		);

		return ddmFormLayout;
	}

	public static DDMFormLayout createDDMFormLayout(
		String pageDescription, String pageTitle, String[] ddmFormFieldNames) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.addDDMFormLayoutPage(
			createDDMFormLayoutPage(
				pageDescription, pageTitle, ddmFormFieldNames));

		return ddmFormLayout;
	}

	public static DDMFormLayoutColumn createDDMFormLayoutColumn(
		String... ddmFormFieldNames) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		ddmFormLayoutColumn.setDDMFormFieldNames(
			ListUtil.fromArray(ddmFormFieldNames));
		ddmFormLayoutColumn.setSize(DDMFormLayoutColumn.FULL);

		return ddmFormLayoutColumn;
	}

	public static DDMFormLayoutColumn[] createDDMFormLayoutColumns(
		String... ddmFormFieldNames) {

		return Stream.of(
			ddmFormFieldNames
		).map(
			ddmFormFieldName -> {
				DDMFormLayoutColumn ddmFormLayoutColumn =
					new DDMFormLayoutColumn();

				ddmFormLayoutColumn.setDDMFormFieldNames(
					ListUtil.fromArray(ddmFormFieldNames));
				ddmFormLayoutColumn.setSize(
					DDMFormLayoutColumn.FULL / ddmFormFieldNames.length);

				return ddmFormLayoutColumn;
			}
		).toArray(
			DDMFormLayoutColumn[]::new
		);
	}

	public static DDMFormLayoutPage createDDMFormLayoutPage(
		String... ddmFormFieldNames) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.addDDMFormLayoutRow(
			createDDMFormLayoutRow(ddmFormFieldNames));

		return ddmFormLayoutPage;
	}

	public static DDMFormLayoutPage createDDMFormLayoutPage(
		String description, String title,
		DDMFormLayoutColumn... ddmFormLayoutColumns) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.addDDMFormLayoutRow(
			createDDMFormLayoutRow(ddmFormLayoutColumns));
		ddmFormLayoutPage.setDescription(
			DDMFormValuesTestUtil.createLocalizedValue(
				description, LocaleUtil.US));
		ddmFormLayoutPage.setTitle(
			DDMFormValuesTestUtil.createLocalizedValue(title, LocaleUtil.US));

		return ddmFormLayoutPage;
	}

	public static DDMFormLayoutPage createDDMFormLayoutPage(
		String description, String title,
		DDMFormLayoutRow... ddmFormLayoutRows) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		Stream.of(
			ddmFormLayoutRows
		).forEach(
			ddmFormLayoutPage::addDDMFormLayoutRow
		);

		ddmFormLayoutPage.setDescription(
			DDMFormValuesTestUtil.createLocalizedValue(
				description, LocaleUtil.US));
		ddmFormLayoutPage.setTitle(
			DDMFormValuesTestUtil.createLocalizedValue(title, LocaleUtil.US));

		return ddmFormLayoutPage;
	}

	public static DDMFormLayoutPage createDDMFormLayoutPage(
		String description, String title, String[] ddmFormFieldNames) {

		return createDDMFormLayoutPage(
			description, title, createDDMFormLayoutColumn(ddmFormFieldNames));
	}

	public static DDMFormLayoutRow createDDMFormLayoutRow(
		DDMFormLayoutColumn... ddmFormLayoutColumns) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		Stream.of(
			ddmFormLayoutColumns
		).forEach(
			ddmFormLayoutRow::addDDMFormLayoutColumn
		);

		return ddmFormLayoutRow;
	}

	public static DDMFormLayoutRow createDDMFormLayoutRow(
		String... ddmFormFieldNames) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.addDDMFormLayoutColumn(
			createDDMFormLayoutColumn(ddmFormFieldNames));

		return ddmFormLayoutRow;
	}

}