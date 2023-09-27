/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.TestDDMForm;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class DDMFormFieldFactoryHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
	public void testGetDDMFormFieldValidationWithAllParameters()
		throws Exception {

		Class<?> clazz = TestDDMForm.class;

		DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
			new DDMFormFieldFactoryHelper(
				new DDMFormFactoryHelper(clazz),
				clazz.getMethod("fieldWithAllValidationParameters"));

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormFieldFactoryHelper.getDDMFormFieldValidation();

		LocalizedValue localizedValue =
			ddmFormFieldValidation.getErrorMessageLocalizedValue();

		Assert.assertEquals(
			"errorMessage",
			localizedValue.getString(localizedValue.getDefaultLocale()));

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		Assert.assertEquals(
			"expression", ddmFormFieldValidationExpression.getValue());
		Assert.assertEquals(
			"expressionName", ddmFormFieldValidationExpression.getName());

		localizedValue = ddmFormFieldValidation.getParameterLocalizedValue();

		Assert.assertEquals(
			"parameter",
			localizedValue.getString(localizedValue.getDefaultLocale()));
	}

	@Test
	public void testGetDDMFormFieldValidationWithPartialParameters()
		throws Exception {

		Class<?> clazz = TestDDMForm.class;

		DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
			new DDMFormFieldFactoryHelper(
				new DDMFormFactoryHelper(clazz),
				clazz.getMethod("fieldWithPartialValidationParameters"));

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormFieldFactoryHelper.getDDMFormFieldValidation();

		LocalizedValue localizedValue =
			ddmFormFieldValidation.getErrorMessageLocalizedValue();

		Assert.assertEquals(
			"errorMessage",
			localizedValue.getString(localizedValue.getDefaultLocale()));

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		Assert.assertEquals(
			"expression", ddmFormFieldValidationExpression.getValue());
		Assert.assertNull(ddmFormFieldValidationExpression.getName());

		Assert.assertNull(ddmFormFieldValidation.getParameterLocalizedValue());
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