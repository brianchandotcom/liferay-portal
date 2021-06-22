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
 * @author Leslie Wong
 */
public class CompileModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	public static class CompileModulesBatchTestClass
		extends ModulesBatchTestClass {

		protected static CompileModulesBatchTestClass getInstance(
			File moduleBaseDir, File modulesDir) {

			return new CompileModulesBatchTestClass(
				new File(
					JenkinsResultsParserUtil.getCanonicalPath(moduleBaseDir)),
				modulesDir);
		}

		protected CompileModulesBatchTestClass(
			File moduleBaseDir, File modulesDir) {

			super(moduleBaseDir);

			final File baseDir = modulesDir;
			final List<File> modulesProjectDirs = new ArrayList<>();
			final Path moduleBaseDirPath = moduleBaseDir.toPath();

			try {
				Files.walkFileTree(
					moduleBaseDirPath,
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult preVisitDirectory(
							Path filePath, BasicFileAttributes attrs) {

							if (filePath.equals(baseDir.toPath())) {
								return FileVisitResult.CONTINUE;
							}

							File currentDirectory = filePath.toFile();

							File bndBndFile = new File(
								currentDirectory, "bnd.bnd");

							File buildFile = new File(
								currentDirectory, "build.gradle");

							String directoryName = currentDirectory.getName();

							if (buildFile.exists() && bndBndFile.exists()) {
								modulesProjectDirs.add(currentDirectory);

								return FileVisitResult.SKIP_SUBTREE;
							}

							if (directoryName.startsWith("frontend-theme")) {
								File gulpFile = new File(
									currentDirectory, "gulpfile.js");

								if (buildFile.exists() && gulpFile.exists()) {
									modulesProjectDirs.add(currentDirectory);

									return FileVisitResult.SKIP_SUBTREE;
								}
							}

							buildFile = new File(currentDirectory, "build.xml");

							if (directoryName.endsWith("-hook") &&
								buildFile.exists()) {

								modulesProjectDirs.add(currentDirectory);

								return FileVisitResult.SKIP_SUBTREE;
							}

							if (directoryName.endsWith("-portlet")) {
								File ivyFile = new File(
									currentDirectory, "ivy.xml");

								if (buildFile.exists() && ivyFile.exists()) {
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

			initTestClassMethods(modulesProjectDirs, modulesDir, "assemble");
		}

	}

	protected CompileModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		if (testRelevantChanges &&
			!(includeStableTestSuite && isStableTestSuiteBatch())) {

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
		else {
			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}

		File portalModulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		for (File moduleDir : moduleDirsList) {
			testClasses.add(
				CompileModulesBatchTestClass.getInstance(
					new File(
						JenkinsResultsParserUtil.getCanonicalPath(moduleDir)),
					portalModulesBaseDir));
		}

		Collections.sort(testClasses);
	}

}