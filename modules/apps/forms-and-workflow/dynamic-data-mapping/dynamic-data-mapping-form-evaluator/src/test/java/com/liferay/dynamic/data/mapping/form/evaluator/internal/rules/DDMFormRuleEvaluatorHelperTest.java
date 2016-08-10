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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DDMFormRuleEvaluatorHelperTest extends PowerMockito {

	@Test
	public void testCalculatedValue() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		fieldDDMFormField0.setDataType(FieldConstants.NUMBER);

		ddmForm.addDDMFormField(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		fieldDDMFormField1.setDataType(FieldConstants.NUMBER);

		ddmForm.addDDMFormField(fieldDDMFormField1);

		DDMFormField fieldDDMFormField2 = new DDMFormField("field2", "text");

		fieldDDMFormField2.setDataType(FieldConstants.NUMBER);

		ddmForm.addDDMFormField(fieldDDMFormField2);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("5"));

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("2"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		DDMFormFieldValue fieldDDMFormFieldValue2 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("0"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue2);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		List<String> actions = ListUtil.fromArray(
			new String[] {
				"set(fieldAt(\"field2\", 0), \"value\", " +
					"get(fieldAt(\"field0\", 0), \"value\") * " +
						"get(fieldAt(\"field1\", 0), \"value\"))"
			});

		String condition =
			"get(fieldAt(\"field0\", 0), \"value\") > 0 && " +
				"get(fieldAt(\"field1\", 0), \"value\") > 0";

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormRuleEvaluatorHelper ddmFormRuleEvaluatorHelper =
			new DDMFormRuleEvaluatorHelper(
				_ddmExpressionFactory, ddmForm, ddmFormValues, LocaleUtil.US);

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			ddmFormRuleEvaluatorHelper.evaluate();

		Assert.assertEquals(3, ddmFormFieldEvaluationResults.size());

		for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult :
				ddmFormFieldEvaluationResults) {

			if (ddmFormFieldEvaluationResult.getName().equals("field0")) {
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isVisible());
				Assert.assertEquals(
					false, ddmFormFieldEvaluationResult.isReadOnly());
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isValid());
				Assert.assertEquals(
					5.0, ddmFormFieldEvaluationResult.getValue());
			}
			else if(ddmFormFieldEvaluationResult.getName().equals("field1")) {
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isVisible());
				Assert.assertEquals(
					false, ddmFormFieldEvaluationResult.isReadOnly());
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isValid());
				Assert.assertEquals(
					2.0, ddmFormFieldEvaluationResult.getValue());
			}
			else if(ddmFormFieldEvaluationResult.getName().equals("field2")) {
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isVisible());
				Assert.assertEquals(
					false, ddmFormFieldEvaluationResult.isReadOnly());
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isValid());
				Assert.assertEquals(
					10.0, ddmFormFieldEvaluationResult.getValue());
			}
		}
	}

	@Test
	public void testVisibleAndReadOnly() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		fieldDDMFormField0.setDataType(FieldConstants.NUMBER);

		ddmForm.addDDMFormField(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		fieldDDMFormField1.setDataType(FieldConstants.NUMBER);

		ddmForm.addDDMFormField(fieldDDMFormField1);

		DDMFormField fieldDDMFormField2 = new DDMFormField("field2", "text");

		fieldDDMFormField2.setDataType(FieldConstants.NUMBER);

		ddmForm.addDDMFormField(fieldDDMFormField2);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("30"));

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("15"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		DDMFormFieldValue fieldDDMFormFieldValue2 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("10"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue2);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		List<String> actions = ListUtil.fromArray(
			new String[] {
				"set(fieldAt(\"field1\", 0), \"visible\", false)",
				"set(fieldAt(\"field2\", 0), \"readOnly\", true)"
			});

		String condition = "get(fieldAt(\"field0\", 0), \"value\") >= 30";

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormRuleEvaluatorHelper ddmFormRuleEvaluatorHelper =
			new DDMFormRuleEvaluatorHelper(
				_ddmExpressionFactory, ddmForm, ddmFormValues, LocaleUtil.US);

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			ddmFormRuleEvaluatorHelper.evaluate();

		Assert.assertEquals(3, ddmFormFieldEvaluationResults.size());

		for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult :
				ddmFormFieldEvaluationResults) {

			if (ddmFormFieldEvaluationResult.getName().equals("field0")) {
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isVisible());
				Assert.assertEquals(
					false, ddmFormFieldEvaluationResult.isReadOnly());
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isValid());
				Assert.assertEquals(
					30.0, ddmFormFieldEvaluationResult.getValue());
			}
			else if(ddmFormFieldEvaluationResult.getName().equals("field1")) {
				Assert.assertEquals(
					false, ddmFormFieldEvaluationResult.isVisible());
				Assert.assertEquals(
					false, ddmFormFieldEvaluationResult.isReadOnly());
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isValid());
				Assert.assertEquals(
					15.0, ddmFormFieldEvaluationResult.getValue());
			}
			else if(ddmFormFieldEvaluationResult.getName().equals("field2")) {
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isReadOnly());
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isVisible());
				Assert.assertEquals(
					true, ddmFormFieldEvaluationResult.isValid());
				Assert.assertEquals(
					10.0, ddmFormFieldEvaluationResult.getValue());
			}
		}
	}

	private final DDMExpressionFactory _ddmExpressionFactory =
		new DDMExpressionFactoryImpl();

}