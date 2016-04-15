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

import static org.mockito.Mockito.mock;

import static org.powermock.api.mockito.PowerMockito.when;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustNotSetValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidAvailableLocales;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidDefaultLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidValuesSize;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.RequiredValue;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@RunWith(PowerMockRunner.class)
public class DDMFormValuesValidatorTest {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormValuesValidator();
	}

	@Test
	public void testValidationWithInvalidFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("firstName");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue("lastName", null));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test
	public void testValidationWithInvalidNestedFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addNestedTextDDMFormFields(ddmFormField, "contact");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringUtil.randomString());

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue);

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"invalid", localizedValue));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustSetValidValue.class)
	public void testValidationWithLocalizableField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", new UnlocalizedValue("Joe")));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingNestedRequiredField()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			DDMFormTestUtil.createTextDDMFormField(
				"contact", "", false, false, true));

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue("name", null);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingNestedRequiredFieldValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			DDMFormTestUtil.createTextDDMFormField(
				"contact", "", false, false, true));

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue("name", null);

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue("contact", null));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingRequiredField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		ddmFormField.setRequired(true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingRequiredFieldValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		ddmFormField.setRequired(true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue("name", null));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test
	public void testValidationWithNonRequiredFieldAndEmptyDefaultLocaleValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringPool.BLANK);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test
	public void testValidationWithNonRequiredFieldValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = NullPointerException.class)
	public void testValidationWithoutDDMFormReference() throws Exception {
		DDMFormValues ddmFormValues = new DDMFormValues(null);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithRequiredFieldAndEmptyDefaultLocaleValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		String instanceId = StringUtil.randomString();

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringPool.BLANK);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				instanceId, "name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDDMFormFieldEvaluationResult("name", instanceId, true);

		DDMFormEvaluator ddmFormEvaluator = mockDDMFormEvaluatorWithResult(
			createDDMFormEvaluationResult(ddmFormFieldEvaluationResult));

		setDDMFormValuesValidatorEvaluator(ddmFormEvaluator);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithRequiredFieldAndEmptyTranslatedValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(
				LocaleUtil.US, LocaleUtil.BRAZIL),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm,
			DDMFormTestUtil.createAvailableLocales(
				LocaleUtil.US, LocaleUtil.BRAZIL),
			LocaleUtil.US);

		String instanceId = StringUtil.randomString();

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringUtil.randomString());
		localizedValue.addString(LocaleUtil.BRAZIL, StringPool.BLANK);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				instanceId, "name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDDMFormFieldEvaluationResult("name", instanceId, true);

		DDMFormEvaluator ddmFormEvaluator = mockDDMFormEvaluatorWithResult(
			createDDMFormEvaluationResult(ddmFormFieldEvaluationResult));

		setDDMFormValuesValidatorEvaluator(ddmFormEvaluator);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustSetValidAvailableLocales.class)
	public void testValidationWithRequiredFieldAndNullValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		String instanceId = StringUtil.randomString();

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				instanceId, "name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDDMFormFieldEvaluationResult("name", instanceId, true);

		DDMFormEvaluator ddmFormEvaluator = mockDDMFormEvaluatorWithResult(
			createDDMFormEvaluationResult(ddmFormFieldEvaluationResult));

		setDDMFormValuesValidatorEvaluator(ddmFormEvaluator);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithRequiredFieldAndWithNoValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test
	public void testValidationWithRequiredHiddenFieldAndEmptyValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		String instanceId = StringUtil.randomString();

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringPool.BLANK);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				instanceId, "name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDDMFormFieldEvaluationResult("name", instanceId, false);

		DDMFormEvaluator ddmFormEvaluator = mockDDMFormEvaluatorWithResult(
			createDDMFormEvaluationResult(ddmFormFieldEvaluationResult));

		setDDMFormValuesValidatorEvaluator(ddmFormEvaluator);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustNotSetValue.class)
	public void testValidationWithSeparatorField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createSeparatorDDMFormField(
			"separator", false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"separator", new UnlocalizedValue("separator value")));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustSetValidValue.class)
	public void testValidationWithUnlocalizableField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "", false, false, false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustNotSetValue.class)
	public void testValidationWithValueSetForTransientField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("fieldset", "fieldset");

		DDMFormTestUtil.addNestedTextDDMFormFields(ddmFormField, "name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"fieldset", new UnlocalizedValue("Value"));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", new UnlocalizedValue("Joe")));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustSetValidAvailableLocales.class)
	public void testValidationWithWrongAvailableLocales() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.BRAZIL, "Joao");
		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustSetValidDefaultLocale.class)
	public void testValidationWithWrongDefaultLocale() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.BRAZIL);

		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustSetValidValue.class)
	public void testValidationWithWrongValueSetForSelect() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDataType("string");

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");
		ddmFormFieldOptions.addOptionLabel("B", LocaleUtil.US, "Option B");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"option", new UnlocalizedValue("[\"Invalid\"]")));

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	@Test(expected = MustSetValidValuesSize.class)
	public void testValidationWithWrongValuesForNonRepeatableField()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			DDMFormTestUtil.createTextDDMFormField(
				"contact", "", false, false, true));

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", new UnlocalizedValue("name value"));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"contact", new UnlocalizedValue("contact value 1")));
		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"contact", new UnlocalizedValue("contact value 2")));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidatorImpl.validate(ddmFormValues);
	}

	protected DDMFormEvaluationResult createDDMFormEvaluationResult(
		DDMFormFieldEvaluationResult... ddmFormFieldEvaluationResults) {

		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			Arrays.asList(ddmFormFieldEvaluationResults));

		return ddmFormEvaluationResult;
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		String name, String instanceId, boolean visible) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(name, instanceId);

		ddmFormFieldEvaluationResult.setVisible(visible);

		return ddmFormFieldEvaluationResult;
	}

	protected DDMFormEvaluator mockDDMFormEvaluatorWithEmptyResult()
		throws Exception {

		DDMFormEvaluator ddmFormEvaluator = mock(DDMFormEvaluator.class);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			new ArrayList<DDMFormFieldEvaluationResult>());

		whenDDMFormEvaluatorEvaluateThenReturn(
			ddmFormEvaluator, ddmFormEvaluationResult);

		return ddmFormEvaluator;
	}

	protected DDMFormEvaluator mockDDMFormEvaluatorWithResult(
			DDMFormEvaluationResult ddmFormEvaluationResult)
		throws Exception {

		DDMFormEvaluator ddmFormEvaluator = mock(DDMFormEvaluator.class);

		whenDDMFormEvaluatorEvaluateThenReturn(
			ddmFormEvaluator, ddmFormEvaluationResult);

		return ddmFormEvaluator;
	}

	protected void setDDMFormValuesValidatorEvaluator(
		DDMFormEvaluator ddmFormEvaluator) {

		_ddmFormValuesValidatorImpl.setDDMFormEvaluator(ddmFormEvaluator);
	}

	protected void setUpDDMFormValuesValidator() throws Exception {
		_ddmFormValuesValidatorImpl.setJSONFactory(new JSONFactoryImpl());

		DDMFormEvaluator ddmFormEvaluator =
			mockDDMFormEvaluatorWithEmptyResult();

		setDDMFormValuesValidatorEvaluator(ddmFormEvaluator);
	}

	protected void whenDDMFormEvaluatorEvaluateThenReturn(
			DDMFormEvaluator ddmFormEvaluator,
			DDMFormEvaluationResult ddmFormEvaluationResult)
		throws Exception {

		Class<?> clazz = ddmFormEvaluator.getClass();

		Method evaluateMethod = clazz.getMethod(
			"evaluate", DDMForm.class, DDMFormValues.class, Locale.class);

		when(
			ddmFormEvaluator, evaluateMethod
		).withArguments(
			Mockito.any(DDMForm.class), Mockito.any(DDMFormValues.class),
			Mockito.any(Locale.class)
		).thenReturn(
			ddmFormEvaluationResult
		);
	}

	private final DDMFormValuesValidatorImpl _ddmFormValuesValidatorImpl =
		new DDMFormValuesValidatorImpl();

}