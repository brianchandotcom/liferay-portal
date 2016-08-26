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
public class ConcatFunctionTest {

	@Test
	public void testConcatConstants() throws Exception {
		ConcatFunction concatFunction = new ConcatFunction();

		Assert.assertEquals(
			"hello world!", concatFunction.evaluate("hello ", "world", "!"));
	}

	@Test
	public void testConcatNull() throws Exception {
		ConcatFunction concatFunction = new ConcatFunction();

		Assert.assertEquals("test", concatFunction.evaluate("test", null));
	}

	@Test
	public void testConcatNullWithConstant() throws Exception {
		ConcatFunction concatFunction = new ConcatFunction();

		Assert.assertEquals("test", concatFunction.evaluate(null, "test"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfParameters() throws Exception {
		ConcatFunction concatFunction = new ConcatFunction();

		concatFunction.evaluate("invalid");
	}

}