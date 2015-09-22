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

package com.liferay.deployment.helper;

import java.io.File;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class DeploymentHelperTest {

	@Test
	public void testDeploymentHelper() throws Exception {
		Class<?> clazz = this.getClass();

		URL deploymentFiles = clazz.getResource("dependencies/license.xml");

		String deploymentPath = "/home";
		String outputFile =
			System.getProperty("user.dir") + "/deployment-helper-web.war";

		DeploymentHelper deploymentHelper = new DeploymentHelper(
			deploymentFiles.getFile(), deploymentPath, outputFile);
		deploymentHelper.run();

		File file = new File(outputFile);
		file.deleteOnExit();

		Assert.assertTrue(file.exists());
	}

}