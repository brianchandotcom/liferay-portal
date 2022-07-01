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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.processor.PropertiesSourceProcessor;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesPortalFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (((isPortalSource() || isSubrepository()) &&
			 fileName.matches(".*/portal(-[^-/]+)*\\.properties")) ||
			(!isPortalSource() && !isSubrepository() &&
			 fileName.endsWith("portal.properties"))) {

			content = _generateFeatureFlags(content);
			content = _sortPortalProperties(absolutePath, content);

			content = _formatPortalProperties(absolutePath, content);
		}

		return content;
	}

	private String _formatPortalProperties(String absolutePath, String content)
		throws IOException {

		List<String> allowedSingleLinePropertyKeys = getAttributeValues(
			_ALLOWED_SINGLE_LINE_PROPERTY_KEYS, absolutePath);

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.matches("    [^# ]+?=[^,]+(,[^ ][^,]+)+")) {
					String propertyKey = StringUtil.extractFirst(
						StringUtil.trimLeading(line), "=");

					if (!propertyKey.contains("regex") &&
						!allowedSingleLinePropertyKeys.contains(propertyKey)) {

						line = line.replaceFirst("=", "=\\\\\n        ");

						line = line.replaceAll(",", ",\\\\\n        ");
					}
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
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

		if (featureFlags.isEmpty()) {
			return content;
		}

		ListUtil.distinct(featureFlags, new NaturalOrderStringComparator());

		matcher = _featureFlagsPattern.matcher(content);

		if (matcher.find()) {
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

			content = StringUtil.replaceFirst(
				content, matcher.group(2), sb.toString(), matcher.start(2));
		}

		return content;
	}

	private synchronized String _getPortalPortalPropertiesContent(
			String absolutePath)
		throws IOException {

		if (_portalPortalPropertiesContent != null) {
			return _portalPortalPropertiesContent;
		}

		if (isPortalSource() || isSubrepository()) {
			_portalPortalPropertiesContent = getPortalContent(
				"portal-impl/src/portal.properties", absolutePath);

			if (_portalPortalPropertiesContent == null) {
				_portalPortalPropertiesContent = StringPool.BLANK;
			}

			return _portalPortalPropertiesContent;
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			_portalPortalPropertiesContent = IOUtils.toString(url);
		}
		else {
			_portalPortalPropertiesContent = StringPool.BLANK;
		}

		return _portalPortalPropertiesContent;
	}

	private String _getPropertyCluster(String content, int lineNumber) {
		StringBundler sb = new StringBundler();

		while (true) {
			String line = getLine(content, lineNumber);

			if (Validator.isNull(line)) {
				sb.setIndex(sb.index() - 1);

				return sb.toString();
			}

			sb.append(line);
			sb.append("\n");

			lineNumber++;
		}
	}

	private String _sortPortalProperties(
		String content, int lineNumber, Collection<Integer> positions,
		Map<Integer, Collection<Integer>> propertyClusterPositionsMap) {

		if (propertyClusterPositionsMap.isEmpty()) {
			return content;
		}

		outerLoop:
		for (Map.Entry<Integer, Collection<Integer>> entry :
				propertyClusterPositionsMap.entrySet()) {

			for (int curPosition : entry.getValue()) {
				for (int position : positions) {
					if (curPosition <= position) {
						continue outerLoop;
					}
				}

				int previousLineNumber = entry.getKey();

				String previousPropertyCluster = _getPropertyCluster(
					content, previousLineNumber);

				String propertyCluster = _getPropertyCluster(
					content, lineNumber);

				content = StringUtil.replaceFirst(
					content, propertyCluster, previousPropertyCluster,
					getLineStartPos(content, lineNumber) - 1);
				content = StringUtil.replaceFirst(
					content, previousPropertyCluster, propertyCluster,
					getLineStartPos(content, previousLineNumber) - 1);

				return content;
			}
		}

		return content;
	}

	private String _sortPortalProperties(
		String content, int lineNumber, int pos,
		Map<Integer, Integer> propertyPositionsMap) {

		for (Map.Entry<Integer, Integer> entry :
				propertyPositionsMap.entrySet()) {

			int curPos = entry.getValue();

			if (curPos <= pos) {
				continue;
			}

			int curLineNumber = entry.getKey();

			String curProperty = getLine(content, curLineNumber);

			String property = getLine(content, lineNumber);

			content = StringUtil.replaceFirst(
				content, property, curProperty,
				getLineStartPos(content, lineNumber) - 1);
			content = StringUtil.replaceFirst(
				content, curProperty, property,
				getLineStartPos(content, curLineNumber) - 1);

			return content;
		}

		return content;
	}

	private String _sortPortalProperties(String absolutePath, String content)
		throws IOException {

		if (absolutePath.endsWith("/portal-impl/src/portal.properties")) {
			return content;
		}

		String portalPortalPropertiesContent =
			_getPortalPortalPropertiesContent(absolutePath);

		Map<Integer, Integer> propertyPositionsMap = new HashMap<>();
		Map<Integer, Collection<Integer>> propertyClusterPositionsMap =
			new HashMap<>();

		int startLineNumber = 1;

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				if (Validator.isNull(line)) {
					if (!propertyPositionsMap.isEmpty()) {
						Collection<Integer> positions =
							propertyPositionsMap.values();

						String newContent = _sortPortalProperties(
							content, startLineNumber, positions,
							propertyClusterPositionsMap);

						if (!newContent.equals(content)) {
							return newContent;
						}

						propertyClusterPositionsMap.put(
							startLineNumber, positions);

						propertyPositionsMap = new HashMap<>();
					}

					startLineNumber = lineNumber + 1;

					continue;
				}

				if (line.matches(" *#.*")) {
					continue;
				}

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					continue;
				}

				String property = StringUtil.trim(line.substring(0, pos + 1));

				pos = portalPortalPropertiesContent.indexOf(
					StringPool.FOUR_SPACES + property);

				if (pos == -1) {
					continue;
				}

				String newContent = _sortPortalProperties(
					content, lineNumber, pos, propertyPositionsMap);

				if (!newContent.equals(content)) {
					return newContent;
				}

				propertyPositionsMap.put(lineNumber, pos);
			}
		}

		if (!propertyPositionsMap.isEmpty()) {
			return _sortPortalProperties(
				content, startLineNumber, propertyPositionsMap.values(),
				propertyClusterPositionsMap);
		}

		return content;
	}

	private static final String _ALLOWED_SINGLE_LINE_PROPERTY_KEYS =
		"allowedSingleLinePropertyKeys";

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".releng", ".settings", "bin",
		"build", "classes", "node_modules", "node_modules_cache", "sdk",
		"poshi", "sql", "source-formatter", "test", "test-classes",
		"test-coverage", "test-results", "tmp"
	};

	private static final Pattern _featureFlagPattern = Pattern.compile(
		"\"(feature\\.flag\\..+?)\"");
	private static final Pattern _featureFlagsPattern = Pattern.compile(
		"(\n|\\A)##\n## Feature Flag\n##(\n\n[\\s\\S]*?)(?=(\n\n##|\\Z))");

	private String _portalPortalPropertiesContent;

}