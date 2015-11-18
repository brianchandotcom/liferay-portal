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

package com.liferay.gradle.plugins.deployment.helper;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class BuildDeploymentHelperTask extends JavaExec {

	public BuildDeploymentHelperTask() {
		setMain("com.liferay.deployment.helper.DeploymentHelper");
	}

	public BuildDeploymentHelperTask deploymentFiles(
		Iterable<?> deploymentFiles) {

		GUtil.addToCollection(_deploymentFiles, deploymentFiles);

		return this;
	}

	public BuildDeploymentHelperTask deploymentFiles(
		Object ... deploymentFiles) {

		return deploymentFiles(Arrays.asList(deploymentFiles));
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	public FileCollection getDeploymentFiles() {
		Project project = getProject();

		return project.files(_deploymentFiles);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getDeploymentInputFiles() {
		Project project = getProject();

		List<Object> inputFiles = new ArrayList<>();

		for (File deploymentFile : getDeploymentFiles()) {
			if (deploymentFile.isDirectory()) {
				FileTree fileTree = getJarsFileTree(deploymentFile);

				inputFiles.add(fileTree);
			}
			else {
				inputFiles.add(deploymentFile);
			}
		}

		return project.files(inputFiles);
	}

	@Input
	public File getDeploymentPath() {
		return GradleUtil.toFile(getProject(), _deploymentPath);
	}

	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	public void setDeploymentFiles(Iterable<?> deploymentFiles) {
		_deploymentFiles.clear();

		deploymentFiles(deploymentFiles);
	}

	public void setDeploymentFiles(Object ... deploymentFiles) {
		setDeploymentFiles(Arrays.asList(deploymentFiles));
	}

	public void setDeploymentPath(Object deploymentPath) {
		_deploymentPath = deploymentPath;
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	protected List<String> getCompleteArgs() {
		List<String> completeArgs = new ArrayList<>();

		GUtil.addToCollection(completeArgs, getArgs());

		completeArgs.add("deployment.files=" + getDeploymentFileNames());
		completeArgs.add(
			"deployment.output.file=" +
				FileUtil.getAbsolutePath(getOutputFile()));
		completeArgs.add(
			"deployment.path=" + FileUtil.getAbsolutePath(getDeploymentPath()));

		return completeArgs;
	}

	protected String getDeploymentFileNames() {
		StringBuilder sb = new StringBuilder();

		for (File file : getDeploymentFiles()) {
			sb.append(FileUtil.getAbsolutePath(file));
			sb.append(',');
		}

		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

	protected FileTree getJarsFileTree(File dir) {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", dir);
		args.put("include", "**/*.jar");

		return project.fileTree(args);
	}

	private final List<Object> _deploymentFiles = new ArrayList<>();
	private Object _deploymentPath;
	private Object _outputFile;

}