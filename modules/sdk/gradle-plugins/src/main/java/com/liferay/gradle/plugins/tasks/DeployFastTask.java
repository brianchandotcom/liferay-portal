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

import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author Gregory Amerson
 */
public class DeployFastTask extends DefaultTask {

	/**
	 * Returns the directory to deploy files into.
	 *
	 * @return The destination dir.
	 */
	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	/**
	 * Sets the directory to deploy files into.
	 *
	 * @param destinationDir The destination directory. Must not be null.
	 */
	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	private Object _destinationDir;

}