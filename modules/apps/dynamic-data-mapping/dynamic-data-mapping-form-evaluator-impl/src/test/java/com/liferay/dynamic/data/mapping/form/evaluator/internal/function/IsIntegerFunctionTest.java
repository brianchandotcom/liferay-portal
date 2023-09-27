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
public class IsIntegerFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testInteger() {
		IsIntegerFunction isIntegerFunction = new IsIntegerFunction();

		Boolean result = isIntegerFunction.apply("12312545");

		Assert.assertTrue(result);
	}

	@Test
	public void testNotAInteger() {
		IsIntegerFunction isIntegerFunction = new IsIntegerFunction();

		Boolean result = isIntegerFunction.apply("number");

		Assert.assertFalse(result);
	}

	@Test
	public void testOutbound() {
		IsIntegerFunction isIntegerFunction = new IsIntegerFunction();

		Boolean result = isIntegerFunction.apply(
			"2312321243423432423424234233234324324242");

		Assert.assertFalse(result);
	}

}