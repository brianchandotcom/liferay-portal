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
public class PluginsLocalGitBranch extends LocalGitBranch {

	public PluginsGitWorkingDirectory getPluginsGitWorkingDirectory() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		return (PluginsGitWorkingDirectory)gitWorkingDirectory;
	}

	public PluginsLocalRepository getPluginsLocalRepository() {
		LocalRepository localRepository = getLocalRepository();

		return (PluginsLocalRepository)localRepository;
	}

	protected PluginsLocalGitBranch(
		LocalRepository localRepository, String name, String sha) {

		super(localRepository, name, sha);

		if (!(localRepository instanceof PluginsLocalRepository)) {
			throw new IllegalArgumentException(
				"Local repository is not a plugins repository");
		}
	}

}