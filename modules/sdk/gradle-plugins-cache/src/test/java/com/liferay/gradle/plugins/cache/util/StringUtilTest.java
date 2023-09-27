/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.cache.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtilTest {

	@Test
	public void testGetCommonPrefix() {
		Assert.assertEquals(
			"/home/user1/tmp",
			StringUtil.getCommonPrefix(
				'/', "/home/user1/tmp/coverage/test",
				"/home/user1/tmp/covert/operator",
				"/home/user1/tmp/coven/members"));

		Assert.assertNull(
			StringUtil.getCommonPrefix('/', "/dir1/tmp", "/dir2/tmp"));
	}

	@Test
	public void testGetCommonPrefixWindows() {
		Assert.assertEquals(
			"C:/home/user1/tmp",
			StringUtil.getCommonPrefix(
				'/', "C:/home/user1/tmp/coverage/test",
				"C:/home/user1/tmp/covert/operator",
				"C:/home/user1/tmp/coven/members"));

		Assert.assertNull(
			StringUtil.getCommonPrefix('/', "C:/dir1/tmp", "D:/dir2/tmp"));
	}

}