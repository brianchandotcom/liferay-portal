/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		project.setGroup(GradleUtil.getProjectGroup(project, _GROUP));
	}

	private static final String _GROUP = "com.liferay";

}