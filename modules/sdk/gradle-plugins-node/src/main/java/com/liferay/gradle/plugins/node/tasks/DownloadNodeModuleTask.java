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

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;

import java.nio.file.Files;

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
						File packageJSONFile = new File(
							getModuleDir(), "package.json");

						if (!packageJSONFile.exists()) {
							return true;
						}

						String packageJSON = new String(
							Files.readAllBytes(packageJSONFile.toPath()));

						String version = getModuleVersion();

						if (packageJSON.contains(
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

	public void setModuleName(Object moduleName) {
		_moduleName = moduleName;
	}

	public void setModuleVersion(Object moduleVersion) {
		_moduleVersion = moduleVersion;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("install");
		completeArgs.add(getModuleName() + "@" + getModuleVersion());

		return completeArgs;
	}

	private Object _moduleName;
	private Object _moduleVersion;

}