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

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayThemeExtension;
import com.liferay.gradle.plugins.tasks.BuildCssTask;
import com.liferay.gradle.plugins.tasks.BuildThumbnailsTask;
import com.liferay.gradle.plugins.util.ArrayUtil;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.RelativePath;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.copy.CopySpecInternal;
import org.gradle.api.internal.file.copy.CopySpecResolver;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayWebAppPlugin extends LiferayJavaPlugin {

	@Override
	public void apply(Project project) {
		super.apply(project);

		configureWebAppDirName(project);
	}

	@Override
	protected LiferayExtension addLiferayExtension(Project project) {
		Class<? extends LiferayExtension> clazz = LiferayExtension.class;

		String projectName = project.getName();

		if (projectName.endsWith("-theme")) {
			clazz = LiferayThemeExtension.class;
		}

		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, clazz);
	}

	protected void addTaskBuildThumbnails(Project project) {
		Task task = GradleUtil.addTask(
			project, _BUILD_THUMBNAILS_TASK_NAME, BuildThumbnailsTask.class);

		task.setDescription("Generates the theme thumbnails.");
		task.setGroup(BasePlugin.BUILD_GROUP);
	}

	@Override
	protected void addTasks(
		Project project, LiferayExtension liferayExtension) {

		super.addTasks(project, liferayExtension);

		if (isTheme(liferayExtension)) {
			addTaskBuildThumbnails(project);
		}
	}

	@Override
	protected void addTaskWar(Project project) {
	}

	@Override
	protected void applyPlugins(Project project) {
		super.applyPlugins(project);

		GradleUtil.applyPlugin(project, WarPlugin.class);
	}

	@Override
	protected void configureDependencies(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependencies(project, liferayExtension);

		configureDependenciesProvidedCompile(project, liferayExtension);
	}

	@Override
	protected void configureDependenciesCompile(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependenciesCompile(project, liferayExtension);

		if (isTheme(liferayExtension)) {
			for (String dependencyNotation :
					_THEME_COMPILE_DEPENDENCY_NOTATIONS) {

				GradleUtil.addDependency(
					project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
					dependencyNotation);
			}
		}

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					File serviceJarFile = new File(
						getWebAppDir(project),
						"WEB-INF/lib/" + project.getName() + "-service.jar");

					if (serviceJarFile.exists()) {
						GradleUtil.addDependency(
							project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
							serviceJarFile);
					}
				}

			});
	}

	protected void configureDependenciesProvidedCompile(
		Project project, LiferayExtension liferayExtension) {

		for (String dependencyNotation : COMPILE_DEPENDENCY_NOTATIONS) {
			if (isTheme(liferayExtension) &&
				ArrayUtil.contains(
					_THEME_COMPILE_DEPENDENCY_NOTATIONS, dependencyNotation)) {

				continue;
			}

			GradleUtil.addDependency(
				project, WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	@Override
	protected void configureSourceSetMain(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		File srcDir = project.file("docroot/WEB-INF/src");

		Set<File> srcDirs = Collections.singleton(srcDir);

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);
	}

	@Override
	protected void configureTaskBuildCssDirNames(BuildCssTask buildCssTask) {
		List<String> cssDirNames = buildCssTask.getCssDirNames();

		if (!cssDirNames.isEmpty()) {
			return;
		}

		Project project = buildCssTask.getProject();

		String cssDirName = project.relativePath(getWebAppDir(project));

		cssDirNames.add(cssDirName);
	}

	protected void configureTaskBuildThumbnails(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		BuildThumbnailsTask buildThumbnailsTask =
			(BuildThumbnailsTask)GradleUtil.getTask(
				project, _BUILD_THUMBNAILS_TASK_NAME);

		configureTaskBuildThumbnailsImagesDir(
			buildThumbnailsTask, liferayThemeExtension);
	}

	protected void configureTaskBuildThumbnailsImagesDir(
		BuildThumbnailsTask buildThumbnailsTask,
		LiferayThemeExtension liferayThemeExtension) {

		if (buildThumbnailsTask.getImagesDir() == null) {
			File imagesDir = new File(
				liferayThemeExtension.getDiffsDir(), "images");

			buildThumbnailsTask.setImagesDir(imagesDir);
		}
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

		if (isTheme(liferayExtension)) {
			LiferayThemeExtension liferayThemeExtension =
				(LiferayThemeExtension)liferayExtension;

			configureTaskBuildThumbnails(project, liferayThemeExtension);
		}

		configureTaskWar(project, liferayExtension);
	}

	protected void configureTaskWar(
		Project project, LiferayExtension liferayExtension) {

		War warTask = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		configureTaskWarDuplicatesStrategy(warTask);
		configureTaskWarExcludeManifest(warTask);
		configureTaskWarFilesMatching(warTask);
		configureTaskWarOutputs(warTask);
		configureTaskWarRenameDependencies(warTask);
	}

	protected void configureTaskWarDuplicatesStrategy(War warTask) {
		warTask.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	protected void configureTaskWarExcludeManifest(War warTask) {
		CopySpecInternal copySpecInternal = warTask.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			CopySpecResolver copySpecResolver =
				childCopySpecInternal.buildRootResolver();

			RelativePath destRelativePath = copySpecResolver.getDestPath();

			String destRelativePathString = destRelativePath.getPathString();

			if (destRelativePathString.equals("META-INF")) {
				childCopySpecInternal.exclude("**");

				return;
			}
		}
	}

	protected void configureTaskWarFilesMatching(War warTask) {
		final Project project = warTask.getProject();

		final Closure<String> closure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String line) {
				if (!line.contains("content/Language*.properties")) {
					return line;
				}

				StringBuilder sb = new StringBuilder();

				SourceSet sourceSet = GradleUtil.getSourceSet(
					project, SourceSet.MAIN_SOURCE_SET_NAME);

				FileTree fileTree = GradleUtil.getFilteredFileTree(
					sourceSet.getResources(),
					new String[] {"content/Language*.properties"}, null);

				Iterator<File> iterator = fileTree.iterator();

				while (iterator.hasNext()) {
					File file = iterator.next();

					sb.append("\t<language-properties>content/");
					sb.append(file.getName());
					sb.append("</language-properties>");
					sb.append("\n");
				}

				if (sb.length() > 0) {
					sb.setLength(sb.length() - 1);
				}

				return sb.toString();
			}

		};

		warTask.filesMatching(
			"WEB-INF/liferay-hook.xml",
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					fileCopyDetails.filter(closure);
				}

			});
	}

	protected void configureTaskWarOutputs(War warTask) {
		TaskOutputs taskOutputs = warTask.getOutputs();

		taskOutputs.file(warTask.getArchivePath());
	}

	protected void configureTaskWarRenameDependencies(War warTask) {
		final Project project = warTask.getProject();

		Closure<String> closure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String name) {
				Map<String, String> newDependencyNames =
					_getNewDependencyNames();

				String newDependencyName = newDependencyNames.get(name);

				if (Validator.isNotNull(newDependencyName)) {
					return newDependencyName;
				}

				return name;
			}

			private Map<String, String> _getNewDependencyNames() {
				if (_newDependencyNames != null) {
					return _newDependencyNames;
				}

				_newDependencyNames = new HashMap<>();

				Configuration compileConfiguration =
					GradleUtil.getConfiguration(
						project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

				ResolvedConfiguration resolvedConfiguration =
					compileConfiguration.getResolvedConfiguration();

				for (ResolvedArtifact resolvedArtifact :
						resolvedConfiguration.getResolvedArtifacts()) {

					ResolvedModuleVersion resolvedModuleVersion =
						resolvedArtifact.getModuleVersion();

					ModuleVersionIdentifier moduleVersionIdentifier =
						resolvedModuleVersion.getId();

					String oldDependencyName =
						moduleVersionIdentifier.getName() + "-" +
							moduleVersionIdentifier.getVersion() + ".jar";
					String newDependencyName =
						moduleVersionIdentifier.getName() + ".jar";

					_newDependencyNames.put(
						oldDependencyName, newDependencyName);
				}

				return _newDependencyNames;
			}

			private Map<String, String> _newDependencyNames;

		};

		CopySpecInternal copySpecInternal = warTask.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			childCopySpecInternal.rename(closure);
		}
	}

	@Override
	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		Object versionObj = project.getVersion();

		if (!versionObj.equals(Project.DEFAULT_VERSION)) {
			return;
		}

		File pluginPackagePropertiesFile = new File(
			getWebAppDir(project), "WEB-INF/liferay-plugin-package.properties");

		Properties pluginPackageProperties;

		try {
			pluginPackageProperties = FileUtil.readProperties(
				pluginPackagePropertiesFile);
		}
		catch (Exception e) {
			throw new GradleException(
				"Unable to read " + pluginPackagePropertiesFile, e);
		}

		String version = pluginPackageProperties.getProperty(
			"module-full-version");

		if (Validator.isNull(version)) {
			version = pluginPackageProperties.getProperty(
				"module-incremental-version");

			version = liferayExtension.getVersionPrefix() + "." + version;
		}

		project.setVersion(version);
	}

	protected void configureWebAppDirName(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("docroot");
	}

	protected File getWebAppDir(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

	protected boolean isTheme(LiferayExtension liferayExtension) {
		if (liferayExtension instanceof LiferayThemeExtension) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final String _BUILD_THUMBNAILS_TASK_NAME = "buildThumbnails";

	private static final String[] _THEME_COMPILE_DEPENDENCY_NOTATIONS = {
		"com.liferay.portal:util-bridges:default",
		"com.liferay.portal:util-java:default",
		"com.liferay.portal:util-taglib:default",
		"commons-logging:commons-logging:1.1.1", "log4j:log4j:1.2.16"
	};

}