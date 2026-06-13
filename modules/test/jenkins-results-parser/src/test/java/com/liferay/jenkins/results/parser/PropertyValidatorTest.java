/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import org.junit.Assume;
import org.junit.Test;

/**
 * @author Brittney Nguyen
 */
public class PropertyValidatorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testValidate() throws Exception {
		File jenkinsRepositoryDir =
			JenkinsResultsParserUtil.getJenkinsRepositoryDir();

		File commandsDir = new File(jenkinsRepositoryDir, "commands");

		Assume.assumeTrue(
			JenkinsResultsParserUtil.getCanonicalPath(commandsDir) +
				" does not exist",
			commandsDir.isDirectory());

		PropertyValidator.ValidationResult validationResult =
			PropertyValidator.validate(
				jenkinsRepositoryDir, new File("src/main/java"));

		for (PropertyValidator.ConsumedKeyFailure consumedKeyFailure :
				validationResult.getConsumedKeyFailures()) {

			errorCollector.addError(
				new Throwable(consumedKeyFailure.getMessage()));
		}
	}

}