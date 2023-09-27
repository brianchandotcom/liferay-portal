/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.upgrade.table.builder.BuildUpgradeTableTask;
import com.liferay.gradle.plugins.upgrade.table.builder.UpgradeTableBuilderPlugin;
import com.liferay.gradle.plugins.util.PortalTools;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class UpgradeTableBuilderDefaultsPlugin
	extends BaseDefaultsPlugin<UpgradeTableBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new UpgradeTableBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, UpgradeTableBuilderPlugin upgradeTableBuilderPlugin) {

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, UpgradeTableBuilderPlugin.CONFIGURATION_NAME,
			PortalTools.GROUP, _PORTAL_TOOL_NAME);

		// Containers

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildUpgradeTableTask.class,
			new Action<BuildUpgradeTableTask>() {

				@Override
				public void execute(
					BuildUpgradeTableTask buildUpgradeTableTask) {

					_configureTaskBuildUpgradeTable(buildUpgradeTableTask);
				}

			});
	}

	@Override
	protected Class<UpgradeTableBuilderPlugin> getPluginClass() {
		return UpgradeTableBuilderPlugin.class;
	}

	private UpgradeTableBuilderDefaultsPlugin() {
	}

	private void _configureTaskBuildUpgradeTable(
		BuildUpgradeTableTask buildUpgradeTableTask) {

		File file = GradleUtil.getProperty(
			buildUpgradeTableTask.getProject(), "upgrade.table.dir",
			(File)null);

		buildUpgradeTableTask.setUpgradeTableDir(file);
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.upgrade.table.builder";

}