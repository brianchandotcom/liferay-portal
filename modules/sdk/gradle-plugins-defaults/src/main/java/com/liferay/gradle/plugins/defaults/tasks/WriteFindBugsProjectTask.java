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

import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class WriteFindBugsProjectTask extends DefaultTask {

	public WriteFindBugsProjectTask() {
		Project project = getProject();

		_auxClasspath = project.files();
		_classpath = project.files();
		_srcDirs = project.files();
	}

	public WriteFindBugsProjectTask auxClasspath(Object... auxClasspath) {
		Project project = getProject();

		FileCollection fileCollection = project.files(auxClasspath);

		_auxClasspath = _auxClasspath.plus(fileCollection);

		return this;
	}

	public WriteFindBugsProjectTask classpath(Object... classpath) {
		Project project = getProject();

		FileCollection fileCollection = project.files(classpath);

		_classpath = _classpath.plus(fileCollection);

		return this;
	}

	@Input
	public FileCollection getAuxClasspath() {
		return _auxClasspath;
	}

	@Input
	public FileCollection getClasspath() {
		return _classpath;
	}

	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	public String getProjectName() {
		return GradleUtil.toString(_projectName);
	}

	@Input
	public FileCollection getSrcDirs() {
		return _srcDirs;
	}

	public void setAuxClasspath(FileCollection auxClasspath) {
		_auxClasspath = auxClasspath;
	}

	public void setClasspath(FileCollection classpath) {
		_classpath = classpath;
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	public void setProjectName(Object projectName) {
		_projectName = projectName;
	}

	public void setSrcDirs(FileCollection srcDirs) {
		_srcDirs = srcDirs;
	}

	public WriteFindBugsProjectTask srcDirs(Object... srcDirs) {
		Project project = getProject();

		FileCollection fileCollection = project.files(srcDirs);

		_srcDirs = _srcDirs.plus(fileCollection);

		return this;
	}

	@TaskAction
	public void writeFindBugsProject() throws Exception {
		File outputFile = getOutputFile();

		Path outputPath = outputFile.toPath();

		Files.createDirectories(outputPath.getParent());

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				outputFile.toPath(), StandardCharsets.UTF_8)) {

			bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bufferedWriter.write(System.lineSeparator());

			bufferedWriter.write(System.lineSeparator());

			bufferedWriter.write("<Project projectName=\"");
			bufferedWriter.write(getProjectName());
			bufferedWriter.write("\">");

			bufferedWriter.write(System.lineSeparator());

			_writeFileElements(
				bufferedWriter, "AuxClasspathEntry", getAuxClasspath());
			_writeFileElements(bufferedWriter, "Jar", getClasspath());
			_writeFileElements(bufferedWriter, "SrcDir", getSrcDirs());

			bufferedWriter.write("</Project>");
		}
	}

	private void _writeFileElements(
			Writer writer, String name, FileCollection fileCollection)
		throws IOException {

		for (File file : fileCollection) {
			writer.write("\t<");
			writer.write(name);
			writer.write('>');
			writer.write(FileUtil.getAbsolutePath(file));
			writer.write("</");
			writer.write(name);
			writer.write('>');

			writer.write(System.lineSeparator());
		}
	}

	private FileCollection _auxClasspath;
	private FileCollection _classpath;
	private Object _outputFile;
	private Object _projectName;
	private FileCollection _srcDirs;

}