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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class MergeFilesTask extends DefaultTask {

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getInputFiles() {
		Project project = getProject();

		return project.files(_inputFiles);
	}

	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	public String getSeparator() {
		return GradleUtil.toString(_separator);
	}

	public MergeFilesTask inputFiles(Iterable<?> inputFiles) {
		GUtil.addToCollection(_inputFiles, inputFiles);

		return this;
	}

	public MergeFilesTask inputFiles(Object... inputFiles) {
		return inputFiles(Arrays.asList(inputFiles));
	}

	@TaskAction
	public void mergeFiles() throws IOException {
		File outputFile = getOutputFile();
		String separator = getSeparator();

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				outputFile.toPath(), StandardCharsets.UTF_8)) {

			boolean first = true;

			for (File inputFile : getInputFiles()) {
				if (!first) {
					bufferedWriter.write(separator);
				}

				first = false;

				String content = new String(
					Files.readAllBytes(inputFile.toPath()),
					StandardCharsets.UTF_8);

				bufferedWriter.write(content);
			}
		}
	}

	public void setInputFiles(Iterable<?> inputFiles) {
		_inputFiles.clear();

		inputFiles(inputFiles);
	}

	public void setInputFiles(Object... inputFiles) {
		setInputFiles(Arrays.asList(inputFiles));
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	public void setSeparator(Object separator) {
		_separator = separator;
	}

	private final Set<Object> _inputFiles = new LinkedHashSet<>();
	private Object _outputFile;
	private Object _separator = System.lineSeparator();

}