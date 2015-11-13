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

package com.liferay.gradle.plugins.patcher;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class PatcherPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PatchTask.class,
			new Action<PatchTask>() {

				@Override
				public void execute(PatchTask patchTask) {
					configureTaskPatchPatchedSrcDirMappings(patchTask);

					Project project = patchTask.getProject();

					JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
						project, JavaPlugin.COMPILE_JAVA_TASK_NAME);

					javaCompile.dependsOn(patchTask);

					Copy copy = addTaskCopyOriginalLibClasses(patchTask);

					Task classesTask = GradleUtil.getTask(
						project, JavaPlugin.CLASSES_TASK_NAME);

					classesTask.dependsOn(copy);
				}

			});
	}

	protected Copy addTaskCopyOriginalLibClasses(final PatchTask patchTask) {
		String taskName =
			"copy" + StringUtil.capitalize(patchTask.getName()) +
				"OriginalLibClasses";

		final Copy copy = GradleUtil.addTask(
			patchTask.getProject(), taskName, Copy.class);

		copy.eachFile(
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					File file = new File(
						copy.getDestinationDir(), fileCopyDetails.getPath());

					if (file.exists()) {
						fileCopyDetails.exclude();
					}
				}

			});

		copy.from(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					Project project = patchTask.getProject();

					return project.zipTree(patchTask.getOriginalLibFile());
				}

			});

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
						patchTask.getProject(),
						JavaPlugin.COMPILE_JAVA_TASK_NAME);

					return javaCompile.getDestinationDir();
				}

			});

		copy.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					if (patchTask.isCopyOriginalLibClasses()) {
						return true;
					}

					return false;
				}

			});

		TaskOutputs taskOutputs = copy.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});

		return copy;
	}

	protected void configureTaskPatchPatchedSrcDirMappings(
		PatchTask patchTask) {

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			patchTask.getProject(), SourceSet.MAIN_SOURCE_SET_NAME);

		patchTask.patchedSrcDirMapping(
			"java",
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getSrcDir(sourceSet.getJava());
				}

			});

		patchTask.patchedSrcDirMapping(
			PatchTask.PATCHED_SRC_DIR_MAPPING_DEFAULT_EXTENSION,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getSrcDir(sourceSet.getResources());
				}

			});
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

}