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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.cache.CacheExtension;
import com.liferay.gradle.plugins.cache.CachePlugin;
import com.liferay.gradle.plugins.cache.task.TaskCache;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.tasks.ReplaceRegexTask;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.util.IncrementVersionClosure;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.util.Properties;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.DomainObjectCollection;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileTree;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.Upload;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemeDefaultsPlugin implements Plugin<Project> {

	public static final String EXPAND_FRONTEND_CSS_COMMON_TASK_NAME =
		"expandFrontendCSSCommon";

	public static final String FRONTEND_CSS_COMMON_CONFIGURATION_NAME =
		"frontendCSSCommon";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, LiferayThemePlugin.class);

		applyPlugins(project);

		CacheExtension cacheExtension = GradleUtil.getExtension(
			project, CacheExtension.class);

		// GRADLE-2427

		addTaskInstall(project);

		applyConfigScripts(project);

		LiferayOSGiDefaultsPlugin.configureRepositories(project);

		Configuration frontendCSSCommonConfiguration =
			addConfigurationFrontendCSSCommon(project);

		Copy expandFrontendCSSCommonTask = addTaskExpandFrontendCSSCommon(
			project, frontendCSSCommonConfiguration);
		final ReplaceRegexTask updateVersionTask = addTaskUpdateVersion(
			project);

		TaskCache gulpBuildTaskCache = configureCacheGulpBuild(
			project, cacheExtension);

		configureDeployDir(project);
		configureProject(project);

		Project frontendThemeStyledProject = getThemeProject(
			project, "frontend-theme-styled");
		Project frontendThemeUnstyledProject = getThemeProject(
			project, "frontend-theme-unstyled");

		configureTasksExecuteGulp(
			project, expandFrontendCSSCommonTask, frontendThemeStyledProject,
			frontendThemeUnstyledProject, gulpBuildTaskCache);

		GradleUtil.withPlugin(
			project, CachePlugin.class,
			new Action<CachePlugin>() {

				@Override
				public void execute(CachePlugin cachePlugin) {
					configureTaskUpdateVersionForCachePlugin(updateVersionTask);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					GradleUtil.setProjectSnapshotVersion(project);

					// setProjectSnapshotVersion must be called before
					// configureTaskUploadArchives, because the latter one needs
					// to know if we are publishing a snapshot or not.

					configureTaskUploadArchives(project, updateVersionTask);
				}

			});
	}

	protected Configuration addConfigurationFrontendCSSCommon(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, FRONTEND_CSS_COMMON_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					addDependenciesFrontendCSSCommon(project);
				}

			});

		configuration.setDescription(
			"Configures com.liferay.frontend.css.common for compiling the " +
				"theme.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	protected void addDependenciesFrontendCSSCommon(Project project) {
		String version = GradleUtil.getPortalToolVersion(
			project, CSSBuilderDefaultsPlugin.FRONTEND_COMMON_CSS_NAME);

		GradleUtil.addDependency(
			project, FRONTEND_CSS_COMMON_CONFIGURATION_NAME, "com.liferay",
			CSSBuilderDefaultsPlugin.FRONTEND_COMMON_CSS_NAME, version, false);
	}

	protected Copy addTaskExpandFrontendCSSCommon(
		final Project project,
		final Configuration frontendCSSCommonConfguration) {

		Copy copy = GradleUtil.addTask(
			project, EXPAND_FRONTEND_CSS_COMMON_TASK_NAME, Copy.class);

		copy.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					project.delete(copy.getDestinationDir());
				}

			});

		copy.eachFile(new StripPathSegmentsAction(2));

		copy.from(
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public FileTree doCall() {
					return project.zipTree(
						frontendCSSCommonConfguration.getSingleFile());
				}

			});

		copy.include("META-INF/resources/");
		copy.into(new File(project.getBuildDir(), "frontend-css-common"));
		copy.setDescription(
			"Expands com.liferay.frontend.css.common to a temporary " +
				"directory.");
		copy.setIncludeEmptyDirs(false);

		return copy;
	}

	protected Upload addTaskInstall(Project project) {
		Upload upload = GradleUtil.addTask(
			project, MavenPlugin.INSTALL_TASK_NAME, Upload.class);

		Configuration configuration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		upload.setConfiguration(configuration);
		upload.setDescription(
			"Installs the '" + configuration.getName() +
				"' artifacts into the local Maven repository.");

		return upload;
	}

	protected void addTaskSkippedDependency(
		Task task, TaskCache taskCache, Object taskDependency) {

		task.dependsOn(taskDependency);

		if (taskCache != null) {
			taskCache.skipTaskDependency(taskDependency);
		}
	}

	protected ReplaceRegexTask addTaskUpdateVersion(final Project project) {
		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, LiferayRelengPlugin.UPDATE_VERSION_TASK_NAME,
			ReplaceRegexTask.class);

		replaceRegexTask.match("\\n\\t\"version\": \"(.+)\"", "package.json");

		replaceRegexTask.setDescription(
			"Updates the project version in the package.json file.");

		replaceRegexTask.setReplacement(
			IncrementVersionClosure.MICRO_INCREMENT);

		return replaceRegexTask;
	}

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-maven.gradle",
			project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, CachePlugin.class);
		GradleUtil.applyPlugin(project, MavenPlugin.class);
	}

	protected TaskCache configureCacheGulpBuild(
		Project project, CacheExtension cacheExtension) {

		return cacheExtension.task(
			LiferayThemePlugin.GULP_BUILD_TASK_NAME,
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(TaskCache taskCache) {
					taskCache.setBaseDir("dist");
					taskCache.setCacheDir(".task-cache");
					taskCache.skipTaskDependency(
						NodePlugin.DOWNLOAD_NODE_TASK_NAME,
						NodePlugin.NPM_INSTALL_TASK_NAME);
					taskCache.testFile("gulpfile.js", "package.json", "src");
				}

			});
	}

	protected void configureDeployDir(Project project) {
		final LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		boolean requiredForStartup = getPluginPackageProperty(
			project, "required-for-startup");

		if (requiredForStartup) {
			liferayExtension.setDeployDir(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return new File(
							liferayExtension.getLiferayHome(), "osgi/war");
					}

				});
		}
		else {
			liferayExtension.setDeployDir(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return new File(
							liferayExtension.getLiferayHome(), "deploy");
					}

				});
		}
	}

	protected void configureProject(Project project) {
		project.setGroup(_GROUP);
	}

	protected void configureTaskExecuteGulp(
		ExecuteGulpTask executeGulpTask, final Copy expandFrontendCSSCommonTask,
		Project frontendThemeStyledProject,
		Project frontendThemeUnstyledProject, TaskCache taskCache) {

		executeGulpTask.args(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					File dir = expandFrontendCSSCommonTask.getDestinationDir();

					return "--css-common-path=" + FileUtil.getAbsolutePath(dir);
				}

			});

		addTaskSkippedDependency(
			executeGulpTask, taskCache, expandFrontendCSSCommonTask);

		configureTaskExecuteGulpParentTheme(
			executeGulpTask, frontendThemeStyledProject, "styled", taskCache);
		configureTaskExecuteGulpParentTheme(
			executeGulpTask, frontendThemeUnstyledProject, "unstyled",
			taskCache);
	}

	protected void configureTaskExecuteGulpParentTheme(
		ExecuteGulpTask executeGulpTask, Project themeProject, String name,
		TaskCache taskCache) {

		if (themeProject == null) {
			if (_logger.isWarnEnabled()) {
				_logger.warn("Unable to configure " + name + " parent theme");
			}

			return;
		}

		File dir = themeProject.file(
			"src/main/resources/META-INF/resources/_" + name);

		executeGulpTask.args(
			"--" + name + "-path=" + FileUtil.getAbsolutePath(dir));

		addTaskSkippedDependency(
			executeGulpTask, taskCache,
			themeProject.getPath() + ":" + JavaPlugin.CLASSES_TASK_NAME);

		if (taskCache != null) {
			taskCache.testFile(themeProject.getBuildFile(), dir);
		}
	}

	protected void configureTasksExecuteGulp(
		Project project, final Copy expandFrontendCSSCommonTask,
		final Project frontendThemeStyledProject,
		final Project frontendThemeUnstyledProject,
		final TaskCache... taskCaches) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteGulpTask.class,
			new Action<ExecuteGulpTask>() {

				@Override
				public void execute(ExecuteGulpTask executeGulpTask) {
					TaskCache executeGulpTaskCache = null;

					for (TaskCache taskCache : taskCaches) {
						if (executeGulpTask.equals(taskCache.getTask())) {
							executeGulpTaskCache = taskCache;

							break;
						}
					}

					configureTaskExecuteGulp(
						executeGulpTask, expandFrontendCSSCommonTask,
						frontendThemeStyledProject,
						frontendThemeUnstyledProject, executeGulpTaskCache);
				}

			});
	}

	protected void configureTaskUpdateVersionForCachePlugin(
		final ReplaceRegexTask updateVersionTask) {

		CacheExtension cacheExtension = GradleUtil.getExtension(
			updateVersionTask.getProject(), CacheExtension.class);

		DomainObjectCollection<TaskCache> taskCaches =
			cacheExtension.getTasks();

		taskCaches.all(
			new Action<TaskCache>() {

				@Override
				public void execute(TaskCache taskCache) {
					updateVersionTask.finalizedBy(
						taskCache.getRefreshDigestTaskName());
				}

			});
	}

	protected void configureTaskUploadArchives(
		Project project, Task updateThemeVersionTask) {

		if (GradleUtil.isSnapshot(project)) {
			return;
		}

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		uploadArchivesTask.finalizedBy(updateThemeVersionTask);
	}

	protected boolean getPluginPackageProperty(Project project, String key) {
		try {
			Properties properties = FileUtil.readProperties(
				project, "src/WEB-INF/liferay-plugin-package.properties");

			return Boolean.parseBoolean(properties.getProperty(key));
		}
		catch (IOException ioe) {
			throw new GradleException(
				"Unable to read liferay-plugin-package.properties", ioe);
		}
	}

	protected Project getThemeProject(Project project, String name) {
		Project parentProject = project.getParent();

		Project themeProject = parentProject.findProject(name);

		if (themeProject == null) {
			themeProject = GradleUtil.getProject(
				project.getRootProject(), name);
		}

		return themeProject;
	}

	private static final String _GROUP = "com.liferay.plugins";

	private static final Logger _logger = Logging.getLogger(
		LiferayThemeDefaultsPlugin.class);

}