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

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.GradleException;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author Andrea Di Giorgi
 */
public class DownloadNodeModuleTask extends ExecuteNpmTask {

	public DownloadNodeModuleTask() {
		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					try {
						File packageJsonFile = new File(
							getModuleDir(), "package.json");

						if (!packageJsonFile.exists()) {
							return true;
						}

						String packageJson = new String(
							Files.readAllBytes(packageJsonFile.toPath()));

						String version = getModuleVersion();

						if (packageJson.contains(
								"\"version\": \"" + version + "\"")) {

							return false;
						}

						return true;
					}
					catch (Exception e) {
						throw new GradleException(e.getMessage(), e);
					}
				}

			});
	}

	@Override
	public void executeNode() {
		setArgs(getCompleteArgs());

		super.setWorkingDir(getWorkingDir());

		super.executeNode();
	}

	@OutputDirectory
	public File getModuleDir() {
		File nodeModulesDir = new File(getWorkingDir(), "node_modules");

		return new File(nodeModulesDir, getModuleName());
	}

	@Input
	public String getModuleName() {
		return GradleUtil.toString(_moduleName);
	}

	@Input
	public String getModuleVersion() {
		return GradleUtil.toString(_moduleVersion);
	}

	@Override
	public File getWorkingDir() {
		return getNodeDir();
	}

	public void setModuleName(Object moduleName) {
		_moduleName = moduleName;
	}

	public void setModuleVersion(Object moduleVersion) {
		_moduleVersion = moduleVersion;
	}

	@Override
	public void setWorkingDir(Object workingDir) {
		throw new UnsupportedOperationException();
	}

	protected List<Object> getCompleteArgs() {
		List<Object> completeArgs = new ArrayList<>();

		completeArgs.add("install");
		completeArgs.add(getModuleName() + "@" + getModuleVersion());

		completeArgs.addAll(getArgs());

		return completeArgs;
	}

	private Object _moduleName;
	private Object _moduleVersion;

}