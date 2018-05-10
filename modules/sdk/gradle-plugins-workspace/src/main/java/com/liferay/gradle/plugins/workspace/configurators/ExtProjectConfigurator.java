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

import com.liferay.gradle.plugins.LiferayExtPlugin;
import com.liferay.gradle.plugins.LiferayOSGiExtPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
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

import org.gradle.api.Project;
import org.gradle.api.file.CopySourceSpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.bundling.War;
import org.gradle.jvm.tasks.Jar;

/**
 * @author David Truong
 */
public class ExtProjectConfigurator extends BaseProjectConfigurator {

	public ExtProjectConfigurator(Settings settings) {
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

		boolean extProject = _isExtProject(project);

		_applyPlugins(project, extProject);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		_configureLiferay(project, workspaceExtension);
		_configureRootTaskDistBundle(project, extProject);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
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

	protected static final String NAME = "ext";

	private void _applyPlugins(Project project, boolean extProject) {
		if (extProject) {
			GradleUtil.applyPlugin(project, LiferayExtPlugin.class);
		}
		else {
			GradleUtil.applyPlugin(project, LiferayOSGiExtPlugin.class);
		}
	}

	private void _configureLiferay(
		Project project, WorkspaceExtension workspaceExtension) {

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setAppServerParentDir(workspaceExtension.getHomeDir());
	}

	private void _configureRootTaskDistBundle(
		Project project, boolean extProject) {

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		if (extProject) {
			final War war = (War)GradleUtil.getTask(
				project, WarPlugin.WAR_TASK_NAME);

			copy.into(
				"osgi/war",
				new Closure<Void>(project) {

					@SuppressWarnings("unused")
					public void doCall(CopySourceSpec copySourceSpec) {
						copySourceSpec.from(war);
					}

				});
		}
		else {
			final Jar jar = (Jar)GradleUtil.getTask(
				project, JavaPlugin.JAR_TASK_NAME);

			copy.into(
				"osgi/marketplace/override",
				new Closure<Void>(project) {

					@SuppressWarnings("unused")
					public void doCall(CopySourceSpec copySourceSpec) {
						copySourceSpec.from(jar);
					}

				});
		}
	}

	private boolean _isExtProject(Project project) {
		File webappDir = project.file("src/main/webapp");

		if (webappDir.exists()) {
			return true;
		}

		File extImplDir = project.file(
			"src/" + LiferayExtPlugin.EXT_IMPL_SOURCESET_NAME);

		if (extImplDir.exists()) {
			return true;
		}

		File extKernelDir = project.file(
			"src/" + LiferayExtPlugin.EXT_KERNEL_SOURCESET_NAME);

		if (extKernelDir.exists()) {
			return true;
		}

		File extUtilBridgesDir = project.file(
			"src/" + LiferayExtPlugin.EXT_UTIL_BRIDGES_SOURCESET_NAME);

		if (extUtilBridgesDir.exists()) {
			return true;
		}

		File extUtilJavaDir = project.file(
			"src/" + LiferayExtPlugin.EXT_UTIL_JAVA_SOURCESET_NAME);

		if (extUtilJavaDir.exists()) {
			return true;
		}

		File extUtilTaglibDir = project.file(
			"src/" + LiferayExtPlugin.EXT_UTIL_TAGLIB_SOURCESET_NAME);

		if (extUtilTaglibDir.exists()) {
			return true;
		}

		return false;
	}

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private final boolean _defaultRepositoryEnabled;

}