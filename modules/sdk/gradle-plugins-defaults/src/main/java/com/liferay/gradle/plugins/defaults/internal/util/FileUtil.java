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

package com.liferay.gradle.plugins.defaults.internal.util;

import com.liferay.gradle.util.ArrayUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskInputs;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static boolean contains(File file, String s) throws IOException {
		Path path = file.toPath();

		if (Files.notExists(path)) {
			return false;
		}

		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		if (content.contains(s)) {
			return true;
		}

		return false;
	}

	public static File[] getDirectories(File dir) {
		return dir.listFiles(
			new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return true;
					}

					return false;
				}

			});
	}

	public static FileTree getJarsFileTree(
		Project project, File dir, String... excludes) {

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

	public static FileCollection join(FileCollection... fileCollections) {
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

	public static void replace(
			Path path, String regex, String firstGroupReplacement)
		throws IOException {

		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		int groupCount = matcher.groupCount();

		content =
			content.substring(0, matcher.start(groupCount)) +
				firstGroupReplacement +
					content.substring(matcher.end(groupCount));

		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	public static void writeProperties(File file, Map<?, ?> properties) {
		File dir = file.getParentFile();

		dir.mkdirs();

		properties = new TreeMap<>(properties);

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				file.toPath(), StandardCharsets.ISO_8859_1)) {

			boolean firstLine = true;

			for (Map.Entry<?, ?> entry : properties.entrySet()) {
				String key = GradleUtil.toString(entry.getKey());
				String value = GradleUtil.toString(entry.getValue());

				if (firstLine) {
					firstLine = false;
				}
				else {
					bufferedWriter.newLine();
				}

				bufferedWriter.write(key);
				bufferedWriter.write('=');
				bufferedWriter.write(value);
			}
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

}