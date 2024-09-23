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

	public String getTestrayMainComponentName() {
		return _testrayMainComponentName;
	}

	protected JSUnitModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile, "packageRunTest");

		File testPropertiesBaseDir = getTestPropertiesBaseDir(
			getTestClassFile());

		if ((testPropertiesBaseDir != null) && testPropertiesBaseDir.exists()) {
			_testPropertiesFile = new File(
				testPropertiesBaseDir, "test.properties");

			_testrayMainComponentName = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getProperties(_testPropertiesFile),
				"testray.main.component.name");
		}
		else {
			_testPropertiesFile = null;
			_testrayMainComponentName = null;
		}
	}

	protected JSUnitModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		if (jsonObject.has("file")) {
			_testPropertiesFile = new File(
				jsonObject.getString("file") + "/test.properties");
		}
		else if (jsonObject.has("test_properties_file")) {
			_testPropertiesFile = new File(
				jsonObject.getString("test_properties_file"));
		}
		else {
			_testPropertiesFile = null;
		}

		if (_testPropertiesFile != null) {
			String testrayMainComponentName =
				JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getProperties(_testPropertiesFile),
					"testray.main.component.name");

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					testrayMainComponentName)) {

				File parentFile = _testPropertiesFile.getParentFile();

				parentFile = parentFile.getParentFile();

				testrayMainComponentName = JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getProperties(
						new File(parentFile + "/test.properties")),
					"testray.main.component.name");
			}

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					testrayMainComponentName)) {

				_testrayMainComponentName = null;
			}
			else {
				_testrayMainComponentName = testrayMainComponentName;
			}
		}
		else {
			_testrayMainComponentName = jsonObject.optString(
				"testray_main_component_name");
		}
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

						String currentDirectoryPath =
							currentDirectory.getAbsolutePath();

						if (currentDirectoryPath.contains("modules") &&
							!(currentDirectoryPath.contains("modules/apps") ||
							  currentDirectoryPath.contains("modules/dxp"))) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						if (!testGitrepoJSUnit) {
							File gitrepoFile = new File(
								currentDirectory, ".gitrepo");

							if (gitrepoFile.exists() &&
								!currentDirectoryPath.contains("osb-faro")) {

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

	private final File _testPropertiesFile;
	private final String _testrayMainComponentName;

}