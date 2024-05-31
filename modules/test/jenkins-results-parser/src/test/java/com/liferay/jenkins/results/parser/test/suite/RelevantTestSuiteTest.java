/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.batch.PlaywrightTestBatch;
import com.liferay.jenkins.results.parser.test.batch.PlaywrightTestSelector;
import com.liferay.jenkins.results.parser.test.batch.TestBatch;

import java.io.File;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class RelevantTestSuiteTest {

	@Test
	public void testPlaywrightTestSelectorMerge() {
		RelevantTestSuite relevantTestSuite = new RelevantTestSuite(
			_baseDir,
			Arrays.asList(
				new File(_baseDir, "modules/module-1/text_file_1.txt"),
				new File(_baseDir, "modules/module-2/text_file_2.txt")));

		PlaywrightTestBatch playwrightTestBatch = null;

		for (TestBatch testBatch : relevantTestSuite.getTestBatches()) {
			if (testBatch instanceof PlaywrightTestBatch) {
				playwrightTestBatch = (PlaywrightTestBatch)testBatch;
			}

			break;
		}

		PlaywrightTestSelector playwrightTestSelector =
			playwrightTestBatch.getTestSelector();

		Set<String> expectedPlaywrightProjectNames = new HashSet<>(
			Arrays.asList(
				"module-1-playwright-project", "module-2-playwright-project"));

		Assert.assertEquals(
			expectedPlaywrightProjectNames,
			playwrightTestSelector.getPlaywrightProjectNames());
	}

	private static final File _baseDir;

	static {
		File baseDir = new File(
			"src/test/resources/dependencies/test/suite" +
				"/RelevantRuleEngineTest");

		_baseDir = JenkinsResultsParserUtil.getCanonicalFile(baseDir);
	}

}