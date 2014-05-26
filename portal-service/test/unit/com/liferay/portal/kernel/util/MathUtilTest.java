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

package com.liferay.portal.kernel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class MathUtilTest {

	@Test
	public void testSumWithDoubleValues() {
		double expected = 1.1 + 2.2 + 3.3 + 5.5 + 8.8;

		Assert.assertEquals(
			expected, MathUtil.sum(1.1, 2.2, 3.3, 5.5, 8.8), 0.01);
	}

	@Test
	public void testSumWithLongValues() {
		long expected = 1 + 2 + 3 + 5 + 8;

		Assert.assertEquals(expected, MathUtil.sum(1L, 2L, 3L, 5L, 8L));
	}

}