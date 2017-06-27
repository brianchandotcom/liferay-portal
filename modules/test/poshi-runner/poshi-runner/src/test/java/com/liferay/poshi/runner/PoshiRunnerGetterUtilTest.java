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

import java.net.URL;

import junit.framework.TestCase;

import org.dom4j.Element;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerGetterUtilTest extends TestCase {

	@Test
	public void testGetClassNameFromClassCommandName() {
		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				"PortalSmoke#Smoke");

		Assert.assertEquals(
			"getClassNameFromClassCommandName is failing", "PortalSmoke",
			className);
	}

	@Test
	public void testGetClassNameFromFilePath() {
		String className = PoshiRunnerGetterUtil.getClassNameFromFilePath(
			"/com/liferay/poshi/runner/dependencies/Test.testcase");

		Assert.assertEquals(
			"getClassNameFromFilePath is failing", "Test", className);
	}

	@Test
	public void testGetClassTypeFromFilePath() {
		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			"/com/liferay/poshi/runner/dependencies/Test.testcase");

		Assert.assertEquals(
			"getClassTypeFromFilePath is failing", "test-case", classType);
	}

	@Test
	public void testGetCommandNameFromClassCommandName() {
		String commandName =
			PoshiRunnerGetterUtil.getCommandNameFromClassCommandName(
				"MathUtil#add(1,2)");

		Assert.assertEquals(
			"getCommandNameFromClassCommandName is failing", "add",
			commandName);

		commandName = PoshiRunnerGetterUtil.getCommandNameFromClassCommandName(
			"Page#addPG");

		Assert.assertEquals(
			"getCommandNameFromClassCommandName is failing", "addPG",
			commandName);
	}

	@Test
	public void testGetRootElementFromURL() throws Exception {
		URL url = new URL(
			"file:src/test/resources/com/liferay/poshi/runner/dependencies" +
				"/Test.testcase");

		Element rootElement = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		Assert.assertEquals(
			"getRootElementFromURL is failing", "definition",
			rootElement.getName());
	}

	@Test
	public void testGetVarMethodValue() throws Exception {
		Object value = PoshiRunnerGetterUtil.getVarMethodValue(
			"MathUtil#quotient('3', '1')");

		Assert.assertEquals(
			"getVarMethodValue is failing", "3", value.toString());

		value = PoshiRunnerGetterUtil.getVarMethodValue(
			"StringUtil#endsWith('The fox jumped over the dog', 'dog')");

		Assert.assertEquals("getVarMethodValue is failing", true, value);
	}

}