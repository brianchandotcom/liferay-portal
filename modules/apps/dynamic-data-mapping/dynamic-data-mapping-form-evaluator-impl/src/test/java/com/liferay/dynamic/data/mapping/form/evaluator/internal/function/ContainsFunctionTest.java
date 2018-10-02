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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class ContainsFunctionTest {

	@Test
	public void testApplyFalse() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply("liferay", "forms");

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyTrue() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply("liferayFORMS", "forms");

		Assert.assertTrue(result);
	}

	@Test
	public void testApplyWithNull1() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply(null, "forms");

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyWithNull2() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply("liferay", null);

		Assert.assertFalse(result);
	}

}