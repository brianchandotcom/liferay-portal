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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.NpmShrinkwrapTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;

import java.util.Collections;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodeDefaultsPlugin extends BaseDefaultsPlugin<NodePlugin> {

	public static final Plugin<Project> INSTANCE = new NodeDefaultsPlugin();

	@Override
	protected void configureDefaults(Project project, NodePlugin nodePlugin) {
		_configureTaskClean(project);
		_configureTaskNpmShrinkwrap(project);
		_configureTasksPublishNodeModule(project);
	}

	@Override
	protected Class<NodePlugin> getPluginClass() {
		return NodePlugin.class;
	}

	private NodeDefaultsPlugin() {
	}

	private void _configureTaskClean(Project project) {
		boolean cleanNodeModules = Boolean.getBoolean("clean.node.modules");

		if (cleanNodeModules) {
			Delete delete = (Delete)GradleUtil.getTask(
				project, BasePlugin.CLEAN_TASK_NAME);

			delete.delete("node_modules");
		}
	}

	private void _configureTaskNpmShrinkwrap(Project project) {
		NpmShrinkwrapTask npmShrinkwrapTask =
			(NpmShrinkwrapTask)GradleUtil.getTask(
				project, NodePlugin.NPM_SHRINKWRAP_TASK_NAME);

		npmShrinkwrapTask.excludeDependencies(
			_NPM_SHRINKWRAP_EXCLUDED_DEPENDENCIES);
	}

	private void _configureTasksPublishNodeModule(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					publishNodeModuleTask.doFirst(
						MavenDefaultsPlugin.failReleaseOnWrongBranchAction);
				}

			});
	}

	private static final Iterable<String>
		_NPM_SHRINKWRAP_EXCLUDED_DEPENDENCIES = Collections.singleton(
			"fsevents");

}