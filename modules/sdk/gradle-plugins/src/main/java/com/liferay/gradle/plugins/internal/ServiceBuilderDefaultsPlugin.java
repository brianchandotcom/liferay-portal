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

package com.liferay.gradle.plugins.internal;

import aQute.bnd.gradle.BndBuilderPlugin;

import com.liferay.gradle.plugins.BasePortalToolDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.db.support.DBSupportPlugin;
import com.liferay.gradle.plugins.db.support.tasks.CleanServiceBuilderTask;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.tasks.BuildDBTask;
import com.liferay.gradle.util.FileUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class ServiceBuilderDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<ServiceBuilderPlugin> {

	public static final String BUILD_DB_TASK_NAME = "buildDB";

	public static final Plugin<Project> INSTANCE =
		new ServiceBuilderDefaultsPlugin();

	@Override
	protected void configureDefaults(
		final Project project, ServiceBuilderPlugin serviceBuilderPlugin) {

		super.configureDefaults(project, serviceBuilderPlugin);

		BuildServiceTask buildServiceTask =
			(BuildServiceTask)GradleUtil.getTask(
				project, ServiceBuilderPlugin.BUILD_SERVICE_TASK_NAME);

		GradleUtil.applyPlugin(project, DBSupportPlugin.class);

		_addTaskBuildDB(project);
		_configureTaskCleanServiceBuilder(buildServiceTask);
		_configureTaskProcessResources(buildServiceTask);

		GradleUtil.withPlugin(
			project, LiferayBasePlugin.class,
			new Action<LiferayBasePlugin>() {

				@Override
				public void execute(LiferayBasePlugin liferayBasePlugin) {
					Configuration portalConfiguration =
						GradleUtil.getConfiguration(
							project,
							LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

					_configureTasksBuildDB(project, portalConfiguration);
				}

			});

		GradleUtil.withPlugin(
			project, BndBuilderPlugin.class,
			new Action<BndBuilderPlugin>() {

				@Override
				public void execute(BndBuilderPlugin bndBuilderPlugin) {
					_configureTasksBuildServiceForBndBuilderPlugin(project);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskBuildServiceTask(project, buildServiceTask);
				}

			});
	}

	@Override
	protected Class<ServiceBuilderPlugin> getPluginClass() {
		return ServiceBuilderPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return ServiceBuilderPlugin.CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private ServiceBuilderDefaultsPlugin() {
	}

	private BuildDBTask _addTaskBuildDB(final Project project) {
		BuildDBTask buildDBTask = GradleUtil.addTask(
			project, BUILD_DB_TASK_NAME, BuildDBTask.class);

		buildDBTask.setDatabaseName("lportal");
		buildDBTask.setDatabaseTypes("hypersonic", "mysql", "postgresql");
		buildDBTask.setDescription(
			"Builds database SQL scripts from the generic SQL scripts.");
		buildDBTask.setGroup(BasePlugin.BUILD_GROUP);

		buildDBTask.setSqlDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					BuildServiceTask buildServiceTask =
						(BuildServiceTask)GradleUtil.getTask(
							project,
							ServiceBuilderPlugin.BUILD_SERVICE_TASK_NAME);

					return buildServiceTask.getSqlDir();
				}

			});

		return buildDBTask;
	}

	private void _configureTaskBuildServiceTask(
		Project project, BuildServiceTask buildServiceTask) {

		File apiDir = buildServiceTask.getApiDir();

		if (apiDir == null) {
			return;
		}

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
			return;
		}

		buildServiceTask.finalizedBy(apiProjectPath + ":baseline");
	}

	private void _configureTaskBuildDBClasspath(
		BuildDBTask buildDBTask, FileCollection fileCollection) {

		buildDBTask.setClasspath(fileCollection);
	}

	private void _configureTaskBuildServiceForBndBuilderPlugin(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setOsgiModule(true);
	}

	private void _configureTaskCleanServiceBuilder(
		final BuildServiceTask buildServiceTask) {

		CleanServiceBuilderTask cleanServiceBuilderTask =
			(CleanServiceBuilderTask)GradleUtil.getTask(
				buildServiceTask.getProject(),
				DBSupportPlugin.CLEAN_SERVICE_BUILDER_TASK_NAME);

		cleanServiceBuilderTask.setServiceXmlFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildServiceTask.getInputFile();
				}

			});
	}

	@SuppressWarnings("serial")
	private void _configureTaskProcessResources(
		final BuildServiceTask buildServiceTask) {

		Copy copy = (Copy)GradleUtil.getTask(
			buildServiceTask.getProject(),
			JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		copy.into(
			"META-INF",
			new Closure<Void>(copy) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					File inputFile = buildServiceTask.getInputFile();

					File dir = inputFile.getParentFile();

					String dirName = dir.getName();

					if (!dirName.equals("META-INF")) {
						copySpec.from(inputFile);
					}
				}

			});
	}

	private void _configureTasksBuildDB(
		Project project, final FileCollection classpath) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildDBTask.class,
			new Action<BuildDBTask>() {

				@Override
				public void execute(BuildDBTask buildDBTask) {
					_configureTaskBuildDBClasspath(buildDBTask, classpath);
				}

			});
	}

	private void _configureTasksBuildServiceForBndBuilderPlugin(
		Project project) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildServiceTask.class,
			new Action<BuildServiceTask>() {

				@Override
				public void execute(BuildServiceTask buildServiceTask) {
					_configureTaskBuildServiceForBndBuilderPlugin(
						buildServiceTask);
				}

			});
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.service.builder";

}