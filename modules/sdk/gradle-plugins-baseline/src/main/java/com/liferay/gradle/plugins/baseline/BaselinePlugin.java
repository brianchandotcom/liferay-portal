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

package com.liferay.gradle.plugins.baseline;

import aQute.bnd.version.Version;

import com.liferay.gradle.plugins.baseline.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ComponentSelection;
import org.gradle.api.artifacts.ComponentSelectionRules;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.ReportingBasePlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
public class BaselinePlugin implements Plugin<Project> {

	public static final String BASELINE_CONFIGURATION_NAME = "baseline";

	public static final String BASELINE_TASK_NAME = "baseline";

	public static final String EXTENSION_NAME = "baselineConfiguration";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);
		GradleUtil.applyPlugin(project, ReportingBasePlugin.class);

		final BaselineConfigurationExtension baselineConfigurationExtension =
			GradleUtil.addExtension(
				project, EXTENSION_NAME, BaselineConfigurationExtension.class);

		final Jar jar = (Jar)GradleUtil.getTask(
			project, JavaPlugin.JAR_TASK_NAME);

		final Configuration baselineConfiguration = _addConfigurationBaseline(
			jar, baselineConfigurationExtension);

		final BaselineTask baselineTask = _addTaskBaseline(jar);

		_configureTasksBaseline(project, baselineConfigurationExtension);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskBaseline(
						baselineTask, jar, baselineConfiguration,
						baselineConfigurationExtension);
				}

			});
	}

	private Configuration _addConfigurationBaseline(
		final AbstractArchiveTask newJarTask,
		final BaselineConfigurationExtension baselineConfigurationExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			newJarTask.getProject(), BASELINE_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					Dependency dependency = _createDependencyBaseline(
						newJarTask,
						baselineConfigurationExtension.getLowestMajorVersion());

					dependencySet.add(dependency);
				}

			});

		configuration.setDescription(
			"Configures the previous released version of this project for " +
				"baselining.");

		_configureConfigurationBaseline(configuration);

		return configuration;
	}

	private BaselineTask _addTaskBaseline(AbstractArchiveTask newJarTask) {
		BaselineTask baselineTask = _addTaskBaseline(
			newJarTask, BASELINE_TASK_NAME);

		baselineTask.setDescription(
			"Compares the public API of this project with the public API of " +
				"the previous released version, if found.");
		baselineTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		return baselineTask;
	}

	private BaselineTask _addTaskBaseline(
		AbstractArchiveTask newJarTask, int majorVersion) {

		BaselineTask baselineTask = _addTaskBaseline(
			newJarTask, BASELINE_TASK_NAME + majorVersion);

		baselineTask.dependsOn(newJarTask);

		baselineTask.setDescription(
			"Compares the public API of this project with the public API of " +
				"the previous released version in the " + majorVersion +
					".x series, if found.");

		Project project = baselineTask.getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Dependency dependency = _createDependencyBaseline(
			newJarTask, majorVersion);

		Configuration baselineConfiguration =
			configurationContainer.detachedConfiguration(dependency);

		_configureConfigurationBaseline(baselineConfiguration);

		baselineTask.setBaselineConfiguration(baselineConfiguration);

		TaskOutputs taskOutputs = baselineTask.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});

		return baselineTask;
	}

	private BaselineTask _addTaskBaseline(
		final AbstractArchiveTask newJarTask, String taskName) {

		Project project = newJarTask.getProject();

		BaselineTask baselineTask = GradleUtil.addTask(
			project, taskName, BaselineTask.class);

		File bndFile = project.file("bnd.bnd");

		if (bndFile.exists()) {
			baselineTask.setBndFile(bndFile);
		}

		baselineTask.setNewJarFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return newJarTask.getArchivePath();
				}

			});

		baselineTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						newJarTask.getProject(),
						SourceSet.MAIN_SOURCE_SET_NAME);

					return GradleUtil.getSrcDir(sourceSet.getResources());
				}

			});

		return baselineTask;
	}

	private void _configureConfigurationBaseline(
		Configuration baselineConfiguration) {

		baselineConfiguration.setTransitive(false);
		baselineConfiguration.setVisible(false);

		ResolutionStrategy resolutionStrategy =
			baselineConfiguration.getResolutionStrategy();

		ComponentSelectionRules componentSelectionRules =
			resolutionStrategy.getComponentSelection();

		componentSelectionRules.all(
			new Action<ComponentSelection>() {

				@Override
				public void execute(ComponentSelection componentSelection) {
					ModuleComponentIdentifier moduleComponentIdentifier =
						componentSelection.getCandidate();

					String version = moduleComponentIdentifier.getVersion();

					if (version.endsWith("-SNAPSHOT")) {
						componentSelection.reject("no snapshots are allowed");
					}
				}

			});

		resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS);
		resolutionStrategy.cacheDynamicVersionsFor(0, TimeUnit.SECONDS);
	}

	private void _configureTaskBaseline(
		BaselineTask baselineTask, AbstractArchiveTask newJarTask,
		Configuration baselineConfiguration,
		BaselineConfigurationExtension baselineConfigurationExtension) {

		VersionNumber lowestBaselineVersionNumber = VersionNumber.parse(
			baselineConfigurationExtension.getLowestBaselineVersion());
		VersionNumber versionNumber = VersionNumber.parse(
			newJarTask.getVersion());

		if (lowestBaselineVersionNumber.compareTo(versionNumber) >= 0) {
			baselineTask.setEnabled(false);

			return;
		}

		Integer lowestMajorVersion =
			baselineConfigurationExtension.getLowestMajorVersion();

		if (lowestMajorVersion != null) {
			int maxMajorVersion = versionNumber.getMajor();

			if ((versionNumber.getMinor() == 0) &&
				(versionNumber.getMicro() == 0)) {

				maxMajorVersion--;
			}

			if (maxMajorVersion >= (lowestMajorVersion + 1)) {
				baselineTask.setIgnoreExcessiveVersionIncreases(true);
			}

			for (int majorVersion = lowestMajorVersion + 1;
				 majorVersion <= maxMajorVersion; majorVersion++) {

				BaselineTask majorVersionBaselineTask = _addTaskBaseline(
					newJarTask, majorVersion);

				if (majorVersion < maxMajorVersion) {
					majorVersionBaselineTask.setIgnoreExcessiveVersionIncreases(
						true);
				}

				baselineTask.dependsOn(majorVersionBaselineTask);
			}
		}
		else if (baselineConfigurationExtension.
					isLowestMajorVersionRequired()) {

			throw new GradleException(
				"Please configure a lowest major version for " +
					baselineTask.getProject());
		}

		baselineTask.dependsOn(newJarTask);

		baselineTask.setBaselineConfiguration(baselineConfiguration);
	}

	private void _configureTaskBaseline(
		BaselineTask baselineTask,
		final BaselineConfigurationExtension baselineConfigurationExtension) {

		baselineTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					if (baselineConfigurationExtension.isAllowMavenLocal()) {
						return;
					}

					BaselineTask baselineTask = (BaselineTask)task;

					File oldJarFile = baselineTask.getOldJarFile();

					if ((oldJarFile != null) &&
						GradleUtil.isFromMavenLocal(
							task.getProject(), oldJarFile)) {

						throw new GradleException(
							"Please delete " + oldJarFile.getParent() +
								" and try again");
					}
				}

			});

		String ignoreFailures = GradleUtil.getTaskPrefixedProperty(
			baselineTask, "ignoreFailures");

		if (Validator.isNotNull(ignoreFailures)) {
			baselineTask.setIgnoreFailures(
				Boolean.parseBoolean(ignoreFailures));
		}

		Project project = baselineTask.getProject();

		String reportLevel = GradleUtil.getProperty(
			project, "baseline.jar.report.level", "standard");

		boolean reportLevelIsDiff = reportLevel.equals("diff");
		boolean reportLevelIsPersist = reportLevel.equals("persist");

		boolean reportDiff = false;

		if (reportLevelIsDiff || reportLevelIsPersist) {
			reportDiff = true;
		}

		baselineTask.setReportDiff(reportDiff);

		boolean reportOnlyDirtyPackages = GradleUtil.getProperty(
			project, "baseline.jar.report.only.dirty.packages", true);

		baselineTask.setReportOnlyDirtyPackages(reportOnlyDirtyPackages);
	}

	private void _configureTasksBaseline(
		Project project,
		final BaselineConfigurationExtension baselineConfigurationExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaselineTask.class,
			new Action<BaselineTask>() {

				@Override
				public void execute(BaselineTask baselineTask) {
					_configureTaskBaseline(
						baselineTask, baselineConfigurationExtension);
				}

			});
	}

	private Dependency _createDependencyBaseline(
		AbstractArchiveTask newJarTask, Integer majorVersion) {

		Project project = newJarTask.getProject();

		DependencyHandler dependencyHandler = project.getDependencies();

		Map<String, String> args = new HashMap<>();

		args.put("group", String.valueOf(project.getGroup()));
		args.put("name", newJarTask.getBaseName());

		String version = null;

		if (majorVersion != null) {
			StringBuilder sb = new StringBuilder();

			sb.append('[');
			sb.append(majorVersion);
			sb.append(".0.0,");
			sb.append(majorVersion + 1);
			sb.append(".0.0)");

			version = sb.toString();
		}
		else {
			version = "(," + newJarTask.getVersion() + ")";

			if (newJarTask.getVersion() != null) {
				Version newVersion = null;

				try {
					newVersion = new Version(newJarTask.getVersion());
				}
				catch (IllegalArgumentException illegalArgumentException) {
					Logger logger = project.getLogger();

					if (logger.isWarnEnabled()) {
						logger.warn(
							"Unable to parse version {}",
							newJarTask.getVersion());
					}
				}

				if ((newVersion != null) &&
					(newVersion.getQualifier() == null) &&
					(newVersion.getMicro() > 0)) {

					StringBuilder sb = new StringBuilder();

					sb.append(newVersion.getMajor());
					sb.append('.');
					sb.append(newVersion.getMinor());
					sb.append('.');
					sb.append(newVersion.getMicro() - 1);

					version = sb.toString();
				}
			}
		}

		args.put("version", version);

		return dependencyHandler.create(args);
	}

}