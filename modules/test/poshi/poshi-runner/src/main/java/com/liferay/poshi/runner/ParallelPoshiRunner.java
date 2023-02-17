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

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.PoshiValidation;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.runner.junit.ParallelParameterized;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

/**
 * @author Kenji Heigel
 */
@RunWith(ParallelParameterized.class)
public class ParallelPoshiRunner extends PoshiRunner {

	@AfterClass
	public static void evaluateResults() throws IOException {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, List<String>> testResult :
				_testResults.entrySet()) {

			List<String> testResultMessages = testResult.getValue();

			if (testResultMessages.size() == 1) {
				continue;
			}

			int passes = Collections.frequency(testResultMessages, "PASS");

			int failures = testResultMessages.size() - passes;

			if ((passes > 0) && (failures > 0)) {
				sb.append("\n");
				sb.append(testResult.getKey());
			}
		}

		if (sb.length() != 0) {
			FileUtil.write(
				FileUtil.getCanonicalPath(".") + "/test-results/flaky-tests",
				sb.toString());
		}
	}

	@ParallelParameterized.Parameters(name = "{0}")
	public static List<String> getList() throws Exception {
		List<String> namespacedClassCommandNames = new ArrayList<>();

		List<String> testNames = Arrays.asList(
			PropsValues.TEST_NAME.split("\\s*,\\s*"));

		PoshiContext.readFiles(false);

		PoshiValidation.validate();

		for (String testName : testNames) {
			PoshiValidation.validate(testName);

			String namespace =
				PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
					testName);

			if (testName.contains("#")) {
				String classCommandName =
					PoshiGetterUtil.
						getClassCommandNameFromNamespacedClassCommandName(
							testName);

				namespacedClassCommandNames.add(
					namespace + "." + classCommandName);
			}
			else {
				String className =
					PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
						testName);

				Element rootElement = PoshiContext.getTestCaseRootElement(
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

	public ParallelPoshiRunner(String namespacedClassCommandName)
		throws Exception {

		super(namespacedClassCommandName);
	}

	private static final Map<String, List<String>> _testResults =
		new HashMap<>();

}