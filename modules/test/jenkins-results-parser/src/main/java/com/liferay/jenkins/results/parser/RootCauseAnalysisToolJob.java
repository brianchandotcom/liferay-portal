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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class RootCauseAnalysisToolJob
	extends BaseJob implements PortalTestClassJob {

	@Override
	public Set<String> getDistTypes() {
		return Collections.emptySet();
	}

	public GitWorkingDirectory getJenkinsGitWorkingDirectory() {
		return _jenkinsGitWorkingDirectory;
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("upstream_branch_name", _upstreamBranchName);

		return jsonObject;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _portalGitWorkingDirectory;
	}

	@Override
	public boolean isSegmentEnabled() {
		return true;
	}

	protected RootCauseAnalysisToolJob(
		BuildProfile buildProfile, String jobName, String upstreamBranchName) {

		super(buildProfile, jobName);

		_upstreamBranchName = upstreamBranchName;

		_initialize();
	}

	protected RootCauseAnalysisToolJob(JSONObject jsonObject) {
		super(jsonObject);

		_upstreamBranchName = jsonObject.getString("upstream_branch_name");

		_initialize();
	}

	@Override
	protected Set<String> getRawBatchNames() {
		return new HashSet<>();
	}

	private void _initialize() {
		_jenkinsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newJenkinsGitWorkingDirectory();

		_portalGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				_upstreamBranchName);

		jobPropertiesFiles.add(
			new File(
				_jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/build.properties"));

		jobPropertiesFiles.add(
			new File(
				_jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/dependencies/root-cause-analysis-tool.properties"));

		jobPropertiesFiles.add(
			new File(
				_portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));
	}

	private GitWorkingDirectory _jenkinsGitWorkingDirectory;
	private PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final String _upstreamBranchName;

}