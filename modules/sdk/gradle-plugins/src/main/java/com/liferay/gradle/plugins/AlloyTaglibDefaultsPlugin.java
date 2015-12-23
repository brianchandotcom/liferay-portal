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

import com.liferay.gradle.plugins.alloy.taglib.AlloyTaglibPlugin;
import com.liferay.gradle.plugins.alloy.taglib.BuildTaglibsTask;
import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class AlloyTaglibDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<AlloyTaglibPlugin> {

	protected Configuration addPortalToolConfiguration(final Project project) {
		final Configuration configuration = GradleUtil.addConfiguration(
			project, getPortalToolConfigurationName());

		configuration.setDescription(
			"Configures the Alloy Taglib tool for this project.");
		configuration.setVisible(false);

		withLiferayPlugin(
			project,
			new Action<LiferayPlugin>() {

				@Override
				public void execute(LiferayPlugin liferayPlugin) {
					Configuration portalConfiguration =
						GradleUtil.getConfiguration(
							project,
							LiferayJavaPlugin.PORTAL_CONFIGURATION_NAME);

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
			project, getPortalToolConfigurationName(), "com.liferay",
			"org.freemarker", "2.3.17.LIFERAY-PATCHED-1");
	}

	@Override
	protected void configureDefaults(
		Project project, AlloyTaglibPlugin alloyTaglibPlugin) {

		super.configureDefaults(project, alloyTaglibPlugin);

		configureTasksBuildTaglibs(project);
	}

	protected void configureTaskBuildTaglibs(
		BuildTaglibsTask buildTaglibsTask) {

		Configuration configuration = GradleUtil.getConfiguration(
			buildTaglibsTask.getProject(), getPortalToolConfigurationName());

		buildTaglibsTask.setClasspath(configuration);
	}

	protected void configureTasksBuildTaglibs(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildTaglibsTask.class,
			new Action<BuildTaglibsTask>() {

				@Override
				public void execute(BuildTaglibsTask buildTaglibsTask) {
					configureTaskBuildTaglibs(buildTaglibsTask);
				}

			});
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

	private static final String _PORTAL_TOOL_CONFIGURATION_NAME = "alloyTaglib";

	private static final String _PORTAL_TOOL_NAME = "com.liferay.alloy.taglib";

}