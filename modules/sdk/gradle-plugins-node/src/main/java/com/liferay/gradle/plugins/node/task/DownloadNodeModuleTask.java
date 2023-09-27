/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;

import java.nio.file.Files;

import java.util.List;

import org.gradle.api.GradleException;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class DownloadNodeModuleTask extends ExecutePackageManagerTask {

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
					catch (Exception exception) {
						throw new GradleException(
							exception.getMessage(), exception);
					}
				}

			});
	}

	@OutputDirectory
	public File getModuleDir() {
		return new File(getNodeModulesDir(), getModuleName());
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

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		if (isUseNpm()) {
			completeArgs.add("install");
		}
		else {
			completeArgs.add("add");
		}

		completeArgs.add(getModuleName() + "@" + getModuleVersion());

		return completeArgs;
	}

	private Object _moduleName;
	private Object _moduleVersion;

}