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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsAcceptancePullRequestJob extends PluginsGitRepositoryJob {

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("plugins_test_base_dirs", _pluginsTestBaseDirs);

		return jsonObject;
	}

	@Override
	public List<File> getPluginsTestBaseDirs() {
		return _pluginsTestBaseDirs;
	}

	protected PluginsAcceptancePullRequestJob(
		BuildProfile buildProfile, String jobName, String upstreamBranchName) {

		super(buildProfile, jobName, upstreamBranchName);

		_pluginsTestBaseDirs = new ArrayList<>();

		PluginsGitWorkingDirectory pluginsGitWorkingDirectory =
			portalGitWorkingDirectory.getPluginsGitWorkingDirectory();

		for (File modifiedFile :
				pluginsGitWorkingDirectory.getModifiedFilesList()) {

			File parentDir = new File(modifiedFile.getPath());

			while (parentDir != null) {
				File pluginsTestBaseDir = new File(
					parentDir, "test/functional");

				if (pluginsTestBaseDir.exists()) {
					if (!_pluginsTestBaseDirs.contains(pluginsTestBaseDir)) {
						_pluginsTestBaseDirs.add(pluginsTestBaseDir);
					}

					break;
				}

				parentDir = parentDir.getParentFile();
			}
		}
	}

	protected PluginsAcceptancePullRequestJob(JSONObject jsonObject) {
		super(jsonObject);

		_pluginsTestBaseDirs = new ArrayList<>();

		JSONArray pluginsTestBaseDirJSONArray = jsonObject.getJSONArray(
			"plugins_test_base_dirs");

		if (pluginsTestBaseDirJSONArray == null) {
			return;
		}

		for (int i = 0; i < pluginsTestBaseDirJSONArray.length(); i++) {
			String pluginsTestBaseDirPath =
				pluginsTestBaseDirJSONArray.getString(i);

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					pluginsTestBaseDirPath)) {

				continue;
			}

			File pluginsTestBaseDir = new File(pluginsTestBaseDirPath);

			if (_pluginsTestBaseDirs.contains(pluginsTestBaseDir)) {
				continue;
			}

			_pluginsTestBaseDirs.add(pluginsTestBaseDir);
		}
	}

	private final List<File> _pluginsTestBaseDirs;

}