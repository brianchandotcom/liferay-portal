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

package com.liferay.portal.security.auth;

import com.liferay.portal.model.UserConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class DefaultFullNameGeneratorTest {

	@Test
	public void testNormalLengthGetFullName() {
		String fullName = _defaultDefaultFullNameGenerator.getFullName(
			"Test", "Test", "Test");

		Assert.assertTrue(
			fullName.length() < UserConstants.FULL_NAME_MAX_LENGTH);
		Assert.assertEquals("Test Test Test", fullName);
	}

	@Test
	public void testVeryLongLengthGetFullName() {
		String fullName = _defaultDefaultFullNameGenerator.getFullName(
			"ThisShouldBeAVeryLongName", "ThisShouldBeAVeryLongMiddleName",
			"ThisShouldBeAVeryLongLastName");

		Assert.assertTrue(
			fullName.length() < UserConstants.FULL_NAME_MAX_LENGTH);
		Assert.assertEquals("T T ThisShouldBeAVeryLongLastName", fullName);
	}

	private final DefaultFullNameGenerator _defaultDefaultFullNameGenerator =
		new DefaultFullNameGenerator();

}