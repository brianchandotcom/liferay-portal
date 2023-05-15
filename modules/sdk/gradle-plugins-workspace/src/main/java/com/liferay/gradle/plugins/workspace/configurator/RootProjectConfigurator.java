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

package com.liferay.gradle.plugins.workspace.configurator;

import com.bmuschko.gradle.docker.DockerRegistryCredentials;
import com.bmuschko.gradle.docker.DockerRemoteApiPlugin;
import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerLogsContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerRemoveContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer;
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer;
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage;
import com.bmuschko.gradle.docker.tasks.image.DockerPullImage;
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage;
import com.bmuschko.gradle.docker.tasks.image.DockerRemoveImage;
import com.bmuschko.gradle.docker.tasks.image.DockerTagImage;
import com.bmuschko.gradle.docker.tasks.image.Dockerfile;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.task.NpmInstallTask;
import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.workspace.LiferayWorkspaceYarnPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.docker.DockerPruneImage;
import com.liferay.gradle.plugins.workspace.internal.configurator.TargetPlatformRootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.gradle.plugins.workspace.task.CreateTokenTask;
import com.liferay.gradle.plugins.workspace.task.InitBundleTask;
import com.liferay.gradle.plugins.workspace.task.VerifyBundleTask;
import com.liferay.gradle.plugins.workspace.task.VerifyProductTask;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import de.undercouch.gradle.tasks.download.Download;

import groovy.lang.Closure;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.SetProperty;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.WorkResult;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Compression;
import org.gradle.api.tasks.bundling.Tar;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 * @author Simon Jiang
 * @author Seiphon Wang
 * @author Kevin Lee
 */
@SuppressWarnings("deprecation")
public class RootProjectConfigurator implements Plugin<Project> {

	public static final String BUILD_DOCKER_IMAGE_TASK_NAME =
		"buildDockerImage";

	public static final String BUNDLE_CONFIGURATION_NAME = "bundle";

	public static final String BUNDLE_GROUP = "bundle";

	public static final String BUNDLE_SUPPORT_CONFIGURATION_NAME =
		"bundleSupport";

	public static final String CLEAN_DOCKER_IMAGE_TASK_NAME =
		"cleanDockerImage";

	public static final String CLEAN_TASK_NAME =
		LifecycleBasePlugin.CLEAN_TASK_NAME;

	public static final String CREATE_DOCKER_CONTAINER_TASK_NAME =
		"createDockerContainer";

	public static final String CREATE_DOCKERFILE_ALL_TASK_NAME =
		"createDockerfileAll";

	public static final String CREATE_DOCKERFILE_TASK_NAME = "createDockerfile";

	public static final String CREATE_TOKEN_TASK_NAME = "createToken";

	public static final String DIST_BUNDLE_TAR_ALL_TASK_NAME =
		"distBundleTarAll";

	public static final String DIST_BUNDLE_TAR_TASK_NAME = "distBundleTar";

	public static final String DIST_BUNDLE_TASK_NAME = "distBundle";

	public static final String DIST_BUNDLE_ZIP_ALL_TASK_NAME =
		"distBundleZipAll";

	public static final String DIST_BUNDLE_ZIP_TASK_NAME = "distBundleZip";

	public static final String DOCKER_DEPLOY_TASK_NAME = "dockerDeploy";

	public static final String DOCKER_GROUP = "docker";

	public static final String DOWNLOAD_BUNDLE_TASK_NAME = "downloadBundle";

	public static final String FORMAT_SOURCE_UPGRADE_TASK_NAME =
		"formatSourceUpgrade";

	public static final String INIT_BUNDLE_TASK_NAME = "initBundle";

	public static final String LIFERAY_CONFIGS_DIR_NAME = "configs";

	public static final String LOGS_DOCKER_CONTAINER_TASK_NAME =
		"logsDockerContainer";

	public static final String PROVIDED_MODULES_CONFIGURATION_NAME =
		"providedModules";

	public static final String PRUNE_DOCKER_IMAGE_TASK_NAME =
		"pruneDockerImage";

	public static final String PULL_DOCKER_IMAGE_TASK_NAME = "pullDockerImage";

	public static final String PUSH_DOCKER_IMAGE_TASK_NAME = "pushDockerImage";

	public static final String REMOVE_DOCKER_CONTAINER_TASK_NAME =
		"removeDockerContainer";

	public static final String START_DOCKER_CONTAINER_TASK_NAME =
		"startDockerContainer";

	public static final String START_REGISTRY_CONTAINER_TASK_NAME =
		"startRegistryContainer";

	public static final String STOP_DOCKER_CONTAINER_TASK_NAME =
		"stopDockerContainer";

	public static final String TAG_DOCKER_IMAGE_TASK_NAME = "tagDockerImage";

	public static final String VERIFY_BUNDLE_TASK_NAME = "verifyBundle";

	public static final String VERIFY_PRODUCT_TASK_NAME = "verifyProduct";

	/**
	 * @deprecated As of 1.4.0, replaced by {@link
	 *             #RootProjectConfigurator(Settings)}
	 */
	@Deprecated
	public RootProjectConfigurator() {
	}

	public RootProjectConfigurator(Settings settings) {
		_bundleCheckSumMD5 = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + "bundle.checksum.md5",
			null);
		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + "default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		_configureWorkspaceExtension(project, workspaceExtension);

		GradleUtil.applyPlugin(project, DockerRemoteApiPlugin.class);
		GradleUtil.applyPlugin(project, LifecycleBasePlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);

		String nodePackageManager = workspaceExtension.getNodePackageManager();

		if (nodePackageManager.equals("yarn")) {
			GradleUtil.applyPlugin(project, LiferayWorkspaceYarnPlugin.class);
		}
		else {
			_configureNpmProject(project);
		}

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		Configuration bundleSupportConfiguration =
			_addConfigurationBundleSupport(project);

		Configuration providedModulesConfiguration =
			_addConfigurationProvidedModules(project);

		TargetPlatformRootProjectConfigurator.INSTANCE.apply(project);

		_addTaskCreateToken(project);

		VerifyProductTask verifyProductTask = _addTaskVerifyProduct(
			project, workspaceExtension);

		Download downloadBundleTask = _addTaskDownloadBundle(
			project, verifyProductTask, workspaceExtension);

		VerifyBundleTask verifyBundleTask = _addTaskVerifyBundle(
			project, verifyProductTask, downloadBundleTask, workspaceExtension);

