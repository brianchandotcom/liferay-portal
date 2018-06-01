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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leslie Wong
 */
public class ModulesCompileBatchTestClassGroup extends BatchTestClassGroup {

	public static class ModulesCompileBatchTestClass extends BaseTestClass {

		protected static ModulesCompileBatchTestClass getInstance(
			String batchName, File moduleBaseDir) {

			return new ModulesCompileBatchTestClass(batchName, moduleBaseDir);
		}

		protected ModulesCompileBatchTestClass(
			String batchName, File moduleBaseDir) {

			super(moduleBaseDir);

			addTestMethod(batchName);
		}

	}

	protected ModulesCompileBatchTestClassGroup(
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

		List<File> moduleDirsList = portalGitWorkingDirectory.getModuleDirsList(
			excludesPathMatchers, includesPathMatchers);

		if (testRelevantChanges) {
			moduleDirsList = new ArrayList<>();

			List<File> modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers);

			for (File modifiedModuleDir : modifiedModuleDirsList) {
				List<File> lfrBuildPortalFiles =
					JenkinsResultsParserUtil.findFiles(
						modifiedModuleDir, "\\.lfrbuild-portal");

				if (!lfrBuildPortalFiles.isEmpty()) {
					moduleDirsList.add(modifiedModuleDir);
				}
			}
		}

		for (File moduleDir : moduleDirsList) {
			testClasses.add(
				ModulesCompileBatchTestClass.getInstance(batchName, moduleDir));
		}
	}

}