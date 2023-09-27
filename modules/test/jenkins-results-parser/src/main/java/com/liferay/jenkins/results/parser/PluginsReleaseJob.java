/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsReleaseJob extends PluginsTestSuiteJob {

	protected PluginsReleaseJob(
		BuildProfile buildProfile, String jobName, String pluginName,
		String upstreamBranchName) {

		super(buildProfile, jobName, pluginName, upstreamBranchName);
	}

	protected PluginsReleaseJob(JSONObject jsonObject) {
		super(jsonObject);
	}

}