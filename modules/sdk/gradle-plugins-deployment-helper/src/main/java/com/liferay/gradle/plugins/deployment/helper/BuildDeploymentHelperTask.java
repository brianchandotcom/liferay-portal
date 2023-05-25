/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.deployment.helper;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GUtil;
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
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SkipWhenEmpty;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildDeploymentHelperTask extends JavaExec {

	public BuildDeploymentHelperTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.deployment.helper.DeploymentHelper");
	}

	public BuildDeploymentHelperTask deploymentFiles(
		Iterable<?> deploymentFiles) {

		GUtil.addToCollection(_deploymentFiles, deploymentFiles);

		return this;
	}

	public BuildDeploymentHelperTask deploymentFiles(
		Object... deploymentFiles) {

		return deploymentFiles(Arrays.asList(deploymentFiles));
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	@SkipWhenEmpty
	public FileCollection getDeploymentFiles() {
		Project project = getProject();

		return project.files(_deploymentFiles);
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
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

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
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

	public void setDeploymentFiles(Object... deploymentFiles) {
		setDeploymentFiles(Arrays.asList(deploymentFiles));
	}

	public void setDeploymentPath(Object deploymentPath) {
		_deploymentPath = deploymentPath;
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	@Internal
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = new ArrayList<>();

		GUtil.addToCollection(completeArgs, getArgs());

		completeArgs.add("--fileNames=" + getDeploymentFileNames());
		completeArgs.add(
			"--outputFile=" + FileUtil.getAbsolutePath(getOutputFile()));

		File deploymentPath = getDeploymentPath();

		if (deploymentPath != null) {
			completeArgs.add(
				"--path=" + FileUtil.getAbsolutePath(deploymentPath));
		}

		return completeArgs;
	}

	@Input
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