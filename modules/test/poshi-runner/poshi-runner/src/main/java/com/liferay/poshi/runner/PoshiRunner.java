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
import com.liferay.poshi.runner.logger.SummaryLoggerHandler;
import com.liferay.poshi.runner.logger.XMLLoggerHandler;
import com.liferay.poshi.runner.selenium.LiferaySeleniumHelper;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 * @author Leslie Wong
 */
@RunWith(Parameterized.class)
public class PoshiRunner {

	@Parameters(name = "{0}")
	public static List<String> getList() throws Exception {
		PoshiRunnerContext.readFiles();

		List<String> namespacedClassCommandNames = new ArrayList<>();

		List<String> testNames = Arrays.asList(
			PropsValues.TEST_NAME.split("\\s*,\\s*"));

		for (String testName : testNames) {
			PoshiRunnerValidation.validate(testName);

			String namespace =
				PoshiRunnerGetterUtil.
					getNamespaceFromNamespacedClassCommandName(testName);

			if (testName.contains("#")) {
				String classCommandName =
					PoshiRunnerGetterUtil.
						getClassCommandNameFromNamespacedClassCommandName(
							testName);

				namespacedClassCommandNames.add(
					namespace + "." + classCommandName);
			}
			else {
				String className =
					PoshiRunnerGetterUtil.
						getClassNameFromNamespacedClassCommandName(testName);

				Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
					className, namespace);

				List<Element> commandElements = rootElement.elements("command");

				for (Element commandElement : commandElements) {
					namespacedClassCommandNames.add(
						namespace + "." + className + "#" +
							commandElement.attributeValue("name"));
				}
			}
		}

