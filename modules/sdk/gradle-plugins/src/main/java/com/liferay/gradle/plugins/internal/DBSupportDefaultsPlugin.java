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
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class DBSupportDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<DBSupportPlugin> {

	public static final Plugin<Project> INSTANCE =
		new DBSupportDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, DBSupportPlugin plugin) {

		addPortalToolDependencies(project);

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

		baseDBSupportTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					BaseDBSupportTask baseDBSupportTask =
						(BaseDBSupportTask)task;

					File propertiesFile = baseDBSupportTask.getPropertiesFile();
					String url = baseDBSupportTask.getUrl();

					if ((propertiesFile != null) || Validator.isNotNull(url)) {
						return;
					}

					File jarFile = new File(
						liferayExtension.getAppServerPortalDir(),
						"WEB-INF/lib/portal-impl.jar");

					if (!jarFile.exists()) {
						return;
					}

					Properties defaultProperties = null;

					try {
						defaultProperties = FileUtil.readPropertiesFromZipFile(
							jarFile, "portal.properties");
					}
					catch (IOException ioException) {
						Logger logger = baseDBSupportTask.getLogger();

						if (logger.isWarnEnabled()) {
							logger.warn(
								"Unable to read portal.properties file from {}",
								jarFile);
						}
					}

					Properties properties = new Properties(defaultProperties);

					propertiesFile = _getPortalPropertiesFile();

					if (propertiesFile != null) {
						try (FileInputStream fileInputStream =
								new FileInputStream(propertiesFile)) {

							properties.load(fileInputStream);
						}
						catch (IOException ioException) {
							Logger logger = baseDBSupportTask.getLogger();

							if (logger.isWarnEnabled()) {
								logger.warn(
									"Unable to read properties from {}",
									propertiesFile);
							}
						}
					}

					url = properties.getProperty("jdbc.default.url");

					url = url.replace(
						"${liferay.home}",
						FileUtil.getAbsolutePath(
							liferayExtension.getLiferayHome()));

					baseDBSupportTask.setUrl(url);

					if (baseDBSupportTask.getUserName() == null) {
						baseDBSupportTask.setUserName(
							properties.getProperty("jdbc.default.username"));
					}

					if (baseDBSupportTask.getPassword() == null) {
						baseDBSupportTask.setPassword(
							properties.getProperty("jdbc.default.password"));
					}
				}

				private File _getPortalPropertiesFile() {
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