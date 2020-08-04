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