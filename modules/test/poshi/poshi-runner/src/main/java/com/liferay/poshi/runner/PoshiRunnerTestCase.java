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
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.selenium.SeleniumUtil;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;

import org.junit.After;

/**
 * @author Kenji Heigel
 */
public abstract class PoshiRunnerTestCase extends TestCase {

	public void runPoshiTest(String testName) throws Exception {
		PoshiRunner poshiRunner = new PoshiRunner("LocalFile." + testName);

		poshiRunner.setUp();

		poshiRunner.test();
	}

	public void setUpPoshiRunner(String testBaseDirName) throws Exception {
		File testBaseDir = new File(testBaseDirName);

		if (!testBaseDir.exists()) {
			throw new RuntimeException(
				"Test directory does not exist: " + testBaseDirName);
		}

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			PropsUtil.set(
				"browser.firefox.bin.file", "/opt/firefox-52.0.2esr/firefox");
		}

		String[] poshiFileNames = ArrayUtils.addAll(
			PoshiContext.POSHI_SUPPORT_FILE_INCLUDES,
			PoshiContext.POSHI_TEST_FILE_INCLUDES);

		PoshiContext.clear();

		PoshiContext.readFiles(poshiFileNames, testBaseDirName);

		PoshiValidation.validate();
	}

	@After
	@Override
	public void tearDown() {
		SeleniumUtil.stopSelenium();
	}

}