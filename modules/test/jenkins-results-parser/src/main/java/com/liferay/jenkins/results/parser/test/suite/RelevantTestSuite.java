/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.test.batch.TestBatch;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class RelevantTestSuite {

	public static Job getJob() {
		return _job;
	}

	public RelevantTestSuite(File baseDir, List<File> modifiedFiles) {
		_modifiedFiles = modifiedFiles;

		_relevantRuleEngine = RelevantRuleEngine.getInstance(baseDir);
	}

	public RelevantTestSuite(File baseDir, List<File> modifiedFiles, Job job) {
		_baseDir = baseDir;
		_modifiedFiles = modifiedFiles;

		_relevantRuleEngine = RelevantRuleEngine.getInstance(baseDir);

		_job = job;
	}

	public List<TestBatch> getTestBatches() {
		List<TestBatch> testBatches = new ArrayList<>();

		List<RelevantRule> relevantRules =
			_relevantRuleEngine.getMatchingRelevantRules(_modifiedFiles);

		for (RelevantRule relevantRule : relevantRules) {
			for (TestBatch testBatch : relevantRule.getTestBatches()) {
				if (testBatches.contains(testBatch)) {
					TestBatch existingTestBatch = testBatches.get(
						testBatches.indexOf(testBatch));

					existingTestBatch.merge(testBatch);

					continue;
				}

				testBatches.add(testBatch);
			}
		}

		return testBatches;
	}

	private static File _baseDir = new File("");
	private static Job _job;

	private final List<File> _modifiedFiles;
	private final RelevantRuleEngine _relevantRuleEngine;

}