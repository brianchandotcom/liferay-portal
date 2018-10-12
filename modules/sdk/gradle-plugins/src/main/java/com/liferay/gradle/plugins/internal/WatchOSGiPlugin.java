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

package com.liferay.gradle.plugins.internal;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.internal.util.GradlePluginsUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.internal.util.IncludeResourceCompileIncludeInstruction;
import com.liferay.gradle.plugins.tasks.ExecuteBndTask;
import com.liferay.gradle.plugins.tasks.WatchTask;

import java.io.File;
import java.io.FileInputStream;

import java.util.concurrent.Callable;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class WatchOSGiPlugin implements Plugin<Project> {

	public static final String BUILD_BUNDLE_DIR_TASK_NAME = "buildBundleDir";

	public static final Plugin<Project> INSTANCE = new WatchOSGiPlugin();

	public static final String JAR_COMPILE_INCLUDE_FRAGMENT_TASK_NAME =
		"jarCompileIncludeFragment";

	public static final String WATCH_TASK_NAME = "watch";

	@Override
	public void apply(Project project) {
		ExecuteBndTask jarCompileIncludeFragmentTask =
			_addTaskJarCompileIncludeFragment(project);

		Sync buildBundleDirTask = _addTaskBuildBundleDir(project);

		_addTaskWatch(buildBundleDirTask, jarCompileIncludeFragmentTask);
	}

	private WatchOSGiPlugin() {
	}

	private Sync _addTaskBuildBundleDir(final Project project) {
		Sync sync = GradleUtil.addTask(
			project, BUILD_BUNDLE_DIR_TASK_NAME, Sync.class);

		final Jar jar = (Jar)GradleUtil.getTask(
			project, JavaPlugin.JAR_TASK_NAME);

		sync.dependsOn(jar);

		sync.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					return project.zipTree(jar.getArchivePath());
				}

			});

		sync.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						project.getBuildDir(), BUILD_BUNDLE_DIR_TASK_NAME);
				}

			});

		sync.setDescription(
			"Unzips the project's JAR file into a temporary directory.");

		return sync;
	}

	private ExecuteBndTask _addTaskJarCompileIncludeFragment(
		final Project project) {

		ExecuteBndTask executeBndTask = GradleUtil.addTask(
			project, JAR_COMPILE_INCLUDE_FRAGMENT_TASK_NAME,
			ExecuteBndTask.class);

		executeBndTask.property(
			Constants.BUNDLE_NAME,
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return GradlePluginsUtil.getBundleInstruction(
						project, Constants.BUNDLE_NAME) + " Libs";
				}

			});

		executeBndTask.property(
			Constants.BUNDLE_SYMBOLICNAME,
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return GradlePluginsUtil.getBundleInstruction(
						project, Constants.BUNDLE_SYMBOLICNAME) + ".libs";
				}

			});

		executeBndTask.property(Constants.BUNDLE_VERSION, "1.0.0");

		executeBndTask.property(
			Constants.FRAGMENT_HOST,
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return GradlePluginsUtil.getBundleInstruction(
						project, Constants.BUNDLE_SYMBOLICNAME);
				}

			});

		executeBndTask.property(
			Constants.INCLUDERESOURCE,
			new IncludeResourceCompileIncludeInstruction(
				new Callable<Iterable<File>>() {

					@Override
					public Iterable<File> call() throws Exception {
						return GradleUtil.getConfiguration(
							project,
							LiferayOSGiPlugin.
								COMPILE_INCLUDE_CONFIGURATION_NAME);
					}

				},
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return Boolean.FALSE;
					}

				}));

		executeBndTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Configuration configuration = GradleUtil.getConfiguration(
						project,
						LiferayOSGiPlugin.COMPILE_INCLUDE_CONFIGURATION_NAME);

					return !configuration.isEmpty();
				}

			});

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		executeBndTask.setClasspath(sourceSet.getCompileClasspath());

		executeBndTask.setDescription(
			"Generates an OSGi fragment containing all dependencies of " +
				LiferayOSGiPlugin.COMPILE_INCLUDE_CONFIGURATION_NAME + ".");
		executeBndTask.setGroup(BasePlugin.BUILD_GROUP);

		executeBndTask.setOutputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						project.getBuildDir(),
						project.getName() + "-libs." + Jar.DEFAULT_EXTENSION);
				}

			});

		executeBndTask.setResourceDirs(project.files());
		executeBndTask.setSourceDirs(project.files());

		return executeBndTask;
	}

	private WatchTask _addTaskWatch(
		final Sync buildBundleDirTask,
		ExecuteBndTask jarCompileIncludeFragmentTask) {

		final WatchTask watchTask = GradleUtil.addTask(
			buildBundleDirTask.getProject(), WATCH_TASK_NAME, WatchTask.class);

		watchTask.dependsOn(buildBundleDirTask);

		watchTask.setBundleDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildBundleDirTask.getDestinationDir();
				}

			});

		watchTask.setBundleSymbolicName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					File manifestFile = new File(
						buildBundleDirTask.getDestinationDir(),
						"META-INF/MANIFEST.MF");

					if (manifestFile.exists()) {
						try (FileInputStream fileInputStream =
								new FileInputStream(manifestFile)) {

							Manifest manifest = new Manifest(fileInputStream);

							Attributes attributes =
								manifest.getMainAttributes();

							return attributes.getValue("Bundle-SymbolicName");
						}
					}

					return null;
				}

			});

		watchTask.setDescription(
			"Continuously redeploys the project's OSGi bundle.");
		watchTask.setGroup(BasePlugin.BUILD_GROUP);

		TaskOutputs taskOutputs = jarCompileIncludeFragmentTask.getOutputs();

		watchTask.setFragments(taskOutputs.getFiles());

		return watchTask;
	}

}