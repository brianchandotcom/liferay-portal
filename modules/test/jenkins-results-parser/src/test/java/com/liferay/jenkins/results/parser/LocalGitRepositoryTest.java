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

import com.liferay.jenkins.results.parser.util.TestPropsUtil;
import com.liferay.jenkins.results.parser.util.TestPropsValues;

import java.io.File;
import java.io.IOException;

import java.util.Properties;

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class LocalGitRepositoryTest extends TestPropsValues {

	@BeforeClass
	public static void setUpClass() {
		TestPropsUtil.printProperties();

		Properties repositoryProperties = new Properties();

		repositoryProperties.put(
			JenkinsResultsParserUtil.combine(
				"repository.dir[", REPOSITORY_NAME, "][",
				REPOSITORY_UPSTREAM_BRANCH_NAME, "]"),
			REPOSITORY_DIR);

		BaseGitRepository.setRepositoryProperties(repositoryProperties);
	}

	@Test
	public void testLocalGitRepository() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		if (!(localGitRepository instanceof DefaultLocalGitRepository)) {
			Assert.fail("Invalid LocalGitRepository instance");
		}

		GitWorkingDirectory gitWorkingDirectory =
			localGitRepository.getGitWorkingDirectory();

		System.out.println(gitWorkingDirectory);

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			Assert.fail("Invalid GitWorkingDirectory instance");
		}

		File directory = localGitRepository.getDirectory();

		try {
			if (!REPOSITORY_DIR.equals(directory.getCanonicalPath())) {
				Assert.fail("The repository dir should be " + REPOSITORY_DIR);
			}
		}
		catch (IOException ioe) {
			Assert.fail("The repository dir should be " + REPOSITORY_DIR);
		}

		if (!REPOSITORY_UPSTREAM_BRANCH_NAME.equals(
				localGitRepository.getUpstreamBranchName())) {

			Assert.fail(
				"The upstream branch name should be " +
					REPOSITORY_UPSTREAM_BRANCH_NAME);
		}

		if (!REPOSITORY_NAME.equals(localGitRepository.getName())) {
			Assert.fail("The repository name should be " + REPOSITORY_NAME);
		}

		JSONObject expectedJSONObject = new JSONObject();

		expectedJSONObject.put("directory", REPOSITORY_DIR);
		expectedJSONObject.put("name", REPOSITORY_NAME);
		expectedJSONObject.put(
			"upstream_branch_name", REPOSITORY_UPSTREAM_BRANCH_NAME);

		JSONObject actualJSONObject = localGitRepository.getJSONObject();

		if (!JenkinsResultsParserUtil.isJSONObjectEqual(
				expectedJSONObject, actualJSONObject)) {

			Assert.fail(
				JenkinsResultsParserUtil.combine(
					"Expected does not match actual\nexpected: ",
					expectedJSONObject.toString(), "\nactual:   ",
					actualJSONObject.toString()));
		}
	}

	private LocalGitRepository _getLocalGitRepository() {
		return GitRepositoryFactory.getLocalGitRepository(
			REPOSITORY_NAME, REPOSITORY_UPSTREAM_BRANCH_NAME);
	}

}