/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.util;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class PortalTools {

	public static final String GROUP = "com.liferay";

	public static final String PORTAL_VERSION_7_0_X = "7.0.x";

	public static final String PORTAL_VERSION_7_1_X = "7.1.x";

	public static final String PORTAL_VERSION_7_2_X = "7.2.x";

	public static final String PORTAL_VERSION_7_3_X = "7.3.x";

	public static final String PORTAL_VERSION_PROPERTY_NAME = "portal.version";

	public static void addPortalToolDependencies(
		Project project, String configurationName, String portalToolGroup,
		String portalToolName) {

		String portalToolVersion = getVersion(project, portalToolName);

		if (Validator.isNotNull(portalToolVersion)) {
			GradleUtil.addDependency(
				project, configurationName, portalToolGroup, portalToolName,
				portalToolVersion);
		}
	}

	public static String getPortalVersion(Project project) {
		return _getPortalVersion(project);
	}

	public static String getVersion(Project project, String name) {
		return _getVersion(project, name, _getPortalVersion(project));
	}

	protected static final String PORTAL_TOOLS_FILE_NAME =
		"com/liferay/gradle/plugins/dependencies/portal-tools.properties";

	private static String _getPortalVersion(Project project) {
		String portalVersion = null;

		for (String name : _PORTAL_VERSION_PROPERTY_NAMES) {
			portalVersion = GradleUtil.getProperty(project, name, (String)null);

			if (Validator.isNotNull(portalVersion)) {
				break;
			}
		}

		if (portalVersion != null) {
			portalVersion = portalVersion.trim();
			portalVersion = portalVersion.toLowerCase();

			int pos = portalVersion.indexOf('-');

			if (pos != -1) {
				portalVersion = portalVersion.substring(0, pos);
			}

			if (portalVersion.isEmpty() || portalVersion.equals("latest") ||
				portalVersion.equals("master")) {

				portalVersion = null;
			}
		}

		return portalVersion;
	}

	private static String _getVersion(
		Project project, String name, String portalVersion) {

		String key = name + ".version";

		String version = GradleUtil.getProperty(project, key, (String)null);

		if (Validator.isNotNull(version)) {
			return version;
		}

		File dir = project.getProjectDir();

		while ((dir != null) && Validator.isNull(version)) {
			Properties properties = GradleUtil.getGradleProperties(dir);

			if (properties != null) {
				version = properties.getProperty(key);
			}

			dir = dir.getParentFile();
		}

		if (Validator.isNotNull(version)) {
			return version;
		}

		Properties properties = _versionsMap.get(portalVersion);

		if (properties == null) {
			properties = _versionsMap.get(null);
		}

		return properties.getProperty(name);
	}

	private static Properties _populateVersionsMap(
			ClassLoader classLoader, String portalVersion,
			Properties defaultProperties)
		throws IOException {

		String fileName = PORTAL_TOOLS_FILE_NAME;

		if (portalVersion != null) {
			fileName =
				fileName.substring(0, fileName.length() - 11) + "-" +
					portalVersion + ".properties";
		}

		Properties properties = new Properties(defaultProperties);

		try (InputStream inputStream = classLoader.getResourceAsStream(
				fileName)) {

			properties.load(inputStream);
		}

		_versionsMap.put(portalVersion, properties);

		return properties;
	}

	private static final String[] _PORTAL_VERSION_PROPERTY_NAMES = {
		"git.working.branch.name", PORTAL_VERSION_PROPERTY_NAME
	};

	private static final Map<String, Properties> _versionsMap;

	static {
		_versionsMap = new HashMap<>();

		ClassLoader classLoader = PortalTools.class.getClassLoader();

		try {
			_populateVersionsMap(classLoader, null, null);
		}
		catch (IOException ioException) {
			throw new ExceptionInInitializerError(ioException);
		}
	}

}