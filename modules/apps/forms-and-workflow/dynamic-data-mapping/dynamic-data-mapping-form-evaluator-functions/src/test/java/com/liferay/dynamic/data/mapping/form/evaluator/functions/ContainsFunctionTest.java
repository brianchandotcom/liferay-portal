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

package com.liferay.dynamic.data.mapping.form.evaluator.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class ContainsFunctionTest {

	@Test
	public void testCaseInsensitiveComparison() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = (Boolean)containsFunction.evaluate(
			"Some test", "Test");

		Assert.assertTrue(result);
	}

	@Test
	public void testEvaluateFalse1() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = (Boolean)containsFunction.evaluate(
			"another text", "not contains");

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateFalse2() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = (Boolean)containsFunction.evaluate(
			null, "not contains");

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateFalse3() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = (Boolean)containsFunction.evaluate(
			"simple text", null);

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateFalse4() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = (Boolean)containsFunction.evaluate(
			"text", "simple text");

		Assert.assertFalse(result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEvaluateInvalid() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		containsFunction.evaluate("test");
	}

	@Test
	public void testEvaluateTrue1() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = (Boolean)containsFunction.evaluate(
			"another text", "another");

		Assert.assertTrue(result);
	}

	@Test
	public void testEvaluateTrue2() throws Exception {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = (Boolean)containsFunction.evaluate(
			"not contains 2", 2);

		Assert.assertTrue(result);
	}

}