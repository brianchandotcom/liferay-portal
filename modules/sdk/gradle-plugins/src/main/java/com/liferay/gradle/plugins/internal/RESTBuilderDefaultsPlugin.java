/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.rest.builder.RESTBuilderPlugin;
import com.liferay.gradle.plugins.util.PortalTools;

import groovy.lang.Closure;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Peter Shin
 */
public class RESTBuilderDefaultsPlugin
	extends BaseDefaultsPlugin<RESTBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new RESTBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, RESTBuilderPlugin restBuilderPlugin) {

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, RESTBuilderPlugin.CONFIGURATION_NAME, PortalTools.GROUP,
			_PORTAL_TOOL_NAME);

		// Tasks

		TaskProvider<Copy> processResourcesTaskProvider =
			GradleUtil.getTaskProvider(
				project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME, Copy.class);

		_configureTaskProcessResourcesProvider(
			project, processResourcesTaskProvider);
	}

	@Override
	protected Class<RESTBuilderPlugin> getPluginClass() {
		return RESTBuilderPlugin.class;
	}

	private RESTBuilderDefaultsPlugin() {
	}

	@SuppressWarnings("serial")
	private void _configureTaskProcessResourcesProvider(
		final Project project,
		TaskProvider<Copy> processResourcesTaskProvider) {

		processResourcesTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy processResourcesCopy) {
					processResourcesCopy.setDuplicatesStrategy(
						DuplicatesStrategy.INCLUDE);

					processResourcesCopy.into(
						"META-INF/liferay/rest",
						new Closure<Void>(processResourcesCopy) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.from(project.getProjectDir());
								copySpec.include("*.yaml");
							}

						});
				}

			});
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.rest.builder";

}