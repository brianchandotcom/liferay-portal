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
public class TestClassGroupFactory {

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory) {

		return newBatchTestClassGroup(
			batchName, portalGitWorkingDirectory, null);
	}

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		if (batchName.startsWith("functional-")) {
			return new FunctionalBatchTestClassGroup(
				batchName, portalGitWorkingDirectory, testSuiteName);
		}

		if (batchName.startsWith("integration-") ||
			batchName.startsWith("unit-")) {

			return new JUnitBatchTestClassGroup(
				batchName, portalGitWorkingDirectory, testSuiteName);
		}

		if (batchName.startsWith("modules-integration-") ||
			batchName.startsWith("modules-unit-")) {

			return new ModulesJUnitBatchTestClassGroup(
				batchName, portalGitWorkingDirectory, testSuiteName);
		}

		if (batchName.startsWith("plugins-compile-")) {
			return new PluginsBatchTestClassGroup(
				batchName, portalGitWorkingDirectory, testSuiteName);
		}

		if (batchName.startsWith("portal-frontend-js-")) {
			return new NPMTestBatchTestClassGroup(
				batchName, portalGitWorkingDirectory, testSuiteName);
		}

		if (batchName.startsWith("tck-")) {
			return new TCKJunitBatchTestClassGroup(
				batchName, portalGitWorkingDirectory, testSuiteName);
		}

		return new DefaultBatchTestClassGroup(
			batchName, portalGitWorkingDirectory, testSuiteName);
	}

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, PortalRepositoryJob portalRepositoryJob) {

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalRepositoryJob.getPortalGitWorkingDirectory();

		String testSuiteName = null;

		if (portalRepositoryJob instanceof PortalAcceptancePullRequestJob) {
			PortalAcceptancePullRequestJob portalAcceptancePullRequestJob =
				(PortalAcceptancePullRequestJob)portalRepositoryJob;

			testSuiteName = portalAcceptancePullRequestJob.getTestSuiteName();
		}

		return newBatchTestClassGroup(
			batchName, portalGitWorkingDirectory, testSuiteName);
	}

}