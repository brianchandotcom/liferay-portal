/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.petra.string.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class PathHelperTest {

	@Test
	public void testBuildPathWithNonrootFolder() {
		Assert.assertEquals(
			"/folderPath/name", _pathHelper.buildPath("/folderPath", "name"));
	}

	@Test
	public void testBuildPathWithRootFolder() {
		Assert.assertEquals(
			"/name", _pathHelper.buildPath(StringPool.SLASH, "name"));
	}

	@Test
	public void testGetExtensionWhithMissingExtension() {
		Assert.assertEquals(
			StringPool.BLANK, _pathHelper.getExtension("/name."));
		Assert.assertEquals(
			StringPool.BLANK, _pathHelper.getExtension("/name"));
	}

	@Test
	public void testGetExtensionWithExtension() {
		Assert.assertEquals("ext", _pathHelper.getExtension("/name.ext"));
	}

	@Test
	public void testGetName() {
		Assert.assertEquals("name.ext", _pathHelper.getName("/name.ext"));
	}

	@Test
	public void testGetNameWithoutExtensionWithExtension() {
		Assert.assertEquals(
			"name", _pathHelper.getNameWithoutExtension("/name.ext"));
	}

	@Test
	public void testGetNameWithoutExtensionWithMissingExtension() {
		Assert.assertEquals(
			"name", _pathHelper.getNameWithoutExtension("/name."));
		Assert.assertEquals(
			"name", _pathHelper.getNameWithoutExtension("/name"));
	}

	@Test
	public void testGetParentFolderPathWithNonrootFolder() {
		Assert.assertEquals(
			"/folder", _pathHelper.getParentFolderPath("/folder/name"));
	}

	@Test
	public void testGetParentFolderPathWithRootFolder() {
		Assert.assertEquals("/", _pathHelper.getParentFolderPath("/name"));
	}

	private final PathHelper _pathHelper = new PathHelper();

}