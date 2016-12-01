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
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodeDefaultsPlugin extends BaseDefaultsPlugin<NodePlugin> {

	public static final Plugin<Project> INSTANCE = new NodeDefaultsPlugin();

	@Override
	protected void configureDefaults(Project project, NodePlugin nodePlugin) {
		_configureNode(project);
		_configureTasksExecuteNpm(project);
		_configureTasksNpmInstall(project);
		_configureTasksPublishNodeModule(project);
	}

	@Override
	protected Class<NodePlugin> getPluginClass() {
		return NodePlugin.class;
	}

	private NodeDefaultsPlugin() {
	}

	private void _configureNode(Project project) {
		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

		nodeExtension.setNodeVersion(_NODE_VERSION);
	}

	private void _configureTaskExecuteNpm(ExecuteNpmTask executeNpmTask) {
		String registry = GradleUtil.getProperty(
			executeNpmTask.getProject(), "nodejs.npm.registry", (String)null);

		if (Validator.isNotNull(registry)) {
			executeNpmTask.setRegistry(registry);
		}
	}

	private void _configureTaskNpmInstall(NpmInstallTask npmInstallTask) {
		Project project = npmInstallTask.getProject();

		String sassBinarySite = GradleUtil.getProperty(
			project, "nodejs.npm.sass.binary.site", (String)null);

		if (Validator.isNotNull(sassBinarySite)) {
			_setTaskExecuteNodeArgDefault(
				npmInstallTask, _SASS_BINARY_SITE_ARG, sassBinarySite);
		}
	}

	private void _configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		Project project = publishNodeModuleTask.getProject();

		String author = GradleUtil.getProperty(
			project, "nodejs.npm.module.author", (String)null);

		if (Validator.isNotNull(author)) {
			publishNodeModuleTask.setModuleAuthor(author);
		}

		String bugsUrl = GradleUtil.getProperty(
			project, "nodejs.npm.module.bugs.url", (String)null);

		if (Validator.isNotNull(bugsUrl)) {
			publishNodeModuleTask.setModuleBugsUrl(bugsUrl);
		}

		String license = GradleUtil.getProperty(
			project, "nodejs.npm.module.license", (String)null);

		if (Validator.isNotNull(license)) {
			publishNodeModuleTask.setModuleLicense(license);
		}

		String emailAddress = GradleUtil.getProperty(
			project, "nodejs.npm.email", (String)null);

		if (Validator.isNotNull(emailAddress)) {
			publishNodeModuleTask.setNpmEmailAddress(emailAddress);
		}

		String password = GradleUtil.getProperty(
			project, "nodejs.npm.password", (String)null);

		if (Validator.isNotNull(password)) {
			publishNodeModuleTask.setNpmPassword(password);
		}

		String userName = GradleUtil.getProperty(
			project, "nodejs.npm.user", (String)null);

		if (Validator.isNotNull(userName)) {
			publishNodeModuleTask.setNpmUserName(userName);
		}

		String repository = GradleUtil.getProperty(
			project, "nodejs.npm.module.repository", (String)null);

		if (Validator.isNotNull(repository)) {
			publishNodeModuleTask.setModuleRepository(repository);
		}
	}

	private void _configureTasksExecuteNpm(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNpmTask.class,
			new Action<ExecuteNpmTask>() {

				@Override
				public void execute(ExecuteNpmTask executeNpmTask) {
					_configureTaskExecuteNpm(executeNpmTask);
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

	private void _setTaskExecuteNodeArgDefault(
		ExecuteNodeTask executeNodeTask, String key, String value) {

		for (String arg : executeNodeTask.getArgs()) {
			if (arg.startsWith(key)) {
				return;
			}
		}

		executeNodeTask.args(key + value);
	}

	private static final String _NODE_VERSION = "6.6.0";

	private static final String _SASS_BINARY_SITE_ARG = "--sass-binary-site=";

}