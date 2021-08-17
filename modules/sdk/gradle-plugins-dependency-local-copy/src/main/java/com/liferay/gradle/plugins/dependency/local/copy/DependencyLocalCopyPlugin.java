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

package com.liferay.gradle.plugins.dependency.local.copy;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.DependencySubstitution;
import org.gradle.api.artifacts.DependencySubstitutions;
import org.gradle.api.artifacts.ExternalDependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.component.ComponentSelector;
import org.gradle.api.artifacts.component.ModuleComponentSelector;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Sync;

/**
 * @author Andrea Di Giorgi
 */
public class DependencyLocalCopyPlugin implements Plugin<Project> {

	public static final String LOCAL_COPY_GROUP = "local.copy";

	public static final String SYNC_LOCAL_COPY_TASK_NAME = "syncLocalCopy";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		Task syncLocalCopyTask = _addTaskSyncLocalCopy(project);

		_configureConfigurations(syncLocalCopyTask);
		_configureTaskCompileJava(syncLocalCopyTask);
	}

	private Task _addTaskSyncLocalCopy(Project project) {
		Task task = project.task(SYNC_LOCAL_COPY_TASK_NAME);

		task.setDescription(
			"Creates local copies of all configured project dependencies.");

		return task;
	}

	private Sync _addTaskSyncLocalCopy(
		final Project project, final Project dependencyProject,
		boolean transitive) {

		final String dependencyProjectPath = dependencyProject.getPath();

		Sync sync = GradleUtil.addTask(
			project,
			SYNC_LOCAL_COPY_TASK_NAME + dependencyProjectPath.replace(':', '_'),
			Sync.class);

		Configuration configuration = GradleUtil.addConfiguration(
			project, sync.getName());

		DependencyHandler dependencyHandler = project.getDependencies();

		ProjectDependency projectDependency =
			(ProjectDependency)dependencyHandler.add(
				configuration.getName(), dependencyProject);

		projectDependency.setTransitive(transitive);

		sync.from(configuration);

		sync.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return _getLocalCopyDir(project, dependencyProjectPath);
				}

			});

		sync.setDescription(
			"Creates a local copy of " + dependencyProject + ".");

		return sync;
	}

	private void _configureConfiguration(
		final Task syncLocalCopyTask, final Configuration configuration) {

		DependencySet dependencySet = configuration.getDependencies();

		dependencySet.withType(
			ExternalDependency.class,
			new Action<ExternalDependency>() {

				@Override
				public void execute(ExternalDependency externalDependency) {
					if (!LOCAL_COPY_GROUP.equals(
							externalDependency.getGroup())) {

						return;
					}

					String dependencyProjectPath = externalDependency.getName();

					Project project = syncLocalCopyTask.getProject();

					Project dependencyProject = project.findProject(
						dependencyProjectPath);

					if (dependencyProject != null) {
						Sync sync = _addTaskSyncLocalCopy(
							project, dependencyProject,
							externalDependency.isTransitive());

						syncLocalCopyTask.dependsOn(sync);
					}
					else {
						Map<String, String> excludeProperties = new HashMap<>();

						excludeProperties.put(
							"group", externalDependency.getGroup());
						excludeProperties.put("module", dependencyProjectPath);

						configuration.exclude(excludeProperties);

						FileTree fileTree = project.fileTree(
							_getLocalCopyDir(project, dependencyProjectPath));

						GradleUtil.addDependency(
							project, configuration.getName(), fileTree);
					}
				}

			});

		ResolutionStrategy resolutionStrategy =
			configuration.getResolutionStrategy();

		final DependencySubstitutions dependencySubstitutions =
			resolutionStrategy.getDependencySubstitution();

		dependencySubstitutions.all(
			new Action<DependencySubstitution>() {

				@Override
				public void execute(
					DependencySubstitution dependencySubstitution) {

					ComponentSelector componentSelector =
						dependencySubstitution.getRequested();

					if (!(componentSelector instanceof
							ModuleComponentSelector)) {

						return;
					}

					ModuleComponentSelector moduleComponentSelector =
						(ModuleComponentSelector)componentSelector;

					if (!LOCAL_COPY_GROUP.equals(
							moduleComponentSelector.getGroup())) {

						return;
					}

					dependencySubstitution.useTarget(
						dependencySubstitutions.project(
							moduleComponentSelector.getModule()));
				}

			});
	}

	private void _configureConfigurations(final Task syncLocalCopyTask) {
		Project project = syncLocalCopyTask.getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		configurationContainer.all(
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					_configureConfiguration(syncLocalCopyTask, configuration);
				}

			});
	}

	private void _configureTaskCompileJava(Task syncLocalCopyTask) {
		Task task = GradleUtil.getTask(
			syncLocalCopyTask.getProject(), JavaPlugin.COMPILE_JAVA_TASK_NAME);

		task.finalizedBy(syncLocalCopyTask);
	}

	private File _getLocalCopyDir(
		Project project, String dependencyProjectPath) {

		File localCopyDir = new File(project.getBuildDir(), "local-copy");

		return new File(
			localCopyDir,
			dependencyProjectPath.replace(':', File.separatorChar));
	}

}