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
public abstract class PortalJob extends BaseJob {

	public List<String> getBatchNames() {
		String testBatchNames = _portalTestProperies.getProperty(
			"test.batch.names");

		if (testBatchNames == null) {
			return new ArrayList<>();
		}

		List<String> batchNames = new ArrayList<>();

		for (String batchName : StringUtils.split(testBatchNames, ",")) {
			if (batchNames.contains(batchName)) {
				continue;
			}

			if (batchName.startsWith("#")) {
				continue;
			}

			batchNames.add(batchName);
		}

		Collections.sort(batchNames);

		return batchNames;
	}

	public List<String> getDistTypes() {
		String testBatchDistAppServers = _portalTestProperies.getProperty(
			"test.batch.dist.app.servers");

		if (testBatchDistAppServers == null) {
			return new ArrayList<>();
		}

		List<String> distTypes = new ArrayList<>();

		for (String distType :
				StringUtils.split(testBatchDistAppServers, ",")) {

			if (distTypes.contains(distType)) {
				continue;
			}

			if (distType.startsWith("#")) {
				continue;
			}

			distTypes.add(distType);
		}

		Collections.sort(distTypes);

		return distTypes;
	}

	public String getPortalBranchName() {
		return _portalBranchName;
	}

	public String getPortalWorkingDirectory() {
		return _portalWorkingDirectory;
	}

	protected PortalJob(String url) {
		super(url);

		_portalBranchName = _getPortalBranchName();
		_portalWorkingDirectory = _getPortalWorkingDirectory();

		try {
			_portalGitWorkingDirectory = new GitWorkingDirectory(
				_portalBranchName, _portalWorkingDirectory);

			_portalTestProperies = getGitWorkingDirectoryProperties(
				_portalGitWorkingDirectory, "test.properties");
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private String _getPortalBranchName() {
		Matcher matcher = _pattern.matcher(jobName);

		if (matcher.find()) {
			return matcher.group("branchName");
		}

		return "master";
	}

	private String _getPortalWorkingDirectory() {
		String portalBranchName = _getPortalBranchName();

		if (!portalBranchName.equals("master")) {
			return JenkinsResultsParserUtil.combine(
				"/opt/dev/projects/github/liferay-portal-", portalBranchName);
		}

		return "/opt/dev/projects/github/liferay-portal";
	}

	private static final Pattern _pattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^\\)]+)\\)");

	private final String _portalBranchName;
	private final GitWorkingDirectory _portalGitWorkingDirectory;
	private final Properties _portalTestProperies;
	private final String _portalWorkingDirectory;

}