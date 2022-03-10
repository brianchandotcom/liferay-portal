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

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class PluginsTestSuiteJob
	extends PluginsGitRepositoryJob implements TestSuiteJob {

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("plugin_name", _pluginName);

		return jsonObject;
	}

	public String getPluginName() {
		return _pluginName;
	}

	@Override
	public List<File> getPluginsTestBaseDirs() {
		return Arrays.asList(_getPluginTestBaseDir());
	}

	@Override
	public String getTestSuiteName() {
		return getPluginName();
	}

	protected PluginsTestSuiteJob(
		BuildProfile buildProfile, String jobName, String pluginName,
		String upstreamBranchName) {

		super(buildProfile, jobName, upstreamBranchName);

		_pluginName = pluginName;

		_initialize();
	}

	protected PluginsTestSuiteJob(JSONObject jsonObject) {
		super(jsonObject);

		_pluginName = jsonObject.getString("plugin_name");

		_initialize();
	}

	private File _getPluginTestBaseDir() {
		GitWorkingDirectory pluginsGitWorkingDirectory =
			getGitWorkingDirectory();

		return new File(
			pluginsGitWorkingDirectory.getWorkingDirectory(),
			JenkinsResultsParserUtil.combine(
				"portlets/", getPluginName(), "/test/functional"));
	}

	private void _initialize() {
		jobPropertiesFiles.add(
			new File(_getPluginTestBaseDir(), "test.properties"));
	}

	private final String _pluginName;

}