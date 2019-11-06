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

import com.liferay.gradle.plugins.service.builder.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;

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
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
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
			addConfigurationServiceBuilder(project);

		final BuildServiceTask buildServiceTask = addTaskBuildService(project);

		configureTasksBuildService(project, serviceBuilderConfiguration);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTaskBuildService(buildServiceTask);
				}

			});
	}

	protected Configuration addConfigurationServiceBuilder(
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
					addDependenciesServiceBuilder(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesServiceBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.service.builder", "latest.release");
	}

	protected BuildServiceTask addTaskBuildService(final Project project) {
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

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					configureTaskBuildServiceForWarPlugin(buildServiceTask);
				}

			});

		return buildServiceTask;
	}

	@SuppressWarnings("rawtypes")
	protected void configureTaskBuildService(
		final BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withId(
			"com.liferay.defaults.plugin",
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					configureTaskBuildServiceForLiferayDefaultsPlugin(
						buildServiceTask);
				}

			});
	}

	protected void configureTaskBuildServiceClasspath(
		BuildServiceTask buildServiceTask,
		Configuration serviceBuilderConfiguration) {

		buildServiceTask.setClasspath(serviceBuilderConfiguration);
	}

	protected void configureTaskBuildServiceForLiferayDefaultsPlugin(
		BuildServiceTask buildServiceTask) {

		File apiDir = buildServiceTask.getApiDir();

		if (apiDir == null) {
			return;
		}

		Project project = buildServiceTask.getProject();

		File apiProjectDir = GradleUtil.getRootDir(
			project.file(apiDir), "bnd.bnd");

		if (apiProjectDir == null) {
			return;
		}

		Project rootProject = project.getRootProject();

		String relativePath = FileUtil.relativize(
			apiProjectDir, rootProject.getProjectDir());

		relativePath = relativePath.replace(File.separatorChar, '/');

		String apiProjectPath = ':' + relativePath.replace('/', ':');

		Project apiProject = rootProject.findProject(apiProjectPath);

		if (apiProject == null) {
			String apiProjectName = apiProjectDir.getName();

			apiProject = GradleUtil.findProject(rootProject, apiProjectName);
		}

		if (apiProject == null) {
			return;
		}

		buildServiceTask.finalizedBy(apiProject.getPath() + ":baseline");
	}

	protected void configureTaskBuildServiceForWarPlugin(
		final BuildServiceTask buildServiceTask) {

		buildServiceTask.setApiDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getWebAppDir(buildServiceTask.getProject()),
						"WEB-INF/service");
				}

			});

		buildServiceTask.setInputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getWebAppDir(buildServiceTask.getProject()),
						"WEB-INF/service.xml");
				}

			});

		buildServiceTask.setSqlDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getWebAppDir(buildServiceTask.getProject()),
						"WEB-INF/sql");
				}

			});
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

	protected File getWebAppDir(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}