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
public class BuildRunnerFactory {

	public static BatchBuildRunner newBatchBuildRunner(
		Job job, String gitHubURL, String batchName) {

		if (!PortalWorkspace.isPortalGitHubURL(gitHubURL)) {
			throw new RuntimeException("Unsupported github url " + gitHubURL);
		}

		return new PortalBatchBuildRunner(job, gitHubURL, batchName);
	}

	public static TopLevelBuildRunner newTopLevelBuildRunner(
		Job job, String gitHubURL) {

		if (!PortalWorkspace.isPortalGitHubURL(gitHubURL)) {
			throw new RuntimeException("Unsupported github url " + gitHubURL);
		}

		return new PortalTopLevelBuildRunner(job, gitHubURL);
	}

}