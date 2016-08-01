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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.util.FileUtil;
import com.liferay.gradle.plugins.node.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * @author Andrea Di Giorgi
 */
public class ExecuteNodeScriptTask extends ExecuteNodeTask {

	@Override
	public void executeNode() throws Exception {
		setArgs(getCompleteArgs());

		super.executeNode();
	}

	@Input
	@Optional
	public File getScriptFile() {
		return GradleUtil.toFile(getProject(), _scriptFile);
	}

	public void setScriptFile(Object scriptFile) {
		_scriptFile = scriptFile;
	}

	protected List<String> getCompleteArgs() {
		File scriptFile = getScriptFile();

		if (scriptFile == null) {
			return getArgs();
		}

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add(FileUtil.getAbsolutePath(scriptFile));

		completeArgs.addAll(getArgs());

		return completeArgs;
	}

	private Object _scriptFile;

}