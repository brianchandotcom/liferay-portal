/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.js.transpiler.internal.util;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.copy.RenameDependencyClosure;

import java.io.File;

import java.util.HashSet;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskDependency;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class JSTranspilerPluginUtil {

	public static Copy addTaskExpandCompileDependency(
		Project project, File file, File destinationDir, String taskNamePrefix,
		RenameDependencyClosure renameDependencyClosure) {

		Copy copy = GradleUtil.addTask(
			project, GradleUtil.getTaskName(taskNamePrefix, file), Copy.class);

		copy.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					Project project = copy.getProject();

					project.delete(copy.getDestinationDir());
				}

			});

		copy.from(project.zipTree(file));

		String name = renameDependencyClosure.call(file.getName());

		name = name.substring(0, name.length() - 4);

		destinationDir = new File(destinationDir, name);

		copy.setDescription(
			"Expands " + file.getName() + " into " +
				project.relativePath(destinationDir) + ".");
		copy.setDestinationDir(destinationDir);

		return copy;
	}

	public static Iterable<TaskDependency> getTaskDependencies(
		Configuration configuration) {

		Set<TaskDependency> taskDependencies = new HashSet<>();

		DependencySet dependencySet = configuration.getAllDependencies();

		for (ProjectDependency projectDependency :
				dependencySet.withType(ProjectDependency.class)) {

			taskDependencies.add(projectDependency.getBuildDependencies());
		}

		return taskDependencies;
	}

}