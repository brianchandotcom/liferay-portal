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

import com.liferay.gradle.plugins.BasePortalToolDefaultsPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static final String PORTAL_TOOL_GROUP = "com.liferay";

	public static String getPortalToolVersion(
		Project project, String portalToolName) {

		String portalToolVersion = _portalToolVersions.getProperty(
			portalToolName);

		return GradleUtil.getProperty(
			project, portalToolName + ".version", portalToolVersion);
	}

	public static File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	public static Map<String, String> toStringMap(Map<String, ?> map) {
		Map<String, String> stringMap = new HashMap<>();

		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = toString(entry.getValue());

			stringMap.put(key, value);
		}

		return stringMap;
	}

	private static final Properties _portalToolVersions = new Properties();

	static {
		ClassLoader classLoader =
			BasePortalToolDefaultsPlugin.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/gradle/plugins/dependencies" +
					"/portal-tools.properties")) {

			_portalToolVersions.load(inputStream);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}