/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.schema.manifest.builder;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Luis Ortiz
 */
public class SchemaManifestBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_SCHEMA_MANIFEST_TASK_NAME =
		"buildSchemaManifest";

	public static final String CONFIGURATION_NAME = "schemaManifestBuilder";

	@Override
	public void apply(Project project) {
		Configuration schemaManifestBuilderConfiguration =
			_addConfigurationSchemaManifestBuilder(project);

		_addTaskBuildSchemaManifest(project);

		_configureTasksBuildSchemaManifest(
			project, schemaManifestBuilderConfiguration);
	}

	private Configuration _addConfigurationSchemaManifestBuilder(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					GradleUtil.addDependency(
						project, CONFIGURATION_NAME, "com.liferay",
						"com.liferay.portal.tools.schema.manifest.builder",
						"latest.release");
				}

			});

		configuration.setDescription(
			"Configures Liferay Schema Manifest Builder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addTaskBuildSchemaManifest(Project project) {
		BuildSchemaManifestTask buildSchemaManifestTask = GradleUtil.addTask(
			project, BUILD_SCHEMA_MANIFEST_TASK_NAME,
			BuildSchemaManifestTask.class);

		buildSchemaManifestTask.setBaseDir(project.getProjectDir());
		buildSchemaManifestTask.setDescription(
			"Runs Liferay Schema Manifest Builder to build the database " +
				"changes manifest.");
		buildSchemaManifestTask.setGroup(BasePlugin.BUILD_GROUP);
		buildSchemaManifestTask.setManifestFile(
			project.file("src/main/resources/META-INF/schema/manifest.json"));
	}

	private void _configureTasksBuildSchemaManifest(
		Project project,
		final Configuration schemaManifestBuilderConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildSchemaManifestTask.class,
			new Action<BuildSchemaManifestTask>() {

				@Override
				public void execute(
					BuildSchemaManifestTask buildSchemaManifestTask) {

					buildSchemaManifestTask.setClasspath(
						schemaManifestBuilderConfiguration);
				}

			});
	}

}