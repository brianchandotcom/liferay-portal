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

package com.liferay.sdk.gradle.plugins;

import com.liferay.sdk.gradle.plugins.internal.extensions.LiferayExtension;
import com.liferay.sdk.gradle.plugins.internal.util.StringUtil;
import com.liferay.sdk.gradle.plugins.internal.util.Validator;

import java.io.File;

import java.util.Collections;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayPlugin extends BasePlugin {

	protected void configureClean() {
		project.afterEvaluate(new Action<Project>() {

			@Override
			public void execute(Project project) {
				configureCleanAfterEvaluate();
			}

		});
	}

	protected void configureCleanAfterEvaluate() {
		Task cleanTask = getTask(CLEAN_TASK_NAME);

		for (Task task : project.getTasks()) {
			String taskName =
				CLEAN_TASK_NAME + StringUtil.capitalize(task.getName());

			cleanTask.dependsOn(taskName);
		}

		Configuration compileConfiguration = getConfiguration(
			JavaPlugin.COMPILE_CONFIGURATION_NAME);

		Set<Dependency> compileDependencies =
			compileConfiguration.getAllDependencies();

		for (Dependency dependency : compileDependencies) {
			if (dependency instanceof ProjectDependency) {
				ProjectDependency projectDependency =
					(ProjectDependency)dependency;

				Project dependencyProject =
					projectDependency.getDependencyProject();

				String taskName =
					dependencyProject.getPath() + Project.PATH_SEPARATOR +
						CLEAN_TASK_NAME;

				cleanTask.dependsOn(taskName);
			}
		}
	}

	protected void configureSourceSets() {
		Set<File> srcDirs = Collections.singleton(_extension.getPluginSrcDir());

		SourceSet sourceSet = getSourceSet(SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaDirectorySet = sourceSet.getJava();

		javaDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesDirectorySet = sourceSet.getResources();

		resourcesDirectorySet.setSrcDirs(srcDirs);
	}

	protected void configureVersion() {
		String version = null;

		String moduleFullVersion = _extension.getPluginPackageProperty(
			"module-full-version");

		if (Validator.isNotNull(moduleFullVersion)) {
			version = moduleFullVersion;
		}
		else {
			String bundleVersion = _extension.getBndProperty("Bundle-Version");

			if (Validator.isNotNull(bundleVersion)) {
				version = bundleVersion;
			}
			else {
				String moduleIncrementalVersion =
					_extension.getPluginPackageProperty(
						"module-incremental-version");

				version = PORTAL_VERSION + "." + moduleIncrementalVersion;
			}
		}

		project.setVersion(version);
	}

	protected void configureWebAppDirName() {
		WarPluginConvention warPluginConvention = getPluginConvention(
			WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("docroot");
	}

	@Override
	protected void doApply() throws Exception {
		applyPlugin(WarPlugin.class);

		_extension = createExtension(_EXTENSION_NAME, LiferayExtension.class);

		configureSourceSets();
		configureVersion();
		configureWebAppDirName();

		configureClean();
	}

	private static final String _EXTENSION_NAME = "liferay";

	private LiferayExtension _extension;

}