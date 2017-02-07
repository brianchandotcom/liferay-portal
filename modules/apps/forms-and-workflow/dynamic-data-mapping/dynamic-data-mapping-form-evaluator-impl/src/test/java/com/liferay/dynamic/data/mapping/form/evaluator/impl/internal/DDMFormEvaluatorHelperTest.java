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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMFormEvaluatorHelperTest {

	@Before
	public void setUp() throws Exception {
		setPortalUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testBelongsToCondition() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		ddmForm.addDDMFormField(ddmFormField0);

		String condition = "belongsTo(\"Role1\")";

		String action = "setEnabled(\"field0\", false)";

		DDMFormRule ddmFormRule = new DDMFormRule(
			condition, Arrays.asList(action));

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("request", _request);

		when(
			_userLocalService.hasRoleUser(
				_company.getCompanyId(), "Role1", _user.getUserId(), true)
		).thenReturn(
			true
		);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmFormEvaluatorContext,
				null, _jsonFactory, _userLocalService);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 1,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult field0DDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertTrue(field0DDMFormFieldEvaluationResult.isReadOnly());
	}

	@Test
	public void testJumpPageAction() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.NUMBER);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1")));

		String condition = "getValue(\"field0\") >= 1";

		List<String> actions = ListUtil.fromArray(
			new String[] {"jumpPage(1, 3)"});

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Set<Integer> disabledPagesIndexes =
			ddmFormEvaluationResult.getDisabledPagesIndexes();

		Assert.assertTrue(disabledPagesIndexes.contains(2));
	}

	@Test
	public void testNotBelongsToCondition() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		ddmForm.addDDMFormField(ddmFormField0);

		String condition = "not(belongsTo(\"Role1\"))";

		String action = "setVisible(\"field0\", false)";

		DDMFormRule ddmFormRule = new DDMFormRule(
			condition, Arrays.asList(action));

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("request", _request);

		when(
			_userLocalService.hasRoleUser(
				_company.getCompanyId(), "Role1", _user.getUserId(), true)
		).thenReturn(
			false
		);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmFormEvaluatorContext,
				null, _jsonFactory, _userLocalService);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 1,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult field0DDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertFalse(field0DDMFormFieldEvaluationResult.isVisible());
	}

	@Test
	public void testNotCalledJumpPageAction() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.NUMBER);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1")));

		String condition = "getValue(\"field0\") > 1";

		List<String> actions = ListUtil.fromArray(
			new String[] {"jumpPage(1, 3)"});

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Set<Integer> disabledPagesIndexes =
			ddmFormEvaluationResult.getDisabledPagesIndexes();

		Assert.assertTrue(disabledPagesIndexes.isEmpty());
	}

	@Test
	public void testRequiredValidationWithCheckboxField() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "checkbox", FieldConstants.BOOLEAN);

		ddmFormField.setRequired(true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("false")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 1,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertEquals(
			"This field is required.",
			ddmFormFieldEvaluationResult.getErrorMessage());
		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testRequiredValidationWithHiddenField() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.INTEGER));

		DDMFormField field1DDMFormField = createDDMFormField(
			"field1", "text", FieldConstants.STRING);

		field1DDMFormField.setRequired(true);

		field1DDMFormField.setVisibilityExpression("field0 > 5");

		ddmForm.addDDMFormField(field1DDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("4")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 2,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult field1DDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId");

		Assert.assertEquals(
			StringPool.BLANK,
			field1DDMFormFieldEvaluationResult.getErrorMessage());
		Assert.assertTrue(field1DDMFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testRequiredValidationWithinRuleAction() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "text", FieldConstants.NUMBER);

		DDMFormField ddmFormField1 = createDDMFormField(
			"field1", "text", FieldConstants.STRING);

		ddmForm.addDDMFormField(ddmFormField0);
		ddmForm.addDDMFormField(ddmFormField1);

		String condition = "getValue(\"field0\") > 10";

		List<String> actions = ListUtil.fromArray(
			new String[] {"setRequired(\"field1\", true)"});

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("11")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 2,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId");

		Assert.assertEquals(
			"This field is required.",
			ddmFormFieldEvaluationResult.getErrorMessage());
		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testRequiredValidationWithTextField() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		ddmFormField.setRequired(true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("\n")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 1,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertEquals(
			"This field is required.",
			ddmFormFieldEvaluationResult.getErrorMessage());
		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testShowHideAndEnableDisableRules() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field1", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field2", "text", FieldConstants.DOUBLE));

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("30")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("15")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("10")));

		String condition = "getValue(\"field0\") >= 30";

		List<String> actions = ListUtil.fromArray(
			new String[] {
				"setVisible(\"field1\", false)", "setEnabled(\"field2\", false)"
			});

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 3,
			ddmFormFieldEvaluationResultMap.size());

		// Field 0

		DDMFormFieldEvaluationResult
			expectedField0DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field0", "field0_instanceId", StringPool.BLANK, true, 30.,
					true, false, false);

		assertEquals(
			expectedField0DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId"));

		// Field 1

		DDMFormFieldEvaluationResult
			expectedField1DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field1", "field1_instanceId", StringPool.BLANK, true, 15.,
					false, false, false);

		assertEquals(
			expectedField1DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId"));

		// Field 2

		DDMFormFieldEvaluationResult
			expectedField2DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field2", "field2_instanceId", StringPool.BLANK, true, 10.,
					true, true, false);

		assertEquals(
			expectedField2DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field2", "field2_instanceId"));
	}

	@Test
	public void testUpdateAndCalculateRule() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field1", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field2", "text", FieldConstants.DOUBLE));

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("5")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("2")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("0")));

		String condition =
			"getValue(\"field0\") > 0 && getValue(\"field1\") > 0";

		String action =
			"setValue(\"field2\", getValue(\"field0\") * getValue(\"field1\"))";

		DDMFormRule ddmFormRule = new DDMFormRule(
			condition, Arrays.asList(action));

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 3,
			ddmFormFieldEvaluationResultMap.size());

		// Field 0

		DDMFormFieldEvaluationResult
			expectedField0DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field0", "field0_instanceId", 5.0);

		assertEquals(
			expectedField0DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId"));

		// Field 1

		DDMFormFieldEvaluationResult
			expectedField1DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field1", "field1_instanceId", 2.0);

		assertEquals(
			expectedField1DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId"));

		// Field 2

		DDMFormFieldEvaluationResult
			expectedField2DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field2", "field2_instanceId", 10.0);

		assertEquals(
			expectedField2DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field2", "field2_instanceId"));
	}

	@Test
	public void testValidationExpression() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.INTEGER);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setErrorMessage("This field should be zero.");
		ddmFormFieldValidation.setExpression("field0 == 0");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 1,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertEquals(
			"This field should be zero.",
			ddmFormFieldEvaluationResult.getErrorMessage());
		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testValidationRule() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.DOUBLE));

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("5")));

		String condition = "getValue(\"field0\") <= 10";

		String action =
			"setInvalid(\"field0\", \"The value should be greater than 10.\")";

		DDMFormRule ddmFormRule = new DDMFormRule(
			condition, Arrays.asList(action));

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 1,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult actualDDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertEquals(
			"The value should be greater than 10.",
			actualDDMFormFieldEvaluationResult.getErrorMessage());
		Assert.assertEquals(
			false, actualDDMFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testVisibilityExpression() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.INTEGER));

		DDMFormField field1DDMFormField = createDDMFormField(
			"field1", "text", FieldConstants.STRING);

		field1DDMFormField.setVisibilityExpression("field0 > 5");

		ddmForm.addDDMFormField(field1DDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("6")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory,
				new DDMFormEvaluatorContext(
					ddmForm, ddmFormValues, LocaleUtil.US),
				null, _jsonFactory, null);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(
			ddmFormFieldEvaluationResultMap.toString(), 2,
			ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult field1DDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId");

		Assert.assertTrue(field1DDMFormFieldEvaluationResult.isVisible());
	}

	protected void assertEquals(
		DDMFormFieldEvaluationResult expectedDDMFormFieldEvaluationResult,
		DDMFormFieldEvaluationResult actualDDMFormFieldEvaluationResult) {

		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult,
			actualDDMFormFieldEvaluationResult);

		// Properties

		Object expectedValue = expectedDDMFormFieldEvaluationResult.getValue();
		Object actualValue = actualDDMFormFieldEvaluationResult.getValue();

		Assert.assertEquals(expectedValue, actualValue);

		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.getErrorMessage(),
			actualDDMFormFieldEvaluationResult.getErrorMessage());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isValid(),
			actualDDMFormFieldEvaluationResult.isValid());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isVisible(),
			actualDDMFormFieldEvaluationResult.isVisible());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isReadOnly(),
			actualDDMFormFieldEvaluationResult.isReadOnly());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isRequired(),
			actualDDMFormFieldEvaluationResult.isRequired());
	}

	protected DDMFormField createDDMFormField(
		String name, String type, String dataType) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);

		return ddmFormField;
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		String name, String instanceId, Object value) {

		return createDDMFormFieldEvaluationResult(
			name, instanceId, StringPool.BLANK, true, value, true, false,
			false);
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		String name, String instanceId, String errorMessage, boolean valid,
		Object value, boolean visible, boolean readOnly, boolean required) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(name, instanceId);

		ddmFormFieldEvaluationResult.setErrorMessage(errorMessage);
		ddmFormFieldEvaluationResult.setValid(valid);
		ddmFormFieldEvaluationResult.setValue(value);
		ddmFormFieldEvaluationResult.setVisible(visible);
		ddmFormFieldEvaluationResult.setReadOnly(readOnly);
		ddmFormFieldEvaluationResult.setRequired(required);

		return ddmFormFieldEvaluationResult;
	}

	protected void setPortalUtil() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		when(portal.getUser(_request)).thenReturn(_user);
		when(portal.getCompany(_request)).thenReturn(_company);

		portalUtil.setPortal(portal);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		_language = mock(Language.class);

		when(
			_language.get(
				Matchers.eq(Locale.US), Matchers.eq("this-field-is-required"))
		).thenReturn(
			"This field is required."
		);

		languageUtil.setLanguage(_language);
	}

	@Mock
	private Company _company;

	private final DDMExpressionFactory _ddmExpressionFactory =
		new DDMExpressionFactoryImpl();
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private Language _language;

	@Mock
	private HttpServletRequest _request;

	@Mock
	private User _user;

	@Mock
	private UserLocalService _userLocalService;

}