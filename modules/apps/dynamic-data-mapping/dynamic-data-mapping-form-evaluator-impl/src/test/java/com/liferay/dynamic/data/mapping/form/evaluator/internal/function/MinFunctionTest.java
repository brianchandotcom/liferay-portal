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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class MinFunctionTest {

	@Test
	public void testEmptyArray() {
		MinFunction minFunction = new MinFunction();

		BigDecimal result = minFunction.apply(new BigDecimal[0]);

		Assert.assertEquals(BigDecimal.ZERO, result);
	}

	@Test
	public void testMax() {
		MinFunction minFunction = new MinFunction();

		BigDecimal result = minFunction.apply(
			new BigDecimal[] {
				new BigDecimal(1), new BigDecimal(10), new BigDecimal(3),
				new BigDecimal(19), new BigDecimal(17)
			});

		Assert.assertEquals(BigDecimal.ONE, result);
	}

}