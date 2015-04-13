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
import com.liferay.gradle.plugins.util.StringUtil;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class BuildXsdTask extends Zip {

	public BuildXsdTask() {
		_project = getProject();

		setArchiveName(_project.getName() + "-xbean." + Jar.DEFAULT_EXTENSION);
		setExtension(Jar.DEFAULT_EXTENSION);

		Configuration configuration = GradleUtil.addConfiguration(
			_project, _CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Apache XMLBeans for generating XMLBeans types.");
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
		File rootDir = getRootDir();

		if ((rootDir == null) || !rootDir.exists()) {
			return;
		}

		Task generateTask = addTaskGenerate();

		Task compileTask = addTaskCompile(generateTask);

		from(compileTask.getOutputs());
		from(generateTask.getOutputs());
	}

	@InputDirectory
	public File getRootDir() {
		return _rootDir;
	}

	@InputFiles
	@SkipWhenEmpty
	public Iterable<File> getXsdFiles() {
		File rootDir = getRootDir();

		if (rootDir == null) {
			return Collections.emptyList();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", _project.getProjectDir());
		args.put("include", _project.relativePath(rootDir) + "/**/*.*");

		return _project.fileTree(args);
	}

	public void setRootDir(File rootDir) {
		_rootDir = rootDir;
	}

	protected void addDependencies() {
		GradleUtil.addDependency(
			_project, _CONFIGURATION_NAME, "org.apache.xmlbeans", "xmlbeans",
			"2.5.0");
	}

	protected Task addTaskCompile(Task generateTask) {
		JavaCompile javaCompile = GradleUtil.addTask(
			_project, LiferayJavaPlugin.BUILD_XSD_TASK_NAME + "Compile",
			JavaCompile.class);

		javaCompile.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(javaCompile.getName()));

		javaCompile.setClasspath(getConfiguration());

		final File tmpBinDir = new File(getTmpDir(), "bin");

		javaCompile.setDestinationDir(tmpBinDir);

		javaCompile.setSource(generateTask.getOutputs());

		return javaCompile;
	}

	protected Task addTaskGenerate() {
		JavaExec javaExec = GradleUtil.addTask(
			_project, LiferayJavaPlugin.BUILD_XSD_TASK_NAME + "Generate",
			JavaExec.class);

		final File tmpSrcDir = new File(getTmpDir(), "src");

		javaExec.args("-d");
		javaExec.args(FileUtil.getAbsolutePath(tmpSrcDir));

		javaExec.args("-srconly");

		Iterable<File> xsdFiles = getXsdFiles();

		for (File xsdFile : xsdFiles) {
			javaExec.args(FileUtil.getAbsolutePath(xsdFile));
		}

		javaExec.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(javaExec.getName()));

		javaExec.setClasspath(getConfiguration());
		javaExec.setMain("org.apache.xmlbeans.impl.tool.SchemaCompiler");

		TaskInputs taskInputs = javaExec.getInputs();

		taskInputs.files(xsdFiles);

		TaskOutputs taskOutputs = javaExec.getOutputs();

		taskOutputs.dir(tmpSrcDir);

		return javaExec;
	}

	protected Configuration getConfiguration() {
		return GradleUtil.getConfiguration(_project, _CONFIGURATION_NAME);
	}

	protected File getTmpDir() {
		return new File(_project.getBuildDir(), "build-xsd");
	}

	private static final String _CONFIGURATION_NAME =
		LiferayJavaPlugin.BUILD_XSD_TASK_NAME;

	private final Project _project;
	private File _rootDir;

}