/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.GradleSourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class GradleTestDependencyVersionCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/modules/apps/") &&
			!absolutePath.contains("/modules/dxp/apps/") &&
			!absolutePath.contains("/modules/private/apps/")) {

			return content;
		}

		for (String dependencies : _getDependenciesBlocks(content)) {
			content = _formatDependencies(
				fileName, absolutePath, content, dependencies);
		}

		return content;
	}

	private String _formatDependencies(
		String fileName, String absolutePath, String content,
		String dependencies) {

		int x = dependencies.indexOf("\n");
		int y = dependencies.lastIndexOf("\n");

		if (x == y) {
			return content;
		}

		List<String> allowedDependencyNames = getAttributeValues(
			_ALLOWED_DEPENDENCY_NAMES_KEY, absolutePath);

		dependencies = dependencies.substring(x, y + 1);

		StringBundler sb = new StringBundler();

		for (String line : StringUtil.splitLines(dependencies)) {
			if (!StringUtil.startsWith(
					GradleSourceUtil.getConfiguration(StringUtil.trim(line)),
					"test")) {

				sb.append(line);
				sb.append("\n");

				continue;
			}

			String dependencyName = _getDependencyName(line);

			if (dependencyName.equals("com.liferay.portal.impl") ||
				dependencyName.equals("com.liferay.portal.kernel") ||
				dependencyName.equals("com.liferay.portal.test") ||
				dependencyName.equals("com.liferay.portal.test.integration") ||
				dependencyName.equals("com.liferay.util.bridges") ||
				dependencyName.equals("com.liferay.util.java") ||
				dependencyName.equals("com.liferay.util.taglib")) {

				String dependencyVersion = _getDependencyVersion(line);

				if (dependencyVersion.equals("default")) {
					sb.append(line);
				}
				else {
					String newLine = StringUtil.replaceFirst(
						line, "version: \"" + dependencyVersion + "\"",
						"version: \"default\"");

					sb.append(newLine);
				}

				sb.append("\n");

				continue;
			}

			if (dependencyName.startsWith("com.liferay.") &&
				!dependencyName.startsWith("com.liferay.portletmvc4spring") &&
				!line.contains("project(\"") &&
				!allowedDependencyNames.contains(dependencyName)) {

				addMessage(
					fileName,
					"Use a project dependency instead of a module dependency",
					getLineNumber(content, content.indexOf(line)));
			}

			sb.append(line);
			sb.append("\n");
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

	private static final String _ALLOWED_DEPENDENCY_NAMES_KEY =
		"allowedDependencyNames";

	private static final Pattern _dependenciesPattern = Pattern.compile(
		"(\n|\\A)(\t*)dependencies \\{\n");
	private static final Pattern _dependencyNamePattern = Pattern.compile(
		".*, name: \"([^\"]*)\".*");
	private static final Pattern _dependencyVersionPattern = Pattern.compile(
		".*, version: \"([^\"]*)\".*");

}