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
			if (!REPOSITORY_DIR.equals(
					JenkinsResultsParserUtil.getCanonicalPath(directory))) {

				errorCollector.addError(
					new Throwable(
						"The repository directorydirectory should be " +
							REPOSITORY_DIR));
			}
		}
		catch (RuntimeException re) {
			errorCollector.addError(
				new Throwable(
					"The repository directory should be " + REPOSITORY_DIR));
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

		expectedJSONObject.put("directory", REPOSITORY_DIR);
		expectedJSONObject.put("name", REPOSITORY_NAME);
		expectedJSONObject.put(
			"upstream_branch_name", REPOSITORY_UPSTREAM_BRANCH_NAME);

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

		if (!REPOSITORY_NAME.equals(localGitRepository.getName())) {
			errorCollector.addError(
				new Throwable(
					"The repository name should be " + REPOSITORY_NAME));
		}
	}

	@Test
	public void testGetUpstreamBranchName() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		if (!REPOSITORY_UPSTREAM_BRANCH_NAME.equals(
				localGitRepository.getUpstreamBranchName())) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"The upstream branch name should be ",
						REPOSITORY_UPSTREAM_BRANCH_NAME)));
		}
	}

	private LocalGitRepository _getLocalGitRepository() {
		return GitRepositoryFactory.getLocalGitRepository(
			REPOSITORY_NAME, REPOSITORY_UPSTREAM_BRANCH_NAME);
	}

}