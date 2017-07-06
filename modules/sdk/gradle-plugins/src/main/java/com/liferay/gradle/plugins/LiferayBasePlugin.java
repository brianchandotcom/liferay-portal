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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.AppServer;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.internal.NodeDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayBasePlugin implements Plugin<Project> {

	public static final String DEPLOY_TASK_NAME = "deploy";

	public static final String PORTAL_CONFIGURATION_NAME = "portal";

	@Override
	public void apply(Project project) {
		LiferayExtension liferayExtension = _addLiferayExtension(project);

		NodeDefaultsPlugin.INSTANCE.apply(project);
		SourceFormatterDefaultsPlugin.INSTANCE.apply(project);

		_addConfigurationPortal(project, liferayExtension);
		_addTaskDeploy(project, liferayExtension);

		_configureConfigurations(project, liferayExtension);
		_configureTasksDirectDeploy(project, liferayExtension);
	}

	private Configuration _addConfigurationPortal(
		final Project project, final LiferayExtension liferayExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, PORTAL_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesPortal(project, liferayExtension);
				}

			});

		configuration.setDescription(
			"Configures the classpath from the local Liferay bundle.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesPortal(
		Project project, LiferayExtension liferayExtension) {

		File appServerClassesPortalDir = new File(
			liferayExtension.getAppServerPortalDir(), "WEB-INF/classes");

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, appServerClassesPortalDir);

		File appServerLibPortalDir = new File(
			liferayExtension.getAppServerPortalDir(), "WEB-INF/lib");

		FileTree appServerLibPortalDirJarFiles = FileUtil.getJarsFileTree(
			project, appServerLibPortalDir);

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, appServerLibPortalDirJarFiles);

		FileTree appServerLibGlobalDirJarFiles = FileUtil.getJarsFileTree(
			project, liferayExtension.getAppServerLibGlobalDir(), "mail.jar");

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, appServerLibGlobalDirJarFiles);

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "com.liferay", "net.sf.jargs",
			"1.0");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "com.thoughtworks.qdox", "qdox",
			"1.12.1");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "javax.activation",
			"activation", "1.1");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "javax.servlet",
			"javax.servlet-api", "3.0.1");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "javax.servlet.jsp",
			"javax.servlet.jsp-api", "2.3.1");

		AppServer appServer = liferayExtension.getAppServer();

		appServer.addAdditionalDependencies(PORTAL_CONFIGURATION_NAME);
	}

	private LiferayExtension _addLiferayExtension(Project project) {
		LiferayExtension liferayExtension = GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayExtension.class);

		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-liferay.gradle",
			project);

		return liferayExtension;
	}

	private Copy _addTaskDeploy(
		Project project, final LiferayExtension liferayExtension) {

		Copy copy = GradleUtil.addTask(project, DEPLOY_TASK_NAME, Copy.class);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getDeployDir();
				}

			});

		copy.setDescription("Assembles the project and deploys it to Liferay.");
		copy.setGroup(BasePlugin.BUILD_GROUP);

		return copy;
	}

	private void _configureConfigurations(
		Project project, final LiferayExtension liferayExtension) {

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Action<Configuration> action = new Action<Configuration>() {

			@Override
			public void execute(Configuration configuration) {
				ResolutionStrategy resolutionStrategy =
					configuration.getResolutionStrategy();

				resolutionStrategy.eachDependency(
					new Action<DependencyResolveDetails>() {

						@Override
						public void execute(
							DependencyResolveDetails dependencyResolveDetails) {

							ModuleVersionSelector moduleVersionSelector =
								dependencyResolveDetails.getRequested();

							String version = moduleVersionSelector.getVersion();

							if (!version.equals("default")) {
								return;
							}

							version = liferayExtension.getDefaultVersion(
								moduleVersionSelector);

							dependencyResolveDetails.useVersion(version);
						}

					});
			}

		};

		configurationContainer.all(action);
	}

	private void _configureTaskDirectDeploy(
		DirectDeployTask directDeployTask,
		final LiferayExtension liferayExtension) {

		directDeployTask.setAppServerDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getAppServerDir();
				}

			});

		directDeployTask.setAppServerLibGlobalDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getAppServerLibGlobalDir();
				}

			});

		directDeployTask.setAppServerPortalDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getAppServerPortalDir();
				}

			});

		directDeployTask.setAppServerType(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return liferayExtension.getAppServerType();
				}

			});
	}

	private void _configureTasksDirectDeploy(
		Project project, final LiferayExtension liferayExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			DirectDeployTask.class,
			new Action<DirectDeployTask>() {

				@Override
				public void execute(DirectDeployTask directDeployTask) {
					_configureTaskDirectDeploy(
						directDeployTask, liferayExtension);
				}

			});
	}

}