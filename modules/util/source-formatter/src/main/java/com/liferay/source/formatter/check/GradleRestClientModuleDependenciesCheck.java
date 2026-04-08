/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.util.GradleBuildFile;
import com.liferay.source.formatter.util.GradleDependency;

import java.util.List;

/**
 * @author Alan Huang
 */
public class GradleRestClientModuleDependenciesCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.contains("/third-party/") ||
			!absolutePath.endsWith("-rest-client/build.gradle")) {

			return content;
		}

		int x = -1;

		while (true) {
			x = content.indexOf(" project(\":", x + 1);

			if (x == -1) {
				break;
			}

			addMessage(
				fileName,
				"Only Jakarta dependencies can be used in `*-rest-client` " +
					"modules",
				getLineNumber(content, x));
		}

		GradleBuildFile gradleBuildFile = new GradleBuildFile(content);

		List<GradleDependency> gradleDependencies =
			gradleBuildFile.getGradleDependencies();

		for (GradleDependency gradleDependency : gradleDependencies) {
			String group = gradleDependency.getGroup();
			String name = gradleDependency.getName();
			String version = gradleDependency.getVersion();

			if (((group != null) && group.contains("jakarta.")) ||
				((name != null) && name.contains("jakarta.")) ||
				((version != null) &&
				 version.contains("JAKARTA-LIFERAY-PATCHED"))) {

				continue;
			}

			addMessage(
				fileName,
				"Only Jakarta dependencies can be used in `*-rest-client` " +
					"modules",
				gradleDependency.getLineNumber());
		}

		return content;
	}

}