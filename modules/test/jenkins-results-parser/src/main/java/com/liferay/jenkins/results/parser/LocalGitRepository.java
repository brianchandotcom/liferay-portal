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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Peter Yoo
 */
public class LocalGitRepository extends BaseGitRepository {

	public File getDirectory() {
		return directory;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public RemoteGitRepository getUpstreamRemoteGitRepository() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		return GitRepositoryFactory.getRemoteGitRepository(
			gitWorkingDirectory.getGitRemote("upstream"));
	}

	public void writeGitRepositoryPropertiesFiles() {
		for (Map.Entry<String, Properties> entry :
				_propertiesFilesMap.entrySet()) {

			JenkinsResultsParserUtil.writePropertiesFile(
				new File(getDirectory(), entry.getKey()), entry.getValue(),
				true);
		}
	}

	protected LocalGitRepository(String name, String upstreamBranchName) {
		super(name);

		if ((upstreamBranchName == null) || upstreamBranchName.isEmpty()) {
			throw new IllegalArgumentException("Upstream branch name is null");
		}

		_upstreamBranchName = upstreamBranchName;

		Properties gitRepositoryProperties = _getProperties();

		String gitRepositoryDirPropertyKey = getGitRepositoryDirPropertyKey();

		if (gitRepositoryProperties.containsKey(gitRepositoryDirPropertyKey)) {
			directory = new File(
				gitRepositoryProperties.getProperty(
					gitRepositoryDirPropertyKey));
		}
		else {
			directory = new File(
				JenkinsResultsParserUtil.getBaseGitRepositoryDir(),
				getDefaultRelativeGitRepositoryDirPath());
		}

		if (!directory.exists()) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find repository directory for ", getName(),
					" at ", directory.toString()));
		}

		File dotGitFile = new File(directory, ".git");

		if (!dotGitFile.exists()) {
			throw new IllegalArgumentException(
				directory + " is not a valid repository");
		}

		_gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, getDirectory(), getName());
	}

	protected String getDefaultRelativeGitRepositoryDirPath() {
		return getName();
	}

	protected String getGitRepositoryDirPropertyKey() {
		return JenkinsResultsParserUtil.combine(
			"repository.dir[", name, "/" + getUpstreamBranchName(), "]");
	}

	protected Properties getProperties(String filePath) {
		return _propertiesFilesMap.get(filePath);
	}

	protected void setProperties(String filePath, Properties properties) {
		if (!_propertiesFilesMap.containsKey(filePath)) {
			_propertiesFilesMap.put(filePath, new Properties());
		}

		Properties fileProperties = _propertiesFilesMap.get(filePath);

		fileProperties.putAll(properties);

		_propertiesFilesMap.put(filePath, fileProperties);
	}

	protected final File directory;

	private static Properties _getProperties() {
		if (_properties != null) {
			return _properties;
		}

		File propertiesFile = new File("repository.properties");

		_properties = JenkinsResultsParserUtil.getProperties(propertiesFile);

		return _properties;
	}

	private static Properties _properties;

	private final GitWorkingDirectory _gitWorkingDirectory;
	private final Map<String, Properties> _propertiesFilesMap = new HashMap<>();
	private final String _upstreamBranchName;

}