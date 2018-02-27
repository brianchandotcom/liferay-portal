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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class JobFactory {

	public static PortalRepositoryJob newPortalRepositoryJob(String jobName) {
		return newPortalRepositoryJob(jobName, "default");
	}

	public static PortalRepositoryJob newPortalRepositoryJob(
		String jobName, String testSuiteName) {

		if (_jobs.containsKey(jobName)) {
			Job job = _jobs.get(jobName);

			if (job instanceof PortalRepositoryJob) {
				return (PortalRepositoryJob)_jobs.get(jobName);
			}

			throw new RuntimeException(
				jobName + " is not a portal repository job");
		}

		PortalRepositoryJob portalRepositoryJob = null;

		if (jobName.contains("test-portal-acceptance-pullrequest(")) {
			portalRepositoryJob = new PortalAcceptancePullRequestJob(
				jobName, testSuiteName);
		}
		else if (jobName.contains("test-portal-acceptance-upstream(")) {
			portalRepositoryJob = new PortalAcceptanceUpstreamJob(jobName);
		}
		else {
			throw new RuntimeException("Invalid job name " + jobName);
		}

		if (portalRepositoryJob != null) {
			_jobs.put(jobName, portalRepositoryJob);
		}

		return portalRepositoryJob;
	}

	private static final Map<String, Job> _jobs = new HashMap<>();

}