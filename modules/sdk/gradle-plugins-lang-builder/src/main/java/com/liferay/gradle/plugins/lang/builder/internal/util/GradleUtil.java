/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.lang.builder.internal.util;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Peter Shin
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static Project getAppProject(Project project) {
		while (true) {
			File appBndFile = project.file("app.bnd");

			if (appBndFile.exists()) {
				return project;
			}

			project = project.getParent();

			if (project == null) {
				return null;
			}
		}
	}

	public static boolean hasTask(Project project, String name) {
		TaskContainer taskContainer = project.getTasks();

		if (taskContainer.findByName(name) != null) {
			return true;
		}

		return false;
	}

}