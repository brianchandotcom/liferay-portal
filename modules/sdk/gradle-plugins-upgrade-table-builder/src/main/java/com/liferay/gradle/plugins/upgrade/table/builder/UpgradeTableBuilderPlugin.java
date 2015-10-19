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

package com.liferay.gradle.plugins.upgrade.table.builder;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

/**
 * @author Andrea Di Giorgi
 */
public class UpgradeTableBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_UPGRADE_TABLE_TASK_NAME =
		"buildUpgradeTable";

	public static final String CONFIGURATION_NAME = "upgradeTableBuilder";

	@Override
	public void apply(Project project) {
		addUpgradeTableBuilderConfiguration(project);

		addBuildUpgradeTableTask(project);
	}

	protected BuildUpgradeTableTask addBuildUpgradeTableTask(Project project) {
		BuildUpgradeTableTask buildUpgradeTableTask = GradleUtil.addTask(
			project, BUILD_UPGRADE_TABLE_TASK_NAME,
			BuildUpgradeTableTask.class);

		buildUpgradeTableTask.setDescription(
			"Runs Liferay Upgrade Table Builder to build upgrade tables.");

		return buildUpgradeTableTask;
	}

	protected Configuration addUpgradeTableBuilderConfiguration(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Upgrade Table Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addUpgradeTableBuilderDependencies(project);
				}

			});

		return configuration;
	}

	protected void addUpgradeTableBuilderDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.upgrade.table.builder", "latest.release");
	}

}