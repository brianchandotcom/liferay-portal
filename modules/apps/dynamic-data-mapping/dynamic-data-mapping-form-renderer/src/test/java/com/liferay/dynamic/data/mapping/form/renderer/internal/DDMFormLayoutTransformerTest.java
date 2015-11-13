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

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutTransformerTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPages() {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		LocalizedValue title = new LocalizedValue(_LOCALE);

		title.addString(_LOCALE, "Page 1");

		ddmFormLayoutPage.setTitle(title);

		DDMFormLayoutRow ddmFormLayoutRow1 = new DDMFormLayoutRow();

		ddmFormLayoutRow1.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field_1", "Field_2"));

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow1);

		DDMFormLayoutRow ddmFormLayoutRow2 = new DDMFormLayoutRow();

		ddmFormLayoutRow2.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field_3"));

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow2);

		DDMFormLayoutRow ddmFormLayoutRow3 = new DDMFormLayoutRow();

		ddmFormLayoutRow3.addDDMFormLayoutColumn(
			new DDMFormLayoutColumn(12, "Field_4", "Field_5"));

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow3);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		Map<String, String> renderedDDMFormFieldsMap = new HashMap<>();

		renderedDDMFormFieldsMap.put("Field_1", "Rendered Field 1");
		renderedDDMFormFieldsMap.put("Field_2", "Rendered Field 2");
		renderedDDMFormFieldsMap.put("Field_3", "Rendered Field 3");
		renderedDDMFormFieldsMap.put("Field_4", "Rendered Field 4");
		renderedDDMFormFieldsMap.put("Field_5", "Rendered Field 5");

		DDMFormLayoutTransformer ddmFormLayoutTransformer =
			new DDMFormLayoutTransformer(
				ddmFormLayout, renderedDDMFormFieldsMap, _LOCALE);

		List<Object> pages = ddmFormLayoutTransformer.getPages();

		Assert.assertEquals(1, pages.size());

		Map<String, Object> page1 = (Map<String, Object>)pages.get(0);

		Assert.assertEquals("Page 1", page1.get("title"));

		List<Object> rows = (List<Object>)page1.get("rows");

		Assert.assertEquals(3, rows.size());

		Map<String, Object> row1 = (Map<String, Object>)rows.get(0);

		List<Object> columnsRow1 = (List<Object>)row1.get("columns");

		Assert.assertEquals(2, columnsRow1.size());

		assertColumnEquals(
			new String[] {"Rendered Field 1"}, 6,
			(Map<String, Object>)columnsRow1.get(0));
		assertColumnEquals(
			new String[] {"Rendered Field 2"}, 6,
			(Map<String, Object>)columnsRow1.get(1));

		Map<String, Object> row2 = (Map<String, Object>)rows.get(1);

		List<Object> columnsRow2 = (List<Object>)row2.get("columns");

		Assert.assertEquals(1, columnsRow2.size());

		assertColumnEquals(
			new String[] {"Rendered Field 3"}, 12,
			(Map<String, Object>)columnsRow2.get(0));

		Map<String, Object> row3 = (Map<String, Object>)rows.get(2);

		List<Object> columnsRow3 = (List<Object>)row3.get("columns");

		Assert.assertEquals(1, columnsRow3.size());

		assertColumnEquals(
			new String[] {"Rendered Field 4", "Rendered Field 5"}, 12,
			(Map<String, Object>)columnsRow3.get(0));
	}

	@SuppressWarnings("unchecked")
	protected void assertColumnEquals(
		String[] expectedRenderedDDMFormFields, int expectedSize,
		Map<String, Object> actualColumn) {

		List<String> actualRenderedDDMFormFields =
			(List<String>)actualColumn.get("fields");

		Assert.assertArrayEquals(
			expectedRenderedDDMFormFields,
			ArrayUtil.toStringArray(actualRenderedDDMFormFields));
		Assert.assertEquals(expectedSize, actualColumn.get("size"));
	}

	protected DDMFormLayoutColumn createDDMFormLayoutColumn(
		String ddmFormFieldName, int size) {

		return new DDMFormLayoutColumn(size, ddmFormFieldName);
	}

	protected List<DDMFormLayoutColumn> createDDMFormLayoutColumns(
		String... ddmFormFieldNames) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		int ddmFormLayoutColumnSize =
			(DDMFormLayoutColumn.FULL / ddmFormFieldNames.length);

		for (String ddmFormFieldName : ddmFormFieldNames) {
			ddmFormLayoutColumns.add(
				createDDMFormLayoutColumn(
					ddmFormFieldName, ddmFormLayoutColumnSize));
		}

		return ddmFormLayoutColumns;
	}

	private static final Locale _LOCALE = LocaleUtil.US;

}