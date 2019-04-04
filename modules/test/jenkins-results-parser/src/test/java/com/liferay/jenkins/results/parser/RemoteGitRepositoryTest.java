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

		if (!HOSTNAME_REPOSITORY.equals(remoteGitRepository.getHostname())) {
			errorCollector.addError(
				new Throwable("The hostname should be " + HOSTNAME_REPOSITORY));
		}
	}

	@Test
	public void testGetJSONObject() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		JSONObject expectedJSONObject = new JSONObject();

		expectedJSONObject.put("hostname", HOSTNAME_REPOSITORY);
		expectedJSONObject.put("name", NAME_REPOSITORY);
		expectedJSONObject.put("username", USERNAME_REPOSITORY);

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