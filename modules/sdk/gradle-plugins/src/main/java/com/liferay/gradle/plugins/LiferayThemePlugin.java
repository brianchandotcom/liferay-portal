/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.lang.builder.BuildLangTask;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.task.PackageRunBuildTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.ConfigurablePublishArtifact;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginExtension;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemePlugin implements Plugin<Project> {

	public static final String CREATE_LIFERAY_THEME_JSON_TASK_NAME =
		"createLiferayThemeJson";

	@Override
	public void apply(Project project) {

		// Plugins

		GradleUtil.applyPlugin(project, BasePlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderPlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, NodePlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		BasePluginExtension basePluginExtension = extensionContainer.getByType(
			BasePluginExtension.class);
		LiferayExtension liferayExtension = extensionContainer.getByType(
			LiferayExtension.class);

		Map<String, Object> packageJsonMap = _getPackageJsonMap(project);

		_configureExtensionBasePlugin(basePluginExtension, packageJsonMap);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration archivesConfiguration =
			configurationContainer.maybeCreate(
				Dependency.ARCHIVES_CONFIGURATION);
		Configuration defaultConfiguration = configurationContainer.getByName(
			Dependency.DEFAULT_CONFIGURATION);

		_configureConfigurationDefault(
			archivesConfiguration, defaultConfiguration);

		// Tasks

		TaskProvider<Delete> cleanTaskProvider = GradleUtil.getTaskProvider(
			project, BasePlugin.CLEAN_TASK_NAME, Delete.class);
		final TaskProvider<Task> createLiferayThemeJsonTaskProvider =
			GradleUtil.addTaskProvider(
				project, CREATE_LIFERAY_THEME_JSON_TASK_NAME, Task.class);
		TaskProvider<BuildLangTask> buildLangTaskProvider =
			GradleUtil.getTaskProvider(
				project, LangBuilderPlugin.BUILD_LANG_TASK_NAME,
				BuildLangTask.class);
		TaskProvider<Copy> deployTaskProvider = GradleUtil.getTaskProvider(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME, Copy.class);

		_configureTaskBuildLangProvider(buildLangTaskProvider);
		_configureTaskCleanProvider(cleanTaskProvider);
		_configureTaskCreateLiferayThemeJsonProvider(
			project, liferayExtension, createLiferayThemeJsonTaskProvider);
		_configureTaskDeployProvider(project, deployTaskProvider);

		// Containers

		final TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PackageRunBuildTask.class,
			new Action<PackageRunBuildTask>() {

				@Override
				public void execute(PackageRunBuildTask packageRunBuildTask) {
					_configureTaskPackageRunBuild(
						createLiferayThemeJsonTaskProvider,
						packageRunBuildTask);
				}

			});

		// Other

		_configureProject(project, packageJsonMap);

		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(
			Dependency.ARCHIVES_CONFIGURATION, _getWarFile(project),
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(
					ConfigurablePublishArtifact configurablePublishArtifact) {

					Task packageRunBuildTask = taskContainer.findByName(
						NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);

					if (packageRunBuildTask != null) {
						configurablePublishArtifact.builtBy(
							packageRunBuildTask);
					}
				}

			});
	}

	private void _configureConfigurationDefault(
		Configuration archivesConfiguration,
		Configuration defaultConfiguration) {

		defaultConfiguration.extendsFrom(archivesConfiguration);
	}

	private void _configureExtensionBasePlugin(
		BasePluginExtension basePluginExtension,
		Map<String, Object> packageJsonMap) {

		String name = null;

		Map<String, Object> liferayThemeMap =
			(Map<String, Object>)packageJsonMap.get("liferayTheme");

		if (liferayThemeMap != null) {
			name = (String)liferayThemeMap.get("distName");
		}

		if (Validator.isNull(name)) {
			name = (String)packageJsonMap.get("name");
		}

		if (Validator.isNull(name)) {
			return;
		}

		Property<String> archivesNameProperty =
			basePluginExtension.getArchivesName();

		archivesNameProperty.set(name);
	}

	private void _configureProject(
		Project project, Map<String, Object> packageJsonMap) {

		// liferay-theme-tasks already uses the "build" directory

		project.setBuildDir("build_gradle");

		String version = (String)packageJsonMap.get("version");

		if (Validator.isNotNull(version)) {
			project.setVersion(version);
		}
	}

	private void _configureTaskBuildLangProvider(
		TaskProvider<BuildLangTask> buildLangTaskProvider) {

		buildLangTaskProvider.configure(
			new Action<BuildLangTask>() {

				@Override
				public void execute(BuildLangTask buildLangTask) {
					buildLangTask.setLangDir("src/WEB-INF/src/content");
				}

			});
	}

	private void _configureTaskCleanProvider(
		TaskProvider<Delete> cleanTaskProvider) {

		cleanTaskProvider.configure(
			new Action<Delete>() {

				@Override
				public void execute(Delete cleanDelete) {
					cleanDelete.delete("build", "dist");
				}

			});
	}

	private void _configureTaskCreateLiferayThemeJsonProvider(
		final Project project, final LiferayExtension liferayExtension,
		TaskProvider<Task> createLiferayThemeJsonTaskProvider) {

		createLiferayThemeJsonTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task createLiferayThemeJsonTask) {
					final File liferayThemeJsonFile = project.file(
						"liferay-theme.json");

					createLiferayThemeJsonTask.doLast(
						new Action<Task>() {

							@Override
							public void execute(Task task) {
								Project project = task.getProject();

								Map<String, Object> map = new HashMap<>();

								map.put(
									"appServerPath",
									FileUtil.getAbsolutePath(
										liferayExtension.getAppServerDir()));

								File appServerThemeDir = new File(
									liferayExtension.getAppServerDeployDir(),
									project.getName());

								map.put(
									"appServerPathTheme",
									FileUtil.getAbsolutePath(
										appServerThemeDir));

								map.put("deployed", false);

								map.put(
									"deployPath",
									FileUtil.getAbsolutePath(
										liferayExtension.getDeployDir()));
								map.put("themeName", project.getName());

								String json = JsonOutput.toJson(
									Collections.singletonMap(
										"LiferayTheme", map));

								try {
									Files.write(
										liferayThemeJsonFile.toPath(),
										json.getBytes(StandardCharsets.UTF_8));
								}
								catch (IOException ioException) {
									throw new UncheckedIOException(ioException);
								}
							}

						});

					createLiferayThemeJsonTask.setDescription(
						"Generates the " + liferayThemeJsonFile.getName() +
							" file for this project.");
				}

			});
	}

	private void _configureTaskDeployProvider(
		final Project project, TaskProvider<Copy> deployTaskProvider) {

		deployTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy deployCopy) {
					deployCopy.dependsOn(BasePlugin.ASSEMBLE_TASK_NAME);
					deployCopy.from(_getWarFile(project));
				}

			});
	}

	private void _configureTaskPackageRunBuild(
		TaskProvider<Task> createLiferayThemeJsonTaskProvider,
		PackageRunBuildTask packageRunBuildTask) {

		packageRunBuildTask.dependsOn(createLiferayThemeJsonTaskProvider);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(Project project) {
		File file = project.file("package.json");

		if (!file.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(file);
	}

	private File _getWarFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + ".war");
	}

}