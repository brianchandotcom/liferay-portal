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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.upgrade.GradleBuildFile;
import com.liferay.source.formatter.upgrade.GradleDependency;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeGradleIncludeResourceCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/build.gradle")) {
			return content;
		}

		return _formatDependencies(fileName, content);
	}

	private String _formatDependencies(String fileName, String content)
		throws IOException {

		fileName = StringUtil.replace(
			fileName, CharPool.BACK_SLASH, CharPool.SLASH);

		String[] jars = _getIncludeResourceJars(fileName);

		if (jars == null) {
			return content;
		}

		GradleBuildFile gradleBuildFile = new GradleBuildFile(content);

		List<GradleDependency> gradleDependencies =
			gradleBuildFile.getGradleDependencies();

		Iterator<GradleDependency> iterator = gradleDependencies.iterator();

		while (iterator.hasNext()) {
			GradleDependency gradleDependency = iterator.next();

			boolean has = false;

			for (String dependency : jars) {
				String dependencyConfiguration =
					gradleDependency.getConfiguration();

				if (dependency.contains(gradleDependency.getName()) &&
					!dependencyConfiguration.equals("compileInclude")) {

					has = true;

					break;
				}
			}

			if (has) {
				continue;
			}

			iterator.remove();
		}

		if (gradleDependencies.isEmpty()) {
			return content;
		}

		for (GradleDependency gradleDependency : gradleDependencies) {
			gradleBuildFile.insertGradleDependency(
				"compileInclude", gradleDependency.getGroup(),
				gradleDependency.getName(), gradleDependency.getVersion());
		}

		gradleBuildFile.deleteGradleDependencies(gradleDependencies);

		String source = gradleBuildFile.getSource();

		return source.concat(
			"\n\nliferayOSGi {\n\texpandCompileInclude = true\n}");
	}

	private String[] _getIncludeResourceJars(String fileName)
		throws IOException {

		String bndFileName = StringUtil.replace(
			fileName, "build.gradle", "bnd.bnd");

		File file = new File(bndFileName);

		String bndContent = FileUtil.read(file);

		if (bndContent == null) {
			return null;
		}

		Matcher matcher1 = _includeResourcePattern.matcher(bndContent);

		if (!matcher1.find()) {
			return null;
		}

		String includeResources = matcher1.group();

		String jarDependencies = StringUtil.removeSubstring(
			includeResources, "Include-Resource:\\");

		jarDependencies = StringUtil.removeSubstring(jarDependencies, "\n");
		jarDependencies = StringUtil.removeSubstring(jarDependencies, "\t");

		return StringUtil.split(jarDependencies, "\\");
	}

	private static final Pattern _includeResourcePattern = Pattern.compile(
		"^(-includeresource|Include-Resource):[\\s\\S]*?([^\\\\]\n|\\Z)",
		Pattern.MULTILINE);

}