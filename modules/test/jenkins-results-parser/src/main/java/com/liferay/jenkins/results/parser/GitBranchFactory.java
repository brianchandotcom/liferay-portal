/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class GitBranchFactory {

	public static LocalGitBranch newLocalGitBranch(
		LocalGitRepository localGitRepository, String name, String sha) {

		return new LocalGitBranch(localGitRepository, name, sha);
	}

	public static RemoteGitRef newRemoteGitRef(
		RemoteGitRepository remoteGitRepository, String name, String sha,
		String type) {

		if (type.equals("heads")) {
			return new RemoteGitBranch(remoteGitRepository, name, sha);
		}

		return new RemoteGitRef(remoteGitRepository, name, sha);
	}

}