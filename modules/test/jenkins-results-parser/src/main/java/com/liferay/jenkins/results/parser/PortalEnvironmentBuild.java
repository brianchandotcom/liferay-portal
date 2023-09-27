/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class PortalEnvironmentBuild extends PortalTopLevelBuild {

	public PortalEnvironmentBuild(String url, TopLevelBuild topLevelBuild) {
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
		String jobName = getJobName();

		return jobName.substring(
			jobName.indexOf("(") + 1, jobName.indexOf(")"));
	}

	@Override
	public Job.BuildProfile getBuildProfile() {
		String branchName = getBranchName();

		if (branchName.startsWith("ee-")) {
			return Job.BuildProfile.PORTAL;
		}

		return Job.BuildProfile.DXP;
	}

}