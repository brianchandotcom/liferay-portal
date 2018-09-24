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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.helper;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandlerAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserverAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.AllFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.BelongsToRoleFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.BetweenFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.CalculateFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.GetValueFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.JumpPageFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetEnabledFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetInvalidFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetRequiredFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetValueFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetVisibleFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SumFunction;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.checkbox.internal.CheckboxDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.numeric.internal.NumericDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
@PrepareForTest(ResourceBundleLoaderUtil.class)
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	"com.liferay.portal.kernel.util.ResourceBundleLoaderUtil"
)
public class DDMFormEvaluatorHelperTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleLoaderUtil();

		_ddmExpressionFactory = new DDMExpressionFactoryImpl();
	}

	@Test
	public void testAllCondition() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		DDMFormField ddmFormField1 = createDDMFormField(
			"field1", "number", FieldConstants.DOUBLE);

		ddmFormField1.setRepeatable(true);

		ddmForm.addDDMFormField(ddmFormField0);
		ddmForm.addDDMFormField(ddmFormField1);

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"all('#value# <= 10', getValue('field1'))",
				"setEnabled(\"field0\", false)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_0", "field1", new UnlocalizedValue("1")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_1", "field1", new UnlocalizedValue("5")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_2", "field1", new UnlocalizedValue("10")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertTrue((boolean)ddmFormFieldPropertyChanges.get("readOnly"));
	}

	@Test
	public void testBelongsToCondition() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		ddmForm.addDDMFormField(ddmFormField0);

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"belongsTo([\"Role1\"])", "setEnabled(\"field0\", false)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		Mockito.when(
			_roleLocalService.fetchRole(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			_role
		);

		Mockito.when(
			_role.getType()
		).thenReturn(
			RoleConstants.TYPE_REGULAR
		);

		Mockito.when(
			_userLocalService.hasRoleUser(
				Matchers.anyLong(), Matchers.eq("Role1"), Matchers.anyLong(),
				Matchers.eq(true))
		).thenReturn(
			true
		);

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertTrue((boolean)ddmFormFieldPropertyChanges.get("readOnly"));
	}

	@Test
	public void testJumpPageAction() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.NUMBER);

		ddmForm.addDDMFormField(ddmFormField);

		ddmForm.addDDMFormRule(
			new DDMFormRule("getValue(\"field0\") >= 1", "jumpPage(1, 3)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("2")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Set<Integer> disabledPagesIndexes =
			ddmFormEvaluatorEvaluateResponse.getDisabledPagesIndexes();

		Assert.assertTrue(
			disabledPagesIndexes.toString(), disabledPagesIndexes.contains(2));
	}

	@Test
	public void testNotAllCondition() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		DDMFormField ddmFormField1 = createDDMFormField(
			"field1", "number", FieldConstants.DOUBLE);

		ddmFormField1.setRepeatable(true);

		ddmForm.addDDMFormField(ddmFormField0);
		ddmForm.addDDMFormField(ddmFormField1);

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"not(all('between(#value#,2,6)', getValue('field1')))",
				"setVisible(\"field0\", false)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_0", "field1", new UnlocalizedValue("1")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_1", "field1", new UnlocalizedValue("5")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("visible"));
	}

	@Test
	public void testNotBelongsToCondition() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		ddmForm.addDDMFormField(ddmFormField0);

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"not(belongsTo([\"Role1\"]))",
				"setVisible(\"field0\", false)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		Mockito.when(
			_userLocalService.hasRoleUser(
				_company.getCompanyId(), "Role1", _user.getUserId(), true)
		).thenReturn(
			false
		);

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("visible"));
	}

	@Test
	public void testNotCalledJumpPageAction() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.NUMBER);

		ddmForm.addDDMFormField(ddmFormField);

		ddmForm.addDDMFormRule(
			new DDMFormRule("getValue(\"field0\") > 1", "jumpPage(1, 3)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Set<Integer> disabledPagesIndexes =
			ddmFormEvaluatorEvaluateResponse.getDisabledPagesIndexes();

		Assert.assertTrue(
			disabledPagesIndexes.toString(), disabledPagesIndexes.isEmpty());
	}

	@Test
	public void testRequiredValidationWithCheckboxField() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "checkbox", FieldConstants.BOOLEAN);

		ddmFormField.setRequired(true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("false")));

		Map<String, DDMFormFieldValueAccessor<?>> ddmFormFieldValueAccessorMap =
			new HashMap<>();

		ddmFormFieldValueAccessorMap.put(
			"checkbox",
			new DefaultDDMFormFieldValueAccessor() {

				@Override
				public boolean isEmpty(
					DDMFormFieldValue ddmFormFieldValue, Locale locale) {

					return true;
				}

			});

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertEquals(
			"This field is required.",
			ddmFormFieldPropertyChanges.get("errorMessage"));
		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("valid"));
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

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field1", "field1_instanceId"));

		Assert.assertNull(ddmFormFieldPropertyChanges.get("errorMessage"));
		Assert.assertNull(ddmFormFieldPropertyChanges.get("valid"));
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

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"getValue(\"field0\") > 10", "setRequired(\"field1\", true)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("11")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field1", "field1_instanceId"));

		Assert.assertEquals(
			"This field is required.",
			ddmFormFieldPropertyChanges.get("errorMessage"));
		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("valid"));
	}

	@Test
	public void testRequiredValidationWithTextField() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.STRING);

		ddmFormField.setRequired(true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("\n")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertEquals(
			"This field is required.",
			ddmFormFieldPropertyChanges.get("errorMessage"));
		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("valid"));
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

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"getValue(\"field0\") >= 30", "setVisible(\"field1\", false)",
				"setEnabled(\"field2\", false)"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("30")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("15")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("10")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 2,
			ddmFormFieldsPropertyChanges.size());

		// Field 0

		Assert.assertNull(
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId")));

		// Field 1

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field1", "field1_instanceId"));

		Assert.assertEquals(
			ddmFormFieldPropertyChanges.toString(), 1,
			ddmFormFieldPropertyChanges.size());

		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("visible"));

		// Field 2

		ddmFormFieldPropertyChanges = ddmFormFieldsPropertyChanges.get(
			new DDMFormEvaluatorFieldContextKey("field2", "field2_instanceId"));

		Assert.assertEquals(
			ddmFormFieldPropertyChanges.toString(), 1,
			ddmFormFieldPropertyChanges.size());

		Assert.assertTrue((boolean)ddmFormFieldPropertyChanges.get("readOnly"));
	}

	@Test
	public void testSumValuesForRepeatableField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField0 = createDDMFormField(
			"field0", "numeric", FieldConstants.DOUBLE);

		DDMFormField ddmFormField1 = createDDMFormField(
			"field1", "numeric", FieldConstants.DOUBLE);

		ddmFormField1.setRepeatable(true);

		ddmForm.addDDMFormField(ddmFormField0);
		ddmForm.addDDMFormField(ddmFormField1);

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"TRUE", "setValue('field0', sum(getValue('field1')))"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_0", "field1", new UnlocalizedValue("1")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_1", "field1", new UnlocalizedValue("1")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_2", "field1", new UnlocalizedValue("2")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertEquals(
			new BigDecimal(4), ddmFormFieldPropertyChanges.get("value"));
	}

	@Test
	public void testUpdateAndCalculateRule() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "numeric", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field1", "numeric", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field2", "numeric", FieldConstants.DOUBLE));

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"getValue(\"field0\") > 0 && getValue(\"field1\") > 0",
				"calculate(\"field2\", getValue(\"field0\") * " +
					"getValue(\"field1\"))"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("5")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("2")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("0")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		// Field 0

		Assert.assertNull(
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId")));

		// Field 1

		Assert.assertNull(
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field1", "field1_instanceId")));

		// Field 2

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field2", "field2_instanceId"));

		Assert.assertEquals(
			ddmFormFieldPropertyChanges.toString(), 1,
			ddmFormFieldPropertyChanges.size());

		Assert.assertEquals(
			ddmFormFieldPropertyChanges.toString(), new BigDecimal(10.0),
			ddmFormFieldPropertyChanges.get("value"));
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

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertEquals(
			"This field should be zero.",
			ddmFormFieldPropertyChanges.get("errorMessage"));
		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("valid"));
	}

	@Test
	public void testValidationExpressionWithNoErrorMessage() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "numeric", FieldConstants.INTEGER);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("field0 > 10");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertEquals(
			"This field is invalid.",
			ddmFormFieldPropertyChanges.get("errorMessage"));
		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("valid"));
	}

	@Test
	public void testValidationRule() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "numeric", FieldConstants.DOUBLE));

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				"getValue(\"field0\") <= 10",
				"setInvalid(\"field0\", \"The value should be greater than " +
					"10.\")"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("5")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertEquals(
			"The value should be greater than 10.",
			ddmFormFieldPropertyChanges.get("errorMessage"));
		Assert.assertFalse((boolean)ddmFormFieldPropertyChanges.get("valid"));
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

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("6")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("")));

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			doEvaluate(ddmForm, ddmFormValues);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Assert.assertEquals(
			ddmFormFieldsPropertyChanges.toString(), 1,
			ddmFormFieldsPropertyChanges.size());

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field1", "field1_instanceId"));

		Assert.assertTrue((boolean)ddmFormFieldPropertyChanges.get("visible"));
	}

	protected DDMExpressionFunction createAllFunction() throws Exception {
		AllFunction allFunction = new AllFunction();

		field(
			AllFunction.class, "ddmExpressionFactory"
		).set(
			allFunction, _ddmExpressionFactory
		);

		return allFunction;
	}

	protected DDMExpressionFunction createBelongsToRoleFunction()
		throws Exception {

		BelongsToRoleFunction belongsToRoleFunction =
			new BelongsToRoleFunction();

		field(
			BelongsToRoleFunction.class, "roleLocalService"
		).set(
			belongsToRoleFunction, _roleLocalService
		);

		field(
			BelongsToRoleFunction.class, "userGroupRoleLocalService"
		).set(
			belongsToRoleFunction, _userGroupRoleLocalService
		);

		field(
			BelongsToRoleFunction.class, "userLocalService"
		).set(
			belongsToRoleFunction, _userLocalService
		);

		return belongsToRoleFunction;
	}

	protected Map<String, DDMExpressionFunction>
		createDDMExpressionFunctionMap() throws Exception {

		Map<String, DDMExpressionFunction> ddmExpressionFunctionMap =
			new HashMap<>();

		ddmExpressionFunctionMap.put("all", createAllFunction());
		ddmExpressionFunctionMap.put(
			"belongsTo", createBelongsToRoleFunction());
		ddmExpressionFunctionMap.put("between", new BetweenFunction());
		ddmExpressionFunctionMap.put("calculate", new CalculateFunction());
		ddmExpressionFunctionMap.put("getValue", new GetValueFunction());
		ddmExpressionFunctionMap.put("jumpPage", new JumpPageFunction());
		ddmExpressionFunctionMap.put("setEnabled", new SetEnabledFunction());
		ddmExpressionFunctionMap.put("setInvalid", new SetInvalidFunction());
		ddmExpressionFunctionMap.put("setRequired", new SetRequiredFunction());
		ddmExpressionFunctionMap.put("setValue", new SetValueFunction());
		ddmExpressionFunctionMap.put("setVisible", new SetVisibleFunction());
		ddmExpressionFunctionMap.put("sum", new SumFunction());

		return ddmExpressionFunctionMap;
	}

	protected DDMFormField createDDMFormField(
		String name, String type, String dataType) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);

		return ddmFormField;
	}

	protected Map<String, DDMFormFieldValueAccessor<?>>
		createDDMFormFieldValueAccessorMap() {

		Map<String, DDMFormFieldValueAccessor<?>> ddmFormFieldValueAccessorMap =
			new HashMap<>();

		ddmFormFieldValueAccessorMap.put(
			"checkbox", new CheckboxDDMFormFieldValueAccessor());
		ddmFormFieldValueAccessorMap.put(
			"numeric", new NumericDDMFormFieldValueAccessor());
		ddmFormFieldValueAccessorMap.put(
			"text", new DefaultDDMFormFieldValueAccessor());

		return ddmFormFieldValueAccessorMap;
	}

	protected DDMFormEvaluatorEvaluateResponse doEvaluate(
			DDMForm ddmForm, DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, LocaleUtil.US);

		builder.withCompanyId(
			1L
		).withGroupId(
			1L
		).withUserId(
			1L
		);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				builder.build(), _ddmExpressionFactory,
				mockDDMFormFieldTypeServicesTracker());

		mockDDMExpressionFunctionTracker(ddmFormEvaluatorHelper);

		return ddmFormEvaluatorHelper.evaluate();
	}

	protected void mockDDMExpressionFunctionTracker(
			DDMFormEvaluatorHelper ddmFormEvaluatorHelper)
		throws Exception {

		Map<String, DDMExpressionFunction> ddmExpressionFunctionMap =
			createDDMExpressionFunctionMap();

		for (Map.Entry<String, DDMExpressionFunction> entry :
				ddmExpressionFunctionMap.entrySet()) {

			DDMExpressionFunction ddmExpressionFunction = entry.getValue();

			if (ddmExpressionFunction instanceof
					DDMExpressionActionHandlerAware) {

				((DDMExpressionActionHandlerAware)ddmExpressionFunction).
					setDDMExpressionActionHandler(
						ddmFormEvaluatorHelper.
							ddmFormEvaluatorExpressionActionHandler);
			}

			if (ddmExpressionFunction instanceof
					DDMExpressionFieldAccessorAware) {

				((DDMExpressionFieldAccessorAware)ddmExpressionFunction).
					setDDMExpressionFieldAccessor(
						ddmFormEvaluatorHelper.
							ddmFormEvaluatorDDMExpressionFieldAccessor);
			}

			if (ddmExpressionFunction instanceof DDMExpressionObserverAware) {
				((DDMExpressionObserverAware)ddmExpressionFunction).
					setDDMExpressionObserver(
						ddmFormEvaluatorHelper.
							ddmFormEvaluatorExpressionObserver);
			}

			if (ddmExpressionFunction instanceof
					DDMExpressionParameterAccessorAware) {

				((DDMExpressionParameterAccessorAware)ddmExpressionFunction).
					setDDMExpressionParameterAccessor(
						ddmFormEvaluatorHelper.
							ddmFormEvaluatorExpressionParameterAccessor);
			}
		}

		DDMExpressionFunctionTracker ddmExpressionFunctionTracker = mock(
			DDMExpressionFunctionTracker.class);

		when(
			ddmExpressionFunctionTracker.getDDMExpressionFunctions(
				Matchers.any())
		).thenReturn(
			ddmExpressionFunctionMap
		);

		field(
			DDMExpressionFactoryImpl.class, "ddmExpressionFunctionTracker"
		).set(
			_ddmExpressionFactory, ddmExpressionFunctionTracker
		);
	}

	protected DDMFormFieldTypeServicesTracker
		mockDDMFormFieldTypeServicesTracker() {

		Map<String, DDMFormFieldValueAccessor<?>> ddmFormFieldValueAccessorMap =
			createDDMFormFieldValueAccessorMap();

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			Mockito.mock(DDMFormFieldTypeServicesTracker.class);

		for (Map.Entry<String, DDMFormFieldValueAccessor<?>> entry :
				ddmFormFieldValueAccessorMap.entrySet()) {

			Mockito.when(
				ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
					Matchers.eq(entry.getKey()))
			).then(
				new Answer<DDMFormFieldValueAccessor<?>>() {

					@Override
					public DDMFormFieldValueAccessor<?> answer(
							InvocationOnMock invocation)
						throws Throwable {

						return entry.getValue();
					}

				}
			);

		}

		return ddmFormFieldTypeServicesTracker;
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		_language = Mockito.mock(Language.class);

		Mockito.when(
			_language.get(
				Matchers.any(ResourceBundle.class),
				Matchers.eq("this-field-is-invalid"))
		).thenReturn(
			"This field is invalid."
		);

		Mockito.when(
			_language.get(
				Matchers.any(ResourceBundle.class),
				Matchers.eq("this-field-is-required"))
		).thenReturn(
			"This field is required."
		);

		languageUtil.setLanguage(_language);
	}

	protected void setUpPortalUtil() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getUser(_request)
		).thenReturn(
			_user
		);

		Mockito.when(
			portal.getCompany(_request)
		).thenReturn(
			_company
		);

		portalUtil.setPortal(portal);
	}

	protected void setUpResourceBundleLoaderUtil() {
		PowerMockito.mockStatic(ResourceBundleLoaderUtil.class);

		ResourceBundleLoader portalResourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		Mockito.when(
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader()
		).thenReturn(
			portalResourceBundleLoader
		);
	}

	@Mock
	private Company _company;

	private DDMExpressionFactory _ddmExpressionFactory;
	private Language _language;

	@Mock
	private HttpServletRequest _request;

	@Mock
	private Role _role;

	@Mock
	private RoleLocalService _roleLocalService;

	@Mock
	private User _user;

	@Mock
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Mock
	private UserLocalService _userLocalService;

}