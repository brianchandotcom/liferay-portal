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
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.extensions.LiferayExtension;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayBaseDefaultsPlugin
	extends BaseDefaultsPlugin<LiferayBasePlugin> {

	public static final Plugin<Project> INSTANCE =
		new LiferayBaseDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, LiferayBasePlugin liferayBasePlugin) {

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureLiferayDeployDir(project);
				}

			});
	}

	@Override
	protected Class<LiferayBasePlugin> getPluginClass() {
		return LiferayBasePlugin.class;
	}

	private LiferayBaseDefaultsPlugin() {
	}

	private void _configureLiferayDeployDir(Project project) {
		File forcedDeployDir = GradleUtil.getProperty(
			project, "forced.deploy.dir", (File)null);

		if (forcedDeployDir != null) {
			LiferayExtension liferayExtension = GradleUtil.getExtension(
				project, LiferayExtension.class);

			liferayExtension.setDeployDir(forcedDeployDir);
		}
	}

}