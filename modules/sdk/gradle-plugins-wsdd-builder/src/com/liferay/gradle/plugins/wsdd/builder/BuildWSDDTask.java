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

package com.liferay.gradle.plugins.wsdd.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildWSDDTask extends JavaExec {

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("wsdd.class.path=" + getBuilderClasspath());
		args.add("wsdd.input.file=" + FileUtil.getAbsolutePath(getInputFile()));
		args.add(
			"wsdd.output.path=" +
				FileUtil.getAbsolutePath(getOutputDir()) + "/");
		args.add(
			"wsdd.server.config.file=" +
				FileUtil.getAbsolutePath(getServerConfigFile()));
		args.add("wsdd.service.namespace=" + getServiceNamespace());

		return args;
	}

	public String getBuilderClasspath() {
		return _wsddBuilderArgs.getClassPath();
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			WSDDBuilderPlugin.CONFIGURATION_NAME);
	}

	public File getInputFile() {
		Project project = getProject();

		return project.file(_wsddBuilderArgs.getFileName());
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.wsdd.builder.WSDDBuilder";
	}

	public File getOutputDir() {
		Project project = getProject();

		return project.file(_wsddBuilderArgs.getOutputPath());
	}

	public File getServerConfigFile() {
		Project project = getProject();

		return project.file(_wsddBuilderArgs.getServerConfigFileName());
	}

	public String getServiceNamespace() {
		return _wsddBuilderArgs.getServiceNamespace();
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	public void setBuilderClasspath(String builderClasspath) {
		_wsddBuilderArgs.setClassPath(builderClasspath);
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setInputFileName(String inputFileName) {
		_wsddBuilderArgs.setFileName(inputFileName);
	}

	public void setOutputDirName(String outputDirName) {
		_wsddBuilderArgs.setOutputPath(outputDirName);
	}

	public void setServerConfigFileName(String serverConfigFileName) {
		_wsddBuilderArgs.setServerConfigFileName(serverConfigFileName);
	}

	public void setServiceNamespace(String serviceNamespace) {
		_wsddBuilderArgs.setServiceNamespace(serviceNamespace);
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private final WSDDBuilderArgs _wsddBuilderArgs = new WSDDBuilderArgs();

}