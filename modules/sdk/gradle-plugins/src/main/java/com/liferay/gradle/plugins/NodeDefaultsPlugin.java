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

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.util.Map;
import java.util.concurrent.Callable;

import org.dm.gradle.plugins.bundle.BundleExtension;
import org.dm.gradle.plugins.bundle.BundlePlugin;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodeDefaultsPlugin extends BaseDefaultsPlugin<NodePlugin> {

	@Override
	protected void configureDefaults(
		final Project project, NodePlugin nodePlugin) {

		configureTasksPublishNodeModule(project);

		withPlugin(
			project, BundlePlugin.class,
			new Action<BundlePlugin>() {

				@Override
				public void execute(BundlePlugin bundlePlugin) {
					configureTasksPublishNodeModuleForBundlePlugin(project);
				}

			});
	}

	protected void configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		String author = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.author",
			(String)null);

		if (Validator.isNotNull(author)) {
			publishNodeModuleTask.setModuleAuthor(author);
		}

		String bugsUrl = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.bugs.url",
			(String)null);

		if (Validator.isNotNull(bugsUrl)) {
			publishNodeModuleTask.setModuleBugsUrl(bugsUrl);
		}

		String license = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.license",
			(String)null);

		if (Validator.isNotNull(license)) {
			publishNodeModuleTask.setModuleLicense(license);
		}

		String emailAddress = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.email",
			(String)null);

		if (Validator.isNotNull(emailAddress)) {
			publishNodeModuleTask.setNpmEmailAddress(emailAddress);
		}

		String password = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.password",
			(String)null);

		if (Validator.isNotNull(password)) {
			publishNodeModuleTask.setNpmPassword(password);
		}

		String userName = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.user",
			(String)null);

		if (Validator.isNotNull(userName)) {
			publishNodeModuleTask.setNpmUserName(userName);
		}

		String repository = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.repository",
			(String)null);

		if (Validator.isNotNull(repository)) {
			publishNodeModuleTask.setModuleRepository(repository);
		}
	}

	protected void configureTaskPublishNodeModuleForBundlePlugin(
		final PublishNodeModuleTask publishNodeModuleTask) {

		publishNodeModuleTask.setModuleDescription(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return getBundleInstruction(
						publishNodeModuleTask.getProject(),
						Constants.BUNDLE_NAME);
				}

			});

		publishNodeModuleTask.setModuleName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String bundleSymbolicName = getBundleInstruction(
						publishNodeModuleTask.getProject(),
						Constants.BUNDLE_SYMBOLICNAME);

					int pos = bundleSymbolicName.indexOf('.');

					String moduleName = bundleSymbolicName.substring(pos + 1);

					return moduleName.replace('.', '-');
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

	protected void configureTasksPublishNodeModuleForBundlePlugin(
		Project project) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					configureTaskPublishNodeModuleForBundlePlugin(
						publishNodeModuleTask);
				}

			});
	}

	protected String getBundleInstruction(Project project, String key) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		return bundleInstructions.get(key);
	}

	protected Map<String, String> getBundleInstructions(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		return (Map<String, String>)bundleExtension.getInstructions();
	}

	@Override
	protected Class<NodePlugin> getPluginClass() {
		return NodePlugin.class;
	}

}