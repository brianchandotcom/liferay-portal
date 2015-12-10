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

package com.liferay.portal.tools.deploy;

import static org.junit.Assert.assertTrue;

import com.liferay.portal.deploy.auto.ModuleAutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.io.File;
import java.text.MessageFormat;

import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class ModuleAutoDeployerTest extends BaseDeployerTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		PropsUtil.removeProperties(PropsUtil.getProperties());

		String moduleFrameworkAutoDeployDirs =
			MessageFormat.format("{0},{1}", getConfigsDir(), getModulesDir());

		PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS,
			moduleFrameworkAutoDeployDirs);
	}

	protected File getConfigsDir() {
		return new File(getRootDir(), "configs");
	}

	@Override
	public BaseDeployer getDeployer() {
		return new ModuleAutoDeployer();
	}

	protected File getModulesDir() {
		return new File(getRootDir(), "modules");
	}

	@Test
	public void testDeployModuleFile() throws Exception {
		File moduleFile = File.createTempFile("module", ".jar");

		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		autoDeploymentContext.setFile(moduleFile);

		getDeployer().deployFile(autoDeploymentContext);

		assertTrue(new File(getModulesDir(), moduleFile.getName()).exists());
	}

	@Test
	public void testDeployWarFile() throws Exception {
		File warFile = File.createTempFile("web", ".war");

		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		autoDeploymentContext.setFile(warFile);

		getDeployer().deployFile(autoDeploymentContext);

		assertTrue(new File(getModulesDir(), warFile.getName()).exists());
	}
}