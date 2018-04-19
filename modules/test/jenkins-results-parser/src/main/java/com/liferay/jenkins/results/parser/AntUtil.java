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

package com.liferay.jenkins.results.parser;

import java.util.Map;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Task;

/**
 * @author Cesar Polanco
 */
public class AntUtil {

	public static void callMacrodef(
		Map<String, String> attributes, String name, Project project) {

		Task task = project.createTask(name);

		RuntimeConfigurable runtimeConfigurable =
			task.getRuntimeConfigurableWrapper();

		for (Map.Entry<String, String> attribute : attributes.entrySet()) {
			runtimeConfigurable.setAttribute(
				attribute.getKey(), attribute.getValue());
		}

		task.setRuntimeConfigurableWrapper(runtimeConfigurable);

		task.perform();
	}

}