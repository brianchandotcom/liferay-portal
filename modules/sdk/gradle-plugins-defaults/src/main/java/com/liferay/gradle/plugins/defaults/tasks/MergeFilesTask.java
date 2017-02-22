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

package com.liferay.gradle.plugins.defaults.tasks;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.LinkedHashMap;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class MergeFilesTask extends DefaultTask {

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getInputFiles() {
		Project project = getProject();

		return project.files(_fileMergeMap.keySet());
	}

	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	public MergeFilesTask merge(Object file, Object header, Object footer) {
		MergeOptions mergeOptions = new MergeOptions(header, footer);

		_fileMergeMap.put(file, mergeOptions);

		return this;
	}

	@TaskAction
	public void mergeFiles() throws IOException {
		File outputFile = getOutputFile();
		Project project = getProject();

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				outputFile.toPath(), StandardCharsets.UTF_8)) {

			for (Map.Entry<Object, MergeOptions> entry :
					_fileMergeMap.entrySet()) {

				File file = GradleUtil.toFile(project, entry.getKey());

				MergeOptions mergeOptions = entry.getValue();

				String header = GradleUtil.toString(mergeOptions.header);

				if (Validator.isNotNull(header)) {
					bufferedWriter.write(header);
				}

				String content = new String(
					Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

				bufferedWriter.write(content);

				String footer = GradleUtil.toString(mergeOptions.footer);

				if (Validator.isNotNull(footer)) {
					bufferedWriter.write(footer);
				}

				bufferedWriter.write(System.lineSeparator());
			}
		}
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	private final Map<Object, MergeOptions> _fileMergeMap =
		new LinkedHashMap<>();
	private Object _outputFile;

	private static class MergeOptions {

		public MergeOptions(Object header, Object footer) {
			this.header = header;
			this.footer = footer;
		}

		public final Object footer;
		public final Object header;

	}

}