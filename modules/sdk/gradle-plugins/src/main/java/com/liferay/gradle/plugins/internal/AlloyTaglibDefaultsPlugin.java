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
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.alloy.taglib.AlloyTaglibPlugin;
import com.liferay.gradle.plugins.alloy.taglib.BuildTaglibsTask;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.util.PortalTools;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class AlloyTaglibDefaultsPlugin
	extends BaseDefaultsPlugin<AlloyTaglibPlugin> {

	public static final Plugin<Project> INSTANCE =
		new AlloyTaglibDefaultsPlugin();

	public static final String PORTAL_TOOL_NAME = "alloy-taglib";

	@Override
	protected void applyPluginDefaults(
		final Project project, AlloyTaglibPlugin alloyTaglibPlugin) {

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		final Configuration portalToolConfiguration =
			configurationContainer.create(_PORTAL_TOOL_CONFIGURATION_NAME);

		Configuration runtimeConfiguration = configurationContainer.getByName(
			JavaPlugin.RUNTIME_CONFIGURATION_NAME);

		_configureConfigurationPortalTool(
			portalToolConfiguration, runtimeConfiguration);

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, _PORTAL_TOOL_CONFIGURATION_NAME, _PORTAL_TOOL_GROUP,
			PORTAL_TOOL_NAME);

		GradleUtil.addDependency(
			project, _PORTAL_TOOL_CONFIGURATION_NAME, "org.freemarker",
			"freemarker", "2.3.23");

		// Other

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildTaglibsTask.class,
			new Action<BuildTaglibsTask>() {

				@Override
				public void execute(BuildTaglibsTask buildTaglibsTask) {
					_configureTaskBuildTaglibs(
						portalToolConfiguration, buildTaglibsTask);
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			LiferayBasePlugin.class,
			new Action<LiferayBasePlugin>() {

				@Override
				public void execute(LiferayBasePlugin liferayBasePlugin) {
					_configurePluginLiferayBase(
						project, portalToolConfiguration);
				}

			});
	}

	@Override
	protected Class<AlloyTaglibPlugin> getPluginClass() {
		return AlloyTaglibPlugin.class;
	}

	private AlloyTaglibDefaultsPlugin() {
	}

	private void _configureConfigurationPortalTool(
		Configuration portalToolConfiguration,
		Configuration runtimeConfiguration) {

		portalToolConfiguration.setDescription(
			"Configures the Alloy Taglib tool for this project.");
		portalToolConfiguration.setVisible(false);

		portalToolConfiguration.extendsFrom(runtimeConfiguration);
	}

	private void _configurePluginLiferayBase(
		Project project, Configuration portalToolConfiguration) {

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration portalConfiguration = configurationContainer.getByName(
			LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

		portalToolConfiguration.extendsFrom(portalConfiguration);
	}

	private void _configureTaskBuildTaglibs(
		Configuration portalToolConfiguration,
		BuildTaglibsTask buildTaglibsTask) {

		buildTaglibsTask.setClasspath(portalToolConfiguration);
	}

	private static final String _PORTAL_TOOL_CONFIGURATION_NAME = "alloyTaglib";

	private static final String _PORTAL_TOOL_GROUP =
		"com.liferay.alloy-taglibs";

}