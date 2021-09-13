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

import java.net.MalformedURLException;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalReleasePortalTopLevelBuild extends PortalTopLevelBuild {

	public PortalReleasePortalTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		return "liferay-portal-ee";
	}

	@Override
	public String getBranchName() {
		String portalBranchName = getParameterValue("TEST_PORTAL_BRANCH_NAME");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalBranchName)) {
			return portalBranchName;
		}

		PortalRelease portalRelease = getPortalRelease();

		String portalVersion = portalRelease.getPortalVersion();

		Matcher matcher = _pattern.matcher(portalVersion);

		if (!matcher.find()) {
			throw new RuntimeException(
				"Invalid portal version: " + portalVersion);
		}

		return JenkinsResultsParserUtil.combine(
			matcher.group("major"), ".", matcher.group("minor"), ".x");
	}

	@Override
	public PortalRelease getPortalRelease() {
		if (_portalRelease != null) {
			return _portalRelease;
		}

		try {
			URL portalReleaseTomcatURL = new URL(
				getParameterValue("TEST_PORTAL_RELEASE_TOMCAT_URL"));

			_portalRelease = new PortalRelease(portalReleaseTomcatURL);

			String dependenciesURLString = getParameterValue(
				"TEST_PORTAL_RELEASE_DEPENDENCIES_URL");

			if (_isURL(dependenciesURLString)) {
				_portalRelease.setDependenciesURL(
					new URL(dependenciesURLString));
			}

			String osgiURLString = getParameterValue(
				"TEST_PORTAL_RELEASE_OSGI_URL");

			if (_isURL(osgiURLString)) {
				_portalRelease.setOSGiURL(new URL(osgiURLString));
			}

			String portalWarURLString = getParameterValue(
				"TEST_PORTAL_RELEASE_WAR_URL");

			if (_isURL(portalWarURLString)) {
				_portalRelease.setPortalWarURL(new URL(portalWarURLString));
			}

			String sqlURLString = getParameterValue(
				"TEST_PORTAL_RELEASE_SQL_URL");

			if (_isURL(sqlURLString)) {
				_portalRelease.setSQLURL(new URL(sqlURLString));
			}

			String toolsURLString = getParameterValue(
				"TEST_PORTAL_RELEASE_TOOLS_URL");

			if (_isURL(toolsURLString)) {
				_portalRelease.setToolsURL(new URL(toolsURLString));
			}
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}

		return _portalRelease;
	}

	private boolean _isURL(String urlString) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(urlString) ||
			!urlString.matches("https?://.+")) {

			return false;
		}

		return true;
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?<major>\\d)\\.(?<minor>\\d)\\.(?<fix>\\d+)");

	private PortalRelease _portalRelease;

}