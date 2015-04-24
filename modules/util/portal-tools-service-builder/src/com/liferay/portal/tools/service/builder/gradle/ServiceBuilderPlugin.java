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

import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		ExtensionContainer extensionContainer = project.getExtensions();

		extensionContainer.create("serviceBuilder", ServiceBuilderArgs.class);

		TaskContainer taskContainer = project.getTasks();

		taskContainer.create("buildService", ServiceBuilderTask.class);
	}

}