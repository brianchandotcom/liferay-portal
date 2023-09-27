/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import org.json.JSONObject;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class LocalGitRepositoryTest extends GitRepositoryTest {

	@Test
	public void testGetDirectory() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		File directory = localGitRepository.getDirectory();

		try {
			if (!FILE_PATH_REPOSITORY.equals(
					JenkinsResultsParserUtil.getCanonicalPath(directory))) {

				errorCollector.addError(
					new Throwable(
						"The repository directory should be " +
							FILE_PATH_REPOSITORY));
			}
		}
		catch (RuntimeException runtimeException) {
			errorCollector.addError(
				new Throwable(
					"The repository directory should be " +
						FILE_PATH_REPOSITORY));
		}
	}

	@Test
	public void testGetGitWorkingDirectory() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		GitWorkingDirectory gitWorkingDirectory =
			localGitRepository.getGitWorkingDirectory();

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			errorCollector.addError(
				new Throwable("Invalid GitWorkingDirectory instance"));
		}
	}

	@Test
	public void testGetJSONObject() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		JSONObject expectedJSONObject = new JSONObject();

		expectedJSONObject.put(
			"directory", FILE_PATH_REPOSITORY
		).put(
			"name", NAME_REPOSITORY
		).put(
			"upstream_branch_name", NAME_REPOSITORY_UPSTREAM_BRANCH
		);

		JSONObject actualJSONObject = localGitRepository.getJSONObject();

		if (!JenkinsResultsParserUtil.isJSONObjectEqual(
				expectedJSONObject, actualJSONObject)) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedJSONObject.toString(), "\nActual:   ",
						actualJSONObject.toString())));
		}
	}

	@Test
	public void testGetName() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		if (!NAME_REPOSITORY.equals(localGitRepository.getName())) {
			errorCollector.addError(
				new Throwable(
					"The repository name should be " + NAME_REPOSITORY));
		}
	}

	@Test
	public void testGetUpstreamBranchName() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		if (!NAME_REPOSITORY_UPSTREAM_BRANCH.equals(
				localGitRepository.getUpstreamBranchName())) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"The upstream branch name should be ",
						NAME_REPOSITORY_UPSTREAM_BRANCH)));
		}
	}

	private LocalGitRepository _getLocalGitRepository() {
		return GitRepositoryFactory.getLocalGitRepository(
			NAME_REPOSITORY, NAME_REPOSITORY_UPSTREAM_BRANCH);
	}

}