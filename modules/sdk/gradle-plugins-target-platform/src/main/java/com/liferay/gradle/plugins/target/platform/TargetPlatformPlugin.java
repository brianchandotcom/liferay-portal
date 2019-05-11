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
import com.liferay.gradle.plugins.target.platform.internal.util.SkipIfExecutingParentTaskSpec;
import com.liferay.gradle.plugins.target.platform.internal.util.TargetPlatformPluginUtil;
import com.liferay.gradle.plugins.target.platform.tasks.ResolveTask;

import groovy.lang.Closure;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;

import java.util.Arrays;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 */
public class TargetPlatformPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "targetPlatform";

	public static final String RESOLVE_TASK_NAME = "resolve";

	public static final String TARGET_PLATFORM_BOMS_CONFIGURATION_NAME =
		"targetPlatformBoms";

	public static final String TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME =
		"targetPlatformDistro";

	@Override
	@SuppressWarnings("serial")
	public void apply(final Project project) {
		GradleUtil.applyPlugin(project, DependencyManagementPlugin.class);

		final TargetPlatformExtension targetPlatformExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, TargetPlatformExtension.class);

		final Configuration targetPlatformBomsConfiguration =
			_addConfigurationTargetPlatformBoms(project);

		final Configuration targetPlatformDistroConfiguration =
			_addConfigurationTargetPlatformDistro(project);

		ResolveTask resolveTask = _addTaskResolve(project);

		_configureTaskResolve(project, resolveTask);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					TargetPlatformPluginUtil.configureDependencyManagement(
						project, targetPlatformBomsConfiguration,
						_configurationNames);
				}

			});

		Set<Project> subprojects = targetPlatformExtension.getSubprojects();

		for (Project subproject : subprojects) {
			GradleUtil.applyPlugin(
				subproject, DependencyManagementPlugin.class);
		}

		Gradle gradle = project.getGradle();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project subproject) {
					if (subprojects.contains(subproject)) {
						_configureSubproject(
							subproject, project.getDependencies(),
							project.getLogger(),
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

	private ResolveTask _addTaskResolve(Project project) {
		final ResolveTask resolveTask = GradleUtil.addTask(
			project, RESOLVE_TASK_NAME, ResolveTask.class);

		resolveTask.onlyIf(_skipIfExecutingParentTaskSpec);
		resolveTask.setDescription(
			"Checks whether a set of OSGi bundles can be found to meet all " +
				"the requirements of the current project.");
		resolveTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		return resolveTask;
	}

	private void _configureSubproject(
		Project subproject, DependencyHandler dependencyHandler, Logger logger,
		Configuration targetPlatformBomsConfiguration,
		Configuration targetPlatformDistroConfiguration,
		TargetPlatformExtension targetPlatformExtension) {

		TaskContainer taskContainer = subproject.getTasks();

		Task jarTask = taskContainer.findByName(JavaPlugin.JAR_TASK_NAME);

		if (!(jarTask instanceof Jar)) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"Excluding {} because it is not a valid Java project",
					subproject);
			}

			return;
		}

		Spec<Project> spec = targetPlatformExtension.getOnlyIf();

		if (!spec.isSatisfiedBy(subproject)) {
			if (logger.isInfoEnabled()) {
				logger.info("Explicitly excluding {}", subproject);
			}

			return;
		}

		TargetPlatformPluginUtil.configureDependencyManagement(
			subproject, targetPlatformBomsConfiguration, _configurationNames);

		spec = targetPlatformExtension.getResolveOnlyIf();

		if (spec.isSatisfiedBy(subproject)) {
			ResolveTask resolveTask = _addTaskResolve(subproject);

			_configureTaskResolve(subproject, resolveTask);
		}
		else if (logger.isInfoEnabled()) {
			logger.info("Explicitly excluding {} from resolution", subproject);
		}
	}

	private void _configureTaskResolve(
		Project project, ResolveTask resolveTask) {

		/**
		 * bundle {
		 includeTransitiveDependencies true
		 buildPathConfigurations 'compileClasspath'
		 instruction "-fixupmessages.extra",
		 '"annotations are deprecated";is:=warning',
		 '"In component <name not yet determined>...";is:=warning',
		 '"No interface specified on ...";is:=warning',
		 '"Unable to determine whether the annotation ...";is:=warning'
		 instruction "-check", "EXPORTS"
		 }
		 */

		/**
		 * task resolve(type: Resolve, dependsOn: "deploy", overwrite: true) {
		 description = "Resolve a project against the Liferay Distro"
		 group = "verification"
		 bndrun = file("${rootProject.projectDir}/bnd.bndrun")
		 failOnChanges false
		 reportOptional false
		 bundles = [project.jar.archivePath, project.configurations.default]
		 project.ext.liferayDistro = "${rootProject.configurations.targetPlatformDistro.files.first()};version=file" <-----
		 }
		 */
		TaskContainer taskContainer = project.getTasks();

		resolveTask.dependsOn(taskContainer.findByName("assemble"));

		resolveTask.setDescription(
			"Resolve a project against the Liferay distro");
		resolveTask.setGroup("verification");
		resolveTask.setBndrunFile(project.file(""));
		resolveTask.setFailOnChanges(false);
		resolveTask.setReportOptional(false);
	}

	private static final Iterable<String> _configurationNames = Arrays.asList(
		"compile", "compileClasspath", "compileInclude", "compileOnly",
		"default", "implementation", "originalModule", "parentThemes",
		"portalCommonCSS", "runtime", "runtimeClasspath",
		"runtimeImplementation", "runtimeOnly", "testCompileClasspath",
		"testCompileOnly", "testIntegration", "testImplementation",
		"testRuntime", "testRuntimeClasspath", "testRuntimeOnly");
	private static final Spec<Task> _skipIfExecutingParentTaskSpec =
		new SkipIfExecutingParentTaskSpec();

}