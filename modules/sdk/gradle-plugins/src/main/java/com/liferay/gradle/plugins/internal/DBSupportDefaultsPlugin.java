/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.db.support.DBSupportPlugin;
import com.liferay.gradle.plugins.db.support.task.BaseDBSupportTask;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.util.PortalTools;
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
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class DBSupportDefaultsPlugin
	extends BaseDefaultsPlugin<DBSupportPlugin> {

	public static final Plugin<Project> INSTANCE =
		new DBSupportDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, DBSupportPlugin plugin) {

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		LiferayExtension liferayExtension = extensionContainer.getByType(
			LiferayExtension.class);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration dbSupportConfiguration = configurationContainer.getByName(
			DBSupportPlugin.CONFIGURATION_NAME);

		_configureConfigurationDBSupport(
			project, liferayExtension, dbSupportConfiguration);

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, DBSupportPlugin.TOOL_CONFIGURATION_NAME, PortalTools.GROUP,
			_PORTAL_TOOL_NAME);

		// Containers

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

	@Override
	protected Class<DBSupportPlugin> getPluginClass() {
		return DBSupportPlugin.class;
	}

	private DBSupportDefaultsPlugin() {
	}

	private void _configureConfigurationDBSupport(
		final Project project, final LiferayExtension liferayExtension,
		Configuration dbSupportConfiguration) {

		dbSupportConfiguration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					GradleUtil.addDependency(
						project, DBSupportPlugin.CONFIGURATION_NAME,
						FileUtil.getJarsFileTree(
							project,
							liferayExtension.getAppServerLibGlobalDir()));
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

	private static final String[] _PORTAL_PROPERTIES_FILE_NAMES = {
		"portal-setup-wizard.properties", "portal-ext.properties",
		"portal-bundle.properties"
	};

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.db.support";

}