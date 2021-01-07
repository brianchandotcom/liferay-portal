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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalFixpackEnvironmentJob extends PortalEnvironmentJob {

	protected PortalFixpackEnvironmentJob(
		String jobName, BuildProfile buildProfile, String portalBranchName) {

		super(jobName, buildProfile, portalBranchName);
	}

	@Override
	protected Set<String> getRawBatchNames() {
		String environmentJobNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "fixpack.environment.job.names",
			getPortalBranchName());

		return new HashSet<>(Arrays.asList(environmentJobNames.split(",")));
	}

}