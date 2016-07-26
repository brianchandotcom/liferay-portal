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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Andrea Di Giorgi
 */
public class GradleSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	protected void checkDefaultVersion(String fileName, String content) {
		Matcher matcher = _defaultVersionPattern.matcher(content);

		while (matcher.find()) {
			String name = matcher.group(1);

			if (!name.equals("com.liferay.portal.impl") &&
				!name.equals("com.liferay.portal.kernel") &&
				!name.equals("com.liferay.util.bridges") &&
				!name.equals("com.liferay.util.taglib")) {

				processMessage(
					fileName, "Do not use 'default' version",
					getLineCount(content, matcher.start()));
			}
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		content = formatDependencies(absolutePath, content);

		checkDefaultVersion(fileName, content);

		return trimContent(content, false);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	protected String formatDependencies(String absolutePath, String content) {
		Matcher matcher = _dependenciesPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String dependencies = matcher.group(1);

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
				(absolutePath.contains("/modules/apps/") ||
				 absolutePath.contains("/modules/private/apps/"))) {

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

	private static final String[] _INCLUDES = new String[] {"**/build.gradle"};

	private final Pattern _defaultVersionPattern = Pattern.compile(
		"name: \"(.*?)\", version: \"default\"");
	private final Pattern _dependenciesPattern = Pattern.compile(
		"^dependencies \\{(.+?\n)\\}", Pattern.DOTALL | Pattern.MULTILINE);

}