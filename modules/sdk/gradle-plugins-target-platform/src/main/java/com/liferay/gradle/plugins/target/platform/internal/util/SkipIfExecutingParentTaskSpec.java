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

package com.liferay.gradle.plugins.target.platform.internal.util;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class SkipIfExecutingParentTaskSpec implements Spec<Task> {

	@Override
	public boolean isSatisfiedBy(Task task) {
		Project project = task.getProject();

		Gradle gradle = project.getGradle();

		TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

		Project parentProject = project;

		while ((parentProject = parentProject.getParent()) != null) {
			TaskContainer parentProjectTaskContainer = parentProject.getTasks();

			Task parentProjectTask = parentProjectTaskContainer.findByName(
				task.getName());

			if ((parentProjectTask != null) &&
				taskExecutionGraph.hasTask(parentProjectTask)) {

				return false;
			}
		}

		return true;
	}

}