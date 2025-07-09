/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestReport;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SemVerModulesTestClass extends ModulesTestClass {

	public DownstreamBuildReport getCachedDownstreamBuildReport() {
		if (!JenkinsResultsParserUtil.isBuildCachingEnabled()) {
			return null;
		}

		if (_cachedTestReportsSearched) {
			getCachedTestReports();
		}

		return _cachedDownstreamBuildReport;
	}

	public List<TestReport> getCachedTestReports() {
		if (!JenkinsResultsParserUtil.isBuildCachingEnabled() ||
			_cachedTestReportsSearched) {

			return _cachedTestReports;
		}

		_cachedTestReports = new ArrayList<>();

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		for (DownstreamBuildReport cachedDownstreamBuildReport :
				batchTestClassGroup.getCachedDownstreamBuildReports()) {

			for (TestReport cachedTestReport :
					cachedDownstreamBuildReport.getTestReports()) {

				Matcher matcher = _modulePathPattern.matcher(
					cachedTestReport.getTestName());

				if (matcher.find()) {
					String modulePath = matcher.group("modulePath");

					if (modulePath.startsWith(getModulePath())) {
						_cachedTestReports.add(cachedTestReport);
					}
				}

				_cachedDownstreamBuildReport = cachedDownstreamBuildReport;

				_cachedTestReportsSearched = true;

				return _cachedTestReports;
			}
		}

		_cachedTestReportsSearched = true;

		return _cachedTestReports;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		if ((_testPropertiesFile != null) && _testPropertiesFile.exists()) {
			jsonObject.put("test_properties_file", _testPropertiesFile);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				_testrayMainComponentName)) {

			jsonObject.put(
				"testray_main_component_name", _testrayMainComponentName);
		}

		return jsonObject;
	}

	public String getModulePath() {
		String modulePath = getName();

		if (modulePath.startsWith("modules")) {
			modulePath = modulePath.substring(7);
		}

		return modulePath;
	}

	public String getTestrayMainComponentName() {
		return _testrayMainComponentName;
	}

	protected SemVerModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, File moduleBaseDir) {

		super(batchTestClassGroup, moduleBaseDir, "baseline");

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

	protected SemVerModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		if (jsonObject.has("test_properties_file")) {
			_testPropertiesFile = new File(
				jsonObject.getString("test_properties_file"));
		}
		else {
			_testPropertiesFile = null;
		}

		_testrayMainComponentName = jsonObject.optString(
			"testray_main_component_name");
	}

	@Override
	protected List<File> getModulesProjectDirs() {
		final List<File> modulesProjectDirs = new ArrayList<>();

		final File portalModulesBaseDir = getPortalModulesBaseDir();

		Path moduleBaseDirPath = getModuleBaseDirPath();

		try {
			Files.walkFileTree(
				moduleBaseDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path filePath,
						BasicFileAttributes basicFileAttributes) {

						if (filePath.equals(portalModulesBaseDir.toPath())) {
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
				"Unable to get module marker files from " + moduleBaseDirPath,
				ioException);
		}

		return modulesProjectDirs;
	}

	private static final Pattern _modulePathPattern = Pattern.compile(
		"testSemanticVersioning\\[(?<modulePath>[\\w\\/-]+)\\]");

	private DownstreamBuildReport _cachedDownstreamBuildReport;
	private List<TestReport> _cachedTestReports;
	private boolean _cachedTestReportsSearched;
	private final File _testPropertiesFile;
	private final String _testrayMainComponentName;

}