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
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.theme.builder.BuildThemeTask;
import com.liferay.gradle.plugins.theme.builder.ThemeBuilderPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.provider.Property;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.War;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Simon Jiang
 */
public class DesignPacksProjectConfigurator extends BaseProjectConfigurator {

	public static final String DESIGN_PACK_TASK_NAME = "zipDesignPack";

	public DesignPacksProjectConfigurator(Settings settings) {
		super(settings);

		String defaultRootDirNames = GradleUtil.getProperty(
			settings, getDefaultRootDirPropertyName(), (String)null);

		if (Validator.isNotNull(defaultRootDirNames)) {
			_defaultRootDirs = new HashSet<>();

			for (String dirName : defaultRootDirNames.split("\\s*,\\s*")) {
				_defaultRootDirs.add(new File(settings.getRootDir(), dirName));
			}
		}
		else {
			File dir = new File(settings.getRootDir(), getDefaultRootDirName());

			_defaultRootDirs = Collections.singleton(dir);
		}

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

		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, WarPlugin.class);

		_configureTaskProcessResources(project);

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		GradleUtil.applyPlugin(project, ThemeBuilderPlugin.class);

		_addDependenciesParentThemes(project);

		_addDependenciesPortalCommonCSS(project);

		_configureTaskBuildTheme(project);
		_configureWar(project);

		Zip zipDesignPackTask = _addTaskZipDesignPack(
			project, DESIGN_PACK_TASK_NAME, workspaceExtension);

		zipDesignPackTask.setDescription(
			"Assembles design pack project (zip).");

		zipDesignPackTask.setGroup(BasePlugin.BUILD_GROUP);

		_configureTaskDesignPack(project, zipDesignPackTask);

		_configureTaskWarForZipDesignPack(project, zipDesignPackTask);
	}

	@Override
	public Iterable<File> getDefaultRootDirs() {
		return _defaultRootDirs;
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
		GradleUtil.addDependency(
			project, CSSBuilderPlugin.PORTAL_COMMON_CSS_CONFIGURATION_NAME,
			"org.webjars", "font-awesome", "latest.release", false);
	}

	@SuppressWarnings("serial")
	private Zip _addTaskZipDesignPack(
		Project project, String taskName,
		final WorkspaceExtension workspaceExtension) {

		Zip task = GradleUtil.addTask(project, taskName, Zip.class);
		
		task.dependsOn(CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

		_configureTaskDisableUpToDate(task);

		task.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String bundleDistRootDirName =
						workspaceExtension.getBundleDistRootDirName();

					if (Validator.isNull(bundleDistRootDirName)) {
						bundleDistRootDirName = "";
					}

					return bundleDistRootDirName;
				}

			},
			new Closure<Void>(task) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.from(
						new File(
							project.getBuildDir(),
							"buildTheme/css/.sass-cache"));
					copySpec.setIncludeEmptyDirs(false);
					copySpec.include("**/clay.css");
					copySpec.include("**/clay_rtl.css");
					copySpec.include("**/main.css");
					copySpec.include("**/main_rtl.css");
				}

			});

		DirectoryProperty destinationDirectoryProperty =
			task.getDestinationDirectory();

		destinationDirectoryProperty.set(
			new File(project.getProjectDir(), "dist"));

		return task;
	}

	@SuppressWarnings("unchecked")
	private void _configureTaskBuildTheme(Project project) {
		File packageJsonFile = project.file("package.json");

		if (!packageJsonFile.exists()) {
			return;
		}

		BuildThemeTask buildThemeTask = (BuildThemeTask)GradleUtil.getTask(
			project, ThemeBuilderPlugin.BUILD_THEME_TASK_NAME);

		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonFile);

		Map<String, String> liferayDesignPackMap =
			(Map<String, String>)packageJsonMap.get("liferayDesignPack");

		String baseTheme = liferayDesignPackMap.get("baseTheme");

		if (baseTheme.equals("styled") || baseTheme.equals("unstyled")) {
			baseTheme = "_" + baseTheme;
		}

		buildThemeTask.setParentName(baseTheme);
		buildThemeTask.setTemplateExtension("ftl");
	}

	private void _configureTaskDesignPack(Project project, Zip zipTask) {
		Property<String> archiveBaseNameProperty = zipTask.getArchiveBaseName();

		archiveBaseNameProperty.set(
			project.provider(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						StringBuilder sb = new StringBuilder();

						sb.append(project.getName());

						return sb.toString();
					}

				}));

		Property<String> archiveVersion = zipTask.getArchiveVersion();

		archiveVersion.set(
			project.provider(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						return null;
					}

				}));

		zipTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					project.delete(
						new File(
							project.getBuildDir(),
							"libs/" + project.getName() + ".war"));
				}

			});
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

	private void _configureTaskProcessResources(Project project) {
		project.afterEvaluate(
			curProject -> {
				if (GradleUtil.hasTask(
						curProject, CSSBuilderPlugin.BUILD_CSS_TASK_NAME)) {

					Copy copy = (Copy)GradleUtil.getTask(
						project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

					if (copy != null) {
						copy.dependsOn(CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

						copy.exclude("**/*.css");
						copy.exclude("**/*.scss");

						copy.filesMatching(
							"**/.sass-cache/",
							fileCopyDetails -> {
								String path = fileCopyDetails.getPath();

								fileCopyDetails.setPath(
									path.replace(".sass-cache/", ""));
							});

						copy.setIncludeEmptyDirs(false);
					}
				}
			});
	}

	private void _configureTaskWarForZipDesignPack(
		Project project, Zip taskZipDesignPack) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			War.class,
			new Action<War>() {

				@Override
				public void execute(War war) {
					war.finalizedBy(taskZipDesignPack);
				}

			});
	}

	private void _configureWar(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("src");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
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
	private final Set<File> _defaultRootDirs;

}