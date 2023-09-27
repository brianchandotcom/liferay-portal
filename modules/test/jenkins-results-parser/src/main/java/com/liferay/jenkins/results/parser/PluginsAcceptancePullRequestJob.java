/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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