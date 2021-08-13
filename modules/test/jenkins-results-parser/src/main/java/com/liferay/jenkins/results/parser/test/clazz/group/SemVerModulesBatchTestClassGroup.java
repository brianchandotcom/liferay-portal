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
public class SemVerModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	public static class SemVerModulesBatchTestClass
		extends ModulesBatchTestClass {

		protected static SemVerModulesBatchTestClass getInstance(
			File moduleBaseDir, File modulesDir,
			List<File> modulesProjectDirs) {

			return new SemVerModulesBatchTestClass(
				new File(
					JenkinsResultsParserUtil.getCanonicalPath(moduleBaseDir)),
				modulesDir, modulesProjectDirs);
		}

		protected SemVerModulesBatchTestClass(
			File moduleBaseDir, File modulesDir,
			List<File> modulesProjectDirs) {

			super(moduleBaseDir);

			initTestClassMethods(modulesProjectDirs, modulesDir, "baseline");
		}

	}

	protected static List<File> getModulesProjectDirs(
		File moduleBaseDir, File modulesDir) {

		final File baseDir = modulesDir;
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

						if (filePath.equals(baseDir.toPath())) {
							return FileVisitResult.CONTINUE;
						}

						String filePathString = filePath.toString();

						if (filePathString.endsWith("-test")) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						File currentDirectory = filePath.toFile();

						File bndBndFile = new File(currentDirectory, "bnd.bnd");

						File buildFile = new File(
							currentDirectory, "build.gradle");

						File lfrRelengIgnoreFile = new File(
							currentDirectory, ".lfrbuild-releng-ignore");

						if (buildFile.exists() && bndBndFile.exists() &&
							!lfrRelengIgnoreFile.exists()) {

							modulesProjectDirs.add(currentDirectory);

							return FileVisitResult.SKIP_SUBTREE;
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

	protected SemVerModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalModulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		if (testRelevantChanges &&
			!(includeStableTestSuite && isStableTestSuiteBatch())) {

			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}
		else {
			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModuleDirsList(
					excludesPathMatchers, includesPathMatchers));

			List<File> semVerMarkerFiles = JenkinsResultsParserUtil.findFiles(
				portalModulesBaseDir, "\\.lfrbuild-semantic-versioning");

			for (File semVerMarkerFile : semVerMarkerFiles) {
				moduleDirsList.add(semVerMarkerFile.getParentFile());
			}
		}

		for (File moduleDir : moduleDirsList) {
			List<File> modulesProjectDirs = getModulesProjectDirs(
				moduleDir, portalModulesBaseDir);

			if (!modulesProjectDirs.isEmpty()) {
				testClasses.add(
					SemVerModulesBatchTestClass.getInstance(
						new File(
							JenkinsResultsParserUtil.getCanonicalPath(
								moduleDir)),
						portalModulesBaseDir, modulesProjectDirs));
			}
		}

		Collections.sort(testClasses);
	}

}