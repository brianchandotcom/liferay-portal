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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.util.FileUtil;
import com.liferay.gradle.plugins.node.util.GradleUtil;

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.List;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author Andrea Di Giorgi
 */
public class NpmInstallTask extends ExecuteNpmTask {

	public NpmInstallTask() {
		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					NpmInstallTask npmInstallTask = (NpmInstallTask)task;

					File packageJsonFile = npmInstallTask.getPackageJsonFile();

					if (!packageJsonFile.exists()) {
						return false;
					}

					JsonSlurper jsonSlurper = new JsonSlurper();

					Map<String, Object> packageJson =
						(Map<String, Object>)jsonSlurper.parse(packageJsonFile);

					if (packageJson.containsKey("dependencies") ||
						packageJson.containsKey("devDependencies")) {

						return true;
					}

					return false;
				}

			});
	}

	@Override
	public void executeNode() throws Exception {
		Logger logger = getLogger();
		Project project = getProject();

		PluginContainer pluginContainer = project.getPlugins();

		if (pluginContainer.hasPlugin("com.liferay.cache") ||
			(getNodeModulesCacheDir() == null)) {

			if (logger.isInfoEnabled()) {
				logger.info("Cache for {} is disabled", this);
			}

			_npmInstall();
		}
		else {
			if (logger.isInfoEnabled()) {
				logger.info("Cache for {} is enabled", this);
			}

			_npmInstallCached(this);
		}
	}

	public File getNodeModulesCacheDir() {
		return GradleUtil.toFile(getProject(), _nodeModulesCacheDir);
	}

	@OutputDirectory
	public File getNodeModulesDir() {
		Project project = getProject();

		return project.file("node_modules");
	}

	@InputFile
	public File getPackageJsonFile() {
		Project project = getProject();

		return project.file("package.json");
	}

	@InputFile
	@Optional
	public File getShrinkwrapJsonFile() {
		Project project = getProject();

		File shrinkwrapJsonFile = project.file("npm-shrinkwrap.json");

		if (!shrinkwrapJsonFile.exists()) {
			shrinkwrapJsonFile = null;
		}

		return shrinkwrapJsonFile;
	}

	public boolean isNodeModulesCacheNativeSync() {
		return _nodeModulesCacheNativeSync;
	}

	public void setNodeModulesCacheDir(Object nodeModulesCacheDir) {
		_nodeModulesCacheDir = nodeModulesCacheDir;
	}

	public void setNodeModulesCacheNativeSync(
		boolean nodeModulesCacheNativeSync) {

		_nodeModulesCacheNativeSync = nodeModulesCacheNativeSync;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("install");

		return completeArgs;
	}

	private static String _getNodeModulesCacheDigest(
		NpmInstallTask npmInstallTask) {

		JsonSlurper jsonSlurper = new JsonSlurper();

		File jsonFile = npmInstallTask.getShrinkwrapJsonFile();

		if (jsonFile == null) {
			Logger logger = npmInstallTask.getLogger();

			if (logger.isWarnEnabled()) {
				logger.warn(
					"Unable to find npm-shrinkwrap.json for {}, using " +
						"package.json instead",
					npmInstallTask.getProject());
			}

			jsonFile = npmInstallTask.getPackageJsonFile();
		}

		Map<String, Object> map = (Map<String, Object>)jsonSlurper.parse(
			jsonFile);

		map.remove("name");
		map.remove("version");

		return String.valueOf(map.hashCode());
	}

	private static synchronized void _npmInstallCached(
			NpmInstallTask npmInstallTask)
		throws Exception {

		Logger logger = npmInstallTask.getLogger();
		Project project = npmInstallTask.getProject();

		String digest = _getNodeModulesCacheDigest(npmInstallTask);

		File nodeModulesCacheDir = new File(
			npmInstallTask.getNodeModulesCacheDir(), digest);

		File nodeModulesDir = npmInstallTask.getNodeModulesDir();

		boolean nativeSync = npmInstallTask.isNodeModulesCacheNativeSync();

		if (nodeModulesCacheDir.exists()) {
			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Restoring node_modules of {} from {}", project,
					nodeModulesCacheDir);
			}

			FileUtil.syncDir(
				project, nodeModulesCacheDir, nodeModulesDir, nativeSync);
		}

		npmInstallTask._npmInstall();

		if (logger.isLifecycleEnabled()) {
			logger.lifecycle(
				"Caching node_modules of {} in {}", project,
				nodeModulesCacheDir);
		}

		FileUtil.syncDir(
			project, nodeModulesDir, nodeModulesCacheDir, nativeSync);
	}

	private void _npmInstall() throws Exception {
		super.executeNode();
	}

	private Object _nodeModulesCacheDir;
	private boolean _nodeModulesCacheNativeSync = true;

}