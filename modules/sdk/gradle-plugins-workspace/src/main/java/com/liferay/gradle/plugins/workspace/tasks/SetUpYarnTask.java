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

package com.liferay.gradle.plugins.workspace.tasks;

import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author David Truong
 */
public class SetUpYarnTask extends DefaultTask {

	public SetUpYarnTask excludes(Iterable<Object> excludes) {
		GUtil.addToCollection(_excludes, excludes);

		return this;
	}

	public SetUpYarnTask excludes(Object... excludes) {
		return excludes(Arrays.asList(excludes));
	}

	@Input
	public List<String> getExcludes() {
		return GradleUtil.toStringList(_excludes);
	}

	@OutputFile
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getPackageJsonFile() {
		Project project = getProject();

		return project.file("package.json");
	}

	@Input
	public List<String> getYarnWorkspaces() throws IOException {
		Project project = getProject();

		File rootDir = project.getRootDir();

		Path rootPath = rootDir.toPath();

		return FileUtil.getRelativePaths(
			rootPath, "package.json", getExcludes(), true);
	}

	public void setExcludes(Iterable<Object> excludes) {
		_excludes.clear();

		excludes(excludes);
	}

	public void setExcludes(Object... excludes) {
		setExcludes(Arrays.asList(excludes));
	}

	@TaskAction
	public void setUpYarn() throws IOException {
		_defineWorkspaces();
	}

	private void _defineWorkspaces() throws IOException {
		final File file = getPackageJsonFile();

		final Path path = file.toPath();

		if (!file.exists()) {
			Files.write(path, "{}".getBytes());
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> packageJsonMap =
			(Map<String, Object>)jsonSlurper.parse(file);

		Map<String, Object> workspaces =
			(Map<String, Object>)packageJsonMap.get("workspaces");

		if (workspaces == null) {
			packageJsonMap.put("private", true);

			workspaces = new HashMap<>();

			packageJsonMap.put("workspaces", workspaces);
		}

		List<String> packages = getYarnWorkspaces();

		workspaces.put("packages", packages);

		String packageJSON = JsonOutput.prettyPrint(
			JsonOutput.toJson(packageJsonMap));

		Files.write(path, packageJSON.getBytes(StandardCharsets.UTF_8));
	}

	private final List<Object> _excludes = Arrays.asList(
		".gradle", "build", "build_gradle", "dist", "gradle", "node_modules",
		"node_modules_cache", "src");

}