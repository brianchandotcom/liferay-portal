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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class ModuleNameUtilTest {

	@Before
	public void setUp() {
		new FileUtil().setFile(new FileImpl());
	}

	@Test
	public void testGetDependencyPath() {
		Assert.assertEquals(
			"dep", ModuleNameUtil.getDependencyPath("a-dir/a-module", "dep"));
		Assert.assertEquals(
			"dep",
			ModuleNameUtil.getDependencyPath("a-dir/a-module", "../dep"));
		Assert.assertEquals(
			"a-dir/dep",
			ModuleNameUtil.getDependencyPath("a-dir/a-module", "./dep"));
		Assert.assertEquals(
			"a-dir/dep",
			ModuleNameUtil.getDependencyPath(
				"a-dir/other-dir/a-module", "../dep"));
		Assert.assertEquals(
			"a-dir/other-dir",
			ModuleNameUtil.getDependencyPath("a-dir/other-dir/a-module", "."));
		Assert.assertEquals(
			"a-dir",
			ModuleNameUtil.getDependencyPath("a-dir/other-dir/a-module", ".."));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDependencyPathWithInvalidDependency() {
		ModuleNameUtil.getDependencyPath("a-module", "../dep");
	}

	@Test
	public void testGetPackageName() {
		Assert.assertEquals(
			"a-package", ModuleNameUtil.getPackageName("a-package"));

		Assert.assertEquals(
			"a-package",
			ModuleNameUtil.getPackageName("a-package/a-folder/a-module"));

		Assert.assertEquals(
			"a-package",
			ModuleNameUtil.getPackageName("a-package/a-folder/a-module.js"));

		Assert.assertNull(ModuleNameUtil.getPackageName("./a-module"));
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
	public void testGetPackagePath() {
		Assert.assertNull(ModuleNameUtil.getPackagePath("a-package"));

		Assert.assertEquals(
			"a-module", ModuleNameUtil.getPackagePath("a-package/a-module"));

		Assert.assertEquals(
			"a-module.js",
			ModuleNameUtil.getPackagePath("a-package/a-module.js"));

		Assert.assertEquals(
			"a-folder/a-module",
			ModuleNameUtil.getPackagePath("a-package/a-folder/a-module"));

		Assert.assertEquals(
			"a-folder/a-module.js",
			ModuleNameUtil.getPackagePath("a-package/a-folder/a-module.js"));

		Assert.assertNull(ModuleNameUtil.getPackagePath("./a-module"));
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

	@Test
	public void testToModuleName() {
		Assert.assertEquals(
			"a-module", ModuleNameUtil.toModuleName("a-module"));

		Assert.assertEquals(
			"a-module", ModuleNameUtil.toModuleName("a-module.js"));

		Assert.assertEquals(
			"a-package/a-module",
			ModuleNameUtil.toModuleName("a-package/a-module"));

		Assert.assertEquals(
			"a-package/a-module",
			ModuleNameUtil.toModuleName("a-package/a-module.js"));

		Assert.assertEquals(
			"a-package/a-folder/a-module",
			ModuleNameUtil.toModuleName("a-package/a-folder/a-module"));

		Assert.assertEquals(
			"a-package/a-folder/a-module",
			ModuleNameUtil.toModuleName("a-package/a-folder/a-module.js"));
	}

	@Test
	public void testToModuleNameWithNonjsExtensions() {
		Assert.assertEquals(
			"a-module.es", ModuleNameUtil.toModuleName("a-module.es"));

		Assert.assertEquals(
			"a-folder/a-module.es",
			ModuleNameUtil.toModuleName("a-folder/a-module.es"));
	}

	@Test
	public void testToModuleNameWithRelativePaths() {
		Assert.assertEquals(
			"./a-module", ModuleNameUtil.toModuleName("./a-module"));

		Assert.assertEquals(
			"./a-module", ModuleNameUtil.toModuleName("./a-module.js"));

		Assert.assertEquals(
			"../a-module", ModuleNameUtil.toModuleName("../a-module"));

		Assert.assertEquals(
			"../a-module", ModuleNameUtil.toModuleName("../a-module.js"));
	}

}