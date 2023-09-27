/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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