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
public class PortalBatchBuildRunner extends BatchBuildRunner {

	protected PortalBatchBuildRunner(
		Job job, String portalGitHubURL, String batchName) {

		super(job, batchName);

		_portalGitHubURL = portalGitHubURL;

		if (!(job instanceof PortalTestClassJob)) {
			Class<? extends Job> clazz = job.getClass();

			throw new RuntimeException(
				"Invalid job type " + clazz.getSimpleName());
		}

		PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		_portalUpstreamBranchName =
			portalGitWorkingDirectory.getUpstreamBranchName();
	}

	@Override
	protected void initWorkspace() {
		workspace = WorkspaceFactory.newBatchWorkspace(
			_portalGitHubURL, _portalUpstreamBranchName, getBatchName());

		if (!(workspace instanceof BatchPortalWorkspace)) {
			throw new RuntimeException("Invalid workspace");
		}
	}

	private final String _portalGitHubURL;
	private final String _portalUpstreamBranchName;

}