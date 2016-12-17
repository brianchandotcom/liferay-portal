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

import com.liferay.gradle.plugins.BasePortalToolDefaultsPlugin;
import com.liferay.gradle.plugins.db.support.DBSupportPlugin;
import com.liferay.gradle.plugins.db.support.tasks.BaseDBSupportTask;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class DBSupportDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<DBSupportPlugin> {

	public static final Plugin<Project> INSTANCE =
		new DBSupportDefaultsPlugin();

	@Override
	protected void configureDefaults(Project project, DBSupportPlugin plugin) {
		super.configureDefaults(project, plugin);

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		_configureConfigurationDBSupport(project, liferayExtension);
		_configureTasksBaseDBSupport(project, liferayExtension);
	}

	@Override
	protected Class<DBSupportPlugin> getPluginClass() {
		return DBSupportPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return DBSupportPlugin.TOOL_CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private DBSupportDefaultsPlugin() {
	}

	private void _addDependenciesDBSupport(
		Project project, LiferayExtension liferayExtension) {

		GradleUtil.addDependency(
			project, DBSupportPlugin.CONFIGURATION_NAME,
			FileUtil.getJarsFileTree(
				project, liferayExtension.getAppServerLibGlobalDir()));
	}

	private void _configureConfigurationDBSupport(
		final Project project, final LiferayExtension liferayExtension) {

		Configuration configuration = GradleUtil.getConfiguration(
			project, DBSupportPlugin.CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesDBSupport(project, liferayExtension);
				}

			});
	}

	private void _configureTaskBaseDBSupport(
		BaseDBSupportTask baseDBSupportTask,
		final LiferayExtension liferayExtension) {

		baseDBSupportTask.setPropertiesFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File liferayHome = liferayExtension.getLiferayHome();

					for (String fileName : _PORTAL_PROPERTIES_FILE_NAMES) {
						File file = new File(liferayHome, fileName);

						if (file.exists()) {
							return file;
						}
					}

					return null;
				}

			});
	}

	private void _configureTasksBaseDBSupport(
		Project project, final LiferayExtension liferayExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaseDBSupportTask.class,
			new Action<BaseDBSupportTask>() {

				@Override
				public void execute(BaseDBSupportTask baseDBSupportTask) {
					_configureTaskBaseDBSupport(
						baseDBSupportTask, liferayExtension);
				}

			});
	}

	private static final String[] _PORTAL_PROPERTIES_FILE_NAMES = {
		"portal-setup-wizard.properties", "portal-ext.properties",
		"portal-bundle.properties"
	};

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.db.support";

}