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

import java.io.File;

import junit.framework.TestCase;

import org.dom4j.Element;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerContextTest extends TestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		PoshiRunnerContext.readFiles();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		PoshiRunnerContext.clear();
	}

	@Test
	public void testGetFilePath() throws Exception {
		String actualFilePath = PoshiRunnerContext.getFilePathFromFileName(
			"Action2.action", PoshiRunnerContext.getDefaultNamespace());

		String baseDirName = PoshiRunnerGetterUtil.getCanonicalPath(
			"src/test/resources/com/liferay/poshi/runner/");

		File file = new File(baseDirName + "/dependencies/Action2.action");

		String expectedFilePath = file.getCanonicalPath();

		Assert.assertEquals(
			"getFilePath is failing", expectedFilePath, actualFilePath);
	}

	@Test
	public void testGetFunctionCommandElement() throws Exception {
		Element element = PoshiRunnerContext.getFunctionCommandElement(
			"Click#clickAt", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionCommandElement is failing", "clickAt",
			element.attributeValue("name"));
	}

	@Test
	public void testGetFunctionLocatorCount() throws Exception {
		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			"Click", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionLocatorCount is failing", 1, locatorCount);
	}

	@Test
	public void testGetFunctionRootElement() {
		Element element = PoshiRunnerContext.getFunctionRootElement(
			"Click", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionRootElement is failing", "definition",
			element.getName());
	}

	@Test
	public void testGetMacroCommandElement() {
		Element element = PoshiRunnerContext.getMacroCommandElement(
			"Macro#test", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getMacroCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetPathLocator() throws Exception {
		String locator = PoshiRunnerContext.getPathLocator(
			"Action1#TEST_TITLE", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getPathLocator is failing", "//input[@class='Title']", locator);

		locator = PoshiRunnerContext.getPathLocator(
			"Action1#TEST_CONTENT", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getPathLocator is failing", "//input[@class='Content']", locator);
	}

	@Test
	public void testGetSeleniumParameterCount() {
		int count = PoshiRunnerContext.getSeleniumParameterCount("clickAt");

		Assert.assertEquals("getSeleniumParameterCount is failing", 2, count);

		count = PoshiRunnerContext.getSeleniumParameterCount("click");

		Assert.assertEquals("getSeleniumParameterCount is failing", 1, count);
	}

	@Test
	public void testGetTestCaseCommandElement() {
		Element element = PoshiRunnerContext.getTestCaseCommandElement(
			"Test#test", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getTestCaseCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetTestCaseRootElement() {
		Element element = PoshiRunnerContext.getTestCaseRootElement(
			"Test", PoshiRunnerContext.getDefaultNamespace());

		Assert.assertEquals(
			"getTestCaseRootElement is failing", "definition",
			element.getName());
	}

}