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

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.PackageRunBuildTask;
import com.liferay.gradle.plugins.workspace.FrontendPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Property;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Simon Jiang
 */
public class DesignPacksProjectConfigurator extends BaseProjectConfigurator {

	public static final String DESIGN_PACK_TASK_NAME = "zipDesignPack";

	public DesignPacksProjectConfigurator(Settings settings) {
		super(settings);

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
		File packageJsonFile = project.file("package.json");

		if (packageJsonFile.exists()) {
			WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
				(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

			GradleUtil.applyPlugin(project, FrontendPlugin.class);

			Zip zipDesignPackTask = _addTaskZipDesignPack(
				project, DESIGN_PACK_TASK_NAME, workspaceExtension);

			zipDesignPackTask.setDescription(
				"Assembles design pack project (zip).");

			zipDesignPackTask.setGroup(BasePlugin.BUILD_GROUP);

			_configureTaskDesignPack(project, zipDesignPackTask);

			_configureTaskPackageRunBuild(project, zipDesignPackTask);
		}
	}

	@Override
	public Iterable<File> getDefaultRootDirs() {
		return _defaultRootDirs;
	}

	@Override
	public String getName() {
		return NAME;
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

					String dirName = String.valueOf(dirPath.getFileName());

					if (isExcludedDirName(dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path gulpfileJsPath = dirPath.resolve("gulpfile.js");
					Path packageJsonPath = dirPath.resolve("package.json");

					if (Files.exists(gulpfileJsPath) &&
						Files.exists(packageJsonPath) &&
						_isLiferayTheme(packageJsonPath)) {

						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	@Override
	protected String getDefaultRootDirName() {
		return "design-packs";
	}

	protected static final String NAME = "design.pack";

	@SuppressWarnings("serial")
	private Zip _addTaskZipDesignPack(
		Project project, String taskName,
		final WorkspaceExtension workspaceExtension) {

		Zip task = GradleUtil.addTask(project, taskName, Zip.class);

		task.dependsOn(NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);

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
					copySpec.from(new File(project.getBuildDir(), "css"));
					copySpec.setIncludeEmptyDirs(false);
					copySpec.include("**/clay.css");
					copySpec.include("**/clay_rtl.css");
					copySpec.include("**/main.css");
					copySpec.include("**/main_rtl.css");
				}

			});

		DirectoryProperty destinationDirectoryProperty =
			task.getDestinationDirectory();

		destinationDirectoryProperty.set(
			new File(project.getProjectDir(), "dist"));

		return task;
	}

	private void _configureTaskDesignPack(Project project, Zip zipTask) {
		Property<String> archiveBaseNameProperty = zipTask.getArchiveBaseName();

		archiveBaseNameProperty.set(
			project.provider(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						StringBuilder sb = new StringBuilder();

						sb.append(project.getName());

						return sb.toString();
					}

				}));

		Property<String> archiveVersion = zipTask.getArchiveVersion();

		archiveVersion.set(
			project.provider(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						return null;
					}

				}));

		zipTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					project.delete(
						new File(
							project.getProjectDir(),
							"dist/" + project.getName() + ".war"));
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

	private void _configureTaskPackageRunBuild(
		Project project, Zip taskZipDesignPack) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PackageRunBuildTask.class,
			new Action<PackageRunBuildTask>() {

				@Override
				public void execute(PackageRunBuildTask packageRunBuildTask) {
					packageRunBuildTask.finalizedBy(taskZipDesignPack);
				}

			});
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

	@SuppressWarnings("unchecked")
	private boolean _isLiferayTheme(Path packageJsonPath) {
		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonPath.toFile());

		Map<String, Object> liferayTheme =
			(Map<String, Object>)packageJsonMap.get("liferayTheme");

		if (liferayTheme != null) {
			return true;
		}

		return false;
	}

	private final Set<File> _defaultRootDirs;

}