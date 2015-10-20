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

package com.liferay.gradle.plugins.tld.formatter;

import com.liferay.tld.formatter.TLDFormatterArgs;

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
public class FormatTLDTask extends JavaExec {

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

		args.add("tld.base.dir=" + getBaseDirName());
		args.add("tld.plugin=" + isPlugin());

		return args;
	}

	public String getBaseDirName() {
		return _tldFormatterArgs.getBaseDirName();
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			TLDFormatterPlugin.CONFIGURATION_NAME);
	}

	@Override
	public String getMain() {
		return "com.liferay.tld.formatter.TLDFormatter";
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	public boolean isPlugin() {
		return _tldFormatterArgs.isPlugin();
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	public void setBaseDirName(String baseDirName) {
		_tldFormatterArgs.setBaseDirName(baseDirName);
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setPlugin(boolean plugin) {
		_tldFormatterArgs.setPlugin(plugin);
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private final TLDFormatterArgs _tldFormatterArgs = new TLDFormatterArgs();

}