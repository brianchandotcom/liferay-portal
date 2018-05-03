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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class SetPropertyFunctionTest extends BaseDDMFormRuleFunctionTestCase {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		SetPropertyFunction setPropertyFunction = new SetPropertyFunction(
			null, null);

		setPropertyFunction.evaluate();
	}

	@Test
	public void testSetBooleanProperty() {
		String propertyName = StringUtil.randomString();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			createDDMFormFieldEvaluationResult(
				"Field1", propertyName, RandomTestUtil.randomBoolean());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult2 =
			createDDMFormFieldEvaluationResult(
				"Field1", propertyName, RandomTestUtil.randomBoolean());

		boolean field2PropertyValue = RandomTestUtil.randomBoolean();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult3 =
			createDDMFormFieldEvaluationResult(
				"Field2", propertyName, field2PropertyValue);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult1,
					ddmFormFieldEvaluationResult2,
					ddmFormFieldEvaluationResult3);

		SetPropertyFunction setPropertyFunction = new SetPropertyFunction(
			ddmFormFieldEvaluationResultsMap, propertyName);

		boolean field1NewPropertyValue = RandomTestUtil.randomBoolean();

		setPropertyFunction.evaluate("Field1", field1NewPropertyValue);

		assertProperty(
			field1NewPropertyValue, ddmFormFieldEvaluationResult1,
			propertyName);

		assertProperty(
			field1NewPropertyValue, ddmFormFieldEvaluationResult2,
			propertyName);

		// Unchanged property value

		assertProperty(
			field2PropertyValue, ddmFormFieldEvaluationResult3, propertyName);
	}

	@Test
	public void testSetDataType() {
		String propertyName = "dataType";

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			createDDMFormFieldEvaluationResult(
				"Field1", propertyName, "integer");

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult2 =
			createDDMFormFieldEvaluationResult("Field2", "value", "integer");

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult1,
					ddmFormFieldEvaluationResult2);

		SetPropertyFunction setPropertyFunction = new SetPropertyFunction(
			ddmFormFieldEvaluationResultsMap, propertyName);

		String field2NewPropertyValue = "double";

		setPropertyFunction.evaluate("Field2", field2NewPropertyValue);

		assertProperty("integer", ddmFormFieldEvaluationResult1, propertyName);

		assertProperty(
			field2NewPropertyValue, ddmFormFieldEvaluationResult2,
			propertyName);
	}

	@Test
	public void testSetMultiple() {
		String propertyName = "multiple";

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			createDDMFormFieldEvaluationResult("Field1", propertyName, true);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult2 =
			createDDMFormFieldEvaluationResult("Field2", "value", false);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult1,
					ddmFormFieldEvaluationResult2);

		SetPropertyFunction setPropertyFunction = new SetPropertyFunction(
			ddmFormFieldEvaluationResultsMap, propertyName);

		boolean field2NewPropertyValue = RandomTestUtil.randomBoolean();

		setPropertyFunction.evaluate("Field2", field2NewPropertyValue);

		assertProperty(true, ddmFormFieldEvaluationResult1, propertyName);

		assertProperty(
			field2NewPropertyValue, ddmFormFieldEvaluationResult2,
			propertyName);
	}

	@Test
	public void testSetValue() {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			createDDMFormFieldEvaluationResult(
				"Field1", "value", RandomTestUtil.randomInt());

		int field2Value = RandomTestUtil.randomInt();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult2 =
			createDDMFormFieldEvaluationResult("Field2", "value", field2Value);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult1,
					ddmFormFieldEvaluationResult2);

		SetPropertyFunction setPropertyFunction = new SetPropertyFunction(
			ddmFormFieldEvaluationResultsMap, "value");

		int field1NewValue = RandomTestUtil.randomInt();

		setPropertyFunction.evaluate("Field1", field1NewValue);

		assertValue(field1NewValue, ddmFormFieldEvaluationResult1);

		assertValue(field2Value, ddmFormFieldEvaluationResult2);
	}

	protected static void assertProperty(
		Object expected,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		String name) {

		Object property = ddmFormFieldEvaluationResult.getProperty(name);

		Assert.assertEquals(expected, property);
	}

	protected static void assertValue(
		Object expected,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult) {

		Object value = ddmFormFieldEvaluationResult.getValue();

		Assert.assertEquals(expected, value);
	}

}