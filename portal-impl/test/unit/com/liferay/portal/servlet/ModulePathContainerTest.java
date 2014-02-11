/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.StringPool;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Carlos Sierra Andrés
 */
public class ModulePathContainerTest {

	@Test
	public void testModulePathWithNoContext() {
		ComboServlet.ModulePathContainer modulePathContainer =
			new ComboServlet.ModulePathContainer("/js/javascript.js");

		Assert.assertEquals(
			StringPool.BLANK, modulePathContainer.getModuleContextPath());

		Assert.assertEquals(
			"/js/javascript.js", modulePathContainer.getResourcePath());
	}

	@Test
	public void testModulePathWithPluginContext() {
		ComboServlet.ModulePathContainer modulePathContainer =
			new ComboServlet.ModulePathContainer(
				"plugin-context:/js/javascript.js");

		Assert.assertEquals(
			"plugin-context", modulePathContainer.getModuleContextPath());

		Assert.assertEquals(
			"/js/javascript.js", modulePathContainer.getResourcePath());
	}

	@Test
	public void testModulePathWithPluginContextAndNoResource() {
		ComboServlet.ModulePathContainer modulePathContainer =
			new ComboServlet.ModulePathContainer("/plugin-context:");

		Assert.assertEquals(
			"/plugin-context", modulePathContainer.getModuleContextPath());

		Assert.assertEquals(
			StringPool.BLANK, modulePathContainer.getResourcePath());
	}

	@Test
	public void testModulePathWithSlashPluginContext() {
		ComboServlet.ModulePathContainer modulePathContainer =
			new ComboServlet.ModulePathContainer(
				"/plugin-context:/js/javascript.js");

		Assert.assertEquals(
			"/plugin-context", modulePathContainer.getModuleContextPath());

		Assert.assertEquals(
			"/js/javascript.js", modulePathContainer.getResourcePath());
	}

}