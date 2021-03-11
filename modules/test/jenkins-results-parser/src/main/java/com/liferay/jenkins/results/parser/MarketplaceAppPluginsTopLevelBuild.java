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

import java.io.IOException;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class MarketplaceAppPluginsTopLevelBuild
	extends PluginsTopLevelBuild implements PortalReleaseBuild {

	public MarketplaceAppPluginsTopLevelBuild(
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
		Matcher matcher = _pattern.matcher(
			getParameterValue("TEST_PORTAL_BUILD_NUMBER"));

		if (!matcher.find()) {
			throw new RuntimeException("Please set 'TEST_PORTAL_BUILD_NUMBER'");
		}

		return JenkinsResultsParserUtil.combine(
			matcher.group("major"), ".", matcher.group("minor"), ".x");
	}

	@Override
	public Job.BuildProfile getBuildProfile() {
		Matcher matcher = _pattern.matcher(
			getParameterValue("TEST_PORTAL_BUILD_NUMBER"));

		if (!matcher.find()) {
			throw new RuntimeException("Please set 'TEST_PORTAL_BUILD_NUMBER'");
		}

		String fix = matcher.group("fix");

		if (fix.startsWith("1")) {
			return Job.BuildProfile.DXP;
		}

		return Job.BuildProfile.PORTAL;
	}

	@Override
	public String getPluginName() {
		return getParameterValue("TEST_PACKAGE_FILE_NAME");
	}

	public PortalRelease getPortalRelease() {
		if (_portalRelease != null) {
			return _portalRelease;
		}

		String portalBuildNumber = getParameterValue(
			"TEST_PORTAL_BUILD_NUMBER");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBuildNumber)) {
			return null;
		}

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String portalVersion = JenkinsResultsParserUtil.getProperty(
			buildProperties, "portal.version", portalBuildNumber);

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalVersion)) {
			return null;
		}

		String portalVersionLatest = JenkinsResultsParserUtil.getProperty(
			buildProperties, "portal.version.latest", portalVersion);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalVersionLatest)) {
			_portalRelease = new PortalRelease(portalVersionLatest);

			return _portalRelease;
		}

		_portalRelease = new PortalRelease(portalVersion);

		return _portalRelease;
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?<major>\\d)(?<minor>\\d)(?<fix>\\d+)");

	private PortalRelease _portalRelease;

}