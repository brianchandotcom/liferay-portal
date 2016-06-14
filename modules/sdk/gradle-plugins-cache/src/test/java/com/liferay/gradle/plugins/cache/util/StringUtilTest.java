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