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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
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
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesFeatureFlagsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("portal.properties")) {
			return content;
		}

		return _generateFeatureFlags(content);
	}

	private String _generateFeatureFlags(String content) throws IOException {
		List<File> javaFiles = new ArrayList<>();

		File portalDir = getPortalDir();

		Files.walkFileTree(
			portalDir.toPath(), EnumSet.noneOf(FileVisitOption.class), 25,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (ArrayUtil.contains(
							_SKIP_DIR_NAMES,
							String.valueOf(dirPath.getFileName()))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					String absolutePath = SourceUtil.getAbsolutePath(filePath);

					if (!absolutePath.endsWith(".java")) {
						return FileVisitResult.CONTINUE;
					}

					javaFiles.add(filePath.toFile());

					return FileVisitResult.CONTINUE;
				}

			});

		List<String> featureFlags = new ArrayList<>();

		Matcher matcher = null;

		for (File javafile : javaFiles) {
			String javaContent = FileUtil.read(javafile);

			if (!javaContent.contains("\"feature.flag.")) {
				continue;
			}

			matcher = _featureFlagPattern.matcher(javaContent);

			while (matcher.find()) {
				featureFlags.add(matcher.group(1));
			}
		}

		ListUtil.distinct(featureFlags, new NaturalOrderStringComparator());

		matcher = _featureFlagsPattern.matcher(content);

		if (matcher.find()) {
			String matchedFeatureFlags = matcher.group(2);

			if (featureFlags.isEmpty()) {
				if (matchedFeatureFlags.contains("feature.flag.")) {
					return StringUtil.replaceFirst(
						content, matchedFeatureFlags, StringPool.BLANK,
						matcher.start(2));
				}

				return content;
			}

			StringBundler sb = new StringBundler(featureFlags.size() * 14);

			for (String featureFlag : featureFlags) {
				String environmentVariable =
					ToolsUtil.encodeEnvironmentProperty(featureFlag);

				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(StringPool.POUND);
				sb.append(StringPool.NEW_LINE);
				sb.append("    # Env: ");
				sb.append(environmentVariable);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(StringPool.POUND);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(featureFlag);
				sb.append("=false");
			}

			if (matchedFeatureFlags.contains("feature.flag.")) {
				content = StringUtil.replaceFirst(
					content, matchedFeatureFlags, sb.toString(),
					matcher.start(2));
			}
			else {
				content = StringUtil.insert(
					content, sb.toString(), matcher.start(2));
			}
		}

		return content;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".releng", ".settings", "bin",
		"build", "classes", "node_modules", "node_modules_cache", "poshi",
		"sdk", "source-formatter", "sql", "test", "test-classes",
		"test-coverage", "test-results", "tmp"
	};

	private static final Pattern _featureFlagPattern = Pattern.compile(
		"\"(feature\\.flag\\..+?)\"");
	private static final Pattern _featureFlagsPattern = Pattern.compile(
		"(\n|\\A)##\n## Feature Flag\n##(\n\n[\\s\\S]*?)(?=(\n\n##|\\Z))");

}