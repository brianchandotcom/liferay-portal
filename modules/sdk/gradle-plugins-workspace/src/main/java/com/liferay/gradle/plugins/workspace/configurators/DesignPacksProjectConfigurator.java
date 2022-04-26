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

package com.liferay.gradle.plugins.workspace.configurators;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.theme.builder.BuildThemeTask;
import com.liferay.gradle.plugins.theme.builder.ThemeBuilderPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.War;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Simon Jiang
 */
public class DesignPacksProjectConfigurator extends BaseProjectConfigurator {

	public static final String BUILD_DESIGN_PACK_TASK_NAME = "buildDesignPack";

	public DesignPacksProjectConfigurator(Settings settings) {
		super(settings);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, NodePlugin.class);
		GradleUtil.applyPlugin(project, ThemeBuilderPlugin.class);

		configureLiferay(project, workspaceExtension);

		_addDependenciesParentThemes(project);

		_addDependenciesPortalCommonCSS(project);

		BuildThemeTask buildThemeTask = _configureTaskBuildTheme(project);

		BuildCSSTask buildCSSTask = _configureTaskBuildCSS(project);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		war.setEnabled(false);

		Zip zip = _addTaskBuildDesignPack(
			project, buildCSSTask, buildThemeTask);

		_configureRootTaskDistBundle(project, zip);

		_configureTaskClean(project);
		_configureTaskDeploy(project);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (isExcludedDirName(dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path packageJsonPath = dirPath.resolve("package.json");

					if (Files.exists(packageJsonPath) &&
						_isLiferayDesignPack(packageJsonPath)) {

						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	@Override
	protected String getDefaultRootDirName() {
		return "design-packs";
	}

	protected static final String NAME = "design.pack";

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

	@SuppressWarnings("serial")
	private Zip _addTaskBuildDesignPack(
		Project project, BuildCSSTask buildCSSTask,
		BuildThemeTask buildThemeTask) {

		Zip zip = GradleUtil.addTask(
			project, BUILD_DESIGN_PACK_TASK_NAME, Zip.class);

		_configureTaskDisableUpToDate(zip);

		zip.dependsOn(buildCSSTask);

		zip.setDescription("Assembles design pack.");
		zip.setGroup(BasePlugin.BUILD_GROUP);

		Property<String> archiveBaseNameProperty = zip.getArchiveBaseName();

		archiveBaseNameProperty.set(
			project.provider(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						return project.getName();
					}

				}));

		zip.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "";
				}

			},
			new Closure<Void>(zip) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.from(
						new File(buildThemeTask.getOutputDir(), "/css/"));
					copySpec.setIncludeEmptyDirs(false);
					copySpec.include("*.css");
				}

			});

		DirectoryProperty destinationDirectoryProperty =
			zip.getDestinationDirectory();

		destinationDirectoryProperty.set(
			new File(project.getProjectDir(), "dist"));

		buildCSSTask.finalizedBy(zip);

		return zip;
	}

	@SuppressWarnings({"serial", "unused"})
	private void _configureRootTaskDistBundle(Project project, Zip zip) {
		Task assembleTask = GradleUtil.getTask(
			project, BasePlugin.ASSEMBLE_TASK_NAME);

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.dependsOn(assembleTask);

		copy.into(
			"deploy",
			new Closure<Void>(project) {

				public void doCall(CopySpec copySpec) {
					Project project = assembleTask.getProject();

					Provider<RegularFile> fileProvider = zip.getArchiveFile();

					ConfigurableFileCollection configurableFileCollection =
						project.files(fileProvider);

					configurableFileCollection.builtBy(assembleTask);

					copySpec.from(fileProvider);
				}

			});
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

	private void _configureTaskClean(Project project) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete("build", "dist");
	}

	private void _configureTaskDeploy(Project project) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.dependsOn("build");
		copy.from(_getZipFile(project));
	}

	private void _configureTaskDisableUpToDate(Task task) {
		TaskOutputs taskOutputs = task.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

	private File _getZipFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + ".zip");
	}

	@SuppressWarnings("unchecked")
	private boolean _isLiferayDesignPack(Path packageJsonPath) {
		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonPath.toFile());

		Map<String, Object> liferayTheme =
			(Map<String, Object>)packageJsonMap.get("liferayDesignPack");

		if (liferayTheme != null) {
			return true;
		}

		return false;
	}

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private final boolean _defaultRepositoryEnabled;

}