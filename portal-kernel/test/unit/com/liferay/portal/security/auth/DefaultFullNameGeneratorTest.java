/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.DefaultFullNameGenerator;

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