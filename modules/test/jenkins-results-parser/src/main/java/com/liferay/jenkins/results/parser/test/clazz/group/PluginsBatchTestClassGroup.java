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

import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PluginsGitWorkingDirectory;
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
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PluginsBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		List<String> axisIndexes = new ArrayList<>();

		for (int axisIndex = 0; axisIndex < getAxisCount(); axisIndex++) {
			axisIndexes.add(String.valueOf(axisIndex));

			AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup(
				axisIndex);

			List<File> testClassFiles = axisTestClassGroup.getTestClassFiles();

			sb.append("TEST_PLUGIN_GROUP_");
			sb.append(axisIndex);
			sb.append("=");

			for (File testClassFile : testClassFiles) {
				sb.append(testClassFile.getName());
				sb.append(",");
			}

			if (!testClassFiles.isEmpty()) {
				sb.setLength(sb.length() - 1);
			}

			sb.append("\n");
		}

		sb.append("TEST_PLUGIN_GROUPS=");
		sb.append(JenkinsResultsParserUtil.join(" ", axisIndexes));
		sb.append("\n");

		return sb.toString();
	}

	public static class PluginsBatchTestClass extends BaseTestClass {

		protected static PluginsBatchTestClass getInstance(
			String batchName, File pluginDir) {

			return new PluginsBatchTestClass(
				batchName,
				new File(JenkinsResultsParserUtil.getCanonicalPath(pluginDir)));
		}

		protected PluginsBatchTestClass(String batchName, File testClassFile) {
			super(testClassFile);

			addTestClassMethod(batchName);
		}

	}

	protected PluginsBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		Properties portalReleaseProperties =
			JenkinsResultsParserUtil.getProperties(
				new File(
					portalGitWorkingDirectory.getWorkingDirectory(),
					"release.properties"));

		_pluginsGitWorkingDirectory =
			(PluginsGitWorkingDirectory)
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					portalGitWorkingDirectory.getUpstreamBranchName(),
					JenkinsResultsParserUtil.getProperty(
						portalReleaseProperties, "lp.plugins.dir"));

		excludesPathMatchers.addAll(
			getPathMatchers(
				getFirstPropertyValue("test.batch.plugin.names.excludes"),
				_pluginsGitWorkingDirectory.getWorkingDirectory()));

		includesPathMatchers.addAll(
			getPathMatchers(
				getFirstPropertyValue("test.batch.plugin.names.includes"),
				_pluginsGitWorkingDirectory.getWorkingDirectory()));

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			excludesPathMatchers.addAll(
				getPathMatchers(
					getFirstPropertyValue(
						"test.batch.plugin.names.excludes", batchName,
						NAME_STABLE_TEST_SUITE),
					_pluginsGitWorkingDirectory.getWorkingDirectory()));

			includesPathMatchers.addAll(
				getPathMatchers(
					getFirstPropertyValue(
						"test.batch.plugin.names.includes", batchName,
						NAME_STABLE_TEST_SUITE),
					_pluginsGitWorkingDirectory.getWorkingDirectory()));
		}

		setTestClasses();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	protected void setTestClasses() {
		File workingDirectory =
			_pluginsGitWorkingDirectory.getWorkingDirectory();

		try {
			Files.walkFileTree(
				workingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileExcluded(
								excludesPathMatchers, filePath)) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileIncluded(
								excludesPathMatchers, includesPathMatchers,
								filePath)) {

							File file = filePath.toFile();

							testClasses.add(
								PluginsBatchTestClass.getInstance(
									batchName, file.getParentFile()));
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					workingDirectory.getPath(),
				ioException);
		}

		Collections.sort(testClasses);
	}

	private final PluginsGitWorkingDirectory _pluginsGitWorkingDirectory;

}