/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.gradle.plugins.defaults.task.MergeFilesTask;
import com.liferay.gradle.plugins.defaults.task.ReplaceRegexTask;
import com.liferay.gradle.plugins.defaults.task.WriteArtifactPublishCommandsTask;
import com.liferay.gradle.plugins.defaults.task.WritePropertiesTask;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.util.Map;
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
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.util.CollectionUtils;

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

		// Plugins

		GradleUtil.applyPlugin(project, ChangeLogBuilderPlugin.class);
		GradleUtil.applyPlugin(project, MavenPublishPlugin.class);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration archivesConfiguration =
			configurationContainer.maybeCreate(
				Dependency.ARCHIVES_CONFIGURATION);

		// Tasks

		TaskProvider<Task> printDependentArtifactTaskProvider =
			GradleUtil.addTaskProvider(
				project, PRINT_DEPENDENT_ARTIFACT_TASK_NAME, Task.class);
		TaskProvider<Task> printStaleArtifactTaskProvider =
			GradleUtil.addTaskProvider(
				project, PRINT_STALE_ARTIFACT_TASK_NAME, Task.class);
		TaskProvider<WritePropertiesTask> recordArtifactTaskProvider =
			GradleUtil.addTaskProvider(
				project, RECORD_ARTIFACT_TASK_NAME, WritePropertiesTask.class);
		TaskProvider<WriteArtifactPublishCommandsTask>
			writeArtifactPublishCommandsTaskProvider =
				GradleUtil.addTaskProvider(
					project, WRITE_ARTIFACT_PUBLISH_COMMANDS,
					WriteArtifactPublishCommandsTask.class);

		TaskProvider<Delete> cleanArtifactsPublishCommandsTaskProvider =
			_getTaskCleanArtifactsPublishCommandsProvider(project.getGradle());

		TaskProvider<MergeFilesTask> mergeArtifactsPublishCommandsTaskProvider =
			_getTaskMergeArtifactsPublishCommandsProvider(
				project.getGradle(), cleanArtifactsPublishCommandsTaskProvider,
				writeArtifactPublishCommandsTaskProvider);

		final TaskProvider<BuildChangeLogTask> buildChangeLogTaskProvider =
			GradleUtil.getTaskProvider(
				project, ChangeLogBuilderPlugin.BUILD_CHANGE_LOG_TASK_NAME,
				BuildChangeLogTask.class);
		TaskProvider<Task> publishTaskProvider = GradleUtil.getTaskProvider(
			project, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

		File relengDir = LiferayRelengUtil.getRelengDir(project);

		_configureTaskBuildChangeLogProvider(
			buildChangeLogTaskProvider, relengDir);
		_configureTaskPrintDependentArtifactProvider(
			printDependentArtifactTaskProvider);
		_configureTaskPrintStaleArtifactProvider(
			project, recordArtifactTaskProvider,
			printStaleArtifactTaskProvider);
		_configureTaskRecordArtifactProvider(
			project, recordArtifactTaskProvider, relengDir);
		_configureTaskPublishProvider(
			recordArtifactTaskProvider, publishTaskProvider);
		_configureTaskWriteArtifactPublishCommandsProvider(
			project, cleanArtifactsPublishCommandsTaskProvider,
			mergeArtifactsPublishCommandsTaskProvider,
			recordArtifactTaskProvider,
			writeArtifactPublishCommandsTaskProvider);

		// Other

		_configureProperties(project);

		PublishArtifactSet publishArtifactSet =
			archivesConfiguration.getArtifacts();

		publishArtifactSet.all(
			new Action<PublishArtifact>() {

				@Override
				public void execute(PublishArtifact publishArtifact) {
					_configureTaskRecordArtifactProvider(
						project, recordArtifactTaskProvider, publishArtifact);
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					TaskProvider<Copy> processResourcesTaskProvider =
						GradleUtil.getTaskProvider(
							project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME,
							Copy.class);

					_configureTaskProcessResourcesProvider(
						project, buildChangeLogTaskProvider,
						processResourcesTaskProvider);
				}

			});

		pluginContainer.withType(
			LiferayOSGiDefaultsPlugin.class,
			new Action<LiferayOSGiDefaultsPlugin>() {

				@Override
				public void execute(
					LiferayOSGiDefaultsPlugin liferayOSGiDefaultsPlugin) {

					_configureTaskPrintStaleArtifactProviderForLiferayOSGiDefaultsPlugin(
						printStaleArtifactTaskProvider);
					_configureTaskWriteArtifactPublishCommandsProviderForLiferayOSGiDefaultsPlugin(
						writeArtifactPublishCommandsTaskProvider);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskWriteArtifactPublishCommandsProviderAfterEvaluate(
						project, writeArtifactPublishCommandsTaskProvider);
				}

			});
	}

	protected static final String RELENG_IGNORE_FILE_NAME =
		".lfrbuild-releng-ignore";

	private LiferayRelengPlugin() {
	}

	private void _configureProperties(Project project) {
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

	private void _configureTaskBuildChangeLogProvider(
		TaskProvider<BuildChangeLogTask> buildChangeLogTaskProvider,
		final File relengDir) {

		buildChangeLogTaskProvider.configure(
			new Action<BuildChangeLogTask>() {

				@Override
				public void execute(BuildChangeLogTask buildChangeLogTask) {
					String ticketIdPrefixes = GradleUtil.getProperty(
						buildChangeLogTask.getProject(), "jira.project.keys",
						(String)null);

					if (Validator.isNotNull(ticketIdPrefixes)) {
						buildChangeLogTask.ticketIdPrefixes(
							ticketIdPrefixes.split(","));
					}

					buildChangeLogTask.setChangeLogFile(
						new File(relengDir, "liferay-releng.changelog"));
				}

			});
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

	private void _configureTaskPrintDependentArtifactProvider(
		TaskProvider<Task> printDependentArtifactTaskProvider) {

		printDependentArtifactTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task printDependentArtifactTask) {
					printDependentArtifactTask.doLast(
						new Action<Task>() {

							@Override
							public void execute(Task task) {
								Project project = task.getProject();

								File projectDir = project.getProjectDir();

								System.out.println(
									projectDir.getAbsolutePath());
							}

						});

					Spec<Task> onlyIfSpec = new Spec<Task>() {

						@Override
						public boolean isSatisfiedBy(Task task) {
							Project project = task.getProject();

							String ignoreProjectRegex =
								GradleUtil.getTaskPrefixedProperty(
									task, "ignore.project.regex");

							if (Validator.isNotNull(ignoreProjectRegex)) {
								Pattern pattern = Pattern.compile(
									ignoreProjectRegex);

								Matcher matcher = pattern.matcher(
									project.getName());

								if (matcher.find()) {
									return false;
								}
							}

							String result = GitUtil.getGitResult(
								project, "ls-files",
								FileUtil.getAbsolutePath(
									project.getProjectDir()));

							if (Validator.isNull(result) ||
								GradlePluginsDefaultsUtil.isTestProject(
									project)) {

								return false;
							}

							String dependencyName =
								LiferayRelengUtil.getUnpublishedDependencyName(
									project);

							if (dependencyName == null) {
								return false;
							}

							Logger logger = project.getLogger();

							if (logger.isQuietEnabled()) {
								logger.quiet(
									"The project dependency '{}' has new commits.",
									dependencyName);
							}

							return true;
						}

					};

					printDependentArtifactTask.onlyIf(onlyIfSpec);

					printDependentArtifactTask.setDescription(
						"Prints the project directory if this project contains " +
							"dependencies to other projects.");
					printDependentArtifactTask.setGroup(
						JavaBasePlugin.VERIFICATION_GROUP);
				}

			});
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

				if ((liferayThemeProject &&
					 LiferayRelengUtil.hasStaleParentTheme(project)) ||
					LiferayRelengUtil.hasUnpublishedCommits(
						project, project.getProjectDir(),
						recordArtifactTask.getOutputFile())) {

					return true;
				}

				if (LiferayRelengUtil.hasStaleUnstyledTheme(
						project, recordArtifactTask.getOutputFile())) {

					return true;
				}

				return false;
			}

		};

		printStaleArtifactTask.onlyIf(onlyIfSpec);
	}

	private void _configureTaskPrintStaleArtifactProvider(
		final Project project,
		final TaskProvider<WritePropertiesTask> recordArtifactTaskProvider,
		TaskProvider<Task> printStaleArtifactTaskProvider) {

		printStaleArtifactTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task printStaleArtifactTask) {
					printStaleArtifactTask.doLast(
						new Action<Task>() {

							@Override
							public void execute(Task task) {
								Project project = task.getProject();

								File projectDir = project.getProjectDir();

								System.out.println(
									projectDir.getAbsolutePath());
							}

						});

					printStaleArtifactTask.setDescription(
						"Prints the project directory if this project has been changed " +
							"since the last publish.");
					printStaleArtifactTask.setGroup(
						JavaBasePlugin.VERIFICATION_GROUP);

					_configureTaskPrintStaleArtifactOnlyIf(
						project, printStaleArtifactTask,
						recordArtifactTaskProvider.get());
				}

			});
	}

	private void
		_configureTaskPrintStaleArtifactProviderForLiferayOSGiDefaultsPlugin(
			TaskProvider<Task> printStaleArtifactTaskProvider) {

		printStaleArtifactTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task printStaleArtifactTask) {
					if (GradlePluginsDefaultsUtil.isTestProject(
							printStaleArtifactTask.getProject())) {

						printStaleArtifactTask.setEnabled(false);
					}
				}

			});
	}

	private void _configureTaskProcessResourcesProvider(
		final Project project,
		final TaskProvider<BuildChangeLogTask> buildChangeLogTaskProvider,
		TaskProvider<Copy> processResourcesTaskProvider) {

		processResourcesTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy processResourcesCopy) {
					processResourcesCopy.setDuplicatesStrategy(
						DuplicatesStrategy.INCLUDE);

					processResourcesCopy.from(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								BuildChangeLogTask buildChangeLogTask =
									buildChangeLogTaskProvider.get();

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

			});
	}

	private void _configureTaskPublishProvider(
		final TaskProvider<WritePropertiesTask> recordArtifactTaskProvider,
		TaskProvider<Task> publishTaskProvider) {

		publishTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task publishTask) {
					publishTask.dependsOn(recordArtifactTaskProvider);

					_configureTaskEnabledIfRelease(
						recordArtifactTaskProvider.get());
				}

			});
	}

	private void _configureTaskRecordArtifactProvider(
		final Project project,
		TaskProvider<WritePropertiesTask> recordArtifactTaskProvider,
		final File relengDir) {

		recordArtifactTaskProvider.configure(
			new Action<WritePropertiesTask>() {

				@Override
				public void execute(
					WritePropertiesTask recordArtifactWritePropertiesTask) {

					recordArtifactWritePropertiesTask.property(
						"artifact.git.id",
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return GitUtil.getGitResult(
									project, "rev-parse", "HEAD");
							}

						});

					recordArtifactWritePropertiesTask.setDescription(
						"Records the commit ID and the artifact URLs.");
					recordArtifactWritePropertiesTask.setOutputFile(
						new File(relengDir, "artifact.properties"));
				}

			});
	}

	private void _configureTaskRecordArtifactProvider(
		final Project project,
		TaskProvider<WritePropertiesTask> recordArtifactTaskProvider,
		final PublishArtifact publishArtifact) {

		WritePropertiesTask recordArtifactWritePropertiesTask =
			recordArtifactTaskProvider.get();

		recordArtifactWritePropertiesTask.property(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String key = publishArtifact.getClassifier();

					if (Validator.isNull(key)) {
						key = publishArtifact.getType();

						if ((JavaPlugin.JAR_TASK_NAME.equals(key) &&
							 GradleUtil.hasPlugin(project, JavaPlugin.class)) ||
							(WarPlugin.WAR_TASK_NAME.equals(key) &&
							 (GradleUtil.hasPlugin(
								 project, LiferayAntPlugin.class) ||
							  GradleUtil.hasPlugin(
								  project, LiferayThemePlugin.class)))) {

							key = null;
						}
					}

					if (Validator.isNull(key)) {
						return "artifact.url";
					}

					return "artifact." + key + ".url";
				}

			},
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return LiferayRelengUtil.getArtifactRemoteURL(
						project, publishArtifact, true);
				}

			});
	}

	private void _configureTaskWriteArtifactPublishCommandsOnlyIf(
		Project project,
		WriteArtifactPublishCommandsTask writeArtifactPublishCommandsTask,
		final WritePropertiesTask recordArtifactTask) {

		final String force = GradleUtil.getTaskPrefixedProperty(
			writeArtifactPublishCommandsTask, "force");

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

				if (Boolean.parseBoolean(force) ||
					(liferayThemeProject &&
					 LiferayRelengUtil.hasStaleParentTheme(project))) {

					return true;
				}

				if (LiferayRelengUtil.hasStalePortalDependencies(
						project, recordArtifactTask.getOutputFile()) ||
					LiferayRelengUtil.hasStaleUnstyledTheme(
						project, recordArtifactTask.getOutputFile())) {

					return true;
				}

				if (LiferayRelengUtil.hasUnpublishedCommits(
						project, project.getProjectDir(),
						recordArtifactTask.getOutputFile())) {

					String dependencyName =
						LiferayRelengUtil.getUnpublishedDependencyName(project);

					if (dependencyName != null) {
						Logger logger = project.getLogger();

						if (logger.isInfoEnabled()) {
							logger.info(
								"The project dependency '{}' has new commits.",
								dependencyName);
						}

						return false;
					}

					return true;
				}

				return false;
			}

		};

		writeArtifactPublishCommandsTask.onlyIf(onlyIfSpec);
	}

	private void _configureTaskWriteArtifactPublishCommandsProvider(
		final Project project,
		final TaskProvider<Delete> cleanArtifactsPublishCommandsTaskProvider,
		final TaskProvider<MergeFilesTask>
			mergeArtifactsPublishCommandsTaskProvider,
		final TaskProvider<WritePropertiesTask> recordArtifactTaskProvider,
		TaskProvider<WriteArtifactPublishCommandsTask>
			writeArtifactPublishCommandsTaskProvider) {

		writeArtifactPublishCommandsTaskProvider.configure(
			new Action<WriteArtifactPublishCommandsTask>() {

				@Override
				public void execute(
					WriteArtifactPublishCommandsTask
						writeArtifactPublishCommandsTask) {

					writeArtifactPublishCommandsTask.dependsOn(
						cleanArtifactsPublishCommandsTaskProvider);

					writeArtifactPublishCommandsTask.doFirst(
						new Action<Task>() {

							@Override
							public void execute(Task task) {
								Project project = task.getProject();

								Gradle gradle = project.getGradle();

								StartParameter startParameter =
									gradle.getStartParameter();

								if (startParameter.
										isParallelProjectExecutionEnabled()) {

									throw new GradleException(
										"Unable to run " + task +
											" in parallel");
								}
							}

						});

					writeArtifactPublishCommandsTask.finalizedBy(
						mergeArtifactsPublishCommandsTaskProvider);

					writeArtifactPublishCommandsTask.setArtifactPropertiesFile(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								WritePropertiesTask
									recordArtifactWritePropertiesTask =
										recordArtifactTaskProvider.get();

								return recordArtifactWritePropertiesTask.
									getOutputFile();
							}

						});

					writeArtifactPublishCommandsTask.setDescription(
						"Prints the artifact publish commands if this project has been " +
							"changed since the last publish.");

					Delete cleanArtifactsPublishCommandsDelete =
						cleanArtifactsPublishCommandsTaskProvider.get();

					writeArtifactPublishCommandsTask.setOutputDir(
						CollectionUtils.first(
							cleanArtifactsPublishCommandsDelete.getDelete()));

					_configureTaskWriteArtifactPublishCommandsOnlyIf(
						project, writeArtifactPublishCommandsTask,
						recordArtifactTaskProvider.get());
				}

			});
	}

	private void
		_configureTaskWriteArtifactPublishCommandsProviderAfterEvaluate(
			Project project,
			TaskProvider<WriteArtifactPublishCommandsTask>
				writeArtifactPublishCommandsTaskProvider) {

		TaskProvider<ReplaceRegexTask> updateVersionTaskProvider =
			GradleUtil.fetchTaskProvider(
				project, UPDATE_VERSION_TASK_NAME, ReplaceRegexTask.class);

		if (updateVersionTaskProvider != null) {
			ReplaceRegexTask updateVersionReplaceRegexTask =
				updateVersionTaskProvider.get();

			Map<String, FileCollection> matches =
				updateVersionReplaceRegexTask.getMatches();

			WriteArtifactPublishCommandsTask writeArtifactPublishCommandsTask =
				writeArtifactPublishCommandsTaskProvider.get();

			writeArtifactPublishCommandsTask.prepNextFiles(matches.values());
		}

		if (GradleUtil.hasPlugin(project, CachePlugin.class)) {
			CacheExtension cacheExtension = GradleUtil.getExtension(
				project, CacheExtension.class);

			WriteArtifactPublishCommandsTask writeArtifactPublishCommandsTask =
				writeArtifactPublishCommandsTaskProvider.get();

			for (TaskCache taskCache : cacheExtension.getTasks()) {
				writeArtifactPublishCommandsTask.prepNextFiles(
					new File(
						taskCache.getCacheDir(),
						TaskCacheApplicator.DIGEST_FILE_NAME));
			}
		}

		if (GradleUtil.hasPlugin(project, LiferayThemeDefaultsPlugin.class)) {
			TaskProvider<WriteDigestTask> writeParentThemesDigestTaskProvider =
				GradleUtil.getTaskProvider(
					project,
					LiferayThemeDefaultsPlugin.
						WRITE_PARENT_THEMES_DIGEST_TASK_NAME,
					WriteDigestTask.class);

			WriteArtifactPublishCommandsTask writeArtifactPublishCommandsTask =
				writeArtifactPublishCommandsTaskProvider.get();
			WriteDigestTask writeParentThemesDigestWriteDigestTask =
				writeParentThemesDigestTaskProvider.get();

			writeArtifactPublishCommandsTask.prepNextCommitFile(
				"digest",
				writeParentThemesDigestWriteDigestTask.getDigestFile());
		}
	}

	private void
		_configureTaskWriteArtifactPublishCommandsProviderForLiferayOSGiDefaultsPlugin(
			TaskProvider<WriteArtifactPublishCommandsTask>
				writeArtifactPublishCommandsTaskProvider) {

		writeArtifactPublishCommandsTaskProvider.configure(
			new Action<WriteArtifactPublishCommandsTask>() {

				@Override
				public void execute(
					WriteArtifactPublishCommandsTask
						writeArtifactPublishCommandsTask) {

					if (GradlePluginsDefaultsUtil.isTestProject(
							writeArtifactPublishCommandsTask.getProject())) {

						writeArtifactPublishCommandsTask.setEnabled(false);
					}

					writeArtifactPublishCommandsTask.
						setFirstPublishExcludedTaskName(
							LiferayOSGiDefaultsPlugin.
								UPDATE_FILE_VERSIONS_TASK_NAME);
				}

			});
	}

	private TaskProvider<Delete> _getTaskCleanArtifactsPublishCommandsProvider(
		Gradle gradle) {

		StartParameter startParameter = gradle.getStartParameter();

		final Project currentProject = GradleUtil.getProject(
			gradle.getRootProject(), startParameter.getCurrentDir());

		TaskProvider<Delete> cleanArtifactsPublishCommandsTaskProvider =
			GradleUtil.fetchTaskProvider(
				currentProject, CLEAN_ARTIFACTS_PUBLISH_COMMANDS_TASK_NAME,
				Delete.class);

		if (cleanArtifactsPublishCommandsTaskProvider != null) {
			return cleanArtifactsPublishCommandsTaskProvider;
		}

		cleanArtifactsPublishCommandsTaskProvider = GradleUtil.addTaskProvider(
			currentProject, CLEAN_ARTIFACTS_PUBLISH_COMMANDS_TASK_NAME,
			Delete.class);

		cleanArtifactsPublishCommandsTaskProvider.configure(
			new Action<Delete>() {

				@Override
				public void execute(
					Delete cleanArtifactsPublishCommandsDelete) {

					cleanArtifactsPublishCommandsDelete.delete(
						new File(
							currentProject.getBuildDir(),
							"artifacts-publish-commands"));
					cleanArtifactsPublishCommandsDelete.setDescription(
						"Deletes the temporary directory that contains the " +
							"artifacts publish commands.");
				}

			});

		return cleanArtifactsPublishCommandsTaskProvider;
	}

	private TaskProvider<MergeFilesTask>
		_getTaskMergeArtifactsPublishCommandsProvider(
			Gradle gradle,
			final TaskProvider<Delete>
				cleanArtifactsPublishCommandsTaskProvider,
			final TaskProvider<WriteArtifactPublishCommandsTask>
				writeArtifactPublishCommandsTaskProvider) {

		StartParameter startParameter = gradle.getStartParameter();

		final Project currentProject = GradleUtil.getProject(
			gradle.getRootProject(), startParameter.getCurrentDir());

		TaskProvider<MergeFilesTask> mergeArtifactsPublishCommandsTaskProvider =
			GradleUtil.fetchTaskProvider(
				currentProject, MERGE_ARTIFACTS_PUBLISH_COMMANDS,
				MergeFilesTask.class);

		if (mergeArtifactsPublishCommandsTaskProvider != null) {
			return mergeArtifactsPublishCommandsTaskProvider;
		}

		mergeArtifactsPublishCommandsTaskProvider = GradleUtil.addTaskProvider(
			currentProject, MERGE_ARTIFACTS_PUBLISH_COMMANDS,
			MergeFilesTask.class);

		mergeArtifactsPublishCommandsTaskProvider.configure(
			new Action<MergeFilesTask>() {

				@Override
				public void execute(
					MergeFilesTask
						mergeArtifactsPublishCommandsMergeFilesTask) {

					mergeArtifactsPublishCommandsMergeFilesTask.mustRunAfter(
						writeArtifactPublishCommandsTaskProvider);

					Delete cleanArtifactsPublishCommandsDelete =
						cleanArtifactsPublishCommandsTaskProvider.get();

					File dir = GradleUtil.toFile(
						currentProject,
						CollectionUtils.first(
							cleanArtifactsPublishCommandsDelete.getDelete()));

					mergeArtifactsPublishCommandsMergeFilesTask.doLast(
						new Action<Task>() {

							@Override
							public void execute(Task task) {
								MergeFilesTask mergeFilesTask =
									(MergeFilesTask)task;

								Logger logger = mergeFilesTask.getLogger();

								File file = mergeFilesTask.getOutputFile();

								if (file.exists()) {
									boolean success = file.setExecutable(true);

									if (!success) {
										logger.error(
											"Unable to set the owner's " +
												"execute permission for {}",
											file);
									}

									if (logger.isQuietEnabled()) {
										logger.quiet(
											"Artifacts publish commands " +
												"written in {}.",
											file);
									}
								}
								else {
									if (logger.isQuietEnabled()) {
										logger.quiet(
											"No artifacts publish commands " +
												"are available.");
									}
								}
							}

						});

					mergeArtifactsPublishCommandsMergeFilesTask.setDescription(
						"Merges the artifacts publish commands.");
					mergeArtifactsPublishCommandsMergeFilesTask.setHeader(
						"#!/bin/bash" + System.lineSeparator() +
							System.lineSeparator() + "set -e" +
								System.lineSeparator());

					mergeArtifactsPublishCommandsMergeFilesTask.setInputFiles(
						/*new File(
							dir, WRITE_ARTIFACT_PUBLISH_COMMANDS + "-step1.sh"),
						new File(
							dir, WRITE_ARTIFACT_PUBLISH_COMMANDS + "-step2.sh"),*/
						new File(
							dir,
							WRITE_ARTIFACT_PUBLISH_COMMANDS + "-step3.sh"));

					mergeArtifactsPublishCommandsMergeFilesTask.setOutputFile(
						new File(dir, "artifacts-publish-commands.sh"));

					TaskOutputs taskOutputs =
						mergeArtifactsPublishCommandsMergeFilesTask.
							getOutputs();

					taskOutputs.upToDateWhen(
						new Spec<Task>() {

							@Override
							public boolean isSatisfiedBy(Task task) {
								return false;
							}

						});
				}

			});

		return mergeArtifactsPublishCommandsTaskProvider;
	}

	private static final String _LIFERAY_RELENG_APP_TITLE_PREFIX =
		"liferay.releng.app.title.prefix";

	private static final String _LIFERAY_RELENG_PUBLIC =
		"liferay.releng.public";

	private static final String _LIFERAY_RELENG_SUPPORTED =
		"liferay.releng.supported";

}