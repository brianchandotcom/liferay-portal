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

package com.liferay.gradle.plugins.source.formatter;

import com.liferay.source.formatter.SourceFormatter;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Raymond Augé
 */
public class SourceFormatterTask extends DefaultTask {

	@TaskAction
	public void buildService() {
		Project project = getProject();

		ExtensionContainer extensionContainer = project.getExtensions();

		SourceFormatterArgs sourceFormatterArgs = extensionContainer.findByType(
			SourceFormatterArgs.class);

		if (sourceFormatterArgs == null) {
			sourceFormatterArgs = new SourceFormatterArgs();
		}

		try {
			SourceFormatter sourceFormatter = new SourceFormatter(
				sourceFormatterArgs);

			sourceFormatter.format();

			List<String> modifiedFileNames =
				sourceFormatter.getModifiedFileNames();

			ExtraPropertiesExtension extraProperties =
				extensionContainer.getExtraProperties();

			extraProperties.set(
				SourceFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				modifiedFileNames);
		}
		catch (Exception e) {
			throw new GradleException(e.getMessage(), e);
		}
	}

}