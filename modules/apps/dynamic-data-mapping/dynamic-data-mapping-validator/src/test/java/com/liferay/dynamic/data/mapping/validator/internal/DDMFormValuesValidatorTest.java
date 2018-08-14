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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorErrorStatus;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorValidateRequest;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@RunWith(PowerMockRunner.class)
public class DDMFormValuesValidatorTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormValuesValidator();
	}

	@Test
	public void testFormThrowsMultipleValidationExceptions() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField1 = new DDMFormField("fieldset", "fieldset");

		DDMFormTestUtil.addNestedTextDDMFormFields(ddmFormField1, "name");

		DDMFormField ddmFormField2 =
			DDMFormTestUtil.createLocalizableTextDDMFormField("country");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField1, ddmFormField2);

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

		LocalizedValue localizedValue1 = new LocalizedValue(LocaleUtil.BRAZIL);

		localizedValue1.addString(LocaleUtil.US, "Brazil");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"country", localizedValue1));

		LocalizedValue localizedValue2 = new LocalizedValue(LocaleUtil.BRAZIL);

		localizedValue2.addString(LocaleUtil.US, "Recife");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"city", localizedValue2));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_NOT_SET_VALUE_EXCEPTION));

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_DEFAULT_LOCALE_EXCEPTION));

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_FIELD_EXCEPTION));
		}
	}

	@Test
	public void testNumericValidationWithWrongValueForDoubleTypeExpression() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("Height", "numeric");

		ddmFormField.setDataType("double");

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("Height <= 3.5");
		ddmFormFieldValidation.setErrorMessage("maximum height allowed 3.5.");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Height", new UnlocalizedValue("4.3")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testNumericValidationWithWrongValueForIntegerTypeExpression() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("Age", "numeric");

		ddmFormField.setDataType("integer");

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("Age > 18");
		ddmFormFieldValidation.setErrorMessage("Age must be greater than 18.");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Age", new UnlocalizedValue("14")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithAutocompleteText() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Country", false, false, false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("Brazil", LocaleUtil.US, "Brazil");
		ddmFormFieldOptions.addOptionLabel("USA", LocaleUtil.US, "USA");
		ddmFormFieldOptions.addOptionLabel("France", LocaleUtil.US, "France");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"Country", "Spain"));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		_ddmFormValuesValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidationWithInvalidFieldName() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("firstName");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue("lastName", null));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_FIELD_EXCEPTION));
		}
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_FIELD_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithLocalizableField() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", new UnlocalizedValue("Joe")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithMissingNestedRequiredField() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						REQUIRED_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithMissingNestedRequiredFieldValue() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						REQUIRED_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithMissingRequiredField() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		ddmFormField.setRequired(true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						REQUIRED_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithMissingRequiredFieldValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		ddmFormField.setRequired(true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue("name", null));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						REQUIRED_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithNonrequiredFieldAndEmptyDefaultLocaleValue()
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		_ddmFormValuesValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidationWithNonrequiredFieldValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		_ddmFormValuesValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidationWithNonrequiredSelect() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDataType("string");
		ddmFormField.setRequired(false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");
		ddmFormFieldOptions.addOptionLabel("B", LocaleUtil.US, "Option B");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		String instanceId = StringUtil.randomString();

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				instanceId, "option", new UnlocalizedValue("[\"A\"]")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		_ddmFormValuesValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidationWithNonrequiredSelectAndEmptyDefaultLocaleValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDataType("string");
		ddmFormField.setRequired(false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");
		ddmFormFieldOptions.addOptionLabel("B", LocaleUtil.US, "Option B");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		String instanceId = StringUtil.randomString();

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				instanceId, "option", new UnlocalizedValue("[\"\"]")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		_ddmFormValuesValidatorImpl.validate(builder.build());
	}

	@Test(expected = NullPointerException.class)
	public void testValidationWithoutDDMFormReference() throws Exception {
		DDMFormValues ddmFormValues = new DDMFormValues(null);

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		_ddmFormValuesValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidationWithRequiredFieldAndEmptyDefaultLocaleValue() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						REQUIRED_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithRequiredFieldAndEmptyTranslatedValue() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						REQUIRED_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithRequiredFieldAndNullValue() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_AVAILABLE_LOCALES_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithRequiredFieldAndWithNoValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						REQUIRED_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithRequiredSelect() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDataType("string");
		ddmFormField.setRequired(true);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");
		ddmFormFieldOptions.addOptionLabel("B", LocaleUtil.US, "Option B");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		String instanceId = StringUtil.randomString();

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				instanceId, "option", new UnlocalizedValue("[\"A\"]")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		_ddmFormValuesValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidationWithSeparatorField() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createSeparatorDDMFormField(
			"separator", false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"separator", new UnlocalizedValue("separator value")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_NOT_SET_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithUnlocalizableField() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithValueSetForTransientField() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_NOT_SET_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithWrongAvailableLocales() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_AVAILABLE_LOCALES_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithWrongDefaultLocale() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_DEFAULT_LOCALE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithWrongValueSetDueValidationExpression() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("Age", "text");

		ddmFormField.setDataType("integer");

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("Age > 18");
		ddmFormFieldValidation.setErrorMessage("Age must be greater than 18.");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Age", new UnlocalizedValue("5")));

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUE_EXCEPTION));
		}
	}

	@Test
	public void testValidationWithWrongValuesForNonrepeatableField() {
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

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues
			);

		try {
			_ddmFormValuesValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			List<DDMFormValuesValidatorErrorStatus>
				ddmFormValuesValidatorErrorStatus =
					getDDMFormValuesValidatorErrorStatus(ddmfvve);

			Assert.assertTrue(
				ddmFormValuesValidatorErrorStatus.contains(
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUES_SIZE_EXCEPTION));
		}
	}

	protected List<DDMFormValuesValidatorErrorStatus>
		getDDMFormValuesValidatorErrorStatus(
			DDMFormValuesValidationException ddmfvve) {

		List<DDMFormValuesValidatorError> ddmFormValuesValidatorErrors =
			ddmfvve.getDDMFormValuesValidatorErrors();

		Stream<DDMFormValuesValidatorError> ddmFormValuesValidatorErrorStream =
			ddmFormValuesValidatorErrors.stream();

		return ddmFormValuesValidatorErrorStream.map(
			ddmFormValidatorError -> ddmFormValidatorError.getErrorStatus()
		).collect(
			Collectors.toList()
		);
	}

	protected void setUpDDMFormValuesValidator() throws Exception {
		field(
			DDMFormValuesValidatorImpl.class, "ddmExpressionFactory"
		).set(
			_ddmFormValuesValidatorImpl, new DDMExpressionFactoryImpl()
		);

		field(
			DDMFormValuesValidatorImpl.class, "ddmFormFieldTypeServicesTracker"
		).set(
			_ddmFormValuesValidatorImpl,
			mock(DDMFormFieldTypeServicesTracker.class)
		);

		field(
			DDMFormValuesValidatorImpl.class, "jsonFactory"
		).set(
			_ddmFormValuesValidatorImpl, new JSONFactoryImpl()
		);
	}

	private final DDMFormValuesValidatorImpl _ddmFormValuesValidatorImpl =
		new DDMFormValuesValidatorImpl();

}