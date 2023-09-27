/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.task;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.Validator;

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
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class MergeFilesTask extends DefaultTask {

	@Input
	@Optional
	public String getFooter() {
		return GradleUtil.toString(_footer);
	}

	@Input
	@Optional
	public String getHeader() {
		return GradleUtil.toString(_header);
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
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
		FileCollection inputFiles = getInputFiles();

		inputFiles = inputFiles.filter(
			new Spec<File>() {

				@Override
				public boolean isSatisfiedBy(File file) {
					return file.exists();
				}

			});

		File outputFile = getOutputFile();

		if (inputFiles.isEmpty()) {
			outputFile.delete();

			return;
		}

		String separator = getSeparator();

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				outputFile.toPath(), StandardCharsets.UTF_8)) {

			String header = getHeader();

			if (Validator.isNotNull(header)) {
				bufferedWriter.write(header);
			}

			for (File inputFile : inputFiles) {
				bufferedWriter.write(separator);

				String content = new String(
					Files.readAllBytes(inputFile.toPath()),
					StandardCharsets.UTF_8);

				bufferedWriter.write(content);
			}

			String footer = getFooter();

			if (Validator.isNotNull(footer)) {
				bufferedWriter.write(separator);
				bufferedWriter.write(footer);
			}
		}
	}

	public void setFooter(Object footer) {
		_footer = footer;
	}

	public void setHeader(Object header) {
		_header = header;
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

	private Object _footer;
	private Object _header;
	private final Set<Object> _inputFiles = new LinkedHashSet<>();
	private Object _outputFile;
	private Object _separator = System.lineSeparator();

}