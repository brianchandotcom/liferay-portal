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
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.lang.builder.BuildLangTask;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.util.PortalTools;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderDefaultsPlugin
	extends BaseDefaultsPlugin<LangBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new LangBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, LangBuilderPlugin langBuilderPlugin) {

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, LangBuilderPlugin.CONFIGURATION_NAME, PortalTools.GROUP,
			_PORTAL_TOOL_NAME);

		// Containers

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildLangTask.class,
			new Action<BuildLangTask>() {

				@Override
				public void execute(BuildLangTask buildLangTask) {
					_configureTaskBuildLang(buildLangTask);
				}

			});
	}

	@Override
	protected Class<LangBuilderPlugin> getPluginClass() {
		return LangBuilderPlugin.class;
	}

	private LangBuilderDefaultsPlugin() {
	}

	private void _configureTaskBuildLang(BuildLangTask buildLangTask) {
		String translateSubscriptionKey = GradleUtil.getProperty(
			buildLangTask.getProject(), "microsoft.translator.subscription.key",
			(String)null);

		buildLangTask.setTranslateSubscriptionKey(translateSubscriptionKey);
	}

	private static final String _PORTAL_TOOL_NAME = "com.liferay.lang.builder";

}