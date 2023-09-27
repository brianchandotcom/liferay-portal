/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class RemoteGitRepositoryTest extends GitRepositoryTest {

	@Test
	public void testGetHostname() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!HOSTNAME_REPOSITORY.equals(remoteGitRepository.getHostname())) {
			errorCollector.addError(
				new Throwable("The hostname should be " + HOSTNAME_REPOSITORY));
		}
	}

	@Test
	public void testGetJSONObject() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		JSONObject expectedJSONObject = new JSONObject();

		expectedJSONObject.put(
			"hostname", HOSTNAME_REPOSITORY
		).put(
			"name", NAME_REPOSITORY
		).put(
			"username", USERNAME_REPOSITORY
		);

		JSONObject actualJSONObject = remoteGitRepository.getJSONObject();

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
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!NAME_REPOSITORY.equals(remoteGitRepository.getName())) {
			errorCollector.addError(
				new Throwable(
					"The repository name should be " + NAME_REPOSITORY));
		}
	}

	@Test
	public void testGetRemoteURL() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		String remoteURL = JenkinsResultsParserUtil.combine(
			"git@", HOSTNAME_REPOSITORY, ":", USERNAME_REPOSITORY, "/",
			NAME_REPOSITORY);

		if (!remoteURL.equals(remoteGitRepository.getRemoteURL())) {
			errorCollector.addError(
				new Throwable("The remote URL should be " + remoteURL));
		}
	}

	@Test
	public void testGetUsername() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!USERNAME_REPOSITORY.equals(remoteGitRepository.getUsername())) {
			errorCollector.addError(
				new Throwable("The username should be " + USERNAME_REPOSITORY));
		}
	}

	private RemoteGitRepository _getRemoteGitRepository() {
		return GitRepositoryFactory.getRemoteGitRepository(
			HOSTNAME_REPOSITORY, NAME_REPOSITORY, USERNAME_REPOSITORY);
	}

}