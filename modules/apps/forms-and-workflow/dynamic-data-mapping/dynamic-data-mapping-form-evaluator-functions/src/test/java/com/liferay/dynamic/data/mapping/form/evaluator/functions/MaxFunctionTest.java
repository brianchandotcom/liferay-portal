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
public class MaxFunctionTest {

	@Test
	public void testEvaluateMax1() throws Exception {
		MaxFunction maxFunction = new MaxFunction();

		Assert.assertEquals(6.0, maxFunction.evaluate(3, 2, 5, 6));
	}

	@Test
	public void testEvaluateMax2() throws Exception {
		MaxFunction maxFunction = new MaxFunction();

		Assert.assertEquals(4.0, maxFunction.evaluate(4, 3, 2, 1));
	}

	@Test
	public void testEvaluateMax3() throws Exception {
		MaxFunction maxFunction = new MaxFunction();

		Assert.assertEquals(8.0, maxFunction.evaluate(5, 6, 7, 8));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumber() throws Exception {
		MaxFunction maxFunction = new MaxFunction();

		maxFunction.evaluate(1, "invalid", 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfParameters() throws Exception {
		MaxFunction maxFunction = new MaxFunction();

		maxFunction.evaluate(1);
	}

}