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
public class PropertySetFunctionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() throws Exception {
		PropertySetFunction propertySetFunction = new PropertySetFunction();

		propertySetFunction.evaluate();
	}

	@Test
	public void testSetReadOnly() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		evaluatePropertySetFunction(
			ddmFormFieldEvaluationResult, "readOnly", false);

		Assert.assertFalse(ddmFormFieldEvaluationResult.isReadOnly());

		evaluatePropertySetFunction(
			ddmFormFieldEvaluationResult, "readOnly", true);

		Assert.assertTrue(ddmFormFieldEvaluationResult.isReadOnly());
	}

	@Test
	public void testSetRequired() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		evaluatePropertySetFunction(
			ddmFormFieldEvaluationResult, "required", false);

		Assert.assertFalse(ddmFormFieldEvaluationResult.isRequired());

		evaluatePropertySetFunction(
			ddmFormFieldEvaluationResult, "required", true);

		Assert.assertTrue(ddmFormFieldEvaluationResult.isRequired());
	}

	@Test
	public void testSetValid() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		PropertySetFunction propertySetFunction = new PropertySetFunction();

		propertySetFunction.evaluate(
			ddmFormFieldEvaluationResult, "valid", false,
			"This field is invalid.");

		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());
		Assert.assertEquals(
			"This field is invalid.",
			ddmFormFieldEvaluationResult.getErrorMessage());
	}

	@Test
	public void testSetValue() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		evaluatePropertySetFunction(ddmFormFieldEvaluationResult, "value", 42);

		Assert.assertEquals(42, ddmFormFieldEvaluationResult.getValue());
	}

	@Test
	public void testSetVisible() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field", StringUtil.randomString());

		evaluatePropertySetFunction(
			ddmFormFieldEvaluationResult, "visible", false);

		Assert.assertFalse(ddmFormFieldEvaluationResult.isVisible());

		evaluatePropertySetFunction(
			ddmFormFieldEvaluationResult, "visible", true);

		Assert.assertTrue(ddmFormFieldEvaluationResult.isVisible());
	}

	protected void evaluatePropertySetFunction(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		String property, Object value) {

		PropertySetFunction propertySetFunction = new PropertySetFunction();

		propertySetFunction.evaluate(
			ddmFormFieldEvaluationResult, property, value);
	}

}