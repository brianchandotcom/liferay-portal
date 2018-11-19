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

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.util.TestPropertiesUtil;
import com.liferay.jenkins.results.parser.util.TestPropertiesValues;

import java.io.File;
import java.io.IOException;

import java.util.Properties;

import org.json.JSONObject;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class LocalGitRepositoryTest
	extends com.liferay.jenkins.results.parser.Test {

	@BeforeClass
	public static void setUpClass() {
		TestPropertiesUtil.printProperties();

		Properties repositoryProperties = new Properties();

		repositoryProperties.put(
			JenkinsResultsParserUtil.combine(
				"repository.dir[", _REPOSITORY_NAME, "][",
				_REPOSITORY_UPSTREAM_BRANCH_NAME, "]"),
			_REPOSITORY_DIR);

		BaseGitRepository.setRepositoryProperties(repositoryProperties);
	}

	@Test
	public void testLocalGitRepository() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		if (!(localGitRepository instanceof DefaultLocalGitRepository)) {
			failTest("Invalid LocalGitRepository instance");
		}

		GitWorkingDirectory gitWorkingDirectory =
			localGitRepository.getGitWorkingDirectory();

		System.out.println(gitWorkingDirectory);

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			failTest("Invalid GitWorkingDirectory instance");
		}

		File directory = localGitRepository.getDirectory();

		try {
			if (!_REPOSITORY_DIR.equals(directory.getCanonicalPath())) {
				failTest("The repository dir should be " + _REPOSITORY_DIR);
			}
		}
		catch (IOException ioe) {
			failTest("The repository dir should be " + _REPOSITORY_DIR);
		}

		if (!_REPOSITORY_UPSTREAM_BRANCH_NAME.equals(
				localGitRepository.getUpstreamBranchName())) {

			failTest(
				JenkinsResultsParserUtil.combine(
					"The upstream branch name should be ",
					_REPOSITORY_UPSTREAM_BRANCH_NAME));
		}

		if (!_REPOSITORY_NAME.equals(localGitRepository.getName())) {
			failTest("The repository name should be " + _REPOSITORY_NAME);
		}

		JSONObject expectedJSONObject = new JSONObject();

		expectedJSONObject.put("directory", _REPOSITORY_DIR);
		expectedJSONObject.put("name", _REPOSITORY_NAME);
		expectedJSONObject.put(
			"upstream_branch_name", _REPOSITORY_UPSTREAM_BRANCH_NAME);

		JSONObject actualJSONObject = localGitRepository.getJSONObject();

		if (!JenkinsResultsParserUtil.isJSONObjectEqual(
				expectedJSONObject, actualJSONObject)) {

			failTest(
				JenkinsResultsParserUtil.combine(
					"Expected does not match actual\nexpected: ",
					expectedJSONObject.toString(), "\nactual:   ",
					actualJSONObject.toString()));
		}
	}

	private LocalGitRepository _getLocalGitRepository() {
		return GitRepositoryFactory.getLocalGitRepository(
			_REPOSITORY_NAME, _REPOSITORY_UPSTREAM_BRANCH_NAME);
	}

	private static final String _REPOSITORY_DIR =
		TestPropertiesValues.REPOSITORY_DIR;

	private static final String _REPOSITORY_NAME =
		TestPropertiesValues.REPOSITORY_NAME;

	private static final String _REPOSITORY_UPSTREAM_BRANCH_NAME =
		TestPropertiesValues.REPOSITORY_UPSTREAM_BRANCH_NAME;

}