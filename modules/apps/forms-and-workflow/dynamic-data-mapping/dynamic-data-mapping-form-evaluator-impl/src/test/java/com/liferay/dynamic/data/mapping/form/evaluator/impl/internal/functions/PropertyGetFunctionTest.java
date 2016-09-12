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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class PropertyGetFunctionTest {

	@Test
	public void testGetReadOnly() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		ddmFormFieldEvaluationResult.setReadOnly(false);

		boolean propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "readOnly");

		Assert.assertEquals(false, propertyValue);

		ddmFormFieldEvaluationResult.setReadOnly(true);

		propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "readOnly");

		Assert.assertTrue(propertyValue);
	}

	@Test
	public void testGetRequired() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		ddmFormFieldEvaluationResult.setRequired(false);

		boolean propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "required");

		Assert.assertEquals(false, propertyValue);

		ddmFormFieldEvaluationResult.setRequired(true);

		propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "required");

		Assert.assertTrue(propertyValue);
	}

	@Test
	public void testGetValid() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		ddmFormFieldEvaluationResult.setValid(false);

		boolean propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "valid");

		Assert.assertEquals(false, propertyValue);

		ddmFormFieldEvaluationResult.setValid(true);

		propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "valid");

		Assert.assertEquals(true, propertyValue);
	}

	@Test
	public void testGetValue() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		ddmFormFieldEvaluationResult.setValue(42);

		int propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "value");

		Assert.assertEquals(42, propertyValue);
	}

	@Test
	public void testGetVisible() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		ddmFormFieldEvaluationResult.setVisible(false);

		boolean propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "visible");

		Assert.assertEquals(false, propertyValue);

		ddmFormFieldEvaluationResult.setVisible(true);

		propertyValue = evaluatePropertyGetFunction(
			ddmFormFieldEvaluationResult, "visible");

		Assert.assertTrue(propertyValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() throws Exception {
		PropertyGetFunction propertyGetFunction = new PropertyGetFunction();

		propertyGetFunction.evaluate();
	}

	protected <T> T evaluatePropertyGetFunction(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		String property) {

		PropertyGetFunction propertyGetFunction = new PropertyGetFunction();

		return (T)propertyGetFunction.evaluate(
			ddmFormFieldEvaluationResult, property);
	}

}