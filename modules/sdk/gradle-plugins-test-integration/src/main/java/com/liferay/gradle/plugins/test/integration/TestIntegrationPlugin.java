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

package com.liferay.gradle.plugins.test.integration;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;
import com.liferay.gradle.plugins.test.integration.internal.util.StringUtil;
import com.liferay.gradle.plugins.test.integration.tasks.BaseAppServerTask;
import com.liferay.gradle.plugins.test.integration.tasks.JmxRemotePortSpec;
import com.liferay.gradle.plugins.test.integration.tasks.ManagerSpec;
import com.liferay.gradle.plugins.test.integration.tasks.ModuleFrameworkBaseDirSpec;
import com.liferay.gradle.plugins.test.integration.tasks.SetUpArquillianTask;
import com.liferay.gradle.plugins.test.integration.tasks.SetUpTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StartTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StopTestableTomcatTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.OSDetector;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.StopExecutionException;
import org.gradle.api.tasks.testing.Test;
import org.gradle.process.JavaForkOptions;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "testIntegration";

	public static final String SET_UP_ARQUILLIAN_TASK_NAME = "setUpArquillian";

	public static final String SET_UP_TESTABLE_TOMCAT_TASK_NAME =
		"setUpTestableTomcat";

	public static final String START_TESTABLE_TOMCAT_TASK_NAME =
		"startTestableTomcat";

	public static final String STOP_TESTABLE_TOMCAT_TASK_NAME =
		"stopTestableTomcat";

	@Override
	public void apply(final Project project) {
		GradleUtil.applyPlugin(project, TestIntegrationBasePlugin.class);

		final SourceSet testIntegrationSourceSet = GradleUtil.getSourceSet(
			project,
			TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);
		final Test testIntegrationTask = (Test)GradleUtil.getTask(
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME);

		final TestIntegrationTomcatExtension testIntegrationTomcatExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME + "Tomcat",
				TestIntegrationTomcatExtension.class);

		SetUpTestableTomcatTask setUpTestableTomcatTask =
			_addTaskSetUpTestableTomcat(
				project, testIntegrationTomcatExtension);
		StopTestableTomcatTask stopTestableTomcatTask =
			_addTaskStopTestableTomcat(
				project, testIntegrationTask, testIntegrationTomcatExtension);

		StartTestableTomcatTask startTestableTomcatTask =
			_addTaskStartTestableTomcat(
				project, setUpTestableTomcatTask, stopTestableTomcatTask,
				testIntegrationTomcatExtension);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					SetUpArquillianTask setUpArquillianTask =
						_addTaskSetUpArquillian(
							project, testIntegrationSourceSet,
							testIntegrationTomcatExtension);

					testIntegrationTask.dependsOn(setUpArquillianTask);
				}

			});

		_configureTaskTestIntegration(
			testIntegrationTask, testIntegrationSourceSet,
			testIntegrationTomcatExtension, startTestableTomcatTask);
	}

	private static int _updateStartedAppServerStopCounters(
		File binDir, boolean increment) {

		int originalCounter = 0;

		if (_startedAppServerStopCounters.containsKey(binDir)) {
			originalCounter = _startedAppServerStopCounters.get(binDir);
		}

		int counter = originalCounter;

		if (increment) {
			counter++;
		}
		else {
			counter--;
		}

		_startedAppServerStopCounters.put(binDir, counter);

		return originalCounter;
	}

	private SetUpArquillianTask _addTaskSetUpArquillian(
		final Project project, final SourceSet testIntegrationSourceSet,
		TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		SetUpArquillianTask setUpArquillianTask = GradleUtil.addTask(
			project, SET_UP_ARQUILLIAN_TASK_NAME, SetUpArquillianTask.class);

		setUpArquillianTask.setDescription(
			"Creates the Arquillian container configuration file for this " +
				"project.");

		setUpArquillianTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return _getSrcDir(testIntegrationSourceSet.getResources());
				}

			});

		_configureJmxRemotePortSpec(
			setUpArquillianTask, testIntegrationTomcatExtension);
		_configureManagerSpec(
			setUpArquillianTask, testIntegrationTomcatExtension);

		return setUpArquillianTask;
	}

	private SetUpTestableTomcatTask _addTaskSetUpTestableTomcat(
		Project project,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		final SetUpTestableTomcatTask setUpTestableTomcatTask =
			GradleUtil.addTask(
				project, SET_UP_TESTABLE_TOMCAT_TASK_NAME,
				SetUpTestableTomcatTask.class);

		setUpTestableTomcatTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					_startedAppServersReentrantLock.lock();

					try {
						if (_startedAppServerBinDirs.contains(
								setUpTestableTomcatTask.getBinDir())) {

							return false;
						}

						return true;
					}
					finally {
						_startedAppServersReentrantLock.unlock();
					}
				}

			});

		setUpTestableTomcatTask.setDescription(
			"Configures the local Liferay Tomcat bundle to run integration " +
				"tests.");

		setUpTestableTomcatTask.setDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return testIntegrationTomcatExtension.getDir();
				}

			});

		_configureJmxRemotePortSpec(
			setUpTestableTomcatTask, testIntegrationTomcatExtension);
		_configureManagerSpec(
			setUpTestableTomcatTask, testIntegrationTomcatExtension);
		_configureModuleFrameworkBaseDirSpec(
			setUpTestableTomcatTask, testIntegrationTomcatExtension);

		return setUpTestableTomcatTask;
	}

	private StartTestableTomcatTask _addTaskStartTestableTomcat(
		Project project, SetUpTestableTomcatTask setUpTestableTomcatTask,
		StopTestableTomcatTask stopTestableTomcatTask,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		StartTestableTomcatTask startTestableTomcatTask = GradleUtil.addTask(
			project, START_TESTABLE_TOMCAT_TASK_NAME,
			StartTestableTomcatTask.class);

		startTestableTomcatTask.dependsOn(setUpTestableTomcatTask);

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				StartTestableTomcatTask startTestableTomcatTask =
					(StartTestableTomcatTask)task;

				File binDir = startTestableTomcatTask.getBinDir();
				Logger logger = startTestableTomcatTask.getLogger();

				boolean started = false;

				_startedAppServersReentrantLock.lock();

				try {
					if (_startedAppServerBinDirs.contains(binDir)) {
						started = true;
					}
					else {
						_startedAppServerBinDirs.add(binDir);
					}
				}
				finally {
					_startedAppServersReentrantLock.unlock();
				}

				if (started) {
					if (logger.isDebugEnabled()) {
						logger.debug(
							"Application server {} is already started", binDir);
					}

					Project project = startTestableTomcatTask.getProject();

					Gradle gradle = project.getGradle();

					StartParameter startParameter = gradle.getStartParameter();

					if (startParameter.isParallelProjectExecutionEnabled()) {
						if (logger.isDebugEnabled()) {
							logger.debug(
								"Waiting for application server {} to be " +
									"reachable",
								binDir);
						}

						startTestableTomcatTask.waitForReachable();
					}

					throw new StopExecutionException();
				}
			}

		};

		startTestableTomcatTask.doFirst(action);

		startTestableTomcatTask.finalizedBy(stopTestableTomcatTask);

		startTestableTomcatTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					StartTestableTomcatTask startTestableTomcatTask =
						(StartTestableTomcatTask)task;

					if (startTestableTomcatTask.isReachable()) {
						return false;
					}

					return true;
				}

			});

		startTestableTomcatTask.setDescription(
			"Starts the local Liferay Tomcat bundle.");
		startTestableTomcatTask.setExecutable(
			_getTomcatExecutableFileName("catalina"));
		startTestableTomcatTask.setExecutableArgs(Collections.singleton("run"));
		startTestableTomcatTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		startTestableTomcatTask.setLiferayHome(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return testIntegrationTomcatExtension.getLiferayHome();
				}

			});

		_configureBaseAppServerTask(
			startTestableTomcatTask, testIntegrationTomcatExtension);

		return startTestableTomcatTask;
	}

	private StopTestableTomcatTask _addTaskStopTestableTomcat(
		Project project, Test testIntegrationTask,
		TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		final StopTestableTomcatTask stopTestableTomcatTask =
			GradleUtil.addTask(
				project, STOP_TESTABLE_TOMCAT_TASK_NAME,
				StopTestableTomcatTask.class);

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				StopTestableTomcatTask stopTestableTomcatTask =
					(StopTestableTomcatTask)task;

				File binDir = stopTestableTomcatTask.getBinDir();
				Logger logger = stopTestableTomcatTask.getLogger();

				_startedAppServersReentrantLock.lock();

				try {
					if (!_startedAppServerBinDirs.contains(binDir)) {
						if (logger.isDebugEnabled()) {
							logger.debug(
								"Application server {} is already stopped",
								binDir);
						}

						throw new StopExecutionException();
					}

					int originalCounter = _updateStartedAppServerStopCounters(
						binDir, false);

					if (originalCounter > 1) {
						if (logger.isDebugEnabled()) {
							logger.debug(
								"Application server {} cannot be stopped " +
									"now, still {} to execute",
								binDir, originalCounter - 1);
						}

						throw new StopExecutionException();
					}
				}
				finally {
					_startedAppServersReentrantLock.unlock();
				}
			}

		};

		stopTestableTomcatTask.doFirst(action);

		action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				StopTestableTomcatTask setUpTestableTomcatTask =
					(StopTestableTomcatTask)task;

				_startedAppServersReentrantLock.lock();

				try {
					_startedAppServerBinDirs.remove(
						setUpTestableTomcatTask.getBinDir());
				}
				finally {
					_startedAppServersReentrantLock.unlock();
				}
			}

		};

		stopTestableTomcatTask.doLast(action);

		stopTestableTomcatTask.mustRunAfter(testIntegrationTask);
		stopTestableTomcatTask.setDescription(
			"Stops the local Liferay Tomcat bundle.");
		stopTestableTomcatTask.setExecutable(
			_getTomcatExecutableFileName("shutdown"));
		stopTestableTomcatTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		_configureBaseAppServerTask(
			stopTestableTomcatTask, testIntegrationTomcatExtension);
		_configureModuleFrameworkBaseDirSpec(
			stopTestableTomcatTask, testIntegrationTomcatExtension);

		Gradle gradle = project.getGradle();

		TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

		Closure<Void> closure = new Closure<Void>(gradle) {

			@SuppressWarnings("unused")
			public void doCall(TaskExecutionGraph taskExecutionGraph) {
				if (taskExecutionGraph.hasTask(stopTestableTomcatTask)) {
					_startedAppServersReentrantLock.lock();

					try {
						_updateStartedAppServerStopCounters(
							stopTestableTomcatTask.getBinDir(), true);
					}
					finally {
						_startedAppServersReentrantLock.unlock();
					}
				}
			}

		};

		taskExecutionGraph.whenReady(closure);

		return stopTestableTomcatTask;
	}

	private void _configureBaseAppServerTask(
		BaseAppServerTask baseAppServerTask,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		baseAppServerTask.setBinDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						testIntegrationTomcatExtension.getDir(), "bin");
				}

			});

		baseAppServerTask.setCheckPath(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return testIntegrationTomcatExtension.getCheckPath();
				}

			});

		baseAppServerTask.setPortNumber(
			new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return testIntegrationTomcatExtension.getPortNumber();
				}

			});
	}

	private void _configureJmxRemotePortSpec(
		JmxRemotePortSpec jmxRemotePortSpec,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		jmxRemotePortSpec.setJmxRemotePort(
			new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return testIntegrationTomcatExtension.getJmxRemotePort();
				}

			});
	}

	private void _configureManagerSpec(
		ManagerSpec managerSpec,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		managerSpec.setManagerPassword(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return testIntegrationTomcatExtension.getManagerPassword();
				}

			});

		managerSpec.setManagerUserName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return testIntegrationTomcatExtension.getManagerUserName();
				}

			});
	}

	private void _configureModuleFrameworkBaseDirSpec(
		ModuleFrameworkBaseDirSpec moduleFrameworkBaseDirSpec,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		moduleFrameworkBaseDirSpec.setModuleFrameworkBaseDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File dir = testIntegrationTomcatExtension.getLiferayHome();

					if (dir != null) {
						dir = new File(dir, "osgi");
					}

					return dir;
				}

			});
	}

	private void _configureTaskSystemProperty(
		JavaForkOptions javaForkOptions, String key, File file) {

		Map<String, Object> systemProperties =
			javaForkOptions.getSystemProperties();

		if (!systemProperties.containsKey(key)) {
			systemProperties.put(key, FileUtil.getAbsolutePath(file));
		}
	}

	private void _configureTaskTestIntegration(
		final Test test, final SourceSet testIntegrationSourceSet,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension,
		final StartTestableTomcatTask startTestableTomcatTask) {

		Closure<Task> closure = new Closure<Task>(test.getProject()) {

			@SuppressWarnings("unused")
			public Task doCall(Test test) {
				SourceDirectorySet sourceDirectorySet =
					testIntegrationSourceSet.getResources();

				for (File dir : sourceDirectorySet.getSrcDirs()) {
					File file = new File(
						dir, _SKIP_MANAGED_APP_SERVER_FILE_NAME);

					if (file.exists()) {
						return null;
					}
				}

				return startTestableTomcatTask;
			}

		};

		test.dependsOn(closure);

		test.jvmArgs(
			"-Djava.net.preferIPv4Stack=true", "-Dliferay.mode=test",
			"-Duser.timezone=GMT");

		Project project = test.getProject();

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskTestIntegrationEnabled(
						test, testIntegrationSourceSet);

					// GRADLE-2697

					_configureTaskSystemProperty(
						test, "app.server.tomcat.dir",
						testIntegrationTomcatExtension.getDir());
				}

			});
	}

	private void _configureTaskTestIntegrationEnabled(
		Test test, SourceSet testIntegrationSourceSet) {

		Project project = test.getProject();

		Map<String, Object> args = new HashMap<>();

		args.put(
			"excludes",
			StringUtil.replaceEnding(test.getExcludes(), ".class", ".java"));
		args.put(
			"includes",
			StringUtil.replaceEnding(test.getIncludes(), ".class", ".java"));

		SourceDirectorySet sourceDirectorySet =
			testIntegrationSourceSet.getJava();

		for (File dir : sourceDirectorySet.getSrcDirs()) {
			args.put("dir", dir);

			FileTree fileTree = project.fileTree(args);

			if (!fileTree.isEmpty()) {
				return;
			}
		}

		test.setDependsOn(Collections.emptySet());
		test.setEnabled(false);
	}

	private File _getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private String _getTomcatExecutableFileName(String fileName) {
		if (OSDetector.isWindows()) {
			fileName += ".bat";
		}

		return fileName;
	}

	private static final String _SKIP_MANAGED_APP_SERVER_FILE_NAME =
		"skip.managed.app.server";

	private static final Set<File> _startedAppServerBinDirs = new HashSet<>();
	private static final ReentrantLock _startedAppServersReentrantLock =
		new ReentrantLock();
	private static final Map<File, Integer> _startedAppServerStopCounters =
		new HashMap<>();

}