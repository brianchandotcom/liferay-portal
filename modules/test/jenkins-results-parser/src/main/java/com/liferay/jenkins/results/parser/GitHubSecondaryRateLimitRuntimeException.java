/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class GitHubSecondaryRateLimitRuntimeException extends RuntimeException {

	public GitHubSecondaryRateLimitRuntimeException(
		String gitHubApiUrl, int retryAfterSeconds, Exception exception) {

		this(gitHubApiUrl, retryAfterSeconds, null, exception);
	}

	public GitHubSecondaryRateLimitRuntimeException(
		String gitHubApiUrl, int retryAfterSeconds, String message,
		Exception exception) {

		super(message, exception);

		_gitHubApiUrl = gitHubApiUrl;
		_retryAfterSeconds = retryAfterSeconds;
	}

	public String getGitHubApiUrl() {
		return _gitHubApiUrl;
	}

	public int getRetryAfterSeconds() {
		return _retryAfterSeconds;
	}

	private final String _gitHubApiUrl;
	private final int _retryAfterSeconds;

}