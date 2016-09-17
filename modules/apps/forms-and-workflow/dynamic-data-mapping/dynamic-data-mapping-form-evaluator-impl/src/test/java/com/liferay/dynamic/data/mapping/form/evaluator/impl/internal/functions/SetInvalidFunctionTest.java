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

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class SetInvalidFunctionTest extends BaseDDMFormRuleFunctionTest {

	@Test
	public void testEvaluate() {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			createDDMFormFieldEvaluationResult(
				"Field_1", "valid", RandomTestUtil.randomBoolean());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult2 =
			createDDMFormFieldEvaluationResult(
				"Field_1", "valid", RandomTestUtil.randomBoolean());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult3 =
			createDDMFormFieldEvaluationResult("Field_2", "valid", true);

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult1,
					ddmFormFieldEvaluationResult2,
					ddmFormFieldEvaluationResult3);

		SetInvalidFunction setInvalidFunction = new SetInvalidFunction(
			ddmFormFieldEvaluationResultsMap);

		setInvalidFunction.evaluate("Field_1", "Error Field 1");

		Assert.assertFalse(ddmFormFieldEvaluationResult1.isValid());
		Assert.assertEquals(
			"Error Field 1", ddmFormFieldEvaluationResult1.getErrorMessage());

		Assert.assertFalse(ddmFormFieldEvaluationResult2.isValid());
		Assert.assertEquals(
			"Error Field 1", ddmFormFieldEvaluationResult2.getErrorMessage());

		Assert.assertTrue(ddmFormFieldEvaluationResult3.isValid());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument() throws Exception {
		SetInvalidFunction setInvalidFunction = new SetInvalidFunction(null);

		setInvalidFunction.evaluate("param1");
	}

}