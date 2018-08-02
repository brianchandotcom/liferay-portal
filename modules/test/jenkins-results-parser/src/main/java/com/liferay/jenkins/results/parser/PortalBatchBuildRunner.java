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

	@Override
	public void setup() {
		primaryLocalRepository.setup();

		writeRepositoryProperties();
	}

	protected PortalBatchBuildRunner(Job job, String batchName) {
		super(job, batchName);

		if (!(job instanceof PortalTestClassJob)) {
			throw new RuntimeException("Invalid job type");
		}

		PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		primaryLocalRepository = RepositoryFactory.getLocalRepository(
			portalGitWorkingDirectory.getRepositoryName(),
			portalGitWorkingDirectory.getUpstreamBranchName());

		if (!(primaryLocalRepository instanceof PortalLocalRepository)) {
			throw new RuntimeException("Invalid workspace");
		}

		portalLocalRepository = (PortalLocalRepository)primaryLocalRepository;

		portalLocalRepository.setJobProperties(job);
	}

	protected void writeRepositoryProperties() {
		portalLocalRepository.writeRepositoryPropertiesFiles();
	}

	protected final PortalLocalRepository portalLocalRepository;

}