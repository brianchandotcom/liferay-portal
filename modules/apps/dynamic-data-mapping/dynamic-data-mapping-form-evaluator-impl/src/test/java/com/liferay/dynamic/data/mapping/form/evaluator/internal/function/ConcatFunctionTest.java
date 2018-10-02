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
public class ConcatFunctionTest {

	@Test
	public void testApply1() {
		ConcatFunction concatFunction = new ConcatFunction();

		String actualString = concatFunction.apply(
			new String[] {"liferay", "forms"});

		Assert.assertEquals("liferayforms", actualString);
	}

	@Test
	public void testApply2() {
		ConcatFunction concatFunction = new ConcatFunction();

		String actualString = concatFunction.apply(
			new String[] {"liferay", null, "forms"});

		Assert.assertEquals("liferayforms", actualString);
	}

	@Test
	public void testApply3() {
		ConcatFunction concatFunction = new ConcatFunction();

		String actualString = concatFunction.apply(
			new String[] {"liferay", null});

		Assert.assertEquals("liferay", actualString);
	}

	@Test
	public void testApply4() {
		ConcatFunction concatFunction = new ConcatFunction();

		String actualString = concatFunction.apply(new String[] {null, null});

		Assert.assertEquals("", actualString);
	}

}