/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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