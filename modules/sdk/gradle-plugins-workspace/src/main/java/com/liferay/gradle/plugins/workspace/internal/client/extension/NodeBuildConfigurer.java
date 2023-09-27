/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.gradle.api.Task;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
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

		if (!_hasFrontendScript(project)) {
			return;
		}

		GradleUtil.applyPlugin(project, NodePlugin.class);

		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

		_configureExtensionNode(nodeExtension);

		assembleClientExtensionTaskProvider.configure(
			assembleClientExtensionTask -> {
				TaskContainer tasks = project.getTasks();

				Task task = tasks.findByName(
					NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);

				if (task != null) {
					assembleClientExtensionTask.dependsOn(task);
				}
			});
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

		try {
			Version version = Version.parseVersion(nodeVersion);

			if (version.compareTo(_MINIMUM_NPM_VERSION) < 0) {
				nodeExtension.setNpmVersion(_MINIMUM_NPM_VERSION.toString());
			}
		}
		catch (Exception exception) {
			throw new GradleException("Unable to parse NPM version", exception);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean _hasFrontendScript(Project project) {
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

		if ((liferayThemeMap == null) && (scriptsMap != null)) {
			return true;
		}

		return false;
	}

	private static final Version _MINIMUM_NODE_VERSION = Version.parseVersion(
		"10.15.3");

	private static final Version _MINIMUM_NPM_VERSION = Version.parseVersion(
		"6.4.1");

}