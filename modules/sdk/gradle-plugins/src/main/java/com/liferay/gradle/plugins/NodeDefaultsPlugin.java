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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.ExecutePackageManagerTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodeDefaultsPlugin extends BaseDefaultsPlugin<NodePlugin> {

	@Override
	protected void applyPluginDefaults(Project project, NodePlugin nodePlugin) {

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		NodeExtension nodeExtension = extensionContainer.getByType(
			NodeExtension.class);

		_configureExtensionNode(project, nodeExtension);

		// Containers

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecutePackageManagerTask.class,
			new Action<ExecutePackageManagerTask>() {

				@Override
				public void execute(
					ExecutePackageManagerTask executePackageManagerTask) {

					_configureTaskExecutePackageManager(
						executePackageManagerTask);
				}

			});

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					_configureTaskNpmInstall(npmInstallTask);
				}

			});

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

	@Override
	protected Class<NodePlugin> getPluginClass() {
		return NodePlugin.class;
	}

	private void _configureExtensionNode(
		Project project, NodeExtension nodeExtension) {

		nodeExtension.setGlobal(true);
		nodeExtension.setNodeVersion(_NODE_VERSION);
		nodeExtension.setNpmVersion(_NPM_VERSION);

		String npmArgs = GradleUtil.getProperty(
			project, "nodejs.npm.args", (String)null);

		if (Validator.isNotNull(npmArgs)) {
			nodeExtension.npmArgs((Object[])npmArgs.split("\\s+"));
		}
	}

	private void _configureTaskExecutePackageManager(
		ExecutePackageManagerTask executePackageManagerTask) {

		String nodeEnv = GradleUtil.getProperty(
			executePackageManagerTask.getProject(), "nodejs.node.env",
			(String)null);

		if (Validator.isNotNull(nodeEnv)) {
			executePackageManagerTask.environment("NODE_ENV", nodeEnv);
		}

		String registry = GradleUtil.getProperty(
			executePackageManagerTask.getProject(), "nodejs.npm.registry",
			(String)null);

		if (Validator.isNotNull(registry)) {
			executePackageManagerTask.setRegistry(registry);
		}
	}

	private void _configureTaskNpmInstall(NpmInstallTask npmInstallTask) {
		String sassBinarySite = GradleUtil.getProperty(
			npmInstallTask.getProject(), "nodejs.npm.sass.binary.site",
			(String)null);

		if (Validator.isNotNull(sassBinarySite)) {
			boolean sassBinarySiteArg = false;

			for (Object object : npmInstallTask.getArgs()) {
				String arg = GradleUtil.toString(object);

				if (arg.startsWith(_SASS_BINARY_SITE_ARG)) {
					sassBinarySiteArg = true;
				}
			}

			if (!sassBinarySiteArg) {
				npmInstallTask.args(_SASS_BINARY_SITE_ARG + sassBinarySite);
			}
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

	private static final String _NODE_VERSION = "10.15.3";

	private static final String _NPM_VERSION = "6.4.1";

	private static final String _SASS_BINARY_SITE_ARG = "--sass-binary-site=";

}