		Copy distBundleTask = _addTaskDistBundle(
			project, downloadBundleTask, DIST_BUNDLE_TASK_NAME,
			workspaceExtension, null, providedModulesConfiguration);

		_addTasksDistBundleArchive(project, distBundleTask, workspaceExtension);

		_addTasksDistBundleEnvironments(
			project, downloadBundleTask, workspaceExtension,
			providedModulesConfiguration);

		_addTaskInitBundle(
			project, verifyProductTask, downloadBundleTask, verifyBundleTask,
			workspaceExtension, bundleSupportConfiguration,
			providedModulesConfiguration);

		_addDockerTasks(
			project, workspaceExtension, providedModulesConfiguration,
			verifyProductTask);

		_addTaskFormatSourceUpgrade(project);
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	public void setDefaultRepositoryEnabled(boolean defaultRepositoryEnabled) {
		_defaultRepositoryEnabled = defaultRepositoryEnabled;
	}

	private Configuration _addConfigurationBundleSupport(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, BUNDLE_SUPPORT_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesBundleSupport(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Bundle Support for this project.");
		configuration.setVisible(false);

		return configuration;
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

	private void _addDependenciesBundleSupport(Project project) {
		GradleUtil.addDependency(
			project, BUNDLE_SUPPORT_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.bundle.support", "latest.release");
	}

	private void _addDockerTasks(
		Project project, WorkspaceExtension workspaceExtension,
		Configuration providedModulesConfiguration,
		VerifyProductTask verifyProductTask) {

		Copy dockerDeploy = _addTaskDockerDeploy(
			project, workspaceExtension, providedModulesConfiguration);

		Dockerfile dockerfile = _addTaskCreateDockerfile(
			project, CREATE_DOCKERFILE_TASK_NAME, workspaceExtension,
			dockerDeploy, verifyProductTask);

		DockerBuildImage dockerBuildImage = _addTaskBuildDockerImage(
			dockerfile, workspaceExtension, verifyProductTask);

		DockerStopContainer dockerStopContainer = _addTaskStopDockerContainer(
			project);

		DockerRemoveContainer dockerRemoveContainer =
			_addTaskRemoveDockerContainer(project, dockerStopContainer);

		DockerCreateContainer dockerCreateContainer =
			_addTaskCreateDockerContainer(
				project, workspaceExtension, dockerBuildImage,
				dockerRemoveContainer, verifyProductTask);

		_addTaskStartDockerContainer(project, dockerCreateContainer);

		_addTaskLogsDockerContainer(project);

		_addTaskPullDockerImage(project, workspaceExtension, verifyProductTask);

		DockerTagImage dockerTagImage = _addTaskDockerTagImage(
			project, workspaceExtension, dockerBuildImage);

		_addTaskPushDockerImage(project, workspaceExtension, dockerTagImage);
	}

	private DockerBuildImage _addTaskBuildDockerImage(
		Dockerfile dockerfile, WorkspaceExtension workspaceExtension,
		VerifyProductTask verifyProductTask) {

		Project project = dockerfile.getProject();

		DockerBuildImage dockerBuildImage = GradleUtil.addTask(
			project, BUILD_DOCKER_IMAGE_TASK_NAME, DockerBuildImage.class);

		dockerBuildImage.dependsOn(verifyProductTask, dockerfile);
		dockerBuildImage.mustRunAfter(verifyProductTask);

		DirectoryProperty inputDirectoryProperty =
			dockerBuildImage.getInputDir();

		inputDirectoryProperty.set(workspaceExtension.getDockerDir());

		Property<Boolean> pullProperty = dockerBuildImage.getPull();

		pullProperty.set(workspaceExtension.getDockerPullPolicy());

		if (Objects.nonNull(
				workspaceExtension.getDockerLocalRegistryAddress())) {

			DockerRegistryCredentials dockerRegistryCredentials =
				dockerBuildImage.getRegistryCredentials();

			String dockerUserName = workspaceExtension.getDockerUserName();

			if (Objects.nonNull(dockerUserName)) {
				Property<String> userNameProperty =
					dockerRegistryCredentials.getUsername();

				userNameProperty.set(dockerUserName);
			}

			String dockerUserAccessToken =
				workspaceExtension.getDockerUserAccessToken();

			if (Objects.nonNull(dockerUserAccessToken)) {
				Property<String> passwordProperty =
					dockerRegistryCredentials.getPassword();

				passwordProperty.set(dockerUserAccessToken);
			}
		}

		dockerBuildImage.setDescription(
			"Builds a child docker image from Liferay base image with all " +
				"configs deployed.");
		dockerBuildImage.setGroup(DOCKER_GROUP);

		DockerRemoveImage dockerRemoveImage = GradleUtil.addTask(
			project, CLEAN_DOCKER_IMAGE_TASK_NAME, DockerRemoveImage.class);

		dockerRemoveImage.dependsOn(REMOVE_DOCKER_CONTAINER_TASK_NAME);
		dockerRemoveImage.setDescription("Removes the Docker image.");
		dockerRemoveImage.setGroup(DOCKER_GROUP);

		Property<Boolean> forceProperty = dockerRemoveImage.getForce();

		forceProperty.set(true);

		dockerRemoveImage.onError(
			new Action<Throwable>() {

				@Override
				public void execute(Throwable throwable) {
					Logger logger = project.getLogger();

					if (logger.isWarnEnabled()) {
						logger.warn(
							"No image with ID '" + _getDockerImageId(project) +
								"' found.");
					}
				}

			});

		DockerPruneImage dockerPruneImage = GradleUtil.addTask(
			project, PRUNE_DOCKER_IMAGE_TASK_NAME, DockerPruneImage.class);

		dockerPruneImage.setDescription("Prunes the Docker image.");
		dockerPruneImage.setGroup(DOCKER_GROUP);

		Project rootProject = project.getRootProject();

		rootProject.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project p) {
					String dockerImageId = _getDockerImageId(project);

					SetProperty<String> setProperty =
						dockerBuildImage.getImages();

					setProperty.add(dockerImageId);

					Property<String> dockerPruneImageIdProperty =
						dockerPruneImage.getImageId();

					dockerPruneImageIdProperty.set(dockerImageId);

					Property<String> dockerRemoveImageIdProperty =
						dockerRemoveImage.getImageId();

					dockerRemoveImageIdProperty.set(dockerImageId);
				}

			});

		Task cleanTask = GradleUtil.getTask(
			project, LifecycleBasePlugin.CLEAN_TASK_NAME);

		cleanTask.dependsOn(dockerRemoveImage);

		return dockerBuildImage;
	}

