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

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class WorkspaceFactory {

	public static Workspace newWorkspace() {
		return newWorkspace("liferay-jenkins-ee", "master");
	}

	public static Workspace newWorkspace(
		String repositoryName, String upstreamBranchName) {

		String gitDirectoryName = JenkinsResultsParserUtil.getGitDirectoryName(
			repositoryName, upstreamBranchName);

		Workspace workspace = _workspaces.get(gitDirectoryName);

		if (workspace != null) {
			return workspace;
		}

		workspace = new DefaultWorkspace(repositoryName, upstreamBranchName);

		_workspaces.put(gitDirectoryName, workspace);

		return (Workspace)Proxy.newProxyInstance(
			Workspace.class.getClassLoader(), new Class<?>[] {Workspace.class},
			new MethodLogger(workspace));
	}

	private static final Map<String, Workspace> _workspaces = new HashMap<>();

}