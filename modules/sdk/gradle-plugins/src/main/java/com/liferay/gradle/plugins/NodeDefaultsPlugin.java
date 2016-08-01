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

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodeDefaultsPlugin extends BaseDefaultsPlugin<NodePlugin> {

	@Override
	protected void configureDefaults(Project project, NodePlugin nodePlugin) {
		configureTasksExecuteNpm(project);
		configureTasksNpmInstall(project);
		configureTasksPublishNodeModule(project);
	}

	protected void configureTaskExecuteNpm(ExecuteNpmTask executeNpmTask) {
		String registry = GradleUtil.getProperty(
			executeNpmTask.getProject(), "nodejs.npm.registry", (String)null);

		if (Validator.isNotNull(registry)) {
			executeNpmTask.setRegistry(registry);
		}
	}

	protected void configureTaskNpmInstall(NpmInstallTask npmInstallTask) {
		String removeShrinkwrappedUrls = GradleUtil.getProperty(
			npmInstallTask.getProject(), "nodejs.npm.remove.shrinkwrapped.urls",
			(String)null);

		if (Validator.isNotNull(removeShrinkwrappedUrls)) {
			npmInstallTask.setRemoveShrinkwrappedUrls(
				Boolean.parseBoolean(removeShrinkwrappedUrls));
		}
	}

	protected void configureTaskPublishNodeModule(
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

	protected void configureTasksExecuteNpm(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNpmTask.class,
			new Action<ExecuteNpmTask>() {

				@Override
				public void execute(ExecuteNpmTask executeNpmTask) {
					configureTaskExecuteNpm(executeNpmTask);
				}

			});
	}

	protected void configureTasksNpmInstall(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					configureTaskNpmInstall(npmInstallTask);
				}

			});
	}

	protected void configureTasksPublishNodeModule(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					configureTaskPublishNodeModule(publishNodeModuleTask);
				}

			});
	}

	@Override
	protected Class<NodePlugin> getPluginClass() {
		return NodePlugin.class;
	}

}