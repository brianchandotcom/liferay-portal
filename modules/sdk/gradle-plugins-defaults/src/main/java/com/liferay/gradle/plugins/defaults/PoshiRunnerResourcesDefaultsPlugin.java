/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.defaults.internal.util.GitRepo;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.poshi.runner.PoshiRunnerResourcesExtension;
import com.liferay.gradle.plugins.poshi.runner.PoshiRunnerResourcesPlugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.internal.artifacts.publish.ArchivePublishArtifact;
import org.gradle.api.publish.PublicationContainer;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerResourcesDefaultsPlugin implements Plugin<Project> {

	public static final String ARTIFACT_APPENDIX_PROPERTY_NAME =
		"artifactAppendix";

	public static final String ARTIFACT_VERSION_PROPERTY_NAME =
		"artifactVersion";

	@Override
	public void apply(Project project) {
		GradlePluginsDefaultsUtil.configureRepositories(project, null);

		GradleUtil.applyPlugin(project, MavenPublishPlugin.class);
		GradleUtil.applyPlugin(project, PoshiRunnerResourcesPlugin.class);

		_applyConfigScripts(project);
		_configurePoshiRunnerResources(project);
		_configurePublishing(project);
	}

	private void _applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/defaults/dependencies" +
				"/config-maven-publish.gradle",
			project);
	}

	private void _configurePoshiRunnerResources(Project project) {
		PoshiRunnerResourcesExtension poshiRunnerResourcesExtension =
			GradleUtil.getExtension(
				project, PoshiRunnerResourcesExtension.class);

		poshiRunnerResourcesExtension.setRootDirName(_ROOT_DIR_NAME);

		if (project.hasProperty(ARTIFACT_APPENDIX_PROPERTY_NAME)) {
			poshiRunnerResourcesExtension.setArtifactAppendix(
				GradleUtil.getProperty(
					project, ARTIFACT_APPENDIX_PROPERTY_NAME, (String)null));
		}

		if (project.hasProperty(ARTIFACT_VERSION_PROPERTY_NAME)) {
			poshiRunnerResourcesExtension.setArtifactVersion(
				GradleUtil.getProperty(
					project, ARTIFACT_VERSION_PROPERTY_NAME, (String)null));
		}
	}

	private void _configurePublishing(final Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishToMavenRepository.class,
			new Action<PublishToMavenRepository>() {

				@Override
				public void execute(
					PublishToMavenRepository publishToMavenRepository) {

					publishToMavenRepository.onlyIf(
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
				}

			});

		Configuration configuration = GradleUtil.getConfiguration(
			project,
			PoshiRunnerResourcesPlugin.
				POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME);

		PublishArtifactSet publishArtifactSet = configuration.getAllArtifacts();

		publishArtifactSet.withType(
			ArchivePublishArtifact.class,
			new Action<ArchivePublishArtifact>() {

				@Override
				public void execute(
					ArchivePublishArtifact archivePublishArtifact) {

					AbstractArchiveTask abstractArchiveTask =
						archivePublishArtifact.getArchiveTask();

					PublishingExtension publishingExtension =
						GradleUtil.getExtension(
							project, PublishingExtension.class);

					publishingExtension.publications(
						new Action<PublicationContainer>() {

							@Override
							public void execute(
								PublicationContainer publicationContainer) {

								MavenPublication mavenPublication =
									publicationContainer.maybeCreate(
										"maven", MavenPublication.class);

								mavenPublication.artifact(abstractArchiveTask);

								mavenPublication.setGroupId(_GROUP_ID);
								mavenPublication.setVersion(
									abstractArchiveTask.getVersion());
							}

						});
				}

			});
	}

	private static final String _GROUP_ID =
		"com.liferay.poshi.runner.resources";

	private static final String _ROOT_DIR_NAME = "testFunctional";

}