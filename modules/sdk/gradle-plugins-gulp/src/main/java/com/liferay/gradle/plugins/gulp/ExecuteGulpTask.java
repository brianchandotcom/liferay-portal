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

import com.liferay.gradle.plugins.node.tasks.ExecuteNodeScriptTask;
import com.liferay.gradle.util.GradleUtil;

import java.util.List;

import org.gradle.api.tasks.Input;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class ExecuteGulpTask extends ExecuteNodeScriptTask {

	public ExecuteGulpTask() {
		setScriptFile("node_modules/gulp/bin/gulp.js");
	}

	@Input
	public String getGulpCommand() {
		return GradleUtil.toString(_gulpCommand);
	}

	public void setGulpCommand(Object gulpCommand) {
		_gulpCommand = gulpCommand;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add(getGulpCommand());

		return completeArgs;
	}

	private Object _gulpCommand;

}