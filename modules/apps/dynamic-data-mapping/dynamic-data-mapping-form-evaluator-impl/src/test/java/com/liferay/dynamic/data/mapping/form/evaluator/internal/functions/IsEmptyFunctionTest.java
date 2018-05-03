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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class IsEmptyFunctionTest {

	@Test
	public void testEvaluateFalse1() throws Exception {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertFalse((Boolean)isEmptyFunction.evaluate("test"));
	}

	@Test
	public void testEvaluateFalse2() throws Exception {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertFalse((Boolean)isEmptyFunction.evaluate(0));
	}

	@Test
	public void testEvaluateFalse3() throws Exception {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertFalse((Boolean)isEmptyFunction.evaluate(false));
	}

	@Test
	public void testEvaluateFalse4() throws Exception {
		Object parameters = new Integer[] {1, 2};

		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertFalse((Boolean)isEmptyFunction.evaluate(parameters));
	}

	@Test
	public void testEvaluateFalse5() throws Exception {
		Object parameters = new Double[] {3.0};

		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertFalse((Boolean)isEmptyFunction.evaluate(parameters));
	}

	@Test
	public void testEvaluateFalse6() throws Exception {
		Object parameters = new String[] {"", "test"};

		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertFalse((Boolean)isEmptyFunction.evaluate(parameters));
	}

	@Test
	public void testEvaluateTrue1() throws Exception {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertTrue((Boolean)isEmptyFunction.evaluate(""));
	}

	@Test
	public void testEvaluateTrue2() throws Exception {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertTrue((Boolean)isEmptyFunction.evaluate(null));
	}

	@Test
	public void testEvaluateTrue3() throws Exception {
		Object parameters = new String[] {"", ""};

		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Assert.assertTrue((Boolean)isEmptyFunction.evaluate(parameters));
	}

}