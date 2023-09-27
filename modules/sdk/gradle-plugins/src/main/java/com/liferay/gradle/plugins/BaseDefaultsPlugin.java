/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginContainer;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseDefaultsPlugin<T extends Plugin<? extends Project>>
	implements Plugin<Project> {

	@Override
	public void apply(final Project project) {

		// Containers

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			getPluginClass(),
			new Action<T>() {

				@Override
				public void execute(T plugin) {
					applyPluginDefaults(project, plugin);
				}

			});
	}

	protected abstract void applyPluginDefaults(Project project, T plugin);

	protected abstract Class<T> getPluginClass();

}