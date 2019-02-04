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

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.internal.util.StringUtil;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.NpmRunTask;
import com.liferay.gradle.plugins.node.tasks.NpmShrinkwrapTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;

import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.language.jvm.tasks.ProcessResources;
import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
public class NodePlugin implements Plugin<Project> {

	public static final String CLEAN_NPM_TASK_NAME = "cleanNPM";

	public static final String DOWNLOAD_NODE_TASK_NAME = "downloadNode";

	public static final String EXTENSION_NAME = "node";

	public static final String NPM_INSTALL_TASK_NAME = "npmInstall";

	public static final String NPM_PACKAGE_LOCK_TASK_NAME = "npmPackageLock";

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

		_addTaskNpmPackageLock(project, cleanNpmTask, npmInstallTask);
		_addTaskNpmShrinkwrap(project, cleanNpmTask, npmInstallTask);
		_addTasksNpmRun(npmInstallTask, packageJsonMap);

		_configureTasksDownloadNodeModule(
			project, npmInstallTask, packageJsonMap);

		_configureTasksExecuteNode(
			project, nodeExtension, GradleUtil.isRunningInsideDaemon());
		_configureTasksExecuteNpm(project, nodeExtension);

		_configureTasksPublishNodeModule(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskDownloadNodeGlobal(
						downloadNodeTask, nodeExtension);
					_configureTasksExecuteNpmArgs(project, nodeExtension);
					_configureTasksNpmInstall(project, nodeExtension);
					_configureTasksNpmRun(project, nodeExtension);
				}

			});
	}

	private Delete _addTaskCleanNpm(Project project) {
		Delete delete = GradleUtil.addTask(
			project, CLEAN_NPM_TASK_NAME, Delete.class);

		delete.delete(
			"node_modules", "npm-shrinkwrap.json", "package-lock.json");
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

	private Task _addTaskNpmPackageLock(
		Project project, Delete cleanNpmTask, NpmInstallTask npmInstallTask) {

		Task task = project.task(NPM_PACKAGE_LOCK_TASK_NAME);

		task.dependsOn(cleanNpmTask, npmInstallTask);
		task.setDescription(
			"Deletes NPM files and installs Node packages from package.json.");

		return task;
	}

	private ExecuteNpmTask _addTaskNpmRun(
		String scriptName, NpmInstallTask npmInstallTask) {

		Project project = npmInstallTask.getProject();

		String taskName = "npmRun" + StringUtil.camelCase(scriptName, true);

		final NpmRunTask npmRunTask = GradleUtil.addTask(
			project, taskName, NpmRunTask.class);

		npmRunTask.dependsOn(npmInstallTask);
		npmRunTask.setDescription(
			"Runs the \"" + scriptName + "\" NPM script.");
		npmRunTask.setGroup(BasePlugin.BUILD_GROUP);
		npmRunTask.setScriptName(scriptName);

		npmRunTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					NpmRunTask npmRunTask = (NpmRunTask)task;

					String result = npmRunTask.getResult();

					if (result.contains("errors during Soy compilation")) {
						project.delete(npmRunTask.getSourceDigestFile());

						throw new GradleException("Soy compile error");
					}
				}

			});

		if (taskName.equals(NPM_RUN_BUILD_TASK_NAME)) {
			PluginContainer pluginContainer = project.getPlugins();

			pluginContainer.withType(
				JavaPlugin.class,
				new Action<JavaPlugin>() {

					@Override
					public void execute(JavaPlugin javaPlugin) {
						_configureTaskNpmRunBuildForJavaPlugin(npmRunTask);
					}

				});
		}
		else if (taskName.equals(_NPM_RUN_TEST_TASK_NAME)) {
			PluginContainer pluginContainer = project.getPlugins();

			pluginContainer.withType(
				LifecycleBasePlugin.class,
				new Action<LifecycleBasePlugin>() {

					@Override
					public void execute(
						LifecycleBasePlugin lifecycleBasePlugin) {

						_configureTaskNpmRunTestForLifecycleBasePlugin(
							npmRunTask);
					}

				});
		}

		return npmRunTask;
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

		for (String scriptName : scriptsJsonMap.keySet()) {
			_addTaskNpmRun(scriptName, npmInstallTask);
		}
	}

	private void _configureTaskDownloadNodeGlobal(
		DownloadNodeTask downloadNodeTask, NodeExtension nodeExtension) {

		Project project = downloadNodeTask.getProject();

		if (!nodeExtension.isDownload() || !nodeExtension.isGlobal() ||
			(project.getParent() == null)) {

			return;
		}

		Project rootProject = project.getRootProject();

		DownloadNodeTask rootDownloadNodeTask = null;

		TaskContainer taskContainer = rootProject.getTasks();

		Set<DownloadNodeTask> rootDownloadNodeTasks = taskContainer.withType(
			DownloadNodeTask.class);

		File nodeDir = downloadNodeTask.getNodeDir();
		String nodeUrl = downloadNodeTask.getNodeUrl();

		for (DownloadNodeTask curRootDownloadNodeTask : rootDownloadNodeTasks) {
			if (nodeDir.equals(curRootDownloadNodeTask.getNodeDir()) &&
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
		final ExecuteNpmTask executeNpmTask,
		final NodeExtension nodeExtension) {

		final Callable<Boolean> useGlobalConcurrentCacheCallable =
			new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					if ((_node8VersionNumber.compareTo(
							VersionNumber.parse(
								nodeExtension.getNodeVersion())) <= 0) ||
						(_npm5VersionNumber.compareTo(
							VersionNumber.parse(
								nodeExtension.getNpmVersion())) <= 0)) {

						return true;
					}

					return false;
				}

			};

		executeNpmTask.setCacheConcurrent(useGlobalConcurrentCacheCallable);

		executeNpmTask.setCacheDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					if (useGlobalConcurrentCacheCallable.call()) {
						return null;
					}

					File nodeDir = executeNpmTask.getNodeDir();

					if (nodeDir == null) {
						return null;
					}

					return new File(nodeDir, ".cache");
				}

			});
	}

	private void _configureTaskExecuteNpmArgs(
		ExecuteNpmTask executeNpmTask, NodeExtension nodeExtension) {

		executeNpmTask.args(nodeExtension.getNpmArgs());
	}

	private void _configureTaskNpmInstall(
		NpmInstallTask npmInstallTask, NodeExtension nodeExtension) {

		npmInstallTask.setNodeVersion(nodeExtension.getNodeVersion());
		npmInstallTask.setNpmVersion(nodeExtension.getNpmVersion());
	}

	private void _configureTaskNpmRun(
		NpmRunTask npmRunTask, NodeExtension nodeExtension) {

		npmRunTask.setNodeVersion(nodeExtension.getNodeVersion());
		npmRunTask.setNpmVersion(nodeExtension.getNpmVersion());

		Project project = npmRunTask.getProject();

		PluginContainer pluginContainer = project.getPlugins();

		if (pluginContainer.hasPlugin(JavaPlugin.class)) {
			SourceSet sourceSet = GradleUtil.getSourceSet(
				npmRunTask.getProject(), SourceSet.MAIN_SOURCE_SET_NAME);

			SourceSetOutput sourceSetOutput = sourceSet.getOutput();

			File classesDir = sourceSetOutput.getClassesDir();

			if (!classesDir.exists()) {
				TaskOutputs taskOutputs = npmRunTask.getOutputs();

				taskOutputs.upToDateWhen(
					new Spec<Task>() {

						@Override
						public boolean isSatisfiedBy(Task task) {
							return false;
						}

					});
			}
		}
	}

	private void _configureTaskNpmRunBuildForJavaPlugin(NpmRunTask npmRunTask) {
		npmRunTask.mustRunAfter(JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		Task classesTask = GradleUtil.getTask(
			npmRunTask.getProject(), JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(npmRunTask);

		final File sourceDigestFile = npmRunTask.getSourceDigestFile();

		if (!_isStale(sourceDigestFile, npmRunTask.getSourceFiles())) {
			Project project = npmRunTask.getProject();

			ProcessResources processResourcesTask =
				(ProcessResources)GradleUtil.getTask(
					project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

			processResourcesTask.doFirst(
				new Action<Task>() {

					@Override
					public void execute(Task task) {
						ProcessResources processResourcesTask =
							(ProcessResources)task;

						final File processResourcesDir =
							processResourcesTask.getDestinationDir();

						final File npmRunBuildOutputsDir = new File(
							sourceDigestFile.getParentFile(), "outputs");

						project.delete(npmRunBuildOutputsDir);

						npmRunBuildOutputsDir.mkdirs();

						project.copy(
							new Action<CopySpec>() {

								@Override
								public void execute(CopySpec copySpec) {
									copySpec.from(processResourcesDir);
									copySpec.include("**/*.js");
									copySpec.into(npmRunBuildOutputsDir);
									copySpec.setIncludeEmptyDirs(false);
								}

							});
					}

				});

			processResourcesTask.doLast(
				new Action<Task>() {

					@Override
					public void execute(Task task) {
						ProcessResources processResourcesTask =
							(ProcessResources)task;

						final File processResourcesDir =
							processResourcesTask.getDestinationDir();

						final File npmRunBuildOutputsDir = new File(
							sourceDigestFile.getParentFile(), "outputs");

						project.copy(
							new Action<CopySpec>() {

								@Override
								public void execute(CopySpec copySpec) {
									copySpec.from(npmRunBuildOutputsDir);
									copySpec.into(processResourcesDir);
								}

							});
					}

				});
		}
	}

	private void _configureTaskNpmRunTestForLifecycleBasePlugin(
		ExecuteNpmTask executeNpmTask) {

		Task checkTask = GradleUtil.getTask(
			executeNpmTask.getProject(), LifecycleBasePlugin.CHECK_TASK_NAME);

		checkTask.dependsOn(executeNpmTask);
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

	private void _configureTasksExecuteNpmArgs(
		Project project, final NodeExtension nodeExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNpmTask.class,
			new Action<ExecuteNpmTask>() {

				@Override
				public void execute(ExecuteNpmTask executeNpmTask) {
					_configureTaskExecuteNpmArgs(executeNpmTask, nodeExtension);
				}

			});
	}

	private void _configureTasksNpmInstall(
		Project project, final NodeExtension nodeExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					_configureTaskNpmInstall(npmInstallTask, nodeExtension);
				}

			});
	}

	private void _configureTasksNpmRun(
		Project project, final NodeExtension nodeExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmRunTask.class,
			new Action<NpmRunTask>() {

				@Override
				public void execute(NpmRunTask npmRunTask) {
					_configureTaskNpmRun(npmRunTask, nodeExtension);
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

	private boolean _isStale(
		File sourceDigestFile, FileCollection sourceFileCollection) {

		if (!sourceDigestFile.exists()) {
			return true;
		}

		byte[] bytes = null;

		try {
			bytes = Files.readAllBytes(sourceDigestFile.toPath());
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

		String oldDigest = new String(bytes, StandardCharsets.UTF_8);
		String newDigest = FileUtil.getDigest(sourceFileCollection);

		if (!Objects.equals(oldDigest, newDigest)) {
			return true;
		}

		return false;
	}

	private static final String _NPM_RUN_TEST_TASK_NAME = "npmRunTest";

	private static final VersionNumber _node8VersionNumber =
		VersionNumber.version(8);
	private static final VersionNumber _npm5VersionNumber =
		VersionNumber.version(5);
	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}