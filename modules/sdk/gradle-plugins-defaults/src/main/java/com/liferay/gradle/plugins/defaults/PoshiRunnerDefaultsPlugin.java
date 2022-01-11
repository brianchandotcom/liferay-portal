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

package com.liferay.gradle.plugins.defaults;

import com.github.erdi.gradle.webdriver.WebDriverBinariesPluginExtension;

import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.poshi.runner.PoshiRunnerPlugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.resources.ResourceHandler;
import org.gradle.api.resources.TextResourceFactory;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		GradlePluginsDefaultsUtil.configureRepositories(project, null);

		GradleUtil.applyPlugin(project, PoshiRunnerPlugin.class);

		_configurePoshiRunner(project);
	}

	private void _configurePoshiRunner(Project project) {
		WebDriverBinariesPluginExtension webDriverBinariesPluginExtension =
			GradleUtil.getExtension(
				project, WebDriverBinariesPluginExtension.class);

		webDriverBinariesPluginExtension.setChromedriver("86.0.4240.22");

		ResourceHandler resourceHandler = project.getResources();

		TextResourceFactory textResourceFactory = resourceHandler.getText();

		webDriverBinariesPluginExtension.setDriverUrlsConfiguration(
			textResourceFactory.fromUri(_WEB_DRIVER_URI));
	}

	private static final String _WEB_DRIVER_URI =
		"http://mirrors.lax.liferay.com/raw.githubusercontent.com" +
			"/webdriverextensions/webdriverextensions-maven-plugin-repository" +
				"/master/repository-3.0.json";

}