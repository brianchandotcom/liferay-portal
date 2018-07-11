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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class GradleDependencyVersionCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.contains("/modules/apps/")) {
			return content;
		}

		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

		String moduleName = absolutePath.substring(y + 1, x);

		if (!moduleName.contains("test")) {
			for (String dependencies : _getDependenciesBlocks(content)) {
				content = _formatDependencies(content, dependencies);
			}
		}

		return content;
	}

	private String _formatDependencies(String content, String dependencies)
		throws IOException {

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
			else {
				String dependencyVersion = _getDependencyVersion(line);

				if (dependencyVersion.matches("^[0-9.]+") &&
					!_isValidVersion(dependencyName, dependencyVersion)) {

					Map<String, Integer> publishedMajorVersionsMap =
						_getPublishedMajorVersionsMap();

					line = StringUtil.replaceFirst(
						line, dependencyVersion,
						publishedMajorVersionsMap.get(dependencyName) + ".0.0");
				}

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

	private String _getDependencyVersion(String dependency) {
		Matcher matcher = _dependencyVersionPattern.matcher(dependency);

		if (!matcher.find()) {
			return StringPool.BLANK;
		}

		return matcher.group(1);
	}

	private String _getMajorVersion(String version) {
		Matcher matcher = _majorVersionPattern.matcher(version);

		if (!matcher.find()) {
			return StringPool.BLANK;
		}

		return matcher.group();
	}

	private synchronized Map<String, Integer> _getPublishedMajorVersionsMap()
		throws IOException {

		if (_publishedMajorVersionsMap != null) {
			return _publishedMajorVersionsMap;
		}

		File portalDir = getPortalDir();

		if (portalDir == null) {
			_publishedMajorVersionsMap = Collections.emptyMap();

			return _publishedMajorVersionsMap;
		}

		final List<File> files = new ArrayList<>();

		Files.walkFileTree(
			portalDir.toPath(), EnumSet.noneOf(FileVisitOption.class), 15,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					String dirName = String.valueOf(dirPath.getFileName());

					if (ArrayUtil.contains(_SKIP_DIR_NAMES, dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path path = dirPath.resolve("bnd.bnd");

					if (Files.exists(path)) {
						files.add(path.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		_publishedMajorVersionsMap = new HashMap<>();

		for (File file : files) {
			String content = FileUtil.read(file);

			String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
				content, "Bundle-SymbolicName");

			if (Validator.isNull(bundleSymbolicName)) {
				continue;
			}

			String path = SourceUtil.getAbsolutePath(file);

			if (path.endsWith("/portal-impl/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.impl";
			}
			else if (path.endsWith("/portal-kernel/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.kernel";
			}
			else if (path.endsWith("/portal-test-integration/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.test.integration";
			}
			else if (path.endsWith("/portal-test/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.test";
			}
			else if (path.endsWith("/portal-support-tomcat/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.support.tomcat";
			}
			else if (path.endsWith("/util-bridges/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.bridges";
			}
			else if (path.endsWith("/util-java/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.java";
			}
			else if (path.endsWith("/util-slf4j/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.slf4j";
			}
			else if (path.endsWith("/util-taglib/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.taglib";
			}

			if (!bundleSymbolicName.startsWith("com.liferay.")) {
				continue;
			}

			String bundleVersion = BNDSourceUtil.getDefinitionValue(
				content, "Bundle-Version");

			String majorVersion = _getMajorVersion(bundleVersion);

			if (Validator.isNull(majorVersion)) {
				continue;
			}

			int publishedMajorVersion = GetterUtil.getInteger(majorVersion);

			if ((publishedMajorVersion > 1) &&
				bundleVersion.matches(publishedMajorVersion + "[0.]*")) {

				publishedMajorVersion = publishedMajorVersion - 1;
			}

			_publishedMajorVersionsMap.put(
				bundleSymbolicName, publishedMajorVersion);
		}

		return _publishedMajorVersionsMap;
	}

	private boolean _isValidVersion(
			String dependencyName, String dependencyVersion)
		throws IOException {

		Map<String, Integer> publishedMajorVersionsMap =
			_getPublishedMajorVersionsMap();

		Set<String> bundleSymbolicNames = publishedMajorVersionsMap.keySet();

		if (!dependencyName.startsWith("com.liferay.") ||
			!bundleSymbolicNames.contains(dependencyName)) {

			return true;
		}

		if (GetterUtil.getInteger(_getMajorVersion(dependencyVersion)) ==
				publishedMajorVersionsMap.get(dependencyName)) {

			return true;
		}

		return false;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "sql", "src", "test",
		"test-classes", "test-coverage", "test-results", "tmp"
	};

	private final Pattern _dependenciesPattern = Pattern.compile(
		"(\n|\\A)(\t*)dependencies \\{\n");
	private final Pattern _dependencyNamePattern = Pattern.compile(
		".*, name: \"([^\"]*)\".*");
	private final Pattern _dependencyVersionPattern = Pattern.compile(
		".*, version: \"([^\"]*)\".*");
	private final Pattern _majorVersionPattern = Pattern.compile("^[0-9]+");
	private Map<String, Integer> _publishedMajorVersionsMap;

}