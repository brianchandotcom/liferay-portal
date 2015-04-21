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

package com.liferay.portal.tools.source.formatter.gradle;

import com.liferay.portal.tools.source.formatter.SourceFormatter;
import com.liferay.portal.tools.source.formatter.SourceFormatterBean;

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

		SourceFormatterBean sourceFormatterBean = extensionContainer.findByType(
			SourceFormatterBean.class);

		if (sourceFormatterBean == null) {
			sourceFormatterBean = new SourceFormatterBean();
		}

		try {
			SourceFormatter sourceFormatter = new SourceFormatter(
				sourceFormatterBean);

			sourceFormatter.format();

			List<String> processedFiles = sourceFormatter.getProcessedFiles();

			ExtraPropertiesExtension extraProperties =
				extensionContainer.getExtraProperties();

			extraProperties.set(
				SourceFormatter.PROCESSED_FILES_ATTRIBUTE, processedFiles);
		}
		catch (Exception e) {
			throw new GradleException(e.getMessage(), e);
		}
	}

}