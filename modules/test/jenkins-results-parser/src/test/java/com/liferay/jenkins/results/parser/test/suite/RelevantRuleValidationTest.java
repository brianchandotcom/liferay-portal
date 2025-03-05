/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import java.io.IOException;

import org.json.JSONObject;

import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class RelevantRuleValidationTest {

	@Test
	public void testValidate() throws IOException {
		String repositoryName = "liferay-portal";
		String upstreamBranchName = "master";

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"build_profile", "DXP"
		).put(
			"git_repository_dir", "liferay-portal"
		).put(
			"job_name", "test-portal-acceptance-pullrequest("
		).put(
			"test_suite_name", "relevant"
		).put(
			"upstream_branch_name", "master"
		);

		RelevantRuleValidation.validate(
			repositoryName, upstreamBranchName, jsonObject);
	}

}