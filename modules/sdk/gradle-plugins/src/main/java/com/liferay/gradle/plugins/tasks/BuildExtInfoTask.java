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

import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputFile;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BuildExtInfoTask extends JavaExec {

	public BuildExtInfoTask() {
		setMain("com.liferay.portal.tools.ExtInfoBuilder");
		setMaxHeapSize("256m");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@InputDirectory
	public File getBaseDir() {
		return GradleUtil.toFile(getProject(), _baseDir);
	}

	public File getOutputDir() {
		return GradleUtil.toFile(getProject(), _outputDir);
	}

	@OutputFile
	public File getOutputFile() {
		return new File(
			getOutputDir(), "ext-" + getServletContextName() + ".xml");
	}

	@Input
	public String getServletContextName() {
		return GradleUtil.toString(_servletContextName);
	}

	public void setBaseDir(Object baseDir) {
		_baseDir = baseDir;
	}

	@Input
	public void setOutputDir(Object outputDir) {
		_outputDir = outputDir;
	}

	public void setServletContextName(Object servletContextName) {
		_servletContextName = servletContextName;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = getArgs();

		List<String> completeArgs = new ArrayList<>(args.size() + 3);

		completeArgs.add(FileUtil.getAbsolutePath(getBaseDir()));
		completeArgs.add(FileUtil.getAbsolutePath(getOutputDir()));
		completeArgs.add(getServletContextName());
		completeArgs.addAll(args);

		return completeArgs;
	}

	private Object _baseDir;
	private Object _outputDir;
	private Object _servletContextName;

}