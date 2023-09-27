/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class IsDecimalFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyFalse() {
		IsDecimalFunction isDecimalFunction = new IsDecimalFunction();

		Boolean result = isDecimalFunction.apply("NUMBER");

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyTrue1() {
		IsDecimalFunction isDecimalFunction = new IsDecimalFunction();

		Boolean result = isDecimalFunction.apply("1.2");

		Assert.assertTrue(result);
	}

	@Test
	public void testApplyTrue2() {
		IsDecimalFunction isDecimalFunction = new IsDecimalFunction();

		Boolean result = isDecimalFunction.apply("3");

		Assert.assertTrue(result);
	}

}