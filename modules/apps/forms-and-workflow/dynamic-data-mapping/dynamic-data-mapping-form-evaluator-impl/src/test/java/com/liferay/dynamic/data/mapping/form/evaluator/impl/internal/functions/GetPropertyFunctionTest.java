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
public class GetPropertyFunctionTest extends BaseDDMFormRuleFunctionTest {

	@Test
	public void testGetBooleanProperty() {
		String propertyName = StringUtil.randomString();

		boolean randomBoolean = RandomTestUtil.randomBoolean();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDDMFormFieldEvaluationResult(
				"Field", propertyName, randomBoolean);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult);

		GetPropertyFunction getPropertyFunction = new GetPropertyFunction(
			ddmFormFieldEvaluationResultsMap, propertyName);

		boolean propertyValue = evaluateGetPropertyFunction(
			getPropertyFunction, "Field");

		Assert.assertEquals(randomBoolean, propertyValue);
	}

	@Test
	public void testGetValue() {
		int randomInt = RandomTestUtil.randomInt();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			createDDMFormFieldEvaluationResult("Field", "value", randomInt);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult);

		GetPropertyFunction getPropertyFunction = new GetPropertyFunction(
			ddmFormFieldEvaluationResultsMap, "value");

		int propertyValue = evaluateGetPropertyFunction(
			getPropertyFunction, "Field");

		Assert.assertEquals(randomInt, propertyValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		GetPropertyFunction getPropertyFunction = new GetPropertyFunction(
			null, null);

		getPropertyFunction.evaluate();
	}

	protected <T> T evaluateGetPropertyFunction(
		GetPropertyFunction getPropertyFunction, String fieldName) {

		return (T)getPropertyFunction.evaluate(fieldName);
	}

}