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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Inácio Nery
 */
public class JumpPageFunctionTest {

	@Test
	public void testEvaluate() {
		Map<Integer, Integer> pageFlow = new HashMap<>();

		JumpPageFunction jumpPageFunction = new JumpPageFunction(pageFlow);

		Object result = jumpPageFunction.evaluate(1.0, 4.0);

		Assert.assertEquals(true, result);

		Assert.assertEquals(4, (int)pageFlow.get(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument() throws Exception {
		JumpPageFunction jumpPageFunction = new JumpPageFunction(null);

		jumpPageFunction.evaluate();
	}

}