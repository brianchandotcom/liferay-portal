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
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Leslie Wong
 */
public class SemVerBaselineBatchTestClassGroup extends BatchTestClassGroup {

	public static class SemVerBaselineBatchTestClass extends BaseTestClass {

		protected static SemVerBaselineBatchTestClass getInstance(
			String batchName, File moduleBaseDir) {

			return new SemVerBaselineBatchTestClass(batchName, moduleBaseDir);
		}

		protected SemVerBaselineBatchTestClass(
			String batchName, File moduleBaseDir) {

			super(moduleBaseDir);

			addTestMethod(batchName);
		}

	}

	protected SemVerBaselineBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		try {
			excludesPathMatchers.addAll(
				getPathMatchers(
					JenkinsResultsParserUtil.combine(
						"modules.excludes[", batchName, "]"),
					portalGitWorkingDirectory.getWorkingDirectory()));

			includesPathMatchers.addAll(
				getPathMatchers(
					JenkinsResultsParserUtil.combine(
						"modules.includes[", batchName, "]"),
					portalGitWorkingDirectory.getWorkingDirectory()));

			setTestClasses();

			setAxisTestClassGroups();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		List<File> moduleDirsList =
			portalGitWorkingDirectory.getModifiedModuleDirsList(
				excludesPathMatchers, includesPathMatchers);

		if (!testRelevantChanges) {
			moduleDirsList = portalGitWorkingDirectory.getModuleDirsList(
				excludesPathMatchers, includesPathMatchers);

			File portalModulesBaseDir = new File(
				portalGitWorkingDirectory.getWorkingDirectory(), "modules");

			List<File> semVerMarkerFiles = JenkinsResultsParserUtil.findFiles(
				portalModulesBaseDir, "\\.lfrbuild-semantic-versioning");

			for (File semVerMarkerFile : semVerMarkerFiles) {
				moduleDirsList.add(semVerMarkerFile.getParentFile());
			}
		}

		for (File moduleDir : moduleDirsList) {
			testClasses.add(
				SemVerBaselineBatchTestClass.getInstance(batchName, moduleDir));
		}
	}

}