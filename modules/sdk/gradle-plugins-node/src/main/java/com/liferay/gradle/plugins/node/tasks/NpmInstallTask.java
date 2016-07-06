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

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.List;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.InputFile;
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

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("install");

		return completeArgs;
	}

}