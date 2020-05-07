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

import com.github.spotbugs.snom.SpotBugsExtension;
import com.github.spotbugs.snom.SpotBugsPlugin;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Provider;

/**
 * @author Peter Shin
 */
public class SpotBugsDefaultsPlugin extends BaseDefaultsPlugin<SpotBugsPlugin> {

	public static final Plugin<Project> INSTANCE = new SpotBugsDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, SpotBugsPlugin spotBugsPlugin) {

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureSpotBugs(project);
				}

			});
	}

	@Override
	protected Class<SpotBugsPlugin> getPluginClass() {
		return SpotBugsPlugin.class;
	}

	private SpotBugsDefaultsPlugin() {
	}

	private void _configureSpotBugs(final Project project) {
		SpotBugsExtension spotBugsExtension = GradleUtil.getExtension(
			project, SpotBugsExtension.class);

		RegularFileProperty excludeFilterRegularFileProperty =
			spotBugsExtension.getExcludeFilter();

		if (excludeFilterRegularFileProperty.isPresent()) {
			return;
		}

		File spotBugsExcludeXmlFile = project.file(_EXCLUDE_XML_FILE_NAME);

		if (!spotBugsExcludeXmlFile.exists()) {
			excludeFilterRegularFileProperty.set(
				project.provider(
					new Callable<RegularFile>() {

						@Override
						public RegularFile call() throws Exception {
							ProjectLayout projectLayout = project.getLayout();

							DirectoryProperty directoryProperty =
								projectLayout.getBuildDirectory();

							Provider<RegularFile> provider =
								directoryProperty.file(_EXCLUDE_XML_FILE_NAME);

							RegularFile regularFile = provider.get();

							File file = regularFile.getAsFile();

							Files.write(
								file.toPath(),
								_EXCLUDE_XML.getBytes(StandardCharsets.UTF_8));

							return regularFile;
						}

					}));
		}
		else {
			excludeFilterRegularFileProperty.set(spotBugsExcludeXmlFile);
		}
	}

	private static final String _EXCLUDE_XML;

	private static final String _EXCLUDE_XML_FILE_NAME =
		"spot-bugs-exclude.xml";

	static {
		try {
			_EXCLUDE_XML = FileUtil.read(
				"com/liferay/gradle/plugins/defaults/internal/dependencies/" +
					_EXCLUDE_XML_FILE_NAME);
		}
		catch (IOException ioException) {
			throw new ExceptionInInitializerError(ioException);
		}
	}

}