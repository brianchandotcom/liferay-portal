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

package com.liferay.gradle.plugins.gulp;

import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.util.GUtil;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class ExecuteGulpTask extends ExecuteNodeTask {

	@Override
	public void executeNode() {
		setArgs(getCompleteArgs());

		super.executeNode();
	}

	@Input
	public String getGulpCommand() {
		return GradleUtil.toString(_gulpCommand);
	}

	@Input
	public File getScriptFile() {
		return GradleUtil.toFile(getProject(), _scriptFile);
	}

	public void setGulpCommand(Object gulpCommand) {
		_gulpCommand = gulpCommand;
	}

	public void setScriptFile(Object scriptFile) {
		_scriptFile = scriptFile;
	}

	protected List<Object> getCompleteArgs() {
		List<Object> completeArgs = new ArrayList<>();

		completeArgs.add(FileUtil.getAbsolutePath(getScriptFile()));

		completeArgs.add(getGulpCommand());

		GUtil.addToCollection(completeArgs, getArgs());

		return completeArgs;
	}

	private Object _gulpCommand;
	private Object _scriptFile = "node_modules/gulp/bin/gulp.js";

}