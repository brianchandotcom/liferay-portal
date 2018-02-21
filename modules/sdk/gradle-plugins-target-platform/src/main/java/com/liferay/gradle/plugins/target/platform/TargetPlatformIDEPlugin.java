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

import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementHandler;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;

/**
 * @author Gregory Amerson
 */
public class TargetPlatformIDEPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "targetPlatformIDE";

	public static final String
		TARGET_PLATFORM_IDE_DEPENDENCIES_CONFIGURATION_NAME =
			"targetPlatformIDEDependencies";

	@Override
	public void apply(final Project project) {
		final TargetPlatformIDEExtension targetPlatformIDEExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, TargetPlatformIDEExtension.class);

		GradleUtil.applyPlugin(project, TargetPlatformPlugin.class);
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, JavaBasePlugin.class);

		_addConfigurationTargetPlatformDependencies(project);

		project.afterEvaluate(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project afterProject) {
					_configureTargetPlatformDependencies(
						project, targetPlatformIDEExtension);
				}

			});
	}

	private static Configuration _addConfigurationTargetPlatformDependencies(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_IDE_DEPENDENCIES_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures all the managed dependencies for the configured " +
				"Liferay target platform.");
		configuration.setVisible(false);

		return configuration;
	}

	private static void _configureEclipseModel(Project project) {
		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath classpath = eclipseModel.getClasspath();

		classpath.setDownloadJavadoc(false);

		Collection<Configuration> plusConfigurations =
			classpath.getPlusConfigurations();

		plusConfigurations.add(
			GradleUtil.getConfiguration(
				project, TARGET_PLATFORM_IDE_DEPENDENCIES_CONFIGURATION_NAME));
	}

	private static void _configureTargetPlatformDependencies(
		Project project,
		TargetPlatformIDEExtension targetPlatformIDEExtension) {

		_configureEclipseModel(project);

		DependencyManagementHandler dependencyManagement =
			GradleUtil.getExtension(project, DependencyManagementHandler.class);

		Map<String, String> managedVersions =
			dependencyManagement.getManagedVersions();

		if (managedVersions.isEmpty()) {
			return;
		}

		final Set<String> targetPlatformDependencies = new LinkedHashSet<>();

		for (Map.Entry<String, String> entry : managedVersions.entrySet()) {
			String key = entry.getKey();

			String[] coordinates = key.split(":");

			Set<?> includeGroups =
				targetPlatformIDEExtension.getIncludeGroups();

			if (includeGroups.contains(coordinates[0])) {
				targetPlatformDependencies.add(key + ":" + entry.getValue());
			}
		}

		Configuration configuration = GradleUtil.getConfiguration(
			project, TARGET_PLATFORM_IDE_DEPENDENCIES_CONFIGURATION_NAME);

		final DependencyHandler dependencyHandler = project.getDependencies();

		final Closure<Void> nonTransitive = new Closure<Void>(project) {

			@SuppressWarnings("unused")
			public void doCall(ModuleDependency dependency) {
				dependency.setTransitive(false);
			}

		};

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencies) {
					for (String targetPlatformDependency :
							targetPlatformDependencies) {

						dependencies.add(
							dependencyHandler.create(
								targetPlatformDependency, nonTransitive));
					}
				};

			});
	}

}