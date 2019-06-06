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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class PropertiesBuildIncludeDirsCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/build.properties")) {
			return content;
		}

		Matcher matcher = _pattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		StringBundler sb = new StringBundler();

		sb.append(matcher.group(1));
		sb.append("#build.include.dirs=\\");
		sb.append(StringPool.NEW_LINE);

		Set<String> buildIncludeDirs = _getBuildIncludeDirs();

		for (String buildIncludeDir : buildIncludeDirs) {
			sb.append(matcher.group(1));
			sb.append(StringPool.POUND);
			sb.append(StringPool.FOUR_SPACES);
			sb.append(buildIncludeDir);
			sb.append(",\\");
			sb.append(StringPool.NEW_LINE);
		}

		if (!buildIncludeDirs.isEmpty()) {
			sb.setIndex(sb.index() - 2);
		}

		return StringUtil.replaceFirst(content, matcher.group(), sb.toString());
	}

	private Set<String> _getBuildIncludeDirs() throws IOException {
		File modulesDir = new File(getPortalDir(), "modules");

		final Set<String> buildIncludeDirs = new TreeSet<>();

		Files.walkFileTree(
			modulesDir.toPath(), EnumSet.noneOf(FileVisitOption.class), 15,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (ArrayUtil.contains(
							_SKIP_DIR_NAMES,
							String.valueOf(dirPath.getFileName()))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					String moduleDirName = _getModuleDirName(dirPath);

					if (moduleDirName == null) {
						return FileVisitResult.CONTINUE;
					}

					if (buildIncludeDirs.contains(moduleDirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (Files.exists(dirPath.resolve(".lfrbuild-portal")) ||
						Files.exists(dirPath.resolve("ci-merge"))) {

						buildIncludeDirs.add(moduleDirName);

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return buildIncludeDirs;
	}

	private String _getModuleDirName(Path dirPath) {
		String absolutePath = SourceUtil.getAbsolutePath(dirPath) + "/";

		int x = absolutePath.indexOf("/modules/");

		if (x == -1) {
			return null;
		}

		int y = absolutePath.indexOf("/", x + 9);

		if (y == -1) {
			return null;
		}

		y = absolutePath.indexOf("/", y + 1);

		if (y != -1) {
			return absolutePath.substring(x + 9, y);
		}

		return null;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "node_modules_cache",
		"private", "sql", "src", "test", "test-classes", "test-coverage",
		"test-results", "tmp"
	};

	private static final Pattern _pattern = Pattern.compile(
		"([^\\S\\n]*)#build\\.include\\.dirs=\\\\(\\s*#.*)*");

}