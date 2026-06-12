/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.List;

import org.junit.Assume;
import org.junit.Test;

/**
 * @author Brittney Nguyen
 */
public class PropertyValidationTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testPropertyConsumption() throws Exception {
		File jenkinsRepositoryDir =
			PropertyValidationUtil.resolveJenkinsEEDir();

		File commandsDir = new File(jenkinsRepositoryDir, "commands");

		Assume.assumeTrue(
			JenkinsResultsParserUtil.combine(
				"Skip property validation because ",
				JenkinsResultsParserUtil.getCanonicalPath(jenkinsRepositoryDir),
				" does not exist."),
			commandsDir.isDirectory());

		PropertyValidationUtil.PropertyValidationResult
			propertyValidationResult = PropertyValidationUtil.validate(
				jenkinsRepositoryDir, new File("src/main/java"));

		List<PropertyValidationUtil.ConsumptionFailure> consumptionFailures =
			propertyValidationResult.getConsumptionFailures();
		List<String> unconsumedKeys =
			propertyValidationResult.getUnconsumedKeys();

		for (PropertyValidationUtil.ConsumptionFailure consumptionFailure :
				consumptionFailures) {

			errorCollector.addError(
				new Throwable(consumptionFailure.getMessage()));
		}

		for (String unconsumedKey : unconsumedKeys) {
			System.out.println(
				"WARNING: defined but never consumed: " + unconsumedKey);
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Property validation found ",
				String.valueOf(consumptionFailures.size()), " failure(s) and ",
				String.valueOf(unconsumedKeys.size()),
				" unconsumed-key warning(s)."));
	}

}