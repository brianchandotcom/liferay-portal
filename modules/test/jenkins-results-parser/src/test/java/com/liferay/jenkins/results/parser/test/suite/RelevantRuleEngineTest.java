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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class RelevantRuleEngineTest {

	@After
	public void tearDown() {
		RelevantRuleEngine.clear();
	}

	@Test
	public void testExcludedModifiedFileInModule1Dir() {
		RelevantRuleEngine relevantRuleEngine = RelevantRuleEngine.getInstance(
			_baseDir);

		List<RelevantRule> relevantRules =
			relevantRuleEngine.getMatchingRelevantRules(
				Arrays.asList(
					new File(_baseDir, "modules/module-1/file_1.excluded"),
					new File(_baseDir, "text_file_0.txt")));

		List<String> expectedRelevantRuleNames = Arrays.asList(
			"functional-smoke-0-rule");

		List<String> actualRelevantRuleNames = new ArrayList<>();

		for (RelevantRule relevantRule : relevantRules) {
			actualRelevantRuleNames.add(relevantRule.getName());
		}

		Collections.sort(actualRelevantRuleNames);
		Collections.sort(expectedRelevantRuleNames);

		Assert.assertEquals(expectedRelevantRuleNames, actualRelevantRuleNames);
	}

	@Test
	public void testModifiedFileForPlaywrightBatch() {
		RelevantRuleEngine relevantRuleEngine = RelevantRuleEngine.getInstance(
			_baseDir);

		System.out.println(relevantRuleEngine);

		List<RelevantRule> relevantRules =
			relevantRuleEngine.getMatchingRelevantRules(
				Collections.singletonList(
					new File(_baseDir, "modules/module-1/text_file_1.txt")));

		List<String> expectedRelevantRuleNames = Arrays.asList(
			"modules-integration-0-rule", "modules-integration-1-rule",
			"playwright-1-rule");

		List<String> actualRelevantRuleNames = new ArrayList<>();

		for (RelevantRule relevantRule : relevantRules) {
			actualRelevantRuleNames.add(relevantRule.getName());
		}

		Collections.sort(actualRelevantRuleNames);
		Collections.sort(expectedRelevantRuleNames);

		Assert.assertEquals(expectedRelevantRuleNames, actualRelevantRuleNames);

		PlaywrightTestBatch playwrightTestBatch = null;

		relevantRuleLoop:
		for (RelevantRule relevantRule : relevantRules) {
			for (TestBatch testBatch : relevantRule.getTestBatches()) {
				if (testBatch instanceof PlaywrightTestBatch) {
					playwrightTestBatch = (PlaywrightTestBatch)testBatch;
				}

				break relevantRuleLoop;
			}
		}

		PlaywrightTestSelector playwrightTestSelector =
			playwrightTestBatch.getTestSelector();

		Assert.assertEquals(
			Collections.singleton("module-1-playwright-project"),
			playwrightTestSelector.getPlaywrightProjectNames());
	}

	@Test
	public void testModifiedFileInBaseDir() {
		RelevantRuleEngine relevantRuleEngine = RelevantRuleEngine.getInstance(
			_baseDir);

		List<RelevantRule> relevantRules =
			relevantRuleEngine.getMatchingRelevantRules(
				Collections.singletonList(
					new File(_baseDir, "text_file_0.txt")));

		List<String> actualRelevantRuleNames = new ArrayList<>();

		for (RelevantRule relevantRule : relevantRules) {
			actualRelevantRuleNames.add(relevantRule.getName());
		}

		List<String> expectedRelevantRuleNames = Collections.singletonList(
			"functional-smoke-0-rule");

		Assert.assertEquals(expectedRelevantRuleNames, actualRelevantRuleNames);
	}

	@Test
	public void testModifiedFileInBaseDirAndModule1Dir() {
		RelevantRuleEngine relevantRuleEngine = RelevantRuleEngine.getInstance(
			_baseDir);

		System.out.println(relevantRuleEngine);

		List<RelevantRule> relevantRules =
			relevantRuleEngine.getMatchingRelevantRules(
				Arrays.asList(
					new File(_baseDir, "modules/module-1/text_file_1.txt"),
					new File(_baseDir, "text_file_0.txt")));

		List<String> expectedRelevantRuleNames = Arrays.asList(
			"functional-smoke-0-rule", "modules-integration-0-rule",
			"modules-integration-1-rule", "playwright-1-rule");

		List<String> actualRelevantRuleNames = new ArrayList<>();

		for (RelevantRule relevantRule : relevantRules) {
			actualRelevantRuleNames.add(relevantRule.getName());
		}

		Collections.sort(actualRelevantRuleNames);
		Collections.sort(expectedRelevantRuleNames);

		Assert.assertEquals(expectedRelevantRuleNames, actualRelevantRuleNames);
	}

	@Test
	public void testModifiedFileInBaseDirAndModule2Dir() {
		RelevantRuleEngine relevantRuleEngine = RelevantRuleEngine.getInstance(
			_baseDir);

		List<RelevantRule> relevantRules =
			relevantRuleEngine.getMatchingRelevantRules(
				Arrays.asList(
					new File(_baseDir, "modules/module-2/text_file_2.txt"),
					new File(_baseDir, "text_file_0.txt")));

		List<String> expectedRelevantRuleNames = Arrays.asList(
			"functional-smoke-0-rule", "modules-unit-0-rule",
			"playwright-2-rule");

		List<String> actualRelevantRuleNames = new ArrayList<>();

		for (RelevantRule relevantRule : relevantRules) {
			actualRelevantRuleNames.add(relevantRule.getName());
		}

		Collections.sort(actualRelevantRuleNames);
		Collections.sort(expectedRelevantRuleNames);

		Assert.assertEquals(expectedRelevantRuleNames, actualRelevantRuleNames);
	}

	private static final File _baseDir;

	static {
		File baseDir = new File(
			"src/test/resources/dependencies/test/suite" +
				"/RelevantRuleEngineTest");

		_baseDir = JenkinsResultsParserUtil.getCanonicalFile(baseDir);
	}

}