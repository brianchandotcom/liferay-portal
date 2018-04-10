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

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class TCKJunitBatchTestClassGroup extends JUnitBatchTestClassGroup {

	protected TCKJunitBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);
	}

	@Override
	protected void setTestClassFiles() {
		File workingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

		File tckHomeDirectory = new File(workingDirectory, "tools/tck");

		try {
			Files.walkFileTree(
				tckHomeDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathExcluded(filePath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						if (_pathIncluded(filePath)) {
							testClassFiles.add(
								_getPackagePathClassFile(filePath));
						}

						return FileVisitResult.CONTINUE;
					}

					private File _getPackagePathClassFile(Path path) {
						String filePath = path.toString();

						Matcher matcher = _packagePathPattern.matcher(filePath);

						if (matcher.find()) {
							String packagePath = matcher.group("packagePath");

							return new File(packagePath);
						}

						return new File(filePath.replaceAll("[^/]+\\.war", ""));
					}

					private boolean _pathExcluded(Path path) {
						return _pathMatches(
							path, testClassNamesExcludesPathMatchers);
					}

					private boolean _pathIncluded(Path path) {
						return _pathMatches(
							path, testClassNamesIncludesPathMatchers);
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

					private final Pattern _packagePathPattern = Pattern.compile(
						".*/(?<packagePath>com/.*)/.*\\.war");

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

}