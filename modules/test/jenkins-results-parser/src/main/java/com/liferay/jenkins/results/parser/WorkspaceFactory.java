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

import java.lang.reflect.Proxy;

/**
 * @author Michael Hashimoto
 */
public abstract class WorkspaceFactory {

	public static BatchWorkspace newBatchWorkspace(
		String gitHubURL, String upstreamBranchName, String batchName,
		String branchSHA) {

		if (gitHubURL == null) {
			throw new RuntimeException("GitHub URL is null");
		}

		if (!BasePortalWorkspace.isPortalGitHubURL(gitHubURL)) {
			throw new RuntimeException("Unsupported GitHub URL " + gitHubURL);
		}

		if (batchName == null) {
			batchName = "default";
		}

		BatchWorkspace batchWorkspace = null;

		if (batchName.contains("functional")) {
			batchWorkspace = new FunctionalBatchPortalWorkspace(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else if (batchName.contains("integration") ||
				 batchName.contains("unit")) {

			batchWorkspace = new JunitBatchPortalWorkspace(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else {
			batchWorkspace = new BatchPortalWorkspace(
				gitHubURL, upstreamBranchName, branchSHA);
		}

		if (batchWorkspace == null) {
			throw new RuntimeException("Invalid workspace");
		}

		return (BatchWorkspace)Proxy.newProxyInstance(
			BatchWorkspace.class.getClassLoader(),
			new Class<?>[] {BatchWorkspace.class},
			new MethodLogger(batchWorkspace));
	}

	public static TopLevelWorkspace newTopLevelWorkspace(
		String gitHubURL, String upstreamBranchName) {

		if (gitHubURL == null) {
			throw new RuntimeException("GitHub URL is null");
		}

		if (!BasePortalWorkspace.isPortalGitHubURL(gitHubURL)) {
			throw new RuntimeException("Unsupported GitHub URL " + gitHubURL);
		}

		TopLevelWorkspace topLevelWorkspace = new TopLevelPortalWorkspace(
			gitHubURL, upstreamBranchName);

		return (TopLevelWorkspace)Proxy.newProxyInstance(
			TopLevelWorkspace.class.getClassLoader(),
			new Class<?>[] {TopLevelWorkspace.class},
			new MethodLogger(topLevelWorkspace));
	}

}