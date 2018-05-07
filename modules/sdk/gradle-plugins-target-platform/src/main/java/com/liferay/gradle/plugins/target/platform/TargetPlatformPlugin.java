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
import com.liferay.gradle.plugins.target.platform.tasks.ResolveTask;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementConfigurer;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;
import io.spring.gradle.dependencymanagement.dsl.ImportsHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.FileCollection;
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

	public static final String TARGET_PLATFORM_BUNDLES_CONFIGURATION_NAME =
		"targetPlatformBundles";

	public static final String TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME =
		"targetPlatformDistro";

	public static final String TARGET_PLATFORM_REQUIREMENTS_CONFIGURATION_NAME =
		"targetPlatformRequirements";

	@Override
	public void apply(final Project project) {
		GradleUtil.applyPlugin(project, DependencyManagementPlugin.class);

		final TargetPlatformExtension targetPlatformExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, TargetPlatformExtension.class);

		final Configuration targetPlatformBomsConfiguration =
			_addConfigurationTargetPlatformBoms(project);
		final Configuration targetPlatformBundlesConfiguration =
			_addConfigurationTargetPlatformBundles(project);
		final Configuration targetPlatformDistroConfiguration =
			_addConfigurationTargetPlatformDistro(project);
		final Configuration targetPlatformRequirementsConfiguration =
			_addConfigurationTargetPlatformRequirements(project);

		_addTaskResolve(
			project, targetPlatformBundlesConfiguration,
			targetPlatformDistroConfiguration,
			targetPlatformRequirementsConfiguration);

		_configureTasksResolve(project, targetPlatformExtension);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_addDependenciesBundleAndRequirement(
						project, JavaPlugin.JAR_TASK_NAME,
						project.getDependencies(),
						targetPlatformBundlesConfiguration,
						targetPlatformRequirementsConfiguration);
					_configureDependencyManagement(
						project, targetPlatformBomsConfiguration);
				}

			});

		Gradle gradle = project.getGradle();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project subproject) {
					Set<Project> subprojects =
						targetPlatformExtension.getSubprojects();

					if (subprojects.contains(subproject)) {
						_configureSubproject(
							subproject, project.getDependencies(),
							project.getLogger(),
							targetPlatformBomsConfiguration,
							targetPlatformBundlesConfiguration,
							targetPlatformDistroConfiguration,
							targetPlatformRequirementsConfiguration,
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

	private void _addDependenciesBundleAndRequirement(
		Object object, DependencyHandler dependencyHandler,
		Configuration targetPlatformBundlesConfiguration,
		Configuration targetPlatformRequirementsConfiguration) {

		Dependency dependency = dependencyHandler.create(object);

		GradleUtil.addDependency(
			targetPlatformRequirementsConfiguration, dependency);
		GradleUtil.addDependency(
			targetPlatformBundlesConfiguration, dependency);
	}

	private void _addDependenciesBundleAndRequirement(
		Project project, String taskName, DependencyHandler dependencyHandler,
		Configuration targetPlatformBundlesConfiguration,
		Configuration targetPlatformRequirementsConfiguration) {

		Task task = GradleUtil.getTask(project, taskName);

		FileCollection fileCollection = project.files(task);

		_addDependenciesBundleAndRequirement(
			fileCollection, dependencyHandler,
			targetPlatformBundlesConfiguration,
			targetPlatformRequirementsConfiguration);
	}

	private ResolveTask _addTaskResolve(
		Project project, FileCollection bundles, FileCollection distro,
		FileCollection requirements) {

		final ResolveTask resolveTask = GradleUtil.addTask(
			project, RESOLVE_TASK_NAME, ResolveTask.class);

		resolveTask.onlyIf(_skipIfExecutingParentTaskSpec);
		resolveTask.setBundles(bundles);
		resolveTask.setDistro(distro);
		resolveTask.setDescription(
			"Checks whether a set of OSGi bundles can be found to meet all " +
				"the requirements of the current project.");
		resolveTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
		resolveTask.setRequirements(requirements);

		return resolveTask;
	}

	private void _configureDependencyManagement(
		final Project project,
		final Configuration targetPlatformBomsConfiguration) {

		final DependencyManagementExtension dependencyManagementExtension =
			GradleUtil.getExtension(
				project, DependencyManagementExtension.class);

		GroovyObjectSupport groovyObjectSupport =
			(GroovyObjectSupport)dependencyManagementExtension;

		List<Object> args = new ArrayList<>(_CONFIGURATION_NAMES.length + 1);

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		for (String configurationName : _CONFIGURATION_NAMES) {
			Configuration configuration = configurationContainer.findByName(
				configurationName);

			if (configuration != null) {
				args.add(configuration);
			}
		}

		Closure<Void> closure = new Closure<Void>(project) {

			@SuppressWarnings("unused")
			public void doCall() {
				DependencySet dependencySet =
					targetPlatformBomsConfiguration.getAllDependencies();

				dependencySet.all(
					new Action<Dependency>() {

						@Override
						public void execute(final Dependency dependency) {
							_configureDependencyManagementImportsHandler(
								(DependencyManagementConfigurer)getDelegate(),
								dependency);
						}

					});
			}

		};

		args.add(closure);

		groovyObjectSupport.invokeMethod(
			"configurations", args.toArray(new Object[0]));
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

	private void _configureSubproject(
		Project subproject, DependencyHandler dependencyHandler, Logger logger,
		Configuration targetPlatformBomsConfiguration,
		Configuration targetPlatformBundlesConfiguration,
		Configuration targetPlatformDistroConfiguration,
		Configuration targetPlatformRequirementsConfiguration,
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

		GradleUtil.applyPlugin(subproject, DependencyManagementPlugin.class);

		_configureDependencyManagement(
			subproject, targetPlatformBomsConfiguration);

		spec = targetPlatformExtension.getResolveOnlyIf();

		if (spec.isSatisfiedBy(subproject)) {
			_addDependenciesBundleAndRequirement(
				subproject, dependencyHandler,
				targetPlatformBundlesConfiguration,
				targetPlatformRequirementsConfiguration);

			FileCollection requirements = subproject.files(jarTask);

			_addTaskResolve(
				subproject, targetPlatformBundlesConfiguration,
				targetPlatformDistroConfiguration, requirements);

			_configureTasksResolve(subproject, targetPlatformExtension);
		}
		else if (logger.isInfoEnabled()) {
			logger.info("Explicitly excluding {} from resolution", subproject);
		}
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

	private static final String[] _CONFIGURATION_NAMES = {
		JavaPlugin.COMPILE_CONFIGURATION_NAME, "compileClasspath",
		"compileInclude", "compileOnly", Dependency.DEFAULT_CONFIGURATION,
		"implementation", JavaPlugin.RUNTIME_CONFIGURATION_NAME,
		"runtimeClasspath", "runtimeImplementation", "runtimeOnly",
		"testCompileClasspath", "testCompileOnly", "testIntegration",
		"testImplementation", JavaPlugin.TEST_RUNTIME_CONFIGURATION_NAME,
		"testRuntimeClasspath", "testRuntimeOnly"
	};

	private static final Spec<Task> _skipIfExecutingParentTaskSpec =
		new SkipIfExecutingParentTaskSpec();

}