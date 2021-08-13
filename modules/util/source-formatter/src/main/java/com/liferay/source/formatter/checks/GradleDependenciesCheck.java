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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.GradleSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Peter Shin
 */
public class GradleDependenciesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		List<String> dependenciesBlocks =
			GradleSourceUtil.getDependenciesBlocks(content);

		if (dependenciesBlocks.isEmpty()) {
			return content;
		}

		String releasePortalAPIVersion = getAttributeValue(
			_RELEASE_PORTAL_API_VERSION_KEY, absolutePath);

		for (String dependenciesBlock : dependenciesBlocks) {
			int x = dependenciesBlock.indexOf("\n");
			int y = dependenciesBlock.lastIndexOf("\n");

			if (x == y) {
				continue;
			}

			String dependencies = dependenciesBlock.substring(x, y + 1);

			if (isAttributeValue(
					_CHECK_TEST_INTEGRATION_COMPILE_DEPENDENCIES_KEY,
					absolutePath)) {

				content = _formatTestIntegrationCompileDependencies(
					content, dependencies, _petraPattern);
				content = _formatTestIntegrationCompileDependencies(
					content, dependencies, _portalKernelPattern);
			}

			content = _formatDependencies(
				content, SourceUtil.getIndent(dependenciesBlock), dependencies,
				releasePortalAPIVersion);

			if (isAttributeValue(_CHECK_PETRA_DEPENDENCIES_KEY, absolutePath) &&
				absolutePath.contains("/modules/core/petra/")) {

				_checkPetraDependencies(fileName, content, dependencies);
			}

			_checkCommerceDependencies(
				fileName, absolutePath, content, dependencies,
				getAttributeValues(
					_ALLOWED_COMMERCE_DEPENDENCIES_MODULE_PATH_NAMES,
					absolutePath));
		}

		return content;
	}

	private void _checkCommerceDependencies(
		String fileName, String absolutePath, String content,
		String dependencies,
		List<String> allowedCommerceDependenciesModulePathNames) {

		if (!isModulesFile(absolutePath) ||
			absolutePath.contains("/commerce/")) {

			return;
		}

		for (String line : StringUtil.splitLines(dependencies)) {
			if (Validator.isNull(line) ||
				!line.matches(
					"\\s*compileOnly project\\(\".*?:apps:commerce.+?\"\\)")) {

				continue;
			}

			for (String allowedCommerceDependenciesModulePathName :
					allowedCommerceDependenciesModulePathNames) {

				if (absolutePath.contains(
						allowedCommerceDependenciesModulePathName)) {

					return;
				}
			}

			addMessage(
				fileName,
				"Modules that are outside of Commerce are not allowed to " +
					"depend on Commerce modules",
				SourceUtil.getLineNumber(content, content.indexOf(line)));
		}
	}

	private void _checkPetraDependencies(
		String fileName, String content, String dependencies) {

		for (String line : StringUtil.splitLines(dependencies)) {
			if (Validator.isNotNull(line) && !line.contains("petra")) {
				addMessage(
					fileName,
					"Only modules/core/petra dependencies are allowed",
					SourceUtil.getLineNumber(content, content.indexOf(line)));
			}
		}
	}

	private String _formatDependencies(
		String content, String indent, String dependencies,
		String releasePortalAPIVersion) {

		Matcher matcher = _incorrectWhitespacePattern.matcher(dependencies);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(dependencies, matcher.start())) {
				String newDependencies = StringUtil.insert(
					dependencies, StringPool.SPACE, matcher.end() - 1);

				return StringUtil.replace(
					content, dependencies, newDependencies);
			}
		}

		if (dependencies.contains(StringPool.APOSTROPHE)) {
			String newDependencies = StringUtil.replace(
				dependencies, CharPool.APOSTROPHE, CharPool.QUOTE);

			return StringUtil.replace(content, dependencies, newDependencies);
		}

		Set<String> uniqueDependencies = new TreeSet<>(
			new GradleDependencyComparator());

		for (String dependency : StringUtil.splitLines(dependencies)) {
			dependency = dependency.trim();

			if (Validator.isNull(dependency)) {
				continue;
			}

			if (dependency.startsWith("compileOnly ") &&
				Validator.isNotNull(releasePortalAPIVersion)) {

				uniqueDependencies.add(
					StringBundler.concat(
						"compileOnly group: \"com.liferay.portal\", name: ",
						"\"release.portal.api\", version: \"",
						releasePortalAPIVersion, "\""));

				continue;
			}

			matcher = _incorrectGroupNameVersionPattern.matcher(dependency);

			if (matcher.find()) {
				StringBundler sb = new StringBundler(9);

				sb.append(matcher.group(1));
				sb.append(" group: \"");
				sb.append(matcher.group(2));
				sb.append("\", name: \"");
				sb.append(matcher.group(3));
				sb.append("\", version: \"");
				sb.append(matcher.group(4));
				sb.append("\"");
				sb.append(matcher.group(5));

				dependency = sb.toString();
			}

			uniqueDependencies.add(_sortDependencyAttributes(dependency));
		}

		StringBundler sb = new StringBundler();

		String previousConfiguration = null;

		for (String dependency : uniqueDependencies) {
			String configuration = GradleSourceUtil.getConfiguration(
				dependency);

			if ((previousConfiguration == null) ||
				!previousConfiguration.equals(configuration)) {

				previousConfiguration = configuration;

				sb.append("\n");
			}

			sb.append(indent);
			sb.append("\t");
			sb.append(dependency);
			sb.append("\n");
		}

		return StringUtil.replace(content, dependencies, sb.toString());
	}

	private String _formatTestIntegrationCompileDependencies(
		String content, String dependencies, Pattern pattern) {

		Matcher matcher = pattern.matcher(dependencies);

		if (matcher.find()) {
			return StringUtil.replace(
				content, dependencies,
				StringUtil.removeSubstring(dependencies, matcher.group()));
		}

		return content;
	}

	private String _sortDependencyAttributes(String dependency) {
		Matcher matcher = _dependencyPattern.matcher(dependency);

		if (!matcher.find()) {
			return dependency;
		}

		StringBundler sb = new StringBundler();

		sb.append(matcher.group(1));
		sb.append(StringPool.SPACE);

		Map<String, String> attributesMap = new TreeMap<>();

		matcher = _dependencyAttributesPattern.matcher(dependency);

		while (matcher.find()) {
			attributesMap.put(matcher.group(1), matcher.group(2));
		}

		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(": \"");
			sb.append(entry.getValue());
			sb.append("\"");
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static final String
		_ALLOWED_COMMERCE_DEPENDENCIES_MODULE_PATH_NAMES =
			"allowedCommerceDependenciesModulePathNames";

	private static final String _CHECK_PETRA_DEPENDENCIES_KEY =
		"checkPetraDependencies";

	private static final String
		_CHECK_TEST_INTEGRATION_COMPILE_DEPENDENCIES_KEY =
			"checkTestIntegrationCompileDependencies";

	private static final String _RELEASE_PORTAL_API_VERSION_KEY =
		"releasePortalAPIVersion";

	private static final Pattern _dependencyAttributesPattern = Pattern.compile(
		"(\\w+): \"([\\w.-]+)\"");
	private static final Pattern _dependencyPattern = Pattern.compile(
		"^(\\w+) (\\w+: \"[\\w.-]+\"(, )?)+$");
	private static final Pattern _incorrectGroupNameVersionPattern =
		Pattern.compile(
			"(^[^\\s]+)\\s+\"([^:]+?):([^:]+?):([^\"]+?)\"(.*?)",
			Pattern.DOTALL);
	private static final Pattern _incorrectWhitespacePattern = Pattern.compile(
		"(:|\",)[^ \n]");
	private static final Pattern _petraPattern = Pattern.compile(
		"testIntegrationCompile project\\(\":core:petra:.*");
	private static final Pattern _portalKernelPattern = Pattern.compile(
		"testIntegrationCompile.* name: \"com\\.liferay\\.portal\\.kernel\".*");

	private class GradleDependencyComparator
		implements Comparator<String>, Serializable {

		@Override
		public int compare(String dependency1, String dependency2) {
			String configuration1 = GradleSourceUtil.getConfiguration(
				dependency1);
			String configuration2 = GradleSourceUtil.getConfiguration(
				dependency2);

			if (!configuration1.equals(configuration2)) {
				return dependency1.compareTo(dependency2);
			}

			String group1 = _getPropertyValue(dependency1, "group");
			String group2 = _getPropertyValue(dependency2, "group");

			if ((group1 != null) && group1.equals(group2)) {
				String name1 = _getPropertyValue(dependency1, "name");
				String name2 = _getPropertyValue(dependency2, "name");

				if ((name1 != null) && name1.equals(name2)) {
					int length1 = dependency1.length();
					int length2 = dependency2.length();

					if (length1 == length2) {
						return 0;
					}
				}
			}

			return dependency1.compareTo(dependency2);
		}

		private String _getPropertyValue(
			String dependency, String propertyName) {

			Pattern pattern = Pattern.compile(
				".* " + propertyName + ": \"(.+?)\"");

			Matcher matcher = pattern.matcher(dependency);

			if (matcher.find()) {
				return matcher.group(1);
			}

			return null;
		}

	}

}