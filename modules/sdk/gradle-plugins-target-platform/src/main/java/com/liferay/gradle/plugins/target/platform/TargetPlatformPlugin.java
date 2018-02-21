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

import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;
import com.liferay.gradle.plugins.target.platform.internal.util.SkipIfExecutingParentTaskSpec;

import groovy.lang.Closure;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementHandler;
import io.spring.gradle.dependencymanagement.dsl.ImportsHandler;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
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
 * @author Andrea Di Giorgi
 */
public class TargetPlatformPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "targetPlatform";

	public static final String RESOLVE_TASK_NAME = "resolve";

	public static final String TARGET_PLATFORM_BOMS_CONFIGURATION_NAME =
		"targetPlatformBoms";

	public static final String TARGET_PLATFORM_BUNDLES_CONFIGURATION_NAME =
		"targetPlatformBundles";

	public static final String TARGET_PLATFORM_DEPENDENCIES_CONFIGURATION_NAME =
		"targetPlatformDependencies";

	public static final String TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME =
		"targetPlatformDistro";

	public static final String TARGET_PLATFORM_REQUIREMENTS_CONFIGURATION_NAME =
		"targetPlatformRequirements";

	@Override
	public void apply(final Project project) {
		final TargetPlatformExtension targetPlatformExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, TargetPlatformExtension.class);

		GradleUtil.applyPlugin(project, DependencyManagementPlugin.class);

		Set<Project> subprojects = targetPlatformExtension.getSubprojects();

		if (!subprojects.isEmpty()) {
			GradleUtil.applyPlugin(project, EclipsePlugin.class);
			GradleUtil.applyPlugin(project, JavaBasePlugin.class);

			_addConfigurationTargetPlatformDependencies(project);
		}

		final Configuration targetPlatformBomsConfiguration =
			_addConfigurationTargetPlatformBoms(project);
		final Configuration targetPlatformBundlesConfiguration =
			_addConfigurationTargetPlatformBundles(project);
		final Configuration targetPlatformDistroConfiguration =
			_addConfigurationTargetPlatformDistro(project);
		Configuration targetPlatformRequirementsConfiguration =
			_addConfigurationTargetPlatformRequirements(project);

		final DependencySet bundlesDependencies =
			targetPlatformBundlesConfiguration.getDependencies();
		final DependencySet requirementsDependencies =
			targetPlatformRequirementsConfiguration.getDependencies();

		final ResolveTask resolveTask = _addTaskResolve(
			project, targetPlatformBundlesConfiguration,
			targetPlatformDistroConfiguration,
			targetPlatformRequirementsConfiguration);

		_configureTasksResolve(project, targetPlatformExtension);

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
				public void doCall(Project afterProject) {
					if (afterProject.equals(singleProject) ||
						afterProject.equals(rootProject)) {

						_configureDependencyManagement(
							afterProject, targetPlatformBomsConfiguration);
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
							afterProject, targetPlatformBomsConfiguration);

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

						ResolveTask resolveTask = GradleUtil.addTask(
							afterProject, RESOLVE_TASK_NAME, ResolveTask.class);

						resolveTask.setIgnoreFailures(
							targetPlatformExtension.isIgnoreResolveFailures());
						resolveTask.onlyIf(_skipIfExecutingParentTaskSpec);
						resolveTask.setBundles(
							targetPlatformBundlesConfiguration);
						resolveTask.setDistro(
							targetPlatformDistroConfiguration);

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

	private static Configuration _addConfigurationTargetPlatformBoms(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_BOMS_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures all the BOMs to import as managed dependencies.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private static Configuration _addConfigurationTargetPlatformBundles(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_BUNDLES_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures all the bundles in addition to the distro to resolve " +
				"against.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private static Configuration _addConfigurationTargetPlatformDependencies(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_DEPENDENCIES_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures all the managed dependencies for the configured " +
				"Liferay target platform.");
		configuration.setVisible(false);

		return configuration;
	}

	private static Configuration _addConfigurationTargetPlatformDistro(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the distro JAR file to use as base for resolving " +
				"against.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private static Configuration _addConfigurationTargetPlatformRequirements(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, TARGET_PLATFORM_REQUIREMENTS_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the list of JAR files to use as run requirements for " +
				"resolving.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private static void _configureDependencyManagement(
		Project project, final Configuration bomsConfiguration) {

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
				project, TARGET_PLATFORM_DEPENDENCIES_CONFIGURATION_NAME));
	}

	private static void _configureTargetPlatformDependencies(
		Project project, TargetPlatformExtension targetPlatformExtension) {

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
			project, TARGET_PLATFORM_DEPENDENCIES_CONFIGURATION_NAME);

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

	private ResolveTask _addTaskResolve(
		Project project, FileCollection bundles, FileCollection distro,
		FileCollection requirements) {

		ResolveTask resolveTask = GradleUtil.addTask(
			project, RESOLVE_TASK_NAME, ResolveTask.class);

		resolveTask.setBundles(bundles);
		resolveTask.setDistro(distro);
		resolveTask.setDescription(
			"Checks whether a set of OSGi bundles can be found to meet all " +
				"the requirements of the current project.");
		resolveTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
		resolveTask.setRequirements(requirements);

		return resolveTask;
	}

	private void _configureTaskResolve(
		ResolveTask resolveTask,
		final TargetPlatformExtension targetPlatformExtension) {

		resolveTask.setIgnoreFailures(
			new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					return targetPlatformExtension.isIgnoreResolveFailures();
				}

			});
	}

	private void _configureTasksResolve(
		Project project,
		final TargetPlatformExtension targetPlatformExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ResolveTask.class,
			new Action<ResolveTask>() {

				@Override
				public void execute(ResolveTask resolveTask) {
					_configureTaskResolve(resolveTask, targetPlatformExtension);
				}

			});
	}

	private static final Spec<Task> _skipIfExecutingParentTaskSpec =
		new SkipIfExecutingParentTaskSpec();

}