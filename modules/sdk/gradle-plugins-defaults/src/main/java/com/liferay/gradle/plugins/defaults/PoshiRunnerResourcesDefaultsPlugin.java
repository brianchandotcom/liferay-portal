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

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.defaults.internal.util.GitRepo;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.poshi.runner.PoshiRunnerResourcesExtension;
import com.liferay.gradle.plugins.poshi.runner.PoshiRunnerResourcesPlugin;

import groovy.lang.Closure;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.maven.MavenDeployer;
import org.gradle.api.artifacts.maven.MavenPom;
import org.gradle.api.internal.artifacts.publish.ArchivePublishArtifact;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.plugins.MavenRepositoryHandlerConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Upload;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerResourcesDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, MavenPlugin.class);
		GradleUtil.applyPlugin(project, PoshiRunnerResourcesPlugin.class);

		_applyConfigScripts(project);
		_configurePoshiRunnerResources(project);
		_configureTaskUploadPoshiRunnerResources(project);
	}

	private void _applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/defaults/dependencies" +
				"/config-maven.gradle",
			project);
	}

	private void _configurePoshiRunnerResources(Project project) {
		PoshiRunnerResourcesExtension poshiRunnerResourcesExtension =
			GradleUtil.getExtension(
				project, PoshiRunnerResourcesExtension.class);

		poshiRunnerResourcesExtension.setRootDirName(_ROOT_DIR_NAME);
	}

	private void _configureTaskUploadPoshiRunnerResources(
		final Project project) {

		Upload upload = (Upload)GradleUtil.getTask(
			project,
			PoshiRunnerResourcesPlugin.UPLOAD_POSHI_RUNNER_RESOURCES_TASK_NAME);

		upload.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					GitRepo gitRepo = GitRepo.getGitRepo(
						project.getProjectDir());

					if ((gitRepo != null) && gitRepo.readOnly) {
						return false;
					}

					return true;
				}

			});

		RepositoryHandler repositoryHandler = upload.getRepositories();

		final MavenDeployer mavenDeployer =
			(MavenDeployer)repositoryHandler.getAt(
				MavenRepositoryHandlerConvention.DEFAULT_MAVEN_DEPLOYER_NAME);

		Configuration configuration = upload.getConfiguration();

		PublishArtifactSet publishArtifactSet = configuration.getAllArtifacts();

		publishArtifactSet.withType(
			ArchivePublishArtifact.class,
			new Action<ArchivePublishArtifact>() {

				@Override
				public void execute(
					ArchivePublishArtifact archivePublishArtifact) {

					AbstractArchiveTask abstractArchiveTask =
						archivePublishArtifact.getArchiveTask();

					final String name = abstractArchiveTask.getArchiveName();

					mavenDeployer.addFilter(
						name,
						new Closure<Boolean>(project) {

							@SuppressWarnings("unused")
							public Boolean doCall(Object artifact, File file) {
								if (name.equals(file.getName())) {
									return true;
								}

								return false;
							}

						});

					MavenPom mavenPom = mavenDeployer.pom(name);

					mavenPom.setGroupId(_GROUP_ID);
					mavenPom.setVersion(abstractArchiveTask.getVersion());
				}

			});
	}

	private static final String _GROUP_ID =
		"com.liferay.poshi.runner.resources";

	private static final String _ROOT_DIR_NAME = "testFunctional";

}