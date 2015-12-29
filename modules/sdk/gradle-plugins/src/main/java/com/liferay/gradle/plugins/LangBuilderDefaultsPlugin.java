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

import com.liferay.gradle.plugins.lang.builder.BuildLangTask;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<LangBuilderPlugin> {

	@Override
	protected void configureDefaults(
		Project project, LangBuilderPlugin langBuilderPlugin) {

		super.configureDefaults(project, langBuilderPlugin);

		configureTasksBuildLang(project);
	}

	protected void configureTaskBuildLang(BuildLangTask buildLangTask) {
		String translateClientId = GradleUtil.getProperty(
			buildLangTask.getProject(), "microsoft.translator.client.id",
			(String)null);

		buildLangTask.setTranslateClientId(translateClientId);

		String translateClientSecret = GradleUtil.getProperty(
			buildLangTask.getProject(), "microsoft.translator.client.secret",
			(String)null);

		buildLangTask.setTranslateClientSecret(translateClientSecret);
	}

	protected void configureTasksBuildLang(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildLangTask.class,
			new Action<BuildLangTask>() {

				@Override
				public void execute(BuildLangTask buildLangTask) {
					configureTaskBuildLang(buildLangTask);
				}

			});
	}

	@Override
	protected Class<LangBuilderPlugin> getPluginClass() {
		return LangBuilderPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return LangBuilderPlugin.CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private static final String _PORTAL_TOOL_NAME = "com.liferay.lang.builder";

}