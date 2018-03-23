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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalRepositoryJob extends RepositoryJob {

	@Override
	public List<String> getBatchNames() {
		String testBatchNames = getProperty(
			portalTestProperties, "test.batch.names");

		return getListFromString(testBatchNames);
	}

	@Override
	public List<String> getDistTypes() {
		String testBatchDistAppServers = getProperty(
			portalTestProperties, "test.batch.dist.app.servers");

		return getListFromString(testBatchDistAppServers);
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		return _getPortalGitWorkingDirectory();
	}

	public String getPoshiQuery(String testBatchName) {
		String propertyName = JenkinsResultsParserUtil.combine(
			"test.batch.run.property.query[", testBatchName, "]");

		if (portalTestProperties.containsKey(propertyName)) {
			String propertyValue = getProperty(
				portalTestProperties, propertyName);

			if ((propertyValue != null) && !propertyValue.isEmpty()) {
				return propertyValue;
			}
		}

		return null;
	}

	protected PortalRepositoryJob(String jobName) {
		super(jobName);

		branchName = _getBranchName();
		gitWorkingDirectory = _getPortalGitWorkingDirectory();

		portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				gitWorkingDirectory.getWorkingDirectory(), "test.properties"));
	}

	protected List<String> getListFromString(String string) {
		if (string == null) {
			return Collections.emptyList();
		}

		List<String> list = new ArrayList<>();

		for (String item : StringUtils.split(string, ",")) {
			if (list.contains(item) || item.startsWith("#")) {
				continue;
			}

			list.add(item);
		}

		Collections.sort(list);

		return list;
	}

	protected String getProperty(Properties properties, String name) {
		if (!properties.containsKey(name)) {
			return null;
		}

		String value = properties.getProperty(name);

		Matcher matcher = _propertiesPattern.matcher(value);

		String newValue = value;

		while (matcher.find()) {
			newValue = newValue.replace(
				matcher.group(0), getProperty(properties, matcher.group(1)));
		}

		return newValue;
	}

	protected final Properties portalTestProperties;

	private String _getBranchName() {
		Matcher matcher = _jobNamePattern.matcher(jobName);

		if (matcher.find()) {
			return matcher.group("branchName");
		}

		return "master";
	}

	private PortalGitWorkingDirectory _getPortalGitWorkingDirectory() {
		if ((gitWorkingDirectory != null) &&
			gitWorkingDirectory instanceof PortalGitWorkingDirectory) {

			return (PortalGitWorkingDirectory)gitWorkingDirectory;
		}

		String branchName = _getBranchName();
		String workingDirectoryPath = "/opt/dev/projects/github/liferay-portal";

		if (!branchName.equals("master")) {
			workingDirectoryPath = JenkinsResultsParserUtil.combine(
				workingDirectoryPath, "-", branchName);
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory = null;

		try {
			portalGitWorkingDirectory = new PortalGitWorkingDirectory(
				branchName, workingDirectoryPath);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Invalid Git working directory " + workingDirectoryPath, ioe);
		}

		gitWorkingDirectory = portalGitWorkingDirectory;

		return portalGitWorkingDirectory;
	}

	private static final Pattern _jobNamePattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^\\)]+)\\)");
	private static final Pattern _propertiesPattern = Pattern.compile(
		"\\$\\{([^\\}]+)\\}");

}