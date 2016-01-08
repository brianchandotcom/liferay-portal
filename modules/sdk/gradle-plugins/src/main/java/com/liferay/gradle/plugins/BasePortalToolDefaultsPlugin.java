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

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BasePortalToolDefaultsPlugin<T extends Plugin<Project>>
	extends BaseDefaultsPlugin<T> {

	public static final String PORTAL_TOOL_GROUP = "com.liferay";

	protected void addPortalToolDependencies(Project project) {
		addPortalToolDependencies(
			project, getPortalToolConfigurationName(), getPortalToolName());
	}

	protected void addPortalToolDependencies(
		Project project, String configurationName, String portalToolName) {

		String portalToolVersion = getPortalToolVersion(
			project, portalToolName);

		if (Validator.isNotNull(portalToolVersion)) {
			GradleUtil.addDependency(
				project, configurationName, PORTAL_TOOL_GROUP, portalToolName,
				portalToolVersion);
		}
	}

	@Override
	protected void configureDefaults(Project project, T plugin) {
		addPortalToolDependencies(project);
	}

	protected abstract String getPortalToolConfigurationName();

	protected abstract String getPortalToolName();

	protected String getPortalToolVersion(
		Project project, String portalToolName) {

		String portalToolVersion = _portalToolVersions.getProperty(
			portalToolName);

		return GradleUtil.getProperty(
			project, portalToolName + ".version", portalToolVersion);
	}

	private static final Properties _portalToolVersions = new Properties();

	static {
		ClassLoader classLoader =
			BasePortalToolDefaultsPlugin.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/gradle/plugins/dependencies/" +
					"portal-tools.properties")) {

			_portalToolVersions.load(inputStream);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}