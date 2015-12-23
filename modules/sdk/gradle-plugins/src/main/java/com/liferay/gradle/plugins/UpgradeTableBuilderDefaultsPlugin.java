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

import com.liferay.gradle.plugins.upgrade.table.builder.BuildUpgradeTableTask;
import com.liferay.gradle.plugins.upgrade.table.builder.UpgradeTableBuilderPlugin;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class UpgradeTableBuilderDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<UpgradeTableBuilderPlugin> {

	@Override
	protected void configureDefaults(
		Project project, UpgradeTableBuilderPlugin upgradeTableBuilderPlugin) {

		super.configureDefaults(project, upgradeTableBuilderPlugin);

		configureTasksBuildUpgradeTable(project);
	}

	protected void configureTaskBuildUpgradeTable(
		BuildUpgradeTableTask buildUpgradeTableTask) {

		File file = GradleUtil.getProperty(
			buildUpgradeTableTask.getProject(), "upgrade.table.dir",
			(File)null);

		buildUpgradeTableTask.setUpgradeTableDir(file);
	}

	protected void configureTasksBuildUpgradeTable(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildUpgradeTableTask.class,
			new Action<BuildUpgradeTableTask>() {

				@Override
				public void execute(
					BuildUpgradeTableTask buildUpgradeTableTask) {

					configureTaskBuildUpgradeTable(buildUpgradeTableTask);
				}

			});
	}

	@Override
	protected Class<UpgradeTableBuilderPlugin> getPluginClass() {
		return UpgradeTableBuilderPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return UpgradeTableBuilderPlugin.CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.upgrade.table.builder";

}