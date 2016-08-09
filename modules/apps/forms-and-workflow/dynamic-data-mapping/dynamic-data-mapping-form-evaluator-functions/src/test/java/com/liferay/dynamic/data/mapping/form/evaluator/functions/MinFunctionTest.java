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
public class MinFunctionTest {

	@Test
	public void testEvaluateMin1() throws Exception {
		MinFunction minFunction = new MinFunction();

		Assert.assertEquals(2.0, minFunction.evaluate(3, 2, 5, 6));
	}

	@Test
	public void testEvaluateMin2() throws Exception {
		MinFunction minFunction = new MinFunction();

		Assert.assertEquals(1.0, minFunction.evaluate(4, 3, 2, 1));
	}

	@Test
	public void testEvaluateMin3() throws Exception {
		MinFunction minFunction = new MinFunction();

		Assert.assertEquals(5.0, minFunction.evaluate(5, 6, 7, 8));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumber() throws Exception {
		MinFunction minFunction = new MinFunction();

		minFunction.evaluate(1, "invalid", 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfParameters() throws Exception {
		MinFunction minFunction = new MinFunction();

		minFunction.evaluate(1);
	}

}