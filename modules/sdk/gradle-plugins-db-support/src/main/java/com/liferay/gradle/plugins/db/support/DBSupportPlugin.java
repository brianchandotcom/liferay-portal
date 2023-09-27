/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.db.support;

import com.liferay.gradle.plugins.db.support.internal.util.GradleUtil;
import com.liferay.gradle.plugins.db.support.task.BaseDBSupportTask;
import com.liferay.gradle.plugins.db.support.task.CleanServiceBuilderTask;
import com.liferay.gradle.util.OSGiUtil;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class DBSupportPlugin implements Plugin<Project> {

	public static final String CLEAN_SERVICE_BUILDER_TASK_NAME =
		"cleanServiceBuilder";

	public static final String CONFIGURATION_NAME = "dbSupport";

	public static final String TOOL_CONFIGURATION_NAME = "dbSupportTool";

	@Override
	public void apply(Project project) {
		Configuration dbSupportConfiguration = _addConfigurationDBSupport(
			project);
		Configuration dbSupportToolConfiguration =
			_addConfigurationDBSupportTool(project);

		_addTaskCleanServiceBuilder(project);

		_configureTasksBaseDBSupport(
			project,
			project.files(dbSupportConfiguration, dbSupportToolConfiguration));
	}

	private Configuration _addConfigurationDBSupport(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the additional classpath of the DB Support tasks.");
		configuration.setVisible(false);

		return configuration;
	}

	private Configuration _addConfigurationDBSupportTool(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TOOL_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesDBSupportTool(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay DB Support for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesDBSupportTool(Project project) {
		GradleUtil.addDependency(
			project, TOOL_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.db.support", "latest.release");
	}

	private CleanServiceBuilderTask _addTaskCleanServiceBuilder(
		final Project project) {

		CleanServiceBuilderTask cleanServiceBuilderTask = GradleUtil.addTask(
			project, CLEAN_SERVICE_BUILDER_TASK_NAME,
			CleanServiceBuilderTask.class);

		cleanServiceBuilderTask.setDescription(
			"Cleans the Liferay database from the Service Builder tables and " +
				"rows of a module.");
		cleanServiceBuilderTask.setGroup(BasePlugin.BUILD_GROUP);

		cleanServiceBuilderTask.setServletContextName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					PluginContainer pluginContainer = project.getPlugins();

					if (pluginContainer.hasPlugin(BasePlugin.class)) {
						return OSGiUtil.getBundleSymbolicName(project);
					}

					return null;
				}

			});

		cleanServiceBuilderTask.setServiceXmlFile("service.xml");

		return cleanServiceBuilderTask;
	}

	private void _configureTasksBaseDBSupport(
		Project project, final FileCollection classpath) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaseDBSupportTask.class,
			new Action<BaseDBSupportTask>() {

				@Override
				public void execute(BaseDBSupportTask baseDBSupportTask) {
					baseDBSupportTask.setClasspath(classpath);
				}

			});
	}

}