/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JSUnitModulesBatchTestClassGroup;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JSUnitModulesTestClass extends ModulesTestClass {

	protected JSUnitModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile, "packageRunTest");
	}

	protected JSUnitModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

	@Override
	protected List<File> getModulesProjectDirs() {
		final List<File> modulesProjectDirs = new ArrayList<>();

		final boolean testGitrepoJSUnit = _testGitrepoJSUnit();

		final File portalModulesBaseDir = getPortalModulesBaseDir();

		try {
			Files.walkFileTree(
				getModuleBaseDirPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path filePath,
						BasicFileAttributes basicFileAttributes) {

						if (filePath.equals(portalModulesBaseDir.toPath())) {
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

							modulesProjectDirs.add(currentDirectory);

							return FileVisitResult.SKIP_SUBTREE;
						}
						catch (IOException | JSONException exception) {
							return FileVisitResult.CONTINUE;
						}
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return modulesProjectDirs;
	}

	private boolean _testGitrepoJSUnit() {
		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		if (!(batchTestClassGroup instanceof
				JSUnitModulesBatchTestClassGroup)) {

			return false;
		}

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			(JSUnitModulesBatchTestClassGroup)batchTestClassGroup;

		return jsUnitModulesBatchTestClassGroup.testGitrepoJSUnit();
	}

}