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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.change.log.builder.BuildChangeLogTask;
import com.liferay.gradle.plugins.change.log.builder.ChangeLogBuilderPlugin;
import com.liferay.gradle.plugins.tasks.WritePropertiesTask;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.util.Properties;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.maven.MavenDeployer;
import org.gradle.api.file.CopySpec;
import org.gradle.api.internal.artifacts.publish.ArchivePublishArtifact;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.plugins.MavenRepositoryHandlerConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.Upload;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayRelengPlugin implements Plugin<Project> {

	public static final String PRINT_STALE_ARTIFACT_TASK_NAME =
		"printStaleArtifact";

	public static final String RECORD_ARTIFACT_TASK_NAME = "recordArtifact";

	@Override
	public void apply(final Project project) {
		File relengDir = getRelengDir(project);

		if (relengDir == null) {
			return;
		}

		GradleUtil.applyPlugin(project, ChangeLogBuilderPlugin.class);
		GradleUtil.applyPlugin(project, MavenPlugin.class);

		final BuildChangeLogTask buildChangeLogTask =
			(BuildChangeLogTask)GradleUtil.getTask(
				project, ChangeLogBuilderPlugin.BUILD_CHANGE_LOG_TASK_NAME);

		final WritePropertiesTask recordArtifactTask = addTaskRecordArtifact(
			project, relengDir);

		final Task printStaleArtifactTask = addTaskPrintStaleArtifact(
			project, recordArtifactTask);

		configureTaskBuildChangeLog(buildChangeLogTask, relengDir);
		configureTaskUploadArchives(project, recordArtifactTask);

		GradleUtil.withPlugin(
			project, JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskProcessResources(project, buildChangeLogTask);
				}

			});

		GradleUtil.withPlugin(
			project, LiferayOSGiPlugin.class,
			new Action<LiferayOSGiPlugin>() {

				@Override
				public void execute(LiferayOSGiPlugin liferayOSGiPlugin) {
					if (GradleUtil.hasStartParameterTask(
							project, LiferayBasePlugin.DEPLOY_TASK_NAME)) {

						configureTaskDeploy(project, recordArtifactTask);
					}

					if (LiferayOSGiDefaultsPlugin.isTestProject(project)) {
						printStaleArtifactTask.setEnabled(false);
					}
				}

			});
	}

	protected Task addTaskPrintStaleArtifact(
		Project project, final WritePropertiesTask recordArtifactTask) {

		Task task = project.task(PRINT_STALE_ARTIFACT_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					File projectDir = project.getProjectDir();

					System.out.println(projectDir.getAbsolutePath());
				}

			});

		task.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Properties artifactProperties = getArtifactProperties(
						recordArtifactTask);

					return isStale(
						recordArtifactTask.getProject(), artifactProperties);
				}

			});

		task.setDescription(
			"Prints the project directory if this project has been changed " +
				"since the last publish.");
		task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		return task;
	}

	protected WritePropertiesTask addTaskRecordArtifact(
		Project project, File destinationDir) {

		final WritePropertiesTask writePropertiesTask = GradleUtil.addTask(
			project, RECORD_ARTIFACT_TASK_NAME, WritePropertiesTask.class);

		writePropertiesTask.property(
			"artifact.git.id",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return getGitResult(
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
				String key = publishArtifact.getClassifier();

				if (Validator.isNull(key)) {
					key = "artifact.url";
				}
				else {
					key = "artifact." + key + ".url";
				}

				writePropertiesTask.property(
					key,
					new Callable<String>() {

						@Override
						public String call() throws Exception {
							if (publishArtifact instanceof
									ArchivePublishArtifact) {

								ArchivePublishArtifact archivePublishArtifact =
									(ArchivePublishArtifact)publishArtifact;

								return getArtifactRemoteURL(
									archivePublishArtifact.getArchiveTask(),
									false);
							}
							else {
								Project project =
									writePropertiesTask.getProject();

								return getArtifactRemoteURL(
									project, publishArtifact.getName(),
									String.valueOf(project.getVersion()),
									publishArtifact.getExtension(), false);
							}
						}

					});
			}

		};

		publishArtifactSet.all(action);

		return writePropertiesTask;
	}

	protected void configureTaskBuildChangeLog(
		BuildChangeLogTask buildChangeLogTask, File destinationDir) {

		buildChangeLogTask.setChangeLogFile(
			new File(destinationDir, "liferay-releng.changelog"));
	}

	protected void configureTaskDeploy(
		Copy copy, WritePropertiesTask recordArtifactTask) {

		final Project project = copy.getProject();

		Properties artifactProperties = getArtifactProperties(
			recordArtifactTask);

		if (isStale(project, artifactProperties)) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(
					"Unable to download artifact, " + project + " is stale");
			}

			return;
		}

		final String artifactURL = artifactProperties.getProperty(
			"artifact.url");

		if (Validator.isNull(artifactURL)) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Unable to find artifact.url in " +
						recordArtifactTask.getOutputFile());
			}

			return;
		}

		Task jarTask = GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		boolean replaced = GradleUtil.replaceCopySpecSourcePath(
			copy.getRootSpec(), jarTask,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return FileUtil.get(project, artifactURL);
				}

			});

		if (replaced && _logger.isLifecycleEnabled()) {
			_logger.lifecycle("Downloading artifact from " + artifactURL);
		}
	}

	protected void configureTaskDeploy(
		Project project, final WritePropertiesTask recordArtifactTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Copy.class,
			new Action<Copy>() {

				@Override
				public void execute(Copy copy) {
					String taskName = copy.getName();

					if (taskName.equals(LiferayBasePlugin.DEPLOY_TASK_NAME)) {
						configureTaskDeploy(copy, recordArtifactTask);
					}
				}

			});
	}

	protected void configureTaskProcessResources(
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
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.into("META-INF");
				}

			});
	}

	protected void configureTaskUploadArchives(
		Project project, Task recordArtifactTask) {

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		Spec<Task> releaseProjectSpec = new Spec<Task>() {

			@Override
			public boolean isSatisfiedBy(Task task) {
				Project project = task.getProject();

				if (GradleUtil.hasStartParameterTask(project, task.getName())) {
					return true;
				}

				String version = String.valueOf(project.getVersion());

				if (!version.endsWith(GradleUtil.SNAPSHOT_VERSION_SUFFIX)) {
					return true;
				}

				return false;
			}

		};

		uploadArchivesTask.dependsOn(recordArtifactTask);

		recordArtifactTask.onlyIf(releaseProjectSpec);
	}

	protected Properties getArtifactProperties(
		WritePropertiesTask recordArtifactTask) {

		try {
			return FileUtil.readProperties(recordArtifactTask.getOutputFile());
		}
		catch (IOException ioe) {
			throw new GradleException(
				"Unable to read artifact properties", ioe);
		}
	}

	protected StringBuilder getArtifactRemoteBaseURL(
			Project project, boolean cdn)
		throws Exception {

		Upload upload = (Upload)GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		RepositoryHandler repositoryHandler = upload.getRepositories();

		MavenDeployer mavenDeployer = (MavenDeployer)repositoryHandler.getAt(
			MavenRepositoryHandlerConvention.DEFAULT_MAVEN_DEPLOYER_NAME);

		Object repository = mavenDeployer.getRepository();

		// org.apache.maven.artifact.ant.RemoteRepository is not in the
		// classpath

		Class<?> repositoryClass = repository.getClass();

		Method getUrlMethod = repositoryClass.getMethod("getUrl");

		String url = (String)getUrlMethod.invoke(repository);

		if (cdn) {
			url = url.replace("http://", "http://cdn.");
			url = url.replace("https://", "https://cdn.");
		}

		StringBuilder sb = new StringBuilder(url);

		if (sb.charAt(sb.length() - 1) != '/') {
			sb.append('/');
		}

		String group = String.valueOf(project.getGroup());

		sb.append(group.replace('.', '/'));

		sb.append('/');

		return sb;
	}

	protected String getArtifactRemoteURL(
			AbstractArchiveTask abstractArchiveTask, boolean cdn)
		throws Exception {

		StringBuilder sb = getArtifactRemoteBaseURL(
			abstractArchiveTask.getProject(), cdn);

		sb.append(abstractArchiveTask.getBaseName());
		sb.append('/');
		sb.append(abstractArchiveTask.getVersion());
		sb.append('/');
		sb.append(abstractArchiveTask.getArchiveName());

		return sb.toString();
	}

	protected String getArtifactRemoteURL(
			Project project, String name, String version, String extension,
			boolean cdn)
		throws Exception {

		StringBuilder sb = getArtifactRemoteBaseURL(project, cdn);

		sb.append(name);
		sb.append('/');
		sb.append(version);
		sb.append('/');
		sb.append(name);
		sb.append('-');
		sb.append(version);
		sb.append('.');
		sb.append(extension);

		return sb.toString();
	}

	protected String getGitResult(Project project, final Object... args) {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.args(args);
					execSpec.setExecutable("git");
					execSpec.setStandardOutput(byteArrayOutputStream);
				}

			});

		String result = byteArrayOutputStream.toString();

		return result.trim();
	}

	protected File getRelengDir(Project project) {
		File relengDir = new File(project.getRootDir(), ".releng");

		if (!relengDir.exists()) {
			return null;
		}

		return new File(
			relengDir,
			FileUtil.relativize(project.getProjectDir(), project.getRootDir()));
	}

	protected boolean isStale(
		final Project project, Properties artifactProperties) {

		final String artifactGitId = artifactProperties.getProperty(
			"artifact.git.id");

		if (Validator.isNull(artifactGitId)) {
			if (_logger.isInfoEnabled()) {
				_logger.info(project + " has never been published");
			}

			return true;
		}

		String result = getGitResult(
			project, "log", "--format=%s", artifactGitId + "..HEAD", ".");

		String[] lines = result.split("\\r?\\n");

		for (String line : lines) {
			if (_logger.isInfoEnabled()) {
				_logger.info(line);
			}

			if (Validator.isNull(line)) {
				continue;
			}

			if (!line.contains(_IGNORED_MESSAGE_PATTERN)) {
				return true;
			}
		}

		return false;
	}

	private static final String _IGNORED_MESSAGE_PATTERN = "artifact:ignore";

	private static final Logger _logger = Logging.getLogger(
		LiferayRelengPlugin.class);

}