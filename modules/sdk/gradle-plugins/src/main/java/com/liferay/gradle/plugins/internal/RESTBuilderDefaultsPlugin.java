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

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Peter Shin
 */
public class RESTBuilderDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<RESTBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new RESTBuilderDefaultsPlugin();

	@Override
	protected void configureDefaults(
		final Project project, RESTBuilderPlugin restBuilderPlugin) {

		super.configureDefaults(project, restBuilderPlugin);

		_configureTaskBuildREST(project);
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

	private void _configureTaskBuildREST(Project project) {
		BuildRESTTask buildRESTTask = (BuildRESTTask)GradleUtil.getTask(
			project, RESTBuilderPlugin.BUILD_REST_TASK_NAME);

		buildRESTTask.setAuthor("Brian Wing Shun Chan");
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.rest.builder";

}