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

import com.liferay.poshi.runner.logger.CommandLoggerHandler;
import com.liferay.poshi.runner.logger.LoggerUtil;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 */
@RunWith(Parameterized.class)
public class PoshiRunner {

	@Parameters(name = "{0}")
	public static List<String> getList() throws Exception {
		PoshiRunnerContext.readFiles();

		PoshiRunnerValidation.validate();

		List<String> classCommandNames = new ArrayList<>();

		String testName = PropsValues.TEST_NAME;

		if (testName.contains("#")) {
			classCommandNames.add(testName);
		}
		else {
			String className = testName;

			Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
				className);

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				classCommandNames.add(
					className + "#" + commandElement.attributeValue("name"));
			}
		}

		return classCommandNames;
	}

	public PoshiRunner(String classCommandName) throws Exception {
		System.out.println();
		System.out.println("###");
		System.out.println("### " + classCommandName);
		System.out.println("###");
		System.out.println();

		_testClassCommandName = classCommandName;
		_testClassName = PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
			_testClassCommandName);

		PoshiRunnerContext.setTestCaseCommandName(_testClassCommandName);
		PoshiRunnerContext.setTestCaseName(_testClassName);

		LoggerUtil.startLogger();

		SeleniumUtil.startSelenium();
	}

	@Test
	public void test() throws Exception {
		try {
			_runSetUp();

			_runCommand();
		}
		catch (Exception e) {
			PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			throw new Exception(e.getMessage(), e);
		}
		finally {
			try {
				_runTearDown();
			}
			catch (Exception e) {
				PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

				PoshiRunnerStackTraceUtil.emptyStackTrace();
			}
		}
	}

	private void _runClassCommandName(String classCommandName)
		throws Exception {

		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			_testClassName);

		List<Element> varElements = rootElement.elements("var");

		for (Element varElement : varElements) {
			String name = varElement.attributeValue("name");
			String value = varElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		Element commandElement = PoshiRunnerContext.getTestCaseCommandElement(
			classCommandName);

		if (commandElement != null) {
			PoshiRunnerStackTraceUtil.pushFilePath(
				classCommandName, "test-case");

			PoshiRunnerExecutor.parseElement(commandElement);

			PoshiRunnerStackTraceUtil.popFilePath();
		}
	}

	private void _runCommand() throws Exception {
		CommandLoggerHandler.logClassCommandName(_testClassCommandName);

		_runClassCommandName(_testClassCommandName);
	}

	private void _runSetUp() throws Exception {
		CommandLoggerHandler.logClassCommandName(_testClassName + "#set-up");

		_runClassCommandName(_testClassName + "#set-up");
	}

	private void _runTearDown() throws Exception {
		try {
			CommandLoggerHandler.logClassCommandName(
				_testClassName + "#tear-down");

			_runClassCommandName(_testClassName + "#tear-down");
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			LoggerUtil.stopLogger();

			SeleniumUtil.stopSelenium();
		}
	}

	private final String _testClassCommandName;
	private final String _testClassName;

}