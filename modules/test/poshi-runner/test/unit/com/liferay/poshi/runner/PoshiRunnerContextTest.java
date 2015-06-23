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

import java.util.List;

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
	public void testGetActionCaseElements() {
		List<Element> elements = PoshiRunnerContext.getActionCaseElements(
			"Action1#test2");

		for (Element element : elements) {
			Assert.assertEquals(
				"getActionCaseElements is failing", "/test2",
				element.attributeValue("locator1"));
		}
	}

	@Test
	public void testGetActionCommandElement() {
		Element element = PoshiRunnerContext.getActionCommandElement(
			"Action1#test1");

		Assert.assertEquals(
			"getActionCommandElement is failing", "test1",
			element.attributeValue("name"));
	}

	@Test
	public void testGetActionLocatorCount() throws Exception {
		int locatorCount = PoshiRunnerContext.getActionLocatorCount(
			"Action1#type");

		Assert.assertEquals(
			"getActionLocatorCount is failing", 1, locatorCount);
	}

	@Test
	public void testGetActionRootElement() {
		Element rootElement = PoshiRunnerContext.getActionRootElement(
			"Action1");

		Assert.assertEquals(
			"getActionRootElement is failing", "definition",
			rootElement.getName());
	}

	@Test
	public void testGetFilePath() throws Exception {
		String actualFilePath = PoshiRunnerContext.getFilePathFromFileName(
			"Action2.action");

		String baseDirName = PoshiRunnerGetterUtil.getCanonicalPath(
			"test/unit/com/liferay/poshi/runner/");

		File file = new File(baseDirName + "/dependencies/Action2.action");

		String expectedFilePath = file.getCanonicalPath();

		Assert.assertEquals(
			"getFilePath is failing", expectedFilePath, actualFilePath);
	}

	@Test
	public void testGetFunctionCommandElement() throws Exception {
		Element element = PoshiRunnerContext.getFunctionCommandElement(
			"Click#clickAt");

		Assert.assertEquals(
			"getFunctionCommandElement is failing", "clickAt",
			element.attributeValue("name"));
	}

	@Test
	public void testGetFunctionLocatorCount() throws Exception {
		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount("Click");

		Assert.assertEquals(
			"getFunctionLocatorCount is failing", 1, locatorCount);
	}

	@Test
	public void testGetFunctionRootElement() {
		Element element = PoshiRunnerContext.getFunctionRootElement("Click");

		Assert.assertEquals(
			"getFunctionRootElement is failing", "definition",
			element.getName());
	}

	@Test
	public void testGetMacroCommandElement() {
		Element element = PoshiRunnerContext.getMacroCommandElement(
			"Macro#test");

		Assert.assertEquals(
			"getMacroCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetPathLocator() {
		String locator = PoshiRunnerContext.getPathLocator(
			"Action1#TEST_TITLE");

		Assert.assertEquals(
			"getPathLocator is failing", "//input[@class='Title']", locator);

		locator = PoshiRunnerContext.getPathLocator("Action1#TEST_CONTENT");

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
			"Test#test");

		Assert.assertEquals(
			"getTestCaseCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetTestCaseRootElement() {
		Element element = PoshiRunnerContext.getTestCaseRootElement("Test");

		Assert.assertEquals(
			"getTestCaseRootElement is failing", "definition",
			element.getName());
	}

}