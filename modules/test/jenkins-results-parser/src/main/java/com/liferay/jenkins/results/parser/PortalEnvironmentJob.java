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

import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.io.File;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalEnvironmentJob
	extends BaseJob implements PortalTestClassJob {

	@Override
	public Set<String> getDistTypes() {
		return new HashSet<>();
	}

	@Override
	public List<String> getJobPropertyOptions() {
		List<String> jobPropertyOptions = super.getJobPropertyOptions();

		jobPropertyOptions.add(_portalUpstreamBranchName);

		return jobPropertyOptions;
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put(
			"portal_upstream_branch_name", _portalUpstreamBranchName);

		return jsonObject;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			_portalUpstreamBranchName);
	}

	protected PortalEnvironmentJob(
		BuildProfile buildProfile, String jobName,
		String portalUpstreamBranchName) {

		super(buildProfile, jobName);

		_portalUpstreamBranchName = portalUpstreamBranchName;

		_initialize();
	}

	protected PortalEnvironmentJob(JSONObject jsonObject) {
		super(jsonObject);

		_portalUpstreamBranchName = jsonObject.getString(
			"portal_upstream_branch_name");

		_initialize();
	}

	@Override
	protected Set<String> getRawBatchNames() {
		JobProperty jobProperty = getJobProperty("environment.job.names");

		recordJobProperty(jobProperty);

		return getSetFromString(jobProperty.getValue());
	}

	private void _initialize() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));

		GitWorkingDirectory jenkinsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newJenkinsGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/dependencies/test-environment.properties"));
	}

	private final String _portalUpstreamBranchName;

}