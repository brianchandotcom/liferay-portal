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

package com.liferay.gradle.plugins.gulp.tasks;

import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Truong
 */
public class GulpTask extends ExecuteNodeTask {

	@Override
	public void executeNode() {
		List<Object> args = new ArrayList<>();

		File scriptFile = new File(getNodeDir(), getScriptFileName());

		args.add(scriptFile);

		args.add(getGulpCommand());

		setArgs(args);

		super.setWorkingDir(getWorkingDir());

		super.executeNode();
	}

	public String getGulpCommand() {
		return _gulpCommand;
	}

	public String getScriptFileName() {
		return _SCRIPT_FILE_NAME;
	}

	public void setGulpCommand(String gulpCommand) {
		_gulpCommand = gulpCommand;
	}

	private static final String _SCRIPT_FILE_NAME =
		"node_modules/gulp/bin/gulp.js";

	private String _gulpCommand = "";

}