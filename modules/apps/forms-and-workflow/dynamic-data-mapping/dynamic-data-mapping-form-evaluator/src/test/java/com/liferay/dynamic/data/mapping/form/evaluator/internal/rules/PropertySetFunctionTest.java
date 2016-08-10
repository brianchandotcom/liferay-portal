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
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.functions.PropertySetFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class PropertySetFunctionTest extends BasePropertyFunctionTest {

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

		PropertySetFunction propertySetFunction = new PropertySetFunction(
			ddmFormFieldEvaluationResultsMap);

		propertySetFunction.evaluate("invalidFieldName#0", "value", "test");
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

		PropertySetFunction propertySetFunction = new PropertySetFunction(
			ddmFormFieldEvaluationResultsMap);

		propertySetFunction.evaluate("fieldName#0", "readOnly", true);

		Assert.assertEquals(true, ddmFormFieldEvaluationResult.isReadOnly());

		propertySetFunction.evaluate("fieldName#0", "readOnly", false);

		Assert.assertEquals(false, ddmFormFieldEvaluationResult.isReadOnly());
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

		PropertySetFunction propertySetFunction = new PropertySetFunction(
			ddmFormFieldEvaluationResultsMap);

		propertySetFunction.evaluate("fieldName#0", "valid", true);

		Assert.assertEquals(true, ddmFormFieldEvaluationResult.isValid());

		propertySetFunction.evaluate("fieldName#0", "valid", false);

		Assert.assertEquals(false, ddmFormFieldEvaluationResult.isValid());
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

		PropertySetFunction propertySetFunction = new PropertySetFunction(
			ddmFormFieldEvaluationResultsMap);

		propertySetFunction.evaluate("fieldName#0", "value", "test");

		Assert.assertEquals("test", ddmFormFieldEvaluationResult.getValue());

		propertySetFunction.evaluate("fieldName#0", "value", 23.4);

		Assert.assertEquals(23.4, ddmFormFieldEvaluationResult.getValue());
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

		PropertySetFunction propertySetFunction = new PropertySetFunction(
			ddmFormFieldEvaluationResultsMap);

		propertySetFunction.evaluate("fieldName#0", "visible", true);

		Assert.assertEquals(true, ddmFormFieldEvaluationResult.isVisible());

		propertySetFunction.evaluate("fieldName#0", "visible", false);

		Assert.assertEquals(false, ddmFormFieldEvaluationResult.isVisible());
	}

}