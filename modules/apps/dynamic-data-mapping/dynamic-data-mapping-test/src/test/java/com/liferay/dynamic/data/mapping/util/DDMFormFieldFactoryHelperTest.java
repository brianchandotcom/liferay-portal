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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.TestDDMForm;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class DDMFormFieldFactoryHelperTest {

	@Test
	public void testCreateVoidDDMFormField() throws Exception {
		Class<?> clazz = TestDDMForm.class;

		DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
			new DDMFormFieldFactoryHelper(
				new DDMFormFactoryHelper(clazz), clazz.getMethod("voidField"));

		DDMFormField ddmFormField =
			ddmFormFieldFactoryHelper.createDDMFormField();

		Assert.assertEquals("", ddmFormField.getDataType());
	}

	@Test
	public void testGetLabelProperties() throws Exception {
		Class<?> clazz = TestDDMForm.class;

		DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
			new DDMFormFieldFactoryHelper(
				new DDMFormFactoryHelper(clazz), clazz.getMethod("label"));

		Map<String, Object> properties =
			ddmFormFieldFactoryHelper.getProperties();

		Assert.assertEquals(properties.toString(), 4, properties.size());
		Assert.assertEquals("true", properties.get("autoFocus"));
		Assert.assertEquals(
			"%enter-a-field-label", properties.get("placeholder"));
		Assert.assertEquals(
			"%enter-a-descriptive-field-label-that-guides-users-to-enter-the-" +
				"information-you-want",
			properties.get("tooltip"));
		Assert.assertEquals("true", properties.get("visualProperty"));
	}

	@Test
	public void testGetReadOnlyProperties() throws Exception {
		Class<?> clazz = TestDDMForm.class;

		DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
			new DDMFormFieldFactoryHelper(
				new DDMFormFactoryHelper(clazz), clazz.getMethod("readOnly"));

		Map<String, Object> properties =
			ddmFormFieldFactoryHelper.getProperties();

		Assert.assertEquals(properties.toString(), 0, properties.size());
	}

}