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

import com.liferay.gradle.plugins.LiferayPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.gulp.GulpPlugin;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.lang.Closure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.AntBuilder;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySourceSpec;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Tar;
import org.gradle.api.tasks.bundling.Zip;

import org.json.JSONObject;

/**
 * @author David Truong
 */
public class WorkspacePlugin implements Plugin<Project> {

	public static final String BUNDLE_CONFIGURATION_NAME = "bundle";

	public static final String DIST_BUNDLE_TAR_TASK_NAME = "distBundleTar";

	public static final String DIST_BUNDLE_ZIP_TASK_NAME = "distBundleZip";

	public static final String INIT_BUNDLE_TASK_NAME = "initBundle";

	public static final String PLUGIN_NAME = "workspace";

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.addExtension(
			project, PLUGIN_NAME, WorkspaceExtension.class);

		Configuration bundleConfiguration = addConfigurationBundle(
			project, workspaceExtension);

		addRepositoryBundle(project, workspaceExtension);

		Tar distBundleTarTask = addTaskDistBundle(
			project, DIST_BUNDLE_TAR_TASK_NAME, Tar.class, bundleConfiguration);
		Zip distBundleZipTask = addTaskDistBundle(
			project, DIST_BUNDLE_ZIP_TASK_NAME, Zip.class, bundleConfiguration);

		AbstractArchiveTask[] distBundleTasks = {
			distBundleTarTask, distBundleZipTask
		};

		Copy initBundle = addTaskInitBundle(
			project, workspaceExtension, bundleConfiguration);

		configureModules(project, workspaceExtension);

		configureThemes(project, workspaceExtension, distBundleTasks);

		configurePluginsSDK(
			project, workspaceExtension, initBundle, distBundleTasks);
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