		return namespacedClassCommandNames;
	}

	public PoshiRunner(String namespacedClassCommandName) throws Exception {
		_testNamespacedClassCommandName = namespacedClassCommandName;

		_testNamespacedClassName =
			PoshiRunnerGetterUtil.
				getNamespacedClassNameFromNamespacedClassCommandName(
					_testNamespacedClassCommandName);
	}

	@Before
	public void setUp() throws Exception {
		System.out.println();
		System.out.println("###");
		System.out.println("### " + _testNamespacedClassCommandName);
		System.out.println("###");
		System.out.println();

		PoshiRunnerContext.setTestCaseNamespacedClassCommandName(
			_testNamespacedClassCommandName);

		PoshiRunnerVariablesUtil.clear();

		try {
			XMLLoggerHandler.generateXMLLog(_testNamespacedClassCommandName);

			LoggerUtil.startLogger();

			SeleniumUtil.startSelenium();

			_runSetUp();
		}
		catch (WebDriverException wde) {
			wde.printStackTrace();

			throw wde;
		}
		catch (Exception e) {
			LiferaySeleniumHelper.printJavaProcessStacktrace();

			PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			e.printStackTrace();

			throw e;
		}
	}

	@After
	public void tearDown() throws Exception {
		LiferaySeleniumHelper.writePoshiWarnings();

		LoggerUtil.createSummary();

		try {
			if (!PropsValues.TEST_SKIP_TEAR_DOWN) {
				_runTearDown();
			}
		}
		catch (Exception e) {
			PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

			PoshiRunnerStackTraceUtil.emptyStackTrace();
		}
		finally {
			LoggerUtil.stopLogger();

			SeleniumUtil.stopSelenium();
		}
	}

	@Test
	public void test() throws Exception {
		try {
			_runCommand();

			LiferaySeleniumHelper.assertNoPoshiWarnings();
		}
		catch (Exception e) {
			LiferaySeleniumHelper.printJavaProcessStacktrace();

			PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			e.printStackTrace();

			throw e;
		}
	}

	@Rule
	public RetryTestRule retryTestRule = new RetryTestRule();

	private void _runCommand() throws Exception {
		CommandLoggerHandler.logNamespacedClassCommandName(
			_testNamespacedClassCommandName);

		_runNamespacedClassCommandName(_testNamespacedClassCommandName);
	}

	private void _runNamespacedClassCommandName(
			String namespacedClassCommandName)
		throws Exception {

		String className =
			PoshiRunnerGetterUtil.getClassNameFromNamespacedClassCommandName(
				namespacedClassCommandName);
		String namespace =
			PoshiRunnerGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		List<Element> varElements = PoshiRunnerContext.getRootVarElements(
			"test-case", className, namespace);

		for (Element varElement : varElements) {
			PoshiRunnerExecutor.runRootVarElement(varElement, false);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		String classCommandName =
			PoshiRunnerGetterUtil.
				getClassCommandNameFromNamespacedClassCommandName(
					namespacedClassCommandName);

		Element commandElement = PoshiRunnerContext.getTestCaseCommandElement(
			classCommandName, namespace);

		if (commandElement != null) {
			PoshiRunnerStackTraceUtil.startStackTrace(
				namespacedClassCommandName, "test-case");

			XMLLoggerHandler.updateStatus(commandElement, "pending");

			PoshiRunnerExecutor.parseElement(commandElement);

			XMLLoggerHandler.updateStatus(commandElement, "pass");

			PoshiRunnerStackTraceUtil.emptyStackTrace();
		}
	}

	private void _runSetUp() throws Exception {
		CommandLoggerHandler.logNamespacedClassCommandName(
			_testNamespacedClassName + "#set-up");

		SummaryLoggerHandler.startMajorSteps();

		_runNamespacedClassCommandName(_testNamespacedClassName + "#set-up");
	}

	private void _runTearDown() throws Exception {
		CommandLoggerHandler.logNamespacedClassCommandName(
			_testNamespacedClassName + "#tear-down");

		SummaryLoggerHandler.startMajorSteps();

		_runNamespacedClassCommandName(_testNamespacedClassName + "#tear-down");
	}

	private final String _testNamespacedClassCommandName;
	private final String _testNamespacedClassName;

	private class RetryTestRule implements TestRule {

		public Statement apply(Statement statement, Description description) {
			return new RetryStatement(statement);
		}

		public class RetryStatement extends Statement {

			public RetryStatement(Statement statement) {
				_statement = statement;
			}

			@Override
			public void evaluate() throws Throwable {
				for (int i = 0; i <= _MAX_RETRY_COUNT; i++) {
					try {
						_statement.evaluate();

						return;
					}
					catch (Throwable t) {
						if ((i == _MAX_RETRY_COUNT) ||
							!_isValidRetryThrowable(t)) {

							throw t;
						}
					}
				}
			}

			private String _getShortMessage(Throwable throwable) {
				String message = throwable.getMessage();

				if (throwable instanceof WebDriverException) {
					int index = message.indexOf("Build info:");

					message = message.substring(0, index);

					message = message.trim();
				}

				return message;
			}

			private boolean _isValidRetryThrowable(Throwable throwable) {
				List<Throwable> throwables = null;

				if (throwable instanceof MultipleFailureException) {
					MultipleFailureException mfe =
						(MultipleFailureException)throwable;

					throwables = mfe.getFailures();
				}
				else {
					throwables = Arrays.asList(throwable);
				}

				for (Throwable validRetryThrowable : _validRetryThrowables) {
					Class<?> validRetryThrowableClass =
						validRetryThrowable.getClass();
					String validRetryThrowableShortMessage = _getShortMessage(
						validRetryThrowable);

					for (Throwable t : throwables) {
						if (validRetryThrowableClass.equals(t.getClass())) {
							if ((validRetryThrowableShortMessage == null) ||
								validRetryThrowableShortMessage.isEmpty()) {

								return true;
							}

							if (validRetryThrowableShortMessage.equals(
									_getShortMessage(t))) {

								return true;
							}
						}
					}
				}

				return false;
			}

			private static final int _MAX_RETRY_COUNT = 2;

			private final Statement _statement;
			private final Throwable[] _validRetryThrowables = {
				new TimeoutException(), new UnreachableBrowserException(null),
				new WebDriverException(
					"Timed out waiting 45 seconds for Firefox to start.")
			};

		}

	}

}