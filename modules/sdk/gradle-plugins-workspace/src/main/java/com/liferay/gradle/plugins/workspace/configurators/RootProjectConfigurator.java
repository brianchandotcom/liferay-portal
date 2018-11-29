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

import com.bmuschko.gradle.docker.DockerRemoteApiPlugin;
import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerLogsContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerRemoveContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer;
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage;
import com.bmuschko.gradle.docker.tasks.image.DockerPullImage;
import com.bmuschko.gradle.docker.tasks.image.Dockerfile;

import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.configurators.TargetPlatformRootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.tasks.CreateTokenTask;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import de.undercouch.gradle.tasks.download.Download;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.http.HttpHeaders;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
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

	public static final String BUILD_IMAGE_TASK_NAME = "buildImage";

	public static final String BUNDLE_CONFIGURATION_NAME = "bundle";

	public static final String BUNDLE_GROUP = "bundle";

	public static final String CLEAN_TASK_NAME =
		LifecycleBasePlugin.CLEAN_TASK_NAME;

	public static final String CREATE_CONTAINER_TASK_NAME = "createContainer";

	public static final String CREATE_DOCKERFILE_TASK_NAME = "createDockerfile";

	public static final String CREATE_TOKEN_TASK_NAME = "createToken";

	public static final String DOCKER_DEPLOY_TASK_NAME = "dockerDeploy";

	public static final String DIST_BUNDLE_TAR_TASK_NAME = "distBundleTar";

	public static final String DIST_BUNDLE_TASK_NAME = "distBundle";

	public static final String DIST_BUNDLE_ZIP_TASK_NAME = "distBundleZip";

	public static final String DOCKER_GROUP = "docker";

	public static final String DOWNLOAD_BUNDLE_TASK_NAME = "downloadBundle";

	public static final String INIT_BUNDLE_TASK_NAME = "initBundle";

	public static final String LOGS_CONTAINER_TASK_NAME = "logsContainer";

	public static final String PROVIDED_MODULES_CONFIGURATION_NAME =
		"providedModules";

	public static final String PULL_IMAGE_TASK_NAME = "pullImage";

	public static final String REMOVE_CONTAINER_TASK_NAME = "removeContainer";

	public static final String START_CONTAINER_TASK_NAME = "startContainer";

	public static final String STOP_CONTAINER_TASK_NAME = "stopContainer";

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
		final WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		GradleUtil.applyPlugin(project, DockerRemoteApiPlugin.class);
		GradleUtil.applyPlugin(project, LifecycleBasePlugin.class);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		final Configuration providedModulesConfiguration =
			_addConfigurationProvidedModules(project);

		TargetPlatformRootProjectConfigurator.INSTANCE.apply(project);

		CreateTokenTask createTokenTask = _addTaskCreateToken(
			project, workspaceExtension);

		Download downloadBundleTask = _addTaskDownloadBundle(
			createTokenTask, workspaceExtension);

		Copy distBundleTask = _addTaskDistBundle(
			project, downloadBundleTask, workspaceExtension,
			providedModulesConfiguration);

		Tar distBundleTarTask = _addTaskDistBundle(
			project, DIST_BUNDLE_TAR_TASK_NAME, Tar.class, distBundleTask,
			workspaceExtension);

		distBundleTarTask.setCompression(Compression.GZIP);
		distBundleTarTask.setExtension("tar.gz");

		_addTaskDistBundle(
			project, DIST_BUNDLE_ZIP_TASK_NAME, Zip.class, distBundleTask,
			workspaceExtension);

		_addTaskInitBundle(
			project, downloadBundleTask, workspaceExtension,
			providedModulesConfiguration);

		Dockerfile dockerfile = _addTaskCreateDockerfile(
			project, workspaceExtension);

		_addTaskBuildImage(dockerfile, workspaceExtension);

		_addTaskCreateContainer(project, workspaceExtension);
		_addTaskDeployToDocker(
			project, workspaceExtension, providedModulesConfiguration);
		_addTaskLogsContainer(project);
		_addTaskPullImage(project, workspaceExtension);
		_addTaskRemoveContainer(project);
		_addTaskStartContainer(project);
		_addTaskStopContainer(project);
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	public void setDefaultRepositoryEnabled(boolean defaultRepositoryEnabled) {
		_defaultRepositoryEnabled = defaultRepositoryEnabled;
	}

	private Configuration _addConfigurationProvidedModules(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, PROVIDED_MODULES_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures additional 3rd-party OSGi modules to add to Liferay.");
		configuration.setTransitive(false);
		configuration.setVisible(true);

		return configuration;
	}

	private DockerBuildImage _addTaskBuildImage(
		Dockerfile dockerfile, WorkspaceExtension workspaceExtension) {

		Project project = dockerfile.getProject();

		DockerBuildImage dockerBuildImage = GradleUtil.addTask(
			project, BUILD_IMAGE_TASK_NAME, DockerBuildImage.class);

		dockerBuildImage.dependsOn(dockerfile, PULL_IMAGE_TASK_NAME);

		dockerBuildImage.setDescription(
			"Builds a docker image with all modules/configs deployed");

		dockerBuildImage.setInputDir(workspaceExtension.getDockerDir());

		dockerBuildImage.setGroup(DOCKER_GROUP);

		return dockerBuildImage;
	}

	private Copy _addTaskCopyBundle(
		Project project, String taskName, Download downloadBundleTask,
		final WorkspaceExtension workspaceExtension,
		Configuration providedModulesConfiguration) {

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

		copy.from(
			providedModulesConfiguration,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.into("osgi/modules");
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

	private DockerCreateContainer _addTaskCreateContainer(
		Project project, WorkspaceExtension workspaceExtension) {

		DockerCreateContainer dockerCreateContainer =
			(DockerCreateContainer)GradleUtil.addTask(
				project, CREATE_CONTAINER_TASK_NAME,
				DockerCreateContainer.class);

		dockerCreateContainer.dependsOn(
			REMOVE_CONTAINER_TASK_NAME, DEPLOY_TO_CONTAINER_TASK_NAME,
			PULL_IMAGE_TASK_NAME);

		Map<String, String> binds = new HashMap<>();

		File dockerDir = workspaceExtension.getDockerDir();

		binds.put(dockerDir.getAbsolutePath(), "/etc/liferay/mount");

		dockerCreateContainer.setBinds(binds);

		dockerCreateContainer.setContainerName(
			project.getName() + "-liferayapp");

		dockerCreateContainer.setDescription(
			"Creates a container from your liferay image and mounts " +
				dockerDir.toString() + " to /etc/liferay.");

		ArrayList<String> ports = new ArrayList<>();

		ports.add("8080:8080");
		ports.add("11311:11311");

		dockerCreateContainer.setPortBindings(ports);

		dockerCreateContainer.targetImageId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return workspaceExtension.getDockerImageLiferay();
				}

			});

		return dockerCreateContainer;
	}

	private Dockerfile _addTaskCreateDockerfile(
		Project project, final WorkspaceExtension workspaceExtension) {

		Dockerfile dockerfileTask = GradleUtil.addTask(
			project, CREATE_DOCKERFILE_TASK_NAME, Dockerfile.class);

		dockerfileTask.dependsOn(DEPLOY_TO_CONTAINER_TASK_NAME);

		dockerfileTask.setDescription(
			"Creates a dockerfile to build the project Docker image.");
		dockerfileTask.setGroup(DOCKER_GROUP);

		dockerfileTask.from(workspaceExtension.getDockerImageLiferay());

		dockerfileTask.instruction(
			"COPY --chown=liferay:liferay deploy /etc/liferay/mount/deploy");
		dockerfileTask.instruction(
			"COPY --chown=liferay:liferay files /etc/liferay/mount/files");

		dockerfileTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					try {
						File destinationDir = workspaceExtension.getDockerDir();

						File dir = new File(destinationDir, "files");

						if (!dir.exists()) {
							dir.mkdirs();
						}

						File file = new File(dir, ".touch");

						file.createNewFile();

						dir = new File(destinationDir, "deploy");

						if (!dir.exists()) {
							dir.mkdirs();
						}

						file = new File(dir, ".touch");

						file.createNewFile();
					}
					catch (IOException ioe) {
						Logger logger = dockerfileTask.getLogger();

						if (logger.isWarnEnabled()) {
							StringBuilder sb = new StringBuilder();

							sb.append("Could not create a placeholder file. ");
							sb.append("Please make sure you have at least ");
							sb.append("one config or the buildImage task ");
							sb.append("will fail.");

							logger.warn(sb.toString());
						}
					}
				}

			});

		return dockerfileTask;
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

		createTokenTask.setPasswordFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return workspaceExtension.getBundleTokenPasswordFile();
				}

			});

		return createTokenTask;
	}

	private Copy _addTaskDeployToDocker(
		Project project, final WorkspaceExtension workspaceExtension,
		Configuration providedModulesConfiguration) {

		Copy copy = GradleUtil.addTask(
			project, DEPLOY_TO_CONTAINER_TASK_NAME, Copy.class);

		copy.setDescription(
			"Copy docker configs and provided configurations to docker dir");

		copy.setDestinationDir(workspaceExtension.getDockerDir());

		copy.from(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						workspaceExtension.getConfigsDir(), "docker");
				}

			},
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.into("files");
				}

			});

		copy.from(
			providedModulesConfiguration,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.into("deploy");
				}

			});

		return copy;
	}

	private Copy _addTaskDistBundle(
		final Project project, Download downloadBundleTask,
		WorkspaceExtension workspaceExtension,
		Configuration providedModulesConfiguration) {

		Copy copy = _addTaskCopyBundle(
			project, DIST_BUNDLE_TASK_NAME, downloadBundleTask,
			workspaceExtension, providedModulesConfiguration);

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

		download.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Logger logger = download.getLogger();
					Project project = download.getProject();

					if (workspaceExtension.isBundleTokenDownload()) {
						String token = FileUtil.read(
							createTokenTask.getTokenFile());

						token = token.trim();

						download.header(
							HttpHeaders.AUTHORIZATION, "Bearer " + token);
					}

					for (Object src : _getSrcList(download)) {
						File file = null;

						try {
							URI uri = project.uri(src);

							file = project.file(uri);
						}
						catch (Exception e) {
							if (logger.isDebugEnabled()) {
								logger.debug(e.getMessage(), e);
							}
						}

						if ((file == null) || !file.exists()) {
							continue;
						}

						File destinationFile = download.getDest();

						if (destinationFile.isDirectory()) {
							destinationFile = new File(
								destinationFile, file.getName());
						}

						if (destinationFile.equals(file)) {
							throw new GradleException(
								"Download source " + file +
									" and destination " + destinationFile +
										" cannot be the same");
						}
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

					File destinationDir =
						workspaceExtension.getBundleCacheDir();

					destinationDir.mkdirs();

					download.dest(destinationDir);

					List<?> srcList = _getSrcList(download);

					if (!srcList.isEmpty()) {
						return;
					}

					String bundleUrl = workspaceExtension.getBundleUrl();

					try {
						if (bundleUrl.startsWith("file:")) {
							URL url = new URL(bundleUrl);

							File file = new File(url.getFile());

							file = file.getAbsoluteFile();

							URI uri = file.toURI();

							bundleUrl = uri.toASCIIString();
						}
						else {
							bundleUrl = bundleUrl.replace(" ", "%20");
						}

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
		final WorkspaceExtension workspaceExtension,
		Configuration configurationOsgiModules) {

		Copy copy = _addTaskCopyBundle(
			project, INIT_BUNDLE_TASK_NAME, downloadBundleTask,
			workspaceExtension, configurationOsgiModules);

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

	private DockerLogsContainer _addTaskLogsContainer(Project project) {
		DockerLogsContainer dockerLogsContainer =
			(DockerLogsContainer)GradleUtil.addTask(
				project, LOGS_CONTAINER_TASK_NAME, DockerLogsContainer.class);

		dockerLogsContainer.setDescription("Logs the project docker container");
		dockerLogsContainer.setFollow(true);
		dockerLogsContainer.setTailAll(true);

		dockerLogsContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return project.getName() + "-liferayapp";
				}

			});

		return dockerLogsContainer;
	}

	private DockerPullImage _addTaskPullImage(
		Project project, WorkspaceExtension workspaceExtension) {

		DockerPullImage dockerPullImage = (DockerPullImage)GradleUtil.addTask(
			project, PULL_IMAGE_TASK_NAME, DockerPullImage.class);

		dockerPullImage.setDescription("Pull the docker image");

		String dockerImageLiferay = workspaceExtension.getDockerImageLiferay();

		int i = dockerImageLiferay.indexOf(":");

		String repository = dockerImageLiferay.substring(0, i);

		dockerPullImage.setRepository(repository);

		String tag = dockerImageLiferay.substring(i + 1);

		dockerPullImage.setTag(tag);

		return dockerPullImage;
	}

	private DockerRemoveContainer _addTaskRemoveContainer(Project project) {
		DockerRemoveContainer dockerRemoveContainer = GradleUtil.addTask(
			project, REMOVE_CONTAINER_TASK_NAME, DockerRemoveContainer.class);

		dockerRemoveContainer.setDescription(
			"Removes the project docker container");
		dockerRemoveContainer.setForce(true);
		dockerRemoveContainer.setRemoveVolumes(true);

		dockerRemoveContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return project.getName() + "-liferayapp";
				}

			});

		dockerRemoveContainer.setOnError(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Exception e) {
					Logger logger = project.getLogger();

					if (logger.isWarnEnabled()) {
						logger.warn(
							"No container with ID '" + project.getName() +
								"-liferayapp' found.");
					}
				}

			});

		return dockerRemoveContainer;
	}

	private DockerStartContainer _addTaskStartContainer(Project project) {
		DockerStartContainer dockerStartContainer =
			(DockerStartContainer)GradleUtil.addTask(
				project, START_CONTAINER_TASK_NAME, DockerStartContainer.class);

		dockerStartContainer.setDescription(
			"Starts the project docker container");

		dockerStartContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return project.getName() + "-liferayapp";
				}

			});

		return dockerStartContainer;
	}

	private DockerStopContainer _addTaskStopContainer(Project project) {
		DockerStopContainer dockerStopContainer =
			(DockerStopContainer)GradleUtil.addTask(
				project, STOP_CONTAINER_TASK_NAME, DockerStopContainer.class);

		dockerStopContainer.setDescription(
			"Stops the project docker container");

		dockerStopContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return project.getName() + "-liferayapp";
				}

			});

		return dockerStopContainer;
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

					return project.zipTree(file);
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

	private List<?> _getSrcList(Download download) {
		Object src = download.getSrc();

		if (src == null) {
			return Collections.emptyList();
		}

		if (src instanceof List<?>) {
			return (List<?>)src;
		}

		return Collections.singletonList(src);
	}

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private boolean _defaultRepositoryEnabled;

}