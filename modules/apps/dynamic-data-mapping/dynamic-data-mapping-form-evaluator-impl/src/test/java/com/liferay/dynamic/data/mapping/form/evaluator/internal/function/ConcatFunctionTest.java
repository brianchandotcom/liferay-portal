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
public class ConcatFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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