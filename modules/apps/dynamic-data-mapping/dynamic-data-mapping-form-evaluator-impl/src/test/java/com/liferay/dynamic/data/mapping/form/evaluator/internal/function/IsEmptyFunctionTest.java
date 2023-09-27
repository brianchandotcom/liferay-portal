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
public class IsEmptyFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testArray() {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Boolean result = isEmptyFunction.apply(
			new String[] {"  ", "not empty "});

		Assert.assertFalse(result);
	}

	@Test
	public void testEmptyParameter() {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Boolean result = isEmptyFunction.apply(" ");

		Assert.assertTrue(result);
	}

	@Test
	public void testNullParameter() {
		IsEmptyFunction isEmptyFunction = new IsEmptyFunction();

		Boolean result = isEmptyFunction.apply(null);

		Assert.assertTrue(result);
	}

}