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

package com.liferay.gradle.plugins.workspace.internal.configurators;

import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.tasks.UpdatePropertiesTask;
import com.liferay.gradle.util.FileUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.AntBuilder;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class PluginsProjectConfigurator extends BaseProjectConfigurator {

	public static final String UPDATE_PROPERTIES_TASK_NAME = "updateProperties";

	public PluginsProjectConfigurator(Settings settings) {
		super(settings);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		Task initBundleTask = GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.INIT_BUNDLE_TASK_NAME);

		_configureAnt(project);

		UpdatePropertiesTask updatePropertiesTask = _addTaskUpdateProperties(
			project, workspaceExtension);

		_addTaskBuild(project, updatePropertiesTask);

		_configureTaskWar(project, workspaceExtension, initBundleTask);

		_configureRootTaskDistBundle(
			project, RootProjectConfigurator.DIST_BUNDLE_TAR_TASK_NAME);
		_configureRootTaskDistBundle(
			project, RootProjectConfigurator.DIST_BUNDLE_ZIP_TASK_NAME);
	}

	@Override
	public String getName() {
		return _NAME;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		File buildXmlFile = new File(rootDir, "build.xml");

		if (!buildXmlFile.exists()) {
			return Collections.emptySet();
		}

		return Collections.singleton(rootDir);
	}

	@Override
	protected String getDefaultRootDirName() {
		return _DEFAULT_ROOT_DIR_NAME;
	}

	@Override
	protected String getDefaultRootDirPropertyName() {
		return _DEFAULT_ROOT_DIR_PROPERTY_NAME;
	}

	private Task _addTaskBuild(
		Project project, UpdatePropertiesTask updatePropertiesTask) {

		Task task = project.task(LifecycleBasePlugin.BUILD_TASK_NAME);

		task.dependsOn(updatePropertiesTask, WarPlugin.WAR_TASK_NAME);
		task.setDescription("Alias for 'ant war'.");

		return task;
	}

	private UpdatePropertiesTask _addTaskUpdateProperties(
		Project project, final WorkspaceExtension workspaceExtension) {

		UpdatePropertiesTask updatePropertiesTask = GradleUtil.addTask(
			project, UPDATE_PROPERTIES_TASK_NAME, UpdatePropertiesTask.class);

		updatePropertiesTask.property(
			"app.server.parent.dir",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return FileUtil.getAbsolutePath(
						workspaceExtension.getHomeDir());
				}

			});

		updatePropertiesTask.setDescription(
			"Updates the Plugins SDK build properties with the workspace " +
				"configuration.");

		String userName = System.getProperty("user.name");

		updatePropertiesTask.setPropertiesFile(
			"build." + userName + ".properties");

		return updatePropertiesTask;
	}

	private void _configureAnt(Project project) {
		AntBuilder antBuilder = project.getAnt();

		antBuilder.importBuild("build.xml");
	}

	private void _configureRootTaskDistBundle(
		final Project project, String rootTaskName) {

		CopySpec copySpec = (CopySpec)GradleUtil.getTask(
			project.getRootProject(), rootTaskName);

		copySpec.into(
			"osgi/modules",
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					ConfigurableFileTree configurableFileTree =
						project.fileTree("dist");

					configurableFileTree.builtBy(WarPlugin.WAR_TASK_NAME);
					configurableFileTree.include("*.war");

					copySpec.from(configurableFileTree);
				}

			});
	}

	private void _configureTaskWar(
		Project project, final WorkspaceExtension workspaceExtension,
		final Task initBundleTask) {

		Task task = GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		task.dependsOn(
			new Callable<Task>() {

				@Override
				public Task call() throws Exception {
					File homeDir = workspaceExtension.getHomeDir();

					if (homeDir.exists()) {
						return null;
					}

					return initBundleTask;
				}

			});
	}

	private static final String _DEFAULT_ROOT_DIR_NAME = "plugins-sdk";

	private static final String _DEFAULT_ROOT_DIR_PROPERTY_NAME =
		WorkspacePlugin.PROPERTY_PREFIX + "plugins.sdk.dir";

	private static final String _NAME = "plugins";

}