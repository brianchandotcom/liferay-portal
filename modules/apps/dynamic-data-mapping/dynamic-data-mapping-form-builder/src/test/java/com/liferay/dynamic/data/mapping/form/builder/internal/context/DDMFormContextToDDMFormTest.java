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

package com.liferay.dynamic.data.mapping.form.builder.internal.context;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMFormContextToDDMFormTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpDDMFormContextToDDMFormValues();
	}

	@Test
	public void testGetDDMFormFieldValidationDateField() throws Exception {
		DDMFormFieldValidation ddmFormFieldValidation =
			_ddmFormContextToDDMForm.getDDMFormFieldValidation(
				SetUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.US), "date",
				LocaleUtil.US,
				JSONUtil.put(
					"parameter", JSONUtil.put("en_US", "Test US")
				).toString());

		LocalizedValue parameterLocalizedValue =
			ddmFormFieldValidation.getParameterLocalizedValue();

		Assert.assertEquals(
			"Test US", parameterLocalizedValue.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"Test US", parameterLocalizedValue.getString(LocaleUtil.US));
	}

	@Test
	public void testGetDDMFormFieldValidationEmptyValue() throws Exception {
		Assert.assertNull(
			_ddmFormContextToDDMForm.getDDMFormFieldValidation(
				SetUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.US), "numeric",
				LocaleUtil.US,
				JSONUtil.put(
					"expression", JSONUtil.put("value", StringPool.BLANK)
				).put(
					"parameter", JSONUtil.put("en_US", "Test")
				).toString()));
	}

	@Test
	public void testGetParameterLocalizedValueDateField() {
		LocalizedValue parameterLocalizedValue =
			_ddmFormContextToDDMForm.getParameterLocalizedValue(
				JSONUtil.put(
					"en_US", "Test US"
				).put(
					"pt_BR", "Test BR"
				),
				SetUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.US), "date",
				LocaleUtil.US);

		Assert.assertEquals(
			"Test US", parameterLocalizedValue.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"Test US", parameterLocalizedValue.getString(LocaleUtil.US));
	}

	@Test
	public void testGetParameterLocalizedValueNumericField() {
		LocalizedValue parameterLocalizedValue =
			_ddmFormContextToDDMForm.getParameterLocalizedValue(
				JSONUtil.put(
					"en_US", "Test US"
				).put(
					"pt_BR", "Test BR"
				),
				SetUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.US), "numeric",
				LocaleUtil.US);

		Assert.assertEquals(
			"Test BR", parameterLocalizedValue.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"Test US", parameterLocalizedValue.getString(LocaleUtil.US));
	}

	@Test
	public void testGetParameterLocalizedValueTextField() {
		LocalizedValue parameterLocalizedValue =
			_ddmFormContextToDDMForm.getParameterLocalizedValue(
				JSONUtil.put("en_US", "Test US"),
				SetUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.US), "text",
				LocaleUtil.US);

		Assert.assertEquals(
			"Test US", parameterLocalizedValue.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"Test US", parameterLocalizedValue.getString(LocaleUtil.US));
	}

	@Test
	public void testGetValueFromValueAccessor() throws IOException {
		Mockito.when(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				Matchers.anyString())
		).thenReturn(
			_ddmFormFieldValueAccessor
		);

		Mockito.when(
			_ddmFormFieldValueAccessor.getValue(Matchers.any(), Matchers.any())
		).thenReturn(
			false
		);

		_ddmFormContextToDDMForm.ddmFormFieldTypeServicesTracker =
			_ddmFormFieldTypeServicesTracker;

		Object result = _ddmFormContextToDDMForm.getValueFromValueAccessor(
			"checkbox", "false", LocaleUtil.US);

		Assert.assertFalse((boolean)result);
	}

	@Test
	public void testGetValueWithoutValueAccessor() throws IOException {
		Mockito.when(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				Matchers.anyString())
		).thenReturn(
			null
		);

		_ddmFormContextToDDMForm.ddmFormFieldTypeServicesTracker =
			_ddmFormFieldTypeServicesTracker;

		Object result = _ddmFormContextToDDMForm.getValueFromValueAccessor(
			"checkbox", "false", LocaleUtil.US);

		Assert.assertEquals("false", result);
	}

	private void _setUpDDMFormContextToDDMFormValues() throws Exception {
		_ddmFormContextToDDMForm = new DDMFormContextToDDMForm();

		_ddmFormContextToDDMForm.jsonFactory = new JSONFactoryImpl();
	}

	private DDMFormContextToDDMForm _ddmFormContextToDDMForm;

	@Mock
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Mock
	private DDMFormFieldValueAccessor<Object> _ddmFormFieldValueAccessor;

}