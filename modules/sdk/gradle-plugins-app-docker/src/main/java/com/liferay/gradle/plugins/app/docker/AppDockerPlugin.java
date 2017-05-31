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

package com.liferay.gradle.plugins.app.docker;

import com.bmuschko.gradle.docker.DockerRemoteApiPlugin;
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage;
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage;
import com.bmuschko.gradle.docker.tasks.image.DockerTagImage;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.internal.ConventionMapping;
import org.gradle.api.internal.plugins.DslObject;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Sync;

/**
 * @author Andrea Di Giorgi
 */
public class AppDockerPlugin implements Plugin<Project> {

	public static final String BUILD_APP_DOCKER_IMAGE_TASK_NAME =
		"buildAppDockerImage";

	public static final String PLUGIN_NAME = "appDocker";

	public static final String PREPARE_APP_DOCKER_IMAGE_INPUT_DIR_TASK_NAME =
		"prepareAppDockerImageInputDir";

	public static final String PUSH_APP_DOCKER_IMAGE_TASK_NAME =
		"pushAppDockerImage";

	public static final String TAG_APP_DOCKER_IMAGE_TASK_NAME =
		"tagAppDockerImage";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, DockerRemoteApiPlugin.class);

		final AppDockerExtension appDockerExtension = GradleUtil.addExtension(
			project, PLUGIN_NAME, AppDockerExtension.class);

		final Sync prepareAppDockerImageInputDirTask =
			_addTaskPrepareAppDockerImageInputDir(project, appDockerExtension);

		final DockerBuildImage buildAppDockerImageTask =
			_addTaskBuildAppDockerImage(
				prepareAppDockerImageInputDirTask, appDockerExtension);

		final DockerPushImage pushAppDockerImageTask =
			_addTaskPushAppDockerImage(
				buildAppDockerImageTask, appDockerExtension);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_addTasksPushAppDockerImage(
						buildAppDockerImageTask, pushAppDockerImageTask,
						appDockerExtension);
				}

			});

		Gradle gradle = project.getGradle();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project subproject) {
					_configureTaskPrepareAppDockerImageInputDir(
						prepareAppDockerImageInputDirTask, appDockerExtension,
						subproject);
				}

			});
	}

	private DockerBuildImage _addTaskBuildAppDockerImage(
		final Sync prepareAppDockerImageInputDirTask,
		final AppDockerExtension appDockerExtension) {

		Project project = prepareAppDockerImageInputDirTask.getProject();

		final DockerBuildImage dockerBuildImage = GradleUtil.addTask(
			project, BUILD_APP_DOCKER_IMAGE_TASK_NAME, DockerBuildImage.class);

		dockerBuildImage.setDependsOn(
			Collections.singleton(prepareAppDockerImageInputDirTask));
		dockerBuildImage.setDescription("Builds the Docker image of the app.");
		dockerBuildImage.setGroup(BasePlugin.BUILD_GROUP);

		DslObject dslObject = new DslObject(dockerBuildImage);

		ConventionMapping conventionMapping = dslObject.getConventionMapping();

		conventionMapping.map(
			"inputDir",
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return
						prepareAppDockerImageInputDirTask.getDestinationDir();
				}

			});

		conventionMapping.map(
			"tag",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getImageRepository(appDockerExtension);
				}

			});

		return dockerBuildImage;
	}

	private Sync _addTaskPrepareAppDockerImageInputDir(
		final Project project, final AppDockerExtension appDockerExtension) {

		Sync sync = GradleUtil.addTask(
			project, PREPARE_APP_DOCKER_IMAGE_INPUT_DIR_TASK_NAME, Sync.class);

		sync.from(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return appDockerExtension.getInputDir();
				}

			});

		sync.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(project.getBuildDir(), "docker");
				}

			});

		sync.setDescription(
			"Copies all the subproject artifacts and other resources to a " +
				"temporary directory that will be used to build the Docker " +
					"image of the app.");

		return sync;
	}

	private DockerPushImage _addTaskPushAppDockerImage(
		final DockerBuildImage buildAppDockerImageTask,
		final AppDockerExtension appDockerExtension) {

		DockerPushImage dockerPushImage = GradleUtil.addTask(
			buildAppDockerImageTask.getProject(),
			PUSH_APP_DOCKER_IMAGE_TASK_NAME, DockerPushImage.class);

		dockerPushImage.setDependsOn(
			Collections.singleton(buildAppDockerImageTask));
		dockerPushImage.setDescription(
			"Pushes the Docker image of the app to the registry.");
		dockerPushImage.setGroup(BasePlugin.UPLOAD_GROUP);

		DslObject dslObject = new DslObject(dockerPushImage);

		ConventionMapping conventionMapping = dslObject.getConventionMapping();

		conventionMapping.map(
			"imageName",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getImageRepository(appDockerExtension);
				}

			});

		return dockerPushImage;
	}

	private DockerPushImage _addTaskPushAppDockerImage(
		DockerTagImage dockerTagImage) {

		String imageRepositoryAndTag = _getImageRepositoryAndTag(
			dockerTagImage.getRepository(), dockerTagImage.getTag());

		DockerPushImage dockerPushImage = GradleUtil.addTask(
			dockerTagImage.getProject(),
			PUSH_APP_DOCKER_IMAGE_TASK_NAME + "_" + imageRepositoryAndTag,
			DockerPushImage.class);

		dockerPushImage.setDependsOn(Collections.singleton(dockerTagImage));
		dockerPushImage.setDescription(
			"Pushes the Docker image \"" + imageRepositoryAndTag +
				"\" to the registry.");
		dockerPushImage.setImageName(dockerTagImage.getRepository());
		dockerPushImage.setTag(dockerTagImage.getTag());

		return dockerPushImage;
	}

	private void _addTasksPushAppDockerImage(
		DockerBuildImage buildAppDockerImageTask,
		DockerPushImage pushAppDockerImageTask,
		AppDockerExtension appDockerExtension) {

		String imageRepository = _getImageRepository(appDockerExtension);

		for (Object imageTagObject : appDockerExtension.getImageTags()) {
			String imageTag = GradleUtil.toString(imageTagObject);

			if (Validator.isNull(imageTag)) {
				continue;
			}

			DockerTagImage dockerTagImage = _addTaskTagAppDockerImage(
				buildAppDockerImageTask, imageRepository, imageTag);

			DockerPushImage dockerPushImage = _addTaskPushAppDockerImage(
				dockerTagImage);

			pushAppDockerImageTask.dependsOn(dockerPushImage);
		}
	}

	private DockerTagImage _addTaskTagAppDockerImage(
		final DockerBuildImage buildAppDockerImagetask, String imageRepository,
		String imageTag) {

		Project project = buildAppDockerImagetask.getProject();

		String imageRepositoryAndTag = _getImageRepositoryAndTag(
			imageRepository, imageTag);

		DockerTagImage dockerTagImage = GradleUtil.addTask(
			project,
			TAG_APP_DOCKER_IMAGE_TASK_NAME + "_" + imageRepositoryAndTag,
			DockerTagImage.class);

		dockerTagImage.setDependsOn(
			Collections.singleton(buildAppDockerImagetask));
		dockerTagImage.setDescription(
			"Creates the tag \"" + imageRepositoryAndTag +
				"\" which refers to the Docker image of the app.");
		dockerTagImage.setRepository(imageRepository);
		dockerTagImage.setTag(imageTag);

		dockerTagImage.targetImageId(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall() {
					return buildAppDockerImagetask.getImageId();
				}

			});

		return dockerTagImage;
	}

	private void _configureTaskPrepareAppDockerImageInputDir(
		Sync prepareAppDockerImageInputDirTask,
		AppDockerExtension appDockerExtension, Project subproject) {

		Logger logger = prepareAppDockerImageInputDirTask.getLogger();

		Set<Project> subprojects = appDockerExtension.getSubprojects();

		if (!subprojects.contains(subproject)) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"Excluding {} from {}", subproject,
					prepareAppDockerImageInputDirTask);
			}

			return;
		}

		Spec<Project> spec = appDockerExtension.getOnlyIf();

		if (!spec.isSatisfiedBy(subproject)) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"Explicitly excluding {} from {}", subproject,
					prepareAppDockerImageInputDirTask);
			}

			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			subproject, Dependency.DEFAULT_CONFIGURATION);

		PublishArtifactSet publishArtifactSet = configuration.getAllArtifacts();

		prepareAppDockerImageInputDirTask.from(publishArtifactSet.getFiles());
	}

	private String _getImageRepository(AppDockerExtension appDockerExtension) {
		String imageUser = appDockerExtension.getImageUser();
		String imageName = appDockerExtension.getImageName();

		if (Validator.isNull(imageUser)) {
			return imageName;
		}

		return imageUser + "/" + imageName;
	}

	private String _getImageRepositoryAndTag(
		String imageRepository, String imageTag) {

		if (Validator.isNull(imageTag)) {
			return imageRepository;
		}

		return imageRepository + ":" + imageTag;
	}

}