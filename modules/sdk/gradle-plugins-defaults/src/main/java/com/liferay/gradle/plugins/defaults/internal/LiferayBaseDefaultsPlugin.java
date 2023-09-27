/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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