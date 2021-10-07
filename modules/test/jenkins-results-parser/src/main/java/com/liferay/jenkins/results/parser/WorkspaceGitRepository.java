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

/**
 * @author Michael Hashimoto
 */
public interface WorkspaceGitRepository extends LocalGitRepository {

	public void addPropertyOption(String propertyOption);

	public String getBaseBranchSHA();

	public String getBranchName();

	public String getFileContent(String filePath);

	public String getGitHubDevBranchName();

	public String getGitHubURL();

	public List<LocalGitCommit> getHistoricalLocalGitCommits();

	public String getSenderBranchName();

	public String getSenderBranchSHA();

	public String getSenderBranchUsername();

	public List<List<LocalGitCommit>> partitionLocalGitCommits(
		List<LocalGitCommit> localGitCommits, int count);

	public void setBaseBranchSHA(String branchSHA);

	public void setGitHubURL(String gitHubURL);

	public void setSenderBranchSHA(String branchSHA);

	public void setUp();

	public void storeCommitHistory(List<String> commitSHAs);

	public void synchronizeToGitHubDev();

	public void tearDown();

	public void writePropertiesFiles();

}