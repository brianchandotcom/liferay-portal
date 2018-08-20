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
public class PortalLocalGitBranch extends LocalGitBranch {

	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		return (PortalGitWorkingDirectory)gitWorkingDirectory;
	}

	public PortalLocalRepository getPortalLocalRepository() {
		LocalRepository localRepository = getLocalRepository();

		return (PortalLocalRepository)localRepository;
	}

	protected PortalLocalGitBranch(
		LocalRepository localRepository, String name, String sha) {

		super(localRepository, name, sha);

		if (!(localRepository instanceof PortalLocalRepository)) {
			throw new IllegalArgumentException(
				"Local repository is not a portal repository");
		}
	}

}