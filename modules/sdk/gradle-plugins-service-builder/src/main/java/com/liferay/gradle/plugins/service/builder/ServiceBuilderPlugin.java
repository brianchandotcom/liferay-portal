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

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
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
			addServiceBuilderConfiguration(project);

		addBuildServiceTask(project);

		configureTasksBuildService(project, serviceBuilderConfiguration);
	}

	protected BuildServiceTask addBuildServiceTask(final Project project) {
		final BuildServiceTask buildServiceTask = GradleUtil.addTask(
			project, BUILD_SERVICE_TASK_NAME, BuildServiceTask.class);

		buildServiceTask.setDescription("Runs Liferay Service Builder.");
		buildServiceTask.setGroup(BasePlugin.BUILD_GROUP);

		buildServiceTask.setHbmFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getResourcesDir(project);

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
					return getJavaDir(project);
				}

			});

		buildServiceTask.setInputFile("service.xml");

		buildServiceTask.setModelHintsFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getResourcesDir(project);

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
					return getResourcesDir(project);
				}

			});

		buildServiceTask.setSpringFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getResourcesDir(project);

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
					File resourcesDir = getResourcesDir(project);

					return new File(resourcesDir, "META-INF/sql");
				}

			});

		return buildServiceTask;
	}

	protected Configuration addServiceBuilderConfiguration(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Service Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addServiceBuilderDependencies(project);
				}

			});

		return configuration;
	}

	protected void addServiceBuilderDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.service.builder", "latest.release");
	}

	protected void configureTaskBuildServiceClasspath(
		BuildServiceTask buildServiceTask,
		Configuration serviceBuilderConfiguration) {

		buildServiceTask.setClasspath(serviceBuilderConfiguration);
	}

	protected void configureTasksBuildService(
		Project project, final Configuration serviceBuilderConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildServiceTask.class,
			new Action<BuildServiceTask>() {

				@Override
				public void execute(BuildServiceTask buildServiceTask) {
					configureTaskBuildServiceClasspath(
						buildServiceTask, serviceBuilderConfiguration);
				}

			});
	}

	protected File getJavaDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getJava());
	}

	protected File getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getResources());
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}