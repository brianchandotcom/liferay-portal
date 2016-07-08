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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DDMFormRuleEvaluatorTest extends PowerMockito {

	@Test
	public void testEvaluate() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		List<DDMFormFieldEvaluationResult>
			ddmFormField1InstanceEvaluationResults = new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormField1EvaluationResult =
			createDefaultDDMFormFieldEvaluationResult("field1_instanceId");

		ddmFormField1EvaluationResult.setValue(15.5);

		ddmFormField1InstanceEvaluationResults.add(
			ddmFormField1EvaluationResult);

		ddmFormFieldEvaluationResultsMap.put(
			"field1", ddmFormField1InstanceEvaluationResults);

		List<DDMFormFieldEvaluationResult>
			ddmFormField2InstanceEvaluationResults = new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormField2EvaluationResult =
			createDefaultDDMFormFieldEvaluationResult("field2_instanceId");

		ddmFormField2EvaluationResult.setValue(10);

		ddmFormField2InstanceEvaluationResults.add(
			ddmFormField2EvaluationResult);

		ddmFormFieldEvaluationResultsMap.put(
			"field2", ddmFormField2InstanceEvaluationResults);

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmExpressionFactory, ddmFormFieldEvaluationResultsMap,
			"sum(get(fieldAt(\"field1\", 0), \"value\"), " +
				"get(fieldAt(\"field2\", 0), \"value\")) > 25");

		Map<String, DDMExpressionFunction> functionMap =
			(Map<String, DDMExpressionFunction>)field(
				DDMExpressionFactoryImpl.class, "_ddmExpressionFunctionMap").
					get(_ddmExpressionFactory);

		functionMap.put(
			"sum",
			new DDMExpressionFunction() {

				@Override
				public Object evaluate(Object... parameters) {
					double sum = 0;

					for (int i = 0; i < parameters.length; i++) {
						if (parameters[i] instanceof Number) {
							sum += ((Number)parameters[i]).doubleValue();
						}
					}

					return sum;
				}

			});

		Assert.assertEquals(true, ddmFormRuleEvaluator.evaluate());
	}

	@Test
	public void testExecute1() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldInstanceEvaluationResults = new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult("field1_instanceId");

		ddmFormFieldInstanceEvaluationResults.add(ddmFormFieldEvaluationResult);

		ddmFormFieldEvaluationResultsMap.put(
			"field1", ddmFormFieldInstanceEvaluationResults);

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmExpressionFactory, ddmFormFieldEvaluationResultsMap,
			"set(fieldAt(\"field1\", 0), \"readOnly\", true)");

		ddmFormRuleEvaluator.execute();

		Assert.assertEquals(true, ddmFormFieldEvaluationResult.isReadOnly());
	}

	@Test
	public void testExecute2() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldInstanceEvaluationResults = new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult("field1_instanceId");

		ddmFormFieldInstanceEvaluationResults.add(ddmFormFieldEvaluationResult);

		ddmFormFieldEvaluationResults.put(
			"field1", ddmFormFieldInstanceEvaluationResults);

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmExpressionFactory, ddmFormFieldEvaluationResults,
			"set(fieldAt(\"field1\", 0), \"visible\", true)");

		ddmFormRuleEvaluator.execute();

		Assert.assertEquals(true, ddmFormFieldEvaluationResult.isVisible());
	}

	@Test
	public void testExecute3() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldInstanceEvaluationResults = new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult("field1_instanceId");

		ddmFormFieldInstanceEvaluationResults.add(ddmFormFieldEvaluationResult);

		ddmFormFieldEvaluationResults.put(
			"field1", ddmFormFieldInstanceEvaluationResults);

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmExpressionFactory, ddmFormFieldEvaluationResults,
			"set(fieldAt(\"field1\", 0), \"valid\", false, \"error\")");

		ddmFormRuleEvaluator.execute();

		Assert.assertEquals(false, ddmFormFieldEvaluationResult.isValid());
		Assert.assertEquals(
			"error", ddmFormFieldEvaluationResult.getErrorMessage());
	}

	@Test
	public void testExecute4() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldInstanceEvaluationResults = new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult("field1_instanceId");

		ddmFormFieldInstanceEvaluationResults.add(ddmFormFieldEvaluationResult);

		ddmFormFieldEvaluationResults.put(
			"field1", ddmFormFieldInstanceEvaluationResults);

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmExpressionFactory, ddmFormFieldEvaluationResults,
			"set(fieldAt(\"field1\",0),\"value\",2.7)");

		ddmFormRuleEvaluator.execute();

		Assert.assertEquals(2.7, ddmFormFieldEvaluationResult.getValue());
	}

	protected DDMFormFieldEvaluationResult
		createDefaultDDMFormFieldEvaluationResult(String instanceId) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult("field1", instanceId);

		ddmFormFieldEvaluationResult.setErrorMessage(StringPool.BLANK);
		ddmFormFieldEvaluationResult.setReadOnly(false);
		ddmFormFieldEvaluationResult.setValid(true);
		ddmFormFieldEvaluationResult.setVisible(true);

		return ddmFormFieldEvaluationResult;
	}

	private final DDMExpressionFactory _ddmExpressionFactory =
		new DDMExpressionFactoryImpl();

}