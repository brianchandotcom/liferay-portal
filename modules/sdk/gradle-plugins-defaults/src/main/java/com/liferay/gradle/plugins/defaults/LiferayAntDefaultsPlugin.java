/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.LiferayAntPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayRelengPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.task.CopyIvyDependenciesTask;
import com.liferay.gradle.plugins.defaults.task.ReplaceRegexTask;

import groovy.lang.Closure;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.publish.PublicationContainer;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.ant.AntTarget;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayAntDefaultsPlugin implements Plugin<Project> {

	public static final String COPY_IVY_DEPENDENCIES_TASK_NAME =
		"copyIvyDependencies";

	@Override
	public void apply(Project project) {
		File ivyXmlFile = project.file("ivy.xml");

		if (ivyXmlFile.exists()) {
			File portalRootDir = GradleUtil.getRootDir(
				project.getRootProject(), "portal-impl");

			GradlePluginsDefaultsUtil.configureRepositories(
				project, portalRootDir);

			CopyIvyDependenciesTask copyIvyDependenciesTask =
				_addTaskCopyIvyDependencies(project, ivyXmlFile);

			copyIvyDependenciesTask.writeChecksumFile();
		}

		GradleUtil.applyPlugin(project, LiferayAntPlugin.class);

		_applyPlugins(project);

		_applyConfigScripts(project);

		final ReplaceRegexTask updateVersionTask = _addTaskUpdateVersion(
			project);

		_configureProject(project);

		GradleUtil.excludeTasksWithProperty(
			project, LiferayOSGiDefaultsPlugin.SNAPSHOT_IF_STALE_PROPERTY_NAME,
			true, MavenPublishPlugin.PUBLISH_LOCAL_LIFECYCLE_TASK_NAME,
			PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureExtensionPublishing(project);

					GradlePluginsDefaultsUtil.setProjectSnapshotVersion(
						project);

					// setProjectSnapshotVersion must be called before
					// configureTaskPublish, because the latter one needs
					// to know if we are publishing a snapshot or not.

					_configureTaskPublish(project, updateVersionTask);

					_configurePublishing(project);
				}

			});
	}

	private CopyIvyDependenciesTask _addTaskCopyIvyDependencies(
		Project project, File inputFile) {

		final CopyIvyDependenciesTask copyIvyDependenciesTask =
			GradleUtil.addTask(
				project, COPY_IVY_DEPENDENCIES_TASK_NAME,
				CopyIvyDependenciesTask.class);

		copyIvyDependenciesTask.setDescription(
			"Copies the dependencies declared in the ivy.xml file.");

		File destinationDir = project.file("docroot");

		if (destinationDir.exists()) {
			destinationDir = new File(destinationDir, "WEB-INF/lib");
		}
		else {
			destinationDir = project.file("lib");
		}

		copyIvyDependenciesTask.setDestinationDir(destinationDir);

		copyIvyDependenciesTask.setInputFile(inputFile);

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			AntTarget.class,
			new Action<AntTarget>() {

				@Override
				public void execute(AntTarget antTarget) {
					antTarget.dependsOn(copyIvyDependenciesTask);
				}

			});

		return copyIvyDependenciesTask;
	}

	@SuppressWarnings("serial")
	private ReplaceRegexTask _addTaskUpdateVersion(Project project) {
		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, LiferayRelengPlugin.UPDATE_VERSION_TASK_NAME,
			ReplaceRegexTask.class);

		replaceRegexTask.match(
			"module-incremental-version=(\\d+)",
			"docroot/WEB-INF/liferay-plugin-package.properties");

		replaceRegexTask.setDescription(
			"Updates \"module-incremental-version\" in the " +
				"liferay-plugin-package.properties file.");

		replaceRegexTask.setReplacement(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall(String group) {
					int moduleIncrementalVersion = Integer.parseInt(group);

					return String.valueOf(moduleIncrementalVersion + 1);
				}

			});

		return replaceRegexTask;
	}

	private void _applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/defaults/dependencies" +
				"/config-maven-publish.gradle",
			project);
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, MavenPublishPlugin.class);
	}

	private void _configureExtensionPublishing(final Project project) {
		PublishingExtension publishingExtension = GradleUtil.getExtension(
			project, PublishingExtension.class);

		publishingExtension.publications(
			new Action<PublicationContainer>() {

				@Override
				public void execute(PublicationContainer publicationContainer) {
					MavenPublication mavenPublication =
						publicationContainer.maybeCreate(
							"maven", MavenPublication.class);

					mavenPublication.setArtifactId(
						GradleUtil.getArchivesBaseName(project));
					mavenPublication.setGroupId(
						String.valueOf(project.getGroup()));

					mavenPublication.artifact(_getWarFile(project));
				}

			});
	}

	private void _configureProject(Project project) {
		project.setGroup(GradleUtil.getProjectGroup(project, _GROUP));
	}

	private void _configurePublishing(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishToMavenRepository.class,
			new Action<PublishToMavenRepository>() {

				@Override
				public void execute(
					PublishToMavenRepository publishToMavenRepository) {

					publishToMavenRepository.dependsOn(_WAR_TASK_NAME);
				}

			});
	}

	private void _configureTaskPublish(
		Project project, Task updatePluginVersionTask) {

		if (GradlePluginsDefaultsUtil.isSnapshot(project)) {
			return;
		}

		Task publishTask = GradleUtil.getTask(
			project, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

		publishTask.finalizedBy(updatePluginVersionTask);
	}

	private File _getWarFile(Project project) {
		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		return new File(
			new File(portalRootDir, "tools/sdk/dist"),
			GradleUtil.getArchivesBaseName(project) + "-" +
				project.getVersion() + ".war");
	}

	private static final String _GROUP = "com.liferay.plugins";

	private static final String _WAR_TASK_NAME = "war";

}