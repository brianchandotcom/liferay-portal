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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.util.GradleBuildFile;
import com.liferay.source.formatter.util.GradleDependency;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

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

		List<String> includeResourceJars = _getIncludeResourceJars(fileName);

		if (ListUtil.isEmpty(includeResourceJars)) {
			return content;
		}

		GradleBuildFile gradleBuildFile = new GradleBuildFile(content);

		List<GradleDependency> gradleDependencies =
			gradleBuildFile.getGradleDependencies();

		Iterator<GradleDependency> iterator = gradleDependencies.iterator();

		while (iterator.hasNext()) {
			GradleDependency gradleDependency = iterator.next();

			boolean hasDependency = false;

			for (String includeResourceJar : includeResourceJars) {
				String dependencyConfiguration =
					gradleDependency.getConfiguration();

				if (includeResourceJar.contains(gradleDependency.getName()) &&
					!dependencyConfiguration.equals("compileInclude")) {

					hasDependency = true;

					break;
				}
			}

			if (!hasDependency) {
				iterator.remove();
			}
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

	private List<String> _getIncludeResourceJars(String fileName)
		throws IOException {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return null;
		}

		String bndSettingsContent = bndSettings.getContent();

		List<String> includeResourceJars = BNDSourceUtil.getDefinitionValues(
			bndSettingsContent, "-includeresource");

		includeResourceJars.addAll(
			BNDSourceUtil.getDefinitionValues(
				bndSettingsContent, "Include-Resource"));

		return includeResourceJars;
	}

}