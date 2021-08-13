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

package com.liferay.gradle.plugins.target.platform;

import com.liferay.gradle.plugins.target.platform.extensions.TargetPlatformIDEExtension;
import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;

import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;

import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;
import org.gradle.plugins.ide.idea.model.internal.GeneratedIdeaScope;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public class TargetPlatformIDEPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "targetPlatformIDE";

	public static final String TARGET_PLATFORM_IDE_CONFIGURATION_NAME =
		"targetPlatformIDE";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, JavaPlugin.class);
		GradleUtil.applyPlugin(project, TargetPlatformPlugin.class);

		Configuration targetPlatformIDEConfiguration =
			_addConfigurationTargetPlatformIDE(project);

		TargetPlatformIDEExtension targetPlatformIDEExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, TargetPlatformIDEExtension.class);

		if (targetPlatformIDEExtension.isIndexSources()) {
			_configureEclipseModel(project, targetPlatformIDEConfiguration);
			_configureIdeaModel(project, targetPlatformIDEConfiguration);
		}
	}

	private Configuration _addConfigurationTargetPlatformIDE(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_IDE_CONFIGURATION_NAME);

		final Configuration ideBomsConfiguration = GradleUtil.addConfiguration(
			project, _TARGET_PLATFORM_IDE_BOMS_CONFIGURATION_NAME);

		Configuration bomsConfiguration = GradleUtil.getConfiguration(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME);

		DependencySet bomsDependencySet = bomsConfiguration.getDependencies();

		bomsDependencySet.all(
			new Action<Dependency>() {

				@Override
				public void execute(Dependency dependency) {
					StringBuilder sb = new StringBuilder();

					sb.append(dependency.getGroup());
					sb.append(':');
					sb.append(dependency.getName());
					sb.append(':');
					sb.append(dependency.getVersion());
					sb.append("@pom");

					GradleUtil.addDependency(
						project, _TARGET_PLATFORM_IDE_BOMS_CONFIGURATION_NAME,
						sb.toString());
				}

			});

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesTargetPlatformIDE(
						project, ideBomsConfiguration);
				}

			});

		configuration.setDescription(
			"Configures all the managed dependencies for the configured " +
				"Liferay target platform.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesTargetPlatformIDE(
		Project project, Configuration ideBomsConfiguration) {

		Set<File> bomFiles = ideBomsConfiguration.resolve();

		for (File bomFile : bomFiles) {
			try {
				XmlSlurper xmlSlurper = new XmlSlurper();

				GPathResult gPathResult = xmlSlurper.parse(bomFile);

				gPathResult = (GPathResult)gPathResult.getProperty(
					"dependencyManagement");

				gPathResult = (GPathResult)gPathResult.getProperty(
					"dependencies");

				gPathResult = (GPathResult)gPathResult.getProperty(
					"dependency");

				Iterator<?> iterator = gPathResult.iterator();

				while (iterator.hasNext()) {
					gPathResult = (GPathResult)iterator.next();

					StringBuilder sb = new StringBuilder();

					sb.append(gPathResult.getProperty("groupId"));
					sb.append(':');
					sb.append(gPathResult.getProperty("artifactId"));
					sb.append(':');
					sb.append(gPathResult.getProperty("version"));

					GradleUtil.addDependency(
						project, TARGET_PLATFORM_IDE_CONFIGURATION_NAME,
						sb.toString());
				}
			}
			catch (Exception exception) {
				Logger logger = project.getLogger();

				if (logger.isWarnEnabled()) {
					logger.warn(
						"Unable to add BOM dependencies from {}", bomFile);
				}
			}
		}
	}

	private void _configureEclipseModel(
		Project project, Configuration targetPlatformIDEConfiguration) {

		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		eclipseClasspath.setDownloadJavadoc(false);

		Collection<Configuration> plusConfigurations =
			eclipseClasspath.getPlusConfigurations();

		plusConfigurations.add(targetPlatformIDEConfiguration);
	}

	private void _configureIdeaModel(
		Project project, Configuration targetPlatformIDEConfiguration) {

		IdeaModel ideaModel = GradleUtil.getExtension(project, IdeaModel.class);

		IdeaModule ideaModule = ideaModel.getModule();

		Map<String, Map<String, Collection<Configuration>>> scopes =
			ideaModule.getScopes();

		Map<String, Collection<Configuration>> providedScope = scopes.get(
			GeneratedIdeaScope.PROVIDED.name());

		if (providedScope == null) {
			providedScope = new HashMap<>();
		}

		Collection<Configuration> plus = providedScope.get("plus");

		if (plus == null) {
			plus = new ArrayList<>();
		}

		plus.add(targetPlatformIDEConfiguration);

		providedScope.put("plus", plus);

		scopes.put(GeneratedIdeaScope.PROVIDED.name(), providedScope);

		ideaModule.setScopes(scopes);

		SourceSet mainSourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		FileCollection compileClasspath = mainSourceSet.getCompileClasspath();

		compileClasspath.plus(targetPlatformIDEConfiguration);
	}

	private static final String _TARGET_PLATFORM_IDE_BOMS_CONFIGURATION_NAME =
		"targetPlatformIDEBoms";

}