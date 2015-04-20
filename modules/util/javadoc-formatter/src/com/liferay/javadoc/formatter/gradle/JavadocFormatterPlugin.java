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

package com.liferay.javadoc.formatter.gradle;

import com.liferay.javadoc.formatter.JavadocFormatterBean;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		addJavadocFormatterExtension(project);

		addFormatJavadocTask(project);
	}

	protected void addFormatJavadocTask(Project project) {
		TaskContainer taskContainer = project.getTasks();

		JavadocFormatterTask javadocFormatterTask = taskContainer.create(
			"formatJavadoc", JavadocFormatterTask.class);

		javadocFormatterTask.setDescription(
			"Runs Liferay Javadoc Formatter to format files.");
	}

	protected void addJavadocFormatterExtension(Project project) {
		JavadocFormatterBean javadocFormatterBean = new JavadocFormatterBean();

		javadocFormatterBean.setInputDir(project.getProjectDir());

		ExtensionContainer extensionContainer = project.getExtensions();

		extensionContainer.add("javadocFormatter", javadocFormatterBean);
	}

}