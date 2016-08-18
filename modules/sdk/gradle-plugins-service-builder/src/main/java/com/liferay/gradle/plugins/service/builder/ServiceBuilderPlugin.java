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

package com.liferay.gradle.plugins.service.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class ServiceBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_SERVICE_TASK_NAME = "buildService";

	public static final String CONFIGURATION_NAME = "serviceBuilder";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		Configuration serviceBuilderConfiguration =
			_addConfigurationServiceBuilder(project);

		_addTaskBuildService(project);

		_configureTasksBuildService(project, serviceBuilderConfiguration);
	}

	private Configuration _addConfigurationServiceBuilder(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesServiceBuilder(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Service Builder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesServiceBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.service.builder", "latest.release");
	}

	private BuildServiceTask _addTaskBuildService(final Project project) {
		final BuildServiceTask buildServiceTask = GradleUtil.addTask(
			project, BUILD_SERVICE_TASK_NAME, BuildServiceTask.class);

		buildServiceTask.setDescription("Runs Liferay Service Builder.");
		buildServiceTask.setGroup(BasePlugin.BUILD_GROUP);

		buildServiceTask.setHbmFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = GradleUtil.getMainResourcesDir(project);

					String fileName = "META-INF/portlet-hbm.xml";

					if (buildServiceTask.isOsgiModule()) {
						fileName = "META-INF/module-hbm.xml";
					}

					return new File(resourcesDir, fileName);
				}

			});

		buildServiceTask.setImplDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return GradleUtil.getMainJavaDir(project);
				}

			});

		buildServiceTask.setInputFile("service.xml");

		buildServiceTask.setModelHintsFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = GradleUtil.getMainResourcesDir(project);

					return new File(
						resourcesDir, "META-INF/portlet-model-hints.xml");
				}

			});

		buildServiceTask.setPluginName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					if (buildServiceTask.isOsgiModule()) {
						return "";
					}

					return project.getName();
				}

			});

		buildServiceTask.setPropsUtil(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					if (buildServiceTask.isOsgiModule()) {
						String bundleSymbolicName =
							_osgiHelper.getBundleSymbolicName(project);

						return bundleSymbolicName + ".util.ServiceProps";
					}

					return "com.liferay.util.service.ServiceProps";
				}

			});

		buildServiceTask.setResourcesDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return GradleUtil.getMainResourcesDir(project);
				}

			});

		buildServiceTask.setSpringFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = GradleUtil.getMainResourcesDir(project);

					String fileName = "META-INF/portlet-spring.xml";

					if (buildServiceTask.isOsgiModule()) {
						fileName = "META-INF/spring/module-spring.xml";
					}

					return new File(resourcesDir, fileName);
				}

			});

		buildServiceTask.setSqlDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = GradleUtil.getMainResourcesDir(project);

					return new File(resourcesDir, "META-INF/sql");
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					_configureTaskBuildServiceForWarPlugin(buildServiceTask);
				}

			});

		return buildServiceTask;
	}

	private void _configureTaskBuildServiceClasspath(
		BuildServiceTask buildServiceTask,
		Configuration serviceBuilderConfiguration) {

		buildServiceTask.setClasspath(serviceBuilderConfiguration);
	}

	private void _configureTaskBuildServiceForWarPlugin(
		final BuildServiceTask buildServiceTask) {

		buildServiceTask.setApiDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File webAppDir = GradleUtil.getWebAppDir(
						buildServiceTask.getProject());

					return new File(webAppDir, "WEB-INF/service");
				}

			});

		buildServiceTask.setInputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File webAppDir = GradleUtil.getWebAppDir(
						buildServiceTask.getProject());

					return new File(webAppDir, "WEB-INF/service.xml");
				}

			});

		buildServiceTask.setSqlDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File webAppDir = GradleUtil.getWebAppDir(
						buildServiceTask.getProject());

					return new File(webAppDir, "WEB-INF/sql");
				}

			});
	}

	private void _configureTasksBuildService(
		Project project, final Configuration serviceBuilderConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildServiceTask.class,
			new Action<BuildServiceTask>() {

				@Override
				public void execute(BuildServiceTask buildServiceTask) {
					_configureTaskBuildServiceClasspath(
						buildServiceTask, serviceBuilderConfiguration);
				}

			});
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}