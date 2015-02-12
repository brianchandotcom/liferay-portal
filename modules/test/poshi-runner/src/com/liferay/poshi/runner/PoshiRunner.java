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

import com.liferay.poshi.runner.selenium.SeleniumUtil;

import java.util.List;

import junit.framework.TestCase;

import org.dom4j.Element;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 */
public class PoshiRunner extends TestCase {

	@Override
	public void setUp() throws Exception {
		SeleniumUtil.startSelenium();

		Element testcaseRootElement = PoshiRunnerContext.getTestcaseRootElement(
			_CLASS_NAME);

		List<Element> rootVarElements = testcaseRootElement.elements("var");

		for (Element rootVarElement : rootVarElements) {
			String name = rootVarElement.attributeValue("name");
			String value = rootVarElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		Element setupElement = PoshiRunnerContext.getTestcaseCommandElement(
			_CLASS_NAME + "#set-up");

		if (setupElement != null) {
			PoshiRunnerExecutor.parseElement(setupElement);
		}
	}

	@Override
	public void tearDown() throws Exception {
		Element tearDownElement = PoshiRunnerContext.getTestcaseCommandElement(
			_CLASS_NAME + "#tear-down");

		if (tearDownElement != null) {
			PoshiRunnerExecutor.parseElement(tearDownElement);
		}

		SeleniumUtil.stopSelenium();
	}

	public void testPoshiRunner() throws Exception {
		Element commandElement = PoshiRunnerContext.getTestcaseCommandElement(
			_CLASS_COMMAND_NAME);

		PoshiRunnerExecutor.parseElement(commandElement);
	}

	private static final String _CLASS_COMMAND_NAME = System.getProperty(
		"test.case.name");

	private static final String _CLASS_NAME =
		PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
			_CLASS_COMMAND_NAME);

}