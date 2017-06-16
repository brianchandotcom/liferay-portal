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

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.TomcatAppServer;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationTomcatExtension;
import com.liferay.gradle.plugins.test.integration.tasks.SetUpTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StartTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StopAppServerTask;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationDefaultsPlugin
	extends BaseDefaultsPlugin<TestIntegrationPlugin> {

	public static final Plugin<Project> INSTANCE =
		new TestIntegrationDefaultsPlugin();

	@Override
	protected void configureDefaults(
		Project project, TestIntegrationPlugin testIntegrationPlugin) {

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		_configureTestIntegrationTomcat(
			project, liferayExtension, tomcatAppServer);

		_configureTaskCopyTestModules(project);
		_configureTaskSetUpTestableTomcat(project, tomcatAppServer);
		_configureTaskStartTestableTomcat(project, tomcatAppServer);
		_configureTaskStopTestableTomcat(project, tomcatAppServer);
	}

	@Override
	protected Class<TestIntegrationPlugin> getPluginClass() {
		return TestIntegrationPlugin.class;
	}

	private TestIntegrationDefaultsPlugin() {
	}

	private void _configureTaskCopyTestModules(Project project) {
		Task copyTestModulesTask = GradleUtil.getTask(
			project, TestIntegrationPlugin.COPY_TEST_MODULES_TASK_NAME);

		GradleUtil.setProperty(
			copyTestModulesTask, LiferayOSGiPlugin.AUTO_CLEAN_PROPERTY_NAME,
			false);
	}

	private void _configureTaskSetUpTestableTomcat(
		Project project, final TomcatAppServer tomcatAppServer) {

		SetUpTestableTomcatTask setUpTestableTomcatTask =
			(SetUpTestableTomcatTask)GradleUtil.getTask(
				project,
				TestIntegrationPlugin.SET_UP_TESTABLE_TOMCAT_TASK_NAME);

		String setenvGCNew = GradleUtil.getProperty(
			project, "app.server.tomcat.setenv.gc.new", (String)null);
		String setenvGCOld = GradleUtil.getProperty(
			project, "app.server.tomcat.setenv.gc.old", (String)null);

		if (Validator.isNotNull(setenvGCNew) &&
			Validator.isNotNull(setenvGCOld)) {

			setUpTestableTomcatTask.catalinaOptsReplacement(
				setenvGCOld, setenvGCNew);
		}

		setUpTestableTomcatTask.setZipUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getZipUrl();
				}

			});
	}

	private void _configureTaskStartTestableTomcat(
		Project project, final TomcatAppServer tomcatAppServer) {

		StartTestableTomcatTask startTestableTomcatTask =
			(StartTestableTomcatTask)GradleUtil.getTask(
				project, TestIntegrationPlugin.START_TESTABLE_TOMCAT_TASK_NAME);

		Object checkTimeout = GradleUtil.getProperty(
			project, "timeout.app.server.wait");

		if (checkTimeout != null) {
			startTestableTomcatTask.setCheckTimeout(
				GradleUtil.toInteger(checkTimeout) * 1000);
		}

		startTestableTomcatTask.setExecutable(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getStartExecutable();
				}

			});

		startTestableTomcatTask.setExecutableArgs(
			new Callable<List<String>>() {

				@Override
				public List<String> call() throws Exception {
					return tomcatAppServer.getStartExecutableArgs();
				}

			});
	}

	private void _configureTaskStopTestableTomcat(
		Project project, final TomcatAppServer tomcatAppServer) {

		StopAppServerTask stopAppServerTask =
			(StopAppServerTask)GradleUtil.getTask(
				project, TestIntegrationPlugin.STOP_TESTABLE_TOMCAT_TASK_NAME);

		stopAppServerTask.setExecutable(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getStopExecutable();
				}

			});

		stopAppServerTask.setExecutableArgs(
			new Callable<List<String>>() {

				@Override
				public List<String> call() throws Exception {
					return tomcatAppServer.getStopExecutableArgs();
				}

			});
	}

	private void _configureTestIntegrationTomcat(
		Project project, final LiferayExtension liferayExtension,
		final TomcatAppServer tomcatAppServer) {

		TestIntegrationTomcatExtension testIntegrationTomcatExtension =
			GradleUtil.getExtension(
				project, TestIntegrationTomcatExtension.class);

		testIntegrationTomcatExtension.setCheckPath(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getCheckPath();
				}

			});

		testIntegrationTomcatExtension.setPortNumber(
			new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return tomcatAppServer.getPortNumber();
				}

			});

		testIntegrationTomcatExtension.setDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return tomcatAppServer.getDir();
				}

			});

		testIntegrationTomcatExtension.setJmxRemotePort(
			new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return liferayExtension.getJmxRemotePort();
				}

			});

		testIntegrationTomcatExtension.setLiferayHome(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getLiferayHome();
				}

			});

		testIntegrationTomcatExtension.setManagerPassword(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getManagerPassword();
				}

			});

		testIntegrationTomcatExtension.setManagerUserName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getManagerUserName();
				}

			});
	}

}