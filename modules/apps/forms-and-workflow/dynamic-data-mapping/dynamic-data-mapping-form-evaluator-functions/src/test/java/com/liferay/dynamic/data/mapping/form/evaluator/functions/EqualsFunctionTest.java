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
public class EqualsFunctionTest {

	@Test
	public void testEvaluateFalse1() throws Exception {
		EqualsFunction equalsFunction = new EqualsFunction();

		Boolean result = (Boolean)equalsFunction.evaluate(null, "not equals");

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateFalse2() throws Exception {
		EqualsFunction equalsFunction = new EqualsFunction();

		Boolean result = (Boolean)equalsFunction.evaluate("text", null);

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateFalse3() throws Exception {
		EqualsFunction equalsFunction = new EqualsFunction();

		Boolean result = (Boolean)equalsFunction.evaluate(1, "1");

		Assert.assertFalse(result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEvaluateInvalid() throws Exception {
		EqualsFunction equalsFunction = new EqualsFunction();

		equalsFunction.evaluate("test");
	}

	@Test
	public void testEvaluateTrue1() throws Exception {
		EqualsFunction equalsFunction = new EqualsFunction();

		Boolean result = (Boolean)equalsFunction.evaluate(
			"simple text", "simple text");

		Assert.assertTrue(result);
	}

	@Test
	public void testEvaluateTrue2() throws Exception {
		EqualsFunction equalsFunction = new EqualsFunction();

		Boolean result = (Boolean)equalsFunction.evaluate(2, 2);

		Assert.assertTrue(result);
	}

}