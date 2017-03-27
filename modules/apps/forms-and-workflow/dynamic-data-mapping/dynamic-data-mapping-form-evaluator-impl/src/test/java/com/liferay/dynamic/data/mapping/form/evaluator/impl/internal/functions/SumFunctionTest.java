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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class SumFunctionTest {

	@Test
	public void testEvaluateArray1() throws Exception {
		SumFunction sumFunction = new SumFunction();

		Object parameters = new Integer[] {1, 2, 4};

		Assert.assertEquals(7, sumFunction.evaluate(parameters));
	}

	@Test
	public void testEvaluateArray2() throws Exception {
		SumFunction sumFunction = new SumFunction();

		Object parameters = new Double[] {3.8, 5D, 7D};

		Assert.assertEquals(15.8, sumFunction.evaluate(parameters));
	}

	@Test
	public void testEvaluateEquals1() throws Exception {
		SumFunction sumFunction = new SumFunction();

		Assert.assertEquals(5, sumFunction.evaluate(2, 3));
	}

	@Test
	public void testEvaluateEquals2() throws Exception {
		SumFunction sumFunction = new SumFunction();

		Assert.assertEquals(21.4D, sumFunction.evaluate(1, 13.4, 7));
	}

}