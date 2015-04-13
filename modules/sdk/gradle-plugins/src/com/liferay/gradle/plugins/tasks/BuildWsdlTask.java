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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.plugins.LiferayJavaPlugin;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class BuildWsdlTask extends DefaultTask {

	public BuildWsdlTask() {
		_project = getProject();

		Configuration configuration = GradleUtil.addConfiguration(
			_project, _CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Apache Axis for generating WSDL client stubs.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependencies();
				}

			});
	}

	public void addTasks() {
		Iterable<File> wsdlFiles = getWsdlFiles();

		for (File wsdlFile : wsdlFiles) {
			addTasks(wsdlFile);
		}
	}

	public File getDestinationDir() {
		return _destinationDir;
	}

	public FileCollection getRootDirs() {
		return _project.files(_rootDirs);
	}

	@InputFiles
	@SkipWhenEmpty
	public Iterable<File> getWsdlFiles() {
		FileCollection rootDirs = getRootDirs();

		if ((rootDirs == null) || rootDirs.isEmpty()) {
			return Collections.emptyList();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", _project.getProjectDir());

		for (File dir : rootDirs) {
			args.put("include", _project.relativePath(dir) + "/*.wsdl");
		}

		return _project.fileTree(args);
	}

	@Input
	public boolean isIncludeSource() {
		return _includeSource;
	}

	public BuildWsdlTask rootDirs(Object... rootDirs) {
		for (Object rootDir : rootDirs) {
			_rootDirs.add(rootDir);
		}

		return this;
	}

	public void setDestinationDir(File destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setIncludeSource(boolean includeSource) {
		_includeSource = includeSource;
	}

	public void setRootDirs(Object rootDirs) {
		_rootDirs.clear();

		_rootDirs.add(rootDirs);
	}

	protected void addDependencies() {
		GradleUtil.addDependency(
			_project, _CONFIGURATION_NAME, "axis", "axis", "1.4");
		GradleUtil.addDependency(
			_project, _CONFIGURATION_NAME, "javax.activation", "activation",
			"1.1");
		GradleUtil.addDependency(
			_project, _CONFIGURATION_NAME, "javax.mail", "mail", "1.4");
	}

	protected Task addTaskCompile(
		File wsdlFile, File tmpDir, Task generateTask) {

		String taskName = GradleUtil.getTaskName(
			LiferayJavaPlugin.BUILD_WSDL_TASK_NAME + "Compile", wsdlFile);

		JavaCompile javaCompile = GradleUtil.addTask(
			_project, taskName, JavaCompile.class);

		javaCompile.setClasspath(getConfiguration());

		File tmpBinDir = new File(tmpDir, "bin");

		javaCompile.setDestinationDir(tmpBinDir);

		javaCompile.setSource(generateTask.getOutputs());

		return javaCompile;
	}

	protected Task addTaskGenerate(File wsdlFile, File tmpDir) {
		String taskName = GradleUtil.getTaskName(
			LiferayJavaPlugin.BUILD_WSDL_TASK_NAME + "Generate", wsdlFile);

		JavaExec javaExec = GradleUtil.addTask(
			_project, taskName, JavaExec.class);

		File tmpSrcDir = new File(tmpDir, "src");

		javaExec.args("--output=" + FileUtil.getAbsolutePath(tmpSrcDir));

		javaExec.args(FileUtil.getAbsolutePath(wsdlFile));
		javaExec.setClasspath(getConfiguration());
		javaExec.setMain("org.apache.axis.wsdl.WSDL2Java");

		TaskInputs taskInputs = javaExec.getInputs();

		taskInputs.file(wsdlFile);

		TaskOutputs taskOutputs = javaExec.getOutputs();

		taskOutputs.dir(tmpSrcDir);

		return javaExec;
	}

	protected Task addTaskJar(
		File wsdlFile, Task compileTask, Task generateTask) {

		String taskName = GradleUtil.getTaskName(
			LiferayJavaPlugin.BUILD_WSDL_TASK_NAME, wsdlFile);

		Jar jar = GradleUtil.addTask(_project, taskName, Jar.class);

		jar.from(compileTask.getOutputs());

		if (isIncludeSource()) {
			jar.from(generateTask.getOutputs());
		}

		jar.setDestinationDir(getDestinationDir());

		String wsdlName = FileUtil.stripExtension(wsdlFile.getName());

		jar.setArchiveName(wsdlName + "-ws.jar");

		return jar;
	}

	protected void addTasks(File wsdlFile) {
		String tmpDirName =
			"build-wsdl/" + FileUtil.stripExtension(wsdlFile.getName());

		File tmpDir = new File(_project.getBuildDir(), tmpDirName);

		Task generateTask = addTaskGenerate(wsdlFile, tmpDir);

		Task compileTask = addTaskCompile(wsdlFile, tmpDir, generateTask);

		Task jarTask = addTaskJar(wsdlFile, compileTask, generateTask);

		TaskOutputs taskOutputs = getOutputs();

		taskOutputs.file(jarTask.getOutputs());
	}

	protected Configuration getConfiguration() {
		return GradleUtil.getConfiguration(_project, _CONFIGURATION_NAME);
	}

	private static final String _CONFIGURATION_NAME =
		LiferayJavaPlugin.BUILD_WSDL_TASK_NAME;

	private File _destinationDir;
	private boolean _includeSource = true;
	private final Project _project;
	private final List<Object> _rootDirs = new ArrayList<>();

}