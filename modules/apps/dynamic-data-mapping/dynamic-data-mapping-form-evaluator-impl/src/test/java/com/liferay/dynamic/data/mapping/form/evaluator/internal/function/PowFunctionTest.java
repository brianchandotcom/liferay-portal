/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Selton Guedes
 */
@RunWith(Parameterized.class)
public class PowFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "n1={0}, n2={1}, expectedObject={2}")
	public static List<BigDecimal[]> data() {
		return Arrays.asList(
			new BigDecimal[][] {
				{new BigDecimal(3), new BigDecimal(4), new BigDecimal(81)},
				{new BigDecimal(6), new BigDecimal(3), new BigDecimal(216)}
			});
	}

	@Test
	public void testApply() {
		PowFunction powFunction = new PowFunction();

		Assert.assertEquals(expectedObject, powFunction.apply(n1, n2));
	}

	@Parameterized.Parameter(2)
	public BigDecimal expectedObject;

	@Parameterized.Parameter
	public BigDecimal n1;

	@Parameterized.Parameter(1)
	public BigDecimal n2;

}