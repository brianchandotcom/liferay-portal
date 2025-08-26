/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.hashed.files;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class HashedFilesUtilTest {

	@Test
	public void testAddHash() {
		Assert.assertEquals(
			"/css/main.(HASH1234).css",
			HashedFilesUtil.addHash("/css/main.css", "HASH1234"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddHashWithIncorrectURI() {
		HashedFilesUtil.addHash("/css/main.(HASH1234).css", "HASH1234");
	}

	@Test
	public void testAddNameSuffix() {
		Assert.assertEquals(
			"/css/main_a_suffix.(HASH1234).css",
			HashedFilesUtil.addNameSuffix(
				"/css/main.(HASH1234).css", "_a_suffix"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNameSuffixWithIncorrectURI() {
		HashedFilesUtil.addNameSuffix("/css/main.css", "_a_suffix");
	}

	@Test
	public void testContainsHash() {
		Assert.assertTrue(
			HashedFilesUtil.containsHash("/css/main.(HASH1234).css"));

		Assert.assertFalse(HashedFilesUtil.containsHash("/css/main.css"));
	}

	@Test
	public void testGetHash() {
		Assert.assertEquals(
			"HASH1234", HashedFilesUtil.getHash("/css/main.(HASH1234).css"));

		Assert.assertNull(HashedFilesUtil.getHash("/css/main.css"));
	}

	@Test
	public void testRemoveHash() {
		Assert.assertEquals(
			"/css/main.css",
			HashedFilesUtil.removeHash("/css/main.(HASH1234).css"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveHashWithIncorrectURI() {
		HashedFilesUtil.removeHash("/css/main.css");
	}

}