	@SuppressWarnings("serial")
	private Copy _addTaskCopyBundle(
		Project project, String taskName, Download downloadBundleTask,
		final WorkspaceExtension workspaceExtension, String environment,
		Configuration providedModulesConfiguration) {

		Copy copy = GradleUtil.addTask(project, taskName, Copy.class);

		_configureTaskCopyBundleFromConfig(
			project, copy,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						workspaceExtension.getConfigsDir(),
						(environment == null) ?
							workspaceExtension.getEnvironment() : environment);
				}

			});

		_configureTaskCopyBundleFromConfig(
			project, copy,
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

	private DockerCreateContainer _addTaskCreateDockerContainer(
		Project project, WorkspaceExtension workspaceExtension,
		DockerBuildImage dockerBuildImage,
		DockerRemoveContainer dockerRemoveContainer,
		VerifyProductTask verifyProductTask) {

		DockerCreateContainer dockerCreateContainer = GradleUtil.addTask(
			project, CREATE_DOCKER_CONTAINER_TASK_NAME,
			DockerCreateContainer.class);

		dockerCreateContainer.dependsOn(verifyProductTask, dockerBuildImage);
		dockerCreateContainer.mustRunAfter(
			verifyProductTask, dockerRemoveContainer);

		File dockerDir = workspaceExtension.getDockerDir();

		File clientExtensionsDir = new File(dockerDir, "client-extensions");
		File deployDir = new File(dockerDir, "deploy");
		File workDir = new File(dockerDir, "work");

		String clientExtensionsPath = clientExtensionsDir.getAbsolutePath();
		String deployPath = deployDir.getAbsolutePath();
		String dockerPath = dockerDir.getAbsolutePath();
		String workPath = workDir.getAbsolutePath();

		if (OSDetector.isWindows()) {
			String prefix = FilenameUtils.getPrefix(dockerPath);

			if (prefix.contains(":")) {
				clientExtensionsPath =
					'/' + clientExtensionsPath.replace(":", "");
				deployPath = '/' + deployPath.replace(":", "");
				dockerPath = '/' + dockerPath.replace(":", "");
				workPath = '/' + workPath.replace(":", "");
			}

			clientExtensionsPath = clientExtensionsPath.replace('\\', '/');
			deployPath = deployPath.replace('\\', '/');
			dockerPath = dockerPath.replace('\\', '/');
			workPath = workPath.replace('\\', '/');
		}

		DockerCreateContainer.HostConfig hostConfig =
			dockerCreateContainer.getHostConfig();

		Property<Boolean> autoRemoveProperty = hostConfig.getAutoRemove();

		autoRemoveProperty.set(true);

		MapProperty<String, String> bindsMapProperty = hostConfig.getBinds();

		bindsMapProperty.put(
			clientExtensionsPath, "/opt/liferay/osgi/client-extensions");
		bindsMapProperty.put(deployPath, "/mnt/liferay/deploy");
		bindsMapProperty.put(workPath, "/opt/liferay/work");

		dockerCreateContainer.setDescription(
			"Creates a Docker container from your built image and mounts " +
				dockerPath + " to /mnt/liferay.");
		dockerCreateContainer.setGroup(DOCKER_GROUP);

		ListProperty<String> portBindingsListProperty =
			hostConfig.getPortBindings();

		portBindingsListProperty.add("8000:8000");
		portBindingsListProperty.add("8080:8080");
		portBindingsListProperty.add("11311:11311");

		dockerCreateContainer.targetImageId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getDockerImageId(project);
				}

			});

		dockerCreateContainer.withEnvVar("JPDA_ADDRESS", "0.0.0.0:8000");
		dockerCreateContainer.withEnvVar("LIFERAY_JPDA_ENABLED", "true");
		dockerCreateContainer.withEnvVar(
			_getEnvVarOverride("module.framework.properties.osgi.console"),
			"0.0.0.0:11311");
		dockerCreateContainer.withEnvVar(
			"LIFERAY_WORKSPACE_ENVIRONMENT",
			workspaceExtension.getEnvironment());

		Property<String> containerNameProperty =
			dockerCreateContainer.getContainerName();

		containerNameProperty.set(_getDockerContainerId(project));

		Task cleanTask = GradleUtil.getTask(
			project, LifecycleBasePlugin.CLEAN_TASK_NAME);

		cleanTask.dependsOn(dockerRemoveContainer);

		return dockerCreateContainer;
	}

	private Dockerfile _addTaskCreateDockerfile(
		Project project, String taskName,
		final WorkspaceExtension workspaceExtension, Copy dockerDeploy,
		VerifyProductTask verifyProductTask) {

		Dockerfile dockerfile = GradleUtil.addTask(
			project, taskName, Dockerfile.class);

		dockerfile.dependsOn(verifyProductTask, dockerDeploy);
		dockerfile.mustRunAfter(verifyProductTask);

		dockerfile.instruction(
			"ENV LIFERAY_WORKSPACE_ENVIRONMENT=" +
				workspaceExtension.getEnvironment());

		dockerfile.instruction(
			"COPY --chown=liferay:liferay client-extensions /home/liferay" +
				"/osgi/client-extensions");
		dockerfile.instruction(
			"COPY --chown=liferay:liferay deploy /mnt/liferay/deploy");
		dockerfile.instruction(
			"COPY --chown=liferay:liferay patching /mnt/liferay/patching");
		dockerfile.instruction(
			"COPY --chown=liferay:liferay scripts /mnt/liferay/scripts");
		dockerfile.instruction(
			"COPY --chown=liferay:liferay " + LIFERAY_CONFIGS_DIR_NAME +
				" /home/liferay/configs");
		dockerfile.instruction(
			"COPY --chown=liferay:liferay " + _LIFERAY_IMAGE_SETUP_SCRIPT +
				" /usr/local/liferay/scripts/pre-configure/" +
					_LIFERAY_IMAGE_SETUP_SCRIPT);

		File file = project.file("Dockerfile.ext");

		if (file.exists()) {
			dockerfile.instructionsFromTemplate(file);
		}

		dockerfile.setDescription(
			"Creates a Dockerfile to build the Liferay Workspace Docker " +
				"image.");
		dockerfile.setGroup(DOCKER_GROUP);

		dockerfile.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					try {
						File destinationDir = workspaceExtension.getDockerDir();

						_createTouchFile(
							new File(destinationDir, "client-extensions"));
						_createTouchFile(new File(destinationDir, "configs"));
						_createTouchFile(new File(destinationDir, "deploy"));
						_createTouchFile(new File(destinationDir, "patching"));
						_createTouchFile(new File(destinationDir, "scripts"));
						_createTouchFile(new File(destinationDir, "work"));

						File file = new File(
							destinationDir, _LIFERAY_IMAGE_SETUP_SCRIPT);

						try {
							String template = _loadTemplate(
								_LIFERAY_IMAGE_SETUP_SCRIPT + ".tpl");

							Files.write(file.toPath(), template.getBytes());
						}
						catch (IOException ioException) {
							throw new GradleException(
								"Unable to write script file: " +
									file.getAbsolutePath(),
								ioException);
						}
					}
					catch (IOException ioException) {
						Logger logger = dockerfile.getLogger();

						if (logger.isWarnEnabled()) {
							StringBuilder sb = new StringBuilder();

							sb.append("Could not create a placeholder file. ");
							sb.append("Please make sure you have at least ");
							sb.append("one config or the buildDockerImage ");
							sb.append("task will fail.");

							logger.warn(sb.toString());
						}
					}
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project p) {
					ListProperty<Dockerfile.Instruction> instructions =
						dockerfile.getInstructions();

					List<Dockerfile.Instruction> originalInstructions =
						new ArrayList<>(instructions.get());

					String dockerLocalRegistryAddress =
						workspaceExtension.getDockerLocalRegistryAddress();

					if (Objects.nonNull(workspaceExtension.getProduct())) {
						WorkspaceExtension.ProductInfo productInfo =
							workspaceExtension.getProductInfo();

						if (Objects.nonNull(productInfo)) {
							String dockerImageLiferay =
								productInfo.getLiferayDockerImage();

							if (!Objects.equals(
									workspaceExtension.getDockerImageLiferay(),
									productInfo.getLiferayDockerImage()) &&
								Objects.nonNull(
									workspaceExtension.
										getDockerImageLiferay())) {

								dockerImageLiferay =
									workspaceExtension.getDockerImageLiferay();
							}

							if (Objects.nonNull(dockerLocalRegistryAddress)) {
								dockerImageLiferay = dockerImageLiferay.replace(
									"liferay", dockerLocalRegistryAddress);
							}

							Dockerfile.FromInstruction baseImage =
								new Dockerfile.FromInstruction(
									new Dockerfile.From(dockerImageLiferay));

							originalInstructions.add(0, baseImage);
						}
					}
					else {
						String dockerImageLiferay =
							workspaceExtension.getDockerImageLiferay();

						if (Objects.nonNull(dockerLocalRegistryAddress)) {
							dockerImageLiferay = dockerImageLiferay.replace(
								"liferay", dockerLocalRegistryAddress);
						}

						Dockerfile.FromInstruction baseImage =
							new Dockerfile.FromInstruction(
								new Dockerfile.From(dockerImageLiferay));

						originalInstructions.add(0, baseImage);
					}

					instructions.set(originalInstructions);
				}

			});

		return dockerfile;
	}

	private CreateTokenTask _addTaskCreateToken(Project project) {
		CreateTokenTask createTokenTask = GradleUtil.addTask(
			project, CREATE_TOKEN_TASK_NAME, CreateTokenTask.class);

		createTokenTask.setDescription(
			"This task is deprecated and it will be removed in future.");

		return createTokenTask;
	}

	private Copy _addTaskDistBundle(
		final Project project, Download downloadBundleTask, String taskName,
		WorkspaceExtension workspaceExtension, String environment,
		Configuration providedModulesConfiguration) {

		Copy copy = _addTaskCopyBundle(
			project, taskName, downloadBundleTask, workspaceExtension,
			environment, providedModulesConfiguration);

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

	@SuppressWarnings("serial")
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

		DirectoryProperty destinationDirectoryProperty =
			task.getDestinationDirectory();

		destinationDirectoryProperty.set(project.getBuildDir());

		task.setGroup(BUNDLE_GROUP);

		return task;
	}

	@SuppressWarnings("serial")
	private Copy _addTaskDockerDeploy(
		Project project, WorkspaceExtension workspaceExtension,
		Configuration providedModulesConfiguration) {

		Copy copy = GradleUtil.addTask(
			project, DOCKER_DEPLOY_TASK_NAME, Copy.class);

		copy.setDescription(
			"Copy the Liferay configs and provided configurations to the " +
				"docker build directory.");
		copy.setGroup(DOCKER_GROUP);

		copy.setDestinationDir(workspaceExtension.getDockerDir());

		copy.from(
			providedModulesConfiguration,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.into("deploy");
				}

			});

		File configsDir = workspaceExtension.getConfigsDir();

		if (configsDir.exists()) {
			List<String> commonConfigDirNames = Arrays.asList(
				"common", "docker");

			File[] configDirs = configsDir.listFiles(
				(dir, name) -> {
					File file = new File(dir, name);

					if (!file.isDirectory() ||
						commonConfigDirNames.contains(name)) {

						return false;
					}

					return true;
				});

			if ((configDirs == null) || (configDirs.length == 0)) {
				throw new GradleException(
					"The 'configs' directory must contain one directory not " +
						"named: " + StringUtil.toString(commonConfigDirNames));
			}

			for (String commonConfigDirName : commonConfigDirNames) {
				for (File configDir : configDirs) {
					copy.from(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									configsDir, commonConfigDirName);
							}

						},
						new Closure<Void>(project) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.into(
									LIFERAY_CONFIGS_DIR_NAME + "/" +
										configDir.getName());
							}

						});
				}
			}

			copy.from(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return configsDir;
					}

				},
				new Closure<Void>(project) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						copySpec.exclude(commonConfigDirNames);
						copySpec.into(LIFERAY_CONFIGS_DIR_NAME);
					}

				});
		}

		Task deployTask = GradleUtil.addTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME, Copy.class);

		deployTask.finalizedBy(copy);

		return copy;
	}

	private DockerTagImage _addTaskDockerTagImage(
		Project project, WorkspaceExtension workspaceExtension,
		DockerBuildImage buildDockerImage) {

		DockerTagImage dockerTagImage = GradleUtil.addTask(
			project, TAG_DOCKER_IMAGE_TASK_NAME, DockerTagImage.class);

		dockerTagImage.setDescription("Tag the Docker image.");
		dockerTagImage.setGroup(DOCKER_GROUP);

		dockerTagImage.dependsOn(buildDockerImage);

		Property<String> imageIdProperty = dockerTagImage.getImageId();

		imageIdProperty.set(workspaceExtension.getDockerImageId());

		String dockerUserName = workspaceExtension.getDockerUserName();

		Property<String> repositoryProperty = dockerTagImage.getRepository();

		if (Objects.nonNull(
				workspaceExtension.getDockerLocalRegistryAddress())) {

			repositoryProperty.set(
				workspaceExtension.getDockerLocalRegistryAddress() + "/" +
					workspaceExtension.getDockerImageId());
		}
		else if (Objects.nonNull(dockerUserName)) {
			repositoryProperty.set(
				dockerUserName + "/" + workspaceExtension.getDockerImageId());
		}
		else {
			repositoryProperty.set(workspaceExtension.getDockerImageId());
		}

		Property<String> tagProperty = dockerTagImage.getTag();

		tagProperty.set(_getDockerImageTag(project, workspaceExtension));

		return dockerTagImage;
	}

	private Download _addTaskDownloadBundle(
		final Project project, VerifyProductTask verifyProductTask,
		final WorkspaceExtension workspaceExtension) {

		final Download download = GradleUtil.addTask(
			project, DOWNLOAD_BUNDLE_TASK_NAME, Download.class);

		download.dependsOn(verifyProductTask);

		download.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return Validator.isNotNull(
						workspaceExtension.getBundleUrl());
				}

			});

		download.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Logger logger = download.getLogger();
					Project project = download.getProject();

					for (Object src : _getSrcList(download)) {
						File file = null;

						try {
							URL srcURL = (URL)src;

							if (Objects.equals(srcURL.getProtocol(), "file")) {
								URI uri = project.uri(src);

								file = project.file(uri);
							}
						}
						catch (Exception exception) {
							if (logger.isDebugEnabled()) {
								logger.debug(exception.getMessage(), exception);
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
					_configureDownloadTask(download, workspaceExtension);
				}

			});

		return download;
	}

	private FormatSourceTask _addTaskFormatSourceUpgrade(Project project) {
		FormatSourceTask formatSourceTask = GradleUtil.addTask(
			project, FORMAT_SOURCE_UPGRADE_TASK_NAME, FormatSourceTask.class);

		formatSourceTask.onlyIf(_skipIfExecutingParentTaskSpec);
		formatSourceTask.setCheckCategoryNames("Upgrade");
		formatSourceTask.setDescription(
			"Runs Liferay Source Formatter to perform Upgrade SF checks.");
		formatSourceTask.setGroup("formatting");

		return formatSourceTask;
	}

	private InitBundleTask _addTaskInitBundle(
		Project project, VerifyProductTask verifyProductTask,
		Download downloadBundleTask, VerifyBundleTask verifyBundleTask,
		final WorkspaceExtension workspaceExtension,
		Configuration bundleSupportConfiguration,
		Configuration osgiModulesConfiguration) {

		InitBundleTask initBundleTask = GradleUtil.addTask(
			project, INIT_BUNDLE_TASK_NAME, InitBundleTask.class);

		initBundleTask.dependsOn(
			verifyProductTask, downloadBundleTask, verifyBundleTask);
		initBundleTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					File homeDir = workspaceExtension.getHomeDir();

					WorkResult workResult = project.copy(
						copySpec -> {
							copySpec.from(
								new File(
									workspaceExtension.getConfigsDir(),
									"common"),
								new File(
									workspaceExtension.getConfigsDir(),
									workspaceExtension.getEnvironment()));
							copySpec.into(homeDir);

							_configureCopySpecExpandTomcatVersion(
								copySpec, workspaceExtension);
						});

					if (workResult.getDidWork()) {
						project.delete(new File(homeDir, "tomcat"));
					}
				}

			});
		initBundleTask.mustRunAfter(verifyProductTask);
		initBundleTask.setClasspath(bundleSupportConfiguration);
		initBundleTask.setConfigEnvironment(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return workspaceExtension.getEnvironment();
				}

			});
		initBundleTask.setConfigsDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return workspaceExtension.getConfigsDir();
				}

			});
		initBundleTask.setDescription("Downloads and unzips the bundle.");
		initBundleTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return workspaceExtension.getHomeDir();
				}

			});

		if (Validator.isNotNull(workspaceExtension.getBundleUrl())) {
			initBundleTask.setFile(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return _getDownloadFile(downloadBundleTask);
					}

				});
		}

		initBundleTask.setGroup(BUNDLE_GROUP);
		initBundleTask.setProvidedModules(osgiModulesConfiguration);

		return initBundleTask;
	}

	private DockerLogsContainer _addTaskLogsDockerContainer(Project project) {
		DockerLogsContainer dockerLogsContainer = GradleUtil.addTask(
			project, LOGS_DOCKER_CONTAINER_TASK_NAME,
			DockerLogsContainer.class);

		dockerLogsContainer.setDescription("Logs the Docker container.");
		dockerLogsContainer.setGroup(DOCKER_GROUP);

		Property<Boolean> followProperty = dockerLogsContainer.getFollow();

		followProperty.set(true);

		Property<Boolean> tailAllProperty = dockerLogsContainer.getTailAll();

		tailAllProperty.set(true);

		dockerLogsContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getDockerContainerId(project);
				}

			});

		return dockerLogsContainer;
	}

	private DockerPullImage _addTaskPullDockerImage(
		Project project, WorkspaceExtension workspaceExtension,
		VerifyProductTask verifyProductTask) {

		DockerPullImage dockerPullImage = GradleUtil.addTask(
			project, PULL_DOCKER_IMAGE_TASK_NAME, DockerPullImage.class);

		dockerPullImage.dependsOn(verifyProductTask);

		Property<String> imageProperty = dockerPullImage.getImage();

		String dockerUserName = workspaceExtension.getDockerUserName();

		if (Objects.nonNull(
				workspaceExtension.getDockerLocalRegistryAddress())) {

			imageProperty.set(
				workspaceExtension.getDockerLocalRegistryAddress() + "/" +
					workspaceExtension.getDockerImageId());
		}
		else if (Objects.nonNull(dockerUserName)) {
			imageProperty.set(
				dockerUserName + "/" + workspaceExtension.getDockerImageId());
		}
		else {
			imageProperty.set(workspaceExtension.getDockerImageLiferay());
		}

		DockerRegistryCredentials dockerRegistryCredentials =
			dockerPullImage.getRegistryCredentials();

		if (Objects.nonNull(dockerUserName)) {
			Property<String> userNameProperty =
				dockerRegistryCredentials.getUsername();

			userNameProperty.set(dockerUserName);
		}

		String dockerUserAccessToken =
			workspaceExtension.getDockerUserAccessToken();

		if (Objects.nonNull(dockerUserAccessToken)) {
			Property<String> passwordProperty =
				dockerRegistryCredentials.getPassword();

			passwordProperty.set(dockerUserAccessToken);
		}

		dockerPullImage.setDescription("Pull the Docker image.");
		dockerPullImage.setGroup(DOCKER_GROUP);

		return dockerPullImage;
	}

	private DockerPushImage _addTaskPushDockerImage(
		Project project, WorkspaceExtension workspaceExtension,
		DockerTagImage dockerTagImage) {

		DockerPushImage dockerPushImage = GradleUtil.addTask(
			project, PUSH_DOCKER_IMAGE_TASK_NAME, DockerPushImage.class);

		dockerPushImage.dependsOn(dockerTagImage);

		dockerPushImage.setDescription("Push the Docker iamge.");
		dockerPushImage.setGroup(DOCKER_GROUP);

		SetProperty<String> imagesSetProperty = dockerPushImage.getImages();

		String dockerUserName = workspaceExtension.getDockerUserName();

		if (Objects.nonNull(
				workspaceExtension.getDockerLocalRegistryAddress())) {

			imagesSetProperty.add(
				workspaceExtension.getDockerLocalRegistryAddress() + "/" +
					workspaceExtension.getDockerImageId());
		}
		else if (Objects.nonNull(dockerUserName)) {
			imagesSetProperty.add(
				workspaceExtension.getDockerUserName() + "/" +
					workspaceExtension.getDockerImageId());
		}
		else {
			imagesSetProperty.add(workspaceExtension.getDockerImageId());
		}

		DockerRegistryCredentials dockerRegistryCredentials =
			dockerPushImage.getRegistryCredentials();

		if (Objects.nonNull(dockerUserName)) {
			Property<String> userNameProperty =
				dockerRegistryCredentials.getUsername();

			userNameProperty.set(dockerUserName);
		}

		String dockerUserAccessToken =
			workspaceExtension.getDockerUserAccessToken();

		if (Objects.nonNull(dockerUserAccessToken)) {
			Property<String> passwordProperty =
				dockerRegistryCredentials.getPassword();

			passwordProperty.set(dockerUserAccessToken);
		}

		return dockerPushImage;
	}

	private DockerRemoveContainer _addTaskRemoveDockerContainer(
		Project project, DockerStopContainer stopDockerContainer) {

		DockerRemoveContainer dockerRemoveContainer = GradleUtil.addTask(
			project, REMOVE_DOCKER_CONTAINER_TASK_NAME,
			DockerRemoveContainer.class);

		dockerRemoveContainer.setDescription("Removes the Docker container.");
		dockerRemoveContainer.setGroup(DOCKER_GROUP);

		Property<Boolean> forceProperty = dockerRemoveContainer.getForce();

		forceProperty.set(true);

		Property<Boolean> removeVolumesProperty =
			dockerRemoveContainer.getRemoveVolumes();

		removeVolumesProperty.set(true);

		dockerRemoveContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getDockerContainerId(project);
				}

			});

		dockerRemoveContainer.dependsOn(stopDockerContainer);

		dockerRemoveContainer.onError(
			new Action<Throwable>() {

				@Override
				public void execute(Throwable throwable) {
					Logger logger = project.getLogger();

					if (logger.isWarnEnabled()) {
						logger.warn(
							"No container with ID '" +
								_getDockerContainerId(project) + "' found.");
					}
				}

			});

		return dockerRemoveContainer;
	}

	private void _addTasksDistBundleArchive(
		Project project, Copy distBundleTask,
		WorkspaceExtension workspaceExtension) {

		long buildTime = System.currentTimeMillis();

		Tar distBundleTar = _addTaskDistBundle(
			project, DIST_BUNDLE_TAR_TASK_NAME, Tar.class, distBundleTask,
			workspaceExtension);

		distBundleTar.setDescription(
			"Assembles a Liferay bundle (tar.gz) for the current environment.");
		distBundleTar.setGroup(BUNDLE_GROUP);

		Zip distBundleZip = _addTaskDistBundle(
			project, DIST_BUNDLE_ZIP_TASK_NAME, Zip.class, distBundleTask,
			workspaceExtension);

		distBundleZip.setDescription(
			"Assembles a Liferay bundle (zip) for the current environment.");
		distBundleZip.setGroup(BUNDLE_GROUP);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureDistBundleEnvArchive(
						project, distBundleTar,
						workspaceExtension.getEnvironment(),
						workspaceExtension.isBundleDistIncludeMetadata(),
						buildTime);

					_configureDistBundleEnvArchive(
						project, distBundleZip,
						workspaceExtension.getEnvironment(),
						workspaceExtension.isBundleDistIncludeMetadata(),
						buildTime);
				}

			});
	}

	private void _addTasksDistBundleEnvironments(
		Project project, Download downloadBundleTask,
		WorkspaceExtension workspaceExtension,
		Configuration providedModulesConfiguration) {

		long buildTime = System.currentTimeMillis();

		File configsDir = workspaceExtension.getConfigsDir();

		String[] environments = configsDir.list(
			new FilenameFilter() {

				@Override
				public boolean accept(File file, String name) {
					if (!name.startsWith(".") && file.isDirectory() &&
						!name.equals("common") && !name.equals("docker")) {

						return true;
					}

					return false;
				}

			});

		if ((environments == null) || (environments.length == 0)) {
			return;
		}

		Task distBundleTarAll = GradleUtil.addTask(
			project, DIST_BUNDLE_TAR_ALL_TASK_NAME, DefaultTask.class);

		distBundleTarAll.setDescription(
			"Assembles a Liferay bundle (tar.gz) for each environment.");
		distBundleTarAll.setGroup(BUNDLE_GROUP);

		Task distBundleZipAll = GradleUtil.addTask(
			project, DIST_BUNDLE_ZIP_ALL_TASK_NAME, DefaultTask.class);

		distBundleZipAll.setDescription(
			"Assembles a Liferay bundle (zip) for each environment.");
		distBundleZipAll.setGroup(BUNDLE_GROUP);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					for (String environment : environments) {
						Copy distBundleEnvTask = _addTaskDistBundle(
							project, downloadBundleTask,
							DIST_BUNDLE_TASK_NAME +
								StringUtil.capitalize(environment),
							workspaceExtension, environment,
							providedModulesConfiguration);

						Tar distBundleTarTask = _addTaskDistBundle(
							project,
							DIST_BUNDLE_TAR_TASK_NAME +
								StringUtil.capitalize(environment),
							Tar.class, distBundleEnvTask, workspaceExtension);

						Property<String> archiveExtensionProperty =
							distBundleTarTask.getArchiveExtension();

						archiveExtensionProperty.set("tar.gz");

						distBundleTarTask.setCompression(Compression.GZIP);
						distBundleTarTask.setDescription(
							"Assembles a Liferay bundle (tar.gz) for " +
								environment + ".");

						_configureDistBundleEnvArchive(
							project, distBundleTarTask, environment, true,
							buildTime);

						distBundleTarAll.dependsOn(distBundleTarTask);

						Zip distBundleZipTask = _addTaskDistBundle(
							project,
							DIST_BUNDLE_ZIP_TASK_NAME +
								StringUtil.capitalize(environment),
							Zip.class, distBundleEnvTask, workspaceExtension);

						distBundleZipTask.setDescription(
							"Assembles a Liferay bundle (zip) for " +
								environment + ".");

						_configureDistBundleEnvArchive(
							project, distBundleZipTask, environment, true,
							buildTime);

						distBundleZipAll.dependsOn(distBundleZipTask);
					}
				}

			});
	}

	private DockerStartContainer _addTaskStartDockerContainer(
		Project project, DockerCreateContainer dockerCreateContainer) {

		DockerStartContainer dockerStartContainer = GradleUtil.addTask(
			project, START_DOCKER_CONTAINER_TASK_NAME,
			DockerStartContainer.class);

		dockerStartContainer.dependsOn(dockerCreateContainer);

		dockerStartContainer.setDescription("Starts the Docker container.");
		dockerStartContainer.setGroup(DOCKER_GROUP);

		dockerStartContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getDockerContainerId(project);
				}

			});

		return dockerStartContainer;
	}

	private DockerStopContainer _addTaskStopDockerContainer(Project project) {
		DockerStopContainer dockerStopContainer = GradleUtil.addTask(
			project, STOP_DOCKER_CONTAINER_TASK_NAME,
			DockerStopContainer.class);

		dockerStopContainer.setDescription("Stops the Docker container.");
		dockerStopContainer.setGroup(DOCKER_GROUP);

		dockerStopContainer.targetContainerId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getDockerContainerId(project);
				}

			});

		dockerStopContainer.onError(
			new Action<Throwable>() {

				@Override
				public void execute(Throwable throwable) {
					Logger logger = project.getLogger();

					if (logger.isWarnEnabled()) {
						logger.warn(
							"No container with ID '" +
								_getDockerContainerId(project) + "' running.");
					}
				}

			});

		return dockerStopContainer;
	}

	private VerifyBundleTask _addTaskVerifyBundle(
		Project project, VerifyProductTask verifyProductTask,
		Download downloadBundleTask, WorkspaceExtension workspaceExtension) {

		VerifyBundleTask verifyBundleTask = GradleUtil.addTask(
			project, VERIFY_BUNDLE_TASK_NAME, VerifyBundleTask.class);

		verifyBundleTask.algorithm("MD5");
		verifyBundleTask.dependsOn(verifyProductTask, downloadBundleTask);
		verifyBundleTask.setDescription(
			"Verifies the Liferay bundle zip file.");

		verifyBundleTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					if (!Objects.equals(
							workspaceExtension.getBundleUrl(),
							workspaceExtension.getDefaultBundleUrl())) {

						if (Objects.nonNull(_bundleCheckSumMD5)) {
							return true;
						}

						return false;
					}

					return Validator.isNotNull(verifyBundleTask.getChecksum());
				}

			});

		downloadBundleTask.finalizedBy(verifyBundleTask);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project p) {
					if (Validator.isNotNull(
							workspaceExtension.getBundleUrl())) {

						verifyBundleTask.checksum(
							workspaceExtension.getBundleChecksumMD5());

						TaskOutputs taskOutputs =
							downloadBundleTask.getOutputs();

						FileCollection fileCollection = taskOutputs.getFiles();

						verifyBundleTask.src(fileCollection.getSingleFile());
					}
				}

			});

		return verifyBundleTask;
	}

	private VerifyProductTask _addTaskVerifyProduct(
		Project project, WorkspaceExtension workspaceExtension) {

		VerifyProductTask verifyProductTask = GradleUtil.addTask(
			project, VERIFY_PRODUCT_TASK_NAME, VerifyProductTask.class);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					if (Objects.nonNull(workspaceExtension.getProduct())) {
						WorkspaceExtension.ProductInfo productInfo =
							workspaceExtension.getProductInfo();

						if (Objects.nonNull(productInfo)) {
							verifyProductTask.setBundleUrl(
								productInfo.getBundleUrl());
							verifyProductTask.setDockerImageLiferay(
								productInfo.getLiferayDockerImage());
							verifyProductTask.setTargetPlatformVersion(
								productInfo.getTargetPlatformVersion());
						}
						else {
							verifyProductTask.setErrorMessage(
								"The product key is invalid. Please provide " +
									"a valid product key.");
						}
					}
					else {
						verifyProductTask.setBundleUrl(
							workspaceExtension.getBundleUrl());
						verifyProductTask.setDockerImageLiferay(
							workspaceExtension.getDockerImageLiferay());
						verifyProductTask.setTargetPlatformVersion(
							workspaceExtension.getTargetPlatformVersion());
					}
				}

			});

		verifyProductTask.setDescription(
			"Verify Liferay Workspace product settings.");

		return verifyProductTask;
	}

	private void _configureCopySpecExpandTomcatVersion(
		CopySpec copySpec, WorkspaceExtension workspaceExtension) {

		String tomcatVersion = workspaceExtension.getAppServerTomcatVersion();

		copySpec.eachFile(
			fileCopyDetails -> {
				String path = fileCopyDetails.getPath();

				fileCopyDetails.setPath(
					path.replaceAll(
						"tomcat/",
						StringBundler.concat(
							"tomcat-", tomcatVersion,
							StringPool.FORWARD_SLASH)));
			});
	}

	private <T extends AbstractArchiveTask> void _configureDistBundleEnvArchive(
		Project project, T distBundleArchiveTask, String environment,
		boolean bundleDistIncludeMetadata, long buildTime) {

		Property<String> archiveBaseNameProperty =
			distBundleArchiveTask.getArchiveBaseName();

		archiveBaseNameProperty.set(
			project.provider(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						StringBuilder sb = new StringBuilder();

						sb.append(project.getName());

						if (bundleDistIncludeMetadata) {
							sb.append("-");
							sb.append(environment);
							sb.append("-");
							sb.append(buildTime);
						}

						return sb.toString();
					}

				}));
	}

	private void _configureDownloadTask(
		Download download, WorkspaceExtension workspaceExtension) {

		File destinationDir = workspaceExtension.getBundleCacheDir();

		destinationDir.mkdirs();

		download.dest(destinationDir);

		String bundleURLString = workspaceExtension.getBundleUrl();

		if (Objects.isNull(bundleURLString)) {
			return;
		}

		try {
			if (bundleURLString.startsWith("file:")) {
				URL url = new URL(bundleURLString);

				File file = new File(url.getFile());

				file = file.getAbsoluteFile();

				URI uri = file.toURI();

				bundleURLString = uri.toASCIIString();
			}
			else {
				bundleURLString = bundleURLString.replace(" ", "%20");
			}

			download.src(bundleURLString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new GradleException(
				malformedURLException.getMessage(), malformedURLException);
		}
	}

	private void _configureNpmProject(Project project) {
		project.subprojects(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					project.afterEvaluate(
						new Action<Project>() {

							@Override
							public void execute(Project project) {
								TaskContainer taskContainer =
									project.getTasks();

								taskContainer.withType(
									NpmInstallTask.class,
									new Action<NpmInstallTask>() {

										@Override
										public void execute(
											NpmInstallTask npmInstallTask) {

											NodeExtension nodeExtension =
												GradleUtil.getExtension(
													npmInstallTask.getProject(),
													NodeExtension.class);

											nodeExtension.setUseNpm(true);
										}

									});
							}

						});
				}

			});
	}

	@SuppressWarnings("serial")
	private void _configureTaskCopyBundleFromConfig(
		Project project, CopySpec copy, Callable<File> dir) {

		copy.from(
			dir,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.exclude("**/.touch");
				}

			});
	}

	@SuppressWarnings("serial")
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

					if (copy.getDidWork()) {
						project.delete(new File(destinationDir, "tomcat"));
					}
				}

			});

		copy.from(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					File file = _getDownloadFile(download);

					String fileName = file.getName();

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

		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		_configureCopySpecExpandTomcatVersion(copy, workspaceExtension);
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

	private void _configureWorkspaceExtension(
		Project project, WorkspaceExtension workspaceExtension) {

		String dockerContainerId = workspaceExtension.getDockerContainerId();

		if (dockerContainerId == null) {
			workspaceExtension.setDockerContainerId(
				StringUtil.getDockerSafeName(project.getName()) + "-liferay");
		}

		String dockerImageId = workspaceExtension.getDockerImageId();

		if (dockerImageId == null) {
			String version = _getDockerImageTag(project, workspaceExtension);

			workspaceExtension.setDockerImageId(
				String.format(
					"%s-liferay:%s",
					StringUtil.getDockerSafeName(project.getName()), version));
		}
	}

	private void _createTouchFile(File dir) throws IOException {
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(dir, ".touch");

		file.createNewFile();
	}

	private String _getDockerContainerId(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		return workspaceExtension.getDockerContainerId();
	}

	private String _getDockerImageId(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		return workspaceExtension.getDockerImageId();
	}

	private String _getDockerImageTag(
		Project project, WorkspaceExtension workspaceExtension) {

		String dockerImageLiferay = workspaceExtension.getDockerImageLiferay();
		Object version = project.getVersion();

		if (Objects.nonNull(dockerImageLiferay) &&
			Objects.equals(version, "unspecified")) {

			int index = dockerImageLiferay.indexOf(":");

			version = dockerImageLiferay.substring(index + 1);
		}

		return version.toString();
	}

	private File _getDownloadFile(Download download) {
		String fileName = String.valueOf((URL)download.getSrc());

		return new File(
			download.getDest(),
			fileName.substring(fileName.lastIndexOf('/') + 1));
	}

	private String _getEnvVarOverride(String string) {
		String[] segments = string.split("\\.");

		StringBuilder sb = new StringBuilder();

		sb.append("LIFERAY_");

		for (int i = 0; i < segments.length; i++) {
			sb.append(segments[i].toUpperCase());

			if (i < (segments.length - 1)) {
				sb.append("_PERIOD_");
			}
		}

		return sb.toString();
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

	private String _loadTemplate(String name) {
		try (InputStream inputStream =
				RootProjectConfigurator.class.getResourceAsStream(
					"dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
		catch (Exception exception) {
			throw new GradleException(
				"Unable to read template " + name, exception);
		}
	}

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private static final String _LIFERAY_IMAGE_SETUP_SCRIPT =
		"100_liferay_image_setup.sh";

	private static final Spec<Task> _skipIfExecutingParentTaskSpec =
		new Spec<Task>() {

			@Override
			public boolean isSatisfiedBy(Task task) {
				Project project = task.getProject();

				Gradle gradle = project.getGradle();

				TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

				Project parentProject = project;

				while ((parentProject = parentProject.getParent()) != null) {
					TaskContainer parentProjectTaskContainer =
						parentProject.getTasks();

					Task parentProjectTask =
						parentProjectTaskContainer.findByName(task.getName());

					if ((parentProjectTask != null) &&
						taskExecutionGraph.hasTask(parentProjectTask)) {

						return false;
					}
				}

				return true;
			}

		};

	private String _bundleCheckSumMD5;
	private boolean _defaultRepositoryEnabled;

}