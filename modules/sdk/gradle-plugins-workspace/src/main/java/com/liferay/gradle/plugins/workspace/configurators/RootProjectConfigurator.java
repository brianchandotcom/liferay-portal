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
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.tasks.CreateTokenTask;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import de.undercouch.gradle.tasks.download.Download;

import groovy.lang.Closure;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.http.HttpHeaders;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;
import org.gradle.api.initialization.Settings;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Compression;
import org.gradle.api.tasks.bundling.Tar;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class RootProjectConfigurator implements Plugin<Project> {

	public static final String BUNDLE_CONFIGURATION_NAME = "bundle";

	public static final String BUNDLE_GROUP = "bundle";

	public static final String CLEAN_TASK_NAME =
		LifecycleBasePlugin.CLEAN_TASK_NAME;

	public static final String CREATE_TOKEN_TASK_NAME = "createToken";

	public static final String DIST_BUNDLE_TAR_TASK_NAME = "distBundleTar";

	public static final String DIST_BUNDLE_TASK_NAME = "distBundle";

	public static final String DIST_BUNDLE_ZIP_TASK_NAME = "distBundleZip";

	public static final String DOWNLOAD_BUNDLE_TASK_NAME = "downloadBundle";

	public static final String INIT_BUNDLE_TASK_NAME = "initBundle";

	/**
	 * @deprecated As of 1.4.0, replaced by {@link
	 *             #RootProjectConfigurator(Settings)}
	 */
	@Deprecated
	public RootProjectConfigurator() {
	}

	public RootProjectConfigurator(Settings settings) {
		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + ".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		GradleUtil.applyPlugin(project, LifecycleBasePlugin.class);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		CreateTokenTask createTokenTask = _addTaskCreateToken(
			project, workspaceExtension);

		Download downloadBundleTask = _addTaskDownloadBundle(
			createTokenTask, workspaceExtension);

		Copy distBundleTask = _addTaskDistBundle(
			project, downloadBundleTask, workspaceExtension);

		Tar distBundleTarTask = _addTaskDistBundle(
			project, DIST_BUNDLE_TAR_TASK_NAME, Tar.class, distBundleTask,
			workspaceExtension);

		distBundleTarTask.setCompression(Compression.GZIP);
		distBundleTarTask.setExtension("tar.gz");

		_addTaskDistBundle(
			project, DIST_BUNDLE_ZIP_TASK_NAME, Zip.class, distBundleTask,
			workspaceExtension);

		_addTaskInitBundle(project, downloadBundleTask, workspaceExtension);
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	public void setDefaultRepositoryEnabled(boolean defaultRepositoryEnabled) {
		_defaultRepositoryEnabled = defaultRepositoryEnabled;
	}

	private Copy _addTaskCopyBundle(
		Project project, String taskName, Download downloadBundleTask,
		final WorkspaceExtension workspaceExtension) {

		Copy copy = GradleUtil.addTask(project, taskName, Copy.class);

		_configureTaskCopyBundleFromConfig(
			copy,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						workspaceExtension.getConfigsDir(),
						workspaceExtension.getEnvironment());
				}

			});

		_configureTaskCopyBundleFromConfig(
			copy,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						workspaceExtension.getConfigsDir(), "common");
				}

			});

		_configureTaskCopyBundleFromDownload(copy, downloadBundleTask);

		_configureTaskCopyBundlePreserveTimestamps(copy);

		copy.dependsOn(downloadBundleTask);

		copy.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					Project project = copy.getProject();

					project.delete(copy.getDestinationDir());
				}

			});

		copy.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);

		return copy;
	}

	private CreateTokenTask _addTaskCreateToken(
		Project project, final WorkspaceExtension workspaceExtension) {

		CreateTokenTask createTokenTask = GradleUtil.addTask(
			project, CREATE_TOKEN_TASK_NAME, CreateTokenTask.class);

		createTokenTask.setDescription("Creates a liferay.com download token.");

		createTokenTask.setEmailAddress(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return workspaceExtension.getBundleTokenEmailAddress();
				}

			});

		createTokenTask.setForce(
			new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					return workspaceExtension.isBundleTokenForce();
				}

			});

		createTokenTask.setGroup(BUNDLE_GROUP);

		createTokenTask.setPassword(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return workspaceExtension.getBundleTokenPassword();
				}

			});

		return createTokenTask;
	}

	private Copy _addTaskDistBundle(
		final Project project, Download downloadBundleTask,
		WorkspaceExtension workspaceExtension) {

		Copy copy = _addTaskCopyBundle(
			project, DIST_BUNDLE_TASK_NAME, downloadBundleTask,
			workspaceExtension);

		_configureTaskDisableUpToDate(copy);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(project.getBuildDir(), "dist");
				}

			});

		copy.setDescription("Assembles the Liferay bundle.");

		return copy;
	}

	private <T extends AbstractArchiveTask> T _addTaskDistBundle(
		Project project, String taskName, Class<T> clazz,
		final Copy distBundleTask,
		final WorkspaceExtension workspaceExtension) {

		T task = GradleUtil.addTask(project, taskName, clazz);

		_configureTaskDisableUpToDate(task);

		task.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String bundleDistRootDirName =
						workspaceExtension.getBundleDistRootDirName();

					if (Validator.isNull(bundleDistRootDirName)) {
						bundleDistRootDirName = "";
					}

					return bundleDistRootDirName;
				}

			},
			new Closure<Void>(task) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.from(distBundleTask);
				}

			});

		task.setBaseName(project.getName());
		task.setDescription("Assembles the Liferay bundle and zips it up.");
		task.setDestinationDir(project.getBuildDir());
		task.setGroup(BUNDLE_GROUP);

		return task;
	}

	private Download _addTaskDownloadBundle(
		final CreateTokenTask createTokenTask,
		final WorkspaceExtension workspaceExtension) {

		Project project = createTokenTask.getProject();

		final Download download = GradleUtil.addTask(
			project, DOWNLOAD_BUNDLE_TASK_NAME, Download.class);

		File destinationDir = new File(
			System.getProperty("user.home"), ".liferay/bundles");

		destinationDir.mkdirs();

		download.dest(destinationDir);

		download.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					if (workspaceExtension.isBundleTokenDownload()) {
						String token = FileUtil.read(
							createTokenTask.getTokenFile());

						download.header(
							HttpHeaders.AUTHORIZATION, "Bearer " + token);
					}
				}

			});

		download.onlyIfNewer(true);
		download.setDescription("Downloads the Liferay bundle zip file.");

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					if (workspaceExtension.isBundleTokenDownload()) {
						download.dependsOn(createTokenTask);
					}

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

					String bundleUrl = workspaceExtension.getBundleUrl();

					bundleUrl = bundleUrl.replace(" ", "%20");

					try {
						download.src(bundleUrl);
					}
					catch (MalformedURLException murle) {
						throw new GradleException(murle.getMessage(), murle);
					}
				}

			});

		return download;
	}

	private Copy _addTaskInitBundle(
		Project project, Download downloadBundleTask,
		final WorkspaceExtension workspaceExtension) {

		Copy copy = _addTaskCopyBundle(
			project, INIT_BUNDLE_TASK_NAME, downloadBundleTask,
			workspaceExtension);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return workspaceExtension.getHomeDir();
				}

			});

		copy.setDescription("Downloads and unzips the bundle.");
		copy.setGroup(BUNDLE_GROUP);

		return copy;
	}

	private void _configureTaskCopyBundleFromConfig(
		Copy copy, Callable<File> dir) {

		copy.from(
			dir,
			new Closure<Void>(copy.getProject()) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.exclude("**/.touch");
				}

			});
	}

	private void _configureTaskCopyBundleFromDownload(
		Copy copy, final Download download) {

		final Project project = copy.getProject();

		final Set<String> rootDirNames = new HashSet<>();

		copy.dependsOn(download);

		copy.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					File destinationDir = copy.getDestinationDir();

					for (String rootDirName : rootDirNames) {
						FileUtil.moveTree(
							new File(destinationDir, rootDirName),
							destinationDir);
					}
				}

			});

		copy.from(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					File dir = download.getDest();

					URL url = (URL)download.getSrc();

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
					copySpec.eachFile(
						new Action<FileCopyDetails>() {

							@Override
							public void execute(
								FileCopyDetails fileCopyDetails) {

								RelativePath relativePath =
									fileCopyDetails.getRelativePath();

								String[] segments = relativePath.getSegments();

								rootDirNames.add(segments[0]);
							}

						});

					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});
	}

	private void _configureTaskCopyBundlePreserveTimestamps(Copy copy) {
		final Set<FileCopyDetails> fileCopyDetailsSet = new HashSet<>();

		copy.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					Logger logger = copy.getLogger();

					for (FileCopyDetails fileCopyDetails : fileCopyDetailsSet) {
						File file = new File(
							copy.getDestinationDir(),
							fileCopyDetails.getPath());

						if (!file.exists()) {
							logger.error(
								"Unable to set last modified time of {}, it " +
									"has not been copied",
								file);

							return;
						}

						boolean success = file.setLastModified(
							fileCopyDetails.getLastModified());

						if (!success) {
							logger.error(
								"Unable to set last modified time of {}", file);
						}
					}
				}

			});

		copy.eachFile(
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					fileCopyDetailsSet.add(fileCopyDetails);
				}

			});
	}

	private void _configureTaskDisableUpToDate(Task task) {
		TaskOutputs taskOutputs = task.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});
	}

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private boolean _defaultRepositoryEnabled;

}