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

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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
		_removeShrinkwrappedUrls = new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				if (Validator.isNotNull(getRegistry())) {
					return true;
				}

				return false;
			}

		};

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
		executeNpmInstall(false);
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

	public boolean isNodeModulesCacheRemoveBinDirs() {
		return _nodeModulesCacheRemoveBinDirs;
	}

	public boolean isRemoveShrinkwrappedUrls() {
		return GradleUtil.toBoolean(_removeShrinkwrappedUrls);
	}

	public void setNodeModulesCacheDir(Object nodeModulesCacheDir) {
		_nodeModulesCacheDir = nodeModulesCacheDir;
	}

	public void setNodeModulesCacheNativeSync(
		boolean nodeModulesCacheNativeSync) {

		_nodeModulesCacheNativeSync = nodeModulesCacheNativeSync;
	}

	public void setNodeModulesCacheRemoveBinDirs(
		boolean nodeModulesCacheRemoveBinDirs) {

		_nodeModulesCacheRemoveBinDirs = nodeModulesCacheRemoveBinDirs;
	}

	/**
	 * @deprecated As of 1.3.0, replaced by {@link
	 *             #setRemoveShrinkwrappedUrls(Object)}
	 */
	@Deprecated
	public void setRemoveShrinkwrappedUrls(boolean removeShrinkwrappedUrls) {
		_removeShrinkwrappedUrls = removeShrinkwrappedUrls;
	}

	public void setRemoveShrinkwrappedUrls(Object removeShrinkwrappedUrls) {
		_removeShrinkwrappedUrls = removeShrinkwrappedUrls;
	}

	protected void executeNpmInstall(boolean reset) throws Exception {
		Logger logger = getLogger();

		Path shrinkwrapJsonBackupPath = null;
		Path shrinkwrapJsonPath = null;

		File shrinkwrapJsonFile = getShrinkwrapJsonFile();

		if (isRemoveShrinkwrappedUrls() && (shrinkwrapJsonFile != null)) {
			shrinkwrapJsonPath = shrinkwrapJsonFile.toPath();

			shrinkwrapJsonBackupPath = Paths.get(
				shrinkwrapJsonPath.toString() + ".backup");

			Files.copy(
				shrinkwrapJsonPath, shrinkwrapJsonBackupPath,
				StandardCopyOption.REPLACE_EXISTING);

			_removeShrinkwrappedUrls();
		}

		try {
			if (_isCacheEnabled()) {
				if (logger.isInfoEnabled()) {
					logger.info("Cache for {} is enabled", this);
				}

				_npmInstallCached(this, reset);
			}
			else {
				if (logger.isInfoEnabled()) {
					logger.info("Cache for {} is disabled", this);
				}

				_npmInstall(reset);
			}
		}
		finally {
			if (shrinkwrapJsonBackupPath != null) {
				Files.move(
					shrinkwrapJsonBackupPath, shrinkwrapJsonPath,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
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
			NpmInstallTask npmInstallTask, boolean reset)
		throws Exception {

		Logger logger = npmInstallTask.getLogger();
		Project project = npmInstallTask.getProject();

		String digest = _getNodeModulesCacheDigest(npmInstallTask);

		File nodeModulesCacheDir = new File(
			npmInstallTask.getNodeModulesCacheDir(), digest);

		File nodeModulesDir = npmInstallTask.getNodeModulesDir();

		boolean nativeSync = npmInstallTask.isNodeModulesCacheNativeSync();
		boolean removeBinDirs =
			npmInstallTask.isNodeModulesCacheRemoveBinDirs();

		if (reset) {
			project.delete(nodeModulesCacheDir);
		}

		if (nodeModulesCacheDir.exists()) {
			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Restoring node_modules of {} from {}", project,
					nodeModulesCacheDir);
			}

			FileUtil.syncDir(
				project, nodeModulesCacheDir, nodeModulesDir, nativeSync);

			if (removeBinDirs) {
				FileUtil.removeDirs(
					project, nodeModulesDir, _NODE_MODULES_BIN_DIR_NAME);
			}
		}
		else {
			npmInstallTask._npmInstall(reset);

			if (removeBinDirs) {
				FileUtil.removeDirs(
					project, nodeModulesDir, _NODE_MODULES_BIN_DIR_NAME);
			}

			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Caching node_modules of {} in {}", project,
					nodeModulesCacheDir);
			}

			FileUtil.syncDir(
				project, nodeModulesDir, nodeModulesCacheDir, nativeSync);
		}
	}

	private boolean _isCacheEnabled() {
		Project project = getProject();

		PluginContainer pluginContainer = project.getPlugins();

		if (!pluginContainer.hasPlugin("com.liferay.cache") &&
			(getNodeModulesCacheDir() != null)) {

			return true;
		}

		return false;
	}

	private void _npmInstall(boolean reset) throws Exception {
		Logger logger = getLogger();
		int npmInstallRetries = getNpmInstallRetries();
		Project project = getProject();

		for (int i = 0; i < (npmInstallRetries + 1); i++) {
			if (reset || (i > 0)) {
				project.delete(getNodeModulesDir());
			}

			try {
				super.executeNode();

				break;
			}
			catch (IOException ioe) {
				if (i == npmInstallRetries) {
					throw ioe;
				}

				if (logger.isWarnEnabled()) {
					logger.warn(
						ioe.getMessage() + ". Running \"npm install\" again");
				}
			}
		}
	}

	private void _removeShrinkwrappedUrls() throws IOException {
		File shrinkwrapJsonFile = getShrinkwrapJsonFile();

		Path shrinkwrapJsonPath = shrinkwrapJsonFile.toPath();

		String json = new String(
			Files.readAllBytes(shrinkwrapJsonPath), StandardCharsets.UTF_8);

		json = json.replaceAll(
			"\\s+\"(?:from|resolved)\": \"http.+\",*\\r*\\n", "");

		Files.write(shrinkwrapJsonPath, json.getBytes(StandardCharsets.UTF_8));
	}

	private static final String _NODE_MODULES_BIN_DIR_NAME = ".bin";

	private Object _nodeModulesCacheDir;
	private boolean _nodeModulesCacheNativeSync = true;
	private boolean _nodeModulesCacheRemoveBinDirs = true;
	private Object _removeShrinkwrappedUrls;

}