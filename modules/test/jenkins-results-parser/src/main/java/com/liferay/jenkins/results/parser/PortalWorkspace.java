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
public abstract class PortalWorkspace extends BaseWorkspace {

	public static boolean isPortalGitHubURL(String gitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	protected PortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName) {

		Workbench workbench = WorkbenchFactory.newWorkbench(
			portalGitHubURL, portalUpstreamBranchName);

		if (!(workbench instanceof PortalWorkbench)) {
			throw new RuntimeException("Invalid workbench");
		}

		_primaryPortalWorkbench = (PortalWorkbench)workbench;

		_primaryPortalWorkbench.setUp();

		if (!portalUpstreamBranchName.startsWith("ee-")) {
			_companionPortalWorkbench =
				WorkbenchFactory.newCompanionPortalWorkbench(
					_primaryPortalWorkbench);
			_otherPortalWorkbench = WorkbenchFactory.newOtherPortalWorkbench(
				_primaryPortalWorkbench);
		}
		else {
			_companionPortalWorkbench = null;
			_otherPortalWorkbench = null;
		}

		_pluginsWorkbench = WorkbenchFactory.newPluginsWorkbench(
			_primaryPortalWorkbench);
	}

	protected OtherPortalWorkbench getOtherPortalWorkbench() {
		return _otherPortalWorkbench;
	}

	protected PortalWorkbench getPrimaryPortalWorkbench() {
		return _primaryPortalWorkbench;
	}

	@Override
	protected void setUpWorkbenches() {
		setUpJenkinsWorkbench();

		_primaryPortalWorkbench.setUp();

		if (_companionPortalWorkbench != null) {
			_companionPortalWorkbench.setUp();
		}

		if (_otherPortalWorkbench != null) {
			_otherPortalWorkbench.setUp();
		}

		_pluginsWorkbench.setUp();
	}

	protected void setWorkbenchJobProperties(Job job) {
		_primaryPortalWorkbench.setPortalJobProperties(job);
	}

	@Override
	protected void tearDownWorkbenches() {
		tearDownJenkinsWorkbench();

		_primaryPortalWorkbench.tearDown();

		if (_companionPortalWorkbench != null) {
			_companionPortalWorkbench.tearDown();
		}

		if (_otherPortalWorkbench != null) {
			_otherPortalWorkbench.tearDown();
		}

		_pluginsWorkbench.tearDown();
	}

	@Override
	protected void writeWorkbenchPropertiesFiles() {
		_primaryPortalWorkbench.writePropertiesFiles();

		_pluginsWorkbench.writePropertiesFiles();
	}

	private static final Pattern _portalGitHubURLPattern = Pattern.compile(
		"https://github.com/[^/]+/(?<gitRepositoryName>" +
			"liferay-portal(-ee)?)/.*");

	private final CompanionPortalWorkbench _companionPortalWorkbench;
	private final OtherPortalWorkbench _otherPortalWorkbench;
	private final PluginsWorkbench _pluginsWorkbench;
	private final PortalWorkbench _primaryPortalWorkbench;

}