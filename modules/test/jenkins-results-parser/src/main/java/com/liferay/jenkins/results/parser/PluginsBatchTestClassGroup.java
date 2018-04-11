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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
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

	protected PluginsBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);

		Properties portalReleaseProperties =
			JenkinsResultsParserUtil.getProperties(
				new File(
					portalGitWorkingDirectory.getWorkingDirectory(),
					"release.properties"));

		try {
			_pluginsGitWorkingDirectory = new PluginsGitWorkingDirectory(
				portalGitWorkingDirectory.getUpstreamBranchName(),
				portalReleaseProperties.getProperty("lp.plugins.dir"));

			_pluginNamesExcludePathMatchers = _getPluginNamesPathMatchers(
				"test.batch.plugin.names.excludes");
			_pluginNamesIncludePathMatchers = _getPluginNamesPathMatchers(
				"test.batch.plugin.names.includes");

			setTestClassFiles();

			setAxisTestClassGroups();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected void setTestClassFiles() {
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

						if (_pathExcluded(filePath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathIncluded(filePath) &&
							!_pathExcluded(filePath)) {

							File file = filePath.toFile();

							testClassFiles.add(file.getParentFile());
						}

						return FileVisitResult.CONTINUE;
					}

					private boolean _pathExcluded(Path path) {
						return _pathMatches(
							path, _pluginNamesExcludePathMatchers);
					}

					private boolean _pathIncluded(Path path) {
						return _pathMatches(
							path, _pluginNamesIncludePathMatchers);
					}

					private boolean _pathMatches(
						Path path, List<PathMatcher> pathMatchers) {

						for (PathMatcher pathMatcher : pathMatchers) {
							if (pathMatcher.matches(path)) {
								return true;
							}
						}

						return false;
					}

				});
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					workingDirectory.getPath(),
				ioe);
		}

		Collections.sort(testClassFiles);
	}

	private List<PathMatcher> _getPluginNamesPathMatchers(String propertyName) {
		String pluginNamesRelativeGlobs = getFirstPropertyValue(propertyName);

		if ((pluginNamesRelativeGlobs == null) ||
			pluginNamesRelativeGlobs.isEmpty()) {

			return new ArrayList<>();
		}

		List<PathMatcher> pathMatchers = new ArrayList<>();

		File workingDirectory =
			_pluginsGitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String pluginNamesRelativeGlob :
				pluginNamesRelativeGlobs.split(",")) {

			FileSystem fileSystem = FileSystems.getDefault();

			pathMatchers.add(
				fileSystem.getPathMatcher(
					JenkinsResultsParserUtil.combine(
						"glob:", workingDirectoryPath, "/",
						pluginNamesRelativeGlob)));
		}

		return pathMatchers;
	}

	private final List<PathMatcher> _pluginNamesExcludePathMatchers;
	private final List<PathMatcher> _pluginNamesIncludePathMatchers;
	private final PluginsGitWorkingDirectory _pluginsGitWorkingDirectory;

}