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

package com.liferay.gradle.plugins.node;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.NpmShrinkwrapTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.util.StringUtil;

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodePlugin implements Plugin<Project> {

	public static final String CLEAN_NPM_TASK_NAME = "cleanNPM";

	public static final String DOWNLOAD_NODE_TASK_NAME = "downloadNode";

	public static final String EXTENSION_NAME = "node";

	public static final String NPM_INSTALL_TASK_NAME = "npmInstall";

	public static final String NPM_RUN_BUILD_TASK_NAME = "npmRunBuild";

	public static final String NPM_SHRINKWRAP_TASK_NAME = "npmShrinkwrap";

	@Override
	public void apply(Project project) {
		final NodeExtension nodeExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, NodeExtension.class);

		final DownloadNodeTask downloadNodeTask = _addTaskDownloadNode(
			project, nodeExtension);

		Delete cleanNpmTask = _addTaskCleanNpm(project);

		NpmInstallTask npmInstallTask = _addTaskNpmInstall(
			project, cleanNpmTask);

		Map<String, Object> packageJsonMap = null;

		File packageJsonFile = npmInstallTask.getPackageJsonFile();

		if (packageJsonFile.exists()) {
			JsonSlurper jsonSlurper = new JsonSlurper();

			packageJsonMap = (Map<String, Object>)jsonSlurper.parse(
				packageJsonFile);
		}

		_addTaskNpmShrinkwrap(project, cleanNpmTask, npmInstallTask);
		_addTasksNpmRun(npmInstallTask, packageJsonMap);

		_configureTasksDownloadNodeModule(
			project, npmInstallTask, packageJsonMap);

		_configureTasksExecuteNode(
			project, nodeExtension, GradleUtil.isRunningInsideDaemon());

		_configureTasksPublishNodeModule(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskDownloadNodeGlobal(
						downloadNodeTask, nodeExtension);
					_configureTasksExecuteNpm(project, nodeExtension);
				}

			});
	}

	private Delete _addTaskCleanNpm(Project project) {
		Delete delete = GradleUtil.addTask(
			project, CLEAN_NPM_TASK_NAME, Delete.class);

		delete.delete("node_modules", "npm-shrinkwrap.json");
		delete.setDescription("Deletes NPM files from this project.");

		return delete;
	}

	private DownloadNodeTask _addTaskDownloadNode(
		Project project, final NodeExtension nodeExtension) {

		return _addTaskDownloadNode(
			project, DOWNLOAD_NODE_TASK_NAME, nodeExtension);
	}

	private DownloadNodeTask _addTaskDownloadNode(
		Project project, String taskName, final NodeExtension nodeExtension) {

		DownloadNodeTask downloadNodeTask = GradleUtil.addTask(
			project, taskName, DownloadNodeTask.class);

		downloadNodeTask.setNodeDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return nodeExtension.getNodeDir();
				}

			});

		downloadNodeTask.setNodeExeUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return nodeExtension.getNodeExeUrl();
				}

			});

		downloadNodeTask.setNodeUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return nodeExtension.getNodeUrl();
				}

			});

		downloadNodeTask.setNpmUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return nodeExtension.getNpmUrl();
				}

			});

		downloadNodeTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return nodeExtension.isDownload();
				}

			});

		downloadNodeTask.setDescription(
			"Downloads Node.js in the project build directory.");

		return downloadNodeTask;
	}

	private NpmInstallTask _addTaskNpmInstall(
		Project project, Delete cleanNpmTask) {

		NpmInstallTask npmInstallTask = GradleUtil.addTask(
			project, NPM_INSTALL_TASK_NAME, NpmInstallTask.class);

		npmInstallTask.mustRunAfter(cleanNpmTask);
		npmInstallTask.setDescription(
			"Installs Node packages from package.json.");
		npmInstallTask.setNpmInstallRetries(2);

		return npmInstallTask;
	}

	private ExecuteNpmTask _addTaskNpmRun(
		String name, NpmInstallTask npmInstallTask) {

		Project project = npmInstallTask.getProject();

		String taskName = "npmRun" + StringUtil.capitalize(name);

		final ExecuteNpmTask executeNpmTask = GradleUtil.addTask(
			project, taskName, ExecuteNpmTask.class);

		executeNpmTask.dependsOn(npmInstallTask);
		executeNpmTask.setArgs("run-script", name);
		executeNpmTask.setDescription("Runs the \"" + name + "\" NPM script.");
		executeNpmTask.setGroup(BasePlugin.BUILD_GROUP);

		if (taskName.equals(NPM_RUN_BUILD_TASK_NAME)) {
			PluginContainer pluginContainer = project.getPlugins();

			pluginContainer.withType(
				JavaPlugin.class,
				new Action<JavaPlugin>() {

					@Override
					public void execute(JavaPlugin javaPlugin) {
						_configureTaskNpmRunBuildForJavaPlugin(executeNpmTask);
					}

				});
		}

		return executeNpmTask;
	}

	private NpmShrinkwrapTask _addTaskNpmShrinkwrap(
		Project project, Delete cleanNpmTask, NpmInstallTask npmInstallTask) {

		NpmShrinkwrapTask npmShrinkwrapTask = GradleUtil.addTask(
			project, NPM_SHRINKWRAP_TASK_NAME, NpmShrinkwrapTask.class);

		npmShrinkwrapTask.dependsOn(cleanNpmTask, npmInstallTask);
		npmShrinkwrapTask.setDescription(
			"Locks down the versions of a package's dependencies in order to " +
				"control which versions of each dependency will be used.");

		return npmShrinkwrapTask;
	}

	private void _addTasksNpmRun(
		NpmInstallTask npmInstallTask, Map<String, Object> packageJsonMap) {

		if (packageJsonMap == null) {
			return;
		}

		Map<String, String> scriptsJsonMap =
			(Map<String, String>)packageJsonMap.get("scripts");

		if (scriptsJsonMap == null) {
			return;
		}

		for (String name : scriptsJsonMap.keySet()) {
			_addTaskNpmRun(name, npmInstallTask);
		}
	}

	private void _configureTaskDownloadNodeGlobal(
		DownloadNodeTask downloadNodeTask, NodeExtension nodeExtension) {

		if (!nodeExtension.isDownload() || !nodeExtension.isGlobal()) {
			return;
		}

		Project project = downloadNodeTask.getProject();

		Project rootProject = project.getRootProject();

		DownloadNodeTask rootDownloadNodeTask = null;

		TaskContainer taskContainer = rootProject.getTasks();

		Set<DownloadNodeTask> rootDownloadNodeTasks = taskContainer.withType(
			DownloadNodeTask.class);

		File nodeDir = downloadNodeTask.getNodeDir();
		String nodeExeUrl = downloadNodeTask.getNodeExeUrl();
		String nodeUrl = downloadNodeTask.getNodeUrl();

		for (DownloadNodeTask curRootDownloadNodeTask : rootDownloadNodeTasks) {
			if (nodeDir.equals(curRootDownloadNodeTask.getNodeDir()) &&
				nodeExeUrl.equals(curRootDownloadNodeTask.getNodeExeUrl()) &&
				nodeUrl.equals(curRootDownloadNodeTask.getNodeUrl())) {

				rootDownloadNodeTask = curRootDownloadNodeTask;

				break;
			}
		}

		if (rootDownloadNodeTask == null) {
			String taskName = DOWNLOAD_NODE_TASK_NAME;

			if (!rootDownloadNodeTasks.isEmpty()) {
				taskName += rootDownloadNodeTasks.size();
			}

			rootDownloadNodeTask = _addTaskDownloadNode(
				rootProject, taskName, nodeExtension);
		}

		downloadNodeTask.deleteAllActions();
		downloadNodeTask.dependsOn(rootDownloadNodeTask);
	}

	private void _configureTaskDownloadNodeModule(
		DownloadNodeModuleTask downloadNodeModuleTask,
		final NpmInstallTask npmInstallTask,
		final Map<String, Object> packageJsonMap) {

		downloadNodeModuleTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					DownloadNodeModuleTask downloadNodeModuleTask =
						(DownloadNodeModuleTask)task;

					File moduleDir = downloadNodeModuleTask.getModuleDir();

					File moduleParentDir = moduleDir.getParentFile();

					if (!moduleParentDir.equals(
							npmInstallTask.getNodeModulesDir())) {

						return true;
					}

					if (packageJsonMap == null) {
						return true;
					}

					String moduleName = downloadNodeModuleTask.getModuleName();

					Map<String, Object> dependenciesJsonMap =
						(Map<String, Object>)packageJsonMap.get("dependencies");

					if ((dependenciesJsonMap != null) &&
						dependenciesJsonMap.containsKey(moduleName)) {

						return false;
					}

					dependenciesJsonMap =
						(Map<String, Object>)packageJsonMap.get(
							"devDependencies");

					if ((dependenciesJsonMap != null) &&
						dependenciesJsonMap.containsKey(moduleName)) {

						return false;
					}

					return true;
				}

			});
	}

	private void _configureTaskExecuteNode(
		ExecuteNodeTask executeNodeTask, final NodeExtension nodeExtension,
		boolean useGradleExec) {

		executeNodeTask.setNodeDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					if (nodeExtension.isDownload()) {
						return nodeExtension.getNodeDir();
					}

					return null;
				}

			});

		executeNodeTask.setUseGradleExec(useGradleExec);
	}

	private void _configureTaskExecuteNpm(
		ExecuteNpmTask executeNpmTask, NodeExtension nodeExtension) {

		executeNpmTask.args(nodeExtension.getNpmArgs());
	}

	private void _configureTaskNpmRunBuildForJavaPlugin(
		ExecuteNpmTask executeNpmTask) {

		executeNpmTask.mustRunAfter(JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		Task classesTask = GradleUtil.getTask(
			executeNpmTask.getProject(), JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(executeNpmTask);
	}

	private void _configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		final Project project = publishNodeModuleTask.getProject();

		publishNodeModuleTask.setModuleDescription(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return project.getDescription();
				}

			});

		publishNodeModuleTask.setModuleName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String moduleName = _osgiHelper.getBundleSymbolicName(
						project);

					int pos = moduleName.indexOf('.');

					if (pos != -1) {
						moduleName = moduleName.substring(pos + 1);

						moduleName = moduleName.replace('.', '-');
					}

					return moduleName;
				}

			});

		publishNodeModuleTask.setModuleVersion(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});
	}

	private void _configureTasksDownloadNodeModule(
		Project project, final NpmInstallTask npmInstallTask,
		final Map<String, Object> packageJsonMap) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			DownloadNodeModuleTask.class,
			new Action<DownloadNodeModuleTask>() {

				@Override
				public void execute(
					DownloadNodeModuleTask downloadNodeModuleTask) {

					_configureTaskDownloadNodeModule(
						downloadNodeModuleTask, npmInstallTask, packageJsonMap);
				}

			});
	}

	private void _configureTasksExecuteNode(
		Project project, final NodeExtension nodeExtension,
		final boolean useGradleExec) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNodeTask.class,
			new Action<ExecuteNodeTask>() {

				@Override
				public void execute(ExecuteNodeTask executeNodeTask) {
					_configureTaskExecuteNode(
						executeNodeTask, nodeExtension, useGradleExec);
				}

			});
	}

	private void _configureTasksExecuteNpm(
		Project project, final NodeExtension nodeExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNpmTask.class,
			new Action<ExecuteNpmTask>() {

				@Override
				public void execute(ExecuteNpmTask executeNpmTask) {
					_configureTaskExecuteNpm(executeNpmTask, nodeExtension);
				}

			});
	}

	private void _configureTasksPublishNodeModule(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					_configureTaskPublishNodeModule(publishNodeModuleTask);
				}

			});
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}