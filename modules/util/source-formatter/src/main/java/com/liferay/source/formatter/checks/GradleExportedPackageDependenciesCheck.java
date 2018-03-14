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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class GradleExportedPackageDependenciesCheck extends BaseFileCheck {

	@Override
	public void init() throws Exception {
		_exportPackageBundleSymbolicNames.addAll(
			_getExportPackageBundleSymbolicNames());
	}

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	public void setExportPackageBundleSymbolicNamePrefix(
		String exportPackageBundleSymbolicNamePrefix) {

		_exportPackageBundleSymbolicNamePrefixes.add(
			exportPackageBundleSymbolicNamePrefix);
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (isSubrepository() || isReadOnly(absolutePath)) {
			return content;
		}

		if (absolutePath.contains("/modules/apps/")) {
			for (String dependencies : _getDependenciesBlocks(content)) {
				content = _formatDependencies(content, dependencies);
			}
		}

		return content;
	}

	private String _formatDependencies(String content, String dependencies) {
		int x = dependencies.indexOf("\n");
		int y = dependencies.lastIndexOf("\n");

		if (x == y) {
			return content;
		}

		dependencies = dependencies.substring(x, y + 1);

		StringBundler sb = new StringBundler();

		for (String line : StringUtil.splitLines(dependencies)) {
			String dependencyName = _getDependencyName(line);

			if (Objects.isNull(dependencyName) ||
				line.matches(".*\\s+testCompile\\s+.*")) {

				sb.append(line);
				sb.append("\n");
			}
			else if (_isValidBundleSymbolicName(dependencyName)) {
				sb.append(line);
				sb.append("\n");
			}
		}

		return StringUtil.replace(
			content, StringUtil.trim(dependencies),
			StringUtil.trim(sb.toString()));
	}

	private List<String> _getDependenciesBlocks(String content) {
		List<String> dependenciesBlocks = new ArrayList<>();

		Matcher matcher = _dependenciesPattern.matcher(content);

		while (matcher.find()) {
			int y = matcher.start();

			while (true) {
				y = content.indexOf("}", y + 1);

				if (y == -1) {
					return dependenciesBlocks;
				}

				String dependencies = content.substring(
					matcher.start(2), y + 1);

				int level = getLevel(dependencies, "{", "}");

				if (level == 0) {
					if (!dependencies.contains("}\n")) {
						dependenciesBlocks.add(dependencies);
					}

					break;
				}
			}
		}

		return dependenciesBlocks;
	}

	private String _getDependencyName(String dependency) {
		Matcher matcher = _dependencyNamePattern.matcher(dependency);

		if (!matcher.find()) {
			return StringPool.BLANK;
		}

		return matcher.group(1);
	}

	private Set<String> _getExportPackageBundleSymbolicNames()
		throws Exception {

		File portalDir = getPortalDir();

		if (portalDir == null) {
			return Collections.emptySet();
		}

		final List<File> files = new ArrayList<>();

		Files.walkFileTree(
			portalDir.toPath(), EnumSet.noneOf(FileVisitOption.class), 15,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					for (PathMatcher pathMatcher : _PATH_MATCHERS) {
						if (pathMatcher.matches(dirPath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					Path path = dirPath.resolve("bnd.bnd");

					if (Files.exists(path)) {
						files.add(path.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		Set<String> bundleSymbolicNames = new HashSet<>();

		for (File file : files) {
			String content = FileUtil.read(file);

			if (!content.contains("Export-Package:")) {
				continue;
			}

			String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
				content, "Bundle-SymbolicName");

			if (bundleSymbolicName == null) {
				continue;
			}

			if (bundleSymbolicName.startsWith("com.liferay")) {
				bundleSymbolicNames.add(bundleSymbolicName);
			}
		}

		return bundleSymbolicNames;
	}

	private boolean _isValidBundleSymbolicName(String dependencyName) {
		if (!dependencyName.startsWith("com.liferay.")) {
			return true;
		}

		for (String s : _exportPackageBundleSymbolicNamePrefixes) {
			if (dependencyName.startsWith(s)) {
				return true;
			}
		}

		if (_exportPackageBundleSymbolicNames.contains(dependencyName)) {
			return true;
		}

		return false;
	}

	private static final FileSystem _FILE_SYSTEM = FileSystems.getDefault();

	private static final PathMatcher[] _PATH_MATCHERS = {
		_FILE_SYSTEM.getPathMatcher("glob:**/.git/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.gradle/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.idea/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.m2/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.settings/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/bin/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/build/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/classes/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/sql/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/src/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/test-classes/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/test-coverage/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/test-results/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/tmp/**")
	};

	private final Pattern _dependenciesPattern = Pattern.compile(
		"(\n|\\A)(\t*)dependencies \\{\n");
	private final Pattern _dependencyNamePattern = Pattern.compile(
		".*, name: \"([^\"]*)\".*");
	private final List<String> _exportPackageBundleSymbolicNamePrefixes =
		new ArrayList<>();
	private final List<String> _exportPackageBundleSymbolicNames =
		new ArrayList<>();

}