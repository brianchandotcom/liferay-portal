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

import java.io.File;
import java.io.IOException;

import java.util.Map;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Task;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Cesar Polanco
 * @author Michael Hashimoto
 */
public class AntUtil {

	public static void callMacrodef(
		Project project, String name, Map<String, String> attributes) {

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

	public static void callTarget(
		Project project, File baseDir, File buildFile, String targetName,
		Map<String, String> parameters) {

		if (targetName == null) {
			targetName = project.getDefaultTarget();
		}

		String projectName = project.getName();

		if (buildFile != null) {
			ProjectHelper.configureProject(project, buildFile);

			projectName = _getProjectName(buildFile);
		}

		if (!projectName.equals(project.getName())) {
			targetName = JenkinsResultsParserUtil.combine(
				projectName, ".", targetName);
		}

		if (parameters != null) {
			for (Map.Entry<String, String> parameter : parameters.entrySet()) {
				project.setUserProperty(
					parameter.getKey(), parameter.getValue());
			}
		}

		File projectBaseDir = project.getBaseDir();

		boolean useBaseDir = false;

		if ((baseDir != null) && !baseDir.equals(projectBaseDir)) {
			useBaseDir = true;
		}

		try {
			if (useBaseDir) {
				project.setBaseDir(baseDir);
			}

			project.executeTarget(targetName);
		}
		finally {
			if (useBaseDir) {
				project.setBaseDir(projectBaseDir);
			}
		}
	}

	private static String _getProjectName(File buildFile) {
		try {
			String buildFileContent = JenkinsResultsParserUtil.read(buildFile);

			Document document = Dom4JUtil.parse(buildFileContent);

			Element element = document.getRootElement();

			return element.attributeValue("name");
		}
		catch (DocumentException | IOException e) {
			throw new RuntimeException(e);
		}
	}

}