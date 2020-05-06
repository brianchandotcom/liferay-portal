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
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.rest.builder.BuildRESTTask;
import com.liferay.gradle.plugins.rest.builder.RESTBuilderPlugin;

import groovy.lang.Closure;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;

/**
 * @author Peter Shin
 */
public class RESTBuilderDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<RESTBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new RESTBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		final Project project, RESTBuilderPlugin restBuilderPlugin) {

		addPortalToolDependencies(project);

		BuildRESTTask buildRESTTask = (BuildRESTTask)GradleUtil.getTask(
			project, RESTBuilderPlugin.BUILD_REST_TASK_NAME);

		_configureTaskProcessResources(buildRESTTask);
	}

	@Override
	protected Class<RESTBuilderPlugin> getPluginClass() {
		return RESTBuilderPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return RESTBuilderPlugin.CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private RESTBuilderDefaultsPlugin() {
	}

	@SuppressWarnings("serial")
	private void _configureTaskProcessResources(BuildRESTTask buildRESTTask) {
		final Project project = buildRESTTask.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		copy.into(
			"META-INF/liferay/rest",
			new Closure<Void>(copy) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.from(project.getProjectDir());
					copySpec.include("*.yaml");
				}

			});
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.rest.builder";

}