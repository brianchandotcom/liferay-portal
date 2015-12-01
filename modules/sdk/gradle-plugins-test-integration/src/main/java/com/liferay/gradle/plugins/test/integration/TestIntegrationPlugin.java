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

import com.liferay.gradle.plugins.test.integration.tasks.BaseAppServerTask;
import com.liferay.gradle.plugins.test.integration.tasks.JmxRemotePortSpec;
import com.liferay.gradle.plugins.test.integration.tasks.ManagerSpec;
import com.liferay.gradle.plugins.test.integration.tasks.SetupArquillianTask;
import com.liferay.gradle.plugins.test.integration.tasks.SetupTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StartTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StopAppServerTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

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
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.StopExecutionException;
import org.gradle.api.tasks.testing.Test;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "testIntegration";

	public static final String SETUP_ARQUILLIAN_TASK_NAME = "setupArquillian";

	public static final String SETUP_TESTABLE_TOMCAT_TASK_NAME =
		"setupTestableTomcat";

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

		SetupTestableTomcatTask setupTestableTomcatTask =
			addTaskSetupTestableTomcat(project, testIntegrationTomcatExtension);
		StopAppServerTask stopTestableTomcatTask = addTaskStopTestableTomcat(
			project, testIntegrationTask, testIntegrationTomcatExtension);
		StartTestableTomcatTask startTestableTomcatTask =
			addTaskStartTestableTomcat(
				project, setupTestableTomcatTask, stopTestableTomcatTask,
				testIntegrationTomcatExtension);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					SetupArquillianTask setupArquillianTask =
						addTaskSetupArquillian(
							project, testIntegrationSourceSet,
							testIntegrationTomcatExtension);

					testIntegrationTask.dependsOn(setupArquillianTask);
				}

			});

		configureTaskTestIntegration(
			testIntegrationTask, testIntegrationSourceSet,
			testIntegrationTomcatExtension, startTestableTomcatTask);
	}

	protected SetupArquillianTask addTaskSetupArquillian(
		final Project project, final SourceSet testIntegrationSourceSet,
		TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		SetupArquillianTask setupArquillianTask = GradleUtil.addTask(
			project, SETUP_ARQUILLIAN_TASK_NAME, SetupArquillianTask.class);

		setupArquillianTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getSrcDir(testIntegrationSourceSet.getResources());
				}

			});

		configureJmxRemotePortSpec(
			setupArquillianTask, testIntegrationTomcatExtension);
		configureManagerSpec(
			setupArquillianTask, testIntegrationTomcatExtension);

		return setupArquillianTask;
	}

	protected SetupTestableTomcatTask addTaskSetupTestableTomcat(
		Project project,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		final SetupTestableTomcatTask setupTestableTomcatTask =
			GradleUtil.addTask(
				project, SETUP_TESTABLE_TOMCAT_TASK_NAME,
				SetupTestableTomcatTask.class);

		setupTestableTomcatTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					_startedAppServersReentrantLock.lock();

					try {
						if (_startedAppServerBinDirs.contains(
								setupTestableTomcatTask.getBinDir())) {

							return false;
						}

						return true;
					}
					finally {
						_startedAppServersReentrantLock.unlock();
					}
				}

			});

		setupTestableTomcatTask.setDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return testIntegrationTomcatExtension.getDir();
				}

			});

		setupTestableTomcatTask.setModuleFrameworkBaseDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						testIntegrationTomcatExtension.getLiferayHome(),
						"osgi");
				}

			});

		configureJmxRemotePortSpec(
			setupTestableTomcatTask, testIntegrationTomcatExtension);
		configureManagerSpec(
			setupTestableTomcatTask, testIntegrationTomcatExtension);

		return setupTestableTomcatTask;
	}

	protected StartTestableTomcatTask addTaskStartTestableTomcat(
		Project project, SetupTestableTomcatTask setupTestableTomcatTask,
		StopAppServerTask stopTestableTomcatTask,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		StartTestableTomcatTask startTestableTomcatTask = GradleUtil.addTask(
			project, START_TESTABLE_TOMCAT_TASK_NAME,
			StartTestableTomcatTask.class);

		startTestableTomcatTask.dependsOn(setupTestableTomcatTask);

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				StartTestableTomcatTask startTestableTomcatTask =
					(StartTestableTomcatTask)task;

				File binDir = startTestableTomcatTask.getBinDir();

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
					if (_logger.isDebugEnabled()) {
						_logger.debug(
							"Application server " + binDir +
								" is already started");
					}

					Project project = startTestableTomcatTask.getProject();

					Gradle gradle = project.getGradle();

					StartParameter startParameter = gradle.getStartParameter();

					if (startParameter.isParallelProjectExecutionEnabled()) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(
								"Waiting for application server " +
									binDir + " to be reachable");
						}

						startTestableTomcatTask.waitForAppServer();
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

		startTestableTomcatTask.setLiferayHome(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return testIntegrationTomcatExtension.getLiferayHome();
				}

			});

		configureBaseAppServerTask(
			startTestableTomcatTask, testIntegrationTomcatExtension);

		return startTestableTomcatTask;
	}

	protected StopAppServerTask addTaskStopTestableTomcat(
		Project project, Test testIntegrationTask,
		TestIntegrationTomcatExtension testIntegrationTomcatExtension) {

		final StopAppServerTask stopTestableTomcatTask = GradleUtil.addTask(
			project, STOP_TESTABLE_TOMCAT_TASK_NAME, StopAppServerTask.class);

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				StopAppServerTask stopAppServerTask = (StopAppServerTask)task;

				File binDir = stopAppServerTask.getBinDir();

				_startedAppServersReentrantLock.lock();

				try {
					if (!_startedAppServerBinDirs.contains(binDir)) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(
								"Application server " + binDir +
									" is already stopped");
						}

						throw new StopExecutionException();
					}

					int originalCounter = _updateStartedAppServerStopCounters(
						binDir, false);

					if (originalCounter > 1) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(
								"Application server " + binDir +
									" cannot be stopped now, still " +
										(originalCounter - 1) + " to execute");
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

		stopTestableTomcatTask.mustRunAfter(testIntegrationTask);

		configureBaseAppServerTask(
			stopTestableTomcatTask, testIntegrationTomcatExtension);

		Gradle gradle = project.getGradle();

		TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

		Closure<Void> closure = new Closure<Void>(null) {

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

	protected void configureBaseAppServerTask(
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

	protected void configureJmxRemotePortSpec(
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

	protected void configureManagerSpec(
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

	protected void configureTaskTestIntegration(
		Test test, final SourceSet testIntegrationSourceSet,
		final TestIntegrationTomcatExtension testIntegrationTomcatExtension,
		final StartTestableTomcatTask startTestableTomcatTask) {

		Closure<Task> closure = new Closure<Task>(null) {

			@SuppressWarnings("unused")
			public Task doCall(Test test) {
				FileTree candidateClassFiles = test.getCandidateClassFiles();

				File srcDir = getSrcDir(
					testIntegrationSourceSet.getResources());

				File skipManagedAppServerFile = new File(
					srcDir, _SKIP_MANAGED_APP_SERVER_FILE_NAME);

				if (!candidateClassFiles.isEmpty() &&
					!skipManagedAppServerFile.exists()) {

					return startTestableTomcatTask;
				}

				return null;
			}

		};

		test.dependsOn(closure);

		test.jvmArgs("-Djava.net.preferIPv4Stack=true");
		test.jvmArgs("-Dliferay.mode=test");
		test.jvmArgs("-Duser.timezone=GMT");

		test.systemProperty(
			"app.server.tomcat.dir",
			new Object() {

				@Override
				public String toString() {
					return FileUtil.getAbsolutePath(
						testIntegrationTomcatExtension.getDir());
				}

			});
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private int _updateStartedAppServerStopCounters(
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

	private static final String _SKIP_MANAGED_APP_SERVER_FILE_NAME =
		"skip.managed.app.server";

	private static final Logger _logger = Logging.getLogger(
		TestIntegrationPlugin.class);

	private static final Set<File> _startedAppServerBinDirs = new HashSet<>();
	private static final ReentrantLock _startedAppServersReentrantLock =
		new ReentrantLock();
	private static final Map<File, Integer> _startedAppServerStopCounters =
		new HashMap<>();

}