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

import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONDeserializerTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpDDMFormLayoutJSONDeserializerUtil();
		setUpJSONFactoryUtil();
	}

	@Test
	public void testDDMFormLayoutDeserialization() throws Exception {
		String serializedDDMFormLayout = read(
			"ddm-form-layout-json-deserializer-test-data.json");

		DDMFormLayout ddmFormLayout =
			DDMFormLayoutJSONDeserializerUtil.deserialize(
				serializedDDMFormLayout);

		List<DDMFormLayoutRow> ddmFormLayoutRows =
			ddmFormLayout.getDDMFormLayoutRows();

		assertEquals(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumns("text1", "text2")),
			ddmFormLayoutRows.get(0));
		assertEquals(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumns("text3", "text4", "text5", "text6")),
			ddmFormLayoutRows.get(1));
		assertEquals(
			createDDMFormLayoutRow(createDDMFormLayoutColumns("text7")),
			ddmFormLayoutRows.get(2));
		assertEquals(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumns("text8", "text9", "text10")),
			ddmFormLayoutRows.get(3));
	}

	protected void assertEquals(
		DDMFormLayoutColumn expectedDDMFormLayoutColumn,
		DDMFormLayoutColumn actualDDMFormLayoutColumn) {

		Assert.assertEquals(
			expectedDDMFormLayoutColumn.getDDMFormFieldName(),
			actualDDMFormLayoutColumn.getDDMFormFieldName());
		Assert.assertEquals(
			expectedDDMFormLayoutColumn.getSize(),
			actualDDMFormLayoutColumn.getSize());
	}

	protected void assertEquals(
		DDMFormLayoutRow expectedDDMFormLayoutRow,
		DDMFormLayoutRow actualDDMFormLayoutRow) {

		List<DDMFormLayoutColumn> expectedDDMFormLayoutColumns =
			expectedDDMFormLayoutRow.getDDMFormLayoutColumns();

		for (int i = 0; i < expectedDDMFormLayoutColumns.size(); i++) {
			DDMFormLayoutColumn expectedDDMFormLayoutColumn =
				expectedDDMFormLayoutColumns.get(i);

			assertEquals(
				expectedDDMFormLayoutColumn,
				actualDDMFormLayoutRow.getDDMFormLayoutColumn(i));
		}
	}

	protected void setUpDDMFormLayoutJSONDeserializerUtil() {
		DDMFormLayoutJSONDeserializerUtil ddmFormLayoutJSONDeserializerUtil =
			new DDMFormLayoutJSONDeserializerUtil();

		ddmFormLayoutJSONDeserializerUtil.setDDMFormLayoutJSONDeserializer(
			new DDMFormLayoutJSONDeserializerImpl());
	}

}