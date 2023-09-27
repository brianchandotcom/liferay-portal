/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.context;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
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

import org.mockito.Mockito;

/**
 * @author Jeyvison Nascimento
 */
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
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldValueAccessor(
				Mockito.anyString())
		).thenReturn(
			_ddmFormFieldValueAccessor
		);

		Mockito.when(
			_ddmFormFieldValueAccessor.getValue(Mockito.any(), Mockito.any())
		).thenReturn(
			false
		);

		_ddmFormContextToDDMForm.ddmFormFieldTypeServicesRegistry =
			_ddmFormFieldTypeServicesRegistry;

		Object result = _ddmFormContextToDDMForm.getValueFromValueAccessor(
			"checkbox", "false", LocaleUtil.US);

		Assert.assertFalse((boolean)result);
	}

	@Test
	public void testGetValueWithoutValueAccessor() throws IOException {
		Mockito.when(
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldValueAccessor(
				Mockito.anyString())
		).thenReturn(
			null
		);

		_ddmFormContextToDDMForm.ddmFormFieldTypeServicesRegistry =
			_ddmFormFieldTypeServicesRegistry;

		Object result = _ddmFormContextToDDMForm.getValueFromValueAccessor(
			"checkbox", "false", LocaleUtil.US);

		Assert.assertEquals("false", result);
	}

	private void _setUpDDMFormContextToDDMFormValues() throws Exception {
		_ddmFormContextToDDMForm = new DDMFormContextToDDMForm();

		_ddmFormContextToDDMForm.jsonFactory = new JSONFactoryImpl();
	}

	private DDMFormContextToDDMForm _ddmFormContextToDDMForm;
	private final DDMFormFieldTypeServicesRegistry
		_ddmFormFieldTypeServicesRegistry = Mockito.mock(
			DDMFormFieldTypeServicesRegistry.class);
	private final DDMFormFieldValueAccessor<Object> _ddmFormFieldValueAccessor =
		Mockito.mock(DDMFormFieldValueAccessor.class);

}