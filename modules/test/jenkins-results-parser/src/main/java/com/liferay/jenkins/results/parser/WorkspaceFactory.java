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

	public static Workspace newWorkspace(String repositoryType) {
		return newWorkspace(repositoryType, "master");
	}

	public static Workspace newWorkspace(
		String repositoryType, String upstreamBranchName) {

		String workspaceId = repositoryType + "/" + upstreamBranchName;

		if (_workspaces.containsKey(workspaceId)) {
			return _workspaces.get(workspaceId);
		}

		_validateRepositoryType(repositoryType);

		File repositoryDir = _getRepositoryDir(
			repositoryType, upstreamBranchName);

		String upstreamRepositoryName = _getUpstreamRepositoryName(
			repositoryType, upstreamBranchName);

		if (repositoryType.startsWith("com-liferay-")) {
			_workspaces.put(
				workspaceId,
				new SubrepositoryWorkspace(
					repositoryDir, upstreamBranchName, upstreamRepositoryName));
		}
		else if (repositoryType.equals("liferay-portal")) {
			_workspaces.put(
				workspaceId,
				new PortalWorkspace(
					repositoryDir, upstreamBranchName, upstreamRepositoryName));
		}

		if (_workspaces.containsKey(workspaceId)) {
			return _workspaces.get(workspaceId);
		}

		throw new RuntimeException("Invalid repository type " + repositoryType);
	}

	private static File _getRepositoryDir(
		String repositoryType, String upstreamBranchName) {

		String repositoryDirPath = _getRepositoryDirPath(
			repositoryType, upstreamBranchName);

		File repositoryDir = new File(repositoryDirPath);

		if (!repositoryDir.exists()) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find ", repositoryDirPath));
		}

		return repositoryDir;
	}

	private static String _getRepositoryDirPath(
		String repositoryType, String upstreamBranchName) {

		Properties workspaceProperties = _getWorkspaceProperties();

		String repositoryDirKey = JenkinsResultsParserUtil.combine(
			"repository.dir[", repositoryType, "/", upstreamBranchName, "]");

		if (workspaceProperties.containsKey(repositoryDirKey)) {
			return workspaceProperties.getProperty(repositoryDirKey);
		}

		if (upstreamBranchName.equals("master")) {
			repositoryDirKey = JenkinsResultsParserUtil.combine(
				"repository.dir[", repositoryType, "]");

			if (workspaceProperties.containsKey(repositoryDirKey)) {
				return workspaceProperties.getProperty(repositoryDirKey);
			}
		}

		throw new RuntimeException(
			JenkinsResultsParserUtil.combine(
				"Unable to find '", repositoryDirKey, "' in ",
				_workspaceHomeDir.toString(), "/workspace.properties"));
	}

	private static String _getUpstreamRepositoryName(
		String repositoryType, String upstreamBranchName) {

		if (repositoryType.equals("liferay-portal")) {
			if (!upstreamBranchName.equals("master")) {
				return "liferay-portal-ee";
			}
		}
		else if (repositoryType.startsWith("com-liferay")) {
			if (upstreamBranchName.endsWith("-private")) {
				return repositoryType + "-private";
			}
		}

		return repositoryType;
	}

	private static Properties _getWorkspaceProperties() {
		if (_workspaceProperties != null) {
			return _workspaceProperties;
		}

		File[] workspacePropertiesFiles = {
			new File(_workspaceHomeDir, "workspace.generated.properties"),
			new File(_workspaceHomeDir, "workspace.properties"),
			new File(
				_workspaceHomeDir,
				JenkinsResultsParserUtil.combine(
					"workspace.", System.getenv("HOSTNAME"), "properties")),
			new File(
				_workspaceHomeDir,
				JenkinsResultsParserUtil.combine(
					"workspace.", System.getenv("HOST"), "properties")),
			new File(
				_workspaceHomeDir,
				JenkinsResultsParserUtil.combine(
					"workspace.", System.getenv("COMPUTERNAME"), "properties")),
			new File(
				_workspaceHomeDir,
				JenkinsResultsParserUtil.combine(
					"workspace.", System.getProperty("user.name"),
					"properties"))
		};

		_workspaceProperties = new Properties();

		_workspaceProperties = JenkinsResultsParserUtil.getProperties(
			workspacePropertiesFiles);

		return _workspaceProperties;
	}

	private static void _validateRepositoryType(String repositoryType) {
		if (repositoryType.startsWith("com-liferay") &&
			repositoryType.endsWith("-private")) {

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Please use '", repositoryType.replace("-private", ""),
					"' instead of '", repositoryType, "'"));
		}
		else if (repositoryType.equals("liferay-portal-ee")) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Please use '", repositoryType.replace("-ee", ""),
					"' instead of '", repositoryType, "'"));
		}
	}

	private static final File _workspaceHomeDir;
	private static Properties _workspaceProperties;
	private static final Map<String, Workspace> _workspaces = new HashMap<>();

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