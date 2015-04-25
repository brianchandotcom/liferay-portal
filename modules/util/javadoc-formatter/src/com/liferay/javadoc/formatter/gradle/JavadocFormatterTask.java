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

import com.liferay.javadoc.formatter.JavadocFormatter;
import com.liferay.javadoc.formatter.JavadocFormatterArgs;
import com.liferay.javadoc.formatter.JavadocFormatterInvoker;

import java.util.Set;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterTask extends DefaultTask {

	@TaskAction
	public void formatJavadoc() {
		Project project = getProject();

		ExtensionContainer extensionContainer = project.getExtensions();

		JavadocFormatterArgs javadocFormatterArgs =
			extensionContainer.getByType(JavadocFormatterArgs.class);

		try {
			JavadocFormatter javadocFormatter = JavadocFormatterInvoker.invoke(
				project.getProjectDir(), javadocFormatterArgs);

			Set<String> modifiedFileNames =
				javadocFormatter.getModifiedFileNames();

			ExtraPropertiesExtension extraPropertiesExtension =
				extensionContainer.getExtraProperties();

			extraPropertiesExtension.set(
				JavadocFormatter.OUTPUT_KEY_MODIFIED_FILES, modifiedFileNames);
		}
		catch (Exception e) {
			throw new GradleException(e.getMessage(), e);
		}
	}

}