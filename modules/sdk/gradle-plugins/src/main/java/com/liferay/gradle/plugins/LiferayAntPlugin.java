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

import com.liferay.gradle.plugins.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import org.gradle.api.AntBuilder;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ConfigurablePublishArtifact;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayAntPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, BasePlugin.class);

		AntBuilder antBuilder = project.getAnt();

		antBuilder.importBuild("build.xml");

		configureArchivesBaseName(project, antBuilder);
		configureArtifacts(project, antBuilder);
		configureVersion(project, antBuilder);
	}

	protected void configureArchivesBaseName(
		Project project, AntBuilder antBuilder) {

		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		basePluginConvention.setArchivesBaseName(
			String.valueOf(antBuilder.getProperty("plugin.name")));
	}

	protected void configureArtifacts(
		final Project project, AntBuilder antBuilder) {

		ArtifactHandler artifacts = project.getArtifacts();

		File pluginFile = project.file(antBuilder.getProperty("plugin.file"));

		artifacts.add(
			Dependency.ARCHIVES_CONFIGURATION, pluginFile,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(
					ConfigurablePublishArtifact configurablePublishArtifact) {

					Task warTask = GradleUtil.getTask(project, _WAR_TASK_NAME);

					configurablePublishArtifact.builtBy(warTask);

					configurablePublishArtifact.setName(
						GradleUtil.getArchivesBaseName(project));
				}

			});
	}

	protected void configureVersion(Project project, AntBuilder antBuilder) {
		project.setVersion(antBuilder.getProperty("plugin.full.version"));
	}

	private static final String _WAR_TASK_NAME = "war";

}