/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.poshi.runner;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.HashSet;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.file.CopySpec;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerResourcesPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "poshiRunnerResources";

	public static final String POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME =
		"poshiRunnerResources";

	@Override
	public void apply(Project project) {
		final PoshiRunnerResourcesExtension poshiRunnerResourcesExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, PoshiRunnerResourcesExtension.class);

		_addConfigurationPoshiRunnerResources(
			project, poshiRunnerResourcesExtension);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_addArtifactsPoshiRunnerResources(
						project, poshiRunnerResourcesExtension);
				}

			});
	}

	private Jar _addArtifactPoshiRunnerResources(
		Project project, final Set<File> dirs, String baseName, String appendix,
		String rootDirName, String version) {

		Jar jar = GradleUtil.addTask(
			project,
			"jarPoshiRunnerResources" + StringUtil.capitalize(baseName),
			Jar.class);

		if (Validator.isNotNull(rootDirName)) {
			jar.into(
				rootDirName,
				new Closure<Void>(jar) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						copySpec.from(dirs);
					}

				});
		}
		else {
			jar.from(dirs);
		}

		jar.setAppendix(appendix);
		jar.setBaseName(baseName);
		jar.setDescription(
			"Assembles a jar archive containing the Poshi Runner resources.");
		jar.setVersion(version);

		ArtifactHandler artifactHandler = project.getArtifacts();

		artifactHandler.add(POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME, jar);

		return jar;
	}

	private void _addArtifactsPoshiRunnerResources(
		Project project,
		PoshiRunnerResourcesExtension poshiRunnerResourcesExtension) {

		String appendix = poshiRunnerResourcesExtension.getArtifactAppendix();
		String baseName = poshiRunnerResourcesExtension.getBaseName();
		String rootDirName = poshiRunnerResourcesExtension.getRootDirName();
		String version = poshiRunnerResourcesExtension.getArtifactVersion();

		Set<File> dirs = new HashSet<>();

		for (Object dir : poshiRunnerResourcesExtension.getDirs()) {
			dirs.add(GradleUtil.toFile(project, dir));
		}

		_addArtifactPoshiRunnerResources(
			project, dirs, baseName, appendix, rootDirName, version);
	}

	private Configuration _addConfigurationPoshiRunnerResources(
		final Project project,
		final PoshiRunnerResourcesExtension poshiRunnerResourcesExtension) {

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration configuration = configurationContainer.maybeCreate(
			POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesPoshiRunnerResources(
						project, poshiRunnerResourcesExtension);
				}

			});

		configuration.setDescription(
			"Configures the Poshi Runner resources artifacts.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesPoshiRunnerResources(
		Project project,
		PoshiRunnerResourcesExtension poshiRunnerResourcesExtension) {

		GradleUtil.addDependency(
			project, POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.poshi.runner.resources",
			poshiRunnerResourcesExtension.getVersion());
	}

}