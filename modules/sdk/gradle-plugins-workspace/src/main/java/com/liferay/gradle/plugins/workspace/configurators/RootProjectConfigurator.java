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
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import de.undercouch.gradle.tasks.download.Download;

import groovy.lang.Closure;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.tasks.AbstractCopyTask;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
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

	public static final String BUNDLE_GROUP = "bundle";

	public static final String CLEAN_TASK_NAME = "clean";

	public static final String DIST_BUNDLE_TAR_TASK_NAME = "distBundleTar";

	public static final String DIST_BUNDLE_ZIP_TASK_NAME = "distBundleZip";

	public static final String DOWNLOAD_BUNDLE_TASK_NAME = "downloadBundle";

	public static final String INIT_BUNDLE_TASK_NAME = "initBundle";

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		addTaskClean(project);

		Download downloadBundleTask = addTaskDownloadBundle(
			project, workspaceExtension);

		Tar distBundleTarTask = addTaskDistBundle(
			project, DIST_BUNDLE_TAR_TASK_NAME, Tar.class, downloadBundleTask,
			workspaceExtension);

		distBundleTarTask.setCompression(Compression.GZIP);
		distBundleTarTask.setExtension("tar.gz");

		addTaskDistBundle(
			project, DIST_BUNDLE_ZIP_TASK_NAME, Zip.class, downloadBundleTask,
			workspaceExtension);

		addTaskInitBundle(project, downloadBundleTask, workspaceExtension);
	}

	protected Delete addTaskClean(final Project project) {
		Delete delete = GradleUtil.addTask(
			project, CLEAN_TASK_NAME, Delete.class);

		delete.delete(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return project.getBuildDir();
				}

			});

		delete.setDescription("Deletes the build directory.");

		return delete;
	}

	protected <T extends AbstractArchiveTask> T addTaskDistBundle(
		Project project, String taskName, Class<T> clazz,
		Download downloadBundleTask, WorkspaceExtension workspaceExtension) {

		final T task = GradleUtil.addTask(project, taskName, clazz);

		configureTaskCopyBundle(task, downloadBundleTask, workspaceExtension);

		task.setBaseName(project.getName());
		task.setDestinationDir(project.getBuildDir());
		task.setGroup(BUNDLE_GROUP);
		task.setIncludeEmptyDirs(false);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					if (Validator.isNotNull(task.getDescription())) {
						return;
					}

					task.setDescription(
						"Assembles the Liferay bundle and zips it up into '" +
							project.relativePath(task.getArchivePath()) + "'.");
				}

			});

		return task;
	}

	protected Download addTaskDownloadBundle(
		Project project, final WorkspaceExtension workspaceExtension) {

		final Download download = GradleUtil.addTask(
			project, DOWNLOAD_BUNDLE_TASK_NAME, Download.class);

		File destinationDir = new File(
			System.getProperty("user.home"), ".liferay/bundles");

		destinationDir.mkdirs();

		download.dest(destinationDir);

		download.onlyIfNewer(true);
		download.setDescription("Downloads the Liferay bundle zip file.");

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					Object src = download.getSrc();

					if (src != null) {
						if (src instanceof List<?>) {
							List<?> srcList = (List<?>)src;

							if (!srcList.isEmpty()) {
								return;
							}
						}
						else {
							return;
						}
					}

					try {
						download.src(workspaceExtension.getBundleUrl());
					}
					catch (MalformedURLException murle) {
						throw new GradleException(murle.getMessage(), murle);
					}
				}

			});

		return download;
	}

	protected Copy addTaskInitBundle(
		Project project, Download downloadBundleTask,
		final WorkspaceExtension workspaceExtension) {

		final Copy copy = GradleUtil.addTask(
			project, INIT_BUNDLE_TASK_NAME, Copy.class);

		configureTaskCopyBundle(copy, downloadBundleTask, workspaceExtension);

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
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public File doCall() {
					return workspaceExtension.getHomeDir();
				}

			});

		copy.setGroup(BUNDLE_GROUP);
		copy.setIncludeEmptyDirs(false);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					if (Validator.isNotNull(copy.getDescription())) {
						return;
					}

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
		final Download downloadBundleTask,
		final WorkspaceExtension workspaceExtension) {

		final Project project = abstractCopyTask.getProject();

		abstractCopyTask.dependsOn(downloadBundleTask);

		abstractCopyTask.from(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					File dir = downloadBundleTask.getDest();

					URL url = (URL)downloadBundleTask.getSrc();

					String fileName = url.toString();

					fileName = fileName.substring(
						fileName.lastIndexOf('/') + 1);

					File file = new File(dir, fileName);

					if (fileName.endsWith(".tar.gz")) {
						return project.tarTree(file);
					}
					else {
						return project.zipTree(file);
					}
				}

			},
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});

		abstractCopyTask.from(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public FileCollection doCall() {
					Map<String, Object> args = new HashMap<>();

					args.put("dir", workspaceExtension.getConfigsDir());
					args.put("exclude", "**/.touch");

					List<String> includes = Arrays.asList(
						"common/", workspaceExtension.getEnvironment() + "/");

					args.put("includes", includes);

					return project.fileTree(args);
				}

			},
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});
	}

}