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
import java.util.List;

/**
 * @author Leslie Wong
 */
public class ModulesSemVerBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	public static class ModulesSemVerBatchTestClass
		extends ModulesBatchTestClass {

		protected static ModulesSemVerBatchTestClass getInstance(
			File moduleBaseDir, File modulesDir,
			List<File> modulesProjectDirs) {

			return new ModulesSemVerBatchTestClass(
				moduleBaseDir, modulesDir, modulesProjectDirs);
		}

		protected ModulesSemVerBatchTestClass(
			File moduleBaseDir, File modulesDir,
			List<File> modulesProjectDirs) {

			super(moduleBaseDir);

			initTestMethods(modulesProjectDirs, modulesDir, "baseline");
		}

	}

	protected static List<File> getModulesProjectDirs(File moduleBaseDir) {
		final List<File> modulesProjectDirs = new ArrayList<>();
		final Path moduleBaseDirPath = moduleBaseDir.toPath();

		try {
			Files.walkFileTree(
				moduleBaseDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes attrs) {

						if (filePath.equals(moduleBaseDirPath)) {
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
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get module marker files from " +
					moduleBaseDir.getPath(),
				ioe);
		}

		return modulesProjectDirs;
	}

	protected ModulesSemVerBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalModulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		if ((testSuiteName == null) || testSuiteName.equals("default")) {
			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModuleDirsList(
					excludesPathMatchers, includesPathMatchers));

			List<File> semVerMarkerFiles = JenkinsResultsParserUtil.findFiles(
				portalModulesBaseDir, "\\.lfrbuild-semantic-versioning");

			for (File semVerMarkerFile : semVerMarkerFiles) {
				moduleDirsList.add(semVerMarkerFile.getParentFile());
			}
		}
		else {
			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}

		for (File moduleDir : moduleDirsList) {
			List<File> modulesProjectsDirs = getModulesProjectDirs(moduleDir);

			if (!modulesProjectsDirs.isEmpty()) {
				testClasses.add(
					ModulesSemVerBatchTestClass.getInstance(
						moduleDir, portalModulesBaseDir, modulesProjectsDirs));
			}
		}
	}

}