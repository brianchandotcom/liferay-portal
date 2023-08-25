/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

/**
 * @author Peter Yoo
 */
public class GitHubSecondaryRateLimitIOException extends IOException {

	public GitHubSecondaryRateLimitIOException(
		int retryAfterSeconds, String gitHubApiUrl, IOException ioException) {

		super(ioException);

		_retryAfterSeconds = retryAfterSeconds;
		_gitHubApiUrl = gitHubApiUrl;
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