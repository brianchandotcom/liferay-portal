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

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginContainer;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseDefaultsPlugin<T extends Plugin<Project>>
	implements Plugin<Project> {

	@Override
	public void apply(final Project project) {
		withPlugin(
			project, getPluginClass(),
			new Action<T>() {

				@Override
				public void execute(T plugin) {
					configureDefaults(project, plugin);
				}

			});
	}

	protected abstract void configureDefaults(Project project, T plugin);

	protected abstract Class<T> getPluginClass();

	protected void withLiferayPlugin(
		Project project, Action<LiferayPlugin> action) {

		withPlugin(project, LiferayPlugin.class, action);
	}

	protected <P extends Plugin<? extends Project>> void withPlugin(
		Project project, Class<P> pluginClass, Action<P> action) {

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(pluginClass, action);
	}

}