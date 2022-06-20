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

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.workspace.LiferayExtPlugin;
import com.liferay.gradle.plugins.workspace.LiferayOSGiExtPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySourceSpec;
import org.gradle.api.file.CopySpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Copy;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 */
public class ExtProjectConfigurator extends BaseProjectConfigurator {

	public ExtProjectConfigurator(Settings settings) {
		super(settings);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);

		String defaultRootDirNames = GradleUtil.getProperty(
			settings, getDefaultRootDirPropertyName(), (String)null);

		if (Validator.isNotNull(defaultRootDirNames)) {
			_defaultRootDirs = new HashSet<>();

			for (String dirName : defaultRootDirNames.split("\\s*,\\s*")) {
				_defaultRootDirs.add(new File(settings.getRootDir(), dirName));
			}
		}
		else {
			File dir = new File(settings.getRootDir(), getDefaultRootDirName());

			_defaultRootDirs = Collections.singleton(dir);
		}
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		boolean extPlugin = _isExtPlugin(project);

		_applyPlugins(project, extPlugin);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		_configureRootTaskDistBundle(project, extPlugin);

		configureLiferay(project, workspaceExtension);

		Object dockerDeploySourcePath = null;

		if (extPlugin) {
			dockerDeploySourcePath = GradleUtil.getTask(
				project, WarPlugin.WAR_TASK_NAME);
		}
		else {
			dockerDeploySourcePath = GradleUtil.getTask(
				project, JavaPlugin.JAR_TASK_NAME);
		}

		_addTaskDockerDeploy(
			project, dockerDeploySourcePath, workspaceExtension);
	}

	@Override
	public Iterable<File> getDefaultRootDirs() {
		return _defaultRootDirs;
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

	private void _addTaskDockerDeploy(
		Project project, Object sourcePath,
		WorkspaceExtension workspaceExtension) {

		Task rootDockerDeployTask = GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DOCKER_DEPLOY_TASK_NAME);

		Copy dockerDeploy = GradleUtil.addTask(
			project, RootProjectConfigurator.DOCKER_DEPLOY_TASK_NAME,
			Copy.class);

		dockerDeploy.dependsOn(rootDockerDeployTask);

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

			if ((configDirs != null) && (configDirs.length > 0)) {
				dockerDeploy.into(workspaceExtension.getDockerDir());

				for (File configDir : configDirs) {
					dockerDeploy.into(
						RootProjectConfigurator.LIFERAY_CONFIGS_DIR_NAME + "/" +
							configDir.getName() + "/osgi/marketplace/override",
						copySpec -> {
							Closure<Void> copySpecClosure = new Closure<Void>(
								project) {

								@SuppressWarnings("unused")
								public void doCall(CopySpec copySpec) {
									copySpec.rename(
										_getDeployedFileNameClosure(
											project, sourcePath));
								}

							};

							copySpec.from(sourcePath, copySpecClosure);
						});
				}
			}
		}

		dockerDeploy.setDescription(
			"Assembles the project and deploys it to the Liferay Docker " +
				"container.");
		dockerDeploy.setGroup(RootProjectConfigurator.DOCKER_GROUP);

		Task deployTask = GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		deployTask.finalizedBy(dockerDeploy);

		Task buildDockerImageTask = GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.BUILD_DOCKER_IMAGE_TASK_NAME);

		buildDockerImageTask.dependsOn(deployTask);
	}

	private void _applyPlugins(Project project, boolean extPlugin) {
		if (extPlugin) {
			GradleUtil.applyPlugin(project, LiferayExtPlugin.class);
		}
		else {
			GradleUtil.applyPlugin(project, LiferayOSGiExtPlugin.class);
		}
	}

	@SuppressWarnings("serial")
	private void _configureRootTaskDistBundle(
		Project project, boolean extPlugin) {

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		String dirName = null;
		String taskName = null;

		if (extPlugin) {
			dirName = "osgi/war";
			taskName = WarPlugin.WAR_TASK_NAME;
		}
		else {
			dirName = "osgi/marketplace/override";
			taskName = JavaPlugin.JAR_TASK_NAME;
		}

		final Task task = GradleUtil.getTask(project, taskName);

		copy.into(
			dirName,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySourceSpec copySourceSpec) {
					copySourceSpec.from(task);
				}

			});
	}

	private Closure<String> _getDeployedFileNameClosure(
		Project project, Object sourcePath) {

		return new Closure<String>(project) {

			public String doCall(String fileName) {
				LiferayExtension liferayExtension = GradleUtil.getExtension(
					project, LiferayExtension.class);

				Closure<String> closure =
					liferayExtension.getDeployedFileNameClosure();

				return closure.call(sourcePath);
			}

		};
	}

	private boolean _isExtPlugin(Project project) {
		if (FileUtil.exists(project, "src/main/webapp")) {
			return true;
		}

		for (String name : _EXT_SOURCE_SET_NAMES) {
			if (FileUtil.exists(project, "src/" + name)) {
				return true;
			}
		}

		return false;
	}

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private static final String[] _EXT_SOURCE_SET_NAMES = {
		LiferayExtPlugin.EXT_IMPL_SOURCE_SET_NAME,
		LiferayExtPlugin.EXT_KERNEL_SOURCE_SET_NAME,
		LiferayExtPlugin.EXT_UTIL_BRIDGES_SOURCE_SET_NAME,
		LiferayExtPlugin.EXT_UTIL_JAVA_SOURCE_SET_NAME,
		LiferayExtPlugin.EXT_UTIL_TAGLIB_SOURCE_SET_NAME
	};

	private final boolean _defaultRepositoryEnabled;
	private final Set<File> _defaultRootDirs;

}