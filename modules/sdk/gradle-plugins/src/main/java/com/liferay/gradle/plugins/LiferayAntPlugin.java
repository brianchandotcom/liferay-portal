/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;

import groovy.lang.Closure;

import org.gradle.api.Action;
import org.gradle.api.AntBuilder;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.Transformer;
import org.gradle.api.artifacts.ConfigurablePublishArtifact;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.Convention;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayAntPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {

		// Plugins

		GradleUtil.applyPlugin(project, BasePlugin.class);

		// Ant

		AntBuilder antBuilder = project.getAnt();

		antBuilder.importBuild("build.xml", _antTaskNamer);

		// Conventions

		Convention convention = project.getConvention();

		final BasePluginConvention basePluginConvention = convention.getPlugin(
			BasePluginConvention.class);

		_configureConventionBasePlugin(antBuilder, basePluginConvention);

		// Tasks

		TaskProvider<Delete> cleanTaskProvider = GradleUtil.getTaskProvider(
			project, BasePlugin.CLEAN_TASK_NAME, Delete.class);
		final TaskProvider<Task> warTaskProvider = GradleUtil.getTaskProvider(
			project, _WAR_TASK_NAME);

		_configureTaskCleanProvider(cleanTaskProvider);

		// Other

		_configureProject(project, antBuilder);

		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(
			Dependency.ARCHIVES_CONFIGURATION,
			project.file(antBuilder.getProperty("plugin.file")),
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(
					ConfigurablePublishArtifact configurablePublishArtifact) {

					configurablePublishArtifact.builtBy(warTaskProvider.get());

					configurablePublishArtifact.setName(
						basePluginConvention.getArchivesBaseName());
				}

			});
	}

	private void _configureConventionBasePlugin(
		AntBuilder antBuilder, BasePluginConvention basePluginConvention) {

		basePluginConvention.setArchivesBaseName(
			String.valueOf(antBuilder.getProperty("plugin.name")));
	}

	private void _configureProject(Project project, AntBuilder antBuilder) {
		project.setVersion(antBuilder.getProperty("plugin.full.version"));
	}

	private void _configureTaskCleanProvider(
		TaskProvider<Delete> cleanTaskProvider) {

		cleanTaskProvider.configure(
			new Action<Delete>() {

				@Override
				public void execute(Delete cleanDelete) {
					cleanDelete.dependsOn(
						_antTaskNamer.transform(cleanDelete.getName()));
				}

			});
	}

	private static final String _WAR_TASK_NAME = "war";

	private static final Transformer<String, String> _antTaskNamer =
		new Transformer<String, String>() {

			@Override
			public String transform(String targetName) {
				if (targetName.equals(BasePlugin.CLEAN_TASK_NAME) ||
					targetName.equals(
						PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME)) {

					targetName = "ant" + StringUtil.capitalize(targetName);
				}

				return targetName;
			}

		};

}