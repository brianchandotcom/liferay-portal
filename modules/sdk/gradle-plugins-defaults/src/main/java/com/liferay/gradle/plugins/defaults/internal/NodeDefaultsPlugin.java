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

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.ExecutePackageManagerTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.PackageRunTestTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.concurrent.Callable;

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
		String portalVersion = PortalTools.getPortalVersion(project);

		_configureNode(project, portalVersion);
		_configureTaskNpmInstall(project, portalVersion);

		_configureTaskExecutePackageManager(project);
		_configureTaskPackageRunTest(project);
		_configureTasksPublishNodeModule(project);
	}

	@Override
	protected Class<NodePlugin> getPluginClass() {
		return NodePlugin.class;
	}

	private NodeDefaultsPlugin() {
	}

	private void _configureNode(Project project, String portalVersion) {
		if (PortalTools.PORTAL_VERSION_7_0_X.equals(portalVersion)) {
			NodeExtension nodeExtension = GradleUtil.getExtension(
				project, NodeExtension.class);

			nodeExtension.setGlobal(false);
			nodeExtension.setNodeVersion("6.6.0");
		}
		else if (PortalTools.PORTAL_VERSION_7_1_X.equals(portalVersion)) {
			NodeExtension nodeExtension = GradleUtil.getExtension(
				project, NodeExtension.class);

			nodeExtension.setNodeVersion("8.15.0");
			nodeExtension.setNpmVersion("6.4.1");
		}
	}

	private void _configureTaskExecutePackageManager(Project project) {
		TaskContainer taskContainer = project.getTasks();

		ExecutePackageManagerTask executePackageManagerTask =
			(ExecutePackageManagerTask)taskContainer.findByName(
				NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);

		if (executePackageManagerTask != null) {
			executePackageManagerTask.environment(
				"LIFERAY_NPM_BUNDLER_NO_TRACKING", "1");
		}
	}

	private void _configureTaskNpmInstall(
		Project project, String portalVersion) {

		NpmInstallTask npmInstallTask = (NpmInstallTask)GradleUtil.getTask(
			project, NodePlugin.NPM_INSTALL_TASK_NAME);

		npmInstallTask.setNodeModulesDigestFile(
			new File(npmInstallTask.getNodeModulesDir(), ".digest"));

		if (!PortalTools.PORTAL_VERSION_7_0_X.equals(portalVersion)) {
			npmInstallTask.setUseNpmCI(Boolean.TRUE);
		}
	}

	private void _configureTaskPackageRunTest(Project project) {
		TaskContainer taskContainer = project.getTasks();

		PackageRunTestTask packageRunTestTask =
			(PackageRunTestTask)taskContainer.findByName(
				NodePlugin.PACKAGE_RUN_TEST_TASK_NAME);

		if (packageRunTestTask == null) {
			return;
		}

		String ignoreFailures = GradleUtil.getTaskPrefixedProperty(
			packageRunTestTask, "ignore.failures");

		if (Validator.isNotNull(ignoreFailures)) {
			packageRunTestTask.setIgnoreFailures(
				Boolean.parseBoolean(ignoreFailures));
		}
	}

	private void _configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		final Project project = publishNodeModuleTask.getProject();

		publishNodeModuleTask.doFirst(
			MavenDefaultsPlugin.failReleaseOnWrongBranchAction);

		if (GradlePluginsDefaultsUtil.isPrivateProject(project)) {
			publishNodeModuleTask.setEnabled(false);
		}

		publishNodeModuleTask.setModuleAuthor(
			"Nathan Cavanaugh <nathan.cavanaugh@liferay.com> " +
				"(https://github.com/natecavanaugh)");
		publishNodeModuleTask.setModuleBugsUrl("https://issues.liferay.com/");
		publishNodeModuleTask.setModuleLicense("LGPL");
		publishNodeModuleTask.setModuleMain("package.json");
		publishNodeModuleTask.setModuleRepository("liferay/liferay-portal");

		publishNodeModuleTask.setModuleVersion(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String version = String.valueOf(project.getVersion());

					if (version.endsWith(
							GradlePluginsDefaultsUtil.
								SNAPSHOT_VERSION_SUFFIX)) {

						version = version.substring(
							0,
							version.length() -
								GradlePluginsDefaultsUtil.
									SNAPSHOT_VERSION_SUFFIX.length());

						version += "-alpha." + System.currentTimeMillis();
					}

					return version;
				}

			});

		publishNodeModuleTask.setOverriddenPackageJsonKeys("version");
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

}