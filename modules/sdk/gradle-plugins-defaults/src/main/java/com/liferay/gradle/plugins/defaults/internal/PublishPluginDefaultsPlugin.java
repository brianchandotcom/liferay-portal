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

import aQute.bnd.osgi.Constants;

import com.gradle.publish.PluginBundleExtension;
import com.gradle.publish.PluginConfig;
import com.gradle.publish.PublishPlugin;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.internal.util.StringUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.specs.Spec;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class PublishPluginDefaultsPlugin
	extends BaseDefaultsPlugin<PublishPlugin> {

	public static final Plugin<Project> INSTANCE =
		new PublishPluginDefaultsPlugin();

	@Override
	protected void configureDefaults(
		Project project, PublishPlugin publishPlugin) {

		_configurePluginBundle(project);
		_configureTaskPublishPlugins(project);
		_configureTaskUploadArchives(project);
	}

	@Override
	protected Class<PublishPlugin> getPluginClass() {
		return PublishPlugin.class;
	}

	private PublishPluginDefaultsPlugin() {
	}

	private void _configurePluginBundle(Project project) {
		final PluginBundleExtension pluginBundleExtension =
			GradleUtil.getExtension(project, PluginBundleExtension.class);

		NamedDomainObjectContainer<PluginConfig> pluginConfigs =
			pluginBundleExtension.getPlugins();

		File gradlePluginsDir = project.file(
			"src/main/resources/META-INF/gradle-plugins");

		File[] gradlePluginFiles = gradlePluginsDir.listFiles();

		for (File gradlePluginFile : gradlePluginFiles) {
			String fileName = gradlePluginFile.getName();

			Properties properties = GUtil.loadProperties(gradlePluginFile);

			String className = properties.getProperty("implementation-class");

			String name = StringUtil.uncapitalize(
				className.substring(className.lastIndexOf('.') + 1));

			PluginConfig pluginConfig = pluginConfigs.create(name);

			if (gradlePluginFiles.length == 1) {
				String displayName =
					GradlePluginsDefaultsUtil.getBundleInstruction(
						project, Constants.BUNDLE_NAME);

				pluginConfig.setDisplayName(displayName);
			}

			pluginConfig.setId(fileName.substring(0, fileName.length() - 11));
		}

		String url = _BASE_URL + project.getName();

		pluginBundleExtension.setVcsUrl(url);
		pluginBundleExtension.setWebsite(url);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					if (Validator.isNull(
							pluginBundleExtension.getDescription())) {

						String description =
							GradlePluginsDefaultsUtil.getBundleInstruction(
								project, Constants.BUNDLE_DESCRIPTION);

						pluginBundleExtension.setDescription(description);
					}

					Set<String> pluginBundleTags = new TreeSet<>(
						pluginBundleExtension.getTags());

					pluginBundleTags.addAll(_defaultPluginBundleTags);

					pluginBundleExtension.setTags(pluginBundleTags);

					for (PluginConfig pluginConfig :
							pluginBundleExtension.getPlugins()) {

						Set<String> pluginTags = new TreeSet<>(
							pluginConfig.getTags());

						pluginConfig.setTags(pluginTags);
					}
				}

			});
	}

	private void _configureTaskPublishPlugins(Project project) {
		Task task = GradleUtil.getTask(project, _PUBLISH_PLUGINS_TASK_NAME);

		task.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					if (GradlePluginsDefaultsUtil.isSnapshot(
							task.getProject())) {

						return false;
					}

					return true;
				}

			});
	}

	private void _configureTaskUploadArchives(Project project) {
		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		uploadArchivesTask.dependsOn(_PUBLISH_PLUGINS_TASK_NAME);
	}

	private static final String _BASE_URL =
		"https://github.com/liferay/liferay-portal/tree/master/modules/sdk/";

	private static final String _PUBLISH_PLUGINS_TASK_NAME = "publishPlugins";

	private static final Set<String> _defaultPluginBundleTags =
		Collections.singleton("liferay");

}