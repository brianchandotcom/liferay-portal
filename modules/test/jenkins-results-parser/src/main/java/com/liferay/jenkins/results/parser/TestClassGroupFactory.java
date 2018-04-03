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
		String batchName, GitWorkingDirectory gitWorkingDirectory) {

		return newBatchTestClassGroup(batchName, gitWorkingDirectory, null);
	}

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, GitWorkingDirectory gitWorkingDirectory,
		String testSuiteName) {

		if (batchName.startsWith("modules-integration-") ||
			batchName.startsWith("modules-unit-")) {

			return new ModulesJUnitBatchTestClassGroup(
				batchName, gitWorkingDirectory, testSuiteName);
		}
		else if (batchName.startsWith("integration-") ||
				 batchName.startsWith("unit-")) {

			return new JUnitBatchTestClassGroup(
				batchName, gitWorkingDirectory, testSuiteName);
		}

		return new DefaultBatchTestClassGroup(
			batchName, gitWorkingDirectory, testSuiteName);
	}

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, PortalRepositoryJob portalRepositoryJob) {

		GitWorkingDirectory gitWorkingDirectory =
			portalRepositoryJob.getGitWorkingDirectory();

		String testSuiteName = null;

		if (portalRepositoryJob instanceof PortalAcceptancePullRequestJob) {
			PortalAcceptancePullRequestJob portalAcceptancePullRequestJob =
				(PortalAcceptancePullRequestJob)portalRepositoryJob;

			testSuiteName = portalAcceptancePullRequestJob.getTestSuiteName();
		}

		return newBatchTestClassGroup(
			batchName, gitWorkingDirectory, testSuiteName);
	}

}