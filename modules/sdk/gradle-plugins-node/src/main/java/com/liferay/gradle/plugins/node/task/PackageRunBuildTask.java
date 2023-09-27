/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Peter Shin
 */
@CacheableTask
public class PackageRunBuildTask extends PackageRunTask {

	public PackageRunBuildTask() {
		setScriptName("build");
	}

	@Internal
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getNpmBridgeRCFile() {
		return _getExistentFile(".npmbridgerc");
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getNpmBundlerRCFile() {
		return _getExistentFile(".npmbundlerrc");
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getNpmScriptsConfigJSFile() {
		return _getExistentFile("npmscripts.config.js");
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

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getWebpackConfigJSFile() {
		return _getExistentFile("webpack.config.js");
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getYarnLockFile() {
		return _getExistentYarnFile("yarn.lock");
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getYarnNpmScriptsConfigJSFile() {
		return _getExistentYarnFile("npmscripts.config.js");
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getYarnPackageJsonFile() {
		return _getExistentYarnFile("package.json");
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getYarnProjectNodeModulesDir() {
		if (isUseNpm()) {
			return null;
		}

		return _getExistentFile("node_modules");
	}

	@Internal
	public File getYarnWorkingDir() {
		return GradleUtil.toFile(getProject(), _yarnWorkingDir);
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	public void setYarnWorkingDir(Object yarnWorkingDir) {
		_yarnWorkingDir = yarnWorkingDir;
	}

	private File _getExistentFile(String fileName) {
		Project project = getProject();

		File file = project.file(fileName);

		if (!file.exists()) {
			file = null;
		}

		return file;
	}

	private File _getExistentYarnFile(String fileName) {
		File yarnWorkingDir = getYarnWorkingDir();

		if (yarnWorkingDir == null) {
			return null;
		}

		File file = new File(yarnWorkingDir, fileName);

		if (!file.exists()) {
			file = null;
		}

		return file;
	}

	private Object _destinationDir;
	private Object _sourceDir;
	private Object _yarnWorkingDir;

}