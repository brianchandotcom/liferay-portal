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

		return getListFromString(testBatchNames);
	}

	@Override
	public String getBranchName() {
		if (branchName != null) {
			return branchName;
		}

		Matcher matcher = _pattern.matcher(jobName);

		if (matcher.find()) {
			return matcher.group("branchName");
		}

		return "master";
	}

	public List<String> getDistTypes() {
		String testBatchDistAppServers = portalTestProperies.getProperty(
			"test.batch.dist.app.servers");

		return getListFromString(testBatchDistAppServers);
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		String portalBranchName = getBranchName();
		String workingDirectoryPath = "/opt/dev/projects/github/liferay-portal";

		if (!portalBranchName.equals("master")) {
			workingDirectoryPath = JenkinsResultsParserUtil.combine(
				workingDirectoryPath, "-", portalBranchName);
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

	protected List<String> getListFromString(String string) {
		List<String> list = new ArrayList<>();

		if (string == null) {
			return list;
		}

		for (String item : StringUtils.split(string, ",")) {
			if (list.contains(item)) {
				continue;
			}

			if (item.startsWith("#")) {
				continue;
			}

			list.add(item);
		}

		Collections.sort(list);

		return list;
	}

	protected final Properties portalTestProperies;

	private static final Pattern _pattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^\\)]+)\\)");

}