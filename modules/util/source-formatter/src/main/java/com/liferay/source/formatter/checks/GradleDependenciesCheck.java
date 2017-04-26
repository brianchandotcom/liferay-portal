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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GradleDependenciesCheck extends BaseFileCheck {

	public GradleDependenciesCheck() throws Exception {
		_projectPathPrefix = getProjectPathPrefix();
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatDependencies(absolutePath, content);
	}

	private String _formatDependencies(String absolutePath, String content) {
		Matcher matcher = _dependenciesPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String dependencies = matcher.group(1);

		matcher = _incorrectWhitespacePattern.matcher(dependencies);

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

		Set<String> uniqueDependencies = new TreeSet<>();

		for (String dependency : StringUtil.splitLines(dependencies)) {
			dependency = dependency.trim();

			if (Validator.isNull(dependency)) {
				continue;
			}

			uniqueDependencies.add(dependency);
		}

		StringBundler sb = new StringBundler();

		String previousConfiguration = null;

		for (String dependency : uniqueDependencies) {
			int pos = dependency.indexOf(StringPool.SPACE);

			String configuration = dependency.substring(0, pos);

			if (configuration.equals("compile") &&
				isModulesApp(absolutePath, _projectPathPrefix, false)) {

				dependency = StringUtil.replaceFirst(
					dependency, "compile", "provided");
			}

			if ((previousConfiguration == null) ||
				!previousConfiguration.equals(configuration)) {

				previousConfiguration = configuration;

				sb.append("\n");
			}

			sb.append("\t");
			sb.append(dependency);
			sb.append("\n");
		}

		return StringUtil.replace(content, dependencies, sb.toString());
	}

	private final Pattern _dependenciesPattern = Pattern.compile(
		"^dependencies \\{(.+?\n)\\}", Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _incorrectWhitespacePattern = Pattern.compile(
		":[^ \n]");
	private final String _projectPathPrefix;

}