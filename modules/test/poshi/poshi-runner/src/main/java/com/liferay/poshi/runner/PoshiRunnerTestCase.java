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

package com.liferay.poshi.runner;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiValidation;
import com.liferay.poshi.core.util.PropsUtil;
import com.liferay.poshi.runner.selenium.WebDriverUtil;

import java.io.File;

import java.util.Properties;

import junit.framework.TestCase;

/**
 * @author Kenji Heigel
 */
public abstract class PoshiRunnerTestCase extends TestCase {

	public void runPoshiTest(String testName) throws Exception {
		PoshiRunner poshiRunner = new PoshiRunner("LocalFile." + testName);

		poshiRunner.setUp();

		poshiRunner.test();

		WebDriverUtil.stopWebDriver(
			PoshiContext.getDefaultNamespace() + "." + testName);
	}

	public void setUpPoshiRunner(String testBaseDirName) throws Exception {
		File testBaseDir = new File(testBaseDirName);

		if (!testBaseDir.exists()) {
			throw new RuntimeException(
				"Test directory does not exist: " + testBaseDirName);
		}

		Properties properties = new Properties();

		properties.setProperty("test.base.dir.name", testBaseDirName);

		PropsUtil.setProperties(properties);

		PoshiContext.clear();

		PoshiContext.readFiles();

		PoshiValidation.validate();
	}

}