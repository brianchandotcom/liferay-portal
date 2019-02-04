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

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.tasks.Input;

/**
 * @author David Truong
 * @author Peter Shin
 */
public class NpmRunTask extends BaseNpmCommandTask {

	public NpmRunTask() {
		exclude(_EXCLUDE_DIR_NAMES);
		include(_INCLUDES);

		Project project = getProject();

		setSourceDir(project.getProjectDir());
	}

	@Override
	public String getNpmCommand() {
		return "run-script";
	}

	@Input
	public String getScriptName() {
		return GradleUtil.toString(_scriptName);
	}

	public void setScriptName(Object scriptName) {
		_scriptName = scriptName;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add(getNpmCommand());
		completeArgs.add(getScriptName());

		return completeArgs;
	}

	private static final String[] _EXCLUDE_DIR_NAMES = {
		"bin", "build", "classes", "node_modules", "test-classes", "tmp"
	};

	private static final String[] _INCLUDES = {
		"**/*.*rc", "**/*.css", "**/*.js", "**/*.json", "**/*.jsx", "**/*.soy"
	};

	private Object _scriptName;

}