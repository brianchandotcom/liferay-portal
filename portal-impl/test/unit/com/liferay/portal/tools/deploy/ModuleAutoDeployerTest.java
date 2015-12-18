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
	public BaseDeployer getDeployer() {
		return new ModuleAutoDeployer();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		PropsUtil.removeProperties(PropsUtil.getProperties());

		String moduleFrameworkAutoDeployDirs = MessageFormat.format(
			"{0},{1}", getConfigsDir(), getModulesDir());

		PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS,
			moduleFrameworkAutoDeployDirs);
	}

	@Test
	public void testDeployJARToModules() throws Exception {
		BaseDeployer baseDeployer = getDeployer();

		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		File jarFile = File.createTempFile("some", ".jar");

		autoDeploymentContext.setFile(jarFile);

		baseDeployer.deployFile(autoDeploymentContext);

		File deployedJarFile = new File(getModulesDir(), jarFile.getName());

		assertTrue(deployedJarFile.exists());
	}

	protected File getConfigsDir() {
		return new File(getRootDir(), "configs");
	}

	protected File getModulesDir() {
		return new File(getRootDir(), "modules");
	}

}