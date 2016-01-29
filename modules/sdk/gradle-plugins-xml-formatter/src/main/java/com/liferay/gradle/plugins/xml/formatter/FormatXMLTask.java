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

package com.liferay.gradle.plugins.xml.formatter;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class FormatXMLTask extends SourceTask {

	@TaskAction
	public void formatXML() {
		FileTree fileTree = getSource();

		for (File file : fileTree) {
			formatXML(file);
		}
	}

	@InputFiles
	public FileCollection getClasspath() {
		return _classpath;
	}

	@Input
	public String getMainClassName() {
		return GradleUtil.toString(_mainClassName);
	}

	@Input
	public boolean isStripComments() {
		return _stripComments;
	}

	public void setClasspath(FileCollection classpath) {
		_classpath = classpath;
	}

	public void setMainClassName(Object mainClassName) {
		_mainClassName = mainClassName;
	}

	public void setStripComments(boolean stripComments) {
		_stripComments = stripComments;
	}

	protected void formatXML(final File file) {
		Project project = getProject();

		project.javaexec(
			new Action<JavaExecSpec>() {

				@Override
				public void execute(JavaExecSpec javaExecSpec) {
					javaExecSpec.setClasspath(getClasspath());
					javaExecSpec.setMain(getMainClassName());
					javaExecSpec.setSystemProperties(getSystemProperties(file));
				}

			});
	}

	protected Map<String, Object> getSystemProperties(File file) {
		Map<String, Object> systemProperties = new HashMap<>();

		systemProperties.put(
			"xml.formatter.file", FileUtil.getAbsolutePath(file));
		systemProperties.put("xml.formatter.strip.comments", isStripComments());

		return systemProperties;
	}

	private FileCollection _classpath;
	private Object _mainClassName = "com.liferay.xml.formatter.XMLFormatter";
	private boolean _stripComments;

}