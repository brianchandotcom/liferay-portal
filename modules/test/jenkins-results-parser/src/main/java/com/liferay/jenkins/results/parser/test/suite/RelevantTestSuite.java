/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.test.batch.TestBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class RelevantTestSuite {

	public RelevantTestSuite(
		String name, PortalTestClassJob portalTestClassJob) {

		_name = name;
		_portalTestClassJob = portalTestClassJob;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		_relevantRuleEngine = new RelevantRuleEngine(
			portalGitWorkingDirectory.getWorkingDirectory());
	}

	public String getName() {
		return _name;
	}

	public List<TestBatch> getTestBatches() {
		List<TestBatch> testBatches = new ArrayList<>();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_portalTestClassJob.getPortalGitWorkingDirectory();

		List<RelevantRule> relevantRules =
			_relevantRuleEngine.getMatchingRelevantRules(
				portalGitWorkingDirectory.getModifiedFilesList());

		for (RelevantRule relevantRule : relevantRules) {
			testBatches.addAll(relevantRule.getTestBatches());
		}

		return testBatches;
	}

	private final String _name;
	private final PortalTestClassJob _portalTestClassJob;
	private final RelevantRuleEngine _relevantRuleEngine;

}