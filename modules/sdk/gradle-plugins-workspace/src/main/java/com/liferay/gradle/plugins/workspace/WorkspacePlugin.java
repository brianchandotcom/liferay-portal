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

package com.liferay.gradle.plugins.workspace;

import com.liferay.gradle.plugins.LiferayDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayJavaPlugin;
import com.liferay.gradle.plugins.LiferayPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.gulp.GulpPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.workspace.tasks.UpdatePropertiesTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.json.JsonOutput;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.AntBuilder;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySourceSpec;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.AbstractCopyTask;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Compression;
import org.gradle.api.tasks.bundling.Tar;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class WorkspacePlugin implements Plugin<Project> {

	public static final String BUNDLE_CONFIGURATION_NAME = "bundle";

	public static final String CREATE_LIFERAY_THEME_JSON_TASK_NAME =
		"createLiferayThemeJson";

	public static final String DIST_BUNDLE_TAR_TASK_NAME = "distBundleTar";

	public static final String DIST_BUNDLE_ZIP_TASK_NAME = "distBundleZip";

	public static final String INIT_BUNDLE_TASK_NAME = "initBundle";

	public static final String PLUGIN_NAME = "workspace";

	public static final String UPDATE_SDK_PROPERTIES_TASK_NAME =
		"updateSDKProperties";

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.addExtension(
			project, PLUGIN_NAME, WorkspaceExtension.class);

		Configuration bundleConfiguration = addConfigurationBundle(
			project, workspaceExtension);

		addRepositoryBundle(project, workspaceExtension);

		Tar distBundleTarTask = addTaskDistBundle(
			project, DIST_BUNDLE_TAR_TASK_NAME, Tar.class, bundleConfiguration,
			workspaceExtension);

		distBundleTarTask.setCompression(Compression.GZIP);
		distBundleTarTask.setExtension("tar.gz");

		Zip distBundleZipTask = addTaskDistBundle(
			project, DIST_BUNDLE_ZIP_TASK_NAME, Zip.class, bundleConfiguration,
			workspaceExtension);

		AbstractArchiveTask[] distBundleTasks = {
			distBundleTarTask, distBundleZipTask
		};

		Copy initBundleTask = addTaskInitBundle(
			project, workspaceExtension, bundleConfiguration);

		configureModules(project, workspaceExtension, distBundleTasks);
		configurePluginsSDK(
			project, workspaceExtension, initBundleTask, distBundleTasks);
		configureThemes(project, workspaceExtension, distBundleTasks);
	}

	protected Configuration addConfigurationBundle(
		Project project, WorkspaceExtension workspaceExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, BUNDLE_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the Liferay bundle to use for your project.");

		GradleUtil.addDependency(
			project, BUNDLE_CONFIGURATION_NAME,
			workspaceExtension.getBundleArtifactGroup(),
			workspaceExtension.getBundleArtifactName(),
			workspaceExtension.getBundleArtifactVersion());

		return configuration;
	}

	protected MavenArtifactRepository addRepository(
		Project project, final String url) {

		RepositoryHandler repositoryHandler = project.getRepositories();

		return repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(url);
				}

			});
	}

	protected MavenArtifactRepository addRepositoryBundle(
		Project project, WorkspaceExtension workspaceExtension) {

		return addRepository(project, workspaceExtension.getBundleMavenUrl());
	}

	protected MavenArtifactRepository addRepositoryModules(Project project) {
		return addRepository(
			project, LiferayDefaultsPlugin.DEFAULT_REPOSITORY_URL);
	}

	protected Task addTaskCreateLiferayThemeJson(
		Project project, final WorkspaceExtension workspaceExtension) {

		Task task = project.task(CREATE_LIFERAY_THEME_JSON_TASK_NAME);

		final File liferayThemeJsonFile = project.file("liferay-theme.json");

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Map<String, Object> map = new HashMap<>();

					File appServerDir = new File(
						workspaceExtension.getHomeDir(), "tomcat-7.0.62");

					map.put("appServerPath", appServerDir.getAbsolutePath());

					File appServerThemeDir = new File(
						appServerDir, "webapps/" + project.getName());

					map.put(
						"appServerPathTheme",
						appServerThemeDir.getAbsolutePath());

					map.put("deployed", false);

					File deployDir = new File(
						workspaceExtension.getHomeDir(), "deploy");

					map.put("deployPath", deployDir.getAbsolutePath());
					map.put("themeName", project.getName());

					String json = JsonOutput.toJson(
						Collections.singletonMap("LiferayTheme", map));

					try {
						Files.write(
							liferayThemeJsonFile.toPath(),
							json.getBytes(StandardCharsets.UTF_8));
					}
					catch (IOException ioe) {
						throw new GradleException(ioe.getMessage(), ioe);
					}
				}

			});

		task.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					if (liferayThemeJsonFile.exists()) {
						return true;
					}

					return false;
				}

			});

		return task;
	}

	protected <T extends AbstractArchiveTask> T addTaskDistBundle(
		final Project project, String taskName, Class<T> clazz,
		final Configuration bundleConfiguration,
		WorkspaceExtension workspaceExtension) {

		T task = GradleUtil.addTask(project, taskName, clazz);

		configureTaskCopyBundle(task, bundleConfiguration);

		task.from(
			project.file("configs/common"),
			project.file("configs/" + workspaceExtension.getEnvironment()));

		task.setBaseName(project.getName());
		task.setDescription("Assembles the bundle and zips it up.");
		task.setDestinationDir(project.getBuildDir());
		task.setIncludeEmptyDirs(false);

		return task;
	}

	protected Copy addTaskInitBundle(
		final Project project, final WorkspaceExtension workspaceExtension,
		final Configuration bundleConfiguration) {

		Copy copy = GradleUtil.addTask(
			project, INIT_BUNDLE_TASK_NAME, Copy.class);

		copy.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					project.delete(copy.getDestinationDir());
				}

			});

		configureTaskCopyBundle(copy, bundleConfiguration);

		Project rootProject = project.getRootProject();

		copy.from(
			rootProject.file("configs/common"),
			rootProject.file("configs/" + workspaceExtension.getEnvironment()));

		copy.into(workspaceExtension.getHomeDir());

		copy.setDescription(
			"Downloads and unzips the bundle into " +
			workspaceExtension.getHomeDir() + ".");
		copy.setIncludeEmptyDirs(false);

		return copy;
	}

	protected UpdatePropertiesTask addTaskUpdateSDKProperties(
		Project project, WorkspaceExtension workspaceExtension) {

		UpdatePropertiesTask updatePropertiesTask = GradleUtil.addTask(
			project, UPDATE_SDK_PROPERTIES_TASK_NAME,
			UpdatePropertiesTask.class);

		updatePropertiesTask.property(
			"app.server.parent.dir",
			FileUtil.getAbsolutePath(workspaceExtension.getHomeDir()));

		String userName = System.getProperty("user.name");

		File sdkPropertiesFile = new File(
			workspaceExtension.getPluginsSDKDir(),
			"build." + userName + ".properties");

		updatePropertiesTask.setPropertiesFile(sdkPropertiesFile);

		return updatePropertiesTask;
	}

	protected void configureModules(
		Project project, final WorkspaceExtension workspaceExtension,
		final AbstractArchiveTask[] distBundleTasks) {

		Project modulesProject = GradleUtil.getProject(
			project, workspaceExtension.getModulesDir());

		if (modulesProject == null) {
			return;
		}

		Action<Project> action = new Action<Project>() {

			@Override
			public void execute(Project project) {
				Set<Project> subprojects = project.getSubprojects();

				if (!subprojects.isEmpty()) {
					return;
				}

				GradleUtil.applyPlugin(project, LiferayPlugin.class);

				LiferayExtension liferayExtension = GradleUtil.getExtension(
					project, LiferayExtension.class);

				liferayExtension.setAppServerParentDir(
					workspaceExtension.getHomeDir());

				for (AbstractArchiveTask abstractArchiveTask :
						distBundleTasks) {

					abstractArchiveTask.into(
						"deploy",
						new Closure<Void>(null) {

							@SuppressWarnings("unused")
							public void doCall(CopySourceSpec copySourceSpec) {
								copySourceSpec.from(JavaPlugin.JAR_TASK_NAME);
							}

						});
				}

				if (workspaceExtension.isModulesDefaultRepositoryEnabled()) {
					addRepositoryModules(project);
				}
			}

		};

		modulesProject.subprojects(action);
	}

	protected void configurePluginsSDK(
		Project project, final WorkspaceExtension workspaceExtension,
		Copy initBundleTask, AbstractArchiveTask[] distBundleTasks) {

		final Project pluginsSDKProject = GradleUtil.getProject(
			project, workspaceExtension.getPluginsSDKDir());

		if (pluginsSDKProject == null) {
			return;
		}

		AntBuilder antBuilder = pluginsSDKProject.getAnt();

		antBuilder.importBuild("build.xml");

		final Task warTask = GradleUtil.getTask(
			pluginsSDKProject, WarPlugin.WAR_TASK_NAME);

		File homeDir = workspaceExtension.getHomeDir();

		if (!homeDir.exists()) {
			warTask.dependsOn(initBundleTask);
		}

		Task updateSDKPropertiesTask = addTaskUpdateSDKProperties(
			pluginsSDKProject, workspaceExtension);

		Task buildTask = pluginsSDKProject.task(
			LifecycleBasePlugin.BUILD_TASK_NAME);

		buildTask.dependsOn(updateSDKPropertiesTask, warTask);

		for (AbstractArchiveTask abstractArchiveTask : distBundleTasks) {
			abstractArchiveTask.into(
				"deploy",
				new Closure<Void>(null) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						ConfigurableFileTree configurableFileTree =
							pluginsSDKProject.fileTree("dist");

						configurableFileTree.builtBy(warTask);
						configurableFileTree.include("*.war");

						copySpec.from(configurableFileTree);
					}

				});
		}
	}

	protected void configureTaskCopyBundle(
		final AbstractCopyTask abstractCopyTask,
		final Configuration bundleConfiguration) {

		abstractCopyTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					File file = bundleConfiguration.getSingleFile();

					GradleUtil.setProperty(
						task, _BUNDLE_FILE_PROPERTY_NAME, file);
				}

			});

		abstractCopyTask.from(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					Project project = abstractCopyTask.getProject();

					if (!abstractCopyTask.hasProperty(
							_BUNDLE_FILE_PROPERTY_NAME)) {

						return project.files();
					}

					File file = (File)abstractCopyTask.property(
						_BUNDLE_FILE_PROPERTY_NAME);

					String fileName = file.getName();

					if (fileName.endsWith(".tar.gz")) {
						return project.tarTree(file);
					}
					else {
						return project.zipTree(file);
					}
				}

			},
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});
	}

	protected void configureThemes(
		Project project, final WorkspaceExtension workspaceExtension,
		final AbstractArchiveTask[] distBundleTasks) {

		Project themesProject = GradleUtil.getProject(
			project, workspaceExtension.getThemesDir());

		if (themesProject == null) {
			return;
		}

		Action<Project> action = new Action<Project>() {

			@Override
			public void execute(final Project project) {
				Set<Project> subproject = project.getSubprojects();

				if (!subproject.isEmpty()) {
					return;
				}

				project.setBuildDir("build_gradle");

				GradleUtil.applyPlugin(project, BasePlugin.class);
				GradleUtil.applyPlugin(project, GulpPlugin.class);

				Task assembleTask = GradleUtil.getTask(
					project, BasePlugin.ASSEMBLE_TASK_NAME);

				assembleTask.dependsOn(_GULP_BUILD_TASK_NAME);

				final Task createLiferayThemeJsonTask =
					addTaskCreateLiferayThemeJson(project, workspaceExtension);

				Delete cleanTask = (Delete)GradleUtil.getTask(
					project, BasePlugin.CLEAN_TASK_NAME);

				cleanTask.delete("build", "dist");
				cleanTask.dependsOn(
					BasePlugin.CLEAN_TASK_NAME +
						StringUtil.capitalize(
							NodePlugin.NPM_INSTALL_TASK_NAME));

				Task deployTask = project.task(
					LiferayJavaPlugin.DEPLOY_TASK_NAME);

				deployTask.dependsOn(_GULP_DEPLOY_TASK_NAME);

				for (AbstractArchiveTask abstractArchiveTask :
						distBundleTasks) {

					abstractArchiveTask.into(
						"deploy",
						new Closure<Void>(null) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								ConfigurableFileTree fileTree =
									project.fileTree("dist");

								fileTree.builtBy(_GULP_DEPLOY_TASK_NAME);
								fileTree.include("*.war");

								copySpec.from(fileTree);
							}

						});
				}

				TaskContainer taskContainer = project.getTasks();

				taskContainer.withType(
					ExecuteGulpTask.class,
					new Action<ExecuteGulpTask>() {

						@Override
						public void execute(ExecuteGulpTask executeGulpTask) {
							executeGulpTask.dependsOn(
								createLiferayThemeJsonTask,
								NodePlugin.NPM_INSTALL_TASK_NAME);
						}

					});
			}

		};

		themesProject.subprojects(action);
	}

	private static final String _BUNDLE_FILE_PROPERTY_NAME = "bundleFile";

	private static final String _GULP_BUILD_TASK_NAME = "gulpBuild";

	private static final String _GULP_DEPLOY_TASK_NAME = "gulpDeploy";

}