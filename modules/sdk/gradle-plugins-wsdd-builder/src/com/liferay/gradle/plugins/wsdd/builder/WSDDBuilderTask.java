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

import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderTask extends JavaExec {

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

		Project project = getProject();

		ExtensionContainer extensionContainer = project.getExtensions();

		WSDDBuilderArgs wsddBuilderArgs = extensionContainer.getByType(
			WSDDBuilderArgs.class);

		args.add("wsdd.class.path=" + wsddBuilderArgs.getClassPath());
		args.add(
			"wsdd.input.file=" +
				_getAbsolutePath(wsddBuilderArgs.getFileName()));
		args.add(
			"wsdd.output.path=" +
				_getAbsolutePath(wsddBuilderArgs.getOutputPath()) + "/");
		args.add(
			"wsdd.server.config.file=" +
				_getAbsolutePath(wsddBuilderArgs.getServerConfigFileName()));
		args.add(
			"wsdd.service.namespace=" + wsddBuilderArgs.getServiceNamespace());

		return args;
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			WSDDBuilderPlugin.CONFIGURATION_NAME);
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.wsdd.builder.WSDDBuilder";
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

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private String _getAbsolutePath(String fileName) {
		File file = new File(fileName);

		if (!file.isAbsolute()) {
			Project project = getProject();

			file = new File(project.getProjectDir(), fileName);
		}

		String absolutePath = file.getAbsolutePath();

		return absolutePath.replace('\\', '/');
	}

}