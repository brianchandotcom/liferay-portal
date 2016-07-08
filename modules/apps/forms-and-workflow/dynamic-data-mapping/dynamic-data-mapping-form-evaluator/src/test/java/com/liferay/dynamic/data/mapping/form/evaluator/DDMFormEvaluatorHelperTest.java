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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.DDMFormEvaluatorHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest({LanguageUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class DDMFormEvaluatorHelperTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setDDMExpressionFactory();
		setUpLanguageUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testInvalidEmailFieldWithCustomMessage() throws Exception {
		when(_ddmExpression.evaluate()).thenReturn(false);

		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = new DDMFormField("emailField", "text");

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("isEmailAddress(emailField)");
		ddmFormFieldValidation.setErrorMessage(
			"This is an invalid email address.");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("emailField");
		ddmFormFieldValue.setValue(new UnlocalizedValue("invalidEmail"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			createDDMFormEvaluatorHelper(ddmForm, ddmFormValues, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Assert.assertEquals(
			1,
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResults().size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResults().get(0);

		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());

		Assert.assertEquals(
			"This is an invalid email address.",
			ddmFormFieldEvaluationResult.getErrorMessage());
	}

	@Test
	public void testInvalidEmailFieldWithoutCustomMessage() throws Exception {
		when(_ddmExpression.evaluate()).thenReturn(false);

		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = new DDMFormField("emailField", "text");

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("isEmailAddress(emailField)");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("emailField");
		ddmFormFieldValue.setValue(new UnlocalizedValue("invalidEmail"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			createDDMFormEvaluatorHelper(ddmForm, ddmFormValues, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Assert.assertEquals(
			1,
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResults().size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResults().get(0);

		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());

		Assert.assertEquals(
			"This field is invalid.",
			ddmFormFieldEvaluationResult.getErrorMessage());
	}

	protected DDMFormEvaluatorHelper createDDMFormEvaluatorHelper(
			DDMForm ddmForm, DDMFormValues ddmFormValues, Locale locale)
		throws Exception {

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(ddmForm, ddmFormValues, locale);

		field(
			DDMFormEvaluatorHelper.class, "_ddmExpressionFactory"
		).set(
			ddmFormEvaluatorHelper, _ddmExpressionFactory
		);

		field(
			DDMFormEvaluatorHelper.class, "_jsonFactory"
		).set(
			ddmFormEvaluatorHelper, _jsonFactory
		);

		return ddmFormEvaluatorHelper;
	}

	protected void setDDMExpressionFactory() throws Exception {
		when(
			_ddmExpressionFactory.createBooleanDDMExpression(
				Matchers.anyString())
		).thenReturn(
			_ddmExpression
		);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		when(
			_language.get(_resourceBundle, "this-field-is-invalid")
		).thenReturn(
			"This field is invalid."
		);
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			_resourceBundle
		);
	}

	@Mock
	private DDMExpression<Boolean> _ddmExpression;

	@Mock
	private DDMExpressionFactory _ddmExpressionFactory;

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

	@Mock
	private Language _language;

	@Mock
	private ResourceBundle _resourceBundle;

}