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

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.IOException;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.quality.FindBugsExtension;
import org.gradle.api.plugins.quality.FindBugsPlugin;
import org.gradle.api.resources.ResourceHandler;
import org.gradle.api.resources.TextResourceFactory;

/**
 * @author Andrea Di Giorgi
 */
public class FindBugsDefaultsPlugin extends BaseDefaultsPlugin<FindBugsPlugin> {

	public static final Plugin<Project> INSTANCE = new FindBugsDefaultsPlugin();

	@Override
	protected void configureDefaults(
		Project project, FindBugsPlugin findBugsPlugin) {

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureFindBugs(project);
				}

			});
	}

	@Override
	protected Class<FindBugsPlugin> getPluginClass() {
		return FindBugsPlugin.class;
	}

	private FindBugsDefaultsPlugin() {
	}

	private void _configureFindBugs(Project project) {
		FindBugsExtension findBugsExtension = GradleUtil.getExtension(
			project, FindBugsExtension.class);

		if (findBugsExtension.getExcludeFilter() != null) {
			return;
		}

		ResourceHandler resourceHandler = project.getResources();

		TextResourceFactory textResourceFactory = resourceHandler.getText();

		findBugsExtension.setExcludeFilterConfig(
			textResourceFactory.fromString(_EXCLUDE_XML));
	}

	private static final String _EXCLUDE_XML;

	static {
		try {
			_EXCLUDE_XML = FileUtil.read(
				"com/liferay/gradle/plugins/internal/dependencies" +
					"/fb-exclude.xml");
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}