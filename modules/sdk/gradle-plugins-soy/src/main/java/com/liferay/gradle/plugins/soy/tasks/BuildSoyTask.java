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

package com.liferay.gradle.plugins.soy.tasks;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildSoyTask extends SourceTask {

	@TaskAction
	public void buildSoy() {
		Project project = getProject();

		project.javaexec(
			new Action<JavaExecSpec>() {

				@Override
				public void execute(JavaExecSpec javaExecSpec) {
					javaExecSpec.args(
						"--outputPathFormat",
						"{INPUT_DIRECTORY}/{INPUT_FILE_NAME}.js");
					javaExecSpec.args("--srcs", _getSourceFileNames());

					javaExecSpec.setClasspath(getClasspath());
					javaExecSpec.setMain(
						"com.google.template.soy.SoyToJsSrcCompiler");
				}

			});
	}

	@InputFiles
	public FileCollection getClasspath() {
		return _classpath;
	}

	@OutputFiles
	public Iterable<File> getOutputFiles() {
		List<File> outputFiles = new ArrayList<>();

		for (File sourceFile : getSource()) {
			String fileName = sourceFile.getName();

			File outputFile = new File(
				sourceFile.getParentFile(), fileName + ".js");

			outputFiles.add(outputFile);
		}

		return outputFiles;
	}

	public void setClasspath(FileCollection classpath) {
		_classpath = classpath;
	}

	private String _getSourceFileNames() {
		FileCollection sourceFiles = getSource();

		if (sourceFiles.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		Project project = getProject();

		for (File sourceFile : sourceFiles) {
			sb.append(project.relativePath(sourceFile));
			sb.append(',');
		}

		return sb.substring(0, sb.length() - 1);
	}

	private FileCollection _classpath;

}