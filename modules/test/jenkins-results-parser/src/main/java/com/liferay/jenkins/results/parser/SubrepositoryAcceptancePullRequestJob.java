/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.job.property.JobProperty;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryAcceptancePullRequestJob
	extends SubrepositoryGitRepositoryJob implements TestSuiteJob {

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("test_suite_name", _testSuiteName);

		return jsonObject;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected SubrepositoryAcceptancePullRequestJob(
		BuildProfile buildProfile, String jobName,
		String portalUpstreamBranchName, String repositoryName,
		String testSuiteName, String upstreamBranchName) {

		super(
			buildProfile, jobName, portalUpstreamBranchName, repositoryName,
			upstreamBranchName);

		_testSuiteName = testSuiteName;

		_initialize();
	}

	protected SubrepositoryAcceptancePullRequestJob(JSONObject jsonObject) {
		super(jsonObject);

		_testSuiteName = jsonObject.getString("test_suite_name");

		_initialize();
	}

	private void _initialize() {
		_setValidationRequired();
	}

	private void _setValidationRequired() {
		JobProperty jobProperty = getJobProperty("test.run.validation");

		recordJobProperty(jobProperty);

		validationRequired = Boolean.parseBoolean(jobProperty.getValue());
	}

	private final String _testSuiteName;

}