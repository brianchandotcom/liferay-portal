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

import aQute.bnd.version.Version;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 * @author Hugo Huijser
 */
public class GradleDependencyVersionCheck extends BaseFileCheck {

	public void setEnforceConsistentVersionDependencyNames(
		String enforceConsistentVersionDependencyNames) {

		Collections.addAll(
			_enforceConsistentVersionDependencyNames,
			StringUtil.split(enforceConsistentVersionDependencyNames));
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.contains("/modules/apps/")) {
			content = _formatInconsistentVersions(content);
		}

		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath)) {
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

	private String _formatInconsistentVersions(String content)
		throws IOException {

		Map<String, Version> latestVersionsMap = _getLatestVersionsMap();

		if (latestVersionsMap.isEmpty()) {
			return content;
		}

		for (String dependencyName : _enforceConsistentVersionDependencyNames) {
			Version latestVersion = latestVersionsMap.get(dependencyName);

			if (latestVersion == null) {
				continue;
			}

			Pattern pattern = Pattern.compile(
				"compileOnly .*, name: \"" + dependencyName +
					"\",.* version: \"(.*?)\"");

			Matcher matcher = pattern.matcher(content);

			if (!matcher.find()) {
				continue;
			}

			String version = matcher.group(1);

			if (!version.equals(latestVersion.toString())) {
				return StringUtil.replaceFirst(
					content, version, latestVersion.toString(),
					matcher.start(1));
			}
		}

		return content;
	}

	private List<String> _getBuildGradleFileNames() throws IOException {
		String modulesAppsDirLocation = "modules/apps/";

		for (int i = 0; i < (ToolsUtil.PORTAL_MAX_DIR_LEVEL - 1); i++) {
			File file = new File(getBaseDirName() + modulesAppsDirLocation);

			if (file.exists()) {
				return SourceFormatterUtil.scanForFiles(
					getBaseDirName() + modulesAppsDirLocation, new String[0],
					new String[] {"**/build.gradle"},
					getSourceFormatterExcludes(), false);
			}

			modulesAppsDirLocation = "../" + modulesAppsDirLocation;
		}

		return null;
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

	private synchronized Map<String, Version> _getLatestVersionsMap()
		throws IOException {

		if (_latestVersionsMap != null) {
			return _latestVersionsMap;
		}

		_latestVersionsMap = new HashMap<>();

		if (_enforceConsistentVersionDependencyNames.isEmpty()) {
			return _latestVersionsMap;
		}

		List<String> buildGradleFileNames = _getBuildGradleFileNames();

		if (buildGradleFileNames == null) {
			return _latestVersionsMap;
		}

		for (String buildGradleFileName : buildGradleFileNames) {
			String buildGradleFileContent = FileUtil.read(
				new File(buildGradleFileName));

			for (String dependencyName :
					_enforceConsistentVersionDependencyNames) {

				Pattern pattern = Pattern.compile(
					"compileOnly .*, name: \"" + dependencyName +
						"\",.* version: \"(.*?)\"");

				Matcher matcher = pattern.matcher(buildGradleFileContent);

				if (!matcher.find()) {
					continue;
				}

				Version latestVerion = _latestVersionsMap.get(dependencyName);
				Version version = new Version(matcher.group(1));

				if ((latestVerion == null) ||
					(version.compareTo(latestVerion) > 0)) {

					_latestVersionsMap.put(dependencyName, version);
				}
			}
		}

		return _latestVersionsMap;
	}

	private String _getMajorVersion(String version) {
		Matcher matcher = _majorVersionPattern.matcher(version);

		if (!matcher.find()) {
			return StringPool.BLANK;
		}

		return matcher.group();
	}

	private String _getModulesPropertiesContent() throws IOException {
		if (!isPortalSource()) {
			return getPortalContent(_MODULES_PROPERTIES_FILE_NAME);
		}

		return getContent(
			_MODULES_PROPERTIES_FILE_NAME, ToolsUtil.PORTAL_MAX_DIR_LEVEL);
	}

	private synchronized Map<String, Integer> _getPublishedMajorVersionsMap()
		throws IOException {

		if (_publishedMajorVersionsMap != null) {
			return _publishedMajorVersionsMap;
		}

		String content = _getModulesPropertiesContent();

		if (Validator.isNull(content)) {
			_publishedMajorVersionsMap = Collections.emptyMap();

			return _publishedMajorVersionsMap;
		}

		Map<String, String> bundleVersionsMap = new HashMap<>();

		List<String> lines = ListUtil.fromString(content);

		for (String line : lines) {
			String[] array = StringUtil.split(line, StringPool.EQUAL);

			if (array.length == 2) {
				bundleVersionsMap.put(array[0], array[1]);
			}
		}

		_publishedMajorVersionsMap = new HashMap<>();

		for (Map.Entry<String, String> entry : bundleVersionsMap.entrySet()) {
			String bundleVersion = entry.getValue();

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
				entry.getKey(), publishedMajorVersion);
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

	private static final String _MODULES_PROPERTIES_FILE_NAME =
		"modules/modules.properties";

	private static final Pattern _dependenciesPattern = Pattern.compile(
		"(\n|\\A)(\t*)dependencies \\{\n");
	private static final Pattern _dependencyNamePattern = Pattern.compile(
		".*, name: \"([^\"]*)\".*");
	private static final Pattern _dependencyVersionPattern = Pattern.compile(
		".*, version: \"([^\"]*)\".*");
	private static final Pattern _majorVersionPattern = Pattern.compile(
		"^[0-9]+");

	private final List<String> _enforceConsistentVersionDependencyNames =
		new ArrayList<>();
	private Map<String, Version> _latestVersionsMap;
	private Map<String, Integer> _publishedMajorVersionsMap;

}