/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONSerializerTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpDDMFormLayoutJSONSerializer();
	}

	@Test
	public void testDDMFormLayoutSerialization() throws Exception {
		String expectedJSON = read(
			"ddm-form-layout-json-serializer-test-data.json");

		DDMFormLayout ddmFormLayout = _createDDMFormLayout();

		String actualJSON = serialize(ddmFormLayout);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	@Test
	public void testDDMFormLayoutSerializationWithSchemaVersion()
		throws Exception {

		String expectedJSON = read(
			"ddm-form-layout-json-serializer-with-definition-schema-" +
				"version.json");

		DDMFormLayout ddmFormLayout = _createDDMFormLayout();

		ddmFormLayout.setDefinitionSchemaVersion("2.0");

		String actualJSON = serialize(ddmFormLayout);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
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

	private DDMFormLayout _createDDMFormLayout() {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
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

	private DDMFormLayoutPage _createDDMFormLayoutPage(
		String enTitle, String ptTitle) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		title.addString(LocaleUtil.US, enTitle);
		title.addString(LocaleUtil.BRAZIL, ptTitle);

		return ddmFormLayoutPage;
	}

	private void _setUpDDMFormLayoutJSONSerializer() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			DDMFormLayoutJSONSerializer.class, "_jsonFactory");

		field.set(_ddmFormLayoutJSONSerializer, new JSONFactoryImpl());
	}

	private final DDMFormLayoutJSONSerializer _ddmFormLayoutJSONSerializer =
		new DDMFormLayoutJSONSerializer();

}