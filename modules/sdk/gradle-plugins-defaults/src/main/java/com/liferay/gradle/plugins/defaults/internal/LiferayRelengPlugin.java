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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.LiferayAntPlugin;
import com.liferay.gradle.plugins.LiferayThemePlugin;
import com.liferay.gradle.plugins.cache.CacheExtension;
import com.liferay.gradle.plugins.cache.CachePlugin;
import com.liferay.gradle.plugins.cache.WriteDigestTask;
import com.liferay.gradle.plugins.cache.task.TaskCache;
import com.liferay.gradle.plugins.cache.task.TaskCacheApplicator;
import com.liferay.gradle.plugins.change.log.builder.BuildChangeLogTask;
import com.liferay.gradle.plugins.change.log.builder.ChangeLogBuilderPlugin;
import com.liferay.gradle.plugins.defaults.LiferayOSGiDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.LiferayThemeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GitRepo;
import com.liferay.gradle.plugins.defaults.internal.util.GitUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.internal.util.LiferayRelengUtil;
import com.liferay.gradle.plugins.defaults.tasks.MergeFilesTask;
import com.liferay.gradle.plugins.defaults.tasks.ReplaceRegexTask;
import com.liferay.gradle.plugins.defaults.tasks.WriteArtifactPublishCommandsTask;
import com.liferay.gradle.plugins.defaults.tasks.WritePropertiesTask;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayRelengPlugin implements Plugin<Project> {

	public static final String CLEAN_ARTIFACTS_PUBLISH_COMMANDS_TASK_NAME =
		"cleanArtifactsPublishCommands";

	public static final Plugin<Project> INSTANCE = new LiferayRelengPlugin();

	public static final String MERGE_ARTIFACTS_PUBLISH_COMMANDS =
		"mergeArtifactsPublishCommands";

	public static final String PRINT_DEPENDENT_ARTIFACT_TASK_NAME =
		"printDependentArtifact";

	public static final String PRINT_STALE_ARTIFACT_TASK_NAME =
		"printStaleArtifact";

	public static final String RECORD_ARTIFACT_TASK_NAME = "recordArtifact";

	public static final String UPDATE_VERSION_TASK_NAME = "updateVersion";

	public static final String WRITE_ARTIFACT_PUBLISH_COMMANDS =
		"writeArtifactPublishCommands";

	@Override
	public void apply(final Project project) {
		File relengDir = LiferayRelengUtil.getRelengDir(project);

		if (relengDir == null) {
			return;
		}

		GradleUtil.applyPlugin(project, ChangeLogBuilderPlugin.class);
		GradleUtil.applyPlugin(project, MavenPlugin.class);

		final BuildChangeLogTask buildChangeLogTask =
			(BuildChangeLogTask)GradleUtil.getTask(
				project, ChangeLogBuilderPlugin.BUILD_CHANGE_LOG_TASK_NAME);

		final WritePropertiesTask recordArtifactTask = _addTaskRecordArtifact(
			project, relengDir);

		Delete cleanArtifactsPublishCommandsTask =
			_addRootTaskCleanArtifactsPublishCommands(project.getGradle());

		MergeFilesTask mergeArtifactsPublishCommandsTask =
			_addRootTaskMergeArtifactsPublishCommands(
				cleanArtifactsPublishCommandsTask);

		WriteArtifactPublishCommandsTask writeArtifactPublishCommandsTask =
			_addTaskWriteArtifactPublishCommands(
				project, recordArtifactTask, cleanArtifactsPublishCommandsTask,
				mergeArtifactsPublishCommandsTask);

		mergeArtifactsPublishCommandsTask.mustRunAfter(
			writeArtifactPublishCommandsTask);

		_addTaskPrintStaleArtifact(project, recordArtifactTask);

		_addTaskPrintDependentArtifact(project);

		_configureLiferayRelengProperties(project);
		_configureTaskBuildChangeLog(buildChangeLogTask, relengDir);
		_configureTaskUploadArchives(project, recordArtifactTask);

		GradleUtil.withPlugin(
			project, JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskProcessResources(project, buildChangeLogTask);
				}

			});
	}

	protected static final String RELENG_IGNORE_FILE_NAME =
		".lfrbuild-releng-ignore";

	private LiferayRelengPlugin() {
	}

	private Delete _addRootTaskCleanArtifactsPublishCommands(Gradle gradle) {
		StartParameter startParameter = gradle.getStartParameter();

		Project project = GradleUtil.getProject(
			gradle.getRootProject(), startParameter.getCurrentDir());

		TaskContainer taskContainer = project.getTasks();

		Delete delete = (Delete)taskContainer.findByName(
			CLEAN_ARTIFACTS_PUBLISH_COMMANDS_TASK_NAME);

		if (delete != null) {
			return delete;
		}

		delete = GradleUtil.addTask(
			project, CLEAN_ARTIFACTS_PUBLISH_COMMANDS_TASK_NAME, Delete.class);

		delete.delete(
			new File(project.getBuildDir(), "artifacts-publish-commands"));
		delete.setDescription(
			"Deletes the temporary directory that contains the artifacts " +
				"publish commands.");

		return delete;
	}

	private MergeFilesTask _addRootTaskMergeArtifactsPublishCommands(
		Delete cleanArtifactsPublishCommandsTask) {

		Project rootProject = cleanArtifactsPublishCommandsTask.getProject();

		TaskContainer taskContainer = rootProject.getTasks();

		MergeFilesTask mergeFilesTask =
			(MergeFilesTask)taskContainer.findByName(
				MERGE_ARTIFACTS_PUBLISH_COMMANDS);

		if (mergeFilesTask != null) {
			return mergeFilesTask;
		}

		mergeFilesTask = GradleUtil.addTask(
			rootProject, MERGE_ARTIFACTS_PUBLISH_COMMANDS,
			MergeFilesTask.class);

		File dir = GradleUtil.toFile(
			rootProject,
			CollectionUtils.first(
				cleanArtifactsPublishCommandsTask.getDelete()));

		mergeFilesTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					MergeFilesTask mergeFilesTask = (MergeFilesTask)task;

					Logger logger = mergeFilesTask.getLogger();

					File file = mergeFilesTask.getOutputFile();

					if (file.exists()) {
						boolean success = file.setExecutable(true);

						if (!success) {
							logger.error(
								"Unable to set the owner's execute " +
									"permission for {}",
								file);
						}

						if (logger.isQuietEnabled()) {
							logger.quiet(
								"Artifacts publish commands written in {}.",
								file);
						}
					}
					else {
						if (logger.isQuietEnabled()) {
							logger.quiet(
								"No artifacts publish commands are available.");
						}
					}
				}

			});

		mergeFilesTask.setDescription("Merges the artifacts publish commands.");
		mergeFilesTask.setHeader(
			"#!/bin/bash" + System.lineSeparator() + System.lineSeparator() +
				"set -e" + System.lineSeparator());

		mergeFilesTask.setInputFiles(
			new File(dir, WRITE_ARTIFACT_PUBLISH_COMMANDS + "-step1.sh"),
			new File(dir, WRITE_ARTIFACT_PUBLISH_COMMANDS + "-step2.sh"),
			new File(dir, WRITE_ARTIFACT_PUBLISH_COMMANDS + "-step3.sh"));

		mergeFilesTask.setOutputFile(
			new File(dir, "artifacts-publish-commands.sh"));

		TaskOutputs taskOutputs = mergeFilesTask.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});

		return mergeFilesTask;
	}

	private Task _addTaskPrintDependentArtifact(Project project) {
		Task task = project.task(PRINT_DEPENDENT_ARTIFACT_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					File projectDir = project.getProjectDir();

					System.out.println(projectDir.getAbsolutePath());
				}

			});

		Spec<Task> onlyIfSpec = new Spec<Task>() {

			@Override
			public boolean isSatisfiedBy(Task task) {
				Project project = task.getProject();

				String ignoreProjectRegex = GradleUtil.getTaskPrefixedProperty(
					task, "ignore.project.regex");

				if (Validator.isNotNull(ignoreProjectRegex)) {
					Pattern pattern = Pattern.compile(ignoreProjectRegex);

					Matcher matcher = pattern.matcher(project.getName());

					if (matcher.find()) {
						return false;
					}
				}

				if (GradlePluginsDefaultsUtil.isTestProject(project)) {
					return false;
				}

				if (!LiferayRelengUtil.hasUnpublishedDependencies(project)) {
					return false;
				}

				return true;
			}

		};

		task.onlyIf(onlyIfSpec);

		task.setDescription(
			"Prints the project directory if this project contains " +
				"dependencies to other projects.");
		task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		return task;
	}

	private Task _addTaskPrintStaleArtifact(
		Project project, WritePropertiesTask recordArtifactTask) {

		final Task task = project.task(PRINT_STALE_ARTIFACT_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					File projectDir = project.getProjectDir();

					System.out.println(projectDir.getAbsolutePath());
				}

			});

		task.setDescription(
			"Prints the project directory if this project has been changed " +
				"since the last publish.");
		task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		_configureTaskPrintStaleArtifactOnlyIf(
			project, task, recordArtifactTask);

		GradleUtil.withPlugin(
			project, LiferayOSGiDefaultsPlugin.class,
			new Action<LiferayOSGiDefaultsPlugin>() {

				@Override
				public void execute(
					LiferayOSGiDefaultsPlugin liferayOSGiDefaultsPlugin) {

					_configureTaskPrintStaleArtifactForOSGi(task);
				}

			});

		return task;
	}

	private WritePropertiesTask _addTaskRecordArtifact(
		Project project, File destinationDir) {

		final WritePropertiesTask writePropertiesTask = GradleUtil.addTask(
			project, RECORD_ARTIFACT_TASK_NAME, WritePropertiesTask.class);

		writePropertiesTask.property(
			"artifact.git.id",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return GitUtil.getGitResult(
						writePropertiesTask.getProject(), "rev-parse", "HEAD");
				}

			});

		writePropertiesTask.setDescription(
			"Records the commit ID and the artifact URLs.");
		writePropertiesTask.setOutputFile(
			new File(destinationDir, "artifact.properties"));

		Configuration configuration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		PublishArtifactSet publishArtifactSet = configuration.getArtifacts();

		Action<PublishArtifact> action = new Action<PublishArtifact>() {

			@Override
			public void execute(final PublishArtifact publishArtifact) {
				writePropertiesTask.property(
					new Callable<String>() {

						@Override
						public String call() throws Exception {
							String key = publishArtifact.getClassifier();

							if (Validator.isNull(key)) {
								key = publishArtifact.getType();

								Project project =
									writePropertiesTask.getProject();

								if ((JavaPlugin.JAR_TASK_NAME.equals(key) &&
									 GradleUtil.hasPlugin(
										 project, JavaPlugin.class)) ||
									(WarPlugin.WAR_TASK_NAME.equals(key) &&
									 (GradleUtil.hasPlugin(
										 project, LiferayAntPlugin.class) ||
									  GradleUtil.hasPlugin(
										  project,
										  LiferayThemePlugin.class)))) {

									key = null;
								}
							}

							if (Validator.isNull(key)) {
								key = "artifact.url";
							}
							else {
								key = "artifact." + key + ".url";
							}

							return key;
						}

					},
					new Callable<String>() {

						@Override
						public String call() throws Exception {
							return LiferayRelengUtil.getArtifactRemoteURL(
								writePropertiesTask.getProject(),
								publishArtifact, false);
						}

					});
			}

		};

		publishArtifactSet.all(action);

		return writePropertiesTask;
	}

	private WriteArtifactPublishCommandsTask
		_addTaskWriteArtifactPublishCommands(
			Project project, final WritePropertiesTask recordArtifactTask,
			Delete cleanArtifactsPublishCommandsTask,
			MergeFilesTask mergeArtifactsPublishCommandsTask) {

		final WriteArtifactPublishCommandsTask
			writeArtifactPublishCommandsTask = GradleUtil.addTask(
				project, WRITE_ARTIFACT_PUBLISH_COMMANDS,
				WriteArtifactPublishCommandsTask.class);

		writeArtifactPublishCommandsTask.dependsOn(
			cleanArtifactsPublishCommandsTask);

		writeArtifactPublishCommandsTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Gradle gradle = project.getGradle();

					StartParameter startParameter = gradle.getStartParameter();

					if (startParameter.isParallelProjectExecutionEnabled()) {
						throw new GradleException(
							"Unable to run " + task + " in parallel");
					}
				}

			});

		writeArtifactPublishCommandsTask.finalizedBy(
			mergeArtifactsPublishCommandsTask);

		writeArtifactPublishCommandsTask.setArtifactPropertiesFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return recordArtifactTask.getOutputFile();
				}

			});

		writeArtifactPublishCommandsTask.setDescription(
			"Prints the artifact publish commands if this project has been " +
				"changed since the last publish.");

		writeArtifactPublishCommandsTask.setOutputDir(
			CollectionUtils.first(
				cleanArtifactsPublishCommandsTask.getDelete()));

		_configureTaskWriteArtifactPublishCommandsOnlyIf(
			project, writeArtifactPublishCommandsTask, recordArtifactTask);

		GradleUtil.withPlugin(
			project, LiferayOSGiDefaultsPlugin.class,
			new Action<LiferayOSGiDefaultsPlugin>() {

				@Override
				public void execute(
					LiferayOSGiDefaultsPlugin liferayOSGiDefaultsPlugin) {

					_configureTaskWriteArtifactPublishCommandsForOSGi(
						writeArtifactPublishCommandsTask);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					TaskContainer taskContainer = project.getTasks();

					Task task = taskContainer.findByName(
						UPDATE_VERSION_TASK_NAME);

					if (task instanceof ReplaceRegexTask) {
						ReplaceRegexTask replaceRegexTask =
							(ReplaceRegexTask)task;

						Map<String, FileCollection> matches =
							replaceRegexTask.getMatches();

						writeArtifactPublishCommandsTask.prepNextFiles(
							matches.values());
					}

					if (GradleUtil.hasPlugin(project, CachePlugin.class)) {
						CacheExtension cacheExtension = GradleUtil.getExtension(
							project, CacheExtension.class);

						for (TaskCache taskCache : cacheExtension.getTasks()) {
							writeArtifactPublishCommandsTask.prepNextFiles(
								new File(
									taskCache.getCacheDir(),
									TaskCacheApplicator.DIGEST_FILE_NAME));
						}
					}

					if (GradleUtil.hasPlugin(
							project, LiferayThemeDefaultsPlugin.class)) {

						WriteDigestTask writeDigestTask =
							(WriteDigestTask)GradleUtil.getTask(
								project,
								LiferayThemeDefaultsPlugin.
									WRITE_PARENT_THEMES_DIGEST_TASK_NAME);

						writeArtifactPublishCommandsTask.prepNextCommitFile(
							"digest", writeDigestTask.getDigestFile());
					}
				}

			});

		return writeArtifactPublishCommandsTask;
	}

	private void _configureLiferayRelengProperties(Project project) {
		boolean privateModule = false;

		String projectPath = project.getPath();

		if (projectPath.startsWith(":dxp:") ||
			projectPath.startsWith(":private:")) {

			privateModule = true;
		}

		String liferayRelengAppTitlePrefix = GradleUtil.getProperty(
			project, _LIFERAY_RELENG_APP_TITLE_PREFIX, (String)null);

		if (Validator.isNull(liferayRelengAppTitlePrefix)) {
			if (privateModule) {
				liferayRelengAppTitlePrefix = "Liferay";
			}
			else {
				liferayRelengAppTitlePrefix = "Liferay CE";
			}

			GradleUtil.setProperty(
				project, _LIFERAY_RELENG_APP_TITLE_PREFIX,
				liferayRelengAppTitlePrefix);
		}

		String liferayRelengPublic = GradleUtil.getProperty(
			project, _LIFERAY_RELENG_PUBLIC, (String)null);

		if (Validator.isNull(liferayRelengPublic)) {
			liferayRelengPublic = String.valueOf(!privateModule);

			GradleUtil.setProperty(
				project, _LIFERAY_RELENG_PUBLIC, liferayRelengPublic);
		}

		String liferayRelengSupported = GradleUtil.getProperty(
			project, _LIFERAY_RELENG_SUPPORTED, (String)null);

		if (Validator.isNull(liferayRelengSupported)) {
			liferayRelengSupported = String.valueOf(privateModule);

			GradleUtil.setProperty(
				project, _LIFERAY_RELENG_SUPPORTED, liferayRelengSupported);
		}
	}

	private void _configureTaskBuildChangeLog(
		BuildChangeLogTask buildChangeLogTask, File destinationDir) {

		String ticketIdPrefixes = GradleUtil.getProperty(
			buildChangeLogTask.getProject(), "jira.project.keys", (String)null);

		if (Validator.isNotNull(ticketIdPrefixes)) {
			buildChangeLogTask.ticketIdPrefixes(ticketIdPrefixes.split(","));
		}

		buildChangeLogTask.setChangeLogFile(
			new File(destinationDir, "liferay-releng.changelog"));
	}

	private void _configureTaskEnabledIfRelease(Task task) {
		task.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Project project = task.getProject();

					if (GradleUtil.hasStartParameterTask(
							project, task.getName()) ||
						!GradlePluginsDefaultsUtil.isSnapshot(project)) {

						return true;
					}

					return false;
				}

			});
	}

	private void _configureTaskPrintStaleArtifactForOSGi(Task task) {
		if (GradlePluginsDefaultsUtil.isTestProject(task.getProject())) {
			task.setEnabled(false);
		}
	}

	private void _configureTaskPrintStaleArtifactOnlyIf(
		Project project, Task printStaleArtifactTask,
		final WritePropertiesTask recordArtifactTask) {

		final boolean liferayThemeProject = GradleUtil.hasPlugin(
			project, LiferayThemeDefaultsPlugin.class);

		if (liferayThemeProject &&
			GradlePluginsDefaultsUtil.hasNPMParentThemesDependencies(project)) {

			printStaleArtifactTask.dependsOn(NodePlugin.NPM_INSTALL_TASK_NAME);
		}

		Spec<Task> onlyIfSpec = new Spec<Task>() {

			@Override
			public boolean isSatisfiedBy(Task task) {
				Project project = task.getProject();

				String ignoreProjectRegex = GradleUtil.getTaskPrefixedProperty(
					task, "ignore.project.regex");

				if (Validator.isNotNull(ignoreProjectRegex)) {
					Pattern pattern = Pattern.compile(ignoreProjectRegex);

					Matcher matcher = pattern.matcher(project.getName());

					if (matcher.find()) {
						return false;
					}
				}

				File gitRepoDir = GradleUtil.getRootDir(
					project, GitRepo.GIT_REPO_FILE_NAME);

				if (gitRepoDir != null) {
					File file = new File(
						gitRepoDir, GitRepo.GIT_REPO_FILE_NAME);

					try {
						if (!FileUtil.contains(file, "mode = push")) {
							return false;
						}
					}
					catch (IOException ioException) {
						throw new UncheckedIOException(ioException);
					}
				}

				File relengIgnoreDir = GradleUtil.getRootDir(
					project, RELENG_IGNORE_FILE_NAME);

				if (relengIgnoreDir != null) {
					return false;
				}

				String result = GitUtil.getGitResult(
					project, "ls-files",
					FileUtil.getAbsolutePath(project.getProjectDir()));

				if (Validator.isNull(result)) {
					return false;
				}

				if (liferayThemeProject &&
					LiferayRelengUtil.hasStaleParentTheme(project)) {

					return true;
				}

				if (LiferayRelengUtil.hasUnpublishedCommits(
						project, project.getProjectDir(),
						recordArtifactTask.getOutputFile())) {

					return true;
				}

				return false;
			}

		};

		printStaleArtifactTask.onlyIf(onlyIfSpec);
	}

	@SuppressWarnings("serial")
	private void _configureTaskProcessResources(
		Project project, final BuildChangeLogTask buildChangeLogTask) {

		Copy copy = (Copy)GradleUtil.getTask(
			project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		copy.from(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildChangeLogTask.getChangeLogFile();
				}

			},
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.into("META-INF");
				}

			});
	}

	private void _configureTaskUploadArchives(
		Project project, Task recordArtifactTask) {

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		uploadArchivesTask.dependsOn(recordArtifactTask);

		_configureTaskEnabledIfRelease(recordArtifactTask);
	}

	private void _configureTaskWriteArtifactPublishCommandsForOSGi(
		WriteArtifactPublishCommandsTask writeArtifactPublishCommandsTask) {

		if (GradlePluginsDefaultsUtil.isTestProject(
				writeArtifactPublishCommandsTask.getProject())) {

			writeArtifactPublishCommandsTask.setEnabled(false);
		}

		writeArtifactPublishCommandsTask.setFirstPublishExcludedTaskName(
			LiferayOSGiDefaultsPlugin.UPDATE_FILE_VERSIONS_TASK_NAME);
	}

	private void _configureTaskWriteArtifactPublishCommandsOnlyIf(
		Project project,
		WriteArtifactPublishCommandsTask writeArtifactPublishCommandsTask,
		final WritePropertiesTask recordArtifactTask) {

		String force = GradleUtil.getTaskPrefixedProperty(
			writeArtifactPublishCommandsTask, "force");

		if (Boolean.parseBoolean(force)) {
			return;
		}

		final boolean liferayThemeProject = GradleUtil.hasPlugin(
			project, LiferayThemeDefaultsPlugin.class);

		if (liferayThemeProject &&
			GradlePluginsDefaultsUtil.hasNPMParentThemesDependencies(project)) {

			writeArtifactPublishCommandsTask.dependsOn(
				NodePlugin.NPM_INSTALL_TASK_NAME);
		}

		Spec<Task> onlyIfSpec = new Spec<Task>() {

			@Override
			public boolean isSatisfiedBy(Task task) {
				Project project = task.getProject();

				String ignoreProjectRegex = GradleUtil.getTaskPrefixedProperty(
					task, "ignore.project.regex");

				if (Validator.isNotNull(ignoreProjectRegex)) {
					Pattern pattern = Pattern.compile(ignoreProjectRegex);

					Matcher matcher = pattern.matcher(project.getName());

					if (matcher.find()) {
						return false;
					}
				}

				File gitRepoDir = GradleUtil.getRootDir(
					project, GitRepo.GIT_REPO_FILE_NAME);

				if (gitRepoDir != null) {
					File file = new File(
						gitRepoDir, GitRepo.GIT_REPO_FILE_NAME);

					try {
						if (!FileUtil.contains(file, "mode = push")) {
							return false;
						}
					}
					catch (IOException ioException) {
						throw new UncheckedIOException(ioException);
					}
				}

				File relengIgnoreDir = GradleUtil.getRootDir(
					project, RELENG_IGNORE_FILE_NAME);

				if (relengIgnoreDir != null) {
					return false;
				}

				String result = GitUtil.getGitResult(
					project, "ls-files",
					FileUtil.getAbsolutePath(project.getProjectDir()));

				if (Validator.isNull(result)) {
					return false;
				}

				if (LiferayRelengUtil.hasUnpublishedDependencies(project)) {
					return false;
				}

				if (liferayThemeProject &&
					LiferayRelengUtil.hasStaleParentTheme(project)) {

					return true;
				}

				Properties properties = GUtil.loadProperties(
					recordArtifactTask.getOutputFile());

				String artifactGitId = properties.getProperty(
					"artifact.git.id");

				if (LiferayRelengUtil.hasStalePortalDependencies(
						project, artifactGitId)) {

					return true;
				}

				if (LiferayRelengUtil.hasUnpublishedCommits(
						project, project.getProjectDir(),
						recordArtifactTask.getOutputFile())) {

					return true;
				}

				return false;
			}

		};

		writeArtifactPublishCommandsTask.onlyIf(onlyIfSpec);
	}

	private static final String _LIFERAY_RELENG_APP_TITLE_PREFIX =
		"liferay.releng.app.title.prefix";

	private static final String _LIFERAY_RELENG_PUBLIC =
		"liferay.releng.public";

	private static final String _LIFERAY_RELENG_SUPPORTED =
		"liferay.releng.supported";

}