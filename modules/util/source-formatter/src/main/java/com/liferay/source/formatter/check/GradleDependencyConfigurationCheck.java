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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.GradleSourceUtil;

import java.io.File;

import java.util.List;

/**
 * @author Peter Shin
 */
public class GradleDependencyConfigurationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if ((!isModulesApp(absolutePath, false) &&
			 !absolutePath.contains("/third-party/")) ||
			!_hasBNDFile(absolutePath) ||
			GradleSourceUtil.isSpringBootExecutable(content)) {

			return content;
		}

		List<String> blocks = GradleSourceUtil.getDependenciesBlocks(content);

		for (String dependencies : blocks) {
			content = _formatDependencies(absolutePath, content, dependencies);
		}

		return content;
	}

	private String _formatDependencies(
		String absolutePath, String content, String dependencies) {

		int x = dependencies.indexOf("\n");
		int y = dependencies.lastIndexOf("\n");

		if (x == y) {
			return content;
		}

		dependencies = dependencies.substring(x, y + 1);

		for (String oldDependency : StringUtil.splitLines(dependencies)) {
			String configuration = GradleSourceUtil.getConfiguration(
				oldDependency);
			String newDependency = oldDependency;

			if (!absolutePath.contains("/third-party/")) {
				if (configuration.equals("compile")) {
					newDependency = StringUtil.replaceFirst(
						oldDependency, "compile", "compileOnly");
				}
				else if (configuration.equals("compileOnly")) {
					newDependency = StringUtil.removeSubstrings(
						oldDependency, "transitive: false, ",
						"transitive: true,");
				}
			}
			else {
				if (configuration.equals("compileOnly") &&
					!oldDependency.contains("transitive: false")) {

					newDependency = oldDependency + ", transitive: false";
				}
			}

			content = StringUtil.replaceFirst(
				content, oldDependency, newDependency);
		}

		return content;
	}

	private boolean _hasBNDFile(String absolutePath) {
		if (!absolutePath.endsWith("/build.gradle")) {
			return false;
		}

		int pos = absolutePath.lastIndexOf(StringPool.SLASH);

		File file = new File(absolutePath.substring(0, pos + 1) + "bnd.bnd");

		return file.exists();
	}

}