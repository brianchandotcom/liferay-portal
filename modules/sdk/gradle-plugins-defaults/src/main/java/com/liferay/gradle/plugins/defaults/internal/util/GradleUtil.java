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

package com.liferay.gradle.plugins.defaults.internal.util;

import com.liferay.gradle.util.Validator;

import java.io.File;

import java.lang.reflect.Method;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ArtifactRepositoryContainer;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.PluginContainer;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static final String PORTAL_TOOL_GROUP = "com.liferay";

	public static final String SNAPSHOT_PROPERTY_NAME = "snapshot";

	public static final String SNAPSHOT_VERSION_SUFFIX = "-SNAPSHOT";

	public static <T extends Task> T addTask(
		Project project, String name, Class<T> clazz, boolean overwrite) {

		Map<String, Object> args = new HashMap<>();

		args.put(Task.TASK_OVERWRITE, overwrite);
		args.put(Task.TASK_TYPE, clazz);

		return (T)project.task(args, name);
	}

	public static void excludeTasksWithProperty(
		Project project, String propertyName, boolean defaultValue,
		String... taskNames) {

		if (!project.hasProperty(propertyName) ||
			!getProperty(project, propertyName, defaultValue)) {

			return;
		}

		for (String taskName : taskNames) {
			Task task = getTask(project, taskName);

			task.setDependsOn(Collections.emptySet());
			task.setEnabled(false);
			task.setFinalizedBy(Collections.emptySet());
		}
	}

	public static String getArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = getConvention(
			project, BasePluginConvention.class);

		return basePluginConvention.getArchivesBaseName();
	}

	public static Project getProject(Project rootProject, String name) {
		for (Project project : rootProject.getAllprojects()) {
			if (name.equals(project.getName())) {
				return project;
			}
		}

		return null;
	}

	public static Object getProperty(Object object, String name) {
		try {
			Class<?> clazz = object.getClass();

			Method hasPropertyMethod = clazz.getMethod(
				"hasProperty", String.class);

			boolean hasProperty = (boolean)hasPropertyMethod.invoke(
				object, name);

			if (!hasProperty) {
				return null;
			}

			Method getPropertyMethod = clazz.getMethod(
				"getProperty", String.class);

			Object value = getPropertyMethod.invoke(object, name);

			if ((value instanceof String) && Validator.isNull((String)value)) {
				value = null;
			}

			return value;
		}
		catch (ReflectiveOperationException roe) {
			throw new GradleException("Unable to get property", roe);
		}
	}

	public static String getProperty(
		Object object, String name, String defaultValue) {

		Object value = getProperty(object, name);

		if (value == null) {
			return defaultValue;
		}

		return toString(value);
	}

	public static File getRootDir(File dir, String markerFileName) {
		while (true) {
			File markerFile = new File(dir, markerFileName);

			if (markerFile.exists()) {
				return dir;
			}

			dir = dir.getParentFile();

			if (dir == null) {
				return null;
			}
		}
	}

	public static File getRootDir(Project project, String markerFileName) {
		return getRootDir(project.getProjectDir(), markerFileName);
	}

	public static File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	public static boolean hasPlugin(
		Project project, Class<? extends Plugin<?>> pluginClass) {

		PluginContainer pluginContainer = project.getPlugins();

		return pluginContainer.hasPlugin(pluginClass);
	}

	public static boolean hasStartParameterTask(
		Project project, String taskName) {

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		List<String> taskNames = startParameter.getTaskNames();

		if (taskNames.contains(taskName)) {
			return true;
		}

		return false;
	}

	public static boolean isFromMavenLocal(Project project, File file) {
		RepositoryHandler repositoryHandler = project.getRepositories();

		ArtifactRepository artifactRepository = repositoryHandler.findByName(
			ArtifactRepositoryContainer.DEFAULT_MAVEN_LOCAL_REPO_NAME);

		if (!(artifactRepository instanceof MavenArtifactRepository)) {
			return false;
		}

		MavenArtifactRepository mavenArtifactRepository =
			(MavenArtifactRepository)artifactRepository;

		Path repositoryPath = Paths.get(mavenArtifactRepository.getUrl());

		if (FileUtil.isChild(file, repositoryPath.toFile())) {
			return true;
		}

		return false;
	}

	public static boolean isSnapshot(Project project) {
		String version = String.valueOf(project.getVersion());

		if (version.endsWith(SNAPSHOT_VERSION_SUFFIX)) {
			return true;
		}

		return false;
	}

	public static boolean isTestProject(Project project) {
		String projectName = project.getName();

		if (projectName.endsWith("-test")) {
			return true;
		}

		return false;
	}

	public static void setProjectSnapshotVersion(
		Project project, String... propertyNames) {

		boolean snapshot = false;

		if (project.hasProperty(SNAPSHOT_PROPERTY_NAME)) {
			snapshot = getProperty(project, SNAPSHOT_PROPERTY_NAME, true);
		}

		if (!snapshot) {
			for (String propertyName : propertyNames) {
				if (project.hasProperty(propertyName) &&
					getProperty(project, propertyName, true)) {

					snapshot = true;

					break;
				}
			}
		}

		String version = String.valueOf(project.getVersion());

		if (snapshot && !version.endsWith(SNAPSHOT_VERSION_SUFFIX)) {
			project.setVersion(version + SNAPSHOT_VERSION_SUFFIX);
		}
	}

	public static <P extends Plugin<? extends Project>> void withPlugin(
		Project project, Class<P> pluginClass, Action<P> action) {

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(pluginClass, action);
	}

}