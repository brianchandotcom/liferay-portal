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
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class NpmInstallTask extends ExecutePackageManagerTask {

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

					if (!packageJsonFile.exists() || !isUseNpm()) {
						return false;
					}

					JsonSlurper jsonSlurper = new JsonSlurper();

					Map<String, Object> packageJsonMap =
						(Map<String, Object>)jsonSlurper.parse(packageJsonFile);

					if (packageJsonMap.containsKey("dependencies") ||
						packageJsonMap.containsKey("devDependencies")) {

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

	public File getNodeModulesDigestFile() {
		return GradleUtil.toFile(getProject(), _nodeModulesDigestFile);
	}

	@OutputDirectory
	@Override
	public File getNodeModulesDir() {
		return super.getNodeModulesDir();
	}

	@Input
	@Optional
	public String getNodeVersion() {
		return GradleUtil.toString(_nodeVersion);
	}

	@Input
	@Optional
	public String getNpmVersion() {
		return GradleUtil.toString(_npmVersion);
	}

	@InputFile
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getPackageJsonFile() {
		Project project = getProject();

		return project.file("package.json");
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getPackageLockJsonFile() {
		return _getExistentFile("package-lock.json");
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getShrinkwrapJsonFile() {
		return _getExistentFile("npm-shrinkwrap.json");
	}

	public boolean isCheckDigest() {
		if (_isCacheEnabled()) {
			return false;
		}

		Project project = getProject();

		PluginContainer pluginContainer = project.getPlugins();

		if (!pluginContainer.hasPlugin("com.liferay.cache") &&
			(getNodeModulesDigestFile() != null)) {

			return true;
		}

		return false;
	}

	public boolean isNodeModulesCacheNativeSync() {
		return _nodeModulesCacheNativeSync;
	}

	public boolean isRemoveShrinkwrappedUrls() {
		return GradleUtil.toBoolean(_removeShrinkwrappedUrls);
	}

	public boolean isUseNpmCI() {
		return GradleUtil.toBoolean(_useNpmCI);
	}

	public void setNodeModulesCacheDir(Object nodeModulesCacheDir) {
		_nodeModulesCacheDir = nodeModulesCacheDir;
	}

	public void setNodeModulesCacheNativeSync(
		boolean nodeModulesCacheNativeSync) {

		_nodeModulesCacheNativeSync = nodeModulesCacheNativeSync;
	}

	public void setNodeModulesDigestFile(Object nodeModulesDigestFile) {
		_nodeModulesDigestFile = nodeModulesDigestFile;
	}

	public void setNodeVersion(Object nodeVersion) {
		_nodeVersion = nodeVersion;
	}

	public void setNpmVersion(Object npmVersion) {
		_npmVersion = npmVersion;
	}

	public void setRemoveShrinkwrappedUrls(Object removeShrinkwrappedUrls) {
		_removeShrinkwrappedUrls = removeShrinkwrappedUrls;
	}

	public void setUseNpmCI(Object useNpmCI) {
		_useNpmCI = useNpmCI;
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

				if (isCheckDigest()) {
					_npmInstallCheckDigest(reset);
				}
				else {
					_npmInstall(reset);
				}
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

		if (_npmCacheVerify) {
			completeArgs.add("cache");
			completeArgs.add("verify");
		}
		else if (isUseNpmCI() && (getPackageLockJsonFile() != null)) {
			completeArgs.add("ci");
		}
		else {
			completeArgs.add("install");
		}

		return completeArgs;
	}

	private static String _getNodeModulesCacheDigest(
		NpmInstallTask npmInstallTask) {

		Logger logger = npmInstallTask.getLogger();

		JsonSlurper jsonSlurper = new JsonSlurper();

		File jsonFile = npmInstallTask.getPackageLockJsonFile();

		if (jsonFile == null) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"Unable to find package-lock.json for {}, using " +
						"npm-shrinkwrap.json instead",
					npmInstallTask.getProject());
			}

			jsonFile = npmInstallTask.getShrinkwrapJsonFile();
		}

		if (jsonFile == null) {
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

			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Removing binary symbolic links of {} from {}", project,
					nodeModulesDir);
			}

			FileUtil.removeBinDirLinks(logger, nodeModulesDir);
		}
		else {
			npmInstallTask._npmInstall(reset);

			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Removing binary symbolic links of {} from {}", project,
					nodeModulesDir);
			}

			FileUtil.removeBinDirLinks(logger, nodeModulesDir);

			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Caching node_modules of {} in {}", project,
					nodeModulesCacheDir);
			}

			FileUtil.syncDir(
				project, nodeModulesDir, nodeModulesCacheDir, nativeSync);
		}

		if (!OSDetector.isWindows()) {
			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Restoring binary symbolic links of {} from {}", project,
					nodeModulesDir);
			}

			FileUtil.createBinDirLinks(logger, nodeModulesDir);
		}
	}

	private File _getExistentFile(String fileName) {
		Project project = getProject();

		File file = project.file(fileName);

		if (!file.exists()) {
			file = null;
		}

		return file;
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

	private void _npmCacheVerify() {
		Logger logger = getLogger();

		try {
			_npmCacheVerify = true;

			super.executeNode();
		}
		catch (Exception exception) {
			if (logger.isWarnEnabled()) {
				String message = "Unable to run \"npm cache verify\"";

				if (Validator.isNotNull(exception.getMessage())) {
					message = exception.getMessage() + ". " + message;
				}

				logger.warn(message);
			}
		}
		finally {
			_npmCacheVerify = false;
		}
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
			catch (IOException ioException) {
				if (i == npmInstallRetries) {
					throw ioException;
				}

				if (logger.isWarnEnabled()) {
					logger.warn(
						ioException.getMessage() +
							". Running \"npm install\" again");
				}

				_npmCacheVerify();
			}
		}
	}

	private void _npmInstallCheckDigest(boolean reset) throws Exception {
		String digest = _getNodeModulesCacheDigest(this);

		byte[] digestBytes = digest.getBytes(StandardCharsets.UTF_8);

		File nodeModulesDigestFile = getNodeModulesDigestFile();

		Path nodeModulesDigestPath = nodeModulesDigestFile.toPath();

		if (!reset && Files.exists(nodeModulesDigestPath)) {
			byte[] bytes = Files.readAllBytes(nodeModulesDigestPath);

			if (Arrays.equals(bytes, digestBytes)) {
				return;
			}

			reset = true;
		}
		else {
			reset = true;
		}

		_npmInstall(reset);

		Files.write(nodeModulesDigestPath, digestBytes);
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

	private Object _nodeModulesCacheDir;
	private boolean _nodeModulesCacheNativeSync = true;
	private Object _nodeModulesDigestFile;
	private Object _nodeVersion;
	private boolean _npmCacheVerify;
	private Object _npmVersion;
	private Object _removeShrinkwrappedUrls;
	private Object _useNpmCI;

}