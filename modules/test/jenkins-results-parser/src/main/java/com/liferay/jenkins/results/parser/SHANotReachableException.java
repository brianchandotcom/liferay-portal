/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class SHANotReachableException extends RuntimeException {

	public SHANotReachableException(
		String sha, String branchName, String remoteURL) {

		super(
			JenkinsResultsParserUtil.combine(
				"SHA ", sha, " was not found in branch \"", branchName,
				"\" on ", remoteURL));

		_sha = sha;
		_branchName = branchName;
		_remoteURL = remoteURL;
	}

	public String getBranchName() {
		return _branchName;
	}

	public String getRemoteURL() {
		return _remoteURL;
	}

	public String getSHA() {
		return _sha;
	}

	private final String _branchName;
	private final String _remoteURL;
	private final String _sha;

}