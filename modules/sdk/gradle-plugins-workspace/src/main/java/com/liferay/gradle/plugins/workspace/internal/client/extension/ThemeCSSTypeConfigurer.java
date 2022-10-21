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

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.theme.builder.BuildThemeTask;
import com.liferay.gradle.plugins.theme.builder.ThemeBuilderPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Gregory Amerson
 */
public class ThemeCSSTypeConfigurer implements ClientExtensionTypeConfigurer {

	@Override
	public void apply(
		Project project, Optional<ClientExtension> clientExtensionOptional,
		TaskProvider<Copy> assembleClientExtensionTaskProvider) {

		GradleUtil.applyPlugin(project, NodePlugin.class);
		GradleUtil.applyPlugin(project, ThemeBuilderPlugin.class);

		_addDependenciesParentThemes(project);
		_addDependenciesPortalCommonCSS(project);

		_configureTaskBuildTheme(project);

		BuildCSSTask buildCSSTask = _configureTaskBuildCSS(project);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		war.setEnabled(false);

		assembleClientExtensionTaskProvider.configure(
			new Action<Copy>() {

				@Override
				@SuppressWarnings("serial")
				public void execute(Copy copy) {
					copy.dependsOn(buildCSSTask);

					copy.into(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return "static";
							}

						},
						new Closure<Void>(copy) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.from(
									new File(
										project.getBuildDir(), "buildTheme"));
								copySpec.include("css/*.css");
								copySpec.into("static");
							}

						});
				}

			});
	}

	private void _addDependenciesParentThemes(Project project) {
		GradleUtil.addDependency(
			project, ThemeBuilderPlugin.PARENT_THEMES_CONFIGURATION_NAME,
			"com.liferay", "com.liferay.frontend.theme.styled",
			"latest.release");
		GradleUtil.addDependency(
			project, ThemeBuilderPlugin.PARENT_THEMES_CONFIGURATION_NAME,
			"com.liferay", "com.liferay.frontend.theme.unstyled",
			"latest.release");
		GradleUtil.addDependency(
			project, ThemeBuilderPlugin.PARENT_THEMES_CONFIGURATION_NAME,
			"com.liferay.plugins", "classic-theme", "latest.release");
	}

	private void _addDependenciesPortalCommonCSS(Project project) {
		GradleUtil.addDependency(
			project, CSSBuilderPlugin.PORTAL_COMMON_CSS_CONFIGURATION_NAME,
			"com.liferay", "com.liferay.frontend.css.common", "latest.release",
			false);
	}

	private BuildCSSTask _configureTaskBuildCSS(Project project) {
		BuildCSSTask buildCSSTask = (BuildCSSTask)GradleUtil.getTask(
			project, CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

		buildCSSTask.setOutputDirName(".");

		return buildCSSTask;
	}

	@SuppressWarnings("unchecked")
	private BuildThemeTask _configureTaskBuildTheme(Project project) {
		BuildThemeTask buildThemeTask = (BuildThemeTask)GradleUtil.getTask(
			project, ThemeBuilderPlugin.BUILD_THEME_TASK_NAME);

		buildThemeTask.setDiffsDir(project.file("src"));

		File packageJsonFile = project.file("package.json");

		if (!packageJsonFile.exists()) {
			return buildThemeTask;
		}

		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonFile);

		Map<String, String> liferayDesignPackMap =
			(Map<String, String>)packageJsonMap.get("liferayDesignPack");

		String baseTheme = liferayDesignPackMap.get("baseTheme");

		if (baseTheme.equals("styled") || baseTheme.equals("unstyled")) {
			baseTheme = "_" + baseTheme;
		}

		buildThemeTask.setParentName(baseTheme);

		Map<String, String> allDependencyMap = new HashMap<>();

		Map<String, String> dependenciesMap =
			(Map<String, String>)packageJsonMap.get("dependencies");

		Map<String, String> devDependenciesMap =
			(Map<String, String>)packageJsonMap.get("devDependencies");

		if (Objects.nonNull(dependenciesMap)) {
			allDependencyMap.putAll(dependenciesMap);
		}

		if (Objects.nonNull(devDependenciesMap)) {
			allDependencyMap.putAll(devDependenciesMap);
		}

		if (!allDependencyMap.isEmpty()) {
			buildThemeTask.dependsOn(NodePlugin.NPM_INSTALL_TASK_NAME);

			buildThemeTask.doFirst(
				new Action<Task>() {

					@Override
					public void execute(Task task) {
						WorkspaceExtension workspaceExtension =
							GradleUtil.getExtension(
								(ExtensionAware)project.getGradle(),
								WorkspaceExtension.class);

						String nodePackageManager =
							workspaceExtension.getNodePackageManager();

						File nodeMoudleDir;

						if (Objects.equals(nodePackageManager, "yarn")) {
							Project rootProject = project.getRootProject();

							nodeMoudleDir = rootProject.file("node_modules");
						}
						else {
							nodeMoudleDir = project.file("node_modules");
						}

						for (String key : allDependencyMap.keySet()) {
							final File dependencyDir = new File(
								nodeMoudleDir, key);

							if (!dependencyDir.exists()) {
								continue;
							}

							project.copy(
								new Action<CopySpec>() {

									@Override
									public void execute(CopySpec copySpec) {
										copySpec.from(dependencyDir);

										copySpec.into(
											buildThemeTask.getOutputDir() +
												"/css/" + key);
										copySpec.setIncludeEmptyDirs(false);
									}

								});
						}
					}

				});
		}

		return buildThemeTask;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

}