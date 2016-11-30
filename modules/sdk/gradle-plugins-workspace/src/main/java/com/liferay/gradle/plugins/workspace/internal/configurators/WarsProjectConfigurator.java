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

package com.liferay.gradle.plugins.workspace.internal.configurators;

import com.liferay.gradle.plugins.LiferayBasePlugin;
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
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class WarsProjectConfigurator extends BaseProjectConfigurator {

	public WarsProjectConfigurator(Settings settings) {
		super(settings);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + _NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		GradleUtil.applyPlugin(project, WarPlugin.class);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		_addTaskDeploy(war, workspaceExtension);

		_configureRootTaskDistBundle(war);
	}

	@Override
	public String getName() {
		return _NAME;
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

					if (Files.isDirectory(dirPath.resolve("src"))) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	private Copy _addTaskDeploy(
		War war, final WorkspaceExtension workspaceExtension) {

		Copy copy = GradleUtil.addTask(
			war.getProject(), LiferayBasePlugin.DEPLOY_TASK_NAME, Copy.class);

		copy.from(war);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(workspaceExtension.getHomeDir(), "deploy");
				}

			});

		copy.setDescription("Assembles the project and deploys it to Liferay.");
		copy.setGroup(BasePlugin.BUILD_GROUP);

		return copy;
	}

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

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private static final String _NAME = "wars";

	private boolean _defaultRepositoryEnabled;

}