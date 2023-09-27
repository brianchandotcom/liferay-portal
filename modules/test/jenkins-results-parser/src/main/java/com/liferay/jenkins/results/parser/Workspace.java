/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface Workspace {

	public JSONObject getJSONObject();

	public WorkspaceGitRepository getPrimaryWorkspaceGitRepository();

	public List<WorkspaceGitRepository> getWorkspaceGitRepositories();

	public WorkspaceGitRepository getWorkspaceGitRepository(
		String gitDirectoryName);

	public void setUp();

	public void startSynchronizeToGitHubDev();

	public void synchronizeToGitHubDev();

	public void tearDown();

	public void waitForSynchronizeToGitHubDev();

	public void writePropertiesFiles();

}