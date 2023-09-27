/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormLayoutTestUtil {

	public static DDMFormLayout createDDMFormLayout(
		DDMFormLayoutPage... ddmFormLayoutPages) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);
		}

		return ddmFormLayout;
	}

	public static DDMFormLayout createDDMFormLayout(
		String paginationMode, DDMFormLayoutPage... ddmFormLayoutPages) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setPaginationMode(paginationMode);

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);
		}

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

		return TransformUtil.transform(
			ddmFormFieldNames,
			ddmFormFieldName -> {
				DDMFormLayoutColumn ddmFormLayoutColumn =
					new DDMFormLayoutColumn();

				ddmFormLayoutColumn.setDDMFormFieldNames(
					ListUtil.fromArray(ddmFormFieldNames));
				ddmFormLayoutColumn.setSize(
					DDMFormLayoutColumn.FULL / ddmFormFieldNames.length);

				return ddmFormLayoutColumn;
			},
			DDMFormLayoutColumn.class);
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

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);
		}

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

		for (DDMFormLayoutColumn ddmFormLayoutColumn : ddmFormLayoutColumns) {
			ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);
		}

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