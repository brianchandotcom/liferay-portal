/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class PathUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuildPathWithNonrootFolder() {
		Assert.assertEquals(
			"/folderPath/name", PathUtil.buildPath("/folderPath", "name"));
	}

	@Test
	public void testBuildPathWithRootFolder() {
		Assert.assertEquals(
			"/name", PathUtil.buildPath(StringPool.SLASH, "name"));
	}

	@Test
	public void testGetExtensionWhithMissingExtension() {
		Assert.assertEquals(StringPool.BLANK, PathUtil.getExtension("/name."));
		Assert.assertEquals(StringPool.BLANK, PathUtil.getExtension("/name"));
	}

	@Test
	public void testGetExtensionWithExtension() {
		Assert.assertEquals("ext", PathUtil.getExtension("/name.ext"));
	}

	@Test
	public void testGetName() {
		Assert.assertEquals("name.ext", PathUtil.getName("/name.ext"));
	}

	@Test
	public void testGetNameWithoutExtensionWithExtension() {
		Assert.assertEquals(
			"name", PathUtil.getNameWithoutExtension("/name.ext"));
	}

	@Test
	public void testGetNameWithoutExtensionWithMissingExtension() {
		Assert.assertEquals("name", PathUtil.getNameWithoutExtension("/name."));
		Assert.assertEquals("name", PathUtil.getNameWithoutExtension("/name"));
	}

	@Test
	public void testGetParentFolderPathWithNonrootFolder() {
		Assert.assertEquals(
			"/folder", PathUtil.getParentFolderPath("/folder/name"));
	}

	@Test
	public void testGetParentFolderPathWithRootFolder() {
		Assert.assertEquals("/", PathUtil.getParentFolderPath("/name"));
	}

}