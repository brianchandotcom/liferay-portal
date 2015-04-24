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

package com.liferay.portal.tools.service.builder.gradle;

import com.liferay.portal.tools.service.builder.ServiceBuilder;
import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;
import com.liferay.portal.tools.service.builder.ServiceBuilderInvoker;

import java.util.Set;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderTask extends DefaultTask {

	@TaskAction
	public void buildService() {
		try {
			Project project = getProject();

			ExtensionContainer extensionContainer = project.getExtensions();

			ExtraPropertiesExtension extraProperties =
				extensionContainer.getExtraProperties();

			ServiceBuilderArgs serviceBuilderArgs =
				extensionContainer.findByType(ServiceBuilderArgs.class);

			if (serviceBuilderArgs == null) {
				serviceBuilderArgs = new ServiceBuilderArgs();
			}

			ServiceBuilder serviceBuilder = ServiceBuilderInvoker.invoke(
				project.getProjectDir(), serviceBuilderArgs);

			Set<String> modifiedFileNames =
				serviceBuilder.getModifiedFileNames();

			extraProperties.set(
				ServiceBuilder.OUTPUT_KEY_MODIFIED_FILES, modifiedFileNames);
		}
		catch (Exception e) {
			throw new GradleException(e.getMessage(), e);
		}
	}

}