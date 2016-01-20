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

package com.liferay.gradle.plugins.workspace.configurators;

import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.util.GradleUtil;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.lang.Closure;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.tasks.AbstractCopyTask;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Compression;
import org.gradle.api.tasks.bundling.Tar;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class RootProjectConfigurator implements Plugin<Project> {

	public static final String BUNDLE_CONFIGURATION_NAME = "bundle";

	public static final String DIST_BUNDLE_TAR_TASK_NAME = "distBundleTar";

	public static final String DIST_BUNDLE_ZIP_TASK_NAME = "distBundleZip";

	public static final String INIT_BUNDLE_TASK_NAME = "initBundle";

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		Configuration bundleConfiguration = addConfigurationBundle(
			project, workspaceExtension);

		addRepositoryBundle(project, workspaceExtension);

		Tar distBundleTarTask = addTaskDistBundle(
			project, DIST_BUNDLE_TAR_TASK_NAME, Tar.class, bundleConfiguration,
			workspaceExtension);

		distBundleTarTask.setCompression(Compression.GZIP);
		distBundleTarTask.setExtension("tar.gz");

		addTaskDistBundle(
			project, DIST_BUNDLE_ZIP_TASK_NAME, Zip.class, bundleConfiguration,
			workspaceExtension);

		addTaskInitBundle(project, bundleConfiguration, workspaceExtension);
	}

	protected Configuration addConfigurationBundle(
		final Project project, final WorkspaceExtension workspaceExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, BUNDLE_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					GradleUtil.addDependency(
						project, BUNDLE_CONFIGURATION_NAME,
						workspaceExtension.getBundleArtifactGroup(),
						workspaceExtension.getBundleArtifactName(),
						workspaceExtension.getBundleArtifactVersion());
				}

			});

		configuration.setDescription(
			"Configures the Liferay bundle to use for your project.");

		return configuration;
	}

	protected void addRepositoryBundle(
		Project project, final WorkspaceExtension workspaceExtension) {

		GradleUtil.addMavenRepository(
			project,
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return workspaceExtension.getBundleMavenUrl();
				}

			});
	}

	protected <T extends AbstractArchiveTask> T addTaskDistBundle(
		Project project, String taskName, Class<T> clazz,
		Configuration bundleConfiguration,
		WorkspaceExtension workspaceExtension) {

		final T task = GradleUtil.addTask(project, taskName, clazz);

		configureTaskCopyBundle(task, bundleConfiguration, workspaceExtension);

		task.setBaseName(project.getName());
		task.setDestinationDir(project.getBuildDir());
		task.setIncludeEmptyDirs(false);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					task.setDescription(
						"Assembles the Liferay bundle and zips it up into '" +
							project.relativePath(task.getArchivePath()) + "'.");
				}

			});

		return task;
	}

	protected Copy addTaskInitBundle(
		Project project, Configuration bundleConfiguration,
		final WorkspaceExtension workspaceExtension) {

		final Copy copy = GradleUtil.addTask(
			project, INIT_BUNDLE_TASK_NAME, Copy.class);

		configureTaskCopyBundle(copy, bundleConfiguration, workspaceExtension);

		copy.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					Project project = copy.getProject();

					project.delete(copy.getDestinationDir());
				}

			});

		copy.into(
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public File doCall() {
					return workspaceExtension.getHomeDir();
				}

			});

		copy.setIncludeEmptyDirs(false);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					copy.setDescription(
						"Downloads and unzips the bundle into '" +
							project.relativePath(copy.getDestinationDir()) +
								"'.");
				}

			});

		return copy;
	}

	protected void configureTaskCopyBundle(
		final AbstractCopyTask abstractCopyTask,
		final Configuration bundleConfiguration,
		final WorkspaceExtension workspaceExtension) {

		abstractCopyTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					File file = bundleConfiguration.getSingleFile();

					GradleUtil.setProperty(
						task, _BUNDLE_FILE_PROPERTY_NAME, file);
				}

			});

		abstractCopyTask.from(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					Project project = abstractCopyTask.getProject();

					if (!abstractCopyTask.hasProperty(
							_BUNDLE_FILE_PROPERTY_NAME)) {

						return project.files();
					}

					File file = (File)abstractCopyTask.property(
						_BUNDLE_FILE_PROPERTY_NAME);

					String fileName = file.getName();

					if (fileName.endsWith(".tar.gz")) {
						return project.tarTree(file);
					}
					else {
						return project.zipTree(file);
					}
				}

			},
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});

		abstractCopyTask.from(
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public FileCollection doCall() {
					Project project = abstractCopyTask.getProject();

					Map<String, Object> args = new HashMap<>();

					args.put("dir", workspaceExtension.getConfigsDir());
					args.put("exclude", "**/.touch");

					List<String> includes = Arrays.asList(
						"common/", workspaceExtension.getEnvironment() + "/");

					args.put("includes", includes);

					return project.fileTree(args);
				}

			},
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});
	}

	private static final String _BUNDLE_FILE_PROPERTY_NAME = "bundleFile";

}