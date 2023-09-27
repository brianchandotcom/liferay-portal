/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core;

import com.liferay.poshi.core.selenium.LiferaySeleniumMethod;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;

import org.dom4j.Element;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiContextTest extends TestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		String[] poshiFileNames = ArrayUtils.addAll(
			PoshiContext.POSHI_SUPPORT_FILE_INCLUDES,
			PoshiContext.POSHI_TEST_FILE_INCLUDES);

		String poshiFileDir =
			"src/test/resources/com/liferay/poshi/core/dependencies/test";

		PropsUtil.clear();

		PropsUtil.set("test.base.dir.name", poshiFileDir);

		PoshiContext.readFiles(true, poshiFileNames, poshiFileDir);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		PoshiContext.clear();
	}

	@Test
	public void testGetFilePath() throws Exception {
		String actualFilePath = PoshiContext.getFilePathFromFileName(
			"Macro.macro", PoshiContext.getDefaultNamespace());

		String baseDirName = FileUtil.getCanonicalPath(
			"src/test/resources/com/liferay/poshi/core/");

		File file = new File(baseDirName, "/dependencies/test/Macro.macro");

		String expectedFilePath = file.getCanonicalPath();

		Assert.assertEquals(
			"getFilePath is failing", expectedFilePath, actualFilePath);
	}

	@Test
	public void testGetFunctionCommandElement() throws Exception {
		Element element = PoshiContext.getFunctionCommandElement(
			"Click#clickAt", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionCommandElement is failing", "clickAt",
			element.attributeValue("name"));
	}

	@Test
	public void testGetFunctionLocatorCount() throws Exception {
		int locatorCount = PoshiContext.getFunctionLocatorCount(
			"Click", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionLocatorCount is failing", 1, locatorCount);
	}

	@Test
	public void testGetFunctionRootElement() {
		Element element = PoshiContext.getFunctionRootElement(
			"Click", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionRootElement is failing", "definition",
			element.getName());
	}

	@Test
	public void testGetMacroCommandElement() {
		Element element = PoshiContext.getMacroCommandElement(
			"Macro#test", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getMacroCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetPathLocator() throws Exception {
		String locator = PoshiContext.getPathLocator(
			"Action1#TEST_TITLE", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getPathLocator is failing", "//input[@class='Title']", locator);

		locator = PoshiContext.getPathLocator(
			"Action1#TEST_CONTENT", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getPathLocator is failing", "//input[@class='Content']", locator);
	}

	@Test
	public void testGetSeleniumParameterCount() {
		LiferaySeleniumMethod seleniumMethod =
			PoshiContext.getLiferaySeleniumMethod("clickAt");

		int count = seleniumMethod.getParameterCount();

		Assert.assertEquals("getSeleniumParameterCount is failing", 2, count);

		seleniumMethod = PoshiContext.getLiferaySeleniumMethod("click");

		count = seleniumMethod.getParameterCount();

		Assert.assertEquals("getSeleniumParameterCount is failing", 1, count);
	}

	@Test
	public void testGetTestCaseCommandElement() {
		Element element = PoshiContext.getTestCaseCommandElement(
			"Test#test", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getTestCaseCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetTestCaseRootElement() {
		Element element = PoshiContext.getTestCaseRootElement(
			"Test", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getTestCaseRootElement is failing", "definition",
			element.getName());
	}

}