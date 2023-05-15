/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.db.support.DBSupportPlugin;
import com.liferay.gradle.plugins.db.support.task.CleanServiceBuilderTask;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.task.BuildDBTask;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class ServiceBuilderDefaultsPlugin
	extends BaseDefaultsPlugin<ServiceBuilderPlugin> {

	public static final String BUILD_DB_TASK_NAME = "buildDB";

	public static final Plugin<Project> INSTANCE =
		new ServiceBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		final Project project, ServiceBuilderPlugin serviceBuilderPlugin) {

		// Plugins

		GradleUtil.applyPlugin(project, DBSupportPlugin.class);

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, ServiceBuilderPlugin.CONFIGURATION_NAME, PortalTools.GROUP,
			_PORTAL_TOOL_NAME);

		// Tasks

		TaskProvider<BuildDBTask> buildDBTaskProvider =
			GradleUtil.addTaskProvider(
				project, BUILD_DB_TASK_NAME, BuildDBTask.class);

		TaskProvider<BuildServiceTask> buildServiceTaskProvider =
			GradleUtil.getTaskProvider(
				project, ServiceBuilderPlugin.BUILD_SERVICE_TASK_NAME,
				BuildServiceTask.class);
		TaskProvider<CleanServiceBuilderTask> cleanServiceBuilderTaskProvider =
			GradleUtil.getTaskProvider(
				project, DBSupportPlugin.CLEAN_SERVICE_BUILDER_TASK_NAME,
				CleanServiceBuilderTask.class);
		TaskProvider<Copy> processResourcesTaskProvider =
			GradleUtil.getTaskProvider(
				project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME, Copy.class);

		_configureTaskBuildDBProvider(
			buildDBTaskProvider, buildServiceTaskProvider);
		_configureTaskCleanServiceBuilderProvider(
			buildServiceTaskProvider, cleanServiceBuilderTaskProvider);
		_configureTaskProcessResourcesProvider(
			buildServiceTaskProvider, processResourcesTaskProvider);

		// Containers

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildServiceTask.class,
			new Action<BuildServiceTask>() {

				@Override
				public void execute(BuildServiceTask buildServiceTask) {
					_configureTaskBuildService(buildServiceTask);
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			LiferayBasePlugin.class,
			new Action<LiferayBasePlugin>() {

				@Override
				public void execute(LiferayBasePlugin liferayBasePlugin) {
					TaskContainer taskContainer = project.getTasks();

					taskContainer.withType(
						BuildDBTask.class,
						new Action<BuildDBTask>() {

							@Override
							public void execute(BuildDBTask buildDBTask) {
								_configureTaskBuildDBForLiferayBasePlugin(
									project, buildDBTask);
							}

						});
				}

			});

		pluginContainer.withType(
			LiferayOSGiPlugin.class,
			new Action<LiferayOSGiPlugin>() {

				@Override
				public void execute(LiferayOSGiPlugin liferayOSGiPlugin) {
					TaskContainer taskContainer = project.getTasks();

					taskContainer.withType(
						BuildServiceTask.class,
						new Action<BuildServiceTask>() {

							@Override
							public void execute(
								BuildServiceTask buildServiceTask) {

								_configureTaskBuildServiceForLiferayOSGiPlugin(
									buildServiceTask);
							}

						});
				}

			});
	}

	@Override
	protected Class<ServiceBuilderPlugin> getPluginClass() {
		return ServiceBuilderPlugin.class;
	}

	private ServiceBuilderDefaultsPlugin() {
	}

	private void _configureTaskBuildDBForLiferayBasePlugin(
		Project project, BuildDBTask buildDBTask) {

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration portalConfiguration = configurationContainer.getByName(
			LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

		// Tasks

		buildDBTask.setClasspath(portalConfiguration);
	}

	private void _configureTaskBuildDBProvider(
		TaskProvider<BuildDBTask> buildDBTaskProvider,
		final TaskProvider<BuildServiceTask> buildServiceTaskProvider) {

		buildDBTaskProvider.configure(
			new Action<BuildDBTask>() {

				@Override
				public void execute(BuildDBTask buildDBTask) {
					buildDBTask.setDatabaseName("lportal");
					buildDBTask.setDatabaseTypes(
						"hypersonic", "mysql", "postgresql");
					buildDBTask.setDescription(
						"Builds database SQL scripts from the generic SQL " +
							"scripts.");
					buildDBTask.setGroup(BasePlugin.BUILD_GROUP);

					buildDBTask.setSqlDir(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								BuildServiceTask buildServiceTask =
									buildServiceTaskProvider.get();

								return buildServiceTask.getSqlDir();
							}

						});
				}

			});
	}

	private void _configureTaskBuildService(BuildServiceTask buildServiceTask) {
		String incubationFeatures = GradleUtil.getProperty(
			buildServiceTask.getProject(),
			"service.builder.incubation.features", (String)null);

		if (Validator.isNotNull(incubationFeatures)) {
			buildServiceTask.setIncubationFeatures(
				incubationFeatures.split(","));
		}
	}

	private void _configureTaskBuildServiceForLiferayOSGiPlugin(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setOsgiModule(true);
	}

	private void _configureTaskCleanServiceBuilderProvider(
		final TaskProvider<BuildServiceTask> buildServiceTaskProvider,
		TaskProvider<CleanServiceBuilderTask> cleanServiceBuilderTaskProvider) {

		cleanServiceBuilderTaskProvider.configure(
			new Action<CleanServiceBuilderTask>() {

				@Override
				public void execute(
					CleanServiceBuilderTask cleanServiceBuilderTask) {

					cleanServiceBuilderTask.setServiceXmlFile(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								BuildServiceTask buildServiceTask =
									buildServiceTaskProvider.get();

								return buildServiceTask.getInputFile();
							}

						});
				}

			});
	}

	@SuppressWarnings("serial")
	private void _configureTaskProcessResourcesProvider(
		final TaskProvider<BuildServiceTask> buildServiceTaskProvider,
		TaskProvider<Copy> processResourcesTaskProvider) {

		processResourcesTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy processResourcesCopy) {
					processResourcesCopy.setDuplicatesStrategy(
						DuplicatesStrategy.INCLUDE);

					processResourcesCopy.into(
						"META-INF",
						new Closure<Void>(processResourcesCopy) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								BuildServiceTask buildServiceTask =
									buildServiceTaskProvider.get();

								File inputFile =
									buildServiceTask.getInputFile();

								File dir = inputFile.getParentFile();

								String dirName = dir.getName();

								if (!dirName.equals("META-INF")) {
									copySpec.from(inputFile);
								}
							}

						});
				}

			});
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.service.builder";

}