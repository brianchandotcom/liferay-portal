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

import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public interface WorkspaceGitRepository extends LocalGitRepository {

	public String getFileContent(String filePath);

	public String getGitHubDevBranchName();

	public String getGitHubURL();

	public List<LocalGitCommit> getHistoricalLocalGitCommits();

	public String getType();

	public Properties getWorkspaceJobProperties(String propertyType, Job job);

	public List<List<LocalGitCommit>> partitionLocalGitCommits(
		List<LocalGitCommit> localGitCommits, int count);

	public void setBranchSHA(String branchSHA);

	public void setUp();

	public void storeCommitHistory(List<String> commitSHAs);

	public void tearDown();

	public void writePropertiesFiles();

}