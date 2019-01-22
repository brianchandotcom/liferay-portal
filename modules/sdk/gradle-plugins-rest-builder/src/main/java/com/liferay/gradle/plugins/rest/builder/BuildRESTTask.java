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

package com.liferay.gradle.plugins.rest.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;

/**
 * @author Peter Shin
 */
public class BuildRESTTask extends JavaExec {

	public BuildRESTTask() {
		setMain("com.liferay.portal.tools.rest.builder.RESTBuilder");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@InputFile
	public File getInputFile() {
		return GradleUtil.toFile(getProject(), _inputFile);
	}

	public void setInputFile(Object inputFile) {
		_inputFile = inputFile;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("input.file=" + _relativize(getInputFile()));

		return args;
	}

	private String _relativize(File file) {
		String relativePath = FileUtil.relativize(file, getWorkingDir());

		return relativePath.replace('\\', '/');
	}

	private Object _inputFile;

}