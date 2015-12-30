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

package com.liferay.gradle.plugins.util;

import com.liferay.gradle.util.ArrayUtil;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskInputs;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static FileTree getJarsFileTree(
		Project project, File dir, String ... excludes) {

		Map<String, Object> args = new HashMap<>();

		args.put("dir", dir);

		if (ArrayUtil.isNotEmpty(excludes)) {
			args.put("excludes", Arrays.asList(excludes));
		}

		args.put("include", "*.jar");

		return project.fileTree(args);
	}

	public static String getRelativePath(Project project, File file) {
		String relativePath = project.relativePath(file);

		return relativePath.replace('\\', '/');
	}

	public static boolean hasSourceFiles(Task task, Spec<File> spec) {
		TaskInputs taskInputs = task.getInputs();

		FileCollection fileCollection = taskInputs.getSourceFiles();

		fileCollection = fileCollection.filter(spec);

		if (fileCollection.isEmpty()) {
			return false;
		}

		return true;
	}

	public static FileCollection join(FileCollection ... fileCollections) {
		FileCollection joinedFileCollection = null;

		for (FileCollection fileCollection : fileCollections) {
			if (joinedFileCollection == null) {
				joinedFileCollection = fileCollection;
			}
			else {
				joinedFileCollection = joinedFileCollection.plus(
					fileCollection);
			}
		}

		return joinedFileCollection;
	}

	public static void touchFile(File file, long time) {
		boolean success = file.setLastModified(time);

		if (!success) {
			_logger.error("Unable to touch " + file);
		}
	}

	public static void touchFiles(
		Project project, File dir, long time, String ... includes) {

		Map<String, Object> args = new HashMap<>();

		args.put("dir", dir);
		args.put("includes", Arrays.asList(includes));

		FileTree fileTree = project.fileTree(args);

		for (File file : fileTree) {
			touchFile(file, time);
		}
	}

	private static final Logger _logger = Logging.getLogger(FileUtil.class);

}