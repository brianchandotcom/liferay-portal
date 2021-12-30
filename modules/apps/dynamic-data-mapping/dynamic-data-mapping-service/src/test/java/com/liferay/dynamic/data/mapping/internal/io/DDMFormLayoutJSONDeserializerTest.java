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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONDeserializerTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpDDMFormLayoutJSONDeserializer();
	}

	@Test
	public void testDDMFormLayoutDeserialization() throws Exception {
		DDMFormLayout ddmFormLayout = deserialize(
			read("ddm-form-layout-json-deserializer-test-data.json"));

		Assert.assertEquals(LocaleUtil.US, ddmFormLayout.getDefaultLocale());

		DDMFormLayoutPage ddmFormLayoutPage =
			ddmFormLayout.getDDMFormLayoutPage(0);

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		Assert.assertEquals("Page 1", title.getString(LocaleUtil.US));
		Assert.assertEquals("Pagina 1", title.getString(LocaleUtil.BRAZIL));

		List<DDMFormLayoutRow> ddmFormLayoutRows =
			ddmFormLayoutPage.getDDMFormLayoutRows();

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
				new DDMFormLayoutColumn(6, "text8"),
				new DDMFormLayoutColumn(6, "text9", "text10")),
			ddmFormLayoutRows.get(3));
	}

	@Test
	public void testDDMFormLayoutDeserializationWithSchemaVersion()
		throws Exception {

		DDMFormLayout ddmFormLayout = deserialize(
			read(
				"ddm-form-layout-json-deserializer-with-definition-schema-" +
					"version.json"));

		Assert.assertEquals("2.0", ddmFormLayout.getDefinitionSchemaVersion());
	}

	protected void assertEquals(
		DDMFormLayoutColumn expectedDDMFormLayoutColumn,
		DDMFormLayoutColumn actualDDMFormLayoutColumn) {

		Assert.assertEquals(
			expectedDDMFormLayoutColumn.getDDMFormFieldName(0),
			actualDDMFormLayoutColumn.getDDMFormFieldName(0));
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

	protected DDMFormLayout deserialize(String serializedDDMFormLayout) {
		DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
			DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
				serializedDDMFormLayout);

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_ddmFormLayoutJSONDeserializer.deserialize(builder.build());

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

	private void _setUpDDMFormLayoutJSONDeserializer() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			DDMFormLayoutJSONDeserializer.class, "_jsonFactory");

		field.set(_ddmFormLayoutJSONDeserializer, new JSONFactoryImpl());
	}

	private final DDMFormLayoutJSONDeserializer _ddmFormLayoutJSONDeserializer =
		new DDMFormLayoutJSONDeserializer();

}