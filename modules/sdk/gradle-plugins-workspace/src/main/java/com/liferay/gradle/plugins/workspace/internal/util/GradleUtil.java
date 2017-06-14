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

/**
 * @author Andrea Di Giorgi
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
		catch (ReflectiveOperationException roe) {
			throw new GradleException("Unable to get property", roe);
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
		catch (MalformedURLException murle) {
			throw new GradleException("Unable to parse " + s, murle);
		}
	}

	private static final String _DEFAULT_REPOSITORY_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups" +
			"/public";

}