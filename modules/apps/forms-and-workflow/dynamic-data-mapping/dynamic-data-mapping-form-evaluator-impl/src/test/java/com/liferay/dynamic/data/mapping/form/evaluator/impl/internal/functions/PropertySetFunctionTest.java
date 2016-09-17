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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class PropertySetFunctionTest extends BasePropertyFunctionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		PropertySetFunction propertySetFunction = new PropertySetFunction(
			null, null);

		propertySetFunction.evaluate();
	}

	@Test
	public void testSetBooleanProperty() {
		String randomPropertyName = StringUtil.randomString();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			createDDMFormFieldEvaluationResult(
				"Field1", randomPropertyName, RandomTestUtil.randomBoolean());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult2 =
			createDDMFormFieldEvaluationResult(
				"Field1", randomPropertyName, RandomTestUtil.randomBoolean());

		boolean ddmFormField2PropertyValue = RandomTestUtil.randomBoolean();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult3 =
			createDDMFormFieldEvaluationResult(
				"Field2", randomPropertyName, ddmFormField2PropertyValue);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult1,
					ddmFormFieldEvaluationResult2,
					ddmFormFieldEvaluationResult3);

		PropertySetFunction propertySetFunction = new PropertySetFunction(
			ddmFormFieldEvaluationResultsMap, randomPropertyName);

		boolean expectedDDMFormField1PropertyValue =
			RandomTestUtil.randomBoolean();

		propertySetFunction.evaluate(
			"Field1", expectedDDMFormField1PropertyValue);

		Assert.assertEquals(
			expectedDDMFormField1PropertyValue,
			ddmFormFieldEvaluationResult1.getProperty(randomPropertyName));

		Assert.assertEquals(
			expectedDDMFormField1PropertyValue,
			ddmFormFieldEvaluationResult2.getProperty(randomPropertyName));

		// Unchanged property value

		Assert.assertEquals(
			ddmFormField2PropertyValue,
			ddmFormFieldEvaluationResult3.getProperty(randomPropertyName));
	}

	@Test
	public void testSetValue() {
		int ddmFormField1PropertyValue = RandomTestUtil.randomInt();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			createDDMFormFieldEvaluationResult(
				"Field1", "value", ddmFormField1PropertyValue);

		int ddmFormField2PropertyValue = RandomTestUtil.randomInt();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult2 =
			createDDMFormFieldEvaluationResult(
				"Field2", "value", ddmFormField2PropertyValue);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult1,
					ddmFormFieldEvaluationResult2);

		int expectedDDMFormField1PropertyValue = RandomTestUtil.randomInt();

		PropertySetFunction propertySetFunction = new PropertySetFunction(
			ddmFormFieldEvaluationResultsMap, "value");

		propertySetFunction.evaluate(
			"Field1", expectedDDMFormField1PropertyValue);

		Assert.assertEquals(
			expectedDDMFormField1PropertyValue,
			ddmFormFieldEvaluationResult1.getValue());
		Assert.assertEquals(
			ddmFormField2PropertyValue,
			ddmFormFieldEvaluationResult2.getValue());
	}

}