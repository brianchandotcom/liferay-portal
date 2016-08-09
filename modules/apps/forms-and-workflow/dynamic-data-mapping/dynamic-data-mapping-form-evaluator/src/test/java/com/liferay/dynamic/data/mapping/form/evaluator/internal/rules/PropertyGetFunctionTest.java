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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.functions.PropertyGetFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class PropertyGetFunctionTest extends BasePropertyFunctionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFieldName() throws Exception {
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		ddmFormFieldEvaluationResultsMap.put(
			"fieldName", ddmFormFieldEvaluationResults);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult(
				"fieldName", "fieldName_instanceId");

		ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			ddmFormFieldEvaluationResultsMap);

		propertyGetFunction.evaluate("invalidFieldName#0", "value");
	}

	@Test
	public void testSetReadOnly() throws Exception {
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		ddmFormFieldEvaluationResultsMap.put(
			"fieldName", ddmFormFieldEvaluationResults);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult(
				"fieldName", "fieldName_instanceId");

		ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			ddmFormFieldEvaluationResultsMap);

		ddmFormFieldEvaluationResult.setReadOnly(true);

		Assert.assertEquals(
			true, propertyGetFunction.evaluate("fieldName#0", "readOnly"));

		ddmFormFieldEvaluationResult.setReadOnly(false);

		Assert.assertEquals(
			false, propertyGetFunction.evaluate("fieldName#0", "readOnly"));
	}

	@Test
	public void testSetValid() throws Exception {
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		ddmFormFieldEvaluationResultsMap.put(
			"fieldName", ddmFormFieldEvaluationResults);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult(
				"fieldName", "fieldName_instanceId");

		ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			ddmFormFieldEvaluationResultsMap);

		ddmFormFieldEvaluationResult.setValid(true);

		Assert.assertEquals(
			true, propertyGetFunction.evaluate("fieldName#0", "valid"));

		ddmFormFieldEvaluationResult.setValid(false);

		Assert.assertEquals(
			false, propertyGetFunction.evaluate("fieldName#0", "valid"));
	}

	@Test
	public void testSetValue() throws Exception {
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		ddmFormFieldEvaluationResultsMap.put(
			"fieldName", ddmFormFieldEvaluationResults);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult(
				"fieldName", "fieldName_instanceId");

		ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			ddmFormFieldEvaluationResultsMap);

		ddmFormFieldEvaluationResult.setValue("test");

		Assert.assertEquals(
			"test", propertyGetFunction.evaluate("fieldName#0", "value"));

		ddmFormFieldEvaluationResult.setValue(10.4);

		Assert.assertEquals(
			10.4, propertyGetFunction.evaluate("fieldName#0", "value"));
	}

	@Test
	public void testSetVisible() throws Exception {
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		ddmFormFieldEvaluationResultsMap.put(
			"fieldName", ddmFormFieldEvaluationResults);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDefaultDDMFormFieldEvaluationResult(
				"fieldName", "fieldName_instanceId");

		ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			ddmFormFieldEvaluationResultsMap);

		ddmFormFieldEvaluationResult.setVisible(true);

		Assert.assertEquals(
			true, propertyGetFunction.evaluate("fieldName#0", "visible"));

		ddmFormFieldEvaluationResult.setVisible(false);

		Assert.assertEquals(
			false, propertyGetFunction.evaluate("fieldName#0", "visible"));
	}

}