/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.util.BndUtil;
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
import org.gradle.api.publish.plugins.PublishingPlugin;
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
	protected void applyPluginDefaults(
		Project project, PublishPlugin publishPlugin) {

		BundleExtension bundleExtension = BndUtil.getBundleExtension(
			project.getExtensions());

		_configurePluginBundle(project, bundleExtension);

		_configureTaskPublishPlugins(project);
		_configureTaskPublish(project);
	}

	@Override
	protected Class<PublishPlugin> getPluginClass() {
		return PublishPlugin.class;
	}

	private PublishPluginDefaultsPlugin() {
	}

	private void _configurePluginBundle(
		Project project, final BundleExtension bundleExtension) {

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
				pluginConfig.setDisplayName(
					bundleExtension.getInstruction(Constants.BUNDLE_NAME));
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

						pluginBundleExtension.setDescription(
							bundleExtension.getInstruction(
								Constants.BUNDLE_DESCRIPTION));
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

	private void _configureTaskPublish(Project project) {
		Task publishTask = GradleUtil.getTask(
			project, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

		publishTask.dependsOn(_PUBLISH_PLUGINS_TASK_NAME);
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

	private static final String _BASE_URL =
		"https://github.com/liferay/liferay-portal/tree/master/modules/sdk/";

	private static final String _PUBLISH_PLUGINS_TASK_NAME = "publishPlugins";

	private static final Set<String> _defaultPluginBundleTags =
		Collections.singleton("liferay");

}