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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.jsdoc.JSDocPlugin;
import com.liferay.gradle.plugins.jsdoc.JSDocTask;

import java.io.File;
import java.io.IOException;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.resources.ResourceHandler;
import org.gradle.api.resources.TextResourceFactory;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class JSDocDefaultsPlugin extends BaseDefaultsPlugin<JSDocPlugin> {

	public static final Plugin<Project> INSTANCE = new JSDocDefaultsPlugin();

	@Override
	protected void configureDefaults(Project project, JSDocPlugin jsdocPlugin) {
		_configureTasksJSDoc(project);
	}

	@Override
	protected Class<JSDocPlugin> getPluginClass() {
		return JSDocPlugin.class;
	}

	private JSDocDefaultsPlugin() {
	}

	private void _configureTaskJSDoc(JSDocTask jsdocTask) {
		Project project = jsdocTask.getProject();

		ResourceHandler resourceHandler = project.getResources();

		TextResourceFactory textResourceFactory = resourceHandler.getText();

		jsdocTask.setConfiguration(
			textResourceFactory.fromString(_CONFIG_JSON));

		File readmeFile = project.file("README.markdown");

		if (readmeFile.exists()) {
			jsdocTask.setReadmeFile(readmeFile);
		}
	}

	private void _configureTasksJSDoc(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			JSDocTask.class,
			new Action<JSDocTask>() {

				@Override
				public void execute(JSDocTask jsdocTask) {
					_configureTaskJSDoc(jsdocTask);
				}

			});
	}

	private static final String _CONFIG_JSON;

	static {
		try {
			_CONFIG_JSON = FileUtil.read(
				"com/liferay/gradle/plugins/defaults/internal/dependencies" +
					"/config-jsdoc.json");
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}