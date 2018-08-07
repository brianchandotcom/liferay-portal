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
		Job job, String batchName, String portalGitHubURL) {

		super(job, batchName);

		if (!(job instanceof PortalTestClassJob)) {
			throw new RuntimeException("Invalid job type");
		}

		PortalLocalGitBranch portalLocalGitBranch = getPortalLocalGitBranch(
			portalGitHubURL);

		addLocalGitBranch(portalLocalGitBranch);

		PortalLocalRepository portalLocalRepository =
			getPortalLocalRepository();

		portalLocalRepository.setJobProperties(getJob());
	}

}