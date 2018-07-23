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

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementConfigurer;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;
import io.spring.gradle.dependencymanagement.dsl.ImportsHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
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

		DependencyManagementExtension dependencyManagementExtension =
			GradleUtil.getExtension(
				project, DependencyManagementExtension.class);

		TargetPlatformIDEExtension targetPlatformIDEExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, TargetPlatformIDEExtension.class);

		Configuration targetPlatformIDEConfiguration =
			_addConfigurationTargetPlatformIDE(
				project, dependencyManagementExtension,
				targetPlatformIDEExtension);

		_configureEclipseModel(project, targetPlatformIDEConfiguration);
		_configureIdeaModel(project, targetPlatformIDEConfiguration);
	}

	private Configuration _addConfigurationTargetPlatformIDE(
		final Project project,
		final DependencyManagementExtension dependencyManagementExtension,
		final TargetPlatformIDEExtension targetPlatformIDEExtension) {

		final Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_IDE_CONFIGURATION_NAME);

		final Configuration bomConfiguration = GradleUtil.getConfiguration(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesTargetPlatformIDE(
						project, dependencyManagementExtension,
						targetPlatformIDEExtension, configuration,
						bomConfiguration);
				}

			});

		configuration.setDescription(
			"Configures all the managed dependencies for the configured " +
				"Liferay target platform.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesTargetPlatformIDE(
		Project project,
		DependencyManagementExtension dependencyManagementExtension,
		TargetPlatformIDEExtension targetPlatformIDEExtension,
		Configuration configuration, final Configuration bomConfiguration) {

		GroovyObjectSupport groovyObjectSupport =
			(GroovyObjectSupport)dependencyManagementExtension;

		Object[] args = {
			configuration,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall() {
					DependencySet dependencySet =
						bomConfiguration.getAllDependencies();

					final DependencyManagementConfigurer
						dependencyManagementConfigurer =
							(DependencyManagementConfigurer)getDelegate();

					dependencySet.all(
						new Action<Dependency>() {

							@Override
							public void execute(Dependency dependency) {
								_configureDependencyManagementImportsHandler(
									dependencyManagementConfigurer, dependency);
							}

						});
				}

			}
		};

		groovyObjectSupport.invokeMethod("configurations", args);

		Map<String, String> managedVersions =
			dependencyManagementExtension.getManagedVersionsForConfiguration(
				configuration);

		if (managedVersions.isEmpty()) {
			return;
		}

		Set<String> includeGroups = new HashSet<>();

		for (Object object : targetPlatformIDEExtension.getIncludeGroups()) {
			includeGroups.add(GradleUtil.toString(object));
		}

		Set<String> dependencyNotations = new LinkedHashSet<>();

		for (Map.Entry<String, String> entry : managedVersions.entrySet()) {
			String key = entry.getKey();

			String group = key.substring(0, key.indexOf(':'));

			if (includeGroups.contains(group)) {
				dependencyNotations.add(key + ":" + entry.getValue());
			}
		}

		for (String dependencyNotation : dependencyNotations) {
			GradleUtil.addDependency(
				project, TARGET_PLATFORM_IDE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	private void _configureDependencyManagementImportsHandler(
		DependencyManagementConfigurer dependencyManagementConfigurer,
		final Dependency dependency) {

		dependencyManagementConfigurer.imports(
			new Action<ImportsHandler>() {

				@Override
				public void execute(ImportsHandler importsHandler) {
					StringBuilder sb = new StringBuilder();

					sb.append(dependency.getGroup());
					sb.append(':');
					sb.append(dependency.getName());
					sb.append(':');
					sb.append(dependency.getVersion());

					importsHandler.mavenBom(sb.toString());
				}

			});
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

}