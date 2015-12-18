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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormFieldTypeSettingsSerializerHelper;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormLayoutJSONSerializerImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypeSettingsSerializerHelperTest
	extends BaseDDMFormSerializerTestCase {

	@Test
	public void testGetSettingsLayout() throws Exception {
		JSONFactory jsonFactory = new JSONFactoryImpl();

		DDMFormFieldTypeSettingsSerializerHelper
			ddmFormFieldTypeSettingsSerializerHelper =
				new DDMFormFieldTypeSettingsSerializerHelper(
					SampleDDMFormFieldTypeSettings.class,
					new DDMFormJSONSerializerImpl(),
					new DDMFormLayoutJSONSerializerImpl(), jsonFactory);

		String expectedJSON = read(
			"ddm-form-field-type-settings-layout-serializer-test-data.json");

		JSONObject actualJSONObject =
			ddmFormFieldTypeSettingsSerializerHelper.
				getSettingsLayoutJSONObject();

		JSONAssert.assertEquals(
			expectedJSON, actualJSONObject.toString(), false);
	}

	@Test
	public void testGetSettingsLayoutWithDefaultColumnSize() throws Exception {
		JSONFactory jsonFactory = new JSONFactoryImpl();

		DDMFormFieldTypeSettingsSerializerHelper
			ddmFormFieldTypeSettingsSerializerHelper =
				new DDMFormFieldTypeSettingsSerializerHelper(
					SampleDDMFormFieldTypeSettingsWithDefaultColumnSize.class,
					new DDMFormJSONSerializerImpl(),
					new DDMFormLayoutJSONSerializerImpl(), jsonFactory);

		String expectedJSON = read(
			"ddm-form-field-type-settings-layout-serializer-test-data-" +
				"default-column-size.json");

		JSONObject actualJSONObject =
			ddmFormFieldTypeSettingsSerializerHelper.
				getSettingsLayoutJSONObject();

		JSONAssert.assertEquals(
			expectedJSON, actualJSONObject.toString(), false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithoutLayout() throws Exception {
		JSONFactory jsonFactory = new JSONFactoryImpl();

		DDMFormFieldTypeSettingsSerializerHelper
			ddmFormFieldTypeSettingsSerializerHelper =
				new DDMFormFieldTypeSettingsSerializerHelper(
					SampleDDMFormFieldTypeSettingsWithNoLayout.class,
					new DDMFormJSONSerializerImpl(),
					new DDMFormLayoutJSONSerializerImpl(), jsonFactory);

		ddmFormFieldTypeSettingsSerializerHelper.getSettingsLayoutJSONObject();
	}

	@DDMForm
	@DDMFormLayout(
		{
			@DDMFormLayoutPage(
				title = "basic",
				value = {
					@DDMFormLayoutRow(
						{
							@DDMFormLayoutColumn(
								size = 12,
								value = {"c", "b", "a"}
							)
						}
					)
				}
			),
			@DDMFormLayoutPage(
				title = "advanced",
				value = {
					@DDMFormLayoutRow(
						{
							@DDMFormLayoutColumn(size = 6, value = {"e"}),
							@DDMFormLayoutColumn(size = 6, value = {"d"})
						}
					)
				}
			)
		}
	)
	private interface SampleDDMFormFieldTypeSettings {

		@DDMFormField
		public String a();

		@DDMFormField
		public String b();

		@DDMFormField
		public String c();

		@DDMFormField
		public String d();

		@DDMFormField
		public String e();

	}

	@DDMForm
	@DDMFormLayout(
		{
			@DDMFormLayoutPage(
				title = "basic",
				value = {
					@DDMFormLayoutRow(
						{
							@DDMFormLayoutColumn(value = {"a", "b"}
	)
						}
					)
				}
			),
			@DDMFormLayoutPage(
				title = "advanced",
				value = {
					@DDMFormLayoutRow(
						{
							@DDMFormLayoutColumn(value = {"e", "c", "d"})
						}
					)
				}
			)
		}
	)
	private interface SampleDDMFormFieldTypeSettingsWithDefaultColumnSize {

		@DDMFormField
		public String a();

		@DDMFormField
		public String b();

		@DDMFormField
		public String c();

		@DDMFormField
		public String d();

		@DDMFormField
		public String e();

	}

	@DDMForm
	private interface SampleDDMFormFieldTypeSettingsWithNoLayout {

		@DDMFormField
		public String a();

		@DDMFormField
		public String b();

		@DDMFormField
		public String c();

		@DDMFormField
		public String d();

		@DDMFormField
		public String e();

	}

}