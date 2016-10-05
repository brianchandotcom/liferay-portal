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
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.alloy.taglib.AlloyTaglibPlugin;
import com.liferay.gradle.plugins.alloy.taglib.BuildTaglibsTask;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class AlloyTaglibDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<AlloyTaglibPlugin> {

	public static final Plugin<Project> INSTANCE =
		new AlloyTaglibDefaultsPlugin();

	protected Configuration addPortalToolConfiguration(final Project project) {
		final Configuration configuration = GradleUtil.addConfiguration(
			project, getPortalToolConfigurationName());

		configuration.setDescription(
			"Configures the Alloy Taglib tool for this project.");
		configuration.setVisible(false);

		GradleUtil.withPlugin(
			project, LiferayBasePlugin.class,
			new Action<LiferayBasePlugin>() {

				@Override
				public void execute(LiferayBasePlugin liferayBasePlugin) {
					Configuration portalConfiguration =
						GradleUtil.getConfiguration(
							project,
							LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

					configuration.extendsFrom(portalConfiguration);
				}

			});

		// AlloyTaglibPlugin has already applied JavaPlugin

		Configuration runtimeConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.RUNTIME_CONFIGURATION_NAME);

		configuration.extendsFrom(runtimeConfiguration);

		return configuration;
	}

	@Override
	protected void addPortalToolDependencies(Project project) {
		addPortalToolConfiguration(project);

		super.addPortalToolDependencies(project);

		GradleUtil.addDependency(
			project, getPortalToolConfigurationName(), "org.freemarker",
			"freemarker", "2.3.23");
	}

	@Override
	protected void configureDefaults(
		Project project, AlloyTaglibPlugin alloyTaglibPlugin) {

		super.configureDefaults(project, alloyTaglibPlugin);

		_configureTasksBuildTaglibs(project);
	}

	@Override
	protected Class<AlloyTaglibPlugin> getPluginClass() {
		return AlloyTaglibPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return _PORTAL_TOOL_CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private AlloyTaglibDefaultsPlugin() {
	}

	private void _configureTaskBuildTaglibs(BuildTaglibsTask buildTaglibsTask) {
		Configuration configuration = GradleUtil.getConfiguration(
			buildTaglibsTask.getProject(), getPortalToolConfigurationName());

		buildTaglibsTask.setClasspath(configuration);
	}

	private void _configureTasksBuildTaglibs(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildTaglibsTask.class,
			new Action<BuildTaglibsTask>() {

				@Override
				public void execute(BuildTaglibsTask buildTaglibsTask) {
					_configureTaskBuildTaglibs(buildTaglibsTask);
				}

			});
	}

	private static final String _PORTAL_TOOL_CONFIGURATION_NAME = "alloyTaglib";

	private static final String _PORTAL_TOOL_NAME = "com.liferay.alloy.taglib";

}