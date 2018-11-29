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

import org.json.JSONObject;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class RemoteGitRepositoryTest extends GitRepositoryTest {

	@Test
	public void testGetHostname() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!REPOSITORY_HOSTNAME.equals(remoteGitRepository.getHostname())) {
			errorCollector.addError(
				new Throwable("The hostname should be " + REPOSITORY_HOSTNAME));
		}
	}

	@Test
	public void testGetJSONObject() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		JSONObject expectedJSONObject = new JSONObject();

		expectedJSONObject.put("hostname", REPOSITORY_HOSTNAME);
		expectedJSONObject.put("name", REPOSITORY_NAME);
		expectedJSONObject.put("username", REPOSITORY_USERNAME);

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

		if (!REPOSITORY_NAME.equals(remoteGitRepository.getName())) {
			errorCollector.addError(
				new Throwable(
					"The repository name should be " + REPOSITORY_NAME));
		}
	}

	@Test
	public void testGetRemoteURL() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		String remoteURL = JenkinsResultsParserUtil.combine(
			"git@", REPOSITORY_HOSTNAME, ":", REPOSITORY_USERNAME, "/",
			REPOSITORY_NAME);

		if (!remoteURL.equals(remoteGitRepository.getRemoteURL())) {
			errorCollector.addError(
				new Throwable("The remote URL should be " + remoteURL));
		}
	}

	@Test
	public void testGetUsername() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!REPOSITORY_USERNAME.equals(remoteGitRepository.getUsername())) {
			errorCollector.addError(
				new Throwable("The username should be " + REPOSITORY_USERNAME));
		}
	}

	private RemoteGitRepository _getRemoteGitRepository() {
		return GitRepositoryFactory.getRemoteGitRepository(
			REPOSITORY_HOSTNAME, REPOSITORY_NAME, REPOSITORY_USERNAME);
	}

}