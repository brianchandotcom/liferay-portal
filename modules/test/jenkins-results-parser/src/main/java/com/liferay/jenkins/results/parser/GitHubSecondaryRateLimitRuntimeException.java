/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class GitHubSecondaryRateLimitRuntimeException extends RuntimeException {

	public GitHubSecondaryRateLimitRuntimeException(Exception exception) {
		this(null, exception);
	}

	public GitHubSecondaryRateLimitRuntimeException(
		String message, Exception exception) {

		super(message, exception);

		_gitHubSecondaryRateLimitIOException =
			(GitHubSecondaryRateLimitIOException)exception;
	}

	public String getGitHubApiUrl() {
		return _gitHubSecondaryRateLimitIOException.getGitHubApiUrl();
	}

	public GitHubSecondaryRateLimitIOException
		getGitHubSecondaryRateLimitIOException() {

		return _gitHubSecondaryRateLimitIOException;
	}

	public int getRetryAfterSeconds() {
		return _gitHubSecondaryRateLimitIOException.getRetryAfterSeconds();
	}

	private final GitHubSecondaryRateLimitIOException
		_gitHubSecondaryRateLimitIOException;

}