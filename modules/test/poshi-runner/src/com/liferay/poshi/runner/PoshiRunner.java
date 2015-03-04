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
import com.liferay.poshi.runner.util.PropsValues;

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
			_TEST_CLASS_NAME);

		List<Element> rootVarElements = testcaseRootElement.elements("var");

		for (Element rootVarElement : rootVarElements) {
			String name = rootVarElement.attributeValue("name");
			String value = rootVarElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		Element setUpElement = PoshiRunnerContext.getTestcaseCommandElement(
			_TEST_CLASS_NAME + "#set-up");

		if (setUpElement != null) {
			PoshiRunnerStackTraceUtil.pushFilePath(
				_TEST_CLASS_NAME + "#set-up", "testcase",
				setUpElement.attributeValue("line-number"));

			PoshiRunnerExecutor.parseElement(setUpElement);

			PoshiRunnerStackTraceUtil.popFilePath();
		}
	}

	@Override
	public void tearDown() throws Exception {
		Element tearDownElement = PoshiRunnerContext.getTestcaseCommandElement(
			_TEST_CLASS_NAME + "#tear-down");

		if (tearDownElement != null) {
			PoshiRunnerStackTraceUtil.pushFilePath(
				_TEST_CLASS_NAME + "#tear-down", "testcase",
				tearDownElement.attributeValue("line-number"));

			PoshiRunnerExecutor.parseElement(tearDownElement);

			PoshiRunnerStackTraceUtil.popFilePath();
		}

		SeleniumUtil.stopSelenium();
	}

	public void testPoshiRunner() throws Exception {
		Element commandElement = PoshiRunnerContext.getTestcaseCommandElement(
			_TEST_CLASS_COMMAND_NAME);

		PoshiRunnerStackTraceUtil.pushFilePath(
			_TEST_CLASS_COMMAND_NAME, "testcase",
			commandElement.attributeValue("line-number"));

		PoshiRunnerExecutor.parseElement(commandElement);

		PoshiRunnerStackTraceUtil.popFilePath();
	}

	private static final String _TEST_CLASS_COMMAND_NAME =
		PropsValues.TEST_CLASS_COMMAND_NAME;

	private static final String _TEST_CLASS_NAME =
		PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
			_TEST_CLASS_COMMAND_NAME);

}