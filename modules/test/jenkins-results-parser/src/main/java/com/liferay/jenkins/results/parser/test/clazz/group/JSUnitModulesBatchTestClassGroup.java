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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JSUnitModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	public static class JSUnitModulesBatchTestClass
		extends ModulesBatchTestClass {

		protected JSUnitModulesBatchTestClass(
			File projectDir, File modulesDir) {

			super(projectDir);

			String path = JenkinsResultsParserUtil.getPathRelativeTo(
				projectDir, modulesDir);

			String moduleTaskCall = JenkinsResultsParserUtil.combine(
				":", path.replaceAll("/", ":"), ":packageRunTest");

			addTestClassMethod(moduleTaskCall);
		}

	}

	protected JSUnitModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		List<File> moduleDirs = new ArrayList<>();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		if (testRelevantChanges) {
			moduleDirs.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}
		else {
			moduleDirs.addAll(
				portalGitWorkingDirectory.getModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}

		final List<File> projectDirs = new ArrayList<>();
		final File modulesDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		final boolean testGitrepoJSUnit = _testGitrepoJSUnit();

		for (File moduleDir : moduleDirs) {
			Path moduleDirPath = moduleDir.toPath();

			Files.walkFileTree(
				moduleDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path filePath,
						BasicFileAttributes basicFileAttributes) {

						if (filePath.equals(modulesDir.toPath())) {
							return FileVisitResult.CONTINUE;
						}

						File file = filePath.toFile();

						File currentDirectory = new File(
							JenkinsResultsParserUtil.getCanonicalPath(file));

						if (!testGitrepoJSUnit) {
							File gitrepoFile = new File(
								currentDirectory, ".gitrepo");

							if (gitrepoFile.exists()) {
								return FileVisitResult.SKIP_SUBTREE;
							}
						}

						File buildGradleFile = new File(
							currentDirectory, "build.gradle");
						File packageJSONFile = new File(
							currentDirectory, "package.json");

						if (!buildGradleFile.exists() ||
							!packageJSONFile.exists()) {

							return FileVisitResult.CONTINUE;
						}

						try {
							JSONObject packageJSONObject = new JSONObject(
								JenkinsResultsParserUtil.read(packageJSONFile));

							if (!packageJSONObject.has("scripts")) {
								return FileVisitResult.CONTINUE;
							}

							JSONObject scriptsJSONObject =
								packageJSONObject.getJSONObject("scripts");

							if (!scriptsJSONObject.has("test")) {
								return FileVisitResult.CONTINUE;
							}

							projectDirs.add(currentDirectory);

							return FileVisitResult.SKIP_SUBTREE;
						}
						catch (IOException | JSONException exception) {
							return FileVisitResult.CONTINUE;
						}
					}

				});
		}

		for (File projectDir : projectDirs) {
			testClasses.add(
				new JSUnitModulesBatchTestClass(projectDir, modulesDir));
		}

		Collections.sort(testClasses);
	}

	private boolean _testGitrepoJSUnit() {
		String testGitrepoJSUnit = JenkinsResultsParserUtil.getProperty(
			portalTestClassJob.getJobProperties(), "test.gitrepo.js.unit",
			portalTestClassJob.getJobName(), getTestSuiteName());

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testGitrepoJSUnit) &&
			testGitrepoJSUnit.equals("true")) {

			return true;
		}

		return false;
	}

}