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
public interface PortalBuildData extends BuildData {

	public Job.BuildProfile getBuildProfile();

	public String getPortalBranchSHA();

	public String getPortalGitHubBranchName();

	public String getPortalGitHubRepositoryName();

	public String getPortalGitHubURL();

	public String getPortalGitHubUsername();

	public String getPortalUpstreamBranchName();

	public void setPortalBranchSHA(String portalBranchSHA);

	public void setPortalGitHubURL(String portalGitHubURL);

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName);

	public final String NAME_PORTAL_UPSTREAM_BRANCH_DEFAULT = "master";

	public final String URL_PORTAL_GITHUB_BRANCH_DEFAULT =
		"https://github.com/liferay/liferay-portal/tree/master";

}