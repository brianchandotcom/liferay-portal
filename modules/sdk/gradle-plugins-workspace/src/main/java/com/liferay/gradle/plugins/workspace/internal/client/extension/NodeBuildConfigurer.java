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

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.Map;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;

import org.osgi.framework.Version;

/**
 * @author Gregory Amerson
 */
public class NodeBuildConfigurer implements ClientExtensionConfigurer {

	@Override
	public void apply(
		Project project,
		TaskProvider<Copy> assembleClientExtensionTaskProvider) {

		if (!_hasFrontendBuildScript(project)) {
			return;
		}

		GradleUtil.applyPlugin(project, NodePlugin.class);

		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

		_configureExtensionNode(nodeExtension);

		assembleClientExtensionTaskProvider.configure(
			assembleClientExtensionTask ->
				assembleClientExtensionTask.dependsOn(
					NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME));
	}

	private void _configureExtensionNode(NodeExtension nodeExtension) {
		String nodeVersion = nodeExtension.getNodeVersion();

		try {
			Version version = Version.parseVersion(nodeVersion);

			if (version.compareTo(_MINIMUM_NODE_VERSION) < 0) {
				nodeVersion = _MINIMUM_NODE_VERSION.toString();

				nodeExtension.setNodeVersion(nodeVersion);
			}
		}
		catch (Exception exception) {
			throw new GradleException(
				"Unable to parse Node version", exception);
		}

		String npmVersion = nodeExtension.getNpmVersion();

		try {
			Version version = Version.parseVersion(nodeVersion);

			if (version.compareTo(_MINIMUM_NPM_VERSION) < 0) {
				npmVersion = _MINIMUM_NPM_VERSION.toString();

				nodeExtension.setNpmVersion(npmVersion);
			}
		}
		catch (Exception exception) {
			throw new GradleException("Unable to parse NPM version", exception);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean _hasFrontendBuildScript(Project project) {
		File packageJsonFile = project.file("package.json");

		if (!packageJsonFile.exists()) {
			return false;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> packageJsonMap =
			(Map<String, Object>)jsonSlurper.parse(packageJsonFile);

		Map<String, Object> liferayThemeMap =
			(Map<String, Object>)packageJsonMap.get("liferayTheme");
		Map<String, Object> scriptsMap =
			(Map<String, Object>)packageJsonMap.get("scripts");

		if ((liferayThemeMap == null) && (scriptsMap != null) &&
			(scriptsMap.get("build") != null)) {

			return true;
		}

		return false;
	}

	private static final Version _MINIMUM_NODE_VERSION = Version.parseVersion(
		"10.15.3");

	private static final Version _MINIMUM_NPM_VERSION = Version.parseVersion(
		"6.4.1");

}