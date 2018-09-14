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

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalWorkbench
	extends BaseWorkbench implements PortalWorkbench {

	@Override
	public void setPortalBuildProperties(Properties properties) {
		_portalLocalGitRepository.setBuildProperties(properties);
	}

	@Override
	public void setPortalJobProperties(Job job) {
		_portalLocalGitRepository.setJobProperties(job);
	}

	protected BasePortalWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);

		LocalGitRepository localGitRepository = getLocalGitRepository();

		if (!(localGitRepository instanceof PortalLocalGitRepository)) {
			throw new RuntimeException("Invalid local Git repository");
		}

		_portalLocalGitRepository =
			(PortalLocalGitRepository)localGitRepository;
	}

	private final PortalLocalGitRepository _portalLocalGitRepository;

}