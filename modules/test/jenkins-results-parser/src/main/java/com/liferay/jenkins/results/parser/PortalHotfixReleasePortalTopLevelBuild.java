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
public class PortalHotfixReleasePortalTopLevelBuild
	extends PortalTopLevelBuild {

	public PortalHotfixReleasePortalTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		return "liferay-portal-ee";
	}

	@Override
	public String getBranchName() {
		Matcher matcher = _pattern.matcher(
			getParameterValue("TEST_BUILD_HOTFIX_ZIP_URL"));

		if (!matcher.find()) {
			throw new RuntimeException(
				"Please set 'TEST_BUILD_HOTFIX_ZIP_URL'");
		}

		String branchName = JenkinsResultsParserUtil.combine(
			matcher.group("major"), ".", matcher.group("minor"), ".x");

		if (branchName.startsWith("6")) {
			return "ee-" + branchName;
		}

		return branchName;
	}

	private static final Pattern _pattern = Pattern.compile(
		"https?://.*(?<major>\\d)(?<minor>\\d)(?<fix>\\d{2})\\.(lpkg|zip)");

}