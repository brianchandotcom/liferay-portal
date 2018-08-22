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
public abstract class WorkspaceFactory {

	public static BaseWorkspace newBatchWorkspace(
		String gitHubURL, String upstreamBranchName, String batchName) {

		if (!PortalWorkspace.isPortalGitHubURL(gitHubURL)) {
			throw new RuntimeException("Unsupported github url " + gitHubURL);
		}

		if (batchName == null) {
			batchName = "default";
		}

		if (batchName.contains("functional")) {
			new FunctionalBatchPortalWorkspace(gitHubURL, upstreamBranchName);
		}
		else if (batchName.contains("integration") ||
				 batchName.contains("unit")) {

			new JunitBatchPortalWorkspace(gitHubURL, upstreamBranchName);
		}

		return new PortalWorkspace(gitHubURL, upstreamBranchName, false);
	}

	public static BaseWorkspace newTopLevelWorkspace(
		String gitHubURL, String upstreamBranchName) {

		if (!PortalWorkspace.isPortalGitHubURL(gitHubURL)) {
			throw new RuntimeException("Unsupported github url " + gitHubURL);
		}

		return new PortalWorkspace(gitHubURL, upstreamBranchName, true);
	}

}