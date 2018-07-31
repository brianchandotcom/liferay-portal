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
 * @author Yi-Chen Tsai
 */
public class ServiceBuilderBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	public static class ServiceBuilderBatchTestClass
		extends ModulesBatchTestClass {

		protected static ServiceBuilderBatchTestClass getInstance(
			File moduleBaseDir, File modulesDir,
			List<File> modulesProjectDirs) {

			return new ServiceBuilderBatchTestClass(
				moduleBaseDir, modulesDir, modulesProjectDirs);
		}

		protected ServiceBuilderBatchTestClass(
			File moduleBaseDir, File modulesDir,
			List<File> modulesProjectDirs) {

			super(moduleBaseDir);

			initTestMethods(modulesProjectDirs, modulesDir, "buildService");
		}

	}

	protected ServiceBuilderBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		if (testRelevantChanges) {
			List<File> modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers);

			for (File modifiedModuleDir : modifiedModuleDirsList) {
				List<File> bndBndFiles = JenkinsResultsParserUtil.findFiles(
					modifiedModuleDir, "bnd.bnd");

				List<File> serviceXmlFiles = JenkinsResultsParserUtil.findFiles(
					modifiedModuleDir, "service.xml");

				if (!bndBndFiles.isEmpty() && !serviceXmlFiles.isEmpty()) {
					moduleDirsList.add(modifiedModuleDir);
				}
			}
		}
		else {
			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}
	}

}