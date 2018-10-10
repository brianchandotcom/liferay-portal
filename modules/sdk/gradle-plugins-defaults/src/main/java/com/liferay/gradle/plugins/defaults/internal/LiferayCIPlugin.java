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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.cache.CachePlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayCIPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE = new LiferayCIPlugin();

	@Override
	public void apply(final Project project) {
		_configureTasksDownloadNode(project);
		_configureTasksExecuteNode(project);
		_configureTasksExecuteNpm(project);
		_configureTasksNpmInstall(project);

		GradleUtil.withPlugin(
			project, TestIntegrationPlugin.class,
			new Action<TestIntegrationPlugin>() {

				@Override
				public void execute(
					TestIntegrationPlugin testIntegrationPlugin) {

					_configureTaskTestIntegration(project);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTasksNpmInstallArgs(project);
				}

			});
	}

	private LiferayCIPlugin() {
	}

	private void _configureTaskDownloadNode(DownloadNodeTask downloadNodeTask) {
		downloadNodeTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					if (GradleUtil.hasPlugin(
							task.getProject(), CachePlugin.class)) {

						throw new GradleException(
							"Unable to use Node.js on CI, please configure " +
								"com.liferay.cache or update the cache");
					}
				}

			});
	}

	private void _configureTaskExecuteNode(ExecuteNodeTask executeNodeTask) {
		executeNodeTask.setNpmInstallRetries(_NPM_INSTALL_RETRIES);
	}

	private void _configureTaskExecuteNodeArgs(
		ExecuteNodeTask executeNodeTask, Map<String, String> newArgs) {

		List<Object> args = executeNodeTask.getArgs();

		for (Map.Entry<String, String> entry : newArgs.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			boolean changed = false;

			for (int i = 0; i < args.size(); i++) {
				String arg = GradleUtil.toString(args.get(i));

				if (arg.startsWith(key)) {
					changed = true;

					args.set(i, key + value);

					break;
				}
			}

			if (!changed) {
				args.add(key + value);
			}
		}

		executeNodeTask.setArgs(args);
	}

	private void _configureTaskExecuteNpm(
		ExecuteNpmTask executeNpmTask, String registry) {

		if (Validator.isNotNull(registry)) {
			executeNpmTask.setRegistry(registry);
		}

		executeNpmTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					String[] fileNames =
						{"bnd.bnd", "package.json", "package-lock.json"};

					for (String fileName : fileNames) {
						File file = project.file(fileName);

						if (!file.exists()) {
							continue;
						}

						String version = null;

						if (fileName.endsWith(".bnd")) {
							Properties properties = GUtil.loadProperties(file);

							version = properties.getProperty("Bundle-Version");
						}
						else if (fileName.endsWith(".json")) {
							JsonSlurper jsonSlurper = new JsonSlurper();

							Map<String, Object> map =
								(Map<String, Object>)jsonSlurper.parse(file);

							version = (String)map.get("version");
						}

						if (version == null) {
							continue;
						}

						String newVersion = _fixHotfixVersion(version);

						if (version.equals(newVersion)) {
							continue;
						}

						try {
							String content = new String(
								Files.readAllBytes(file.toPath()),
								StandardCharsets.UTF_8);

							String newContent = content.replace(
								version, newVersion);

							Files.write(
								file.toPath(),
								newContent.getBytes(StandardCharsets.UTF_8));
						}
						catch (IOException ioe) {
							throw new UncheckedIOException(ioe);
						}
					}
				}

				private String _fixHotfixVersion(String version) {
					int index = version.indexOf(".hotfix");

					if (index == -1) {
						return version;
					}

					String prefix = version.substring(0, index);
					String suffix = version.substring(index + 7);

					return prefix + "-hotfix" + suffix.replace('-', '.');
				}

			});

		executeNpmTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					String[] fileNames =
						{"bnd.bnd", "package.json", "package-lock.json"};

					for (String fileName : fileNames) {
						File file = project.file(fileName);

						if (!file.exists()) {
							continue;
						}

						String version = null;

						if (fileName.endsWith(".bnd")) {
							Properties properties = GUtil.loadProperties(file);

							version = properties.getProperty("Bundle-Version");
						}
						else if (fileName.endsWith(".json")) {
							JsonSlurper jsonSlurper = new JsonSlurper();

							Map<String, Object> map =
								(Map<String, Object>)jsonSlurper.parse(file);

							version = (String)map.get("version");
						}

						if (version == null) {
							continue;
						}

						String newVersion = _fixHotfixVersion(version);

						if (version.equals(newVersion)) {
							continue;
						}

						try {
							String content = new String(
								Files.readAllBytes(file.toPath()),
								StandardCharsets.UTF_8);

							String newContent = content.replace(
								version, newVersion);

							Files.write(
								file.toPath(),
								newContent.getBytes(StandardCharsets.UTF_8));
						}
						catch (IOException ioe) {
							throw new UncheckedIOException(ioe);
						}
					}
				}

				private String _fixHotfixVersion(String version) {
					int index = version.indexOf("-hotfix");

					if (index == -1) {
						return version;
					}

					String prefix = version.substring(0, index);
					String suffix = version.substring(index + 7);

					return prefix + ".hotfix" + suffix.replace('.', '-');
				}

			});
	}

	private void _configureTaskNpmInstall(NpmInstallTask npmInstallTask) {
		if (Validator.isNull(System.getenv("FIX_PACKS_RELEASE_ENVIRONMENT"))) {
			npmInstallTask.setNodeModulesCacheDir(_NODE_MODULES_CACHE_DIR);
		}

		npmInstallTask.setRemoveShrinkwrappedUrls(Boolean.TRUE);
		npmInstallTask.setUseNpmCI(Boolean.FALSE);
	}

	private void _configureTasksDownloadNode(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			DownloadNodeTask.class,
			new Action<DownloadNodeTask>() {

				@Override
				public void execute(DownloadNodeTask downloadNodeTask) {
					_configureTaskDownloadNode(downloadNodeTask);
				}

			});
	}

	private void _configureTasksExecuteNode(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNodeTask.class,
			new Action<ExecuteNodeTask>() {

				@Override
				public void execute(ExecuteNodeTask executeNodeTask) {
					_configureTaskExecuteNode(executeNodeTask);
				}

			});
	}

	private void _configureTasksExecuteNpm(Project project) {
		final String ciRegistry = GradleUtil.getProperty(
			project, "nodejs.npm.ci.registry", (String)null);

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNpmTask.class,
			new Action<ExecuteNpmTask>() {

				@Override
				public void execute(ExecuteNpmTask executeNpmTask) {
					_configureTaskExecuteNpm(executeNpmTask, ciRegistry);
				}

			});
	}

	private void _configureTasksNpmInstall(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					_configureTaskNpmInstall(npmInstallTask);
				}

			});
	}

	private void _configureTasksNpmInstallArgs(Project project) {
		final String ciSassBinarySite = GradleUtil.getProperty(
			project, "nodejs.npm.ci.sass.binary.site", (String)null);

		if (Validator.isNull(ciSassBinarySite)) {
			return;
		}

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					_configureTaskExecuteNodeArgs(
						npmInstallTask,
						Collections.singletonMap(
							_SASS_BINARY_SITE_ARG, ciSassBinarySite));
				}

			});
	}

	private void _configureTaskTestIntegration(Project project) {
		Task testIntegrationTask = GradleUtil.getTask(
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME);

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				Project project = task.getProject();

				SourceSet sourceSet = GradleUtil.getSourceSet(
					project,
					TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

				Configuration configuration = GradleUtil.getConfiguration(
					project, sourceSet.getCompileConfigurationName());

				DependencySet dependencySet = configuration.getDependencies();

				for (ProjectDependency projectDependency :
						dependencySet.withType(ProjectDependency.class)) {

					Project dependencyProject =
						projectDependency.getDependencyProject();

					File lfrBuildCIFile = dependencyProject.file(
						".lfrbuild-ci");
					File lfrBuildCISkipTestIntegrationCheckFile =
						dependencyProject.file(
							".lfrbuild-ci-skip-test-integration-check");
					File lfrBuildPortalFile = dependencyProject.file(
						".lfrbuild-portal");

					if (lfrBuildCISkipTestIntegrationCheckFile.exists()) {
						if (lfrBuildCIFile.exists() ||
							lfrBuildPortalFile.exists()) {

							throw new GradleException(
								"Please delete marker file " +
									lfrBuildCISkipTestIntegrationCheckFile);
						}
					}
					else if (!lfrBuildCIFile.exists() &&
							 !lfrBuildPortalFile.exists()) {

						throw new GradleException(
							"Please create marker file " + lfrBuildPortalFile);
					}
				}
			}

		};

		testIntegrationTask.doFirst(action);
	}

	private static final File _NODE_MODULES_CACHE_DIR = new File(
		System.getProperty("user.home"), ".liferay/node-modules-cache");

	private static final int _NPM_INSTALL_RETRIES = 3;

	private static final String _SASS_BINARY_SITE_ARG = "--sass-binary-site=";

}