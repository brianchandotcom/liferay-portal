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
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class WorkspaceFactory {

	public static BaseWorkspace newWorkspace(String repositoryType) {
		return newWorkspace(repositoryType, "master");
	}

	public static BaseWorkspace newWorkspace(
		String repositoryType, String upstreamBranchName) {

		String workspaceId = repositoryType + "/" + upstreamBranchName;

		if (_workspaces.containsKey(workspaceId)) {
			return _workspaces.get(workspaceId);
		}

		if (repositoryType.startsWith("com-liferay-")) {
			_workspaces.put(
				workspaceId,
				new SubrepositoryWorkspace(repositoryType, upstreamBranchName));
		}
		else if (repositoryType.startsWith("liferay-portal")) {
			_workspaces.put(
				workspaceId,
				new PortalWorkspace(repositoryType, upstreamBranchName));
		}
		else if (repositoryType.startsWith("liferay-plugins")) {
			_workspaces.put(
				workspaceId,
				new PluginsWorkspace(repositoryType, upstreamBranchName));
		}
		else {
			_workspaces.put(
				workspaceId,
				new BaseWorkspace(repositoryType, upstreamBranchName));
		}

		return _workspaces.get(workspaceId);
	}

	private static final File _workspaceHomeDir;
	private static Properties _workspaceProperties;
	private static final Map<String, BaseWorkspace> _workspaces =
		new HashMap<>();

	static {
		try {
			File workspaceHomeDir = new File(".");

			_workspaceHomeDir = new File(workspaceHomeDir.getCanonicalPath());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}