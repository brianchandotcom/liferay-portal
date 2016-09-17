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
public class PropertyGetFunctionTest extends BasePropertyFunctionTest {

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

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			ddmFormFieldEvaluationResultsMap, propertyName);

		boolean propertyValue = evaluatePropertyGetFunction(
			propertyGetFunction, "Field");

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

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			ddmFormFieldEvaluationResultsMap, "value");

		int propertyValue = evaluatePropertyGetFunction(
			propertyGetFunction, "Field");

		Assert.assertEquals(randomInt, propertyValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		PropertyGetFunction propertyGetFunction = new PropertyGetFunction(
			null, null);

		propertyGetFunction.evaluate();
	}

	protected <T> T evaluatePropertyGetFunction(
		PropertyGetFunction propertyGetFunction, String fieldName) {

		return (T)propertyGetFunction.evaluate(fieldName);
	}

}