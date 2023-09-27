/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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