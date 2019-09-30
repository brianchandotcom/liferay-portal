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

import java.io.File;

/**
 * @author Peter Shin
 */
public class PackageRunBuildTask extends PackageRunTask {

	public PackageRunBuildTask() {
		setScriptName("build");
	}

	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	public File getYarnWorkingDir() {
		return GradleUtil.toFile(getProject(), _yarnWorkingDir);
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	public void setYarnWorkingDir(Object yarnWorkingDir) {
		_yarnWorkingDir = yarnWorkingDir;
	}

	private Object _destinationDir;
	private Object _sourceDir;
	private Object _yarnWorkingDir;

}