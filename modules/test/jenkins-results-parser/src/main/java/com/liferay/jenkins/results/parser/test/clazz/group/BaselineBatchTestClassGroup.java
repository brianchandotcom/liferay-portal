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

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Leslie Wong
 */
public class BaselineBatchTestClassGroup extends BatchTestClassGroup {

	public static class BaselineBatchTestClass extends BaseTestClass {

		protected static BaselineBatchTestClass getInstance(
			String batchName, File moduleBaseDir) {

			return new BaselineBatchTestClass(batchName, moduleBaseDir);
		}

		protected BaselineBatchTestClass(String batchName, File moduleBaseDir) {
			super(moduleBaseDir);

			addTestMethod(batchName);
		}

	}

	protected BaselineBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);

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

			List<File> semanticVersioningMarkerFiles =
				JenkinsResultsParserUtil.findFiles(
					portalModulesBaseDir, "\\.lfrbuild-semantic-versioning");

			for (File semanticVersioningMarkerFile :
					semanticVersioningMarkerFiles) {

				moduleDirsList.add(
					semanticVersioningMarkerFile.getParentFile());
			}
		}

		for (File moduleDir : moduleDirsList) {
			testClasses.add(
				BaselineBatchTestClass.getInstance(batchName, moduleDir));
		}
	}

}