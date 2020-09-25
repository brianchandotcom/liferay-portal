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

package com.liferay.poshi.core;

import java.net.URL;

import junit.framework.TestCase;

import org.dom4j.Element;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiGetterUtilTest extends TestCase {

	@Test
	public void testGetClassNameFromClassCommandName() {
		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				"PortalSmoke#Smoke");

		Assert.assertEquals(
			"getClassNameFromNamespacedClassCommandName is failing",
			"PortalSmoke", className);
	}

	@Test
	public void testGetClassNameFromFilePath() {
		String className = PoshiGetterUtil.getClassNameFromFilePath(
			"/com/liferay/poshi/core/dependencies/test/Test.testcase");

		Assert.assertEquals(
			"getClassNameFromFilePath is failing", "Test", className);
	}

	@Test
	public void testGetClassTypeFromFilePath() {
		String classType = PoshiGetterUtil.getClassTypeFromFilePath(
			"/com/liferay/poshi/core/dependencies/test/Test.testcase");

		Assert.assertEquals(
			"getClassTypeFromFilePath is failing", "test-case", classType);
	}

	@Test
	public void testGetCommandNameFromClassCommandName() {
		String commandName =
			PoshiGetterUtil.getCommandNameFromNamespacedClassCommandName(
				"Click#clickAt");

		Assert.assertEquals(
			"getCommandNameFromNamespacedClassCommandName is failing",
			"clickAt", commandName);

		commandName =
			PoshiGetterUtil.getCommandNameFromNamespacedClassCommandName(
				"Page#addPG");

		Assert.assertEquals(
			"getCommandNameFromNamespacedClassCommandName is failing", "addPG",
			commandName);
	}

	@Test
	public void testGetRootElementFromURL() throws Exception {
		URL url = new URL(
			"file:src/test/resources/com/liferay/poshi/core/dependencies/test" +
				"/Test.testcase");

		Element rootElement = PoshiGetterUtil.getRootElementFromURL(url);

		Assert.assertEquals(
			"getRootElementFromURL is failing", "definition",
			rootElement.getName());
	}

}