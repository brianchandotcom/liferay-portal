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

package com.liferay.frontend.js.loader.modules.extender.npm;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class ModuleNameUtilTest {

	@Test
	public void testGetPackageName() throws Exception {
		String packageName = ModuleNameUtil.getPackageName(
			"mypackage/lib/mymodule");

		Assert.assertEquals("mypackage", packageName);
	}

	@Test
	public void testGetPackageNameNoModule() throws Exception {
		String packageName = ModuleNameUtil.getPackageName("mypackage");

		Assert.assertEquals("mypackage", packageName);
	}

	@Test
	public void testGetPackageNameScoped() throws Exception {
		String packageName = ModuleNameUtil.getPackageName(
			"@myscope/mypackage/lib/mymodule");

		Assert.assertEquals("@myscope/mypackage", packageName);
	}

	@Test
	public void testGetPackageNameScopedNoModule() throws Exception {
		String packageName = ModuleNameUtil.getPackageName(
			"@myscope/mypackage");

		Assert.assertEquals("@myscope/mypackage", packageName);
	}

	@Test
	public void testGetPackagePath() throws Exception {
		String packagePath = ModuleNameUtil.getPackagePath(
			"mypackage/lib/mymodule");

		Assert.assertEquals("lib/mymodule", packagePath);
	}

	@Test
	public void testGetPackagePathNoModule() throws Exception {
		String packagePath = ModuleNameUtil.getPackagePath("mypackage");

		Assert.assertNull(packagePath);
	}

	@Test
	public void testGetPackagePathScoped() throws Exception {
		String packagePath = ModuleNameUtil.getPackagePath(
			"@myscope/mypackage/lib/mymodule");

		Assert.assertEquals("lib/mymodule", packagePath);
	}

	@Test
	public void testGetPackagePathScopedNoModule() throws Exception {
		String packagePath = ModuleNameUtil.getPackagePath(
			"@myscope/mypackage");

		Assert.assertNull(packagePath);
	}

}