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
import org.gradle.api.tasks.JavaExec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildWSDDTask extends JavaExec {

	public BuildWSDDTask() {
		setMain("com.liferay.portal.tools.wsdd.builder.WSDDBuilder");
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	public String getBuilderClasspath() {
		return _wsddBuilderArgs.getClassPath();
	}

	public File getInputFile() {
		Project project = getProject();

		return project.file(_wsddBuilderArgs.getFileName());
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

	public void setBuilderClasspath(String builderClasspath) {
		_wsddBuilderArgs.setClassPath(builderClasspath);
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

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

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

	private final WSDDBuilderArgs _wsddBuilderArgs = new WSDDBuilderArgs();

}