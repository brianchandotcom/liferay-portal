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

package com.liferay.dynamic.data.mapping.validator.internal;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustNotDuplicateFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetAvailableLocales;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetDefaultLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetDefaultLocaleAsAvailableLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetFieldsForForm;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetOptionsForField;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidAvailableLocalesForProperty;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidCharactersForFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidCharactersForFieldType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidDefaultLocaleForProperty;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidFormRuleExpression;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidIndexType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidValidationExpression;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidVisibilityExpression;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValidatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpBeanPropertiesUtil();
		_setUpDDMFormFieldTypeServicesTracker();
		_setUpDDMFormValidator();

		ReflectionTestUtil.setFieldValue(
			_ddmFormValidatorImpl, "_beanProperties", new BeanPropertiesImpl());
	}

	@Test(expected = MustSetValidCharactersForFieldType.class)
	public void testCaretInFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "html-text_@");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidCharactersForFieldName.class)
	public void testDashInFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("text-dash", DDMFormFieldType.TEXT));

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetDefaultLocaleAsAvailableLocale.class)
	public void testDefaultLocaleMissingAsAvailableLocale() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.BRAZIL));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidCharactersForFieldName.class)
	public void testDollarInFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("$text", DDMFormFieldType.TEXT));

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustNotDuplicateFieldName.class)
	public void testDuplicateCaseInsensitiveFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustNotDuplicateFieldName.class)
	public void testDuplicateFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidFormRuleExpression.class)
	public void testFormRuleEmptyCondition() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Name");

		ddmForm.addDDMFormRule(new DDMFormRule(Arrays.asList("true"), ""));

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidCharactersForFieldType.class)
	public void testInvalidCharacterFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "html-text_*");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidCharactersForFieldName.class)
	public void testInvalidFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"*", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidValidationExpression.class)
	public void testInvalidFieldValidationExpression() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setValue("*/+");
				}
			});

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test
	public void testInvalidFieldValidationExpressionMessage() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setValue("*/+");
				}
			});

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		try {
			_ddmFormValidatorImpl.validate(ddmForm);
		}
		catch (MustSetValidValidationExpression msvve) {
			Assert.assertTrue(StringUtil.equals("*/+", msvve.getExpression()));
		}
	}

	@Test(expected = MustSetValidVisibilityExpression.class)
	public void testInvalidFieldVisibilityExpression() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		ddmFormField.setVisibilityExpression("1 -< 2");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidFormRuleExpression.class)
	public void testInvalidFormRuleAction() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Name");

		ddmForm.addDDMFormRule(new DDMFormRule(Arrays.asList("*/?"), "true"));

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidFormRuleExpression.class)
	public void testInvalidFormRuleCondition() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Name");

		ddmForm.addDDMFormRule(new DDMFormRule(Arrays.asList("true"), "*/?"));

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidIndexType.class)
	public void testInvalidIndexType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		ddmFormField.setIndexType("Invalid");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidType.class)
	public void testInvalidType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "string");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetFieldsForForm.class)
	public void testNoFieldsSetForForm() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetOptionsForField.class)
	public void testNoOptionsSetForFieldOptions() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmFormField.setProperty("dataSourceType", "manual");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetOptionsForField.class)
	public void testNoOptionsSetForGrid() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Grid", DDMFormFieldType.GRID);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetOptionsForField.class)
	public void testNoOptionsSetForMultipleCheckbox() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"MultipleCheckbox", DDMFormFieldType.CHECKBOX_MULTIPLE);

		ddmFormField.setProperty("dataSourceType", "manual");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetAvailableLocales.class)
	public void testNullAvailableLocales() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(null);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetDefaultLocale.class)
	public void testNullDefaultLocale() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(null);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test
	public void testOptionsSetForMultipleCheckbox() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"MultipleCheckbox", DDMFormFieldType.CHECKBOX_MULTIPLE);

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("1", LocaleUtil.US, "Option 1");
		ddmFormFieldOptions.addOptionLabel("2", LocaleUtil.US, "Option 2");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidCharactersForFieldName.class)
	public void testSpaceInFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Text with Space", DDMFormFieldType.TEXT));

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test
	public void testSpecialCharactersInFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(new DDMFormField("和ó", DDMFormFieldType.TEXT));

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test
	public void testSpecialCharactersInFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "html-çê的Ü");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test
	public void testValidFieldValidationExpression() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setValue("false");
				}
			});

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test
	public void testValidFieldVisibilityExpression() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		ddmFormField.setVisibilityExpression("1 < 2");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidAvailableLocalesForProperty.class)
	public void testWrongAvailableLocalesSetForFieldOptions() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidAvailableLocalesForProperty.class)
	public void testWrongAvailableLocalesSetForLabel() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidDefaultLocaleForProperty.class)
	public void testWrongDefaultLocaleSetForFieldOptions() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.US, "Value Label");

		ddmFormFieldOptions.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	@Test(expected = MustSetValidDefaultLocaleForProperty.class)
	public void testWrongDefaultLocaleSetForLabel() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, "Label");

		label.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidatorImpl.validate(ddmForm);
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		return DDMFormTestUtil.createAvailableLocales(locales);
	}

	private void _setUpBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

	private void _setUpDDMFormFieldTypeServicesTracker() {
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			Mockito.mock(DDMFormFieldTypeServicesTracker.class);

		Mockito.when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeNames()
		).thenReturn(
			SetUtil.fromArray("date", "html-çê的Ü", "html-text_*", "html-text_@")
		);

		_ddmFormValidatorImpl.setDDMFormFieldTypeServicesTracker(
			ddmFormFieldTypeServicesTracker);
	}

	private void _setUpDDMFormValidator() {
		_ddmFormValidatorImpl.setDDMExpressionFactory(
			new DDMExpressionFactoryImpl());
	}

	private final DDMFormValidatorImpl _ddmFormValidatorImpl =
		new DDMFormValidatorImpl();

}