	protected MavenArtifactRepository addRepositoryBundle(
		Project project, final WorkspaceExtension workspaceExtension) {

		RepositoryHandler repositoryHandler = project.getRepositories();

		return repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(
						workspaceExtension.getBundleMavenUrl());
				}

			});
	}

	protected <T extends AbstractArchiveTask> T addTaskDistBundle(
		final Project project, String taskName, Class<T> clazz,
		final Configuration bundleConfiguration) {

		T task = GradleUtil.addTask(project, taskName, clazz);

		task.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					File file = bundleConfiguration.getSingleFile();

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

		task.setBaseName(project.getName());
		task.setDescription("Assembles the bundle and zips it up.");
		task.setDestinationDir(project.getBuildDir());
		task.setIncludeEmptyDirs(false);

		return task;
	}

	protected Copy addTaskInitBundle(
		final Project project, final WorkspaceExtension workspaceExtension,
		final Configuration bundleConfiguration) {

		final Copy copy = GradleUtil.addTask(
			project, INIT_BUNDLE_TASK_NAME, Copy.class);

		copy.doFirst(new Action<Task>() {

			@Override
			public void execute(Task task) {
				project.delete(copy.getDestinationDir());
			}

		});

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					File bundle = bundleConfiguration.getSingleFile();

					String bundleName = bundle.getName();

					if (bundleName.endsWith(".tar.gz")) {
						return project.tarTree(
							bundleConfiguration.getSingleFile());
					}
					else {
						return project.zipTree(
							bundleConfiguration.getSingleFile());
					}
				}

			},
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});

		File rootDir = project.getRootDir();

		copy.from(
			new File(rootDir, "/configs/common"),
			new File(rootDir, "/configs/${gradle.environment}"));

		copy.into(workspaceExtension.getHomeDir());

		copy.setDescription(
			"Download and upzip the bundle into " +
			workspaceExtension.getHomeDir());
		copy.setIncludeEmptyDirs(false);

		return copy;
	}

	protected void configureModules(
		final Project project, final WorkspaceExtension workspaceExtension) {

		final Project modulesProject = GradleUtil.getProject(
			project, workspaceExtension.getModulesDir());

		modulesProject.subprojects(new Action<Project>() {
			@Override
			public void execute(Project subproject) {
				Set<Project> subprojects = subproject.getSubprojects();

				if ((subprojects != null) && (subprojects.size() > 0)) {
					return;
				}

				GradleUtil.applyPlugin(subproject, LiferayPlugin.class);

				Zip zip = (Zip)GradleUtil.getTask(project, "distBundle");

				zip.into(
					"deploy",
					new Closure<Void>(null) {

						@SuppressWarnings("unused")
						public void doCall(CopySourceSpec copySourceSpec) {
							copySourceSpec.from(JavaPlugin.JAR_TASK_NAME);
						}

					});

				LiferayExtension liferayExtension = GradleUtil.getExtension(
					subproject, LiferayExtension.class);

				liferayExtension.setAppServerParentDir(
					workspaceExtension.getHomeDir());
			}

		});
	}

	protected void configurePluginsSDK(
		Project project, final WorkspaceExtension workspaceExtension,
		Copy initBundle, AbstractArchiveTask[] distBundleTasks) {

		File pluginsSDKDir = workspaceExtension.getPluginsSDKDir();

		if (!pluginsSDKDir.exists()) {
			return;
		}

		final Project pluginsSDKProject = GradleUtil.getProject(
			project, workspaceExtension.getPluginsSDKDir());

		AntBuilder antBuilder = pluginsSDKProject.getAnt();

		antBuilder.importBuild("build.xml");

		Task build = GradleUtil.addTask(pluginsSDKProject, "build", Task.class);

		final Task war = GradleUtil.getTask(pluginsSDKProject, "war");

		build.dependsOn(war);

		File homeDir = workspaceExtension.getHomeDir();

		if (!homeDir.exists()) {
			war.dependsOn(initBundle);
		}

		for (AbstractArchiveTask abstractArchiveTask : distBundleTasks) {
			abstractArchiveTask.into(
				"deploy",
				new Closure<Void>(null) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						ConfigurableFileTree fileTree =
							pluginsSDKProject.fileTree("dist");

						fileTree.builtBy(war);

						fileTree.include("*.war");

						copySpec.from(fileTree);
					}

				});
		}

		Task updateSDKProperties = GradleUtil.addTask(
			pluginsSDKProject, "updateSDKProperties", Task.class);

		updateSDKProperties.doLast(new Action<Task>() {
			@Override
			public void execute(Task task) {
				try {
					String username = System.getProperty("user.name");

					File buildPropertiesFile = new File(
						workspaceExtension.getPluginsSDKDir(),
						"build." + username + ".properties");

					Properties buildProperties = FileUtil.readProperties(
						buildPropertiesFile);

					buildProperties.setProperty(
						"app.server.parent.dir",
						FileUtil.getAbsolutePath(
							workspaceExtension.getHomeDir()));

					buildProperties.store(
						new FileOutputStream(buildPropertiesFile), "");
				}
				catch (Exception e) {
				}
			}

		});

		build.dependsOn(updateSDKProperties);
	}

	protected void configureThemes(
		Project project, final WorkspaceExtension workspaceExtension,
		final AbstractArchiveTask[] distBundleTasks) {

		final Project themesProject = GradleUtil.getProject(
			project, workspaceExtension.getThemesDir());

		themesProject.subprojects(new Action<Project>() {
			@Override
			public void execute(final Project subproject) {
				Set<Project> subprojects = subproject.getSubprojects();

				if ((subprojects != null) && (subprojects.size() > 0)) {
					return;
				}

				GradleUtil.applyPlugin(subproject, BasePlugin.class);
				GradleUtil.applyPlugin(subproject, GulpPlugin.class);

				Task createLiferayThemeJson = GradleUtil.addTask(
					subproject, "createLiferayThemeJson", Task.class);

				Task deploy = GradleUtil.addTask(
					subproject, "deploy", Task.class);

				final Task gulpDeploy = GradleUtil.getTask(
					subproject, "gulpDeploy");

				deploy.dependsOn(gulpDeploy);

				subproject.setBuildDir(
					new File(subproject.getProjectDir(), "build_gradle"));

				Task assemble = GradleUtil.getTask(subproject, "assemble");

				assemble.dependsOn("gulpBuild");

				Task clean = GradleUtil.getTask(subproject, "clean");

				clean.dependsOn("cleanNpmInstall");

				clean.configure(
					new Closure<Void>(null) {

						@SuppressWarnings("unused")
						public void doCall() {
							subproject.delete("build", "dist");
						}

					}
				);

				final File liferayThemeJsonFile = new File(
					subproject.getProjectDir(), "liferay-theme.json");

				if (!liferayThemeJsonFile.exists()) {
					createLiferayThemeJson.doLast(new Action<Task>() {

						@Override
						public void execute(Task task) {
							File appServerDir = new File(
								workspaceExtension.getHomeDir(),
								"tomcat-7.0.62");
							File appServerThemeDir = new File(
								appServerDir,
								"webapps/" + subproject.getName());
							File deployDir = new File(
								workspaceExtension.getHomeDir(), "deploy");

							JSONObject jsonObject = new JSONObject();

							Map<String, Object> jsonValues = new HashMap<>();

							jsonValues.put(
								"appServerPath",
								appServerDir.getAbsolutePath());
							jsonValues.put(
								"appServerPathTheme",
								appServerThemeDir.getAbsolutePath());
							jsonValues.put("deployed", false);
							jsonValues.put(
								"deployPath", deployDir.getAbsolutePath());
							jsonValues.put("themeName", subproject.getName());

							jsonObject.put("LiferayTheme", jsonValues);

							try (PrintStream out = new PrintStream(
								new FileOutputStream(liferayThemeJsonFile))) {

								out.println(jsonObject.toString());
							}
							catch (Exception e) {
							}
						}

					});
				}

				for (AbstractArchiveTask abstractArchiveTask :
						distBundleTasks) {

					abstractArchiveTask.into(
						"deploy",
						new Closure<Void>(null) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								ConfigurableFileTree fileTree =
									themesProject.fileTree("dist");

								fileTree.builtBy(gulpDeploy);

								fileTree.include("*.war");

								copySpec.from(fileTree);
							}

						});
				}

				TaskContainer taskContainer = subproject.getTasks();

				taskContainer.withType(
					ExecuteGulpTask.class,
					new Action<ExecuteGulpTask>() {

						@Override
						public void execute(ExecuteGulpTask executeGulpTask) {
							executeGulpTask.dependsOn("createLiferayThemeJson");
							executeGulpTask.dependsOn("npmInstall");
						}

					});
			}

		});
	}

}