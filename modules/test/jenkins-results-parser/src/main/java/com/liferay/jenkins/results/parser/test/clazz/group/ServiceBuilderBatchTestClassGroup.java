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
 * @author Yi-Chen Tsai
 */
public class ServiceBuilderBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (testClasses.isEmpty() && (_buildType == BuildType.CORE)) {
			return 1;
		}

		return super.getAxisCount();
	}

	public BuildType getBuildType() {
		return _buildType;
	}

	public static enum BuildType {

		CORE, FULL
	}

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

		@Override
		protected void initTestMethods(
			List<File> modulesProjectDirs, File modulesDir, String taskName) {

			for (File modulesProjectDir : modulesProjectDirs) {
				String path = JenkinsResultsParserUtil.getPathRelativeTo(
					modulesProjectDir, modulesDir);

				String moduleTaskCall = JenkinsResultsParserUtil.combine(
					path, ":", taskName);

				addTestMethod(moduleTaskCall);
			}
		}

	}

	protected static List<File> getModulesProjectDirs(File modulesDir) {
		final List<File> modulesProjectDirs = new ArrayList<>();

		modulesProjectDirs.add(modulesDir);

		return modulesProjectDirs;
	}

	protected ServiceBuilderBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalModulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		if (testRelevantChanges) {
			List<File> modifiedPortalToolsServiceBuilderFiles =
				portalGitWorkingDirectory.getModifiedFilesList(
					"portal-tools-service-builder");

			if (!modifiedPortalToolsServiceBuilderFiles.isEmpty()) {
				_buildType = BuildType.FULL;

				return;
			}

			List<File> modifiedPortalImplFiles =
				portalGitWorkingDirectory.getModifiedFilesList("portal-impl/");

			if (!modifiedPortalImplFiles.isEmpty()) {
				_buildType = BuildType.CORE;
			}
			else {
				List<File> modifiedPortalKernelFiles =
					portalGitWorkingDirectory.getModifiedFilesList(
						"portal-kernel/");

				if (!modifiedPortalKernelFiles.isEmpty()) {
					_buildType = BuildType.CORE;
				}
			}

			List<File> modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers);

			for (File modifiedModuleDir : modifiedModuleDirsList) {
				List<File> serviceXmlFiles = JenkinsResultsParserUtil.findFiles(
					modifiedModuleDir, "service.xml");

				if (!serviceXmlFiles.isEmpty()) {
					moduleDirsList.add(modifiedModuleDir);
				}
			}
		}
		else {
			_buildType = BuildType.FULL;

			moduleDirsList.add(portalModulesBaseDir);
		}

		for (File moduleDir : moduleDirsList) {
			List<File> modulesProjectsDirs = getModulesProjectDirs(moduleDir);

			if (!modulesProjectsDirs.isEmpty()) {
				testClasses.add(
					ServiceBuilderBatchTestClass.getInstance(
						moduleDir, portalModulesBaseDir, modulesProjectsDirs));
			}
		}
	}

	private BuildType _buildType;

}