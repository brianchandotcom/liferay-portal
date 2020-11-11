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

package com.liferay.gradle.plugins.js.transpiler;

import com.liferay.gradle.plugins.js.transpiler.internal.util.JSTranspilerPluginUtil;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.copy.RenameDependencyClosure;

import java.io.File;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskDependency;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class JSTranspilerPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_METAL_CLI_TASK_NAME =
		"downloadMetalCli";

	/**
	 * @deprecated As of 2.4.0, moved to {@link
	 *             JSTranspilerBasePlugin.JS_COMPILE_CONFIGURATION_NAME}
	 */
	@Deprecated
	public static final String JS_COMPILE_CONFIGURATION_NAME =
		JSTranspilerBasePlugin.JS_COMPILE_CONFIGURATION_NAME;

	public static final String SOY_COMPILE_CONFIGURATION_NAME = "soyCompile";

	public static final String TRANSPILE_JS_TASK_NAME = "transpileJS";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JSTranspilerBasePlugin.class);

		Task expandJSCompileDependenciesTask = GradleUtil.getTask(
			project,
			JSTranspilerBasePlugin.EXPAND_JS_COMPILE_DEPENDENCIES_TASK_NAME);
		final NpmInstallTask npmInstallTask =
			(NpmInstallTask)GradleUtil.getTask(
				project, NodePlugin.NPM_INSTALL_TASK_NAME);

		final DownloadNodeModuleTask downloadMetalCliTask =
			_addTaskDownloadMetalCli(project);

		final Configuration soyCompileConfiguration =
			_addConfigurationSoyCompile(project);

		final TranspileJSTask transpileJSTask = _addTaskTranspileJS(
			expandJSCompileDependenciesTask);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_addTasksExpandSoyCompileDependencies(
						transpileJSTask, soyCompileConfiguration);

					_configureTasksTranspileJS(
						project, downloadMetalCliTask, npmInstallTask);
				}

			});
	}

	private Configuration _addConfigurationSoyCompile(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, SOY_COMPILE_CONFIGURATION_NAME);

		configuration.setDescription("Configures additional Soy dependencies.");
		configuration.setVisible(false);

		return configuration;
	}

	private DownloadNodeModuleTask _addTaskDownloadMetalCli(Project project) {
		DownloadNodeModuleTask downloadNodeModuleTask = GradleUtil.addTask(
			project, DOWNLOAD_METAL_CLI_TASK_NAME,
			DownloadNodeModuleTask.class);

		downloadNodeModuleTask.setModuleName("metal-cli");
		downloadNodeModuleTask.setModuleVersion(_METAL_CLI_VERSION);

		return downloadNodeModuleTask;
	}

	private void _addTasksExpandSoyCompileDependencies(
		TranspileJSTask transpileJSTask, Configuration configuration) {

		Project project = transpileJSTask.getProject();

		RenameDependencyClosure renameDependencyClosure =
			new RenameDependencyClosure(project, configuration.getName());

		Iterable<TaskDependency> taskDependencies =
			JSTranspilerPluginUtil.getTaskDependencies(configuration);

		for (File file : configuration) {
			Copy copy = JSTranspilerPluginUtil.addTaskExpandCompileDependency(
				project, file, project.getBuildDir(),
				"expandSoyCompileDependency", renameDependencyClosure);

			copy.dependsOn(taskDependencies);

			transpileJSTask.dependsOn(copy);

			String path = FileUtil.getAbsolutePath(copy.getDestinationDir());

			path += "/META-INF/resources/**/*.soy";

			transpileJSTask.soyDependency(path);
		}
	}

	private TranspileJSTask _addTaskTranspileJS(
		Task expandJSCompileDependenciesTask) {

		Project project = expandJSCompileDependenciesTask.getProject();

		final TranspileJSTask transpileJSTask = GradleUtil.addTask(
			project, TRANSPILE_JS_TASK_NAME, TranspileJSTask.class);

		transpileJSTask.dependsOn(expandJSCompileDependenciesTask);
		transpileJSTask.setDescription("Transpiles JS files.");
		transpileJSTask.setGroup(BasePlugin.BUILD_GROUP);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskTranspileJSForJavaPlugin(transpileJSTask);
				}

			});

		return transpileJSTask;
	}

	private void _configureTasksTranspileJS(
		Project project, final DownloadNodeModuleTask downloadMetalCliTask,
		final NpmInstallTask npmInstallTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			TranspileJSTask.class,
			new Action<TranspileJSTask>() {

				@Override
				public void execute(TranspileJSTask transpileJSTask) {
					_configureTaskTranspileJS(
						transpileJSTask, downloadMetalCliTask, npmInstallTask);
				}

			});
	}

	private void _configureTaskTranspileJS(
		TranspileJSTask transpileJSTask,
		final DownloadNodeModuleTask downloadMetalCliTask,
		final NpmInstallTask npmInstallTask) {

		FileCollection fileCollection = transpileJSTask.getSourceFiles();

		if (!transpileJSTask.isEnabled() ||
			(transpileJSTask.isSkipWhenEmpty() && fileCollection.isEmpty())) {

			transpileJSTask.setDependsOn(Collections.emptySet());
			transpileJSTask.setEnabled(false);

			return;
		}

		transpileJSTask.dependsOn(downloadMetalCliTask, npmInstallTask);

		transpileJSTask.setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						downloadMetalCliTask.getModuleDir(), "index.js");
				}

			});

		transpileJSTask.soyDependency(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return npmInstallTask.getWorkingDir() +
						"/node_modules/clay*/src/**/*.soy";
				}

			},
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return npmInstallTask.getWorkingDir() +
						"/node_modules/metal*/src/**/*.soy";
				}

			});
	}

	private void _configureTaskTranspileJSForJavaPlugin(
		TranspileJSTask transpileJSTask) {

		transpileJSTask.mustRunAfter(JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		Project project = transpileJSTask.getProject();

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		transpileJSTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = _getSrcDir(sourceSet.getResources());

					return new File(resourcesDir, "META-INF/resources");
				}

			});

		transpileJSTask.setWorkingDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return new File(
						sourceSetOutput.getResourcesDir(),
						"META-INF/resources");
				}

			});

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(transpileJSTask);
	}

	private File _getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private static final String _METAL_CLI_VERSION = "1.3.1";

}