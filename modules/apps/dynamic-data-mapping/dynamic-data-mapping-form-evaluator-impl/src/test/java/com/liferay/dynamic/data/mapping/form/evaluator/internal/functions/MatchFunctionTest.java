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
public class MatchFunctionTest {

	@Test
	public void testEvaluateFalse1() throws Exception {
		MatchFunction matchFunction = new MatchFunction();

		Assert.assertEquals(false, matchFunction.evaluate("texto", "[0-9]+"));
	}

	@Test
	public void testEvaluateFalse2() throws Exception {
		MatchFunction matchFunction = new MatchFunction();

		Assert.assertEquals(false, matchFunction.evaluate("123", "[a-z]+"));
	}

	@Test
	public void testEvaluateFalse3() throws Exception {
		MatchFunction matchFunction = new MatchFunction();

		Assert.assertEquals(false, matchFunction.evaluate("invalid*", "\\w+"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEvaluateInvalid() throws Exception {
		MatchFunction matchFunction = new MatchFunction();

		matchFunction.evaluate("value");
	}

	@Test
	public void testEvaluateTrue1() throws Exception {
		MatchFunction matchFunction = new MatchFunction();

		Assert.assertEquals(
			true, matchFunction.evaluate("Liferay123", "Liferay[0-9]{3}"));
	}

	@Test
	public void testEvaluateTrue2() throws Exception {
		MatchFunction matchFunction = new MatchFunction();

		Assert.assertEquals(
			true,
			matchFunction.evaluate("admin@liferay.com", "\\w+@liferay.com"));
	}

}