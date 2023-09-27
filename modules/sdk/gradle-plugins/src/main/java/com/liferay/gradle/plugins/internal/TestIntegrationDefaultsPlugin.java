/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.TomcatAppServer;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationTomcatExtension;
import com.liferay.gradle.plugins.test.integration.task.SetUpTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.task.StartTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.task.StopAppServerTask;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationDefaultsPlugin
	extends BaseDefaultsPlugin<TestIntegrationPlugin> {

	public static final Plugin<Project> INSTANCE =
		new TestIntegrationDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, TestIntegrationPlugin testIntegrationPlugin) {

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		LiferayExtension liferayExtension = extensionContainer.getByType(
			LiferayExtension.class);
		TestIntegrationTomcatExtension testIntegrationTomcatExtension =
			extensionContainer.getByType(TestIntegrationTomcatExtension.class);

		TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		_configureExtensionTestIntegrationTomcat(
			liferayExtension, testIntegrationTomcatExtension, tomcatAppServer);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration testModulesConfiguration =
			configurationContainer.getByName(
				TestIntegrationPlugin.TEST_MODULES_CONFIGURATION_NAME);

		_configureConfigurationTestModules(project, testModulesConfiguration);

		// Tasks

		TaskProvider<Task> copyTestModulesTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.COPY_TEST_MODULES_TASK_NAME);
		TaskProvider<SetUpTestableTomcatTask> setUpTestableTomcatTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.SET_UP_TESTABLE_TOMCAT_TASK_NAME,
				SetUpTestableTomcatTask.class);
		TaskProvider<StartTestableTomcatTask> startTestableTomcatTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.START_TESTABLE_TOMCAT_TASK_NAME,
				StartTestableTomcatTask.class);
		TaskProvider<StopAppServerTask> stopTestableTomcatTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.STOP_TESTABLE_TOMCAT_TASK_NAME,
				StopAppServerTask.class);

		_configureTaskCopyTestModulesProvider(copyTestModulesTaskProvider);
		_configureTaskSetUpTestableTomcatProvider(
			project, setUpTestableTomcatTaskProvider, tomcatAppServer);
		_configureTaskStartTestableTomcatProvider(
			project, startTestableTomcatTaskProvider, tomcatAppServer);
		_configureTaskStopTestableTomcatProvider(
			stopTestableTomcatTaskProvider, tomcatAppServer);
	}

	@Override
	protected Class<TestIntegrationPlugin> getPluginClass() {
		return TestIntegrationPlugin.class;
	}

	private TestIntegrationDefaultsPlugin() {
	}

	private void _configureConfigurationTestModules(
		final Project project, Configuration testModulesConfiguration) {

		testModulesConfiguration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					String version = PortalTools.getPortalVersion(project);

					if (PortalTools.PORTAL_VERSION_7_0_X.equals(version) ||
						PortalTools.PORTAL_VERSION_7_1_X.equals(version) ||
						PortalTools.PORTAL_VERSION_7_2_X.equals(version)) {

						GradleUtil.addDependency(
							project,
							TestIntegrationPlugin.
								TEST_MODULES_CONFIGURATION_NAME,
							"com.liferay.portal",
							"com.liferay.portal.test.integration", "3.0.0");
					}
				}

			});
	}

	private void _configureExtensionTestIntegrationTomcat(
		final LiferayExtension liferayExtension,
		TestIntegrationTomcatExtension testIntegrationTomcatExtension,
		final TomcatAppServer tomcatAppServer) {

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

	private void _configureTaskCopyTestModulesProvider(
		TaskProvider<Task> copyTestModulesTaskProvider) {

		copyTestModulesTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task copyTestModulesTask) {
					GradleUtil.setProperty(
						copyTestModulesTask,
						LiferayOSGiPlugin.AUTO_CLEAN_PROPERTY_NAME, false);
				}

			});
	}

	private void _configureTaskSetUpTestableTomcatProvider(
		final Project project,
		TaskProvider<SetUpTestableTomcatTask> setUpTestableTomcatTaskProvider,
		final TomcatAppServer tomcatAppServer) {

		setUpTestableTomcatTaskProvider.configure(
			new Action<SetUpTestableTomcatTask>() {

				@Override
				public void execute(
					SetUpTestableTomcatTask setUpTestableTomcatTask) {

					setUpTestableTomcatTask.setJaCoCoAgentConfiguration(
						GradleUtil.getProperty(
							project, "jacoco.agent.configuration",
							(String)null));
					setUpTestableTomcatTask.setJaCoCoAgentFile(
						GradleUtil.getProperty(
							project, "jacoco.agent.jar", (String)null));
					setUpTestableTomcatTask.setAspectJAgent(
						GradleUtil.getProperty(
							project, "aspectj.agent", (String)null));
					setUpTestableTomcatTask.setAspectJConfiguration(
						GradleUtil.getProperty(
							project, "aspectj.configuration", (String)null));

					setUpTestableTomcatTask.setZipUrl(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return tomcatAppServer.getZipUrl();
							}

						});
				}

			});
	}

	private void _configureTaskStartTestableTomcatProvider(
		final Project project,
		TaskProvider<StartTestableTomcatTask> startTestableTomcatTaskProvider,
		final TomcatAppServer tomcatAppServer) {

		startTestableTomcatTaskProvider.configure(
			new Action<StartTestableTomcatTask>() {

				@Override
				public void execute(
					StartTestableTomcatTask startTestableTomcatTask) {

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
								String argLine = System.getProperty(
									"app.server.start.executable.arg.line");

								if (Validator.isNotNull(argLine)) {
									return Arrays.asList(argLine.split(" "));
								}

								return tomcatAppServer.getStartExecutableArgs();
							}

						});
				}

			});
	}

	private void _configureTaskStopTestableTomcatProvider(
		TaskProvider<StopAppServerTask> stopTestableTomcatTaskProvider,
		final TomcatAppServer tomcatAppServer) {

		stopTestableTomcatTaskProvider.configure(
			new Action<StopAppServerTask>() {

				@Override
				public void execute(StopAppServerTask stopAppServerTask) {
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

			});
	}

}