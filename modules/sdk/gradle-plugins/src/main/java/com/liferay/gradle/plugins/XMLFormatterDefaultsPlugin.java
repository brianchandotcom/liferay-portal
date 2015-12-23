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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.wsdl.builder.BuildWSDLTask;
import com.liferay.gradle.plugins.xml.formatter.FormatXMLTask;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.plugins.xsd.builder.BuildXSDTask;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class XMLFormatterDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<XMLFormatterPlugin> {

	public static final String FORMAT_WSDL_TASK_NAME = "formatWSDL";

	public static final String FORMAT_XSD_TASK_NAME = "formatXSD";

	protected FormatXMLTask addTaskFormatWSDL(
		final BuildWSDLTask buildWSDLTask) {

		Project project = buildWSDLTask.getProject();

		TaskContainer taskContainer = project.getTasks();

		FormatXMLTask formatXMLTask = taskContainer.maybeCreate(
			FORMAT_WSDL_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format WSDL files.");
		formatXMLTask.setIncludes(Collections.singleton("**/*.wsdl"));

		formatXMLTask.source(
			new Callable<File>() {

			@Override
			public File call() throws Exception {
				return buildWSDLTask.getInputDir();
			}

		});

		return formatXMLTask;
	}

	protected void addTaskFormatWSDL(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildWSDLTask.class,
			new Action<BuildWSDLTask>() {

				@Override
				public void execute(BuildWSDLTask buildWSDLTask) {
					addTaskFormatWSDL(buildWSDLTask);
				}

			});
	}

	protected FormatXMLTask addTaskFormatXSD(final BuildXSDTask buildXSDTask) {
		Project project = buildXSDTask.getProject();

		TaskContainer taskContainer = project.getTasks();

		FormatXMLTask formatXMLTask = taskContainer.maybeCreate(
			FORMAT_XSD_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format XSD files.");
		formatXMLTask.setIncludes(Collections.singleton("**/*.xsd"));

		formatXMLTask.source(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildXSDTask.getInputDir();
				}

			});

		return formatXMLTask;
	}

	protected void addTaskFormatXSD(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildXSDTask.class,
			new Action<BuildXSDTask>() {

				@Override
				public void execute(BuildXSDTask buildXSDTask) {
					addTaskFormatXSD(buildXSDTask);
				}

			});
	}

	@Override
	protected void configureDefaults(
		Project project, XMLFormatterPlugin xmlFormatterPlugin) {

		super.configureDefaults(project, xmlFormatterPlugin);

		addTaskFormatWSDL(project);
		addTaskFormatXSD(project);
	}

	@Override
	protected Class<XMLFormatterPlugin> getPluginClass() {
		return XMLFormatterPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return XMLFormatterPlugin.CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private static final String _PORTAL_TOOL_NAME = "com.liferay.xml.formatter";

}