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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONSerializerTest extends BaseDDMTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMFormLayoutJSONSerializer();
	}

	@Test
	public void testDDMFormLayoutSerialization() throws Exception {
		String expectedJSON = read(
			"ddm-form-layout-json-serializer-test-data.json");

		DDMFormLayout ddmFormLayout = createDDMFormLayout();

		String actualJSON = serialize(ddmFormLayout);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected DDMFormLayout createDDMFormLayout() {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			"Page 1", "Pagina 1");

		ddmFormLayoutPage.addDDMFormLayoutRow(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumns("text1", "text2")));
		ddmFormLayoutPage.addDDMFormLayoutRow(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumns("text3", "text4", "text5")));
		ddmFormLayoutPage.addDDMFormLayoutRow(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumn(12, "text6", "text7", "text8")));

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		return ddmFormLayout;
	}

	protected DDMFormLayoutPage createDDMFormLayoutPage(
		String enTitle, String ptTitle) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		title.addString(LocaleUtil.US, enTitle);
		title.addString(LocaleUtil.BRAZIL, ptTitle);

		return ddmFormLayoutPage;
	}

	protected String serialize(DDMFormLayout ddmFormLayout) {
		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutJSONSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	protected void setUpDDMFormLayoutJSONSerializer() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			DDMFormLayoutJSONSerializer.class, "_jsonFactory");

		field.set(_ddmFormLayoutJSONSerializer, new JSONFactoryImpl());
	}

	private final DDMFormLayoutJSONSerializer _ddmFormLayoutJSONSerializer =
		new DDMFormLayoutJSONSerializer();

}