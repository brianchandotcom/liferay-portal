/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class MinFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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