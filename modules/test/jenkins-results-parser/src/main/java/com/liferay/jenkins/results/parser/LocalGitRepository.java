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

import java.io.File;

import java.util.List;

/**
 * @author Peter Yoo
 */
public interface LocalGitRepository extends GitRepository {

	public static final Integer COMMITS_HISTORY_GROUP_SIZE = 100;

	public static final Integer COMMITS_HISTORY_SIZE_MAX = 50000;

	public File getDirectory();

	public String getDirectoryName();

	public GitWorkingDirectory getGitWorkingDirectory();

	public List<LocalGitCommit> getRangeLocalGitCommits(
		String earliestSHA, String latestSHA);

	public String getUpstreamBranchName();

}