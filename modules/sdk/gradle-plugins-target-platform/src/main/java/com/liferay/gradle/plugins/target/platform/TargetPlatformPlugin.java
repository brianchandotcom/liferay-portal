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

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementHandler;
import io.spring.gradle.dependencymanagement.dsl.ImportsHandler;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;

/**
 * @author Gregory Amerson
 */
public class TargetPlatformPlugin implements Plugin<Project> {

	public static final String EXTENSION_NAME = "targetPlatform";

	@Override
	public void apply(final Project project) {
		final TargetPlatformExtension targetPlatformExtension =
			_addTargetPlatformExtension(project);

		GradleUtil.applyPlugin(project, DependencyManagementPlugin.class);

		final Set<Project> subprojects =
			targetPlatformExtension.getSubprojects();

		if (!subprojects.isEmpty()) {
			GradleUtil.applyPlugin(project, EclipsePlugin.class);
			GradleUtil.applyPlugin(project, JavaBasePlugin.class);

			_addConfigurationDependencies(project);
		}

		final Configuration bomsConfiguration = _addConfigurationBoms(project);
		final Configuration bundlesConfiguration = _addConfigurationBundles(
			project);
		final Configuration distroConfiguration = _addConfigurationDistro(
			project);
		final Configuration requirementsConfiguration =
			_addConfigurationRequirements(project);

		final DependencySet bundlesDependencies =
			bundlesConfiguration.getDependencies();
		final DependencySet requirementsDependencies =
			requirementsConfiguration.getDependencies();

		final ResolveTask resolveTask = GradleUtil.addTask(
			project, "resolve", ResolveTask.class);

		resolveTask.setBundles(bundlesConfiguration);
		resolveTask.setDistro(distroConfiguration);
		resolveTask.setRequirements(requirementsConfiguration);
		resolveTask.setIgnoreFailures(
			new Closure<Boolean>(resolveTask) {

				@SuppressWarnings("unused")
				public boolean doCall() {
					return targetPlatformExtension.isIgnoreResolveFailures();
				}

			});

		final Project singleProject;
		final Project rootProject;

		if (subprojects.isEmpty()) {
			singleProject = project;
			rootProject = null;
		}
		else {
			singleProject = null;
			rootProject = project;
		}

		final DependencyHandler dependencyHandler = project.getDependencies();

		Gradle gradle = project.getGradle();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(final Project afterProject) {
					if (afterProject.equals(singleProject) ||
						afterProject.equals(rootProject)) {

						_configureDependencyManagement(
							afterProject, bomsConfiguration);
					}

					if (afterProject.equals(rootProject)) {
						_configureTargetPlatformDependencies(
							afterProject, targetPlatformExtension);
					}

					if (_shouldConfigureSubproject(
							afterProject, targetPlatformExtension)) {

						GradleUtil.applyPlugin(
							afterProject, DependencyManagementPlugin.class);

						_configureDependencyManagement(
							afterProject, bomsConfiguration);

						Spec<Project> resolveOnlyIfSpec =
							targetPlatformExtension.getResolveOnlyIf();

						if (!resolveOnlyIfSpec.isSatisfiedBy(afterProject)) {
							Logger logger = project.getLogger();

							if (logger.isInfoEnabled()) {
								logger.info(
									"Explicitly excluding {} from {}",
									afterProject, resolveTask);
							}

							return;
						}

						Dependency afterProjectDependency =
							dependencyHandler.create(afterProject);

						bundlesDependencies.add(afterProjectDependency);

						requirementsDependencies.add(afterProjectDependency);

						final ResolveTask resolveTask = GradleUtil.addTask(
							afterProject, "resolve", ResolveTask.class);

						resolveTask.setIgnoreFailures(
							targetPlatformExtension.isIgnoreResolveFailures());
						resolveTask.onlyIf(_skipIfExecutingParentTaskSpec);
						resolveTask.setBundles(bundlesConfiguration);
						resolveTask.setDistro(distroConfiguration);

						Task jar = GradleUtil.getTask(
							afterProject, JavaPlugin.JAR_TASK_NAME);

						resolveTask.setRequirements(afterProject.files(jar));
					}
					else if (afterProject.equals(singleProject)) {
						Task jar = GradleUtil.getTask(
							afterProject, JavaPlugin.JAR_TASK_NAME);

						resolveTask.setBundles(afterProject.files(jar));
						resolveTask.setRequirements(afterProject.files(jar));
					}
				}

			});
	}

	private static Configuration _addConfigurationBoms(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, _BOMS_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures all the BOMs to import as managed dependencies.");
		configuration.setVisible(false);
		configuration.setTransitive(false);

		return configuration;
	}

	private static Configuration _addConfigurationBundles(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, _BUNDLES_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures all the bundles in addition to the distro to resolve " +
				"against.");
		configuration.setVisible(false);
		configuration.setTransitive(false);

		return configuration;
	}

	private static Configuration _addConfigurationDependencies(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, _DEPENDENCIES_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures all the managed dependencies for the configured " +
				"Liferay target platform.");
		configuration.setVisible(false);

		return configuration;
	}

	private static Configuration _addConfigurationDistro(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, _DISTRO_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the distro JAR file to use as base for resolving " +
				"against.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private static Configuration _addConfigurationRequirements(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, _REQUIREMENTS_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the list of JAR files to use as run requirements for " +
				"resolving.");
		configuration.setVisible(false);
		configuration.setTransitive(false);

		return configuration;
	}

	private static void _configureDependencyManagement(
		final Project project, final Configuration bomsConfiguration) {

		DependencyManagementExtension dependencyManagementExtension =
			GradleUtil.getExtension(
				project, DependencyManagementExtension.class);

		dependencyManagementExtension.imports(
			new Action<ImportsHandler>() {

				@Override
				public void execute(ImportsHandler imports) {
					DependencySet deps = bomsConfiguration.getDependencies();

					for (Dependency dep : deps) {
						String coordinates =
							dep.getGroup() + ":" + dep.getName() + ":" +
								dep.getVersion();

						imports.mavenBom(coordinates);
					}
				}

			});
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
				project, _DEPENDENCIES_CONFIGURATION_NAME));
	}

	private static void _configureTargetPlatformDependencies(
		Project project,
		final TargetPlatformExtension targetPlatformExtension) {

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

			Set<?> ideIncludeGroups =
				targetPlatformExtension.getIdeIncludeGroups();

			if (ideIncludeGroups.contains(coordinates[0])) {
				targetPlatformDependencies.add(key + ":" + entry.getValue());
			}
		}

		Configuration configuration = GradleUtil.getConfiguration(
			project, _DEPENDENCIES_CONFIGURATION_NAME);

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

	private static boolean _shouldConfigureSubproject(
		Project afterProject, TargetPlatformExtension targetPlatformExtension) {

		Spec<Project> onlyIfSpec = targetPlatformExtension.getOnlyIf();
		Set<Project> subprojects = targetPlatformExtension.getSubprojects();

		PluginContainer pluginContainer = afterProject.getPlugins();

		if (onlyIfSpec.isSatisfiedBy(afterProject) &&
			subprojects.contains(afterProject) &&
			pluginContainer.hasPlugin(JavaPlugin.class)) {

			return true;
		}

		return false;
	}

	private TargetPlatformExtension _addTargetPlatformExtension(
		Project project) {

		ExtensionAware extensionAware = project;

		ExtensionContainer extensionContainer = extensionAware.getExtensions();

		return extensionContainer.create(
			EXTENSION_NAME, TargetPlatformExtension.class, project);
	}

	private static final String _BOMS_CONFIGURATION_NAME = "targetPlatformBoms";

	private static final String _BUNDLES_CONFIGURATION_NAME =
		"targetPlatformBundles";

	private static final String _DEPENDENCIES_CONFIGURATION_NAME =
		"targetPlatformDependencies";

	private static final String _DISTRO_CONFIGURATION_NAME =
		"targetPlatformDistro";

	private static final String _REQUIREMENTS_CONFIGURATION_NAME =
		"targetPlatformRequirements";

	private static final Spec<Task> _skipIfExecutingParentTaskSpec =
		new Spec<Task>() {

			@Override
			public boolean isSatisfiedBy(Task task) {
				Project project = task.getProject();

				Gradle gradle = project.getGradle();

				TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

				Project parentProject = project;

				while ((parentProject = parentProject.getParent()) != null) {
					TaskContainer parentProjectTaskContainer =
						parentProject.getTasks();

					Task parentProjectTask =
						parentProjectTaskContainer.findByName(task.getName());

					if ((parentProjectTask != null) &&
						taskExecutionGraph.hasTask(parentProjectTask)) {

						return false;
					}
				}

				return true;
			}

		};

}