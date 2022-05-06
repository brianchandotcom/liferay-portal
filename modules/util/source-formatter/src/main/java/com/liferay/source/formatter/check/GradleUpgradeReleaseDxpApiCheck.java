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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.GradleSourceUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kevin Lee
 */
public class GradleUpgradeReleaseDxpApiCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("build.gradle")) {
			return content;
		}

		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		if (StringUtil.equals(upgradeToVersion, StringPool.BLANK)) {
			return content;
		}

		Map<String, Set<String>> versionsMap = _getVersionsMap(
			upgradeToVersion);

		if (versionsMap == null) {
			return content;
		}

		for (String dependenciesBlock :
				GradleSourceUtil.getDependenciesBlocks(content)) {

			int x = dependenciesBlock.indexOf("\n");
			int y = dependenciesBlock.lastIndexOf("\n");

			if (x == y) {
				continue;
			}

			String dependencies = dependenciesBlock.substring(x, y + 1);

			content = _formatDependencies(
				content, dependencies, upgradeToVersion, versionsMap);
		}

		return content;
	}

	private String _formatDependencies(
		String content, String dependencies, String upgradeToVersion,
		Map<String, Set<String>> versionsMap) {

		Set<String> uniqueDependencies = new TreeSet<>();

		boolean hasTestDependencies = false;
		boolean hasCompileDependencies = false;

		for (String dependency : StringUtil.splitLines(dependencies)) {
			dependency = dependency.trim();

			Matcher matcher = _dependencyAttributesPattern.matcher(dependency);

			Map<String, String> attributesMap = new HashMap<>();

			while (matcher.find()) {
				attributesMap.put(matcher.group(1), matcher.group(2));
			}

			if (!(attributesMap.containsKey("group") &&
				  attributesMap.containsKey("name"))) {

				uniqueDependencies.add(dependency);

				continue;
			}

			String group = attributesMap.get("group");

			if (!versionsMap.containsKey(group)) {
				uniqueDependencies.add(dependency);

				continue;
			}

			String name = attributesMap.get("name");

			Set<String> names = versionsMap.get(group);

			if (!names.contains(name)) {
				uniqueDependencies.add(dependency);
			}

			String configuration = GradleSourceUtil.getConfiguration(
				dependency);

			if (configuration.startsWith("compile")) {
				hasCompileDependencies = true;
			}
			else if (configuration.startsWith("test")) {
				hasTestDependencies = true;
			}
		}

		if (hasCompileDependencies) {
			uniqueDependencies.add(
				StringBundler.concat(
					"compileOnly group: \"com.liferay.portal\", name: ",
					"\"release.dxp.api\", version: \"", upgradeToVersion,
					"\""));
		}

		if (hasTestDependencies) {
			uniqueDependencies.add(
				StringBundler.concat(
					"testCompile group: \"com.liferay.portal\", name: ",
					"\"release.dxp.api\", version: \"", upgradeToVersion,
					"\""));
		}

		Stream<String> stream = uniqueDependencies.stream();

		String newDependencies = stream.map(
			uniqueDependency -> "\t" + uniqueDependency + "\n"
		).collect(
			Collectors.joining()
		);

		return StringUtil.replace(
			content, dependencies, "\n" + newDependencies);
	}

	private Map<String, Set<String>> _getVersionsMap(String upgradeToVersion)
		throws IOException {

		if (StringUtil.equals(_upgradeToVersion, upgradeToVersion)) {
			return _versionsMap;
		}

		ClassLoader classLoader =
			GradleUpgradeReleaseDxpApiCheck.class.getClassLoader();

		String content = StringUtil.read(
			classLoader.getResourceAsStream(
				"dependencies/versions/" + upgradeToVersion + ".txt"));

		if (StringUtil.equals(content, StringPool.BLANK)) {
			return null;
		}

		_upgradeToVersion = upgradeToVersion;

		_versionsMap = new HashMap<>();

		for (String line : StringUtil.splitLines(content)) {
			String[] parts = line.split(":");

			_versionsMap.putIfAbsent(parts[0], new HashSet<>());

			Set<String> names = _versionsMap.get(parts[0]);

			names.add(parts[1]);
		}

		return _versionsMap;
	}

	private static final Pattern _dependencyAttributesPattern = Pattern.compile(
		"(\\w+): \"?([\\w.-]+)\"?");

	private String _upgradeToVersion;
	private Map<String, Set<String>> _versionsMap;

}