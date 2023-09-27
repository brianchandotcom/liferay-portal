/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util;

import com.liferay.gradle.util.Validator;

import java.io.File;

import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static void addDefaultRepositories(Project project) {
		RepositoryHandler repositoryHandler = project.getRepositories();

		repositoryHandler.mavenCentral();

		repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(_DEFAULT_REPOSITORY_URL);
				}

			});

		repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(_REPOSITORY_URL);
				}

			});
	}

	public static String getArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = getConvention(
			project, BasePluginConvention.class);

		return basePluginConvention.getArchivesBaseName();
	}

	public static String getProjectPath(File projectDir, File rootDir) {
		String projectPath = FileUtil.relativize(projectDir, rootDir);

		return ":" + projectPath.replace(File.separatorChar, ':');
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
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new GradleException(
				"Unable to get property", reflectiveOperationException);
		}
	}

	public static boolean getProperty(
		Object object, String name, boolean defaultValue) {

		Object value = getProperty(object, name);

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Boolean) {
			return (Boolean)value;
		}

		if (value instanceof String) {
			return Boolean.parseBoolean((String)value);
		}

		return defaultValue;
	}

	public static String getProperty(
		Object object, String name, String defaultValue) {

		Object value = getProperty(object, name);

		if (value == null) {
			return defaultValue;
		}

		return toString(value);
	}

	public static boolean hasTask(Project project, String name) {
		TaskContainer taskContainer = project.getTasks();

		if (taskContainer.findByName(name) != null) {
			return true;
		}

		return false;
	}

	public static boolean toBoolean(Object object) {
		object = toObject(object);

		if (object instanceof Boolean) {
			return (Boolean)object;
		}

		if (object instanceof String) {
			return Boolean.parseBoolean((String)object);
		}

		return false;
	}

	public static File toFile(Project project, Object object) {
		object = toObject(object);

		if (object == null) {
			return null;
		}

		return project.file(object);
	}

	public static URL toURL(Object url) {
		url = toObject(url);

		if (url instanceof URL) {
			return (URL)url;
		}

		String s = toString(url);

		if (Validator.isNull(s)) {
			return null;
		}

		try {
			return new URL(toString(url));
		}
		catch (MalformedURLException malformedURLException) {
			throw new GradleException(
				"Unable to parse " + s, malformedURLException);
		}
	}

	private static final String _DEFAULT_REPOSITORY_URL =
		"https://repository-cdn.liferay.com/nexus/content/groups/public";

	private static final String _REPOSITORY_URL =
		"https://repository.liferay.com/nexus/content/groups/public";

}