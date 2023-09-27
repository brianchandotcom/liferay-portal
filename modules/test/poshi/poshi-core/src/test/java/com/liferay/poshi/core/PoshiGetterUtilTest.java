/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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