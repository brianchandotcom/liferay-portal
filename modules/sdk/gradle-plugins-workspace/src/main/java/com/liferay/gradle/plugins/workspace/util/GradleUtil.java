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

package com.liferay.gradle.plugins.workspace.util;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.initialization.Settings;
import org.gradle.api.internal.DynamicObject;
import org.gradle.api.internal.DynamicObjectUtil;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static MavenArtifactRepository addMavenRepository(
		Project project, final Object url) {

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

	public static String getProjectPath(File projectDir, File rootDir) {
		String projectPath = FileUtil.relativize(projectDir, rootDir);

		return ":" + projectPath.replace(File.separatorChar, ':');
	}

	public static Object getProperty(Object object, String name) {
		DynamicObject dynamicObject = DynamicObjectUtil.asDynamicObject(object);

		if (!dynamicObject.hasProperty(name)) {
			return null;
		}

		Object value = dynamicObject.getProperty(name);

		if ((value instanceof String) && Validator.isNull((String)value)) {
			value = null;
		}

		return value;
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

	public static File getProperty(
		Settings settings, String name, File defaultValue) {

		String value = getProperty(settings, name, (String)null);

		if (Validator.isNull(value)) {
			return defaultValue;
		}

		return new File(settings.getRootDir(), value);
	}

}