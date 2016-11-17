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

import com.liferay.gradle.plugins.baseline.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

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
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.ReportingBasePlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
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
			jar);

		final BaselineTask baselineTask = _addTaskBaseline(
			jar, baselineConfigurationExtension);

		_configureTasksBaseline(project);

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
		final AbstractArchiveTask newJarTask) {

		Configuration configuration = GradleUtil.addConfiguration(
			newJarTask.getProject(), BASELINE_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesBaseline(newJarTask);
				}

			});

		configuration.setDescription(
			"Configures the previous released version of this project for " +
				"baselining.");
		configuration.setVisible(false);

		ResolutionStrategy resolutionStrategy =
			configuration.getResolutionStrategy();

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

		return configuration;
	}

	private void _addDependenciesBaseline(AbstractArchiveTask newJarTask) {
		Project project = newJarTask.getProject();

		GradleUtil.addDependency(
			project, BASELINE_CONFIGURATION_NAME,
			String.valueOf(project.getGroup()), newJarTask.getBaseName(),
			"(," + newJarTask.getVersion() + ")", false);
	}

	private BaselineTask _addTaskBaseline(
		final AbstractArchiveTask newJarTask,
		final BaselineConfigurationExtension baselineConfigurationExtension) {

		final Project project = newJarTask.getProject();

		BaselineTask baselineTask = GradleUtil.addTask(
			project, BASELINE_TASK_NAME, BaselineTask.class);

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
						GradleUtil.isFromMavenLocal(project, oldJarFile)) {

						throw new GradleException(
							"Please delete " + oldJarFile.getParent() +
								" and try again");
					}
				}

			});

		File bndFile = project.file("bnd.bnd");

		if (bndFile.exists()) {
			baselineTask.setBndFile(bndFile);
		}

		baselineTask.setDescription(
			"Compares the public API of this project with the public API of " +
				"the previous released version, if found.");
		baselineTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

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
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					return GradleUtil.getSrcDir(sourceSet.getResources());
				}

			});

		return baselineTask;
	}

	private void _configureTaskBaseline(BaselineTask baselineTask) {
		String ignoreFailures = GradleUtil.getTaskPrefixedProperty(
			baselineTask, "ignoreFailures");

		if (Validator.isNotNull(ignoreFailures)) {
			baselineTask.setIgnoreFailures(
				Boolean.parseBoolean(ignoreFailures));
		}
	}

	private void _configureTaskBaseline(
		BaselineTask baselineTask, final AbstractArchiveTask newJarTask,
		final FileCollection oldJarFileCollection,
		BaselineConfigurationExtension baselineConfigurationExtension) {

		VersionNumber lowestBaselineVersionNumber = VersionNumber.parse(
			baselineConfigurationExtension.getLowestBaselineVersion());
		VersionNumber versionNumber = VersionNumber.parse(
			newJarTask.getVersion());

		if (lowestBaselineVersionNumber.compareTo(versionNumber) >= 0) {
			baselineTask.setEnabled(false);

			return;
		}

		baselineTask.dependsOn(newJarTask);

		baselineTask.setOldJarFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return oldJarFileCollection.getSingleFile();
				}

			});
	}

	private void _configureTasksBaseline(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaselineTask.class,
			new Action<BaselineTask>() {

				@Override
				public void execute(BaselineTask baselineTask) {
					_configureTaskBaseline(baselineTask);
				}

			});
	}

}