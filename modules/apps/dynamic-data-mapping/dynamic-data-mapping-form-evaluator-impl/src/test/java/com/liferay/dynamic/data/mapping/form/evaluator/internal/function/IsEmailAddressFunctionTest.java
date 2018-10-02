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
public class IsEmailAddressFunctionTest {

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