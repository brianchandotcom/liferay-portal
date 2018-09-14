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

/**
 * @author Michael Hashimoto
 */
public class WorkbenchFactory {

	public static CompanionPortalWorkbench newCompanionPortalWorkbench(
		PortalWorkbench portalWorkbench) {

		if (portalWorkbench == null) {
			throw new RuntimeException("Portal workbench is null");
		}

		return new CompanionPortalWorkbench(portalWorkbench);
	}

	public static OtherPortalWorkbench newOtherPortalWorkbench(
		PortalWorkbench portalWorkbench) {

		if (portalWorkbench == null) {
			throw new RuntimeException("Portal workbench is null");
		}

		return new OtherPortalWorkbench(portalWorkbench);
	}

	public static PluginsWorkbench newPluginsWorkbench(
		PortalWorkbench portalWorkbench) {

		if (portalWorkbench == null) {
			throw new RuntimeException("Portal workbench is null");
		}

		return new PluginsWorkbench(portalWorkbench);
	}

	public static Workbench newWorkbench(
		String gitHubURL, String upstreamBranchName) {

		return newWorkbench(gitHubURL, upstreamBranchName, null);
	}

	public static Workbench newWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		if (gitHubURL.contains("/liferay-jenkins-ee")) {
			return new JenkinsWorkbench(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else if (gitHubURL.contains("/liferay-plugins")) {
			return new PluginsWorkbench(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else if (gitHubURL.contains("/liferay-portal")) {
			return new DefaultPortalWorkbench(
				gitHubURL, upstreamBranchName, branchSHA);
		}

		throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
	}

}