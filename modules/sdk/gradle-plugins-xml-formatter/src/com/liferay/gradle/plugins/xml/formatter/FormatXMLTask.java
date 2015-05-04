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
import com.liferay.xml.formatter.XMLFormatterArgs;

import groovy.lang.Closure;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
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

	public boolean isStripComments() {
		return _xmlFormatterArgs.isStripComments();
	}

	public void setStripComments(boolean stripComments) {
		_xmlFormatterArgs.setStripComments(stripComments);
	}

	protected void formatXML(final File file) {
		final Project project = getProject();

		project.javaexec(
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(JavaExecSpec javaExecSpec) {
					javaExecSpec.setClasspath(getClasspath());
					javaExecSpec.setMain(
						"com.liferay.xml.formatter.XMLFormatter");
					javaExecSpec.setSystemProperties(getSystemProperties(file));
					javaExecSpec.setWorkingDir(project.getProjectDir());
				}

			});
	}

	protected FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			XMLFormatterPlugin.CONFIGURATION_NAME);
	}

	protected Map<String, Object> getSystemProperties(File file) {
		Map<String, Object> systemProperties = new HashMap<>();

		systemProperties.put(
			"xml.formatter.file", FileUtil.getAbsolutePath(file));
		systemProperties.put("xml.formatter.strip.comments", isStripComments());

		return systemProperties;
	}

	private final XMLFormatterArgs _xmlFormatterArgs = new XMLFormatterArgs();

}