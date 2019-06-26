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