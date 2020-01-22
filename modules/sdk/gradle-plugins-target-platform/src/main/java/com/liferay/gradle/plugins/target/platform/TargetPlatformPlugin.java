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

import com.liferay.gradle.plugins.target.platform.extensions.TargetPlatformExtension;
import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;
import com.liferay.gradle.plugins.target.platform.internal.util.TargetPlatformPluginUtil;
import com.liferay.gradle.plugins.target.platform.tasks.DependencyManagementTask;
import com.liferay.gradle.plugins.target.platform.tasks.ResolveTask;

import groovy.lang.Closure;

import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.HelpTasksPlugin;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.util.VersionNumber;

/**
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class TargetPlatformPlugin implements Plugin<Project> {

	public static final String DEPENDENCYMANAGEMENT_TASK_NAME =
		"dependencyManagement";

	public static final String PLATFORM_BNDRUN_FILE_NAME = "platform.bndrun";

	public static final String PLUGIN_NAME = "targetPlatform";

	public static final String RESOLVE_TASK_NAME = "resolve";

	public static final String TARGET_PLATFORM_BOMS_CONFIGURATION_NAME =
		"targetPlatformBoms";

	public static final String TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME =
		"targetPlatformDistro";

	@Override
	@SuppressWarnings("serial")
	public void apply(final Project project) {
		Gradle gradle = project.getGradle();

		VersionNumber versionNumber = VersionNumber.parse(
			gradle.getGradleVersion());

		if (versionNumber.getMajor() < 5) {
			throw new GradleException(
				"This plugin requires at least Gradle 5.x or greater");
		}

		final TargetPlatformExtension targetPlatformExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, TargetPlatformExtension.class);

		final Configuration targetPlatformBomsConfiguration =
			_addConfigurationTargetPlatformBoms(project);

		final Configuration targetPlatformDistroConfiguration =
			_addConfigurationTargetPlatformDistro(project);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					TargetPlatformPluginUtil.configureTargetPlatform(
						project, _configurationNames,
						targetPlatformBomsConfiguration);
				}

			});

		final Set<Project> subprojects =
			targetPlatformExtension.getSubprojects();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project afterProject) {
					boolean hasSubprojects = !subprojects.isEmpty();

					if ((afterProject.equals(project) && !hasSubprojects) ||
						subprojects.contains(afterProject)) {

						_configureAfterProject(
							afterProject, project.getLogger(),
							targetPlatformBomsConfiguration,
							targetPlatformDistroConfiguration,
							targetPlatformExtension);
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

	private DependencyManagementTask _addTaskDependencyManagement(
		Project project) {

		final DependencyManagementTask dependencyManagementTask =
			GradleUtil.addTask(
				project, DEPENDENCYMANAGEMENT_TASK_NAME,
				DependencyManagementTask.class);

		dependencyManagementTask.setDescription(
			"Displays the target platform dependency management declared in " +
				dependencyManagementTask.getProject() + ".");

		dependencyManagementTask.setGroup(HelpTasksPlugin.HELP_GROUP);

		return dependencyManagementTask;
	}

	private ResolveTask _addTaskResolve(Project project) {
		final ResolveTask resolveTask = GradleUtil.addTask(
			project, RESOLVE_TASK_NAME, ResolveTask.class);

		resolveTask.setDescription(
			"Checks whether the project and its runtime dependencies will " +
				"have their requirements met when installed into the Liferay " +
					"portal instance defined by the configured distro.");
		resolveTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		return resolveTask;
	}

	private void _configureAfterProject(
		Project afterProject, Logger logger,
		Configuration targetPlatformBomsConfiguration,
		Configuration targetPlatformDistroConfiguration,
		TargetPlatformExtension targetPlatformExtension) {

		Spec<Project> spec = targetPlatformExtension.getOnlyIf();

		if (!spec.isSatisfiedBy(afterProject)) {
			if (logger.isInfoEnabled()) {
				logger.info("Explicitly excluding {}", afterProject);
			}

			return;
		}

		TargetPlatformPluginUtil.configureTargetPlatform(
			afterProject, _configurationNames, targetPlatformBomsConfiguration);

		Spec<Project> resolveSpec = targetPlatformExtension.getResolveOnlyIf();

		if (resolveSpec.isSatisfiedBy(afterProject)) {
			ResolveTask resolveTask = _addTaskResolve(afterProject);

			Project rootProject = afterProject.getRootProject();

			File bndrunFile = rootProject.file(PLATFORM_BNDRUN_FILE_NAME);

			_configureTaskResolve(
				afterProject, resolveTask, bndrunFile,
				targetPlatformDistroConfiguration);
		}
		else if (logger.isInfoEnabled()) {
			logger.info(
				"Explicitly excluding {} from resolution", afterProject);
		}

		_addTaskDependencyManagement(afterProject);
	}

	private void _configureTaskResolve(
		Project project, ResolveTask resolveTask, File bndrunFile,
		Configuration targetPlatformDistroConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		resolveTask.dependsOn(taskContainer.findByName("assemble"));

		resolveTask.setBndrunFile(bndrunFile);
		resolveTask.setDescription(
			"Resolve a project against the Liferay distro.");
		resolveTask.setDistro(targetPlatformDistroConfiguration);
		resolveTask.setFailOnChanges(false);
		resolveTask.setGroup("verification");
		resolveTask.setReportOptional(false);
	}

	private static final List<String> _configurationNames = Arrays.asList(
		"compile", "compileClasspath", "compileInclude", "compileOnly",
		"default", "implementation", "originalModule", "parentThemes",
		"portalCommonCSS", "runtime", "runtimeClasspath",
		"runtimeImplementation", "runtimeOnly", "testCompileClasspath",
		"testCompileOnly", "testImplementation", "testIntegration",
		"testRuntime", "testRuntimeClasspath", "testRuntimeOnly");

}