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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Leslie Wong
 */
public abstract class ModulesBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	public static class ModulesBatchTestClass extends BaseTestClass {

		protected ModulesBatchTestClass(File moduleBaseDir) {
			super(moduleBaseDir);
		}

		protected void initTestClassMethods(
			List<File> modulesProjectDirs, File modulesDir, String taskName) {

			for (File modulesProjectDir : modulesProjectDirs) {
				String path = JenkinsResultsParserUtil.getPathRelativeTo(
					modulesProjectDir, modulesDir);

				String moduleTaskCall = JenkinsResultsParserUtil.combine(
					":", path.replaceAll("/", ":"), ":", taskName);

				addTestClassMethod(moduleTaskCall);
			}
		}

	}

	protected ModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		try {
			File modulesDir = new File(
				portalGitWorkingDirectory.getWorkingDirectory(), "modules");

			String upstreamBranchName =
				portalGitWorkingDirectory.getUpstreamBranchName();

			if (upstreamBranchName.startsWith("ee-") ||
				upstreamBranchName.endsWith("-private")) {

				excludesPathMatchers.addAll(
					getPathMatchers(
						getFirstPropertyValue("modules.excludes.private"),
						modulesDir));

				includesPathMatchers.addAll(
					getPathMatchers(
						getFirstPropertyValue("modules.includes.private"),
						modulesDir));

				if (includeStableTestSuite && isStableTestSuiteBatch()) {
					excludesPathMatchers.addAll(
						getPathMatchers(
							getFirstPropertyValue(
								"modules.excludes.private", batchName,
								NAME_STABLE_TEST_SUITE),
							modulesDir));

					includesPathMatchers.addAll(
						getPathMatchers(
							getFirstPropertyValue(
								"modules.includes.private", batchName,
								NAME_STABLE_TEST_SUITE),
							modulesDir));
				}
			}
			else {
				excludesPathMatchers.addAll(
					getPathMatchers(
						getFirstPropertyValue("modules.excludes.public"),
						modulesDir));

				includesPathMatchers.addAll(
					getPathMatchers(
						getFirstPropertyValue("modules.includes.public"),
						modulesDir));

				if (includeStableTestSuite && isStableTestSuiteBatch()) {
					excludesPathMatchers.addAll(
						getPathMatchers(
							getFirstPropertyValue(
								"modules.excludes.public", batchName,
								NAME_STABLE_TEST_SUITE),
							modulesDir));

					includesPathMatchers.addAll(
						getPathMatchers(
							getFirstPropertyValue(
								"modules.includes.public", batchName,
								NAME_STABLE_TEST_SUITE),
							modulesDir));
				}
			}

			Job.BuildProfile buildProfile =
				portalTestClassJob.getBuildProfile();

			excludesPathMatchers.addAll(
				getPathMatchers(
					getFirstPropertyValue("modules.excludes." + buildProfile),
					modulesDir));

			if (testRelevantChanges) {
				moduleDirsList.addAll(
					getRequiredModuleDirs(
						portalGitWorkingDirectory.getModifiedModuleDirsList(
							excludesPathMatchers, includesPathMatchers)));
			}

			setTestClasses();

			setAxisTestClassGroups();

			setSegmentTestClassGroups();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected abstract void setTestClasses() throws IOException;

	protected Set<File> moduleDirsList = new HashSet<>();

}