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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class FieldAtFunctionTest {

	@Test
	public void testGetField() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		// Field 1

		List<DDMFormFieldEvaluationResult> field1DDMFormFieldEvaluationResults =
			new ArrayList<>();

		field1DDMFormFieldEvaluationResults.add(
			createDDMFormFieldEvaluationResult(
				"Field1", StringUtil.randomString(), "Field 1 Value 0"));

		field1DDMFormFieldEvaluationResults.add(
			createDDMFormFieldEvaluationResult(
				"Field1", StringUtil.randomString(), "Field 1 Value 1"));

		ddmFormFieldEvaluationResultsMap.put(
			"Field1", field1DDMFormFieldEvaluationResults);

		// Field 2

		List<DDMFormFieldEvaluationResult> field2DDMFormFieldEvaluationResults =
			new ArrayList<>();

		field2DDMFormFieldEvaluationResults.add(
			createDDMFormFieldEvaluationResult(
				"Field2", StringUtil.randomString(), "Field 2 Value 0"));

		field2DDMFormFieldEvaluationResults.add(
			createDDMFormFieldEvaluationResult(
				"Field2", StringUtil.randomString(), "Field 2 Value 1"));

		field2DDMFormFieldEvaluationResults.add(
			createDDMFormFieldEvaluationResult(
				"Field2", StringUtil.randomString(), "Field 2 Value 2"));

		ddmFormFieldEvaluationResultsMap.put(
			"Field2", field2DDMFormFieldEvaluationResults);

		FieldAtFunction fieldAtFunction = new FieldAtFunction(
			ddmFormFieldEvaluationResultsMap);

		DDMFormFieldEvaluationResult actualDDMFormFieldEvaluationResult =
			(DDMFormFieldEvaluationResult)fieldAtFunction.evaluate("Field1", 1);

		Assert.assertEquals(
			"Field 1 Value 1", actualDDMFormFieldEvaluationResult.getValue());

		actualDDMFormFieldEvaluationResult =
			(DDMFormFieldEvaluationResult)fieldAtFunction.evaluate("Field2", 2);

		Assert.assertEquals(
			"Field 2 Value 2", actualDDMFormFieldEvaluationResult.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFieldIndex() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		ddmFormFieldEvaluationResults.add(
			createDDMFormFieldEvaluationResult(
				"Field1", StringUtil.randomString(), "Field 1 Value 0"));

		ddmFormFieldEvaluationResults.add(
			createDDMFormFieldEvaluationResult(
				"Field1", StringUtil.randomString(), "Field 1 Value 0"));

		ddmFormFieldEvaluationResultsMap.put(
			"Field1", ddmFormFieldEvaluationResults);

		FieldAtFunction fieldAtFunction = new FieldAtFunction(
			ddmFormFieldEvaluationResultsMap);

		fieldAtFunction.evaluate("Field1", 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFieldName() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		FieldAtFunction fieldAtFunction = new FieldAtFunction(
			ddmFormFieldEvaluationResults);

		fieldAtFunction.evaluate("Field3", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfParameters() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		FieldAtFunction fieldAtFunction = new FieldAtFunction(
			ddmFormFieldEvaluationResults);

		fieldAtFunction.evaluate();
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		String fieldName, String instanceId, Object value) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(fieldName, instanceId);

		ddmFormFieldEvaluationResult.setValue(value);

		return ddmFormFieldEvaluationResult;
	}

}