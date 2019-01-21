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

import com.liferay.gradle.plugins.SourceFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.go.GoExtension;
import com.liferay.gradle.plugins.go.GoPlugin;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;

/**
 * @author Peter Shin
 */
public class LiferayGoDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		_applyPlugins(project);

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		GradlePluginsDefaultsUtil.configureRepositories(project, portalRootDir);

		_configureProject(project);

		_configureGo(project);
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, GoPlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
	}

	private void _configureGo(Project project) {
		GoExtension goExtension = GradleUtil.getExtension(
			project, GoExtension.class);

		goExtension.setWorkingDir(new File(project.getProjectDir(), "go"));
	}

	private void _configureProject(Project project) {
		String group = GradleUtil.getGradlePropertiesValue(
			project, "project.group", _GROUP);

		project.setGroup(group);
	}

	private static final String _GROUP = "com.liferay";

}