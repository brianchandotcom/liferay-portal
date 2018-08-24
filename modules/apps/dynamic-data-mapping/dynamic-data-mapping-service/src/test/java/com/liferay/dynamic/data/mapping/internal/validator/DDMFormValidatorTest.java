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

package com.liferay.dynamic.data.mapping.internal.validator;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidatorValidateRequest;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
public class DDMFormValidatorTest extends PowerMockito {

	@Before
	public void setUp() throws IllegalAccessException {
		setUpBeanPropertiesUtil();
		setUpDDMFormValidatorImpl();
	}

	@Test
	public void testCaretInFieldType() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "html-text_@");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_TYPE_EXCEPTION));
		}
	}

	@Test
	public void testDashInFieldName() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("text-dash", DDMFormFieldType.TEXT));

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testDefaultLocaleMissingAsAvailableLocale() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.BRAZIL));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_DEFAULT_LOCALE_AS_AVAILABLE_LOCALE_EXCEPTION));
		}
	}

	@Test
	public void testDollarInFieldName() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("$text", DDMFormFieldType.TEXT));

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testDuplicateCaseInsensitiveFieldName() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testDuplicateFieldName() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testFormRuleEmptyCondition() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Name");

		ddmForm.addDDMFormRule(new DDMFormRule("", Arrays.asList("true")));

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		_ddmFormValidatorImpl.validate(builder.build());
	}

	@Test
	public void testFormThrowsMultipleValidationExceptions() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name1", null);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, "Label");

		label.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION));

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_FIELD_TYPE_EXCEPTION));

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_DEFAULT_LOCALE_FOR_PROPERTY_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFieldIndexType() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		ddmFormField.setIndexType("Invalid");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_INDEX_TYPE_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFieldName() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"*", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFieldType() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "html-text_*");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_TYPE_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFieldValidationExpression() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("*/+");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_VALIDATION_EXPRESSION_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFieldValidationExpressionMessage() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		String expression = "*/+";

		ddmFormFieldValidation.setExpression(expression);

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_VALIDATION_EXPRESSION_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFieldVisibilityExpression() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		ddmFormField.setVisibilityExpression("1 -< 2");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_VISIBILITY_EXPRESSION_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFormRuleAction() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Name");

		ddmForm.addDDMFormRule(new DDMFormRule("true", Arrays.asList("*/?")));

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_FORM_RULE_EXPRESSION_EXCEPTION));
		}
	}

	@Test
	public void testInvalidFormRuleCondition() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Name");

		ddmForm.addDDMFormRule(new DDMFormRule("*/?", Arrays.asList("true")));

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_FORM_RULE_EXPRESSION_EXCEPTION));
		}
	}

	@Test
	public void testMissingFieldType() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", null);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_FIELD_TYPE_EXCEPTION));
		}
	}

	@Test
	public void testNoFieldsSetForForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_FIELDS_FOR_FORM_EXCEPTION));
		}
	}

	@Test
	public void testNoOptionsSetForFieldOptions() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmFormField.setProperty("dataSourceType", "manual");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_OPTIONS_FOR_FIELD_EXCEPTION));
		}
	}

	@Test
	public void testNoOptionsSetForMultipleCheckbox() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"MultipleCheckbox", DDMFormFieldType.CHECKBOX_MULTIPLE);

		ddmFormField.setProperty("dataSourceType", "manual");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_OPTIONS_FOR_FIELD_EXCEPTION));
		}
	}

	@Test
	public void testNullAvailableLocales() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(null);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_AVAILABLE_LOCALES_EXCEPTION));
		}
	}

	@Test
	public void testNullDefaultLocale() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(null);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_DEFAULT_LOCALE_EXCEPTION));
		}
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

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		_ddmFormValidatorImpl.validate(builder.build());
	}

	@Test
	public void testSpaceInFieldName() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Text with Space", DDMFormFieldType.TEXT));

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testSpecialCharactersInFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(new DDMFormField("和ó", DDMFormFieldType.TEXT));

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		_ddmFormValidatorImpl.validate(builder.build());
	}

	@Test
	public void testSpecialCharactersInFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "html-çê的Ü");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		_ddmFormValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "html-text_1");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		_ddmFormValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidFieldValidationExpression() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("false");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		_ddmFormValidatorImpl.validate(builder.build());
	}

	@Test
	public void testValidFieldVisibilityExpression() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", DDMFormFieldType.TEXT);

		ddmFormField.setVisibilityExpression("1 < 2");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		_ddmFormValidatorImpl.validate(builder.build());
	}

	@Test
	public void testWrongAvailableLocalesSetForFieldOptions() {
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

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_AVAILABLE_LOCALES_FOR_PROPERTY_EXCEPTION));
		}
	}

	@Test
	public void testWrongAvailableLocalesSetForLabel() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_AVAILABLE_LOCALES_FOR_PROPERTY_EXCEPTION));
		}
	}

	@Test
	public void testWrongDefaultLocaleSetForFieldOptions() {
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

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_DEFAULT_LOCALE_FOR_PROPERTY_EXCEPTION));
		}
	}

	@Test
	public void testWrongDefaultLocaleSetForLabel() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, "Label");

		label.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorValidateRequest.Builder builder =
			DDMFormValidatorValidateRequest.Builder.newBuilder(
				ddmForm
			);

		try {
			_ddmFormValidatorImpl.validate(builder.build());
		}
		catch (DDMFormValidationException ddmfve) {
			List<DDMFormValidatorError.Status> errorStatusList =
				getDDMFormValidatorErrorStatus(ddmfve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormValidatorError.Status.
						MUST_SET_VALID_DEFAULT_LOCALE_FOR_PROPERTY_EXCEPTION));
		}
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		return DDMFormTestUtil.createAvailableLocales(locales);
	}

	protected List<DDMFormValidatorError.Status> getDDMFormValidatorErrorStatus(
		DDMFormValidationException ddmfve) {

		List<DDMFormValidatorError> ddmFormValidatorErrors =
			ddmfve.getDDMFormValidatorErrors();

		Stream<DDMFormValidatorError> ddmFormValidatorErrorStream =
			ddmFormValidatorErrors.stream();

		return ddmFormValidatorErrorStream.map(
			ddmFormValidatorError -> ddmFormValidatorError.getErrorStatus()
		).collect(
			Collectors.toList()
		);
	}

	protected void setUpBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

	protected void setUpDDMFormValidatorImpl() throws IllegalAccessException {
		field(
			DDMFormValidatorImpl.class, "ddmExpressionFactory"
		).set(
			_ddmFormValidatorImpl, new DDMExpressionFactoryImpl()
		);
	}

	private final DDMFormValidatorImpl _ddmFormValidatorImpl =
		new DDMFormValidatorImpl();

}