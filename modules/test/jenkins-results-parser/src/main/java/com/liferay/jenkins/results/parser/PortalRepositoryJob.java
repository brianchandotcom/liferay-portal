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
public abstract class PortalRepositoryJob extends RepositoryJob {

	@Override
	public List<String> getBatchNames() {
		String testBatchNames = portalTestProperies.getProperty(
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

	@Override
	public String getBranchName() {
		Matcher matcher = _pattern.matcher(jobName);

		if (matcher.find()) {
			return matcher.group("branchName");
		}

		return "master";
	}

	public List<String> getDistTypes() {
		String testBatchDistAppServers = portalTestProperies.getProperty(
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

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		String portalBranchName = getBranchName();

		String workingDirectoryPath = null;

		if (!portalBranchName.equals("master")) {
			workingDirectoryPath = JenkinsResultsParserUtil.combine(
				"/opt/dev/projects/github/liferay-portal-", portalBranchName);
		}
		else {
			workingDirectoryPath = "/opt/dev/projects/github/liferay-portal";
		}

		try {
			return new GitWorkingDirectory(
				portalBranchName, workingDirectoryPath);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Invalid git working directory " + workingDirectoryPath, ioe);
		}
	}

	protected PortalRepositoryJob(String jobName) {
		super(jobName);

		portalTestProperies = getGitWorkingDirectoryProperties(
			"test.properties");
	}

	protected final Properties portalTestProperies;

	private static final Pattern _pattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^\\)]+)\\)");

}