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
public class EqualsFunctionTest {

	@Test
	public void testApplyFalse1() {
		EqualsFunction equalsFunction = new EqualsFunction();

		Assert.assertFalse(equalsFunction.apply("FORMS", "forms"));
	}

	@Test
	public void testApplyFalse2() {
		EqualsFunction equalsFunction = new EqualsFunction();

		Assert.assertFalse(equalsFunction.apply(null, "forms"));
	}

	@Test
	public void testApplyFalse3() {
		EqualsFunction equalsFunction = new EqualsFunction();

		Assert.assertFalse(equalsFunction.apply("1", new BigDecimal(1)));
	}

	@Test
	public void testApplyTrue() {
		EqualsFunction equalsFunction = new EqualsFunction();

		Assert.assertTrue(equalsFunction.apply("forms", "forms"));
	}

}