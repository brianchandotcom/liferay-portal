/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.configurator;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class WarsProjectConfigurator extends BaseProjectConfigurator {

	public WarsProjectConfigurator(Settings settings) {
		super(settings);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, WarPlugin.class);

		_configureTaskProcessResources(project);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		_configureBaseTaskDeploy(war, workspaceExtension);

		addTaskDockerDeploy(project, war, workspaceExtension);

		_configureRootTaskDistBundle(war);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	public void setDefaultRepositoryEnabled(boolean defaultRepositoryEnabled) {
		_defaultRepositoryEnabled = defaultRepositoryEnabled;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (Files.isDirectory(dirPath.resolve("src/main/webapp"))) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					Path dirNamePath = dirPath.getFileName();

					if (isExcludedDirName(dirNamePath.toString())) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	protected static final String NAME = "wars";

	private void _configureBaseTaskDeploy(
		War war, final WorkspaceExtension workspaceExtension) {

		Project project = war.getProject();

		project.afterEvaluate(
			curProject -> {
				TaskContainer taskContainer = curProject.getTasks();

				taskContainer.named(
					LiferayBasePlugin.DEPLOY_TASK_NAME, Copy.class,
					copy -> {
						copy.from(war);

						copy.into(
							new Callable<File>() {

								@Override
								public File call() throws Exception {
									return new File(
										workspaceExtension.getHomeDir(),
										"deploy");
								}

							});

						copy.setDescription(
							"Assembles the project and deploys it to Liferay.");
						copy.setGroup(BasePlugin.BUILD_GROUP);
					});
			});
	}

	@SuppressWarnings("serial")
	private void _configureRootTaskDistBundle(final War war) {
		Project project = war.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.into(
			"osgi/war",
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.from(war);
				}

			});
	}

	private void _configureTaskProcessResources(Project project) {
		project.afterEvaluate(
			curProject -> {
				if (GradleUtil.hasTask(
						curProject, CSSBuilderPlugin.BUILD_CSS_TASK_NAME)) {

					Copy copy = (Copy)GradleUtil.getTask(
						project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

					if (copy != null) {
						copy.dependsOn(CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

						copy.exclude("**/*.css");
						copy.exclude("**/*.scss");

						copy.filesMatching(
							"**/.sass-cache/",
							fileCopyDetails -> {
								String path = fileCopyDetails.getPath();

								fileCopyDetails.setPath(
									path.replace(".sass-cache/", ""));
							});

						copy.setIncludeEmptyDirs(false);
					}
				}
			});
	}

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private boolean _defaultRepositoryEnabled;

}