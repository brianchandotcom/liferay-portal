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

/**
 * @author Michael Hashimoto
 */
public class GitBranchFactory {

	public static LocalGitBranch newLocalGitBranch(
		LocalGitRepository localGitRepository, String name, String sha) {

		if (localGitRepository instanceof PluginsLocalGitRepository) {
			return new PluginsLocalGitBranch(localGitRepository, name, sha);
		}
		else if (localGitRepository instanceof PortalLocalGitRepository) {
			return new PortalLocalGitBranch(localGitRepository, name, sha);
		}

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