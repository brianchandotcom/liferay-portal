/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class ExtraAppsPluginsTopLevelBuild extends PluginsTopLevelBuild {

	public ExtraAppsPluginsTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		String branchName = getBranchName();

		if (branchName.equals("master")) {
			return "liferay-portal";
		}

		return "liferay-portal-ee";
	}

	@Override
	public String getBranchName() {
		return getParameterValue("TEST_PORTAL_BRANCH_NAME");
	}

	@Override
	public String getPluginName() {
		String testBuildExtraAppsZipURLString = getParameterValue(
			"TEST_BUILD_EXTRAAPPS_ZIP_URL");

		if (testBuildExtraAppsZipURLString == null) {
			return null;
		}

		Matcher matcher = _pattern.matcher(testBuildExtraAppsZipURLString);

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("pluginName");
	}

	private static final Pattern _pattern = Pattern.compile(
		"https?://.*/(?<pluginName>[^/]+)");

}