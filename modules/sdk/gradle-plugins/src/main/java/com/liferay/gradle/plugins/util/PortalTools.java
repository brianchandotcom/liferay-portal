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

package com.liferay.gradle.plugins.util;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.gradle.api.Project;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class PortalTools {

	public static final String GROUP = "com.liferay";

	public static String getVersion(Project project, String name) {
		String key = name + ".version";

		String version = GradleUtil.getProperty(project, key, (String)null);

		if (Validator.isNotNull(version)) {
			return version;
		}

		File dir = project.getProjectDir();

		while ((dir != null) && Validator.isNull(version)) {
			File gradlePropertiesFile = new File(dir, "gradle.properties");

			if (gradlePropertiesFile.exists()) {
				Properties gradleProperties = GUtil.loadProperties(
					gradlePropertiesFile);

				version = gradleProperties.getProperty(key);
			}

			dir = dir.getParentFile();
		}

		if (Validator.isNotNull(version)) {
			return version;
		}

		return _versions.getProperty(name);
	}

	private static final Properties _versions = new Properties();

	static {
		ClassLoader classLoader = PortalTools.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/gradle/plugins/dependencies" +
					"/portal-tools.properties")) {

			_versions.load(inputStream);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}