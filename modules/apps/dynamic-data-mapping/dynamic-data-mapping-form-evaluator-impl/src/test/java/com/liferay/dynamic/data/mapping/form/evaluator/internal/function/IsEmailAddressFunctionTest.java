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
public class IsEmailAddressFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEmailList() {
		IsEmailAddressFunction isEmailAddressFunction =
			new IsEmailAddressFunction();

		Boolean result = isEmailAddressFunction.apply(
			"test1@liferay.com, test2@liferay.com");

		Assert.assertTrue(result);
	}

	@Test
	public void testInvalidEmail() {
		IsEmailAddressFunction isEmailAddressFunction =
			new IsEmailAddressFunction();

		Boolean result = isEmailAddressFunction.apply(
			"test1@liferay.com, invalid email");

		Assert.assertFalse(result);
	}

	@Test
	public void testSingleEmail() {
		IsEmailAddressFunction isEmailAddressFunction =
			new IsEmailAddressFunction();

		Boolean result = isEmailAddressFunction.apply("test@liferay.com");

		Assert.assertTrue(result);
	}

}