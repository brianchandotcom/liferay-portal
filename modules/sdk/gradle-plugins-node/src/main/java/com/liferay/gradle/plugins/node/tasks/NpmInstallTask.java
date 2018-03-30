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
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;

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

	public File getNodeModulesDir() {
		Project project = getProject();

		return project.file("node_modules");
	}

	public File getPackageJsonFile() {
		Project project = getProject();

		return project.file("package.json");
	}

	public File getPackageLockJsonFile() {
		return _getExistentFile("package-lock.json");
	}

	public File getShrinkwrapJsonFile() {
		return _getExistentFile("npm-shrinkwrap.json");
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

				if (_isCheckDigest()) {
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

		if (isUseNpmCI()) {
			completeArgs.add("ci");
		}
		else {
			completeArgs.add("install");
		}

		return completeArgs;
	}

	private static void _createBinDirLinks(Logger logger, File nodeModulesDir)
		throws IOException {

		JsonSlurper jsonSlurper = new JsonSlurper();

		Path nodeModulesDirPath = nodeModulesDir.toPath();

		Path nodeModulesBinDirPath = nodeModulesDirPath.resolve(
			_NODE_MODULES_BIN_DIR_NAME);

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				nodeModulesDirPath, _directoryStreamFilter)) {

			for (Path dirPath : directoryStream) {
				Path packageJsonPath = dirPath.resolve("package.json");

				if (Files.notExists(packageJsonPath)) {
					continue;
				}

				Map<String, Object> packageJsonMap =
					(Map<String, Object>)jsonSlurper.parse(
						packageJsonPath.toFile());

				Object binObject = packageJsonMap.get("bin");

				if (!(binObject instanceof Map<?, ?>)) {
					continue;
				}

				Map<String, String> binJsonMap = (Map<String, String>)binObject;

				if (binJsonMap.isEmpty()) {
					continue;
				}

				Files.createDirectories(nodeModulesBinDirPath);

				for (Map.Entry<String, String> entry : binJsonMap.entrySet()) {
					String linkFileName = entry.getKey();
					String linkTargetFileName = entry.getValue();

					Path linkPath = nodeModulesBinDirPath.resolve(linkFileName);
					Path linkTargetPath = dirPath.resolve(linkTargetFileName);

					Files.deleteIfExists(linkPath);

					Files.createSymbolicLink(linkPath, linkTargetPath);

					if (logger.isInfoEnabled()) {
						logger.info(
							"Created binary symbolic link {} which targets {}",
							linkPath, linkTargetPath);
					}
				}
			}
		}
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

			_removeBinDirLinks(logger, nodeModulesDir);
		}
		else {
			npmInstallTask._npmInstall(reset);

			_removeBinDirLinks(logger, nodeModulesDir);

			if (logger.isLifecycleEnabled()) {
				logger.lifecycle(
					"Caching node_modules of {} in {}", project,
					nodeModulesCacheDir);
			}

			FileUtil.syncDir(
				project, nodeModulesDir, nodeModulesCacheDir, nativeSync);
		}

		if (!OSDetector.isWindows()) {
			_createBinDirLinks(logger, nodeModulesDir);
		}
	}

	private static void _removeBinDirLinks(
			final Logger logger, File nodeModulesDir)
		throws IOException {

		Files.walkFileTree(
			nodeModulesDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (dirName.equals(_NODE_MODULES_BIN_DIR_NAME)) {
						if (logger.isInfoEnabled()) {
							logger.info(
								"Removing binary symbolic links from {}",
								dirPath);
						}

						FileUtil.deleteSymbolicLinks(dirPath);

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});
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

	private boolean _isCheckDigest() {
		Project project = getProject();

		PluginContainer pluginContainer = project.getPlugins();

		if (!pluginContainer.hasPlugin("com.liferay.cache") &&
			(getNodeModulesCacheDir() == null) &&
			(getNodeModulesDigestFile() != null)) {

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

	private static final String _NODE_MODULES_BIN_DIR_NAME = ".bin";

	private static final DirectoryStream.Filter<Path> _directoryStreamFilter =
		new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				return Files.isDirectory(path);
			}

		};

	private Object _nodeModulesCacheDir;
	private boolean _nodeModulesCacheNativeSync = true;
	private Object _nodeModulesDigestFile;
	private Object _removeShrinkwrappedUrls;
	private Object _useNpmCI;

}