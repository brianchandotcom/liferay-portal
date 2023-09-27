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
public class BetweenFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyFalse1() {
		BetweenFunction betweenFunction = new BetweenFunction();

		boolean result = betweenFunction.apply(
			BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE);

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyFalse2() {
		BetweenFunction betweenFunction = new BetweenFunction();

		boolean result = betweenFunction.apply(
			BigDecimal.ONE, new BigDecimal(2), BigDecimal.TEN);

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyTrue() {
		BetweenFunction betweenFunction = new BetweenFunction();

		boolean result = betweenFunction.apply(
			BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);

		Assert.assertTrue(result);
	}

}