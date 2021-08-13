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

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yi-Chen Tsai
 */
public class RESTBuilderModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		if ((_buildType == BuildType.FULL) || testClasses.isEmpty()) {
			return 1;
		}

		return super.getAxisCount();
	}

	public BuildType getBuildType() {
		return _buildType;
	}

	public static enum BuildType {

		FULL

	}

	public static class RESTBuilderModulesBatchTestClass
		extends ModulesBatchTestClass {

		protected static RESTBuilderModulesBatchTestClass getInstance(
			File moduleBaseDir, File modulesDir,
			List<File> modulesProjectDirs) {

			return new RESTBuilderModulesBatchTestClass(
				new File(
					JenkinsResultsParserUtil.getCanonicalPath(moduleBaseDir)),
				modulesDir, modulesProjectDirs);
		}

		protected RESTBuilderModulesBatchTestClass(
			File testClassFile, File modulesDir,
			List<File> modulesProjectDirs) {

			super(testClassFile);

			initTestClassMethods(modulesProjectDirs, modulesDir, "buildREST");
		}

	}

	protected static List<File> getModulesProjectDirs(File moduleBaseDir) {
		final List<File> modulesProjectDirs = new ArrayList<>();
		Path moduleBaseDirPath = moduleBaseDir.toPath();

		try {
			Files.walkFileTree(
				moduleBaseDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path filePath,
						BasicFileAttributes basicFileAttributes) {

						File currentDirectory = filePath.toFile();
						String filePathString = filePath.toString();

						if (filePathString.endsWith("-impl")) {
							File restConfigYAMLFile = new File(
								currentDirectory, "rest-config.yaml");
							File restOpenAPIYAMLFile = new File(
								currentDirectory, "rest-openapi.yaml");

							if (restConfigYAMLFile.exists() &&
								restOpenAPIYAMLFile.exists()) {

								modulesProjectDirs.add(currentDirectory);

								return FileVisitResult.SKIP_SUBTREE;
							}
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get module marker files from " +
					moduleBaseDir.getPath(),
				ioException);
		}

		return modulesProjectDirs;
	}

	protected RESTBuilderModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setAxisTestClassGroups() {
		int testClassCount = testClasses.size();

		int axisCount = getAxisCount();

		if ((testClassCount == 0) && (axisCount == 1)) {
			axisTestClassGroups.add(
				0, TestClassGroupFactory.newAxisTestClassGroup(this));

			return;
		}

		super.setAxisTestClassGroups();
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalModulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		if (testRelevantChanges &&
			!(includeStableTestSuite && isStableTestSuiteBatch())) {

			List<File> modifiedFiles =
				portalGitWorkingDirectory.getModifiedFilesList();

			List<File> modifiedPortalToolsRESTBuilderFiles =
				JenkinsResultsParserUtil.getIncludedFiles(
					null,
					getPathMatchers(
						"util/portal-tools-rest-builder/**",
						portalModulesBaseDir),
					modifiedFiles);

			if (!modifiedPortalToolsRESTBuilderFiles.isEmpty()) {
				_buildType = BuildType.FULL;

				return;
			}

			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}
		else {
			_buildType = BuildType.FULL;

			return;
		}

		for (File moduleDir : moduleDirsList) {
			List<File> modulesProjectDirs = getModulesProjectDirs(moduleDir);

			if (!modulesProjectDirs.isEmpty()) {
				testClasses.add(
					RESTBuilderModulesBatchTestClass.getInstance(
						moduleDir, portalModulesBaseDir, modulesProjectDirs));
			}
		}

		Collections.sort(testClasses);
	}

	private BuildType _buildType;

}